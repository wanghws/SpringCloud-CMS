<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.operationName" :placeholder="$t('manage.operationName')" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.name" :placeholder="$t('manage.name')" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-date-picker
        v-model="listQuery.startTime"
        class="filter-item"
        type="datetime"
        clearable
        format="yyyy-MM-dd HH:mm"
        value-format="yyyy-MM-dd HH:mm"
        :placeholder="$t('manage.startTime')"
      />
      <el-date-picker
        v-model="listQuery.endTime"
        class="filter-item"
        type="datetime"
        clearable
        format="yyyy-MM-dd HH:mm"
        value-format="yyyy-MM-dd HH:mm"
        :placeholder="$t('manage.endTime')"
      />
      <el-select v-model="listQuery.status" :placeholder="$t('manage.status')" clearable class="filter-item" style="width: 130px">
        <el-option v-for="item in statusOptions" :key="item.key" :label="item.display_name" :value="item.key" />
      </el-select>
      <el-select v-model="listQuery.hidden" :placeholder="$t('manage.hidden')" clearable class="filter-item" style="width: 130px">
        <el-option v-for="item in hiddenOptions" :key="item.key" :label="item.display_name" :value="item.key" />
      </el-select>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        {{ $t('table.search') }}
      </el-button>
      <el-button v-if="checkPermission(['ROLE_PERMISSION_SAVE'])" class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
        {{ $t('table.add') }}
      </el-button>
    </div>

    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
    >
      <el-table-column :label="$t('manage.parent')" min-width="5%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.parentName }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('manage.name')" min-width="8%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('manage.permission')" min-width="8%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.permission }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('manage.remark')" min-width="5%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.remark }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('manage.operationName')" min-width="5%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.operationName }}</span>
        </template>
      </el-table-column>

      <el-table-column :label="$t('manage.createTime')" min-width="8%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('manage.updateTime')" min-width="8%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.updateTime }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('manage.hidden')" min-width="5%" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.hidden === 0 ? 'success':'info'">{{ scope.row.hidden | hiddenFilter }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('manage.status')" min-width="5%" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === 0 ? 'success':'info'">{{ scope.row.status | statusFilter }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.actions')" align="center" min-width="10%" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-if="scope.row.status === 0" type="primary" size="mini" @click="handleUpdate(scope.row)">
            {{ $t('table.edit') }}
          </el-button>
          <el-button v-if="scope.row.status === 0" size="mini" type="warning" @click="handleModifyStatus(scope.row.id,1)">
            {{ $t('table.stop') }}
          </el-button>
          <el-button v-if="scope.row.status === 1" size="mini" type="success" @click="handleModifyStatus(scope.row.id,0)">
            {{ $t('table.active') }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.current" :limit.sync="listQuery.size" @pagination="getList" />

    <el-dialog :title="$t('table.add')" :visible.sync="dialogAddFormVisible">
      <el-form ref="addForm" :rules="rules" :model="temp" label-position="left" label-width="85px" style="width: 400px; margin-left:60px;">
        <el-form-item :label="$t('manage.parent')" prop="parentId">
          <treeselect v-model="temp.parentId" :show-count="true" :default-expand-level="6" :multiple="false" :options="permissionTree" />
        </el-form-item>
        <el-form-item :label="$t('manage.name')" prop="name">
          <el-input v-model="temp.name" />
        </el-form-item>
        <el-form-item :label="$t('manage.permission')" prop="permission">
          <el-input v-model="temp.permission" />
        </el-form-item>
        <el-form-item :label="$t('manage.url')" prop="url">
          <el-input v-model="temp.url" />
        </el-form-item>
        <el-form-item :label="$t('manage.remark')" prop="remark">
          <el-input v-model="temp.remark" type="remark" />
        </el-form-item>
        <el-form-item :label="$t('manage.sort')" prop="remark">
          <el-input v-model="temp.sort" type="remark" />
        </el-form-item>
        <el-form-item :label="$t('manage.hidden')" prop="hidden">
          <el-select v-model="temp.hidden" :multiple="false">
            <el-option v-for="item in hiddenOptions" :key="item.key" :label="item.display_name" :value="item.key" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogAddFormVisible = false">
          {{ $t('table.cancel') }}
        </el-button>
        <el-button type="primary" @click="createData()">
          {{ $t('table.save') }}
        </el-button>
      </div>
    </el-dialog>

    <el-dialog :title="$t('table.modify')" :visible.sync="dialogModifyFormVisible">
      <el-form ref="modifyForm" :rules="modifyRules" :model="modify" label-position="left" label-width="85px" style="width: 400px; margin-left:50px;">
        <el-form-item :label="$t('manage.parent')" prop="parentId">
          <treeselect v-model="modify.parentId" :show-count="true" :default-expand-level="6" :multiple="false" :options="permissionTree" />
        </el-form-item>
        <el-form-item :label="$t('manage.name')" prop="name">
          <el-input v-model="modify.name" />
        </el-form-item>
        <el-form-item :label="$t('manage.permission')" prop="permission">
          <el-input v-model="modify.permission" />
        </el-form-item>
        <el-form-item :label="$t('manage.url')" prop="url">
          <el-input v-model="modify.url" />
        </el-form-item>
        <el-form-item :label="$t('manage.remark')" prop="remark">
          <el-input v-model="modify.remark" type="remark" />
        </el-form-item>
        <el-form-item :label="$t('manage.sort')" prop="remark">
          <el-input v-model="modify.sort" type="remark" />
        </el-form-item>
        <el-form-item :label="$t('manage.hidden')" prop="hidden">
          <el-select v-model="modify.hidden" :multiple="false" :selected="modify.hidden">
            <el-option v-for="item in hiddenOptions" :key="item.key" :label="item.display_name" :value="item.key" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogModifyFormVisible = false">
          {{ $t('table.cancel') }}
        </el-button>
        <el-button type="primary" @click="updateData()">
          {{ $t('table.save') }}
        </el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="dialogPvVisible" title="Reading statistics">
      <el-table :data="pvData" border fit highlight-current-row style="width: 100%">
        <el-table-column prop="key" label="Channel" />
        <el-table-column prop="pv" label="Pv" />
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogPvVisible = false">{{ $t('table.confirm') }}</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { validUsername } from '@/utils/validate'
import { permissionList, permissionSave, permissionStatus, permissionTreeList } from '@/api/permission'
import waves from '@/directive/waves'
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import checkPermission from '@/utils/permission'

const statusOptions = [
  { key: '0', display_name: '正常' },
  { key: '1', display_name: '停用' }
]

const hiddenOptions = [
  { key: 0, display_name: '显示' },
  { key: 1, display_name: '隐藏' }
]

const statusKeyValue = statusOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

const hiddenKeyValue = hiddenOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

export default {
  name: 'AccountManage',
  components: { Pagination, Treeselect },
  directives: { waves },
  filters: {
    hiddenFilter(hidden) {
      return hiddenKeyValue[hidden]
    },
    statusFilter(status) {
      return statusKeyValue[status]
    }
  },
  data() {
    const validateName = (rule, value, callback) => {
      if (!value || !validUsername(value)) {
        callback(new Error('权限名称错误'))
      } else {
        callback()
      }
    }
    return {
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        current: 1,
        size: 20,
        operationName: undefined,
        parentName: undefined,
        name: undefined,
        permission: undefined,
        hidden: undefined,
        startTime: undefined,
        endTime: undefined,
        status: undefined
      },
      hiddenOptions,
      statusOptions,
      showReviewer: false,
      temp: {
        id: undefined,
        parentId: undefined,
        name: undefined,
        permission: undefined,
        url: undefined,
        remark: undefined,
        hidden: undefined,
        sort: undefined,
        parentName: undefined,
        operationName: undefined
      },
      modify: {
        id: undefined,
        parentId: undefined,
        name: undefined,
        remark: undefined,
        parentName: undefined,
        operationName: undefined,
        sort: undefined,
        hidden: undefined
      },
      permissionTree: [],
      dialogAddFormVisible: false,
      dialogModifyFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '修改用户',
        create: '添加用户'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {
        name: [{ required: true, trigger: 'blur', validator: validateName }],
        permission: [{ required: true, trigger: 'blur' }],
        url: [{ required: true, trigger: 'blur' }],
        hidden: [{ required: true, trigger: 'blur' }]
      },
      modifyRules: {
        name: [{ required: true, trigger: 'blur', validator: validateName }]
      },
      downloadLoading: false
    }
  },
  created() {
    this.listQuery.page = 1
    this.permissionTreeList()
    this.getList()
  },
  methods: {
    checkPermission,
    getList() {
      this.listLoading = true
      permissionList(this.listQuery).then(response => {
        this.listLoading = false
        this.list = response.data.records
        this.total = response.data.total
      }).catch(() => {
        this.listLoading = false
      })
    },
    permissionTreeList() {
      permissionTreeList({}).then(response => {
        this.permissionTree = response.data
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        parentId: undefined,
        name: undefined,
        remark: undefined,
        hidden: undefined,
        sort: undefined
      }
    },
    resetModify() {
      this.modify = {
        id: undefined,
        parentId: undefined,
        name: undefined,
        remark: undefined,
        hidden: undefined,
        sort: undefined
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogAddFormVisible = true
      this.$nextTick(() => {
        this.$refs['addForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['addForm'].validate((valid) => {
        if (valid) {
          permissionSave(this.temp).then(response => {
            this.temp.id = response.data.id
            this.temp.parentId = response.data.parentId
            this.temp.name = response.data.name
            this.temp.permission = response.data.permission
            this.temp.url = response.data.url
            this.temp.remark = response.data.remark
            this.temp.hidden = response.data.hidden
            this.temp.sort = response.data.sort
            this.temp.parentName = response.data.parentName
            this.temp.operationName = response.data.operationName
            this.temp.createTime = response.data.createTime
            this.temp.updateTime = response.data.updateTime
            this.temp.status = response.data.status// statusKeyValue[response.data.status]

            this.list.unshift(this.temp)
            this.dialogAddFormVisible = false

            this.$notify({
              title: '成功',
              message: '添加成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    handleUpdate(row) {
      this.resetModify()
      this.modify = Object.assign({}, row)
      this.dialogModifyFormVisible = true

      this.$nextTick(() => {
        this.$refs['modifyForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['modifyForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.modify)
          permissionSave(tempData).then(response => {
            for (const v of this.list) {
              if (v.id === response.data.id) {
                const index = this.list.indexOf(v)
                v.parentId = response.data.parentId
                v.name = response.data.name
                v.permission = response.data.permission
                v.url = response.data.url
                v.remark = response.data.remark
                v.hidden = response.data.hidden
                v.sort = response.data.sort
                v.parentName = response.data.parentName
                v.operationName = response.data.operationName
                v.createTime = response.data.createTime
                v.updateTime = response.data.updateTime
                v.status = response.data.status// statusKeyValue[response.data.status]

                this.list.splice(index, 1, v)
                break
              }
            }
            this.dialogModifyFormVisible = false
            this.$notify({
              title: '成功',
              message: '修改成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    handleModifyStatus(id, status) {
      permissionStatus({ 'id': id, 'status': status }).then(() => {
        for (const v of this.list) {
          if (v.id === id) {
            const index = this.list.indexOf(v)
            v.status = status
            this.list.splice(index, 1, v)
            break
          }
        }
        this.$notify({
          title: '成功',
          message: '修改成功',
          type: 'success',
          duration: 2000
        })
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    }
  }
}
</script>

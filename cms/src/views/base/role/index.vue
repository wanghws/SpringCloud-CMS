<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.operationName" :placeholder="$t('manage.operationName')" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.officeName" :placeholder="$t('manage.office')" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.name" :placeholder="$t('manage.role')" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
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
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        {{ $t('table.search') }}
      </el-button>
      <el-button v-if="checkPermission(['ROLE_SAVE'])" class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
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
      <el-table-column :label="$t('manage.role')" min-width="5%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('manage.office')" min-width="5%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.officeName }}</span>
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
      <el-table-column :label="$t('manage.status')" min-width="5%" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === 0 ? 'success':'info'">{{ scope.row.status | statusFilter }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.actions')" align="center" min-width="10%" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-if="scope.row.status === 0" type="primary" size="small" @click="handleAddPermission(scope.row.id)">
            {{ $t('table.addPermission') }}
          </el-button>
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
        <el-form-item :label="$t('manage.office')" prop="parentId">
          <treeselect v-model="temp.officeId" :show-count="true" :default-expand-level="2" :multiple="false" :options="officeTree" :placeholder="$t('manage.office')" />
        </el-form-item>
        <el-form-item :label="$t('manage.name')" prop="name">
          <el-input v-model="temp.name" />
        </el-form-item>
        <el-form-item :label="$t('manage.remark')" prop="permission">
          <el-input v-model="temp.remark" />
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

    <el-dialog :title="$t('table.addPermission')" :visible.sync="dialogAddPermissionFormVisible">
      <el-form ref="addPermissionForm" :rules="modifyRules" :model="addPermission" label-position="left" label-width="85px" style="height:500px;overflow: auto;">
        <el-form-item :label="$t('manage.permission')" prop="parentId">
          <treeselect v-model="addPermission.permissionIds" always-open="true" value-consists-of="ALL_WITH_INDETERMINATE" :show-count="true" :default-expand-level="6" :multiple="true" :options="permissionTree" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogAddPermissionFormVisible = false">
          {{ $t('table.cancel') }}
        </el-button>
        <el-button type="primary" @click="addPermissionData()">
          {{ $t('table.save') }}
        </el-button>
      </div>
    </el-dialog>

    <el-dialog :title="$t('table.modify')" :visible.sync="dialogModifyFormVisible">
      <el-form ref="modifyForm" :rules="modifyRules" :model="modify" label-position="left" label-width="85px" style="width: 400px; margin-left:50px;">
        <el-form-item :label="$t('manage.office')" prop="parentId">
          <treeselect v-model="modify.officeId" :show-count="true" :default-expand-level="2" :multiple="false" :options="officeTree" :placeholder="$t('manage.office')" />
        </el-form-item>
        <el-form-item :label="$t('manage.name')" prop="name">
          <el-input v-model="modify.name" />
        </el-form-item>
        <el-form-item :label="$t('manage.remark')" prop="remark">
          <el-input v-model="modify.remark" type="remark" />
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
import { currentPermissions, roleList, rolePermissionSave, roleSave, roleStatus } from '@/api/role'
import { officeTreeList } from '@/api/office'
import { permissionTreeList } from '@/api/permission'
import waves from '@/directive/waves'
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import checkPermission from '@/utils/permission'

const statusOptions = [
  { key: '0', display_name: '正常' },
  { key: '1', display_name: '禁用' }
]

const statusKeyValue = statusOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

export default {
  name: 'AccountManage',
  components: { Pagination, Treeselect },
  directives: { waves },
  filters: {
    statusFilter(status) {
      return statusKeyValue[status]
    }
  },
  data() {
    const validateName = (rule, value, callback) => {
      if (!value || !validUsername(value)) {
        callback(new Error('部门名称错误'))
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
        officeName: undefined,
        name: undefined,
        startTime: undefined,
        endTime: undefined,
        status: undefined
      },
      statusOptions,
      showReviewer: false,
      temp: {
        id: undefined,
        name: undefined,
        officeId: undefined,
        remark: undefined,
        officeName: undefined,
        operationName: undefined
      },
      modify: {
        id: undefined,
        name: undefined,
        officeId: undefined,
        remark: undefined,
        officeName: undefined,
        operationName: undefined
      },
      addPermission: {
        id: undefined,
        permissionIds: undefined
      },
      officeTree: [],
      permissionTree: [],
      dialogAddFormVisible: false,
      dialogModifyFormVisible: false,
      dialogAddPermissionFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '修改用户',
        create: '添加用户'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {
        officeId: [{ required: true, trigger: 'blur', message: '请选择部门' }],
        name: [{ required: true, trigger: 'blur', validator: validateName }]
      },
      modifyRules: {
        name: [{ required: true, trigger: 'blur', validator: validateName }]
      },
      downloadLoading: false
    }
  },
  created() {
    this.listQuery.page = 1
    this.officeTreeList()
    this.permissionTreeList()
    this.getList()
  },
  methods: {
    checkPermission,
    getList() {
      this.listLoading = true
      roleList(this.listQuery).then(response => {
        this.listLoading = false
        this.list = response.data.records
        this.total = response.data.total
      }).catch(() => {
        this.listLoading = false
      })
    },
    officeTreeList() {
      officeTreeList({}).then(response => {
        this.officeTree = response.data
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
        remark: undefined
      }
    },
    resetModify() {
      this.modify = {
        id: undefined,
        parentId: undefined,
        name: undefined,
        remark: undefined
      }
    },
    resetAddPermission() {
      this.addPermission = {
        id: undefined,
        permissionIds: undefined
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
          roleSave(this.temp).then(response => {
            this.temp.id = response.data.id
            this.temp.officeId = response.data.officeId
            this.temp.name = response.data.name
            this.temp.officeName = response.data.officeName
            this.temp.remark = response.data.remark
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
    handleAddPermission(id) {
      this.resetAddPermission()
      this.addPermission.id = id
      currentPermissions({ 'roleId': id }).then(response => {
        this.addPermission.permissionIds = response.data
        this.dialogAddPermissionFormVisible = true
      })
    },
    addPermissionData() {
      const tempData = Object.assign({}, this.addPermission)
      rolePermissionSave(tempData).then(response => {
        this.dialogAddPermissionFormVisible = false
        this.$notify({
          title: '成功',
          message: '修改成功',
          type: 'success',
          duration: 2000
        })
      })
    },
    updateData() {
      this.$refs['modifyForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.modify)
          roleSave(tempData).then(response => {
            for (const v of this.list) {
              if (v.id === response.data.id) {
                const index = this.list.indexOf(v)
                v.officeId = response.data.officeId
                v.name = response.data.name
                v.officeName = response.data.officeName
                v.remark = response.data.remark
                v.operationName = response.data.operationName
                v.createTime = response.data.createTime
                v.updateTime = response.data.updateTime
                v.status = response.data.status

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
      roleStatus({ 'id': id, 'status': status }).then(() => {
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

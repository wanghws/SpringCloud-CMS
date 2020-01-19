<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.loginName" :placeholder="$t('manage.loginName')" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.officeName" :placeholder="$t('manage.office')" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-date-picker
        v-model="listQuery.regStartTime"
        class="filter-item"
        type="datetime"
        clearable
        format="yyyy-MM-dd HH:mm"
        value-format="yyyy-MM-dd HH:mm"
        :placeholder="$t('manage.startTime')"
      />
      <el-date-picker
        v-model="listQuery.regEndTime"
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
      <el-button v-if="checkPermission(['ROLE_USER_CREATE'])" class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
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
      <el-table-column :label="$t('manage.loginName')" min-width="5%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.loginName }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('manage.office')" min-width="5%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.officeName }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('manage.mobile')" min-width="5%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.mobile }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('manage.email')" min-width="5%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.email }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('manage.loginIp')" min-width="5%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.loginIp }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('manage.loginTime')" min-width="8%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.loginTime }}</span>
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
          <el-button v-if="scope.row.status === 0" type="primary" size="small" @click="handleAddRole(scope.row.id)">
            {{ $t('table.addRole') }}
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
      <el-form ref="addForm" :rules="rules" :model="temp" label-position="left" label-width="70px" style="width: 400px; margin-left:50px;">
        <el-form-item :label="$t('manage.office')" prop="officeId">
          <treeselect v-model="temp.officeId" :show-count="true" :default-expand-level="2" :multiple="false" :options="officeTree" :placeholder="$t('manage.office')" />
        </el-form-item>
        <el-form-item :label="$t('manage.loginName')" prop="loginName">
          <el-input v-model="temp.loginName" />
        </el-form-item>
        <el-form-item :label="$t('login.password')" prop="password">
          <el-input v-model="temp.password" type="password" />
        </el-form-item>
        <el-form-item :label="$t('manage.mobile')" prop="mobile">
          <el-input v-model="temp.mobile" />
        </el-form-item>
        <el-form-item :label="$t('manage.email')" prop="email">
          <el-input v-model="temp.email" />
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
      <el-form ref="modifyForm" :rules="modifyRules" :model="modify" label-position="left" label-width="70px" style="width: 400px; margin-left:50px;">
        <el-form-item :label="$t('manage.office')" prop="officeId">
          <treeselect v-model="modify.officeId" :show-count="true" :default-expand-level="2" :multiple="false" :options="officeTree" :placeholder="$t('manage.office')" />
        </el-form-item>
        <el-form-item :label="$t('manage.mobile')" prop="mobile">
          <el-input v-model="modify.mobile" />
        </el-form-item>
        <el-form-item :label="$t('manage.email')" prop="email">
          <el-input v-model="modify.email" />
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

    <el-dialog :title="$t('table.addRole')" :visible.sync="dialogAddRoleFormVisible">
      <el-form ref="addPermissionForm" :rules="modifyRules" :model="addRole" label-position="left" label-width="85px" style="width: 400px; margin-left:50px;">
        <el-form-item :label="$t('manage.role')" prop="parentId">
          <treeselect v-model="addRole.roleIds" :show-count="true" :default-expand-level="2" :multiple="true" :options="roleTree" :placeholder="$t('manage.role')" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogAddRoleFormVisible = false">
          {{ $t('table.cancel') }}
        </el-button>
        <el-button type="primary" @click="addRoleData()">
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
  import {validEmail, validMobile, validUsername} from '@/utils/validate'
  import {userCreate, userCurrentRoles, userList, userRoleSave, userStatus, userUpdate} from '@/api/user'
  import {officeTreeList} from '@/api/office'
  import {roleTreeList} from '@/api/role'
  import waves from '@/directive/waves' // Waves directive
  import {parseTime} from '@/utils'
  import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
  import Treeselect from '@riophae/vue-treeselect'
  import '@riophae/vue-treeselect/dist/vue-treeselect.css'
  import checkPermission from '@/utils/permission'

  const statusOptions = [
  { key: 0, display_name: '正常' },
  { key: 1, display_name: '停用' }
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
    const validateUsername = (rule, value, callback) => {
      if (!value || !validUsername(value)) {
        callback(new Error('账号格式错误'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (!value || value.length < 6) {
        callback(new Error('密码格式错误'))
      } else {
        callback()
      }
    }
    const validateMobile = (rule, value, callback) => {
      if (!value || !validMobile(value)) {
        callback(new Error('手机格式错误'))
      } else {
        callback()
      }
    }
    const validateEmail = (rule, value, callback) => {
      if (!value || !validEmail(value)) {
        callback(new Error('邮箱格式错误'))
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
        loginName: undefined,
        officeName: undefined,
        regStartTime: undefined,
        regEndTime: undefined,
        status: undefined
      },
      statusOptions,
      showReviewer: false,
      temp: {
        id: undefined,
        officeId: undefined,
        loginName: undefined,
        officeName: undefined,
        password: undefined,
        mobile: undefined,
        email: undefined
      },
      modify: {
        id: undefined,
        officeId: undefined,
        officeName: undefined,
        mobile: undefined,
        email: undefined
      },
      addRole: {
        id: undefined,
        roleIds: []
      },
      officeTree: [],
      roleTree: [],
      dialogAddFormVisible: false,
      dialogModifyFormVisible: false,
      dialogAddRoleFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '修改用户',
        create: '添加用户'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {
        officeId: [{ required: true, trigger: 'blur', message: '请选择部门' }],
        loginName: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }],
        mobile: [{ required: true, trigger: 'blur', validator: validateMobile }],
        email: [{ required: true, trigger: 'blur', validator: validateEmail }]
      },
      modifyRules: {
        officeId: [{ required: true, trigger: 'blur', message: '请选择部门' }],
        mobile: [{ required: true, trigger: 'blur', validator: validateMobile }],
        email: [{ required: true, trigger: 'blur', validator: validateEmail }]
      },
      downloadLoading: false
    }
  },
  created() {
    this.getList()
    this.officeTreeList()
    this.roleTreeList()
  },
  methods: {
    checkPermission,
    getList() {
      this.listLoading = true
      userList(this.listQuery).then(response => {
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
    roleTreeList() {
      roleTreeList({}).then(response => {
        this.roleTree = response.data
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        officeId: undefined,
        officeName: undefined,
        loginName: undefined,
        password: undefined,
        mobile: undefined,
        email: undefined
      }
    },
    resetModify() {
      this.modify = {
        id: undefined,
        officeId: undefined,
        officeName: undefined,
        mobile: undefined,
        email: undefined
      }
    },
    resetAddRole() {
      this.addRole = {
        id: undefined,
        roleIds: []
      }
    },
    handleAddRole(id) {
      this.resetAddRole()
      this.addRole.id = id
      userCurrentRoles({ 'userId': id }).then(response => {
        this.addRole.roleIds = response.data
        this.dialogAddRoleFormVisible = true
      })
    },
    addRoleData() {
      const tempData = Object.assign({}, this.addRole)
      userRoleSave(tempData).then(response => {
        this.dialogAddRoleFormVisible = false
        this.$notify({
          title: '成功',
          message: '修改成功',
          type: 'success',
          duration: 2000
        })
      })
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
          userCreate(this.temp).then(response => {
            this.temp.id = response.data.id
            this.temp.officeName = response.data.officeName
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
          userUpdate(tempData).then(response => {
            for (const v of this.list) {
              if (v.id === response.data.id) {
                const index = this.list.indexOf(v)
                v.officeId = response.data.officeId
                v.email = response.data.email
                v.mobile = response.data.mobile
                v.officeName = response.data.officeName
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
      userStatus({ 'id': id, 'status': status }).then(() => {
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

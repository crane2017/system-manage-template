<template>
  <div v-loading="loading" element-loading-text="拼命加载中">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><i class="el-icon-menu"></i> 系统管理</el-breadcrumb-item>
        <el-breadcrumb-item>帐户管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="form-box contain">
      <el-form :inline="true" :model="filter" class="form-full-width form-top" label-width="60px">
        <el-form-item label="帐号">
          <el-input v-model="filter.admin_user_login" placeholder="帐号" size="small"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="filter.admin_user_name" placeholder="姓名" size="small"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="small" @click="query"><i class="el-icon-search"></i> 查询</el-button>
          <el-button type="primary" @click="handleAdd()" size="small"><i class="el-icon-plus"></i> 创建帐户
          </el-button>
        </el-form-item>
      </el-form>
    </div>
    <!-- table数据显示 -->
    <div>
      <el-table
        :data="tableData"
        border
        highlight-current-row
        @current-change="handleCurrentRowChange"
        fit
        style="width: 100%">
        <el-table-column label="操作" width="380" header-align="center"
                         align="center">
          <template slot-scope="scope">
            <el-button
              size="small"
              type="primary"
              @click="handleEdit(scope.row)"><i class="el-icon-edit"></i> 编辑
            </el-button>
            <el-button
              size="small"
              type="primary"
              @click="changePassword(scope.row)">修改密码
            </el-button>
            <el-button
              size="small"
              type="primary"
              @click="setRole(scope.row)">配置角色
            </el-button>
            <el-button
              size="small"
              type="danger"
              @click="deleteConfirm(scope.row)"><i class="el-icon-delete"></i> 删除
            </el-button>
          </template>
        </el-table-column>
        <el-table-column show-overflow-tooltip header-align="center"
                         align="center"
                         label="帐号" min-width="200px">
          <template slot-scope="scope">
            <span>{{ scope.row.admin_user_login }}</span>
          </template>
        </el-table-column>
        <el-table-column header-align="center"
                         align="center"
                         label="姓名" show-overflow-tooltip min-width="140px">
          <template slot-scope="scope">
            <span>{{ scope.row.admin_user_name }}</span>
          </template>
        </el-table-column>
        <el-table-column header-align="center"
                         align="center" show-overflow-tooltip
                         label="邮箱" min-width="150px">
          <template slot-scope="scope">
            <span>{{ scope.row.admin_user_email }}</span>
          </template>
        </el-table-column>
        <el-table-column header-align="center" show-overflow-tooltip
                         align="center"
                         label="有效期开始时间" min-width="200px">
          <template slot-scope="scope">
            <span>{{ scope.row.start_date | formatDate('yyyy-MM-dd hh:mm:ss') }}</span>
          </template>
        </el-table-column>
        <el-table-column header-align="center" show-overflow-tooltip
                         align="center"
                         label="有效期结束时间" min-width="200px">
          <template slot-scope="scope">
            <span>{{ scope.row.expire_date | formatDate('yyyy-MM-dd hh:mm:ss') }}</span>
          </template>
        </el-table-column>
        <el-table-column show-overflow-tooltip header-align="center"
                         align="center"
                         label="角色" min-width="200px">
          <template slot-scope="scope">
            <span>{{ scope.row.admin_role_pkid | formatRole(roles) }}</span>
          </template>
        </el-table-column>

      </el-table>
      <div class="pagination">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :page-sizes="constData.PAGE_SIZES"
          :page-size="constData.PAGE_SIZES[0]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="pagination.total">
        </el-pagination>
      </div>
    </div>
    <!-- 创建/编辑帐户模态框 -->
    <el-dialog title="编辑" :visible.sync="dialogTableVisible">
      <el-form :model="editForm" ref="editForm" label-width="140px" :rules="rules">
        <el-form-item label="帐号" prop="admin_user_login">
          <el-input v-model="editForm.admin_user_login" placeholder="请输入帐号" size="small"
                    :disabled="viewMode"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="admin_user_pwd" v-if="!editForm.admin_user_pkid">
          <el-input v-model="editForm.admin_user_pwd" placeholder="请输入密码" size="small"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="admin_user_name">
          <el-input v-model="editForm.admin_user_name" placeholder="请输入姓名" size="small"></el-input>
        </el-form-item>
        <el-form-item label="组织部门" prop="admin_dpt_pkid">
          <el-cascader
            :options="department"
            v-model="editForm.admin_dpt_pkid"
            size="small"
            change-on-select
            style="width:100%"
          ></el-cascader>
        </el-form-item>
        <el-form-item label="邮箱" prop="admin_user_email">
          <el-input v-model="editForm.admin_user_email" placeholder="请输入邮箱" size="small"></el-input>
        </el-form-item>
        <el-form-item label="有效期开始时间" prop="start_date">
          <el-date-picker style="width: 100%"
                          v-model="editForm.start_date"
                          size="small"
                          type="datetime"
                          placeholder="选择日期时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="有效期结束时间" prop="expire_date">
          <el-date-picker style="width: 100%"
                          v-model="editForm.expire_date"
                          size="small"
                          type="datetime"
                          placeholder="选择日期时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="reset('editForm')" size="small">取 消</el-button>
        <el-button type="primary" @click="submit('editForm')" size="small">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 角色配置模态框 -->
    <el-dialog title="角色配置" :visible.sync="dialogVisible">
      <el-form :model="roleForm" ref="roleForm" label-width="60px" :rules="roleRules">
        <el-form-item label="角色" prop="admin_role_pkid">
          <el-select v-model="roleForm.admin_role_pkid" filterable clearable placeholder="请选择" size="small"
                     style="width:100%">
            <el-option
              v-for="item in roles"
              :key="item.admin_role_pkid"
              :label="item.admin_role_name"
              :value="item.admin_role_pkid">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm('roleForm')" size="small">取 消</el-button>
        <el-button type="primary" @click="submitForm('roleForm')" size="small">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 修改密码模态框 -->
    <el-dialog title="编辑" :visible="editPasswordVisible">
      <el-form :model="editInfo" ref="editInfo" label-width="100px" :rules="editRules">
        <!--<el-form-item label="旧密码" prop="oldPassword">-->
        <!--<el-input type="password" v-model="editInfo.oldPassword" placeholder="请输旧密码"-->
        <!--size="small"></el-input>-->
        <!--</el-form-item>-->
        <el-form-item label="新密码" prop="newPassword">
          <el-input type="password" v-model="editInfo.newPassword" placeholder="请输新密码"
                    size="small"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input type="password" v-model="editInfo.confirmPassword" placeholder="请再次输入新密码"
                    size="small"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetPassword('editInfo')" size="small">取 消</el-button>
        <el-button type="primary" @click="submitPassword('editInfo')" size="small">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import * as accountApi from '../../../service/system-manage/account-api';
import {message} from '../../../script/message';
import * as authorityApi from '../../../service/system-manage/authority-api';
import * as departmentApi from '../../../service/system-manage/department-api';
export default {
  // 初始化数据
  data () {
    return {
      tableData: [],
      dialogTableVisible: false,
      editForm: {
        admin_user_warehouse_pkid: null,
        admin_user_pkid: null,
        admin_user_pwd: '',
        admin_dpt_pkid: [],
        admin_user_login: '',
        admin_user_name: '',
        admin_user_email: '',
        start_date: '',
        expire_date: '',
        uid: ''
      },
      department: [],
      pagination: {
        total: 0
      },
      filter: {
        admin_user_login: '',
        admin_user_name: '',
        pageNo: 1,
        pageSize: this.constData.PAGE_SIZES[0]
      },
      loading: false,
      roleFilter: {
        pageNo: 1,
        pageSize: 99999
      },
      roles: [],
      dialogVisible: false,
      roleForm: {
        admin_role_pkid: null,
        admin_user_pkid: null,
        admin_user_role_pkid: null
      },
      roleRules: {
        admin_role_pkid: [
          {required: true, message: '请选择角色', trigger: 'change', type: 'number'}
        ]
      },
      rules: {
        admin_user_login: [
          {required: true, message: '请输入帐号', trigger: 'blur'}
        ],
        admin_user_pwd: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ],
        admin_user_name: [
          {required: true, message: '请输入姓名', trigger: 'blur'}
        ],
        admin_dpt_pkid: [
          {type: 'array', required: true, message: '请选择组织部门', trigger: 'change'}
        ],
        admin_user_email: [
          {required: true, message: '请输入邮箱', trigger: 'blur'}
        ],
        start_date: [
          {required: true, message: '请选择日期时间', trigger: 'change', type: 'date'}
        ],
        expire_date: [
          {required: true, message: '请选择日期时间', trigger: 'change', type: 'date'}
        ]
      },
      viewMode: false,
      editPasswordVisible: false,
      editInfo: {},
      editRules: {
        oldPassword: [
          {required: true, message: '请输旧密码', trigger: 'blur'}
        ],
        newPassword: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ],
        confirmPassword: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ]
      },
      currentRow: {}
    };
  },
  mounted: function () {
    this.loadData();
    this.loadAllRole();
  },
  // 定义方法
  methods: {
    handleCurrentRowChange (val) {
      this.currentRow = val;
    },
    getDepartment () {
      const self = this;
      departmentApi.getDepartment()
        .then(function (response) {
          if (response.data.rows) {
            self.department = response.data.rows;
          }
        })
        .catch((error) => {
          console.log('error: ', error);
          self.$message.error(message['1001']);
        });
    },
    submitPassword (form) {
      const self = this;
      self.$refs[form].validate((valid) => {
        if (valid) {
          if (self.editInfo.newPassword === self.editInfo.confirmPassword) {
            accountApi.updateUserPassword2(self.editInfo)
              .then(response => {
                if (response.data.opResult > 0) {
                  self.editPasswordVisible = false;
                  self.$message({
                    type: 'success',
                    message: message['2011']
                  });
                } else {
                  self.$message({
                    type: 'warning',
                    message: message['2012']
                  });
                }
              })
              .catch((error) => {
                console.log('error: ', error);
                self.$message({
                  type: 'warning',
                  message: message['2013']
                });
              });
          } else {
            self.$message({
              type: 'warning',
              message: message['2014']
            });
          }
        } else {
          return false;
        }
      });
    },
    resetPassword (form) {
      const self = this;
      self.editInfo = {};
      self.$refs[form].resetFields();
      self.editPasswordVisible = false;
    },
    changePassword (row) {
      const self = this;
      self.editInfo = {};
      self.editInfo.adminUserLogin = row.admin_user_login;
      self.editPasswordVisible = true;
    },
    setRole (row) {
      const self = this;
      self.roleForm.admin_user_pkid = row.admin_user_pkid;
      self.roleForm.admin_role_pkid = row.admin_role_pkid;
      self.roleForm.admin_user_role_pkid = row.admin_user_role_pkid;
      self.dialogVisible = true;
    },
    loadAllRole () {
      const self = this;
      // 获取角色下拉选的数据
      authorityApi.getRoleList(self.roleFilter)
        .then(function (response) {
          if (response.data.rows) {
            self.roles = response.data.rows;
            self.loading = false;
          }
        })
        .catch(function (error) {
          console.log('error: ', error);
          self.$message.error(message['1001']);
        });
    },
    initData () {
      const self = this;
      self.editForm = {
        admin_user_warehouse_pkid: null,
        admin_user_pkid: null,
        admin_user_login: '',
        admin_dpt_pkid: [],
        admin_user_name: '',
        admin_user_email: '',
        admin_user_pwd: '',
        start_date: '',
        expire_date: '',
        uid: ''
      };
    },
    handleEdit (row) {
      const self = this;
      self.viewMode = true;
      self.editForm = {
        admin_user_pkid: row.admin_user_pkid,
        admin_user_pwd: row.admin_user_pwd,
        admin_user_login: row.admin_user_login,
        admin_user_name: row.admin_user_name,
        admin_user_dpt_pkid: row.admin_user_dpt_pkid,
        admin_user_email: row.admin_user_email,
        start_date: new Date(row.start_date),
        expire_date: new Date(row.expire_date),
        admin_dpt_pkid: []
      };
      if (row.children) {
        let data = row.children.reverse();
        let pkid = [];
        for (let i in data) {
          pkid.push(String(data[i].adminDptPkid));
        }
        self.editForm.admin_dpt_pkid = pkid;
      }

      self.dialogTableVisible = true;
    },
    handleAdd () {
      const self = this;
      self.viewMode = false;
      self.initData();
      self.dialogTableVisible = true;
    },
    handleDelete (index, row) {
    },
    deleteConfirm (row) {
      const self = this;
      self.$confirm(message['2004'], '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        self.loading = true;
        row.uid = self.constData.UID;
        accountApi.deleteUser(row)
          .then(function (response) {
            self.$message({
              type: 'success',
              message: message['2005']
            });
            self.loadData();
            self.loading = false;
          })
          .catch(function (error) {
            self.loading = false;
            console.log('error: ', error);
          });
      }).catch(() => {
        self.$message({
          type: 'primary',
          message: message['2006']
        });
      });
    },
    handleSizeChange (val) {
      const self = this;
      self.filter.pageSize = val;
      self.loadData();
    },
    handleCurrentChange (val) {
      const self = this;
      self.filter.pageNo = val;
      self.loadData();
    },
    insertAction (data) {
      const self = this;
      self.loading = true;
      accountApi.insertUser(data)
        .then(function (response) {
          if (response.data.opResult >= 0) {
            self.$message({
              type: 'success',
              message: message['2007']
            });
            self.dialogTableVisible = false;
            self.loadData();
          } else {
            if (response.data.opResult === -1) {
              self.$message({
                type: 'warning',
                message: message['2021']
              });
            } else {
              self.$message({
                type: 'warning',
                message: message['2008']
              });
            }
          }
          self.loading = false;
        })
        .catch(function (error) {
          self.loading = false;
          console.log('error: ', error);
          self.$message({
            type: 'warning',
            message: message['2008']
          });
        });
    },
    updateAction (data) {
      const self = this;
      self.loading = true;
      // 更新帐户
      accountApi.updateUser(data)
        .then(function (response) {
          if (response.data.opResult >= 0) {
            self.$message({
              type: 'success',
              message: message['2002']
            });
            self.dialogTableVisible = false;
            self.loadData();
          } else {
            if (response.data.opResult === -1) {
              self.$message({
                type: 'warning',
                message: message['2021']
              });
            } else {
              self.$message({
                type: 'warning',
                message: message['2003']
              });
            }
          }
          self.loading = false;
        })
        .catch(function (error) {
          self.loading = false;
          console.log('error: ', error);
          self.$message({
            type: 'warning',
            message: message['2003']
          });
        });
    },
    loadData () {
      const self = this;
      self.loading = true;
      self.getDepartment();
      // 获取table数据
      accountApi.getUser(self.filter)
        .then(function (response) {
          self.loading = false;
          if (response.data.rows) {
            self.tableData = response.data.rows;
            self.pagination.total = self.tableData.length;
          }
        })
        .catch(function (error) {
          self.loading = false;
          console.log('error: ', error);
          self.$message.error(message['1001']);
        });
    },
    query () {
      const self = this;
      self.loadData();
    },
    submit (form) {
      const self = this;
      self.$refs[form].validate((valid) => {
        if (valid) {
          let data = {};
          Object.assign(data, self.editForm);
          data.admin_dpt_pkid = self.editForm.admin_dpt_pkid[self.editForm.admin_dpt_pkid.length - 1];
          if (self.editForm.admin_user_pkid) {
            self.updateAction(data);
          } else {
            self.insertAction(data);
          }
        } else {
          return false;
        }
      });
    },
    reset (form) {
      const self = this;
      self.initData();
      self.$refs[form].resetFields();
      self.dialogTableVisible = false;
    },
    resetForm (roleForm) {
      const self = this;
      self.roleForm = {
        admin_role_pkid: null
      };
      self.$refs[roleForm].resetFields();
      self.dialogVisible = false;
    },
    submitForm (roleForm) {
      const self = this;
      self.$refs[roleForm].validate((valid) => {
        if (valid) {
          self.loading = true;
          self.dialogVisible = false;
          self.roleForm.uid = self.constData.UID;
          accountApi.setRole(self.roleForm)
            .then(function (response) {
              if (response.data.opResult >= 0) {
                self.$message({
                  type: 'success',
                  message: message['2002']
                });
                self.loadData();
              } else {
                self.$message({
                  type: 'warning',
                  message: message['2003']
                });
              }
              self.loading = false;
            })
            .catch(function (error) {
              self.loading = false;
              console.log('error: ', error);
              self.$message({
                type: 'warning',
                message: message['2003']
              });
            });
        } else {
          return false;
        }
      });
    }
  }
};
</script>

<style>
  .form-box, .form-content .el-select, .el-date-editor--daterange.el-input {
    width: 100%;
  }

  .el-table td, .el-table th {
    padding: 5px 10px;
  }

  .el-table .cell {
    padding: 0;
  }

  @media (min-width: 320px) and (max-width: 750px) {
    .el-dialog {
      width: 90% !important;
    }

    .el-message-box {
      width: 90% !important;
    }
  }

  .el-cascader--small .el-cascader__label {
    line-height: 34px;
  }
</style>

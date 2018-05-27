<template>
  <div v-loading="loading" element-loading-text="拼命加载中">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><i class="el-icon-menu"></i> 系统管理</el-breadcrumb-item>
        <el-breadcrumb-item>权限管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="form-box contain">
      <el-form :inline="true" :model="filter" class="form-full-width form-top">
        <el-form-item label="角色名">
          <el-input v-model="filter.admin_role_name" placeholder="角色名" size="small"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="small" @click="query"><i class="el-icon-search"></i> 查询</el-button>
          <el-button type="primary" @click="handleAdd()" size="small"><i class="el-icon-plus"></i> 创建角色</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!-- table数据显示 -->
    <div>
      <el-table
        :data="tableData"
        border
        style="width: 100%">
        <el-table-column label="操作" min-width="300" header-align="center"
                         align="center">
          <template slot-scope="scope">
            <el-button
              size="small"
              type="primary"
              @click="handleEdit(scope.row)"
            ><i class="el-icon-edit"></i> 编辑
            </el-button>
            <el-button
              size="small"
              type="primary"
              @click="setRoleMenu(scope.row)">配置资源
            </el-button>
            <el-button
              size="small"
              type="danger"
              @click="deleteConfirm(scope.row)"><i class="el-icon-delete"></i> 删除
            </el-button>
          </template>
        </el-table-column>
        <el-table-column header-align="center"
                         align="center"
                         show-overflow-tooltip
                         label="角色名" width="140">
          <template slot-scope="scope">
            <span>{{ scope.row.admin_role_name }}</span>
          </template>
        </el-table-column>
        <el-table-column header-align="center"
                         align="center"
                         show-overflow-tooltip
                         label="英文名" width="140">
          <template slot-scope="scope">
            <span>{{ scope.row.admin_role_key }}</span>
          </template>
        </el-table-column>
        <el-table-column header-align="center"
                         align="center"
                         show-overflow-tooltip
                         label="创建时间" min-width="200">
          <template slot-scope="scope">
            <span>{{ new Date(scope.row.cdt) | formatDate('yyyy-MM-dd hh:mm:ss') }}</span>
          </template>
        </el-table-column>
        <el-table-column header-align="center"
                         align="center"
                         show-overflow-tooltip
                         label="更新时间" min-width="200">
          <template slot-scope="scope">
            <span>{{ new Date(scope.row.udt) | formatDate('yyyy-MM-dd hh:mm:ss') }}</span>
          </template>
        </el-table-column>

      </el-table>
      <div class="pagination">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :page-sizes="constData.PAGE_SIZES"
          :page-size="constData.PAGE_SIZES[0]"
          layout="total, sizes, prev, pager, next"
          :total="pagination.total">
        </el-pagination>
      </div>
    </div>
    <!-- 配置资源 模态框 -->
    <el-dialog title="编辑" :visible.sync="dialogTableVisible">
      <el-row type="flex">
        <el-col>
          <div>
            <el-tree
              :data="treeData"
              show-checkbox
              ref="myTree"
              default-expand-all
              node-key="menuPkid"
              :default-checked-keys="checkedData"
              :props="defaultProps">
            </el-tree>
          </div>
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm()" size="small">取 消</el-button>
        <el-button type="primary" @click="submitForm()" size="small">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 新增/编辑 模态框 -->
    <el-dialog title="编辑" :visible.sync="dialogVisible">
      <el-form :model="editForm" ref="editForm" label-width="110px" :rules="rules">
        <el-form-item label="角色名" prop="admin_role_name">
          <el-input v-model="editForm.admin_role_name" placeholder="请输入角色名" size="small"></el-input>
        </el-form-item>
        <el-form-item label="角色英文名" prop="admin_role_key">
          <el-input v-model="editForm.admin_role_key" placeholder="请输入角色英文名" size="small"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="reset('editForm')" size="small">取 消</el-button>
        <el-button type="primary" @click="submit('editForm')" size="small">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import * as authorityApi from '../../../service/system-manage/role-api';
import { message } from '../../../script/message';
import * as commonApi from '../../../service/common-api';
export default {
  // 初始化数据
  data () {
    return {
      pagination: {
        total: 0
      },
      tableData: [],
      pageSizes: [],
      dialogTableVisible: false,
      formLabelAlign: {
        name: '',
        status: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        role: ''
      },
      filter: {
        admin_role_name: '',
        pageNo: 1,
        pageSize: this.constData.PAGE_SIZES[0]
      },
      treeData: [],
      defaultProps: {
        children: 'children',
        label: 'title'
      },
      loading: false,
      checkedData: [],
      clickTimes: true,
      dialogVisible: false,
      editForm: {
        admin_role_pkid: null,
        admin_role_key: '',
        admin_role_name: '',
        uid: ''
      },
      rules: {
        admin_role_key: [
          { required: true, message: '请输入角色名', trigger: 'blur' }
        ],
        admin_role_name: [
          { required: true, message: '请输入角色英文名', trigger: 'blur' }
        ]
      },
      menuAuthPkid: null,
      oldMenuList: [],
      newMenuList: []
    };
  },
  mounted: function () {
    this.loadData();
  },
  // 定义方法
  methods: {
    initData () {
      const self = this;
      self.editForm = {
        admin_role_pkid: null,
        admin_role_key: '',
        admin_role_name: '',
        uid: ''
      };
    },
    handleAdd () {
      const self = this;
      self.initData();
      self.dialogVisible = true;
    },
    query () {
      const self = this;
      self.loadData();
    },
    resetForm () {
      const self = this;
      self.$refs.myTree.setCheckedKeys([]);
      self.dialogTableVisible = false;
    },
    setRoleMenu (row) {
      const self = this;
      if (self.clickTimes) {
        self.checkedData = row.roleMenu ? row.roleMenu.split(',') : [];
        self.clickTimes = false;
      } else {
        self.$refs.myTree.setCheckedKeys([]);
        self.checkedData = row.roleMenu ? row.roleMenu.split(',') : [];
        self.$refs.myTree.setCheckedKeys(self.checkedData);
      }
      self.menuAuthPkid = row.admin_role_pkid;
      self.oldMenuList = row.roleMenu ? row.roleMenu.split(',') : [];
      self.dialogTableVisible = true;
    },
    handleDelete (index, row) {},
    deleteConfirm (row) {
      const self = this;
      self
        .$confirm(message['2004'], '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        .then(() => {
          self.loading = true;
          row.uid = self.constData.UID;
          authorityApi
            .deleteRole(row)
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
        })
        .catch(() => {
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
    loadData () {
      const self = this;
      self.loading = true;
      // 获取菜单列表
      // authorityApi
      //   .getMenu()
      //   .then(function (response) {
      //     if (response.data.rows) {
      //       self.treeData = response.data.rows;
      //     }
      //   })
      //   .catch(function (error) {
      //     console.log('error:', error);
      //   });

      commonApi
        .getUserMenu()
        .then(function (response) {
          if (response.data.rows) {
            self.treeData = response.data.rows;
          }
        })
        .catch(function (error) {
          console.log('error: ', error);
        });

      // 获取table数据
      authorityApi
        .getRoleList(self.filter)
        .then(function (response) {
          if (response.data.rows) {
            self.tableData = response.data.rows;
            self.pagination.total = response.data.count;
            self.loading = false;
          }
        })
        .catch(function (error) {
          console.log('error: ', error);
        });
    },
    insertAction (data) {
      const self = this;
      self.loading = true;
      authorityApi
        .insertRole(data)
        .then(function (response) {
          if (response.data.opResult >= 0) {
            self.$message({
              type: 'success',
              message: message['2007']
            });
            self.loadData();
          } else {
            self.$message({
              type: 'warning',
              message: message['2008']
            });
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
      // 更新角色
      authorityApi
        .updateRole(data)
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
    },
    submit (form) {
      const self = this;
      self.$refs[form].validate(valid => {
        if (valid) {
          self.editForm.uid = self.constData.UID;
          if (self.editForm.admin_role_pkid) {
            self.updateAction(self.editForm);
          } else {
            self.insertAction(self.editForm);
          }
          self.dialogVisible = false;
        } else {
          return false;
        }
      });
    },
    reset (form) {
      const self = this;
      self.initData();
      self.$refs[form].resetFields();
      self.dialogVisible = false;
    },
    handleEdit (row) {
      const self = this;
      self.editForm = {
        admin_role_pkid: row.admin_role_pkid,
        admin_role_key: row.admin_role_key,
        admin_role_name: row.admin_role_name
      };
      self.dialogVisible = true;
    },
    submitForm () {
      const self = this;
      self.newMenuList = self.$refs.myTree.getCheckedKeys();

      let data = {
        admin_role_pkid: self.menuAuthPkid,
        oldMenuList: self.oldMenuList,
        newMenuList: self.newMenuList,
        uid: self.constData.UID
      };
      self.loading = true;
      self.dialogTableVisible = false;

      authorityApi
        .setRole(data)
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
    }
  }
};
</script>

<style>
.form-box,
.form-content .el-select,
.el-date-editor--daterange.el-input {
  width: 100%;
}

.el-table td,
.el-table th {
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
</style>

<template>
  <div v-loading="loading" element-loading-text="拼命加载中">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><i class="el-icon-menu"></i> 系统管理</el-breadcrumb-item>
        <el-breadcrumb-item>菜单管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="form-box contain">
      <imp-panel>
        <h3 class="box-title" slot="header" style="width: 100%;">
          <el-button type="success" icon="el-icon-plus" @click="newAdd()" size="small">新增</el-button>
          <el-button type="danger" icon="el-icon-delete" @click="batchDelete()" size="small">删除</el-button>
        </h3>
        <el-row slot="body" class="m-width">
          <el-col :span="6">
            <el-tree v-if="treeData"
                     ref="treeData"
                     :data="treeData"
                     show-checkbox
                     highlight-current
                     :default-expanded-keys="defaultExpandedKeys"
                     @node-click="handleNodeClick"
                     @node-expand="nodeExpand"
                     @node-collapse="nodeCollapse"
                     clearable node-key="menuPkid"
                     :props="defaultProps"></el-tree>
          </el-col>
          <el-col :span="18">
            <el-card class="box-card" style="margin-left: 15px">
              <div class="text item">
                <el-form :model="form" ref="form">
                  <el-form-item label="父级" :label-width="formLabelWidth">
                    <el-select v-model="form.parent_pkid" placeholder="请选择父级" filterable clearable>
                      <el-option
                        v-for="item in parents"
                        :key="item.menu_pkid"
                        :label="item.title"
                        :value="item.menu_pkid">
                      </el-option>
                    </el-select>
                  </el-form-item>
                  <el-form-item label="名称" :label-width="formLabelWidth">
                    <el-input v-model="form.title" auto-complete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="类型" :label-width="formLabelWidth">
                    <template>
                      <el-radio class="radio" v-model="form.type" label="0">菜单</el-radio>
                      <el-radio class="radio" v-model="form.type" label="1">按钮</el-radio>
                    </template>
                  </el-form-item>
                  <el-form-item label="链接" :label-width="formLabelWidth">
                    <el-input v-model="form.url" auto-complete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="图标" :label-width="formLabelWidth">
                    <el-input v-model="form.menu_class" auto-complete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="排序" :label-width="formLabelWidth">
                    <el-input v-model="form.menu_order"></el-input>
                  </el-form-item>
                  <el-form-item label="" :label-width="formLabelWidth">
                    <el-button type="primary" @click="onSubmit(form)" size="small"
                               v-text="form.menu_pkid?'保存':'保存'">
                    </el-button>
                  </el-form-item>
                </el-form>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </imp-panel>
    </div>

  </div>
</template>

<script>
import * as authorityApi from '../../../service/system-manage/role-api';
import {message} from '../../../script/message';
import panel from '../../common/panel.vue';

export default {
  // 初始化数据
  data () {
    return {
      parents: [],
      tableData: [],
      formLabelWidth: '100px',
      treeData: [],
      defaultProps: {
        children: 'children',
        label: 'title',
        id: 'menu_pkid'
      },
      loading: false,
      checkedData: [],
      rules: {},
      form: {
        menu_pkid: null,
        title: '',
        type: '0',
        url: '',
        menu_order: 0,
        menu_class: '',
        parent_pkid: null
      },
      defaultExpandedKeys: []
    };
  },
  components: {
    'imp-panel': panel
  },
  mounted: function () {
    this.loadData();
    this.loadParent();
  },
  // 定义方法
  methods: {
    // 加载商品种类下拉选项及商品列表
    loadParent () {
      const self = this;
      self.loading = true;
      authorityApi.getMenuList()
        .then(function (response) {
          self.parents = response.data.rows;
          self.loading = false;
        })
        .catch(function (error) {
          console.log(error);
          self.loading = false;
        });
    },
    handleNodeClick (data) {
      // console.log(data)
      this.form = {};
      this.form = {
        menu_pkid: data.menuPkid,
        title: data.title ? data.title : '',
        type: data.type ? data.type : '0',
        url: data.url ? data.url : '',
        menu_order: data.menuOrder ? data.menuOrder : 0,
        menu_class: data.menuClass ? data.menuClass : '',
        parent_pkid: data.parentPkid ? data.parentPkid : null
      };
    },
    onSubmit (form) {
      const self = this;
      if (self.form.menu_pkid) {
        self.updateMenu(self.form);
      } else {
        self.insertMenu(self.form);
      }
    },
    insertMenu (data) {
      const self = this;
      self.loading = true;
      var entryDetail = {};
      entryDetail.menuOrder = self.form.menu_order;
      entryDetail.menuClass = self.form.menu_class;
      entryDetail.title = self.form.title;
      entryDetail.type = self.form.type;
      entryDetail.url = self.form.url;
      entryDetail.parentPkid = self.form.parent_pkid;
      // 插入菜单明细数据
      authorityApi.insertMenu(entryDetail)
        .then(function (response) {
          self.loading = false;
          self.$message({
            type: 'success',
            message: message['2007']
          });

          self.loadData();
        })
        .catch(function (error) {
          console.log(error);
          self.loading = false;
          self.$message({
            type: 'warning',
            message: message['2008']
          });
        });
    },
    updateMenu (data) {
      const self = this;
      self.loading = true;
      var updateDetail = {};
      updateDetail.menuPkid = self.form.menu_pkid;
      updateDetail.menuOrder = self.form.menu_order;
      updateDetail.menuClass = self.form.menu_class;
      updateDetail.type = self.form.type;
      updateDetail.title = self.form.title;
      updateDetail.url = self.form.url;
      updateDetail.parentPkid = self.form.parent_pkid;
      // 插入菜单明细数据
      authorityApi.updateMenu(updateDetail)
        .then(function (response) {
          self.loading = false;
          self.$message({
            type: 'success',
            message: message['2002']
          });

          self.loadData();
        })
        .catch(function (error) {
          console.log(error);
          self.loading = false;
          self.$message({
            type: 'warning',
            message: message['2003']
          });
        });
    },
    newAdd () {
      this.form.parent_pkid = this.form.menu_pkid || null;
      this.form.menu_pkid = null;
      this.form.title = '';
      this.form.url = '';
      this.form.menu_order = 0;
      this.form.menu_class = '';
    },
    batchDelete () {
      const self = this;
      let checkKeys = [];
      checkKeys = this.$refs.treeData.getCheckedKeys();
      if (checkKeys == null || checkKeys.length <= 0) {
        this.$message.warning('请选择要删除的资源');
        return;
      }
      this.$confirm('确定删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        authorityApi.deleteList(checkKeys)
          .then(function (response) {
            self.form = {};
            self.loadData();
          });
        this.$message({
          type: 'success',
          message: '删除成功!'
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    loadData () {
      const self = this;
      self.loading = true;
      self.loadParent();
      // 获取菜单列表
      authorityApi.getMenu()
        .then(function (response) {
          if (response.data.rows) {
            self.treeData = response.data.rows;
            self.loading = false;
          }
        })
        .catch(function (error) {
          console.log('error: ', error);
          self.$message.error(message['1001']);
        });
    },
    nodeExpand (obj, node, cop) {
      this.defaultExpandedKeys.push(node.data.menuPkid);
    },
    nodeCollapse (obj, node, cop) {
      this.defaultExpandedKeys.forEach((row, index) => {
        if (row === node.data.menuPkid) {
          this.defaultExpandedKeys.splice(index, 1);
        }
      });
    }
  }
};
</script>

<style scoped>
  .form-box, .form-content .el-select, .el-date-editor--daterange.el-input {
    width: 100%;
  }

  /*@media (min-width: 320px) and (max-width: 750px) {*/
    /*.el-dialog {*/
      /*width: 90% !important;*/
    /*}*/

    /*.el-message-box {*/
      /*width: 90% !important;*/
    /*}*/

    /*.box {*/
      /*width: 500px !important;*/
    /*}*/
  /*}*/

  .select-tree .icons-wrapper {
    display: block;
  }

  .select-tree .is-empty i {
    width: 30px;
    height: 30px;
    line-height: 30px;
    text-align: center;
    display: inline-block;
    cursor: pointer;
  }

  .select-tree .is-empty i:hover {
    background-color: #0d6aad;
    color: #ffffff;
  }

</style>

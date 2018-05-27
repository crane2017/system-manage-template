<template>
  <div class="sidebar">
    <el-menu :default-active="onRoutes" class="el-menu-vertical-demo" unique-opened router @open="handleOpen"
             @close="handleClose">
      <template v-for="item in items">
        <template v-if="item.subs">
          <el-submenu :index="item.index" :key="item.index">
            <template slot="title">
              <i :class="item.icon"></i>
              <span slot="title">{{ item.title }}</span>
            </template>
            <el-menu-item v-for="(subItem,i) in item.subs" :key="i" :index="subItem.index">
              <span slot="title">{{ subItem.title }}</span>
            </el-menu-item>
          </el-submenu>
        </template>
        <template v-else>
          <el-menu-item :index="item.index" :key="item.index">
            <i :class="item.icon"></i>
            <span slot="title">{{ item.title }}</span>
          </el-menu-item>
        </template>
      </template>
    </el-menu>
    </div>
</template>

<script>
export default {
  data () {
    return {
      isCollapse: false,
      items: [
        {
          icon: 'el-icon-setting',
          index: 'sys',
          title: '系统管理',
          subs: [
            {
              index: 'account',
              title: '帐户管理'
            },
            {
              index: 'role',
              title: '角色管理'
            },
            {
              index: 'menu',
              title: '菜单管理'
            }
          ]
        }
      ]
    };
  },
  computed: {
    onRoutes () {
      return this.$route.path.replace('/', '');
    }
  },
  methods: {
    handleOpen (key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose (key, keyPath) {
      console.log(key, keyPath);
    }
  }
};
</script>

<style scoped>
  .sidebar {
    display: block;
    position: absolute;
    width: 250px;
    left: 0;
    top: 70px;
    bottom: 0;
    /*background: #2E363F;*/
  }

  .sidebar > ul {
    height: 100%;
  }

</style>

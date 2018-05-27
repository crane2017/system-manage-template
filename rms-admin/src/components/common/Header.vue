<template>
    <div class="header">
      <div class="logo">后台管理系统</div>
      <div class="tools" @click.prevent="isCollapse">
        <i class="el-icon-menu"></i>
      </div>
        <div class="user-info">
          <!--<el-dropdown trigger="hover">-->
                    <!--<span class="el-dropdown-link userinfo-inner"><img-->
                      <!--:src="sysUserAvatar"/> {{sysUserName}}</span>-->
            <!--<el-dropdown-menu slot="dropdown">-->
              <!--<el-dropdown-item @click.native="changePw">修改密码</el-dropdown-item>-->
              <!--<el-dropdown-item>设置</el-dropdown-item>-->
              <!--<el-dropdown-item divided @click.native="logout">退出登录</el-dropdown-item>-->
            <!--</el-dropdown-menu>-->
          <!--</el-dropdown>-->
            <el-dropdown trigger="click" @command="handleCommand">
                <span class="el-dropdown-link">
                    <img class="user-logo" src="../../../static/img/img.jpg">
                    {{username}}
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="changePw">修改密码</el-dropdown-item>
                  <el-dropdown-item>设置</el-dropdown-item>
                    <el-dropdown-item command="loginout">退出</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </div>
</template>
<script>
export default {
  data () {
    return {
      name: ''
    };
  },
  computed: {
    username () {
      let username = sessionStorage.getItem('ms_username');
      return username || this.name;
    }
  },
  methods: {
    isCollapse () {},
    handleCommand (command) {
      if (command === 'loginout') {
        sessionStorage.removeItem('ms_username');
        this.$router.push('/login');
      }
    }
  }
};
</script>
<style scoped>
    .header {
        position: relative;
        box-sizing: border-box;
        width: 100%;
        height: 70px;
        font-size: 22px;
        line-height: 70px;
        color: #fff;
        background-color: #20a0ff;
    }
    .header .logo{
        float: left;
        width:250px;
        text-align: center;
    }
    .header .tools{
      width: 300px;
      display: inline-block;
    }
    .user-info {
        float: right;
        padding-right: 50px;
        font-size: 16px;
        color: #fff;
    }
    .user-info .el-dropdown-link{
        position: relative;
        display: inline-block;
        padding-left: 50px;
        color: #fff;
        cursor: pointer;
        vertical-align: middle;
    }
    .user-info .user-logo{
        position: absolute;
        left:0;
        top:15px;
        width:40px;
        height:40px;
        border-radius: 50%;
    }
</style>

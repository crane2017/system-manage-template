<template>
    <div class="login-wrap">
        <div class="ms-title">商城管理系统</div>
        <div class="ms-login">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="0px" class="demo-ruleForm">
                <el-form-item prop="adminUserLogin">
                    <el-input v-model="ruleForm.adminUserLogin" placeholder="login"></el-input>
                </el-form-item>
                <el-form-item prop="adminUserPwd">
                    <el-input type="password" placeholder="password" v-model="ruleForm.adminUserPwd" @keyup.enter.native="submitForm('ruleForm')"></el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click="submitForm('ruleForm')" :disabled="loading">登录<i class="el-icon-loading" v-show="loading"></i></el-button>
                </div>
                <p style="font-size:12px;line-height:30px;color:#999;">Tips : 用户名和密码随便填。</p>
            </el-form>
        </div>
    </div>
</template>

<script>
import { login } from '../../service/login-api';
import { message } from '../../script/message';
export default {
  data: function () {
    return {
      ruleForm: {
        adminUserLogin: '',
        adminUserPwd: ''
      },
      rules: {
        adminUserLogin: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        adminUserPwd: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      },
      loading: false
    };
  },
  created: function () {},
  methods: {
    submitForm (formName) {
      const self = this;
      self.$refs[formName].validate(valid => {
        if (valid) {
          login(self.ruleForm)
            .then(function (response) {
              console.log(response);
              // 用户名存储到sessionStorage中
              sessionStorage.setItem('ms_username', response.data.row.adminUserName);
              sessionStorage.setItem('token', response.data.row.Authorization);
              sessionStorage.setItem('adminUserLogin', response.data.row.adminUserLogin);
              // 画面跳转
              self.$router.push('/welcome');
            })
            .catch(function (error) {
              self.loading = false;
              console.log(error);
              self.$message.error(message['2001']);
            });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    }
  }
};
</script>

<style scoped>
.login-wrap {
  position: relative;
  width: 100%;
  height: 100%;
  background: #324157;
}

.ms-title {
  position: absolute;
  top: 50%;
  width: 100%;
  margin-top: -220px;
  text-align: center;
  font-size: 30px;
  color: #fff;
}

.ms-login {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 300px;
  height: 160px;
  margin: -150px 0 0 -190px;
  padding: 40px;
  border-radius: 5px;
  background: #fff;
}

.login-btn {
  text-align: center;
}

.login-btn button {
  width: 100%;
  height: 36px;
}
</style>

<template>
  <div id="login" v-title data-title="登录账号">
    <div class="me-login-box">
      
    <div class="login-from">
      
      <h1 style="text-align: left; font-size: 35px;">登录<small style="font-size: 13px;color: rgb(99, 99, 99);padding-left: 10px;">ADK-Blog</small></h1>
      <hr>
        <el-form ref="userForm" :model="userForm" :rules="rules" style="margin-top: 10px;">
          <el-form-item prop="account">
            <el-input placeholder="用户名" v-model="userForm.account"></el-input>
          </el-form-item>

          <el-form-item prop="password">
            <el-input placeholder="密码" type="password" v-model="userForm.password"></el-input>
          </el-form-item>
          
          <small style="color: gray;">没有账户？点击此处<a @click="register()" target="_blank">注册</a></small>
          <el-form-item size="small" class="me-login-button">
            <el-button type="primary" @click.native.prevent="login('userForm')">登录</el-button>
          </el-form-item>
        </el-form>
        <img src="../assets/img/ADKBLog-logo.png" style="width: 100%; height: auto; right: 0%;">
    </div>
    </div>

    <div class="background-main">
      <div class="summary-me">
        <h1 style="font-size: 36px; color: rgb(255, 255, 255); text-shadow:5px 5px 5px rgb(31, 31, 31);">欢迎来到我的网站</h1>
        <p style="font-style:inherit; color: rgb(204, 204, 204);text-shadow: 3px 3px 3px rgb(59, 59, 59);">这里是ADK-Blog 基于<span style="color: lawngreen;">vue2.x</span>和<span style="color: rgb(60, 143, 221);" >springboot</span>的前后端分离网站</p>
      </div>
      <div class="about-infomation">
          <p>插画来自<a href="https://www.pixiv.net/artworks/80722956" target="_blank">pixiv</a>  如若侵权请联系790532173@qq.com</p>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'Login',
    data() {
      return {
        userForm: {
          account: '',
          password: ''
        },
        rules: {
          account: [
            {required: true, message: '请输入用户名', trigger: 'blur'},
            {max: 10, message: '不能大于10个字符', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'},
            {max: 10, message: '不能大于10个字符', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      login(formName) {
        let that = this

        this.$refs[formName].validate((valid) => {
          if (valid) {

            that.$store.dispatch('login', that.userForm).then(() => {
                that.$router.go(-1)
            }).catch((error) => {
              if (error !== 'error') {
                that.$message({message: error, type: 'error', showClose: true});
              }
            })
          } else {
            return false;
          }
        });
      },
      register(){
        this.$router.push({path: `/register`})
      }
    }
  }
</script>

<style scoped>
  #login {
    min-width: 100%;
    min-height: 100%;
  }
  .login-from{
    margin-top: 50%;
  }
  .about-infomation{
    position: relative;
    font-size: 10px;
    color: blanchedalmond;
    text-shadow: 2px 2px 2px rgb(46, 46, 46);
    padding-left: 10px;
    top: 95%;
    z-index: 0;
  }

  .background-main{
    width: 100%;
    height: 100%;
    position: absolute;
    z-index:  -1;
    background-image: url(../assets/img/80722956_p0.png) ;
    background-position:left;
    background-size: 1920px 1080px;
  
  }

  .summary-me{
    position: absolute;
    z-index: 1;
    padding-left: 10px;
    top: 40%;
  }

  .me-login-box {
    position: absolute;
    width: 300px;
    height: 100%;
    background-color: white;
    right: 0%;
    border-radius: 0%;
    padding: 30px;
  }

  .me-login-box-radius {
    box-shadow: 0px 0px 1px 1px rgba(161, 159, 159, 0.1);
  }

  .me-login-box h1 {
    text-align: center;
    font-size: 24px;
    margin-bottom: 20px;
    vertical-align: middle;
  }

  .me-login-design {
    text-align: center;
    font-family: 'Open Sans', sans-serif;
    font-size: 18px;
  }

  .me-login-design-color {
    color: #5FB878 !important;
  }

  .me-login-button {
    border-radius: 25px;
    margin-top: 10px;
    text-align: center;
  }

  .me-login-button button {
    width: 100%;
  }

</style>

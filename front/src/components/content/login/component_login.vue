<template>
  <div>
    <login-layout :btn="btn" :head="head" @btnClick="btnClick">
      <div slot="input">
        <el-form :model="ruleForm2" status-icon :rules="rules2" ref="ruleForm2" class="demo-ruleForm">
          <el-form-item prop="name">
            <el-input v-model="ruleForm2.name" placeholder="用户名" prefix-icon="el-icon-user-solid"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input type="password" v-model="ruleForm2.password" auto-complete="off" placeholder="密码" prefix-icon="el-icon-lock"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="info" class="info">
        <el-checkbox v-model="checked" >记住我</el-checkbox>
      </span>
      <span slot="format_login" class="format_login" style="font-size: 14px">
        <a href="/verify">忘记密码</a> |
        <el-link :underline="false" @click="goRegister">去注册</el-link>
      </span>
    </login-layout>
  </div>
</template>

<script>
import LoginLayout from "./LoginLayout";
import {baseURL} from "../../../utils/constant";
import {mapMutations} from 'vuex'

export default {
  name: 'component_login',
  data(){
    return {
      Role:this.$route.params.role?this.$route.params.role:'',
      ruleForm2: {
        password: '',
        name: ''
      },
      rules2: {
        name: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        password: [
          {required: true, message: '密码不能为空', trigger: 'blur'}
        ],
      },
      btn:"登录",
      head:"登录",
      input: '',
      checked: true,
    }
  },
  components:{
    LoginLayout
  },
  methods:{
    ...mapMutations(['changeLogin']),
    btnClick(){
      const fromData = this.ruleForm2;
      this.submitForm('ruleForm2',fromData)
    },
    submitForm(formName,data) {
      let _this = this;
      this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$axios.post(baseURL + '/toLogin', data).then(res => {
              if (res.data.Authorization == null) {
                this.$message.error(res.data)
                _this.resetForm('ruleForm2')
              } else {
                this.$cookies.set('Authorization', res.data.Authorization,60*60)
                // _this.userToken = res.data.token;
                // // 将用户token保存到vuex中
                // _this.changeLogin({ Authorization:_this.userToken });
                //判断角色
                setTimeout(()=>{
                  if (res.data.role === 1) {
                    _this.go('/apply');
                  } else if([2,3,4,5].indexOf(res.data.role)>=0){
                    _this.go('/background');
                  }else {
                    _this.go('/home');
                  }
                },800)
                this.$message.success('登陆成功')
              }
            })
          }
      });
    },
    go(link){
      let routeUrl = this.$router.resolve({
        path:link,
      })
      window.open(routeUrl.href,'_self')
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    goRegister(){
      if(this.Role!==''){
        this.$router.push({name:'Register',params:{role:this.Role}})
      }else {
        this.$router.push({path:'/register'})
      }
    }
  }
};
</script>

<style scoped>
  .format_login a:hover{
    color: #409eff;
  }

</style>

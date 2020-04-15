<template>
  <div>
    <login-layout :btn="btn" :head="head" @btnClick="btnClick">
      <div slot="input">
        <el-form :model="ruleForm2" status-icon :rules="rules2" ref="ruleForm2" class="demo-ruleForm">
          <el-form-item prop="pass">
            <el-input type="password" v-model="ruleForm2.pass" auto-complete="off" placeholder="新密码" prefix-icon="el-icon-lock"></el-input>
          </el-form-item>
          <el-form-item prop="checkPass">
            <el-input type="password" v-model="ruleForm2.checkPass" auto-complete="off" placeholder="再输一次" prefix-icon="el-icon-unlock"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="info"> </span>
      <span slot="format_login" class="format_login" style="font-size: 14px">
        <el-link :underline="false" @click="back_login">返回登录</el-link>
      </span>
    </login-layout>
  </div>
</template>

<script>
  import LoginLayout from "./LoginLayout";
  import {baseURL} from "../../../utils/constant";
  export default {
    name: "Reset",
    data(){
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.ruleForm2.checkPass !== '') {
            this.$refs.ruleForm2.validateField('checkPass');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.ruleForm2.pass) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return{
        btn:"提交",
        head:"修改密码",
        ruleForm2: {
          pass: '',
          checkPass: '',
        },
        rules2: {
          pass: [
            {validator: validatePass, trigger: 'blur'}
          ],
          checkPass: [
            {validator: validatePass2, trigger: 'blur'}
          ],
        }
      }
    },
    components:{
      LoginLayout
    },
    methods:{
      btnClick(){
        this.submitForm('ruleForm2')
      },
      submitForm(formName) {
        let _this = this;
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$axios.get(baseURL+'/amend_pwd',{params:{pass:_this.ruleForm2.pass,checkPass:_this.ruleForm2.checkPass}}).then(res=>{
              this.$message.success(res.data.ok);
            })
          } else {
            this.$message.error('网络错误')
          }
        });
      },
      back_login(){
        this.$store.commit('changeMailState','')
        this.$router.push('/login')
      }
    }
  }
</script>

<style scoped>
  .format_login a:hover{
    color: #409eff;
  }
</style>

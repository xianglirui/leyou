<template>
  <div>
    <login-layout :btn="btn" :head="head" @btnClick="btnClick" :load="load">
      <div slot="input">
        <el-form :model="ruleForm2" status-icon :rules="rules2" ref="ruleForm2" class="demo-ruleForm">
          <el-form-item prop="email">
            <el-input v-model="ruleForm2.email" placeholder="请输入已注册的邮箱" prefix-icon="el-icon-message"></el-input>
          </el-form-item>
          <el-form-item>
            <div style="display: flex">
              <el-input style="margin-right: 10px" v-model="ruleForm2.code" placeholder="验证码" :disabled="code"></el-input>
              <el-button type="primary" plain @click="send_code">发送验证码</el-button>
            </div>
          </el-form-item>
        </el-form>
      </div>
    </login-layout>
  </div>
</template>

<script>
  import LoginLayout from "./LoginLayout";
  import {baseURL} from "../../../utils/constant";
  export default {
    name: "component_verify",
    components:{
      LoginLayout
    },
    data(){
      return{
        btn:"下一步",
        head:"邮箱验证",
        load:false,
        code:true,
        ver_code: '',
        ruleForm2: {
          email: '',
          code:''
        },
        rules2: {
          email: [
            {required: true, message: '请输入邮箱', trigger: 'blur'}
          ],
        },
      }
    },
    methods:{
      btnClick(){
        this.submitForm('ruleForm2',this.ruleForm2.code,this.ver_code)
      },
      send_code(){
        let _this = this;
        this.$axios.get(baseURL+'/send_code',{params:{param:_this.ruleForm2.email}}).then(res=>{
          if(res.data.err!=null){
            this.$message.error(res.data.err)
          }else {
            _this.code = false;
            _this.ver_code = res.data.code;
            this.$store.commit('changeMailState','123')
          }
        })
      },
      submitForm(formName,code,ver_code) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if(ver_code!=null && code===ver_code){
              //奇了怪了，if-else里写反了才正确
              this.$message.error('验证码错误')
            }else{
              this.$message.success('验证成功');
              this.$router.replace('/verify/reset')
            }
          }
        });
      }
    }
  }
</script>

<style scoped>

</style>

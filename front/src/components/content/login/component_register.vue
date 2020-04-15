<template>
  <div>
    <login-layout :btn="btn" :head="head" @btnClick="btnClick" :load="load">
      <div slot="input">
        <el-form :model="ruleForm2" status-icon :rules="rules2" ref="ruleForm2" class="demo-ruleForm">
          <el-form-item prop="name">
            <el-input v-model="ruleForm2.name" placeholder="用户名" prefix-icon="el-icon-user-solid"></el-input>
          </el-form-item>
          <el-form-item prop="pass">
            <el-input type="password" v-model="ruleForm2.password" auto-complete="off" placeholder="密码" prefix-icon="el-icon-lock"></el-input>
          </el-form-item>
          <el-form-item prop="checkPass">
            <el-input type="password" v-model="ruleForm2.checkPass" auto-complete="off" placeholder="确认密码" prefix-icon="el-icon-unlock"></el-input>
          </el-form-item>
          <el-form-item prop="email">
            <el-input v-model="ruleForm2.email" placeholder="邮箱" prefix-icon="el-icon-message"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <span slot="info">
        <el-checkbox v-model="checked">我同意网站协议</el-checkbox>
      </span>
      <span slot="format_login" class="format_login"><el-link :underline="false" @click="goLogin" style="font-size: 14px">返回登录</el-link></span>
    </login-layout>
  </div>
</template>

<script>
  import LoginLayout from "./LoginLayout";
  import {baseURL} from "../../../utils/constant";

  export default {
    name: "component_register",
    created() {

    },
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
        } else if (value !== this.ruleForm2.password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      var checkEmail = (rule, value, callback) => {
        const mailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
        if (!value) {
          return callback(new Error('邮箱不能为空'))
        }
        setTimeout(() => {
          if (mailReg.test(value)) {
            callback()
          } else {
            callback(new Error('请输入正确的邮箱格式'))
          }
        }, 100)
      };
      return {
        ruleForm2: {
          password: '',
          checkPass: '',
          name:'',
          email: '',
          role:this.$route.params.role?this.$route.params.role:'',
        },
        rules2: {
          password: [
            {validator: validatePass, trigger: 'blur'}
          ],
          checkPass: [
            {validator: validatePass2, trigger: 'blur'}
          ],
          name: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
          ],
          email:[
            {validator: checkEmail,trigger: 'blur'}
          ]
        },
        btn: "注册",
        head: this.$route.params.role?'商家注册':'用户注册',
        input: '',
        checked: true,
        load:false
      }
    },
    components:{
      LoginLayout
    },
    methods:{
      btnClick(){
        const fromData = this.ruleForm2;
        const email = this.ruleForm2.email;
        this.load = true;
        this.$message.warning("已向您的邮箱发送了一条验证信息，请查收");
        let _this = this;
        //向邮箱发送信息，后台先处理邮箱是否重复然后处理发送邮件任务，如果说后台没抛异常，则说明该邮箱正确，成功向邮箱发送信息，前端直接提交给处理插入数据库的请求函数：submitForm
        this.$axios.get(baseURL+'/sendMail',{params:{param:email}}).then(res=>{
          if(res.data.err!=null){
            console.log(res);
            this.$message.error(res.data.err);
            _this.ruleForm2.email='';
            _this.load = false
          }else {
            _this.submitForm('ruleForm2',fromData)
            _this.load = false
          }
        })
        // this.$axios.interceptors.response.use(res=>{},error => {Message({
        //   message: error.response.data.message,
        //   type: 'error',
        //   duration: 5 * 1000
        // })})
        // this.$axios({
        //   url:baseURL+'/sendMail',
        //   params:{param:email},
        //   method:'get',
        //   timeout:
        // })
      },
      submitForm(formName,data) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$axios.post(baseURL+'/toRegister',data).then(res=>{
              this.$message.success(res.data)
            }).catch(err=>{
              this.$message.error("网络出错了")
            })
          }
        });
      },
      goLogin(){
        if(this.Role!==''){
          this.$router.push({name:'Login',params:{role:this.ruleForm2.role}})
        }else {
          this.$router.push({path:'/login'})
        }
      }
    }
  }
</script>

<style scoped>
  .format_login a:hover{
    color: #409eff;
  }
</style>

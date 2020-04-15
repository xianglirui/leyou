<template>
  <div class="row">
    <el-card shadow="never">
      <el-row>
        <el-col :span="21">账户信息</el-col>
        <el-col :span="3"><el-link :underline="false" type="primary" @click="$router.push({path:'/area',query:{okid:$store.getters.userInfo.name}})"><i class="el-icon-caret-left" />&nbsp;返回主页</el-link></el-col>
      </el-row>
      <div style="margin-bottom: 40px"></div>
      <el-collapse accordion>
        <el-collapse-item>
          <template slot="title">
            <i class="el-icon-user"></i>&nbsp;&nbsp;<span style="width: 300px">用户名：&nbsp;&nbsp;{{usernamePro}}</span>
            <span style="margin-left: 58%;">点击修改</span>
          </template>
          <div style="position: relative;left: 30%">
            <el-input style="width: 300px" placeholder="新用户名，3-10字符" prefix-icon="el-icon-user" v-model="username"></el-input>
            <el-button type="primary" @click="edit_name">修改</el-button>
          </div>
        </el-collapse-item>


        <el-collapse-item>
          <template slot="title">
            <i class="el-icon-message"></i>&nbsp;&nbsp;<span style="width: 300px">邮箱：&nbsp;&nbsp;{{this.$store.getters.userInfo.email}}</span>
            <span style="margin-left: 58%;">点击修改</span>
          </template>
          <el-form :model="ruleForm2" status-icon :rules="rules2" ref="ruleForm2" class="demo-ruleForm" style="position: relative;left: 30%;width: 35%;">
            <el-form-item prop="email">
              <el-input v-model="ruleForm2.email" placeholder="请输入新的邮箱" prefix-icon="el-icon-message"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="send_code">确认</el-button>
              <el-button type="info" plain @click="ruleForm2.email=''">取消</el-button>
            </el-form-item>
          </el-form>
        </el-collapse-item>


        <el-collapse-item>
          <template slot="title">
            <i class="el-icon-lock"></i>&nbsp;&nbsp;<span style="width: 300px">密码：&nbsp;&nbsp;*********</span>
            <span style="margin-left: 58%;">点击修改</span>
          </template>
          <el-form label-position="right" label-width="100px" :model="formLabelAlign" :rules="rules" style="position: relative;left: 21%" ref="formLabelAlign">
            <el-form-item label="新密码：" prop="pass">
              <el-input v-model="formLabelAlign.pass" style="width: 300px" placeholder="请输入新密码，区分大小写" prefix-icon="el-icon-lock" type="password"></el-input>
            </el-form-item>
            <el-form-item label="确认密码：" prop="checkPass">
              <el-input v-model="formLabelAlign.checkPass" style="width: 300px" placeholder="请再次输入新密码" prefix-icon="el-icon-lock" type="password"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="edit_pwd">确认</el-button>
              <el-button type="info" plain @click="formLabelAlign={}">取消</el-button>
            </el-form-item>
          </el-form>
        </el-collapse-item>


        <el-collapse-item>
          <template slot="title">
            <i class="el-icon-s-custom"></i>&nbsp;&nbsp;<span style="width: 300px">负责人信息</span>
            <span style="margin-left: 58%;">点击修改</span>
          </template>
          <el-alert :title="'此处不支持修改，您可以联系销售经理 '+xiaoshou" type="warning" show-icon close-text="知道了" style="width: 99%"></el-alert>
          <div style="position: relative;left: 35%;top: 15px">
            <div>负责人姓名：<span v-show="!loading">{{principal}}</span></div>
            <div>负责人联系方式：<span v-show="!loading">{{principal_tel}}</span></div>
          </div>
        </el-collapse-item>
      </el-collapse>
    </el-card>
  </div>
</template>

<script>
  import {xiaoshou} from "../../../../assets/js/administrator";
  import {baseURL} from "../../../../utils/constant";

  export default {
    name: "Account",
    data() {
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.formLabelAlign.checkPass !== '') {
            this.$refs.formLabelAlign.validateField('checkPass');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.formLabelAlign.pass) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
        username: '',
        pwd: '',
        email: '',
        xiaoshou: xiaoshou,
        formLabelAlign: {
          pass: '',
          checkPass: ''
        },
        rules: {
          pass: [
            {validator: validatePass, trigger: 'blur'}
          ],
          checkPass: [
            {validator: validatePass2, trigger: 'blur'}
          ],
        },
          btn: "下一步",
          head: "邮箱验证",
          load: true,
          code: true,
          ver_code: true,
          ruleForm2: {
            email: '',
            code: ''
          },
          rules2: {
            email: [
              {required: true, message: '请输入邮箱', trigger: 'blur'}
            ],
          },
        }

    },
    computed:{
      usernamePro(){
        return this.$store.getters.userInfo.name
      },
      loading(){
        if(this.$store.state.area != null) {
          this.load = false
        }
      },
      areas(){
        return this.$store.state.area
      },
      principal(){
        return this.areas==null?'xxx':this.areas.principal
      },
      principal_tel(){
        return this.areas==null?'xxx':this.areas.principal_tel
      }
    },
    methods:{
      send_code(){
        let _this=this
        if(this.ruleForm2.email!==''){
          this.$axios.get(baseURL+'/sendMail',{params:{param:this.$store.getters.userInfo.email}}).then(res=>{
            if(res.data.err){
              this.$message.error(res.data.err)
            }else {
              this.$message.info('邮箱修改成功')
              setTimeout(()=>{location.reload()},500)
            }
          })
        }
      },
      edit_pwd() {
        this.$refs['formLabelAlign'].validate((valid) => {
          if (valid) {
            this.$axios.get(baseURL+'/update_pwd',{params:{password:this.formLabelAlign.pass,id:this.$store.getters.userInfo.id}}).then(res=>{
              if(res.data==='update_pwd_ok'){
                this.$message.info('密码修改成功')
              }
            })
          }
        })
      },
      edit_name(){
        if(this.username!==''){
          if(this.username.length<10&&this.username.length>=3){
            let _this=this
            this.$axios.get(baseURL+'/update_name',{params:{name:this.username,id:this.$store.getters.userInfo.id}}).then(res=>{
              _this.username = res.data
              this.$message.info('用户名修改成功')
              setTimeout(()=>{location.reload()},500)
            })
          }else {
            this.username=''
          }
        }
      }
    }
  }
</script>

<style scoped>
  .row{
    margin-top: 10px;

  }
</style>

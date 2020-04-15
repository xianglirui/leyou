<template>
  <div>
    <el-card shadow="never">
      <div style="margin-bottom: 70px;"><el-tag>账户设置</el-tag></div>

      <el-row>
        <el-col :span="5">
            <div class="info"><span>头像&emsp;<el-divider direction="vertical"/></span></div>
        </el-col>
        <el-col :span="15" style="position: relative;bottom: 15px"><el-avatar :size="50" :src="circleUrl"></el-avatar></el-col>
        <el-col :span="4">
          <el-upload action="https://jsonplaceholder.typicode.com/posts/">
            <el-button round>修改</el-button>
          </el-upload>
        </el-col>
      </el-row>
      <el-divider/>
      <el-row>
        <el-col :span="5">
          <div class="info"><span>昵称&emsp;<el-divider direction="vertical"/></span></div>
        </el-col>
        <el-col :span="15" style="font-size: 12px;position: relative;top: 5px">{{name}}</el-col>
        <el-col :span="4">
          <el-button round @click="edit(1)">修改</el-button>
        </el-col>
      </el-row><el-divider/>
      <el-row>
        <el-col :span="5">
          <div class="info"><span>绑定邮箱&emsp;<el-divider direction="vertical"/></span></div>
        </el-col>
        <el-col :span="15" style="font-size: 12px;position: relative;top: 5px">{{mail}}</el-col>
        <el-col :span="4">
          <el-button round @click="edit(2)">换绑</el-button>
        </el-col>
      </el-row><el-divider/>
      <el-row>
        <el-col :span="5">
          <div class="info"><span>密码&emsp;<el-divider direction="vertical"/></span></div>
        </el-col>
        <el-col :span="15" style="font-size: 12px;position: relative;top: 5px">{{pwd}}</el-col>
        <el-col :span="4">
          <el-button round @click="edit(3)">修改</el-button>
        </el-col>
      </el-row>
    </el-card>

    <el-dialog :title="title" :visible.sync="dialogVisible" width="30%">
      <div style="position: relative;top: -30px">
        <el-row v-if="tag===1">
          <el-col :span="1">&nbsp;</el-col>
          <el-col :span="19" style="position: relative;right: 10px;">
            <el-input placeholder="新用户名，3-10字符" prefix-icon="el-icon-user" v-model="username"></el-input>
          </el-col>
          <el-col :span="4"><el-button type="primary" @click="edit_name">确定</el-button></el-col>
        </el-row>

        <el-row v-if="tag===2">
          <el-form :model="ruleForm2" status-icon :rules="rules2" ref="ruleForm2" class="demo-ruleForm">
            <el-form-item prop="email">
              <el-input v-model="ruleForm2.email" placeholder="请输入新的邮箱" prefix-icon="el-icon-message"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="send_code">确认</el-button>
              <el-button type="info" plain @click="ruleForm2.email=''">取消</el-button>
            </el-form-item>
          </el-form>
        </el-row>

        <el-row v-if="tag===3">
          <el-form label-position="right" label-width="100px" :model="formLabelAlign" :rules="rules" ref="formLabelAlign">
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
        </el-row>
      </div>

    </el-dialog>
  </div>
</template>

<script>
  import {baseURL} from "../../../utils/constant";

  export default {
    name: "Account",
    data(){
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
      return{
        tag:1,
        username:'',
        name:this.$store.getters.userInfo.name,
        mail:this.$store.getters.userInfo.email,
        pwd:'******',
        circleUrl: require('assets/img/other/头像 男孩.svg'),
        dialogVisible: false,
        title:'',
        flag:'',
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
    methods:{
      edit(index){
        this.dialogVisible = true
        switch (index) {
          case 1:
            this.title = '修改昵称';
            this.tag=1
            break;
          case 2:
            this.title = '换绑邮箱';
            this.tag=2
            break;
          case 3:
            this.title = '修改密码';
            this.tag=3
            break;
        }
      },
      send_code(){
        let _this=this
        if(this.ruleForm2.email!==''){
          this.$axios.get(baseURL+'/sendMail',{params:{param:this.$store.getters.userInfo.email}}).then(res=>{
            if(res.data.err){
              this.$message.error(res.data.err)
            }else {
              this.$axios.get(baseURL+'/update_email',{params:{email:this.ruleForm2.email,id:this.$store.getters.userInfo.id}}).then(res=>{
                if(res.data==='update_email_ok'){
                  this.$message.info('邮箱修改成功')
                  setTimeout(()=>{location.reload()},500)
                }
              })
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
  .info{
    width: 150px;
    /*border: 1px solid red;*/
  }
  .info span{
    float: right;
    font-size: 18px;
  }
  .el-row{
    margin-top: 50px;
  }
</style>

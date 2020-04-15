<template>
  <div class="row">
    <el-tag effect="dark" type="info" class="compl">我的信息</el-tag>
    <el-card shadow="never">
      <el-form ref="form" :model="form" label-width="90px">
        <el-row>
          <el-col :span="22">
            <el-form-item label="职工姓名：">
              {{form.name}}
            </el-form-item>
          </el-col>
          <el-col :span="2">
            <div style="font-size: 12px;position: relative;top: 10px">
              <el-link :underline="false" type="primary" @click="logout">退出登录<i class="el-icon-caret-right"/></el-link>
            </div>
          </el-col>
        </el-row>
        <el-form-item label="权限等级：">
          <icon-svg :icon-class="grade"/>
          {{grade_info}}
        </el-form-item>
        <el-form-item label="员工账户：">
          <el-row>
            <el-col :span="20">
              <el-row>
                <el-col :span="3">
                  {{form.account}}
                </el-col>
                <el-col :span="5" style="position: relative;right: 10px;" v-show="edit_name_flag">
                  <el-input placeholder="输入3-10个字符" size="small" prefix-icon="el-icon-user" v-model="username"></el-input>
                </el-col>
                <el-col :span="4" v-show="edit_name_flag">
                  <el-button plain  type="primary" size="mini" @click="editName">修改</el-button>
                </el-col>
              </el-row>
            </el-col>
            <el-col :span="4"><el-link :underline="false" @click="edit_name_flag=true"><i class="el-icon-edit" />&nbsp;修改账户名</el-link></el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="账户密码：">
          <el-row>
            <el-col :span="20">
              <el-row>
                <el-col :span="3">********</el-col>
                <el-col :span="5" style="position: relative;right: 10px;" v-show="edit_pwd_flag"><el-input v-model="pwd" prefix-icon="el-icon-lock" placeholder="输入新密码" size="small"></el-input></el-col>
                <el-col :span="4" v-show="edit_pwd_flag"><el-button plain  type="primary" size="mini" @click="editPwd">修改</el-button></el-col>
              </el-row>
            </el-col>
            <el-col :span="4"><el-link :underline="false" @click="edit_pwd_flag=true"><i class="el-icon-edit" />&nbsp;修改密码</el-link></el-col>
          </el-row>
        </el-form-item>
<!--        <el-form-item label="最近操作：">-->
<!--          <i class="el-icon-info" style="color: #a8714a"/><span style="color: #8a8a8a">&nbsp;只保留三天内的操作信息</span>-->
<!--          <el-container style="height: 30vh;width: 100vw">-->
<!--            <el-scrollbar>-->
<!--            <el-main>-->
<!--              <el-table :data="tableData" style="width: 100%">-->
<!--                <el-table-column label="时间" width="180">-->
<!--                  <template slot-scope="scope">-->
<!--                    <i class="el-icon-time"></i>-->
<!--                    <span style="margin-left: 10px">{{ scope.row.date }}</span>-->
<!--                  </template>-->
<!--                </el-table-column>-->
<!--                <el-table-column label="任务类型" width="180">-->
<!--                  <el-tag effect="dark"  :type="type">{{txt}}</el-tag>-->
<!--                </el-table-column>-->
<!--                <el-table-column label="操作" width="180">-->

<!--                </el-table-column>-->
<!--                <el-table-column label="结果">-->

<!--                </el-table-column>-->
<!--              </el-table>-->
<!--            </el-main>-->
<!--            </el-scrollbar>-->
<!--          </el-container>-->
<!--        </el-form-item>-->
      </el-form>
    </el-card>
  </div>
</template>

<script>
  import {baseURL} from "../../../utils/constant";

  export default {
    name: "MyYL",
    data(){
      return{
        edit_name_flag:false,
        edit_pwd_flag:false,
        times:'1314',
        value2:true,
        username:'',
        pwd:'',
        form:{
          name:this.$store.getters.userInfo.perms,
          account:this.$store.getters.userInfo.name,
          pwd:'',
          flag:this.$store.getters.userInfo.role,
        },
        tableData: []
      }
    },
    computed:{
      txt(){
        if(this.tableData.type===1){
          this.times = 'danger'
          return '游记投诉'
        }
        if(this.tableData.type===2){this.times = 'success'
          return '账号违规'
        }
        if(this.tableData.type===3){this.times = 'warning'
          return '景区投诉'
        }
        if(this.tableData.type===4){this.times = 'info'
          return '未到账申诉'
        }
      },
      type(){
        if(this.tableData.type===1){
          this.times = ''
          return 'danger'
        }
        if(this.tableData.type===2){this.times = ''
          return 'success'
        }
        if(this.tableData.type===3){this.times = ''
          return 'warning'
        }
        if(this.tableData.type===4){this.times = ''
          return 'info'
        }
      },
      grade_info(){
        if(this.form.flag===2){
          return '春芽'
        }
        if(this.form.flag===3){
          return '夏果'
        }
        if(this.form.flag===4){
          return '秋枫'
        }
        if(this.form.flag===5){
          return '暴雪'
        }
      },
      grade(){
        if(this.form.flag===2){
          return 'shuye'
        }
        if(this.form.flag===3){
          return 'watermelon'
        }
        if(this.form.flag===4){
          return 'fengye'
        }
        if(this.form.flag===5){
          return 'xuehua1'
        }
      }
    },
    methods: {
      logout(){
        //因为无法销毁服务端的token，所以采取在前端删除token，然后刷新页面
        this.$cookies.remove('Authorization')
        location.reload()
      },
      editName(){
        if(this.form.account!=='') {
          if (this.username.length < 10&&this.username.length>=3) {
            let _this = this
            this.$axios.get(baseURL + '/update_name', {
              params: {
                name: this.username,
                id: this.$store.getters.userInfo.id
              }
            }).then(res => {
              _this.username = res.data
              this.$message.info('用户名修改成功')
              setTimeout(() => {
                location.reload()
              }, 500)
            })
          } else {
            this.username = ''
          }
        }
      },
      editPwd(){
        if(this.pwd!==''){
          this.$axios.get(baseURL+'/update_pwd',{params:{password:this.pwd,id:this.$store.getters.userInfo.id}}).then(res=>{
            if(res.data==='update_pwd_ok'){
              this.$message.info('密码修改成功')
            }
          })
        }
      }
    }
  }
</script>

<style scoped>
  .compl{
    width: 80px;
    text-align: center;
    margin-bottom: 10px;
  }
  .row{
    width: 85%;
    margin: 0 auto;
  }
</style>

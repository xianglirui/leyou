<template>
  <div class="row">
    <el-row>
      <el-col :span="3">
        <el-radio v-model="radio" label="1" border>员工信息处理</el-radio>
      </el-col>
      <el-col :span="14">
        <el-radio v-model="radio" label="2" border>员工操作信息</el-radio>
      </el-col>
      <el-col :span="4"><el-input v-model="search" size="mini" placeholder="输入员工姓名搜索"></el-input></el-col>
      <el-col :span="1" style="margin-left: 0.5%"><el-button size="mini" @click="handleSearch()">搜索</el-button></el-col>
      <el-col :span="1" style="margin-left: 3%"><el-button size="mini" type="danger" @click="dialogFormVisible = true">新增</el-button></el-col>
      <el-col :span="24">
        <el-card shadow="never" style="margin-top: 10px">
          <el-table :data="tableData" stripe style="width: 100%" height="65vh" highlight-current-row @current-change="handleCurrentChange" ref="singleTable">
            <el-table-column width="10"></el-table-column>
            <el-table-column type="index" label="ID" width="120"></el-table-column>
            <el-table-column prop="perms" label="姓名" width="180"></el-table-column>
            <el-table-column prop="name" label="用户名" width="200"></el-table-column>
            <el-table-column prop="email" label="邮箱" width="300"></el-table-column>
            <el-table-column label="角色" width="200">
              <template slot-scope="scope">
                <icon-svg :icon-class=" role(scope.row.role).icon"/>
                <span style="margin-left: 7px">{{ role(scope.row.role).name }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button type="primary" size="mini" @click="editUsers(scope.$index,scope.row)">修改</el-button>
                <el-button type="info" size="mini" @click="handleDelete(scope.$index,scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
    <el-dialog title="新增员工信息" :visible.sync="dialogFormVisible">
      <el-form :model="form" style="width: 60%;position: relative;left: 15%">
        <el-form-item label="账户名" label-width="120px">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="员工姓名" label-width="120px">
          <el-input v-model="form.perms" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="初始密码" label-width="120px">
          <el-input v-model="form.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="员工等级" label-width="120px">
          <el-input v-model="form.role" autocomplete="off" placeholder="等级:2-3-4"></el-input>
        </el-form-item>
        <el-form-item label="注册邮箱" label-width="120px">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addAdmin">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="修改员工信息" :visible.sync="dialogVisible">
      <el-form :model="editUser" style="width: 60%;position: relative;left: 15%">
        <el-form-item label="账户名" label-width="120px">
          <el-input v-model="editUser.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="员工姓名" label-width="120px">
          <el-input v-model="editUser.perms" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="员工等级" label-width="120px">
          <el-input v-model="editUser.role" autocomplete="off" placeholder="等级:2-3-4"></el-input>
        </el-form-item>
        <el-form-item label="注册邮箱" label-width="120px">
          <el-input v-model="editUser.email" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editAdmin">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {baseURL} from "../../utils/constant";

  export default {
    name: "boss",
    data() {
      return {
        radio: '1',
        icon:'',
        tableData: [],
        editUser:{},
        search:'',
        currentRow: null,
        dialogVisible: false,
        dialogFormVisible: false,
        form: {
          name: '',
          perms: '',
          password: '',
          email: '',
          role: ''
        },
      }
    },
    created() {
      this.$axios.get(baseURL+'/getUserList').then(res=>{
        this.tableData = res.data
      })
    },
    methods:{
      role(item){
        if(item===2){
          return {icon:'shuye',name:'春芽'}
        }
        if(item===3){
          return {icon:'watermelon',name:'夏果'}
        }
        if(item===4){
          return {icon:'fengye',name:'秋枫'}
        }
        if(item===5){
          return {icon:'xuehua1',name:'暴雪'}
        }
      },
      handleDelete(index,item){
        console.log(item.id)
        this.$axios.get(baseURL+'/deleteAdmin',{params:{id:item.id}}).then(res=>{
          if(res.data==='ok'){
            this.tableData.splice(index,1)
          }
        })
      },
      handleSearch(){
        const i = this.tableData.filter(v=>{
          return this.search===v.perms
        })
        this.$refs.singleTable.setCurrentRow(i[0]);
      },
      handleCurrentChange(val) {
        this.currentRow = val;
      },
      addAdmin(){
        this.$axios.post(baseURL+'/addAdmin',this.form).then(res=>{
          if(res.data==='ok'){
            this.$message.success('添加成功')
            this.tableData.push(this.form)
            this.dialogFormVisible = false
          }
        })
      },
      editUsers(index,item){
        this.editUser = item
        this.dialogVisible = true
      },
      editAdmin(){
        this.$axios.post(baseURL+'/editAdmin',this.editUser).then(res=>{
          if(res.data==='ok'){
            this.$message.success('修改成功')
            this.dialogVisible = false
          }
        })
      }
    }
  }
</script>

<style scoped>
  .row{
    width: 85%;
    margin: 0 auto;
  }
</style>

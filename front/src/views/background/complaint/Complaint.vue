<template>
  <div class="row">
    <el-tag effect="dark" type="info" class="compl">投诉处理</el-tag>
    <el-card shadow="never">
      <el-table :data="tableData" stripe  height="65vh" style="width:100%">
        <el-table-column width="70" type="index"></el-table-column>
        <el-table-column label="完成程度" width="150">
          <template slot-scope="scope">
            <i class="el-icon-finished" :style="grade(scope.row.state)"/>
            <span>{{finish}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="starttime" label="发起日期" width="210"></el-table-column>
        <el-table-column prop="types" label="投诉类别" width="220">
          <template slot-scope="scope">
            <el-tag v-for="item in types(scope.row.types,scope.row.additional)" :key="item" style="margin-right: 10px">{{item}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="问题描述" width="320px">
          <template slot-scope="scope">
            <el-link v-if="scope.row.types===1" :href="'/travel?id='+scope.row.tid">{{scope.row.content}}</el-link>
            <span v-else>{{scope.row.content}}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="270">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="toUser(scope.row.types,scope.row)" v-show="scope.row.types===2||scope.row.types===3||scope.row.types===1">请求用户</el-button>
            <el-button type="text" size="small" v-if="scope.row.types===1" @click="D_travel(scope.row)">查封游记</el-button>
            <el-button type="text" size="small" v-if="scope.row.types===4" @click="$message.success('已封号')">查封账号</el-button>
            <el-button type="text" size="small" @click="shop(scope.row)" v-show="scope.row.types===2||scope.row.types===3">请求商家</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-dialog
        title="反馈客户"
        :visible.sync="dialogVisible"
        width="30%">
        <el-input type="textarea" :autosize="{ minRows: 3, maxRows: 4}" resize="none" class="send" :placeholder="text"></el-input>
        <span slot="footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="send">确 定</el-button>
          </span>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
  import {baseURL} from "../../../utils/constant";

  export default {
    name: "Complaint",
    computed:{

    },
    data(){
      return {
        dialogVisible:false,
        text:'',
        flag:2,
        finish:'',
        tableData: [],
        id:'',
        oid:''
      }
    },
    methods:{
      shop(item){
        this.$axios.get(baseURL+'/updateAdminId',{params:{adid:this.$store.getters.userInfo.id,id:item.id}}).then(res=>{
          if(res.data==='ok'){
            this.$axios.post(baseURL+'/toAreaMsg',item).then(res=>{
              if(res.data==='ok'){
                this.$message.success('已提醒商家')
              }else {
                this.$message.warning('暂不支持')
              }
            })
          }
        })
      },
      D_travel(item){
        this.$confirm('点击前必须确定该游记内容违规, 是否已违规?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$axios.get(baseURL+'/updateAdminId',{params:{adid:this.$store.getters.userInfo.id,id:item.id}}).then(res=>{
            if(res.data==='ok'){
              this.$axios.get(baseURL+'/updateArticleShow',{params:{id:item.id,tid:item.tid}}).then(res=>{
                if(res.data==='ok'){
                  this.$message.success('删除成功')
                }
              })
            }
          })
        })
      },

      toUser(item,feed){
        if(feed.state===2){

        }else {
          if(item===1){
            this.text = '该游记系涉嫌违规，感谢您的提醒'
          }
          if(item===3){
            this.text = '我们已经提醒商家，商家已改正，非常抱歉对您产生影响'
          }
          if(item===2){
            this.text='非常抱歉，由于我们的错误给您造成了困扰，现在您的付款已到账，请查收，祝您生活愉快！'
          }
          this.dialogVisible=true
          this.id=feed.id
          this.oid = feed.oid
        }
      },
      send(){
        this.dialogVisible = false
        this.$axios.get(baseURL+'/updateAdminId',{params:{adid:this.$store.getters.userInfo.id,id:this.id}}).then(res=> {
          if (res.data === 'ok') {
            this.$axios.get(baseURL + '/updateFeedBack', {params: {id: this.id, feedback: this.text,oid:this.oid}}).then(res => {
              if (res.data === 'ok') {
                this.$message.success('已反馈给用户')
              }
            })
          }
        })
      },
      grade(item){
        if(item===0){
          this.finish='未开始'
          return 'color:#409eff'
        }
        if(item===1){
          this.finish='完成中'
          return 'color:#ff794f'
        }
        if(item===2){
          this.finish='已完成'
          return 'color:#26d921'
        }
      },
      types(item,add){
        let info=[]
        if(item===1){
          info[0]= '游记内容不良'
        }
        if(item===2){
          info[0]= '退款未到账'
        }
        if(item===3){
          info[0]= '景区纠错'
          info[1] = add
        }
        if(item===4){
          info[0]= '账号违规'
        }
        return info
      }
    },
    created() {
      this.$axios.get(baseURL+'/select_complaint_list').then(res=>{
        for(let i of res.data){
          this.tableData.push(i)
        }
        // console.log(res.data);
      })
      console.log(this.tableData)
    }
  }
</script>

<style scoped>
  .row{
    width: 85%;
    margin: 0 auto;
  }
  .compl{
    width: 80px;
    text-align: center;
    margin-bottom: 10px;
  }
  .page{
    position: relative;
    left: 29%;
    margin-top: 30px;
    margin-bottom: 15px;
  }
  .el-scrollbar{
    height: 100%; /*此处一定要设置高度，不然内层的计算属性不生效*/
    color: #ff794f;
  }
  .send{
    resize: none;
    width: 100%;
    height: 80px;
    padding: 10px;
    border-color: #c5c5c5;
    line-height: 150%;
  }
  textarea::-webkit-input-placeholder{
    color: #c5c5c5;
  }
  textarea::-moz-placeholder{   /* Mozilla Firefox 19+ */
    color:#c5c5c5;
  }
  textarea:-moz-placeholder{    /* Mozilla Firefox 4 to 18 */
    color:#c5c5c5;
  }
  textarea:-ms-input-placeholder{  /* Internet Explorer 10-11 */
    color:#c5c5c5;
  }
</style>

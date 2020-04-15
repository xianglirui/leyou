<template>
  <div class="row">
    <el-tag type="info" effect="dark">商家注册请求</el-tag>
    <el-card shadow="never" style="margin-top: 10px">
      <el-container style="height: 68vh;">
        <el-main>
          <el-collapse v-model="activeName" accordion>
            <el-collapse-item :title="'商家账户:'+item.name" :name="index" v-for="(item,index) in areaName" :key="index">
              <el-form ref="form1" :model="item" label-width="120px" label-position="right" disabled style="width: 95%;">
                <el-form-item label="景区名称">
                  <el-input v-model="item.msg.name"  placeholder="景区名称"></el-input>
                </el-form-item>
                <el-form-item label="选择地区">
                  <el-input v-model="item.msg.province+item.msg.city+item.msg.region"></el-input>
                </el-form-item>
                <el-form-item label="国家A级">
                  <div style="margin-top: 12px">
                    <el-rate v-model="item.msg.star"></el-rate>
                  </div>
                </el-form-item>
                <el-form-item label="负责人姓名">
                  <el-input v-model="item.msg.man" placeholder="负责人姓名"></el-input>
                </el-form-item>
                <el-form-item label="负责人联系方式">
                  <el-input v-model="item.msg.call" placeholder="负责人联系方式"></el-input>
                </el-form-item>
              </el-form>
              <div style="position: relative;left: 120px">
                <el-button type="success" size="mini" @click="ok(item.msg,item.name)">通过申请</el-button>
                <el-button type="info" size="mini" @click="reject(item.msg,item.name)">拒绝申请</el-button>
                <el-button size="mini" style="margin-left: 62%" type="primary" plain @click="downLoad(item.name)"><i class="el-icon-download"/>下载文件</el-button>
              </div>
            </el-collapse-item>
          </el-collapse>
        </el-main>
      </el-container>
      <el-dialog
        title="下载链接"
        :visible.sync="dialogVisible"
        width="30%">
        <el-row style="width: 80%;margin: 0 auto;">
          <el-col v-for="item in fileList" :key="item" style="margin-top: 20px">
            <el-col :span="18">{{item.name}}:</el-col>
            <el-col :span="6"><a :href="item.link" target="_blank" download="">下载</a></el-col>
          </el-col>
          <el-col v-show="fileList.length===0">该用户上传文件已被损坏，请驳回让申请人再提交</el-col>
        </el-row>
        <span slot="footer" class="dialog-footer">
          <el-tag >若无法下载，可在打开页面右键另存为</el-tag>
        </span>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
  import {baseURL} from "../../utils/constant";

  export default {
    name: "co",
    created() {
      this.$axios.get(baseURL+'/getRedisAreaInfo').then(res=>{
        this.form = res.data;
        for(let k in this.form){
          let obj = {};
          obj.name = k;
          obj.msg = this.form[k];
          this.areaName.push(obj)
        }
      })
    },
    data(){
      return{
        activeName:'1',
        form: {},
        areaName:[],
        fileList:[],
        dialogVisible:false
      }
    },
    methods:{
      ok(item,name){
        let form = {id:0,uid:item.id,name:item.name,principal:item.man,principal_tel:item.call,site:item.province+item.city+item.region,star:item.star}
        this.$axios.post(baseURL+'/passRegister',{area:form,name:name}).then(res=>{
          if(res.data==='ok'){
            this.$message.success('已通过')
          }
        })
      },
      reject(item,name){
        let form = {id:0,uid:item.id,name:item.name,principal:item.man,principal_tel:item.call,site:item.province+item.city+item.region,star:item.star}
        this.$axios.post(baseURL+'/rejectRegister',{area:form,name:name}).then(res=>{
          if(res.data==='ok'){
            this.$message.success('已拒绝')
          }
        })
      },
      downLoad(item){
        this.$axios.get(baseURL+'/downLoadApplyDocument',{params:{name:item}}).then(res=>{
          if(res.data){
            for (let i in res.data){
              let info={};
              info.name = i;
              info.link = res.data[i];
              this.fileList.push(info)
            }
            this.dialogVisible=true
          }
        })
      }
    }
  }
</script>

<style scoped>
  .row{
    margin:0 auto;
    width: 85%;
  }
</style>

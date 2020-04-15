<template>
  <div class="row">

    <main-tab-bar/>
    <el-card shadow="never" class="card">
      <el-tag effect="dark" type="info">我要投诉</el-tag>
      <div style="margin: 50px"></div>
      <div v-if="Object.keys($route.query).length===0">
        <el-row :gutter="30">
          <el-col :span="24"><i class="el-icon-info"/>&nbsp;请到对应页面下进行投诉</el-col>
          <el-col :span="24">&nbsp;</el-col>
          <el-col :span="24">举例：</el-col>
          <el-col :span="24">&nbsp;</el-col>
          <el-col :span="24">&nbsp;</el-col>
        </el-row>
        <el-row :gutter="30">
          <el-col :span="8">
            <div>游记不良内容</div>
            <div style="margin: 10px"></div>
            <el-image :src="travel" :preview-src-list="srcList"></el-image>
          </el-col>
          <el-col :span="8">
            <div>景区纠错</div><div style="margin: 10px"></div>
            <el-image :src="area" :preview-src-list="srcList"></el-image>
          </el-col>
          <el-col :span="8">
            <div>退款未到账</div><div style="margin: 10px"></div>
            <el-image :src="refund" :preview-src-list="srcList"></el-image>
          </el-col>
        </el-row>
      </div>
      <el-form :model="form" ref="form" label-width="100px" v-else>
        <el-form-item label="投诉类别：">
          <el-tag>{{info}}</el-tag>&emsp;
          <el-select v-model="form.additional" placeholder="景区投诉具体类别" v-show="$route.query.area==='mis'">
            <el-option
              v-for="item in options"
              :key="item.values"
              :label="item.label"
              :value="item.label">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="投诉内容：" style="width: 90%">
          <el-input type="textarea" placeholder="请输入您投诉的具体内容" resize="none" v-model="form.content" :autosize="{ minRows: 8}"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click.once="sub">提交</el-button>&emsp;
          <span style="color: #8a8a8a">
            <i class="el-icon-info"></i>
            <span>&nbsp;提交后可在<el-link class="link" :underline="false" @click="$router.push('/user/complaint')">我的主页</el-link>里查看</span>
          </span>

        </el-form-item>
      </el-form>
    </el-card>
    <foot/>
  </div>
</template>

<script>
  import MainTabBar from "../tabbar/MainTabBar";
  import Foot from "../footer/Footer";
  import {baseURL} from "../../../utils/constant";
  export default {
    name: "Complaint",
    components:{MainTabBar,Foot},
    data(){
      return{
        refund:require('assets/img/complaint/refund.png'),
        area:require('assets/img/complaint/area.png'),
        travel:require('assets/img/complaint/travel.png'),
        srcList:[require('assets/img/complaint/travel.png'),require('assets/img/complaint/area.png'),require('assets/img/complaint/refund.png'),],
        form:{
          aid:this.$route.query.aid,
          oid:this.$route.query.orderID,
          tid:this.$route.query.tid,
          types:'',
          additional:'',
          content:'',
          uid:this.$store.getters.userInfo.id
        },
        options: [{
          values: '选项1',
          label: '价格有误'
        }, {
          values: '选项2',
          label: '图片错误'
        }, {
          values: '选项3',
          label: '景区简介有误'
        }, {
          values: '选项4',
          label: '景区侵权'
        }],
      }
    },
    computed:{
      info(){
        let info = this.$route.query
        if(info.area){
          this.form.types = 3
          return '景区纠错'
        }
        if(info.customer){
          this.form.types = 2
          return '退款未到账'
        }
        if(info.travel){
          this.form.types = 1
          return '游记内容不良'
        }
      }
    },
    methods:{
      sub(){
        if(this.$store.getters.userInfo){
          this.$axios.post(baseURL+'/add_complaint',this.form).then(res=>{
            if(res.data==='ok'){
              this.$message.success('提交成功')
            }
          })
        }else {
          this.$message.warning('请登录')
        }
      }
    }
  }
</script>

<style scoped>
  .card{
    width: 80%;
    margin: 20px auto;
  }
  textarea{
    border-color: #e1e1e1;
    width: 80%;
    height: 200px;
    padding: 10px;

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
  .link{
    position: relative;
    top: -1px;
  }
</style>

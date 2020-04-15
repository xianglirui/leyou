<template>
  <div class="row">
    <el-row :gutter="20" id="f1">
      <el-col :span="3" v-for="item in infos" :key="item">
        <el-link href="#" :underline="false">{{item}}</el-link>
      </el-col>
    </el-row>
    <el-divider/>
    <el-row :gutter="20" id="f2">
      <el-col :span="3">官方合作：</el-col>
      <el-col :span="3" v-for="item in hezuo" :key="item">
        <el-link href="#" :underline="false">{{item}}</el-link>
      </el-col>
    </el-row>
    <el-row :gutter="20" id="f3">
      <el-col :span="6">备案信息</el-col>
      <el-col :span="6"><el-link href="#" :underline="false">热线：111-222-333-444</el-link></el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'Footer',
  data(){
    return{
      infos:['网站首页','关于我们','联系我们','合作伙伴','服务协议','帮助中心','意见反馈'],
      hezuo:['合作方1','合作方2','合作方3','合作方4']
    }
  },
};
</script>

<style scoped>
  .row{
    width: 100%;
    height: 180px;
    margin-top: 40px;
    background-color: #323232;

    /*border: 1px solid red;*/
  }
  #f1{
    width: 50%;
    position: relative;
    left: 29%;
    top: 10%;
    margin-bottom: 20px;
    height: 40px;
  }
  .el-link{
    color: #a3a3a3;
  }
  #f2{
    width: 60%;
    position: relative;
    left: 30%;
  }
  .el-col{
    color: #a3a3a3;
  }
  #f3{
    width: 50%;
    position: relative;
    left: 37%;
    top: 30px;
  }

</style>

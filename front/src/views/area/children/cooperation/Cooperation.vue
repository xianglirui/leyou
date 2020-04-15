<template>
  <div class="row">
    <div class="col">
      <el-row :gutter="20" v-for="index in 2" :key="index">
        <el-col :span="8" v-for="item in 3" :key="item">
          <el-image :src="url[index*item]" :preview-src-list="srcList" style="width: 100%; height: 220px"></el-image>
          <div style="height: 20px;width: 100%;">宜昌市政府</div>
        </el-col>
      </el-row>
    </div>
    以上图片均来自百度图片
    <div class="exits">
      <el-link type="primary" @click="open2" :underline="false">有错 | 退出合作</el-link>
      <div style="margin: 10px"></div>
      <span>联系我们：+1348576195</span>
    </div>

  </div>

</template>

<script>
  import {xiaoshou} from "../../../../assets/js/administrator";

  export default {
    name: "Cooperation",
    data() {
      return {
        xiaoshou:xiaoshou,
        url:[
          require('./img/timg.jpg'),
          require('./img/timg (1).jpg'),
          require('./img/timg (2).jpg'),
          require('./img/timg (3).jpg'),
          require('./img/timg.jpg'),
        ],
        srcList: [
          require('./img/timg.jpg'),
          require('./img/timg (1).jpg'),
          require('./img/timg (2).jpg'),
          require('./img/timg (3).jpg'),
          require('./img/timg.jpg'),
        ],

      }
    },
    computed:{
      isImage(){
        return true;
      }
    },
    methods:{
      open2() {
        this.$notify({
          title: '合作事宜',
          message: '请联系销售经理 '+this.xiaoshou,
          type: 'warning',
          offset:80
        });
      },
    }
  }
</script>

<style scoped>
  .row{
    width: 100%;
    margin: 0 auto;
  }
  .col{
    padding: 20px;
    width: 100%;
  }
  .el-row{
    margin-bottom: 20px;
  }
  .exits{
    width: 150px;
    float: right;
    margin-top: 2%;
    /*border: 1px solid red;*/
  }
  .exits span{
    font-size: 14px;
    font-family: Bahnschrift;
  }

</style>

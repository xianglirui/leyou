<template>
  <div class="row" v-loading="loading"
       element-loading-text="因数据量太少，该功能暂不支持开放"
       element-loading-spinner="el-icon-loading">
    <el-row>
      <el-col :span="24">
        <div class="grid-content bg-purple-dark">
          <div style="width: 25px;height: 25px;"><img src="~assets/images/svg/城市.svg" width="100%"/></div>
          <div style="margin-left: 8px;font: 20px bold 微软雅黑">全国热门城市</div>
        </div>
      </el-col>
    </el-row>
    <div class="poj">
      <el-col v-for="item in imgs" style="overflow: hidden;" :key="item">
        <span><img :src="item" width="100%" alt="" class="imgs"/></span>
        <div class="info_span">
          <div class="local">
            <img src="~assets/images/svg/定位.svg" width="25px" height="25px" alt=""/>
            <p><strong style="color: white">北京</strong></p>
          </div>
          <div class="place">
            <el-link :underline="false" style="color:white" @click="loading=true">5 处景区</el-link>
          </div>
        </div>
      </el-col>
    </div>
    <div class="p_cta">
      <div class="cta">
        <h3>有一座城，在等你</h3>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "heads",
    data(){
      return{
        imgs:[
          require('assets/images/images3/destination/pic1.jpg'),
          require('assets/images/images3/destination/pic5.jpg'),
          require('assets/images/images3/destination/pic2.jpg'),
          require('assets/images/images3/destination/pic4.jpg'),
          require('assets/images/images3/destination/pic3.jpg'),
        ],
        loading:false
      }
    }
  }
</script>

<style scoped>
  *{
    font-family: 微软雅黑;
  }
  .row{
    margin: 60px auto;
  }
  .bg-purple-dark {
    /*background: #99a9bf;*/
    height: 80px;
    display: flex;
    align-items: center;
    justify-content: center;
    /*margin-bottom: 5px;*/
    background-color: #eeeeee;
  }

  .cta{
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
    background: rgb(238, 238, 238);
    opacity: .6;
  }
  .p_cta{
    background: url("../../assets/img/logo/gugong.png") fixed 50% 50%;
    -webkit-background-size: cover;
    background-size: cover;

    position: relative;
    padding: 100px 0;
  }

  .cta h3{
    text-align: center;
    line-height: 200px;
    color: #ff3e10;
  }
  .poj{
    width: 100%;
    height: 310px;
    position: relative;
    /*bottom: -50px;*/
    z-index: 9;
    display: flex;
    justify-content: start;
    /*border: 1px solid red;*/
  }
  .place{
    width: 80px;
    height: 40px;
    background-color: rgba(64, 159, 255, 0.82);
    color: white;
    text-align: center;
    line-height: 38px;
  }
  .local{
    width:  30%;
    /*border: 1px solid red;*/
  }
  .local strong{
    position: relative;
    left: 5px;
  }
  .info_span{
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 17%;
    height: 12%;
    margin-left: 2%;
    position: absolute;
    top: 250px;
  }
  .imgs{
    transition: all 0.4s;/*图片放大过程的时间*/
  }
  .imgs:hover{
    transform: scale(1.08);
  }


</style>

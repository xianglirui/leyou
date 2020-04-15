<template>
  <div>
    <scenic-bar>
      <one-bar class="one-bar">
        <span slot="home-back" class="home-back">
          <img src="~assets/img/logo/center_1.png"/>
        </span>
      </one-bar>
    </scenic-bar>
    <div class="step1">
      <step-panel :descriptions="descriptions" :titles="titles"/>
    </div>
  </div>
</template>

<script>
  import ScenicBar from "../tabbar/ScenicBar";
  import OneBar from "../tabbar/OneBar";
  import StepPanel from "../../content/steppanel/StepPanel";
  export default {
    name: "PlaceCenter",
    components:{
      OneBar,
      ScenicBar,
      StepPanel
    },
    data(){
      return{
        descriptions:[
          ''
        ],
        titles:[
          '商家登录',
          '点击申请',
          '提交文件',
          ''
        ]
      }
    }
  }
</script>

<style scoped>
  .home-back img{
    transform: scale(0.5);
    position: relative;
    top: 5px;
  }
  .home-back{
    margin-left: 100px;
  }
  .step1{
    width: 800px;
    margin: 200px auto;
    border: 1px solid red;
  }
</style>

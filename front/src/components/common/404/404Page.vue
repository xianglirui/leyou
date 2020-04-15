<template>
  <div>
    <main-tab-bar/>
    <div class="err">
      <img src="@/assets/img/other/404_2.png" id="img404" alt="">
      <div class="err-info">
        <div>
          <p>抱歉！你访问的页面不存在！</p>
          <el-button type="primary" @click="goHome">点我返回首页</el-button>
        </div>

        <div class="QR-code-err">
          <img src="@/assets/img/other/123.png" alt="" style="width: 131px;height: 131px;">
          <span>试试移动端</span>
        </div>
      </div>
  </div>
  </div>
</template>

<script>
import MainTabBar from "../tabbar/MainTabBar";
export default {
  name: 'ErrPage',
  data() {
    return {
    };
  },
  components:{
    MainTabBar
  },
  methods:{
    goHome(){
      return this.$router.push('/home')
    },
  }

};
</script>

<style>
  body{
    background: var(--color-background);
  }
  .err{
    margin-top: 20px;
    position: relative;
    width: 100%;
    height: 500px;
    /*border: 1px solid red;*/
  }
  #img404{
    position: absolute;
    top: 0;
    left: 80px;
    transform: scale(0.8);
  }
  .QR-code-err{
    font-size: 14px;
    margin-top: 80px;
  }
  .err-info p{
    padding-bottom: 15px;
  }
  .err-info{
    margin-top: 100px;
    width: 250px;
    height: 350px;
    position: absolute;
    right: 180px;
    /*border: 1px solid red;*/
  }
</style>

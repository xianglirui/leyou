<template>
  <div class="row">
    <main-tab-bar/>
    <div style="margin: 20px"></div>
    <div>
      <router-view/>
    </div>

    <foot/>
  </div>
</template>

<script>
  import MainTabBar from "../../components/common/tabbar/MainTabBar";
  import Foot from "../../components/common/footer/Footer";
  export default {
    name: "Display",
    components:{
      MainTabBar,
      Foot
    },
  }
</script>

<style scoped>

  .row{
    width: 100%;
    /*height: calc(100vh + 20px);*/
    background-color: #fafafa;
  }
</style>

<template>
  <div class="TabBarItem">
    <div class="left" v-if="isShowHomeBar"><slot name="home-back"></slot></div>
    <div class="left" v-else><slot name="local"></slot></div>
    <div class="center"><slot name="login-info"></slot></div>
    <div class="right"><slot name="person-info"></slot></div>
  </div>
</template>

<script>
export default {
  name: 'TabBarItem',
  computed:{
    isShowHomeBar(){
      return this.$route.path.indexOf('/home') === -1
    }
  },
};
</script>

<style scoped>
  .TabBarItem{
    display: flex;
    align-items: center;
    justify-content: flex-end;
    font-size: 12px;
    width: 1200px;
    margin: 0 auto;
    /*border: 1px solid red;*/
  }
  .left{
    width: 700px;
    /*border: 1px solid blue;*/
  }
  .right{
    width: 410px;
    /*border: 1px solid orange;*/
  }
  .center{
    width: 220px;
    /*border: 1px solid green;*/
  }

</style>

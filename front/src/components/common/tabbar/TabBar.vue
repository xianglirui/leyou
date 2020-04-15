<template>
  <div id="tabBar">
    <slot></slot>
  </div>
</template>

<script>
export default {
  name: 'TabBar',
};
</script>

<style scoped>
  #tabBar{
    display: flex;
    align-items: center;
    background-color: #f0f0f0;
    width: 100%;

    height: 35px;
    z-index: 9;

    /*position: fixed;*/
    /*right: 0;*/
    /*left: 0;*/
    /*top: 0;*/
    /*box-shadow: 0 -1px 1px rgba(100,100,100,.1);*/
  }
</style>

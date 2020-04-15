<template>
  <div id="ScenicBar">
    <slot></slot>
  </div>
</template>

<script>
  export default {
    name: "ScenicBar"
  }
</script>

<style scoped>
  #ScenicBar{
    display: flex;
    align-items: center;
    background-color: #88d7c4;
    width: 100%;

    height: 65px;
    z-index: 9;

    position: fixed;
    right: 0;
    left: 0;
    top: 0;
    box-shadow: 0 -1px 1px rgba(100,100,100,.9);
  }
</style>

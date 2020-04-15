<template>
  <div>
    <div class="left"><slot name="home-back"></slot></div>
    <div class="center"><slot name="login-info"></slot></div>
    <div class="right"><slot name="person-info"></slot></div>
  </div>
</template>

<script>
  export default {
    name: "OneBar"
  }
</script>

<style scoped>

</style>

<template>
  <div>
    <component_verify/>
  </div>
</template>

<script>
  import component_verify from "../../components/content/login/component_verify";
  export default {
    name: "Verify",
    components:{
      component_verify
    }
  }
</script>

<style scoped>
  div{
    width:100%;
    height: 100%;
    background: var(--color-background);;
    position: absolute;

  }
</style>

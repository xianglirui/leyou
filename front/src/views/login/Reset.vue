<template>
  <div>
    <component_reset/>
  </div>
</template>

<script>
  import component_reset from "../../components/content/login/component_reset";
  export default {
    name: "Reset",
    components:{
      component_reset,
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

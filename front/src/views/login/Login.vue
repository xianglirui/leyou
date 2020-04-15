<template>
  <div class="row">
    <component_login/>
  </div>
</template>

<script>
  import component_login from "../../components/content/login/component_login";
  export default {
    name: "Login",
    components:{
      component_login
    }
  }
</script>

<style scoped>
  /*body{*/
  /*  background:var(--color-background);*/
  /*}*/
  .row{
    width:100%;
    height: 100%;
    background: var(--color-background);;
    position: absolute;
  }
</style>

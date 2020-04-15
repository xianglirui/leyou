<template>
  <div class="row">
    <component_register/>
  </div>
</template>

<script>
  import component_register from "../../components/content/login/component_register";
  import {request} from "../../network/request";
  export default {
    name: "Register",
    components:{
      component_register
    },
    created() {
      request({
        url:'/register'
      })
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

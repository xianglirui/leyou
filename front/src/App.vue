<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
  import {baseURL} from "./utils/constant";
  import {getToken} from "./utils/token";

  export default {
  name: 'App',
  created() {
    if(getToken()){
      this.$axios.get(baseURL+'/getRole').then(res=>{})
    }
  },
  mounted() {
    if (this.isMobile()) {
      this.$router.push("/tip");
    }
  },
  methods: {
    isMobile() {
      return navigator.userAgent.match( // match方法可在字符串内检索指定的值，
        /(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i
      );
    }
  }

}
</script>

<style>
  @import "./assets/css/base.css";
</style>

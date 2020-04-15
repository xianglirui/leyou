<template>
  <div id="layout_all">
    <div class="logo_span">
      <img src="~assets/img/logo/logo_2.png" width="150px" height="60px" @click="$router.push('/home')">
      <span style="font-family: 华文仿宋;font-size: 23px">要出游，选乐游</span>
    </div>
    <el-card class="box-card">
      <div class="login-content">
        <el-tag>{{head}}</el-tag>
        <div style="margin: 20px"></div>
        <div class="login-input"><slot name="input"></slot></div>
        <div class="info-footer">
          <slot name="info"></slot>
          <slot name="format_login"></slot>
        </div>
        <div style="margin: 20px"></div>
        <div>
          <el-button type="primary" class="btn" @click="btnClick" :loading="load">{{btn}}</el-button>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
  export default {
    name: "LoginLayout",
    props:{
      btn:{
        type:String,
        default(){
          return ""
        }
      },
      head:{
        type: String,
        default() {
          return "";
        }
      },
      load:{
        type:Boolean,
        default(){
          return false
        }
      },
    },
    data(){
      return{

      }
    },
    methods:{
      btnClick(){
        this.$emit('btnClick')
      }
    }
  }
</script>

<style scoped>
  #layout_all{
    width: 450px;
    margin: 0 auto;
    margin-top: 9%;
  }
  .box-card {
    width: 450px;
  }
  .logo_span{
    width: 450px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .login-content{
    width: 300px;
    margin: 0 auto;
  }
  .btn{
    width: 300px;
  }
  .info-footer{
    display: flex;
    justify-content: space-between;
  }
</style>

<template>
  <div class="row">
    <el-menu :default-active="this.$route.path" :router="true">
      <el-menu-item index="/user">
        <i class="el-icon-s-home"></i>
        <span slot="title">我的乐游</span>
      </el-menu-item>
      <el-submenu index="/user/order">
        <template slot="title">
          <i class="el-icon-tickets"></i>
          <span>我的订单</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="/user/order">全部订单</el-menu-item>
          <el-menu-item index="/user/order/use">待使用&nbsp;{{use}}</el-menu-item>
          <el-menu-item index="/user/order/evaluate">待评价&nbsp;{{evaluate }}</el-menu-item>
          <el-menu-item index="/user/order/refund">退款信息</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
      <el-submenu index="/user/collect">
        <template slot="title">
          <i class="el-icon-star-on"></i>
          <span>我的收藏</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="/user/collect">收藏的景区</el-menu-item>
          <el-menu-item index="/user/collect/blog">收藏的游记</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
      <el-submenu index="/user/article">
        <template slot="title">
          <i class="el-icon-edit-outline"></i>
          <span>我的文章</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="/user/article">我的评论</el-menu-item>
          <el-menu-item index="/user/article/travel_notes">我的游记</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
      <el-menu-item index="/user/complaint">
        <i class="el-icon-s-comment"></i>
        <span slot="title">我的投诉</span>
      </el-menu-item>
      <el-menu-item index="/user/account">
        <i class="el-icon-setting"></i>
        <span slot="title">个人账户</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
  export default {
    name: "navBar",
    data(){
      return{
      }
    },
    created() {
      if(this.evaluate===0){
        this.$router.push('/user')
      }
    },
    props:{
      use:{
        type:Number,
        default:0
      },
      evaluate:{
        type:Number,
        default:0
      }
    }
  }
</script>

<style scoped>
  .row{
    width: 100%;
    height: 100vh;
    background-color: #fff;
  }
  .el-menu:not(.el-menu--collapse) {
    width: 241px;
    min-height: 100vh;
  }
</style>

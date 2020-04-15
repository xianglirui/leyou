<template>
  <div>
    <el-card :body-style="{ padding: '0px' }">
      <div class="head">
        <el-row class="user">
          <el-col :span="2">&nbsp;</el-col>
          <el-col :span="4">
            <el-avatar :size="100" :src="circleUrl"></el-avatar>
          </el-col>
          <el-col :span="14">
            <div style="position: relative;top: 22px">
              <div style="font-size: 20px">{{name}}
              </div>
              <div style="margin: 15px"></div>
              <div>
                <el-link :underline="false" type="info" href="#echart"><icon-svg icon-class="zuji"/>&nbsp;我的足迹</el-link>
              </div>
            </div>
          </el-col>
          <el-col :span="4" style="position: relative;top: 40px">
            <el-link :underline="false" @click="$router.push('/user/account')">个人信息设置<i class="el-icon-caret-right"/></el-link>
          </el-col>
        </el-row>

      </div>
      <div class="body">
        <div class="order">

          <el-link :underline="false" @click="$router.push('/user/order')">
            <icon-svg icon-class="dingdan" style="font-size: 40px"/>&emsp;
            <span>全部订单</span>
          </el-link>

          <el-link :underline="false" @click="$router.push('/user/order/use')">
          <el-badge :value="use">
            <icon-svg icon-class="daishiyong" style="font-size: 40px"/>&emsp;
            <span>待使用</span>
          </el-badge>
          </el-link>
          <el-link :underline="false" @click="$router.push('/user/order/evaluate')">
          <el-badge :value="evaluate">
            <icon-svg icon-class="pinglun" style="font-size: 40px"/>&emsp;
            <span>待评论</span>
          </el-badge>
          </el-link>
          <el-link :underline="false" @click="$router.push('/user/order/refund')">
            <icon-svg icon-class="shouhou" style="font-size: 40px"/>&emsp;
            <span>申请退款</span>
          </el-link>
        </div>
      </div>
    </el-card>
    <el-card style="margin-top: 50px;background-color: #fafafa" shadow="hover">
      <maps/>
    </el-card>
  </div>
</template>

<script>
  import maps from "./maps";
  export default {
    name: "index",
    components:{maps},
    data(){
      return{
        name:'xlr',
        circleUrl: this.$store.getters.userInfo?require('assets/img/other/头像 男孩.svg'):"https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
        evaluate:this.$store.state.customer.evaluate.length,
        use:this.$store.state.customer.use.length
      }
    },
  }
</script>

<style scoped>
  .el-card{
    padding: 0;
  }
  .head{
    background-color: #e1e1e1;
    width: 100%;
    height: 200px;
  }
  .body{
    width: 100%;
    height: 100px;
  }
  .order{
    width: 90%;
    display: flex;
    margin: 0 auto;
    position: relative;
    top: 30px;
    justify-content: space-around;
    font-size: 14px;
  }
  .order span{
    position: relative;
    bottom: 8px;
  }
  .user{
    position: relative;
    top: 50px;
  }
  .el-avatar{
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04)
  }
</style>

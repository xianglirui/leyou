<template>
  <div>
    <el-row style="margin-bottom: 50px">
      <el-col :span="6">
        <div class="logo">
          <img src="~assets/img/logo/logos.png" id="imgs" alt=""/>
        </div>

      </el-col>
      <el-col :span="16"><nav-menu/></el-col>
      <el-col :span="2">
        <div class="info" @click="$router.push('/background')">
          <el-card id="yg" :body-style="{ padding: '5px' }">
            <icon-svg :icon-class="grade" style="font-size: 20px"/>&nbsp;&nbsp;<span style="position: relative;top:-3px">{{this.$store.getters.userInfo.perms}}</span>
          </el-card>
        </div>
      </el-col>
    </el-row>
    <router-view/>
  </div>
</template>

<script>
  import navMenu from "./bar/navMenu";
  import Complaint from "./complaint/Complaint";
  export default {
    name: "index",
    components:{navMenu,Complaint},
    created() {

    },
    computed:{
      grade(){
        if(this.flag===2){
          return 'shuye'
        }
        if(this.flag===3){
          return 'watermelon'
        }
        if(this.flag===4){
          return 'fengye'
        }
        if(this.flag===5){
          return 'xuehua1'
        }
      }
    },
    data(){
      return{
        flag:this.$store.getters.userInfo.role
      }
    },

  }
</script>

<style scoped>
  .logo{
    position: relative;
    background-color: #545c64;
    height: 60px;
    /*border: 1px solid red;*/
  }
  #imgs{
    transform: scale(0.3);
    position: absolute;
    left: -300px;
    top: -90px;
  }
  .info{
    background-color: #545c64;
    height: 60px;
  }
  #yg{
    /*height: 40px;*/
    width: 90px;
    height: 30px;
    /*margin-top: 10px;*/
    padding: 0;
    position: relative;
    top: 15px;
  }
</style>

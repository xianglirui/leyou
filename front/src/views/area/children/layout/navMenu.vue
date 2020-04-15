<template>
  <div class="row">
   <el-menu :default-active="activerouter"  :collapse="isCollapse"   background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b">
     <el-menu-item index="1"  @click="asd(1)">
       <icon-svg icon-class="zuoyelicheng" style="font-size: 20px;" class="icon-svg"/>
       <span class="txt">管理主页</span>
     </el-menu-item>

     <el-menu-item index="2" @click="asd(2)">
       <icon-svg icon-class="bianjibiaodanx" style="font-size: 20px" class="icon-svg"/>
       <span class="txt">景区详情</span>
     </el-menu-item>
     <el-submenu index="3">
       <template slot="title">
         <icon-svg icon-class="shouru" style="font-size: 20px" class="icon-svg"/>
         <span class="txt">营收情况</span>
       </template>
       <el-menu-item-group>
         <el-menu-item index="1-1" @click="asd(3)">营收表</el-menu-item>
         <el-menu-item index="1-2" @click="asd(4)">营收图</el-menu-item>
       </el-menu-item-group>
     </el-submenu>
     <el-submenu index="4">
       <template slot="title">
         <icon-svg icon-class="goutongjiaoliu" style="font-size: 20px" class="icon-svg"/>
         <span class="txt">顾客互动</span>
       </template>
       <el-menu-item-group>
         <el-menu-item index="2-1" @click="asd(5)">留言回复</el-menu-item>
         <el-menu-item index="2-2" @click="asd(6)">投诉处理</el-menu-item>
       </el-menu-item-group>
     </el-submenu>
     <el-menu-item index="5" @click="asd(7)">
       <icon-svg icon-class="hezuoqudao" style="font-size: 20px" class="icon-svg"/>
       <span class="txt">乐游合作</span>
     </el-menu-item>
   </el-menu>
  </div>

</template>

<script>
  export default {
    name: "navMenu",
    data(){
      return{
        activerouter:'1'
      }
    },
    created() {
        if(this.$route.path === '/area'){
          this.activerouter='1'
        }
        if(this.$route.path === '/area/form'){
          this.activerouter='2'
        }
        if(this.$route.path === '/area/revenue/table'){
          this.activerouter= '1-1'
        }
        if(this.$route.path === '/area/revenue/map'){
          this.activerouter= '1-2'
        }
        if(this.$route.path === '/area/customer/talk'){
          this.activerouter= '2-1'
        }
        if(this.$route.path === '/area/customer/complain'){
          this.activerouter= '2-2'
        }
        if(this.$route.path === '/area/cooperation'){
          this.activerouter= '5'
        }
    },
    computed:{
      isCollapse(){
        return this.$store.state.isCollapse
      },
    },
    methods: {
      asd(index){
        switch (index) {
          case 1:
            this.$router.push({path:'/area',query:{okid:this.$store.getters.userInfo.name}})
            break;
          case 2:
            this.$router.push({path:'/area/form',query:{okid:this.$store.getters.userInfo.name}})
            break;
          case 3:
            this.$router.push({path:'/area/revenue/table',query:{okid:this.$store.getters.userInfo.name}})
            break;
          case 4:
            this.$router.push({path:'/area/revenue/map',query:{okid:this.$store.getters.userInfo.name}})
            break;
          case 5:
            this.$router.push({path:'/area/customer/talk',query:{okid:this.$store.getters.userInfo.name}})
            break;
          case 6:
            this.$router.push({path:'/area/customer/complain',query:{okid:this.$store.getters.userInfo.name}})
            break;
          case 7:
            this.$router.push({path:'/area/cooperation',query:{okid:this.$store.getters.userInfo.name}})
            break;
        }
      }
    }
  }
</script>

<style scoped>
  .row{
    /*border: 1px solid red;*/
  }
  .el-menu:not(.el-menu--collapse) {
    width: 200px;
    overflow: hidden;
    min-height: calc(100vh - 100px);
  }
  .el-menu--collapse{
    min-height: calc(100vh - 100px);
  }

  .icon-svg{
    position: relative;
    top: 3px;

    /*color: #cecece;*/
  }
  .txt{
    position: relative;
    left: 13px;
  }

</style>

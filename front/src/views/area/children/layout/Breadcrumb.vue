<template>
  <div class="row">
    <el-link class="icons" :underline="false"  @click="overturn">
      <icon-svg icon-class="shouqi" style="font-size: 20px" v-if="forward"/>
      <icon-svg icon-class="dakai" style="font-size: 20px" v-else/>
    </el-link>

    <el-breadcrumb separator-class="el-icon-arrow-right" class="bread">
      <el-breadcrumb-item><span style="font-weight: bold;">导航</span></el-breadcrumb-item>
      <el-breadcrumb-item v-for="item in navList" :key="item">{{item}}</el-breadcrumb-item>
    </el-breadcrumb>
  </div>
</template>

<script>
  export default {
    name: "Breadcrumb",
    data(){
      return{
        forward:true,
        navList:[],
      }
    },
    methods:{
      overturn(){
        this.forward = !this.forward
        this.$store.commit('changeIsCollapse',!this.$store.state.isCollapse)
      },
    },
    created() {
      this.navList=[]
      let str = this.$route.path
      let name = this.$route.name
      if(str.match('customer')){
        this.navList.push('顾客互动',name)
      }else if(str.match('revenue')){
        this.navList.push('营收情况',name)
      }else if(str.match('edit')){
        this.navList.push('主页',name)
      }else {
        this.navList.push(name)
      }
    },
    watch:{
      $route(){
        this.navList=[]
        let str = this.$route.path
        let name = this.$route.name
        if(str.match('customer')){
          this.navList.push('顾客互动',name)
        }else if(str.match('revenue')){
          this.navList.push('营收情况',name)
        }else if(str.match('edit')){
          this.navList.push('主页',name)
        }else {
          this.navList.push(name)
        }
      }
    }
  }
</script>

<style scoped>
  .row{
    display: flex;
    align-content: center;
    /*border: 1px solid red;*/
    height: 55px;
  }
  .bread{
    position: relative;
    top: 20px;
    left: 15px;
  }

</style>

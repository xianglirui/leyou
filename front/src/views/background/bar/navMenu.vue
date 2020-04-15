<template>
  <div class="row">
    <el-menu
      mode="horizontal"
      :router="true"
      :default-active="$route.path"
      class="el-menu-vertical-demo"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b">
      <el-menu-item :index="item" v-for="item in routeList" :key="item">
        <span slot="title">{{routeName(item)}}</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
  export default {
    name: "navMenu",
    data(){
      return{
        routeList:[]
      }
    },
    created() {
      const list = this.$router.options.routes.filter(v=>{
        return v.path==='/background'
      })
      for(let i of list){
        this.routeList.push(i.path)
        if(i.children){
          for(let k of i.children){
            if(k.path!==''){
              this.routeList.push(i.path+'/'+k.path)
            }
          }
        }
      }
    },
    methods:{
      routeName(item){
        switch (item) {
          case '/background':
            return '我的主页';
          case '/background/complaint':
            return '投诉处理';
          case '/background/co':
            return '合作业务';
          case '/background/account':
            return '账务管理';
          case '/background/boss':
            return '我是老板';
        }
      }
    }
  }
</script>

<style scoped>
  .row{
    margin: 0 auto;
  }
  .el-menu-item{
    margin-left: 5%;
  }
</style>

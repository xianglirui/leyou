<template>
  <div>
    <el-row>
      <el-col :span="24">
        <div class="top">
          <img src="~assets/img/logo/logo_2.png" id="imgs"/>
          <el-badge is-dot  id="bell">
            <el-link :underline="false">
              <i class="el-icon-bell" style="font-size: 20px;color: #000000"/>
            </el-link>
          </el-badge>
          <el-dropdown trigger="click" id="touxiang" @command="edit">
            <el-avatar :size="45" shape="square" :src="url"></el-avatar>
            <i class="el-icon-caret-bottom"/>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item icon="el-icon-user-solid" disabled>{{usernamePro}}</el-dropdown-item>
              <el-dropdown-item divided icon="el-icon-s-tools" command="account">账户管理</el-dropdown-item>
              <el-dropdown-item icon="el-icon-caret-left" command="logout">退出</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </el-col>
    </el-row>
    <div style="margin-bottom: 25px"></div>
    <el-container  class="main">
      <div class="aside">
        <nav-menu/>
      </div>
      <el-container>
        <el-header class="header" height="55px">
          <breadcrumb/>
        </el-header>
        <el-scrollbar>
        <el-main>
          <router-view/>
        </el-main>
        </el-scrollbar>
      </el-container>
    </el-container>
    <foot/>
  </div>
</template>

<script>
  import navMenu from "./children/layout/navMenu";
  import Breadcrumb from "./children/layout/Breadcrumb";
  import Foot from "../../components/common/footer/Footer";
  import {baseURL} from "../../utils/constant";
  export default {
    name: "Area",
    components: {
      navMenu,
      Breadcrumb,
      Foot
    },
    created() {
      if(this.$route.query.okid===this.$store.getters.userInfo.name){
        this.$axios.get(baseURL+'/area_',{params:{param:this.$store.getters.userInfo.name}}).then(res=>{
          // console.log(res.data);
          this.$store.commit('changeArea',res.data)
        })
        this.$axios.get(baseURL+'/getComplaint',{params:{id:this.$store.getters.userInfo.id}}).then(res=>{
          // console.log(res.data)
          let info =[]
          for(let i in res.data.complaint){
            info.push(res.data.complaint[i])
          }
          this.$store.commit("SET_Complaint",info)
          this.$store.commit("SET_Refund",res.data.refund)
        })
        this.$axios.get(baseURL+'/getCommentByAid',{params:{aid:this.$store.state.area.id}}).then(res=>{
          this.$store.commit("SET_COMMENTS",res.data)
          console.log(res.data)
          let info=[]
          for(let i of res.data){
            info.push(i.back)
          }
          // console.log(info)
          this.$store.commit("SET_TXT",info)
          // console.log(res.data)
        })
      }else {
        this.$router.push('/err')
      }
    },
    data(){
      return{
        url:require('assets/img/other/user.gif'),
      }
    },
    computed:{
      usernamePro(){
        return this.$store.getters.userInfo.name
      }
    },
    methods:{
      edit(command){
        if(command==='account'){
          this.$router.push('/area/edit')
        }
        if(command==='logout'){
          this.$cookies.remove('Authorization')
          location.reload()
        }
      },
      logout(){
        //因为无法销毁服务端的token，所以采取在前端删除token，然后刷新页面
        this.$cookies.remove('Authorization')
        location.reload()
      }
    }
  }
</script>

<style scoped>
  .top{
    height: 60px;
    background-color: #88d7c4;
  }
  .top img {
    transform: scale(0.4);
    position: absolute;
    top: -40px;
  }
  .main{
    width: 88%;
    height: calc(100vh - 100px);
    margin: 0 auto;
    border: 1px solid #eee;
    background-color: #fafafa;
  }
  .header{
    width: 100%;
    border: 1px solid #cecece;
    box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.1)
  }
  #touxiang{
    position: relative;
    top: 7px;
    left: 89%;
    border-radius: 50%;
  }
  #bell{
    position: relative;
    top: -10px;
    left: 87.5%;
  }
  .el-avatar{
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
  }
  .el-scrollbar{
    height: 100%; /*此处一定要设置高度，不然内层的计算属性不生效*/
  }


</style>

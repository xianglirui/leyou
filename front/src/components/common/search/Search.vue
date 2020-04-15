<template>
  <div class="Search-div">
    <div class="search-children">
      <img src="~assets/img/other/logo.png" alt="">
      <div class="search-input">
        <el-input placeholder="搜索景点或博主" v-model="input" clearable></el-input>&nbsp;
        <el-button type="primary" icon="el-icon-search" @click="searchKey">搜索</el-button>
      </div>
      <div class="hot_search">
        大家都在搜：<span v-for="item in hot_search" :key="item">
        <el-link :underline="false" @click="go(item.aid)">&nbsp;&nbsp;{{item.area.name}}&nbsp;&nbsp;</el-link></span>
      </div>
      <div class="zhuye">
        <el-card shadow="hover" v-if="!userInfo">
          <el-row :gutter="20">
            <el-col :span="12">
              <div class="touxiang">
                <img src="~assets/images/svg/用户头像.svg" width="60px"/>
              </div>
            </el-col>
            <el-col :span="12">
              <el-button size="mini" round @click="logined">登录</el-button>
              <div style="margin: 8px"></div>
              <el-button size="mini" round @click="registed">注册</el-button>
            </el-col>
          </el-row>
        </el-card>
        <el-card shadow="hover" v-else>
          <el-row>
            <el-col :span="12">
              <el-avatar :size="60" :src="circleUrl"/>
            </el-col>
            <el-col :span="12">
              <el-row>
                <div style="margin: 5px;"></div>
                <icon-svg icon-class="renzheng"/><span class="name">{{userInfo.name}}</span>
              </el-row>
              <div style="margin: 5px;"></div>
              <el-row>
                <el-col :span="14">
                  <el-link @click="goByRole" style="font-size: 12px;color: #409eff"><i class="el-icon-s-home"/>主页</el-link>
                </el-col>
                <el-col :span="10">
                  <el-col><el-link style="font-size: 12px;" @click="logout">退出</el-link></el-col>
                </el-col>
              </el-row>
            </el-col>
          </el-row>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script>
  import {baseURL} from "../../../utils/constant";

  export default {
    name: "Search",
    props:{
      // userInfo:{
      //   type:Object,
      //   default(){
      //     return {}
      //   }
      // }
    },
    data(){
      return{
        input: this.$route.query.key,
        hot_search:[],

        circleUrl: require('assets/img/other/头像 男孩.svg'),
      }
    },
    created() {
      this.$axios.get(baseURL+'/hotSearchList').then(res=>{
        this.hot_search = res.data
      })
    },
    computed:{
      userInfo(){
        return this.$store.state.userInfos
      }
    },
    methods:{
      logined(){
        this.$router.push('/login')
      },
      registed(){
        this.$router.push('/register')
      },
      logout(){
        //因为无法销毁服务端的token，所以采取在前端删除token，然后刷新页面
        this.$cookies.remove('Authorization')
        location.reload()
      },
      goByRole(){
        if(this.$store.getters.userInfo.role===0){
          this.$router.push('/user')
        }
        if(this.$store.getters.userInfo.role===1){
          this.$router.push('/apply')
        }
        if([2,3,4,5].indexOf(this.$store.getters.userInfo.role)>=0){
          this.$router.push('/background/')
        }
      },
      searchKey(){
        if(this.input!==''){
          this.$router.push({path:'/searchList',query:{key:this.input}})
        }else {
          this.$message.warning('请输入关键字')
        }
      },
      go(item){
        this.$router.push({path:'/display',query:{aid:item}})
        if(this.$route.path==='/display'){
          location.reload()
        }
      }
    }
  }
</script>

<style scoped>
  .Search-div{
    width: 100%;
    height: 130px;
    /*margin-top: 35px;*/
    /*box-shadow: 0 -1px 10px rgba(100,100,100,.4);*/
    background-color: #ffffff;
    /*border: 1px solid red;*/

  }
  .search-input{
    width: 495px;
    height: 40px;
    display: flex;
    margin-left: 80px;
    /*border: 2px solid red;*/
  }
  .Search-div img{
    height: 90px;
  }
  .search-children{
    display: flex;
    width: 1080px;
    height: 120px;
    margin: 0 auto;
    align-items: center;
    position: relative;
    /*justify-content: space-between;*/
    /*border:1px solid red;*/
  }


  .hot_search{
    position: absolute;
    bottom: 15px;
    left: 29%;
    color: #8c8c8c;
    font-size: 14px;
  }
  .hot_search span::after{
    content: '|';
  }

  .zhuye{
    position: absolute;
    left: 960px;
    bottom: 30px;
    width: 180px;
    height: 80px;
  }
  .touxiang{
    margin: 0 auto;
    width: 60px;
    height: 60px;
    border-radius: 50%;
    overflow: hidden;
    /*border: 1px solid #666666;*/
  }
  .touxiang img{
    position: relative;
    top: -15px;
  }
  .el-link{
    color: #8c8c8c;
  }
  .name{
    position: absolute;
    width: 50px;
    height: 20px;
    overflow: hidden;
  }
</style>

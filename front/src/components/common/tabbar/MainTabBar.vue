<template>
  <div>
    <tab-bar>
        <tab-bar-item>
          <span slot="home-back">
            <a href="/home" class="home-back">
              <img src="~assets/img/other/home.svg" alt="" style="width: 16px;height: 16px;">
              &nbsp;&nbsp;<span>乐游首页</span>
            </a>
          </span>
          <div slot="local">
            <el-row>
              <el-col :span="4">
                <el-popover placement="top-start" width="180" trigger="click" v-if="city===''">
                  <el-row>
                    <el-col :span="24" style="font-size: 16px;color: #a3a3a3;font-family: PingFang SC;margin-bottom: 10px">热门城市</el-col>
                    <el-row :key="i" :gutter="20">
                      <el-col :span="8" v-for="k in citys" :key="k" style="margin-bottom: 7px;">
                        <el-link :underline="false" @click="changeCity(k)">{{k}}</el-link></el-col></el-row>
                  </el-row>
                  <el-link :underline="false" slot="reference" style="color: #474747;font-size: 12px">
                    <icon-svg icon-class="dingwei1" style="font-size: 16px"/>&nbsp;定位城市&nbsp;<i class="el-icon-arrow-down"/>
                  </el-link>
                </el-popover>
                <el-link :underline="false" v-else @click="city=''" style="font-size: 12px"><icon-svg icon-class="dingwei1" style="font-size: 16px"/>&nbsp;{{city}}</el-link>
              </el-col>
              <el-col :span="20">&nbsp;</el-col>
            </el-row>
          </div>

          <span slot="login-info">
            <div v-if="!userInfo" class="login-info">
               <a href="/login" id="hello_login" target="_blank">你好，请先登录</a>
              <a href="/register" style="color: #409eff" target="_blank">立即注册</a>
            </div>
            <div v-else class="login-info">
              <div>&nbsp;</div>
              <div><el-link :underline="false" type="primary" style="font-size: 12px">欢迎您：{{userInfo.name}}</el-link></div>
            </div>
          </span>

          <span slot="person-info" class="person-info">
            |
            <el-dropdown>
              <span class="el-dropdown-link">
                我的乐游<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item v-for="item in users" :key="item"><span @click="go(item.link)">{{item.name}}</span></el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <el-dropdown>
              <span class="el-dropdown-link">
                商务合作<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item v-for="item in customers" :key="item"><span @click="go(item.link)">{{item.name}}</span></el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <el-dropdown>
              <span class="el-dropdown-link">
                帮助中心<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown" >
                <el-dropdown-item v-for="item in helps" :key="item"><el-link :underline="false" @click="go(item.link)">{{item.name}}</el-link></el-dropdown-item>
                <el-dropdown-item disabled >人工服务</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <el-dropdown>
              <span class="el-dropdown-link">
                手机APP<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item><el-link :underline="false" @click="go('/download')">下载手机APP</el-link></el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </span>
        </tab-bar-item>
    </tab-bar>
    <search/>
  </div>
</template>

<script>
  import TabBar from "./TabBar";
  import TabBarItem from "./TabBarItem";
  import Search from "../search/Search";
  import {baseURL} from "../../../utils/constant";
  export default {
    name: "MainTabBar",
    data(){
      return{
        city:'',
        helps:[{name:'网站导航',link:''},{name:'乐游规则',link:''},{name:'投诉中心',link:'/complaint'}],
        customers:[{name:'我想合作',link:'/apply'}],
        users:[{name:'我的订单',link:'/user/order'},{name:'我的收藏',link: '/user/collect'},{name:'我的文章',link: '/user/article'},{name:'我的投诉',link: '/user/complaint'}],
        // userInfo:this.$store.state.userInfo
        citys:['宜昌','襄阳','武汉','十堰','洛阳','西安'],
        info:'',
        area:[]
      }
    },
    computed:{
      userInfo(){
        return this.$store.state.userInfos
      }
    },
    components:{
      TabBarItem,
      TabBar,
      Search
    },
    created() {
      // console.log(this.userInfo)
      // this.$axios.get(baseURL+'/getRole').then(res=>{
      //   // console.log(res)
      //   // console.log(res.data)
      //   // if(res.data.code==='401'){
      //   //   this.$message.error('出错了，请重新登录')
      //   // }
      //   // if(res.data.code==='200'){
      //   //   this.$store.commit('SET_USER',res.data.userInfo)
      //   // }
      //   // console.log(this.userInfo)
      // })
    },
    methods:{
      go(link){
        let routeUrl = this.$router.resolve({
          path:link
        })
        window.open(routeUrl.href,'_blank')
      },
      changeCity(item){
        this.city=item
        if(this.$route.path==='/home'){
          this.$axios.get(baseURL+'/getLocalArea',{params:{city:item}}).then(res=>{
            if(res.data.length<3){
              let local=JSON.parse(JSON.stringify(this.$store.state.hotArea))
              this.$message.warning('该地景点太少')
              for(let i of res.data){
                let info={}
                info.city = String(i.area.site).indexOf('市')>=0?String(i.area.site).substring(String(i.area.site).indexOf('省')+1,String(i.area.site).indexOf('市'))+'市':String(i.area.site).substring(String(i.area.site).indexOf('省')+1,String(i.area.site).indexOf('州'))+'州'
                info.id = i.id
                info.aid = i.area.id
                info.site = i.area.site
                info.name = i.area.name
                info.click = i.click
                info.star = i.area.star
                info.price = i.area.price
                this.$axios.get(baseURL+"/getCollectAndOrderAndComment",{params:{aid:i.area.id}}).then(res=>{
                  info.comment = res.data[0]
                  info.order = res.data[1]
                  info.collect = res.data[2]
                  local.unshift(info)
                })
              }
              this.$store.commit('SET_HOTAREA1',local)
            }else {
              for(let i of res.data){
                let info={}
                info.city = String(i.area.site).indexOf('市')>=0?String(i.area.site).substring(String(i.area.site).indexOf('省')+1,String(i.area.site).indexOf('市'))+'市':String(i.area.site).substring(String(i.area.site).indexOf('省')+1,String(i.area.site).indexOf('州'))+'州'
                info.id = i.id
                info.aid = i.area.id
                info.site = i.area.site
                info.name = i.area.name
                info.click = i.click
                info.star = i.area.star
                info.price = i.area.price
                this.$axios.get(baseURL+"/getCollectAndOrderAndComment",{params:{aid:i.area.id}}).then(res=>{
                  info.comment = res.data[0]
                  info.order = res.data[1]
                  info.collect = res.data[2]
                  this.area.push(info)
                })
              }
              this.$store.commit('SET_HOTAREA1',this.area)
            }
          })

          this.$axios.get(baseURL+'/queryLocalArticle',{params:{city:item+'市'}}).then(res=>{
            let local=JSON.parse(JSON.stringify(this.$store.state.hotArticle))
            for(let i of res.data){
              let info={}
              info.title = i.title
              info.article = String(i.article).substring(0,200)
              info.uid=i.uid
              info.id=i.id
              info.good=i.good
              info.collect=i.collect
              info.city = i.city
              local.unshift(info)
            }
            this.$store.commit('SET_HOTARTICLE',local)
          })
        }
      }
    }
  }
</script>

<style scoped>
  .home-back{
    display: flex;
    align-items: center;
  }
  .login-info{
    /*border: 1px solid red;*/
    display: flex;
    justify-content: space-evenly;
  }
  .el-dropdown-link {
    cursor: pointer;
    font-size: 12px;
    color: var(--color-text);
  }
  .el-icon-arrow-down {
    font-size: 12px;
  }
  .person-info{
    display: flex;
    justify-content: space-between;
  }
  .home-back span:hover{
    color: #409eff;
  }
  #hello_login:hover{
    color: #409eff;
  }
  .local{
    position: relative;
    left: 270px;
    bottom: 130px;
  }
  .el-dropdown-link img{
    /*border: 1px solid red;*/
    height: 20px;
  }
  .el-dropdown-links{
    width: 106px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 12px;
    /*border: 1px solid red*/
  }
</style>

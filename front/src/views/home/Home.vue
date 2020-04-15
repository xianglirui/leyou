<template>
  <div class="home">
    <main-tab-bar/>
    <div class="opk">
      <div style="width: 100%;height: 2px;background-color: #409fff"></div>
      <el-carousel :interval="5000" arrow="never" height="530px">
        <el-carousel-item v-for="(item,index) in imgs" :key="item">
          <a :href="'/display?aid='+Number(index+1)" title="数据量太少，找的网图先充当一下门面">
            <img :src="item" class="fill" style="width: 100%;"/> </a>
        </el-carousel-item>
      </el-carousel>
    </div>
    <heads/>
    <world/>
    <located/>
    <article_more/>
    <local/>
    <el-backtop :visibilityHeight="900"/>
    <foot/>

  </div>
</template>

<script>
import MainTabBar from "../../components/common/tabbar/MainTabBar";
import world from "./world";
import located from "./hello";
import heads from "./heads";
import Foot from "../../components/common/footer/Footer";
import article_more from "./article_more";
import local from "./local";
import BackTop from "../../components/content/backTop/BackTop";
import {baseURL} from "../../utils/constant";


export default {
  name: 'Home',
  components:{
    MainTabBar,
    world,
    located,
    heads,
    Foot,
    article_more,
    local,
    BackTop
  },
  data(){
    return{
      imgs:[
        'https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg',
        'https://fuss10.elemecdn.com/1/34/19aa98b1fcb2781c4fba33d850549jpeg.jpeg',
        'https://fuss10.elemecdn.com/0/6f/e35ff375812e6b0020b6b4e8f9583jpeg.jpeg',
        'https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg',
        'https://fuss10.elemecdn.com/d/e6/c4d93a3805b3ce3f323f7974e6f78jpeg.jpeg',
        'https://fuss10.elemecdn.com/3/28/bbf893f792f03a54408b3b7a7ebf0jpeg.jpeg',
        'https://fuss10.elemecdn.com/2/11/6535bcfb26e4c79b48ddde44f4b6fjpeg.jpeg'
      ],
      area:[]
    }
  },
  computed:{
    userInfo(){
      return this.$store.state.userInfos

    }
  },
  methods:{
    df(){
      this.$cookies.get('Token')
    }
  },
  created() {
    this.$axios.get(baseURL+'/getHotPlace').then(res=>{
      for(let i of res.data){
        let info={}
        info.city = String(i.area.site).indexOf('市')>=0?String(i.area.site).substring(String(i.area.site).indexOf('省')+1,String(i.area.site).indexOf('市'))+'市':String(i.area.site).substring(String(i.area.site).indexOf('省')+1,String(i.area.site).indexOf('州'))+'州'
        info.id = i.id
        info.aid = i.area.id
        info.name = i.area.name
        info.site = i.area.site
        info.click = i.click
        info.price = i.area.price
        info.star = i.area.star
        this.$axios.get(baseURL+"/getCollectAndOrderAndComment",{params:{aid:i.area.id}}).then(res=>{
          info.comment = res.data[0]
          info.order = res.data[1]
          info.collect = res.data[2]
          this.area.push(info)
        })
      }
      this.$store.commit('SET_HOTAREA',this.area)
      this.$store.commit('SET_HOTAREA1',this.area)
    })
    this.$axios.get(baseURL+'/queryLocalArticleWithoutCity').then(res=>{
      let article=[]
      for(let i of res.data){
        let info={}
        info.title = i.title
        info.article = String(i.article).substring(0,200)
        info.uid=i.uid
        info.id=i.id
        info.good=i.good
        info.collect=i.collect
        info.city = i.city
        article.push(info)
      }
      this.$store.commit('SET_HOTARTICLE',article)
    })
  }
};
</script>

<style scoped>
  .opk{
    width: 100%;
    height: 530px;
    position: relative;
    /*top: -8px;*/
  }


</style>

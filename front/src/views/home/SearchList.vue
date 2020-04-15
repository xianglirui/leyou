<template>
  <div>
    <main-tab-bar/>
    <div style="margin: 30px"></div>
    <el-card shadow="never" style="width: 85%;margin: 0 auto;min-height: 45vh">
      <div>
        <div style="font-weight: bold;margin-bottom: 30px">搜索景区结果：</div>
        <el-row v-for="(item,index) in AreaListByKey" :key="index" style="margin-bottom: 40px">
          <el-col :span="2" style="position: relative;top: 60px">景区序号:{{index+1}}</el-col>
          <el-col :span="5">
            <el-image src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg" style="width: 150px;height: 150px;"></el-image>
          </el-col>
          <el-col :span="12" style="position: relative;top: 10px">
            <el-col style="margin-top: 20px"><el-col :span="4">景区名:</el-col><el-col :span="18">{{item.area.name}}</el-col></el-col>
            <el-col style="margin-top: 20px"><el-col :span="4">景区地址:</el-col><el-col :span="18">{{item.area.site}}</el-col></el-col>
            <el-col style="margin-top: 20px"><el-col :span="4">国家A级:</el-col><el-col :span="18"><el-rate v-model="item.area.star" disabled show-score text-color="#ff9900" score-template="{value}">
            </el-rate></el-col></el-col>
          </el-col>
          <el-col :span="4" style="margin-top: 50px"><el-button type="primary" @click="$router.push({path:'/display',query:{aid:item.aid}})">去看看</el-button></el-col>
        </el-row>
        <div v-show="AreaListByKey.length===0" style="position:relative;left: 45%">
          <icon-svg icon-class="box-package-parcel-office-open-unpack-delivery-ebfdc"/>
          <span style="color: #8c8c8c;font-size: 14px">这里是空的</span>
        </div>
      </div>
      <el-divider/>
      <div>
        <div style="font-weight: bold;margin-bottom: 30px;margin-top: 30px">搜索游记结果：</div>
        <el-row v-for="(item,index) in ArticleListByKey" :key="index" v-if="ArticleListByKey.length!==0" style="margin-bottom: 40px">
          <el-col :span="2" style="position: relative;top: 60px">游记序号:{{index+1}}</el-col>
          <el-col :span="5">
            <el-image src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg" style="width: 150px;height: 150px;"></el-image>
          </el-col>
          <el-col :span="12" style="position: relative;">
            <el-col style="margin-top: 15px"><el-col :span="4">标题:</el-col><el-col :span="18">{{item.article.title}}</el-col></el-col>
            <el-col style="margin-top: 15px"><el-col :span="4">地点:</el-col><el-col :span="18">{{item.article.city}}</el-col></el-col>
            <el-col style="margin-top: 15px"><el-col :span="4">收藏:</el-col><el-col :span="18">{{item.article.collect}}</el-col></el-col>
            <el-col style="margin-top: 15px"><el-col :span="4">点赞:</el-col><el-col :span="18">{{item.article.good}}</el-col></el-col>
          </el-col>
          <el-col :span="4" style="margin-top: 50px"><el-button type="primary" @click="$router.push({path:'/travel',query:{id:item.tid}})">去看看</el-button></el-col>
        </el-row>
        <div v-show="ArticleListByKey.length===0" style="position:relative;left: 45%">
          <icon-svg icon-class="box-package-parcel-office-open-unpack-delivery-ebfdc"/>
          <span style="color: #8c8c8c;font-size: 14px">这里是空的</span>
        </div>
      </div>
    </el-card>
    <foot/>
  </div>
</template>

<script>
  import MainTabBar from "../../components/common/tabbar/MainTabBar";
  import Foot from "../../components/common/footer/Footer";
  import {baseURL} from "../../utils/constant";
  export default {
    name: "SearchList",
    components:{MainTabBar,Foot},
    data(){
      return{
        AreaListByKey:[],
        ArticleListByKey:[],
        key:this.$route.query.key
      }
    },
    created() {
      this.$axios.get(baseURL+'/SearchKey',{params:{name:this.$route.query.key}}).then(res=>{
        if(res.data.length===0){
          this.$message.warning('无数据，换个关键词试试')
        }else {
          this.AreaListByKey = res.data.filter(v=>{return v.aid!==0})
          this.ArticleListByKey = res.data.filter(v=>{return v.tid!==0})
        }
      })
    },
    watch:{
      $route: {
        handler() {
          this.ArticleListByKey=[]
          this.AreaListByKey=[]
          this.$axios.get(baseURL+'/SearchKey',{params:{name:this.$route.query.key}}).then(res=>{
            if(res.data.length===0){
              this.$message.warning('无数据，换个关键词试试')
            }else {
              this.AreaListByKey = res.data.filter(v=>{return v.aid!==0})
              this.ArticleListByKey = res.data.filter(v=>{return v.tid!==0})
            }
          })
          }
        },
    }
  }
</script>

<style scoped>

</style>

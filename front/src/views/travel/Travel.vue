<template>
  <div class="row">
    <main-tab-bar/>
    <div class="col">
      <el-row class="fix">
        <el-col :span="24">
          <el-button circle @click.once="zan" :style="css_good?{'color':'#409eff'}:''"><icon-svg icon-class="dianzan1" style="font-size: 20px;"/></el-button>
        </el-col>
        <el-col :span="24" class="txt">{{article.good}}赞</el-col>
        <el-col :span="24"><el-button circle @click.once="collect" :style="css_collect?{'color':'#ff3d1b'}:''"><icon-svg icon-class="aixin_shixin" style="font-size: 20px"/></el-button></el-col>
        <el-col :span="24" class="txt">收藏</el-col>
      </el-row>
      <el-row style="margin: 20px auto;width: 80%;">
        <el-col :span="18">
          <el-card shadow="never" class="card">
            <el-row>
              <el-col :span="24">
                <div style="font-size: 35px;font-weight: bold;margin-bottom: 30px">{{article.title}}</div>
              </el-col>
              <el-col :span="2"><el-avatar :size="70" :src="circleUrl"></el-avatar></el-col>
              <el-col :span="20" class="cont">
                <span style="margin-right: 10px">{{article.user.name}}</span>
                <el-tooltip class="item" effect="dark" :content="'乐游'+call(article.collect)+'作家'" placement="top">
                  <span><icon-svg icon-class="dengji" :style="style(article.collect)"/>{{call(article.collect)}}</span>
                </el-tooltip>
                <span style="margin-left: 50px;"><icon-svg icon-class="dingwei1"/>&nbsp;<span style="font-size: 14px;color: #8c8c8c">{{article.city}}</span></span>
              </el-col>
              <el-col :span="20" class="ins">
                <span>{{article.times}}</span>
                <span style="margin-left: 5px">浏览：{{article.click}}</span>
                <span style="margin-left: 5px">收藏：{{article.collect}}</span>
              </el-col>
            </el-row>
            <el-row style="padding: 20px;margin-top: 10px">
              <el-col>
               <div v-html="article.article"></div>
              </el-col>
            </el-row>
          </el-card>
          <el-card shadow="never" style="margin-top: 15px">
            <div style="position: relative;">
              <el-avatar :size="50" :src="circleUrl" style="position: absolute;"></el-avatar>
              <div style="width: 92%;margin-left: 70px">
                <el-input type="textarea" id="text" resize="none" placeholder="写下你的评论……" v-model="txt" :autosize="{ minRows: 4, maxRows: 6}" maxlength="100" show-word-limit></el-input>
              </div>
              <transition name="el-fade-in-linear">
                <div style="margin-left: 78%;margin-top: 20px" v-show="txt!==''">
                  <div v-if="$store.getters.userInfo">
                    <el-button round type="primary" size="small" style="width: 80px" @click="sub">发布</el-button>
                    <el-button round plain size="small" style="width: 80px" @click="txt=''">取消</el-button>
                  </div>
                  <div v-else><el-tag effect="dark"><span style="position: relative;top: 5px">登录后才能发表评论哦</span></el-tag></div>
                </div>
              </transition>

            </div>
            <el-divider/>
            <commentlist :travel="true"/>
          </el-card>
        </el-col>

        <el-col :span="6" class="auth">
          <el-link :underline="false" type="warning" @click="$router.push({path:'/complaint',query:{travel:'bad'}})" style="position:relative;left: 70%">
            <i class="el-icon-position"/><span>我要投诉</span>
          </el-link>
          <el-card shadow="never">
            <el-row>
              <el-col :span="8">
                <el-avatar :size="50" :src="circleUrl"></el-avatar>
              </el-col>
              <el-col :span="14">
                {{article.user.name}}
                <el-tooltip class="item" effect="dark" :content="'乐游'+call(article.collect)+'作家'" placement="top" style="font-size: 14px;margin-left: 5px">
                  <span><icon-svg icon-class="dengji" :style="style(article.collect)"/>{{call(article.collect)}}</span>
                </el-tooltip>
              </el-col>
              <el-col :span="14"><span style="color: #8a8a8a;font-size:12px;">他的其他文章<i class="el-icon-arrow-down"/> </span></el-col>
            </el-row>
            <el-divider/>
            <el-row v-for="item in list" :key="item" style="margin-bottom: 20px">
              <div style="display: flex;justify-content: center">
                <el-link :underline="false" :href="'/travel?id='+item.id">{{item.title}}</el-link>
              </div>
            </el-row>
          </el-card>
        </el-col>
      </el-row>
<!--      <el-button circle class="totop" icon="el-icon-caret-top"></el-button>-->
<!--      <back-top :scrollDom=".content-container"></back-top>-->
      <el-backtop class="gotop" :visibilityHeight="50"></el-backtop>
    </div>
    <foot id="fo"/>
  </div>
</template>

<script>
  import MainTabBar from "../../components/common/tabbar/MainTabBar";
  import Foot from "../../components/common/footer/Footer";
  import Commentlist from "../../components/common/comment/Commentlist";
  import {baseURL} from "../../utils/constant";
  import BackTop from "../../components/content/backTop/BackTop";
  export default {
    name: "Travel",
    components:{MainTabBar,Foot,Commentlist,BackTop},
    data(){
      return{
        circleUrl: this.$store.getters.userInfo?require('assets/img/other/头像 男孩.svg'):"https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
        id:this.$route.query.id,
        txt:'',
        article:'',
        list:[],
        css_good:false,
        css_collect:false,
        comment:[],
        ByTime:[]
      }
    },
    created() {
      // console.log(this.id)
      let _this = this
      this.$axios.get(baseURL+'/getArticle',{params:{id:this.id}}).then(res=>{

        //取出图片数组第一个
        let is = String(res.data.article.img).substring(1,String(res.data.article.img).length-1)
        let os = is.split('https')
        console.log('https'+os[0])
        console.log(os[1])
        console.log(os[2])
        console.log(os[3])



        if(res.data.err){
          this.$router.push('/err')
        }else {
          // console.log(res.data)
           _this.article = res.data.article
           this.$axios.get(baseURL+'/article_Lists',{params:{id:_this.article.user.id}}).then(res=>{
            _this.list = res.data.list
          })
          this.$axios.get(baseURL+'/selectCollect',{params:{uid:this.$store.getters.userInfo.id,tid:this.article.id}}).then(res=>{
            if(!res.data.err){
              _this.css_collect = _this.css_good = true
            }
          })
        }
      })
      // this.$axios.get(baseURL+'/getCommentByTid',{params:{tid:this.$route.query.id}}).then(res=>{
      //   // console.log(res.data)
      //   this.comment=JSON.parse(JSON.stringify(res.data))
      //   this.ByTime=JSON.parse(JSON.stringify(res.data))
      //   this.comment.sort((a,b)=>{
      //     return a.star < b.star ? 1 : -1;
      //   })
      //   this.ByTime.sort((a,b)=>{
      //     return a.times > b.times ? 1 : -1;
      //   })
      // })
      this.$axios.get(baseURL+'/addRedisClick',{params:{tid:this.$route.query.id}})
    },
    methods:{
      zan(){
        let _this = this
        if(!this.css_good){
          this.$axios.get(baseURL+'/updateZan',{params:{uid:this.$store.getters.userInfo.id,tid:this.article.id,good:this.article.good}}).then(res=>{
            if(res.data==='ok'){
              _this.article.good+=1
              _this.css_good=true
            }
          })
        }
      },
      collect(){
        let _this = this
        if(!this.css_collect){
          this.$axios.get(baseURL+'/updateCollect',{params:{uid:this.$store.getters.userInfo.id,tid:this.article.id,collect:this.article.collect}}).then(res=>{
            if(res.data==='ok'){
              _this.article.collect+=1
              _this.css_collect=true
            }
          })
        }
      },
      sub(){
        this.$axios.get(baseURL+'/addCommentWithTravel',{params:{uid:this.$store.getters.userInfo.id,tid:this.$route.query.id,html:this.txt}}).then(res=>{
          if(res.data==='ok'){
            this.$message.success('提交成功')
          }
        })
      },
      style(item){
        if(item<=10){
          return {'color':'#167a14'}
        }
        if(item>10&&item<=30){
          return {'color':'#bfbfbf'}
        }
        if(item>30&&item<=50){
          return {'color':'#ffcb20'}
        }
        if(item>50){
          return {'color':'#7a187a'}
        }
      },
      call(item){
        if(item<=10){
          return '青铜'
        }
        if(item>10&&item<=30){
          return '白银'
        }
        if(item>30&&item<=50){
          return '黄金'
        }
        if(item>50){
          return '紫微'
        }
      }
    }
  }
</script>

<style scoped>
  *{
    line-height: 150%;
  }
  .row{
    width: 100%;
    background-color: #fafafa;
  }
  .col{

  }
  .fix{
    position: fixed;
    left: 30px;
    top: 30vh;
  }
  .fix .el-col .el-button{
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    width: 60px;height: 60px
  }
  .txt{
    color: #a3a3a3;
    margin-top: 15px;
    margin-bottom: 25px;
    margin-left: 12px;
  }
  .card{
    padding: 15px;
  }
  .cont{
    padding-left: 10px;
    margin-top: 12px;
    margin-bottom: 3px;

  }
  .ins{
    color: #8a8a8a;
    font-size: 14px;
    padding-left: 10px;
  }
  .auth{
    position: relative;left:10px;
  }
  #text{
    border: 2px solid #8a8a8a;
    /*border-radius: 8%;*/
    resize: none;
    width: 200px;
    height: 120px;
    padding: 10px;
  }
  .totop{
    height: 50px;width: 50px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    color: #409eff;
    position: fixed;
    left: 80%;
    bottom: 200px;
  }
  .gotop{
    height: 50px;width: 50px;
  }
</style>

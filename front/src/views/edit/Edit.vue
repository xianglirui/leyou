<template>
  <div class="row">
    <el-row>
      <el-col :span="4">
        <el-menu
          class="el-menu-vertical-demo"
          background-color="#545c64"
          text-color="#fff"
          default-active="2"
          active-text-color="#ffd04b">
          <el-button type="danger" round style="width:70%;margin-top: 50px;margin-left: 15%;margin-bottom: 30px" @click="$router.push('/user/article/travel_notes')">
            <span style="font-size: 18px">回首页</span></el-button>

          <el-menu-item index="3" @click="isShow=true">
            <i class="el-icon-circle-plus-outline"></i>
            <span slot="title" style="font-size: 18px">新建文章</span>
          </el-menu-item>
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-s-tools"></i>
              <span style="font-size: 18px" @click="his">历史文章</span>
            </template>
            <el-menu-item-group>
              <el-container>
                <el-main style="height: 65vh;">
                  <el-menu-item :index="index" class="content" v-for="(item,index) in article_list" :key="index" @click="addQuery(item)">
                    <div class="txt">
                      <i class="el-icon-s-claim"></i>
                      <span class="ios">{{item.title}}</span>
                    </div>
                    <div class="zisu">{{item.times}}</div>
                  </el-menu-item>
                </el-main>
              </el-container>

            </el-menu-item-group>
          </el-submenu>
        </el-menu>
      </el-col>
      <el-col :span="20">
<!--        <simple-m-d-e id="contentEditor" ref="contentEditor" v-model="content"  :zIndex="20"   style="height: 100vh"/>-->
        <div v-if="isShow">
          <el-input placeholder="在这写标题" v-model="title"></el-input>
          <vue-simplemde v-model="content" ref="markdownEditor" class="simplemde-container"/>
          <div style="position: relative;top: -10px">
            <el-input v-model="city" placeholder="请输入城市" style="width: 130px;position:relative;left: 20%"></el-input>
            <el-checkbox v-model="check" style="position: relative;left: 25%">禁止转载</el-checkbox>
            <el-button type="primary" style="position: relative;left: 28%" @click="sub" v-if="!iSEdit">发布文章</el-button>
            <el-button type="primary" style="position: relative;left: 28%" @click="edit" v-else>修改文章</el-button>
          </div>
        </div>
        <div v-else>
          <div style="background-color: #e1e1e1;width: 100%;height: 100vh">
            <div style="position: absolute;top: 45%;left: 55%;font-size: 60px;color: #8a8a8a">乐游</div>
          </div>
        </div>
      </el-col>
    </el-row>

  </div>
</template>

<script>
  import SimpleMDE from "../../components/common/SimpleMDE/SimpleMDE";
  import MainTabBar from "../../components/common/tabbar/MainTabBar";
  import VueSimplemde  from 'vue-simplemde'
  import {baseURL} from "../../utils/constant";
  import cheerio from 'cheerio'
  export default {
    name: "Edit",
    components:{SimpleMDE,MainTabBar,VueSimplemde},
    data(){
      return{
        content: '',
        html: '',
        check:true,
        isShow:false,
        title:'',
        article_list:[],
        mode:true,
        city:'',
        iSEdit:false,
        id:''
      }
    },
    watch:{

    },
    methods:{
      sub(){
        if(this.content===''||this.title===''){
          this.$message.warning('请输入标题和内容')
        }else if(this.city===''){
          this.$message.warning('请输入城市')
        }else {
          import('showdown').then(showdown => {
            const converter = new showdown.Converter()
            this.html = converter.makeHtml(this.content)

            const $ = cheerio.load(this.html)
            let list = []
            $("img").each((i,e)=>{
              list[i] = $(e).attr("src")
            })
            this.$axios.post(baseURL+'/subEdit',{html:this.html,title:this.title,check:this.check,uid:this.$store.getters.userInfo.id,city:this.city,list:list}).then(res=>{
              if (res.data==='ok'){
                this.$message.success('提交成功')
              }
            })
          })

        }
      },
      edit(){
        if(this.content===''||this.title===''){
          this.$message.warning('请输入标题和内容')
        }else if(this.city===''){
          this.$message.warning('请输入城市')
        }else {
          import('showdown').then(showdown => {
            const converter = new showdown.Converter()
            this.html = converter.makeHtml(this.content)

            const $ = cheerio.load(this.html)
            let list = []
            $("img").each((i,e)=>{
              list[i] = $(e).attr("src")
            })
            this.$axios.post(baseURL+'/articleEdit',{html:this.html,title:this.title,check:this.check,uid:this.$store.getters.userInfo.id,city:this.city,list:list,id:this.id}).then(res=>{
              if (res.data==='ok'){
                this.$message.success('修改成功')
              }
            })
          })

        }

      },
      his(){
        let _this = this
        this.$axios.get(baseURL+'/article_Lists',{params:{id:this.$store.getters.userInfo.id}}).then(res=>{
          // console.log(res.data.list)
          _this.article_list = res.data.list
        })
      },
      addQuery(item){
        this.content=''
        this.id=item.id
        this.isShow=true
        this.iSEdit=true
        this.title = item.title
        this.content = item.article
        this.check = item.check === 0;
      }
    }
  }
</script>

<style scoped>
  .row{
    height: 100vh;
  }
  .el-menu{
   height: 100vh;

  }
  .content{
    width: 100%;
    height: 70px;

  }
  .zisu{
    position: absolute;
    top: 25px;
    font-size: 12px;
    color: #ffffff;
  }
  .txt{
    position: absolute;
    left: 10px;
    text-overflow: ellipsis;
    overflow: hidden;
    width: 90%;
  }
  .ios{
    font-size: 16px;

  }
  .simplemde-container>>>.CodeMirror {
    height: 78vh;
    /*line-height: 20px;*/
  }
</style>

<template>
  <div class="row">
    <el-card shadow="never" class="card">
      <el-row>
        <el-col :span="16">
          <div class="essay">
            <el-row v-for="(item,index) in articleList" :key="index">
              <el-col :span="8">
                <img :src="imgs[index]" alt="" width="100%"/>
              </el-col>
              <el-col :span="14" style="margin-left: 25px">
                <div class="contents">
                  <el-link :underline="false" style="font-weight: bolder;font-size: 18px" @click="$router.push({path:'travel',query:{id:item.id}})">{{item.title}}</el-link>
                  <div style="margin: 20px"></div>
                  <div class="intro" >{{item.article}}</div>
                  <div class="bottom_info">
                    <div class="bottom_kid">
                      <icon-svg icon-class="dingwei"/>
                      <el-link :underline="false">{{item.city}}</el-link>
                    </div>
                    <div class="bottom_kid" style="width: 150px">
                      <p>by</p><div class="yuan"><img src="./b3.jpg" alt=""/></div>
                      <el-link type="primary" :underline="false">作者编号：{{item.uid}}</el-link>
                    </div>
                    <div class="bottom_kid" style="width: 150px">
                      <div class="kid_kid">
                        <img src="~assets/images/svg/留言.svg" alt="" width="18px" height="18px"/>
                        <p>&nbsp;{{commentList[index]}}</p>
                      </div>
                      <div class="kid_kid">
                        <img src="~assets/images/svg/爱心1.svg" alt="" width="17px" height="17px"/>
                        <p>&nbsp;{{item.collect}}</p>
                      </div>
                    </div>
                  </div>
                </div>
              </el-col>
            </el-row>
            <el-pagination layout="prev, pager, next"
                           :current-page="pageConf.pageCode" @current-change="handleCurrentChange"
                           :page-size="pageConf.pageSize"
                           :total="pageConf.totalPage" v-show="pageConf.totalPage>=3"></el-pagination>
          </div>
        </el-col>
        <el-col :span="1">&nbsp;</el-col>
        <el-col :span="7"  v-loading="loading">
          <div class="recommend">
            <div style="display: flex;justify-content: space-between;align-items: center">
              <div><p>推荐作者</p></div>
              <div style="width: 80px;height: 30px; display: flex;align-items: center;justify-content: space-evenly">
                <img src="~assets/images/svg/换一批.svg" alt="" width="16px" height="16px"/>
                <el-link :underline="false">换一批</el-link>
              </div>
            </div>
            <el-row v-for="index in 5" :key="index">
              <el-col :span="24" style="margin-top: 20px;display: flex;align-items: center;justify-content: space-between">
                <div class="author">
                  <div class="yuan" style="width: 50px;height: 50px;">
                    <img src="./b3.jpg" alt=""/>
                  </div>
                  <div>
                    <p style="color: #000000">{{names[index-1]}}</p>
                    <div style="margin:7px"></div>
                    <p style="font-size: 12px">写了62篇游记 · 12.0k喜欢</p>
                  </div>
                </div>

                <el-link :underline="false"  @click="loading=true">
                  <div style="display: flex;align-items: center;width: 60px;;justify-content: center">
                    <img src="~assets/images/svg/关注.svg" alt="" width="20px"/>
                    <p style="color: #87c38d">&nbsp;关注</p>
                  </div>
                </el-link>

              </el-col>
            </el-row>
          </div>
        </el-col>
      </el-row>


    </el-card>

  </div>
</template>

<script>
  import {baseURL} from "../../utils/constant";

  export default {
    name: "articles",
    data(){
      return{
        currentPage1: 5,
        imgs:[
          require('./b1.jpg'),
          require('./b2.jpg'),
          require('./b3.jpg'),
        ],
        loading:false,
        names:['荠麦青青','厌言兵','清角吹寒','二十四桥冷月', '红药','十里长堤醉春风'],
        pageConf: {
          //设置一些初始值(会被覆盖)
          pageCode: 1, //当前页
          pageSize: 3, //每页显示的记录数
          totalPage: 0, //总记录数
          pageOption: [4, 10, 20], //分页选项
        },
        articleList:[],
        commentList:[]
      }
    },
    created() {
      this.$axios.get(baseURL+'/getArticleListByPage',{params:{pageCode:1}}).then(res=>{
        this.pageConf.totalPage=res.data.total;
        this.articleList = res.data.rows
        let idList=[]
        for(let i of this.articleList){
          idList.push(i.id)
        }
        this.$axios.post(baseURL+'/getArticleComment',idList).then(res=>{
          this.commentList = res.data
        })
      })
    },
    methods:{
      handleCurrentChange(val){
        this.$axios.get(baseURL+'/getArticleListByPage',{params:{pageCode:val}}).then(res=>{
          this.articleList = res.data.rows
          let idList=[]
          for(let i of this.articleList){
            idList.push(i.id)
          }
          this.$axios.post(baseURL+'/getArticleComment',idList).then(res=>{
            this.commentList = res.data
          })
        })
      }
    }
  }
</script>

<style scoped>
  *{
    color: #8c8c8c;
  }
  .row{
    width: 76%;
    margin: 0 auto;
    margin-top: -85px;
  }
  .el-row{
    margin-bottom: 30px;
    /*border: 1px solid red;*/
    position: relative;
  }
  .essay{

  }
  .intro{
    width: 100%;
    line-height: 120%;
    /* 文字分两段显示，超出显示框时，超出部分显示省略号 */
    display: -webkit-box;
    -webkit-line-clamp: 4;
    -webkit-box-orient: vertical;
    /* 文字超出后是否自动换行 */
    /* white-space: nowrap; */
    text-overflow: ellipsis;/* 文字溢出，显示省略号 */
    overflow: hidden;
  }
  .bottom_info{
    position: absolute;
    bottom: 4px;
    right: 9%;
    /*border: 1px solid red;*/
    width: 55%;
    height: 30px;
    display: flex;
    align-items: center;
  }
  .yuan{
    width: 20px;
    height: 20px;
    border-radius: 50%;
    border: 1px solid #8c8c8c;
    overflow: hidden;
  }
  .bottom_kid{
    display:flex;
    align-items:center;
    height: 100%;
    width: 100px;
    /*border: 1px solid red;*/
    justify-content: space-evenly;

  }
  .kid_kid{
    color: #666666;
    font-size: 12px;
    display: flex;
    align-items: center;

  }

  .recommend{
    /*border: 1px solid red;*/

    height: 600px;
  }
  .author{
    display: flex;
    align-items: center;
    width: 210px;
    justify-content: space-between;
  }


</style>

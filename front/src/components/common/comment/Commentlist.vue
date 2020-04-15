<template>
  <div class="all">
    <div class="row">
      <div class="top">
        <div>
          <span style="font-size: 20px">{{pageConf.totalPage}}条网友点评</span>
        </div>
        <div style="position: relative;top: 8px;right: 20px" v-show="$route.path==='/display'">
          <el-radio v-model="radio1" label="1">质量排序</el-radio>
          <el-radio v-model="radio1" label="2">时间排序</el-radio>
        </div>
      </div>
      <el-card shadow="never" id="mao" v-if="$route.path==='/display'">
<!--        <div class="check" v-show="radio1==='1'">-->
<!--          <el-checkbox v-model="checked" >有图&nbsp;{{ByTime.length}}</el-checkbox>-->
<!--          <el-radio-group v-model="radio" style="position: relative;top: 2px">-->
<!--            <el-radio :label="3">全部&nbsp;{{comment.length}}</el-radio>-->
<!--            <el-radio :label="6">好评&nbsp;{{hao.length}}</el-radio>-->
<!--            <el-radio :label="9">中评&nbsp;{{medium.length}}</el-radio>-->
<!--            <el-radio :label="12">差评&nbsp;{{cha.length}}</el-radio>-->
<!--          </el-radio-group>-->

<!--        </div>-->
        <div class="comment_content">
          <div v-for="index in timeOrGood.length" :key="index">
            <el-row>
              <el-col :span="3"><el-avatar :size="60" :src="circleUrl"></el-avatar></el-col>
              <el-col :span="16" style="position: relative;top: 5px">
                <div v-if="username===''">匿名用户</div>
                <div v-else>{{username}}</div>
                <div style="position:relative;top: 5px" v-show="timeOrGood[index-1].star"><el-rate v-model="timeOrGood[index-1].star" disabled  text-color="#ff9900" score-template="{value}"></el-rate></div>
              </el-col>
              <el-col :span="4">
                <div style="color: #8c8c8c;position: relative;left: 55px;top: 15px" v-html="timeOrGood[index-1].times"></div>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="3"><div>&nbsp;</div></el-col>
              <el-col :span="21">
                <div v-html="timeOrGood[index-1].content"></div>
                <div style="color: #409eff;margin-top: 15px"><span v-show="timeOrGood[index-1].back">商家回复：{{timeOrGood[index-1].back}}</span></div>
<!--                <div class="right">-->
<!--                  <el-link :underline="false" type="info" @click="zan">-->
<!--                    <icon-svg icon-class="dianzan" style="font-size: 16px"/>&nbsp;赞-->
<!--                  </el-link>-->
<!--                </div>-->
                <el-divider/>
              </el-col>
            </el-row>

          </div>
          <div style="width: 100%;height: 50px;display: flex;justify-content: center">
            <div style="position:relative;top: 10px">
              <el-pagination background layout="prev, pager, next"
                             :current-page="pageConf.pageCode" @current-change="handleCurrentChange"
                             :page-size="pageConf.pageSize"
                             :total="pageConf.totalPage" v-show="pageConf.totalPage>=3"></el-pagination>
            </div>
          </div>

        </div>
      </el-card>
      <div v-else>
        <div class="comment_content">
          <div v-for="index in timeOrGood.length" :key="index">
            <el-row>
              <el-col :span="3"><el-avatar :size="60" :src="circleUrl"></el-avatar></el-col>
              <el-col :span="14" style="position: relative;top: 5px">
                <div v-if="username===''">匿名用户</div>
                <div v-else>{{username}}</div>
                <div style="position:relative;top: 5px;color: #8c8c8c;font-size: 12px">{{timeOrGood[index-1].times}}</div>
              </el-col>
              <el-col :span="6">
                &nbsp;
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="3"><div>&nbsp;</div></el-col>
              <el-col :span="21">
                <div v-html="timeOrGood[index-1].content"></div>
                <el-divider/>
              </el-col>
            </el-row>
          </div>
          <div style="width: 100%;height: 50px;display: flex;justify-content: center">
            <div style="position:relative;top: 10px">
              <el-pagination background layout="prev, pager, next"
                             :current-page="pageConf.pageCode" @current-change="handleCurrentChange"
                             :page-size="pageConf.pageSize"
                             :total="pageConf.totalPage" v-show="pageConf.totalPage>=3"></el-pagination>
            </div>
          </div>

        </div>
      </div>
    </div>

  </div>
</template>

<script>
  import {baseURL} from "../../../utils/constant";

  export default {
    name: "Commentlist",
    props:{
      hao:{
        type:Array,
        default:()=>[]
      },
      cha:{
        type:Array,
        default:()=>[]
      },
      medium:{
        type:Array,
        default:()=>[]
      },
      display:{
        type:Boolean,
        default(){
          return false
        }
      },
      travel:{
        type:Boolean,
        default(){
          return false
        }
      }
    },
    data(){
      return{
        radio: 3,
        radio1:'1',
        checked:false,
        circleUrl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
        username:'',
        color:{
          color:'#e0620d'
        },
        comment: [],
        ByTime:[],
        pageConf: {
          //设置一些初始值(会被覆盖)
          pageCode: 1, //当前页
          pageSize: 3, //每页显示的记录数
          totalPage: 0, //总记录数
          pageOption: [4, 10, 20], //分页选项
        },
      }
    },
    created() {
      if(this.display){
        this.$axios.get(baseURL+'/dnf',{params:{aid:this.$route.query.aid}}).then(res=>{
          this.pageConf.totalPage=res.data.total;
          this.comment=JSON.parse(JSON.stringify(res.data.rows[0].comment))
          this.ByTime=JSON.parse(JSON.stringify(res.data.rows[0].comment))
          this.comment.sort((a,b)=>{
            return a.star < b.star ? 1 : -1;
          })
          this.ByTime.sort((a,b)=>{
            return a.times > b.times ? -1 : 1;
          })
          this.$emit('nums',this.pageConf.totalPage)
        })
      }
      if(this.travel){
        this.$axios.get(baseURL+'/getCommentByTid',{params:{pageCode:1,tid:this.$route.query.id}}).then(res=>{
          this.pageConf.totalPage=res.data.total;
          this.comment=JSON.parse(JSON.stringify(res.data.rows))
          console.log(this.comment)
        })
      }
    },
    watch:{

    },
    computed:{
      timeOrGood(){
        if(this.display){
          if(this.radio1==='1'){
            return this.comment
          }
          if(this.radio1==='2'){
            return this.ByTime
          }
        }
        if(this.travel){
          return this.comment
        }
      }
    },
    methods:{
      handleCurrentChange(val){
        if(this.display){
          this.$axios.get(baseURL+'/changeCurrentPage',{params:{pageCode:val,aid:this.$route.query.aid}}).then(res=>{
            this.comment=JSON.parse(JSON.stringify(res.data.rows[0].comment))
            this.ByTime=JSON.parse(JSON.stringify(res.data.rows[0].comment))
            this.comment.sort((a,b)=>{
              return a.star < b.star ? 1 : -1;
            })
            this.ByTime.sort((a,b)=>{
              return a.times > b.times ? -1 : 1;
            })
          })
        }
        if(this.travel){
          this.$axios.get(baseURL+'/getCommentByTid',{params:{pageCode:val,tid:this.$route.query.id}}).then(res=>{
            this.comment=JSON.parse(JSON.stringify(res.data.rows))
          })
        }
      }
    }
  }
</script>

<style scoped>
  .all{
    width: 100%;
    display: flex;
    justify-content: space-between;
    margin-top: 30px;
  }
  .row{
    width: 100%;
  }
  .col{
    width: 28%;
  }
  .top{
    width: 100%;
    display: flex;
    margin-bottom: 15px;
    justify-content: space-between;
  }
  .check{
    display: flex;
    width: 460px;
    justify-content: space-between;
  }
  .comment_content{
    margin-top: 30px;
  }
</style>

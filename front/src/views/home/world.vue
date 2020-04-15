<template>
  <div class="row">
    <el-row>
      <el-col :span="24">
        <div class="grid-content bg-purple-dark">
          <div style="width: 25px;height: 25px;"><img src="~assets/images/svg/fire.svg" /></div>
          <div style="margin-left: 8px;font: 20px bold 微软雅黑">热门景区推荐</div>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="30" v-for="num in 2" :key="num">
      <el-col :span="8" v-for="index in 3" :key="index">
        <div class="grid-content bg-purple">
          <div class="top">
            <img src="~assets/images/hot_2.png" width="100%" height="100%" v-if="index===1 && num===1"/>
            <img src="~assets/images/hot_1.png" width="100%" height="100%" v-else-if="index===2 && num===1"/>
            <img src="~assets/images/svg/commend.svg" width="85%" height="85%" v-else/>
          </div>
          <img src="~assets/images/images1/t5.png"  class="poto"/>
        </div>
        <div class="last">
          <p><el-link :underline="false" @click="$router.push({path:'/display',query:{aid:areas(num,index).aid}})">{{areas(num,index).name}}（{{areas(num,index).city}}）</el-link></p>
          <div class="btn_pannel" style="width: 120px">
            <el-row style="position: relative;top: 22px">
              <el-col :span="6" v-for="(item,flag) in evaluates" :key="flag">
                <el-tooltip class="item" effect="dark" :content="item.name+'：'+nums(num,index,flag)" placement="bottom">
                  <icon-svg :icon-class="item.url" :style="item.style"/>
                </el-tooltip></el-col>&nbsp;
            </el-row>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import {baseURL} from "../../utils/constant";

  export default {
    name: "world",
    data(){
      return{
        photo:[{imgURL:'', info:''},{},{},{},{},{}],
        //浏览收藏购买评论
        evaluates:[{url:'yanjing',name:'浏览',style:{'color':'#909399'},num:''},
          {url:'aixin_shixin',name:"收藏",style:{'color':'#f56c6c'},num:''},
          {url:'tubiaozhizuo-',name: '评论',style:{'color':'#409eff'},num:''},
          {url:'goumaijilu',name:'下单',style:{'color':'#67c23a'},num:''},
        ],
      }
    },
    computed:{
      area(){
        return this.$store.state.hotArea
      }
    },
    created() {

    },
    methods:{
      nums(num,index,flag){
        if(flag===0){
          return this.areas(num,index).click
        }
        if(flag===1){
          return this.areas(num,index).collect
        }
        if(flag===2){
          return this.areas(num,index).comment
        }
        if(flag===3){
          return this.areas(num,index).order
        }
      },
      areas(num,index){
        if(num===1&&index===1){
          return this.area[0]
        }
        if(num===1&&index===2){
          return this.area[1]
        }
        if(num===1&&index===3){
          return this.area[2]
        }
        if(num===2&&index===1){
          return this.area[3]
        }
        if(num===2&&index===2){
          return this.area[4]
        }
        if(num===2&&index===3){
          return this.area[5]
        }
      }
    }
  }
</script>

<style scoped>
  .bg-purple {
    /*background: #d3dce6;*/
    overflow: hidden;
  }
  .grid-content {
    min-height: 36px;
  }
  .grid-content img{
    width: 100%;
    transition: all 0.6s;/*图片放大过程的时间*/
    position: relative;
  }
  .poto:hover{
    transform:scale(1.08);
  }
  .row{
    width: 76%;
    margin: 50px auto;
    /*border: 1px solid red;*/
  }
  .el-row{
    margin-bottom: 30px;
  }
  .el-col{

  }
  .bg-purple-dark {
    /*background: #99a9bf;*/
    display: flex;
    align-items: center;
    justify-content: center;
    margin-bottom: -10px;
  }
  .last{
    border-radius: 8px;
    background-color: #e8eef1;
    margin-top: -9px;
    width: 100%;
    height: 60px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    /*border: 1px solid red;*/
  }
  .btn_pannel{
    position: relative;
    top: 6px;
    right: 15px;
  }
  .last p{
    position: relative;
    top: 2px;
    left: 15px;
  }
  .top{
    width: 60px;
    height: 88px;
    z-index: 99;
    position: absolute;
    top: -7px;
  }

</style>

<template>
  <div class="row">
    <el-row>
      <el-col :span="24">
        <div class="grid-content bg-purple-dark dark">
          <div style="width: 25px;height: 25px;"><img src="~assets/images/svg/fire.svg" width="100%"/></div>
          <div style="margin-left: 8px;font: 20px bold 微软雅黑"><p>本地热门</p></div>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="30">
      <el-col :span="8" v-for="(eva,num) in local" :key="num">
        <el-card class="grid-content bg-purple-dark">
          <div class="photo">
            <div class="poll">门票余数：充足</div>
            <img src="~assets/images/images1/hotels/1.jpg" alt="" width="100%"/>
          </div>
          <div style="margin: 8px"></div>
          <div class="info">
            <div>
              <strong>{{eva.name}}</strong>
              <div style="margin: 15px"></div>
              <div style="width: 200px;height: 40px">位置：{{eva.site}}</div>
              <div style="margin: 8px"></div>
              <p style="display: flex;align-items: center">
                评分：<el-rate v-model="eva.star" disabled show-score text-color="#ff9900" score-template="{value}"></el-rate>
              </p>
            </div>
            <div>
              <div style="display: flex;align-items: center;">
                <img src="~assets/images/svg/on_sale.svg" width="45px" height="45px"/>
                <p>优惠价</p>
              </div>
              <div style="color: #00bcd4;font:28px bold;">{{'￥'+eva.price}}</div>
            </div>
          </div>
          <div style="margin: 20px"></div>
          <div class="good">
            <div v-for="(item,index) in evaluates" :key="index">
              <icon-svg :icon-class="item.url" style="font-size: 14px"/>&nbsp;{{nums(num,index)}}
            </div>
          </div>
          <div style="margin: 20px"></div>
          <el-button type="primary" style="width: 100%;" @click="$router.push({path:'/display',query:{aid:eva.id}})">去看看</el-button>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import {baseURL} from "../../utils/constant";

  export default {
    name: "hello",
    data(){
      return{
        value: 3.7,
        evaluates:[{url:'yanjing',name:'浏览'},
          {url:'aixin_shixin',name:"收藏"},
          {url:'tubiaozhizuo-',name: '评论'},
          {url:'goumaijilu',name:'购买'},
        ],
      }
    },
    watch:{
    },
    created() {

    },
    computed:{
      local(){
        let info=[]
        info[0]=  this.$store.state.hotArea1[0]
        info[1]=  this.$store.state.hotArea1[1]
        info[2]=  this.$store.state.hotArea1[2]
        return info
      }
    },
    methods:{
      nums(num,flag){
        if(flag===0){
          return this.local[num].click
        }
        if(flag===1){
          return this.local[num].collect
        }
        if(flag===2){
          return this.local[num].comment
        }
        if(flag===3){
          return this.local[num].order
        }
      }
    }
  }
</script>

<style scoped>
  *{
    font-family: 微软雅黑;
  }
  .el-row {
    margin-bottom: 20px;
  }
  .row{
    width: 76%;
    margin: 30px auto;
  }
  .good{
    display: flex;
    justify-content: space-between;
    width: 100%;
  }
  .good div{
    display: flex;
    align-items: center;
    justify-content: center;
    color: #8c8c8c;
    font-size: 12px;
    border: 1px solid #c4c4c4;
    width: 70px;
    height: 30px;
  }
  .info{
    display: flex;
    justify-content: space-between;
  }
  .photo img{
    position: relative;
  }
  .poll{
    width: 118px;
    height: 30px;
    background-color: #00bcd4;
    color: white;
    position: absolute;
    z-index: 9;
    text-align: center;
    line-height: 30px;
  }
  .dark {
    /*background: #99a9bf;*/
    display: flex;
    align-items: center;
    justify-content: center;
    /*margin-bottom: -10px;*/
    /*height: 80px;*/
    /*background-color: #eee;*/
  }
</style>

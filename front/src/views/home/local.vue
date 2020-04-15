<template>
  <div class="row">
    <el-row>
      <el-col :span="24">
        <div class="grid-content bg-purple-dark">
          <div style="width: 25px;height: 25px;"><img src="~assets/images/svg/文章管理.svg" width="100%"/></div>
          <div style="margin-left: 8px;font: 20px bold 微软雅黑">本地游记</div>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="30">
      <el-col :span="8" v-for="(item,index) in article" :key="index">
        <div>
          <div style="overflow: hidden;height: 240px;">
            <img :src="ims[index]" alt="" width="100%"/>
          </div>
          <div style="margin: 18px"></div>
          <div>
            <div style="height: 90px">
              <el-link :underline="false" class="tilte" @click="$router.push({path:'travel',query:{id:item.id}})">{{item.title}}</el-link>
              <div style="margin: 12px"></div>
              <p class="intro">
                {{item.article}}
              </p>
            </div>
            <el-divider></el-divider>
            <div style="margin: -20px"></div>
            <div class="bottom_info">
              <div class="bottom_kid" style="width: 100px;">
                <icon-svg icon-class="dingwei"/>
                <el-link :underline="false">{{item.city}}</el-link>
              </div>
              <div class="bottom_kid" style="width: 150px;">
                <p>by</p><div class="yuan"><img src="./b3.jpg" alt=""/></div>
                <el-link type="primary" :underline="false">作者编号：{{item.uid}}</el-link>
              </div>
              <div class="bottom_kid">
                <div class="kid_kid">
                  <icon-svg icon-class="dianzan1" style="width: 17px;height:17px;"/>
                  <p>&nbsp;{{item.good}}</p>
                </div>
                <div class="kid_kid">
                  <img src="~assets/images/svg/爱心1.svg" alt="" width="17px" height="17px"/>
                  <p>&nbsp;{{item.collect}}</p>
                </div>
              </div>
            </div>
          </div>
          <div></div>
        </div>
      </el-col>
    </el-row>

  </div>
</template>

<script>
  export default {
    name: "local",
    data(){
      return{
        ims:[
          require('assets/images/image_1.jpg'),
          require('assets/images/image_2.jpg'),
          require('assets/images/image_3.jpg'),

        ]
      }
    },
    computed:{
      article(){
        let info=[]
        info[0] = this.$store.state.hotArticle[0]
        info[1] = this.$store.state.hotArticle[1]
        info[2] = this.$store.state.hotArticle[2]
        return info
      }
    },
    created() {

    }
  }
</script>

<style scoped>
  .row{
    width: 76%;
    margin: 60px auto;
    margin-bottom: 100px;
    font-size: 14px;
  }
  .el-link,p {/*文字可复制*/
    user-select: text;
  }
  .tilte{
    font-weight: bolder;
    font-size: 16px
  }

  .intro{
    width: 100%;
    /* 文字分两段显示，超出显示框时，超出部分显示省略号 */
    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
    /* 文字超出后是否自动换行 */
    /* white-space: nowrap; */
    text-overflow: ellipsis;/* 文字溢出，显示省略号 */
    overflow: hidden;
  }
  .bottom_info{
    width: 350px;
    /*border: 1px solid red;*/
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
    width: 120px;
    /*border: 1px solid red;*/
    justify-content: space-evenly;

  }
  .kid_kid{
    color: #666666;
    font-size: 12px;
    display: flex;
    align-items: center;
  }

  .bg-purple-dark {
    /*background: #99a9bf;*/
    height: 80px;
    display: flex;
    align-items: center;
    justify-content: center;
    /*margin-bottom: 5px;*/
    background-color: #eeeeee;
    margin-bottom: 8px;
  }

  .more{
    float: right;
    width: 180px;
    margin-top: 25px;

    /*border: 1px solid red;*/
  }
</style>

<template>
  <div class="row">
    <MainTabBar/>
    <el-row>
      <el-col :span="24">
        <div class="img_intro">
          <img src="./sa.jpg" width="100%" height="100%">
        </div>
        <div class="auth_intro">
          <div class="touxiang">
            <el-image></el-image>
          </div>
          <div class="content">
            <div class="content1">那些年我们一起追过的女孩</div>
            <div class="content2">
              <div>
                <icon-svg icon-class="dingwei" style="font-size: 18px"/>&nbsp;
                <span>乌鲁木齐</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <el-link type="primary">月牙弯弯星垂平野阔</el-link>
                <label>&nbsp;&nbsp;LV:27</label>
              </div>
              <div>
                <span>2020-01-29 14:47</span>
                <span><icon-svg icon-class="yanjing"/>&nbsp;520</span>
              </div>
            </div>
          </div>

          <div class="intro_title"></div>
          <div class="intro_name"></div>

          <div class="fenx">
            <div  class="fenxiang">
              <el-link :underline="false"><icon-svg icon-class="shoucang" style="font-size: 30px"/></el-link>
              <p>收藏20</p>
            </div>

            <el-dropdown>
              <span class="el-dropdown-link">
                <div class="fenxiang">
                  <icon-svg icon-class="fenxiang" style="font-size: 30px"/>
                  <p>分享10</p>
                </div>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item><el-link :underline="false"><icon-svg icon-class="weibo" style="font-size: 30px"/></el-link></el-dropdown-item>
                <el-dropdown-item divided><el-link :underline="false"><icon-svg icon-class="weixin" style="font-size: 30px"/></el-link></el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </div>
      </el-col>
    </el-row>

  </div>
</template>

<script>
  import MainTabBar from "../../components/common/tabbar/MainTabBar";
  export default {
    name: "BlogHead",
    components:{
      MainTabBar
    },
    data(){
      return{

      }
    }
  }
</script>

<style scoped>
  .img_intro{
    height: 500px;
  }
  .auth_intro{
    width: 100%;
    height: 160px;
    position: absolute;
    bottom: -80px;

  }
  .intro_title{
    opacity: .5;
    height: 80px;
  }
  .touxiang{
    width: 100px;
    height: 100px;
    border-radius: 50%;
    border: 1px solid red;
    position: absolute;
    top: 20px;
    left: 250px;
  }
  .intro_name{
    box-shadow: 0 -1px 10px rgba(100,100,100,.4);
    height: 70px;
  }
  .content{
    position: absolute;
    /*border: 1px solid red;*/
    width: 600px;
    height: 90px;
    left: 390px;
    top: 30px;
  }
  .content1{
    color: white;
    font-size: 25px;
    margin-left: 20px;
    font-weight: bold;
    /*border: 1px solid red;*/
  }
  .content2{
    width: 90%;
    position: absolute;
    bottom: 5px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    /*border: 1px solid red;*/
    font-size: 14px;
    color: #8c8c8c;
  }
  .content2 div:first-child{
    width: 320px;
    display: flex;
    justify-content: start;
    align-items: center;
    /*border: 1px solid red;*/
  }
  .content2 div:last-child{
    color: #8c8c8c;
    font-size: 14px;
    width: 200px;
    display: flex;
    justify-content: space-evenly;
    /*border: 1px solid red;*/
  }
  .fenx{
    position: absolute;
    top: 80px;
    left: 1100px;
    width: 160px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .fenxiang{
    /*border: 1px solid #8c8c8c;*/
    margin-top: 8px;
    width: 60px;
    height: 60px;
    text-align: center;
    line-height: 24px;
    font-size: 16px;
    color: black;
  }

  label{
    font-family: "Baskerville Old Face";
    color: #ea3c1a;
    font-weight: bold;
  }
</style>

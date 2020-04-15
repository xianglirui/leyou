<template>
  <div>
    <div id="kl">
      <a href="/home"><img src="~assets/img/other/logo_1.png" id="kjiu"/></a>
      <span id="ert">
        <el-button type="text" @click="open">
          <el-link>Android</el-link>
        </el-button>
        <el-link disabled>iPhone</el-link>
        <el-link disabled>iPad</el-link>
        <el-link disabled>手机网页版</el-link>
      </span>
    </div>
    <div id="ol">
      <img src="~assets/img/other/download.png" id="lm"/>
    </div>
    <div id="mnbmn">
      <span style="font-family: 华文新魏;font-size: 24px;color: #a8714a;margin-left: 25px">行到水穷处，坐看云起时</span>
      <div id="zczv">
        <img src="@/assets/img/other/123.png"/>
        <br/>
        <span>手机扫描二维码下载</span>
        <br/>
        <a href="" class="zsxc">立即下载<i class="el-icon-share"></i></a>
      </div>
    </div>
  </div>

</template>

<script>
  export default {
    name: "Download",
    methods: {
      open() {
        this.$alert('<a href="https://www.baidu.com" id="qwew"><i class="el-icon-share"></i>&nbsp;&nbsp;乐游APP</a>', {
          dangerouslyUseHTMLString: true,
          confirmButtonText: '关闭',
        });
      },
    }
  }
</script>

<style>
  body{
    background: #88d7c4;
  }
  #kl{
    display: flex;
    align-items: center;
    /*justify-content: space-between;*/
    /*background-color: red;*/
    width: 100%;

    height: 60px;
    z-index: 9;

    position: fixed;
    right: 0;
    left: 0;
    top: 0;
    background-color: #c3e7d2;
  }
  #lm{
    transform: scale(0.9);
    margin-top: 30px;
    margin-left: 100px;
  }
  #ol{
    height: 10px;
    width: 10px;
    /*border: 1px solid red;*/
  }
  #kjiu{
    margin-left: 100px;
  }
  #ert{
    display: flex;
    width: 400px;
    justify-content: space-between;
    position: relative;
    left: 750px;
  }
  #qwew{
    font-size: 20px;
    color: #409eff;
  }
  #mnbmn{
    width: 300px;
    height: 350px;
    position: relative;
    left: 1000px;
    top: 200px;
    /*display: flex;*/
    /*border: 1px solid red;*/
  }
  #zczv{
    background-color: white;width: 200px;height: 250px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    position: relative;
    left: 50px;
    top: 25px;
  }
  #zczv img{
    width: 131px;
    height: 131px;
    position: relative;
    left: 35px;
    top: 20px;
  }
  #zczv span{
    position: relative;
    top: 25px;
    left: 30px;
  }
  .zsxc{
    position: relative;
    top: 45px;
    left: 65px;
    color: #88d7c4;
    
  }
</style>

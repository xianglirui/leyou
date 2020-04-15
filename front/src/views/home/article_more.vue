<template>
  <div>
    <el-row class="bg-purple-dark">
      <img src="~assets/images/svg/文章管理.svg" alt="" width="25px"/>
      <p>&nbsp;&nbsp;热门游记</p>
    </el-row>
    <articles/>
  </div>
</template>

<script>
  import articles from "./articles";
  export default {
    name: "article_more",
    components:{
      articles
    }
  }
</script>

<style scoped>
  .bg-purple-dark {
    margin: 90px auto;
    /*background: #99a9bf;*/
    width: 76%;
    height: 80px;
    display: flex;
    align-items: center;
    justify-content: center;
    /*margin-bottom: 5px;*/
    background-color: #eeeeee;
    font-size: 20px;
  }
</style>

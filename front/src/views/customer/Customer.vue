<template>
  <div class="row">
    <main-tab-bar/>
    <div class="main">
        <div style="width: 21%">
          <el-card shadow="never" id="nav">
            <nav-bar :evaluate="this.$store.state.customer.evaluate.length" :use="this.$store.state.customer.use.length"/>
          </el-card>
        </div>
        <div style="width: 78%">
          <router-view/>
        </div>
    </div>

    <foot/>
  </div>
</template>

<script>
  import MainTabBar from "../../components/common/tabbar/MainTabBar";
  import navBar from "./children/navBar";
  import Foot from "../../components/common/footer/Footer";
  import MainContent from "./children/MainContent";
  import {baseURL} from "../../utils/constant";
  export default {
    name: "index",
    components: {navBar, MainTabBar, Foot, MainContent},
    data() {
      return {}
    },
    created() {
      this.$axios.get(baseURL + '/ssr',{params:{uid:this.$store.getters.userInfo.id}}).then(res => {
        console.log(res.data)
        this.$store.commit('changeInfo', res.data)
        this.routeState()
      })
      this.$axios.get(baseURL+'/select_complaint',{params:{uid:this.$store.getters.userInfo.id}}).then(res=>{
        this.$store.commit('changeComplaint', res.data)
      })
      this.$axios.get(baseURL+'/article_Lists',{params:{id:this.$store.getters.userInfo.id}}).then(res=>{
        this.$store.commit('SET_ARTICLE',res.data.list)
      })
      this.$axios.get(baseURL+'/selectCollectList',{params:{id:this.$store.getters.userInfo.id}}).then(res=>{
        this.$store.commit('SET_COLLECT',res.data)
      })
      this.$axios.get(baseURL+'/selectCommentListByUid',{params:{uid:this.$store.getters.userInfo.id}}).then(res=>{
        this.$store.commit('SET_COMMENT',res.data)
      })
    },
    methods: {
      routeState() {
        let use=[]
        let evaluate=[]
        let refund=[]

        for (let i of this.$store.state.customer.info) {
          if (i.state === 0 && i.refund === 0 || i.state === 4) {//0待使用  4失效
            use.push(i)
          }
          if (i.state === 1 || i.state === 2) {//1待评价 2已评价
            evaluate.push(i)
          }
          if (i.refund !== 0) {//有退款信息
            refund.push(i)
          }
        }
        this.$store.commit('changeUse', use)
        this.$store.commit('changeEvaluate', evaluate)
        this.$store.commit('changeRefund', refund)

      }
    }
  }
</script>

<style scoped>
  .row{
    background-color: #fafafa;
    width: 100%;
    /*border: 1px solid red;*/
  }
  .el-col{
    margin-left: 10px;
  }
  .main{
    width: 80%;
    margin: 40px auto;
    display: flex;
    justify-content: space-between;
  }
</style>

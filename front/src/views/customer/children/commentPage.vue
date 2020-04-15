<template>
  <div class="row">
    <main-tab-bar/>
    <div class="col">
      <el-card shadow="never" class="card">
        <commentedit :name="name" @sub="sub"/>
      </el-card>
    </div>

    <foot id="f"/>
  </div>
</template>

<script>
  import MainTabBar from "../../../components/common/tabbar/MainTabBar";
  import Foot from "../../../components/common/footer/Footer";
  import Commentedit from "../../../components/common/comment/Commentedit";
  import {baseURL} from "../../../utils/constant";
  import func from "../../../utils/formatTime";
  export default {
    name: "commentPage",
    components:{MainTabBar,Foot,Commentedit},
    data(){
      return{
        name:this.$route.params.name,
      }
    },
    created() {

    },
    methods:{
      FormatTime() {
        Date.prototype.Format = function (fmt) {
          let o = {
            "M+": this.getMonth() + 1, // 月份
            "d+": this.getDate(), // 日
            "H+": this.getHours(), // 小时
            "m+": this.getMinutes(), // 分
            "s+": this.getSeconds(), // 秒
            "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
            "S": this.getMilliseconds() // 毫秒
          };
          if (/(y+)/.test(fmt))
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
          for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
          return fmt;
        };
        let time2 = new Date().Format("yyyy年MM月dd日");

        console.log(time2);

      },
      sub(data){
        this.$axios.post(baseURL+'/commentArea',[data.html,data.value,this.FormatTime]).then(res=>{
          if(res.data==='ok'){
            this.$message.success('评论发表成功')
          }
        })
      },

    }
  }
</script>

<style scoped>
  .col{
    background-color: #fafafa;
    width: 100%;
    height: 120vh;
  }
  .card{
    width: 70%;
    margin: 0 auto;
    position: relative;
    top: 40px;
  }
  #f{
    margin-top: -40px;
  }
</style>

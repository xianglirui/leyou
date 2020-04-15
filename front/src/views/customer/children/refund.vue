<template>
  <div>
    <main-tab-bar/>
    <div class="col">
      <el-card shadow="never" class="card">
        <el-row>
          <el-col :span="21"><div style="font-size: 20px;font-weight: bold">申请退款</div></el-col>
          <el-col :span="3"><el-link :underline="false" type="primary" @click="$router.push('/user')">返回我的主页</el-link></el-col>
        </el-row>
        <el-divider/>
        <el-row class="state">
          <el-col :span="21" class="pay_state">&nbsp;当前订单状态：<span style="color: red;font-weight: bold;">待支付（￥{{money}}）</span></el-col>
          <el-col :span="3" style="padding: 8px"><el-tag type="success" effect="dark" size="mini">可退款</el-tag></el-col>
        </el-row>
        <div style="margin: 20px"></div>
        <el-row style="border: 1px solid #eeeeee;">
          <el-col :span="24" style="background-color: #eeeeee;padding: 10px;font-size: 14px;font-weight: bold">订单信息</el-col>
          <el-col :span="24" class="info">
            <div>订单编号：{{id*1000}}</div>
            <div>下单时间：{{time}}</div>
            <div>付款方式：{{pay}}</div>
            <div>&nbsp;</div>
          </el-col>
          <el-col :span="24" style="background-color: #eeeeee;padding: 10px;font-size: 14px;font-weight: bold">退款</el-col>

          <el-col :span="24" style="padding: 10px">
            <div style="background-color: #eeeeee;padding: 10px;color: #8a8a8a;font-size: 12px">
              <div>小提示：一日游门票可在景区打烊时间前2小时退掉，逾期失效，退款会在24小时到账，若逾期未到账，可咨询人工客服</div>
            </div>
          </el-col>

          <el-col :span="24"><div style="position: relative;left: 43%;margin-bottom:10px">
            <el-button size="mini" type="primary" @click="refunds">申请退款</el-button></div></el-col>
        </el-row>
      </el-card>
    </div>
    <Foot id="f"/>
  </div>
</template>

<script>
  import MainTabBar from "../../../components/common/tabbar/MainTabBar";
  import Foot from "../../../components/common/footer/Footer";
  import {baseURL} from "../../../utils/constant";
  export default {
    name: "refund",
    components:{
      MainTabBar,Foot
    },
    data(){
      return {
        money: this.$route.params.money,
        time:this.$route.params.time,
        id:this.$route.params.id,
        pay:this.$route.params.payWay===0?'微信支付':'支付宝',
      }
    },
    created() {
      console.log(this.$route.params.id);
    },
    methods:{
      refunds(){
        this.$axios.get(baseURL+'/refund',{params:{id:this.id}}).then(res=>{
          if(res.data==='ok'){
            this.$message.success('申请退款成功')
          }
        })
      }
    }
  }
</script>

<style scoped>
  .col{
    width: 100%;

    height: 65vh;
    background-color: #fafafa;
  }
  .card{
    width: 60%;
    margin: 0 auto;
    position: relative;
    top: 40px;
  }
  .state{
    background-color: #fff6db;
    height: 35px;

    border:1px solid rgba(255, 122, 79, 0.44);
  }
  .pay_state{
    font-size: 12px;
    padding: 10px;
  }
  .info{
    display: flex;
    justify-content: space-between;
    font-size: 12px;
    padding-top: 12px;
    padding-left: 10px;
    height: 35px;
  }
  #f{
    margin-top: -40px;
  }
</style>

<template>
  <div class="row">
    <div class="times">
      <div style="margin: 0px auto;width: 340px;position: relative;top: 30%">
        <i class="el-icon-time" style="font-size: 20px;color: #409eff"/>&nbsp;
        <span>请在<span style="font-size: 18px;color: #409eff"> {{m}}:{{s}} </span>内完成支付，超时订单会自动取消</span>
      </div>
    </div>
    <el-card shadow="never">
      <div class="shop_item">
        <div style="font-size: 20px;position: relative;top: 7px">收款项：{{item}}</div>
        <div class="money">应付金额￥&nbsp;&nbsp;<span style="font-size: 30px">{{value}}</span></div>
      </div>
    </el-card>
    <el-card shadow="never" style="margin-top: 30px">
      <el-divider content-position="left">在线支付</el-divider>
      <div style="margin-top: 40px">
        <el-radio v-model="radio" label="1" border style="height: 50px">
          <icon-svg icon-class="zhifubao" style="font-size: 20px"/>&nbsp;支付宝
        </el-radio>
        <el-radio v-model="radio" label="2" border style="height: 50px">
          <icon-svg icon-class="weixinzhifu" style="font-size: 20px"/>&nbsp;微信&nbsp;&nbsp;
        </el-radio>
      </div>
      <div style="float: right;margin-top: 50px;margin-bottom: 40px">
        <div style="margin-left: 70px;color: #409eff;">支付金额￥&nbsp;&nbsp;<span style="font-size: 30px;">{{value}}</span></div>
        <div style="margin: 20px"></div>
        <div>
          <el-link :underline="false" @click="$router.back()">返回修改订单&nbsp;&nbsp;&nbsp;&nbsp;</el-link>
          <el-button type="primary" round @click="pay">立即付款</el-button>
        </div>
      </div>
    </el-card>
    <el-dialog
      :title="img[radio-1].name"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <el-image :src="img[radio-1].url" style="margin-left: 65px"></el-image>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
  import {baseURL} from "../../utils/constant";

  export default {
    name: "Shopping",
    data(){
      return {
        item: '三峡人家一日游',
        value: this.$route.params.money,
        aid:this.$route.params.aid,
        nums:this.$route.params.nums,
        minutes: 5, //分
        seconds: 0, //秒
        isRoute:0,
        radio:'1',
        dialogVisible: false,
        img:[{url:require('@/assets/img/other/支付宝.png'),name:'支付宝'},{url:require('@/assets/img/other/微信.png'),name:'微信支付'}]
      }
    },
    created() {
      if(this.value==null){
        this.$router.back()
      }
    },
    methods: {
      // 倒计时
      timer() {
        let _this = this;
        let time = window.setInterval(function() {
          if (_this.seconds === 0 && _this.minutes !== 0) {
            _this.seconds = 59;
            _this.minutes -= 1;
          } else if (_this.minutes === 0 && _this.seconds === 0) {
            _this.seconds = 0;
            _this.isRoute = 1;
            window.clearInterval(time);
          } else {
            _this.seconds -= 1;
          }
        }, 1000);
      },
      handleClose(done) {
        this.$confirm('确认取消支付？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      },
      pay(){
        this.dialogVisible=true
        this.$axios.get(baseURL+'/addOrderItem',{params:{uid:this.$store.getters.userInfo.id,nums:this.nums,aid:this.aid,money:this.value,payway:this.radio==='1'?1:0}}).then(res=>{
          if(res.data==='ok'){
            setTimeout(()=>{this.$message.success('已出票')},4000)
          }
        }).catch(err=>{
          this.$message.warning('网络出错，请重试')
        })

      }
    },
    mounted() {
      this.timer();
    },
    watch:{
      isRoute(val){
        this.$router.back()
      }
    },
    computed: {
      m() {
        return this.minutes<10?'0'+this.minutes:''+this.minutes;
      },
      s() {
        return this.seconds<10?'0'+this.seconds:''+this.seconds;
      },
    },

  }
</script>

<style scoped>
  *{
    font-size: 14px;
  }
  .row{
    width: 80%;
    margin: 0 auto;
    height: 100%;
    /*border: 1px solid red;*/
  }
  .times{
    background-color: #fff;
    width: 100%;
    height: 50px;
    margin-bottom: 30px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  }
  .shop_item{
    display: flex;
    justify-content: space-between;
  }
  .money{
    color: #409eff;
  }

</style>

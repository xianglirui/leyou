<template>
  <div class="row">
    <el-row style="margin-bottom: 10px">
      <el-tag type="info" effect="dark">支付池</el-tag>
    </el-row>
    <el-card shadow="never">
      <el-table :data="tableData" stripe style="width: 100%;" height="65vh">
        <el-table-column label="支付状态" width="180">
          <template slot-scope="scope">
            <i class="el-icon-star-on" :style="style(scope.row)"/>
            <span>{{states(scope.row)}}</span>
          </template>
        </el-table-column>
        <el-table-column label="用户编号" prop="uid" width="180"></el-table-column>
        <el-table-column label="订单编号" prop="oid" width="180"></el-table-column>
        <el-table-column label="总金额" prop="money" width="200"></el-table-column>
        <el-table-column label="出票时间" prop="times" width="290"></el-table-column>
        <el-table-column label="状态管理">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" @click="toAreaRefund(scope.row)" v-if="scope.row.state===-1">通知商家</el-button>
            <el-button size="mini" type="danger" @click="okRefund(scope.row)" v-if="scope.row.state===-2">确定退款</el-button>
            <el-button size="mini" type="success" @click="updateState(scope.row)" v-if="scope.row.state===1">已阅放行</el-button>
            <el-button size="mini" type="info" v-if="[0,-4,-3].indexOf(scope.row.state)>=0">暂无操作</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
  import {baseURL} from "../../utils/constant";

  export default {
    name: "account",
    data(){
      return{
        radio:'1',
        tableData:[],
        s:1
      }
    },
    created() {
      this.$axios.get(baseURL+'/getPayPool').then(res=>{
        this.tableData = res.data
      })
    },
    methods:{
      states(item){
        if(item.state===0){
          return "待使用"
        }
        if(item.state===1){
          return "已使用"
        }
        if(item.state===-1){
          return '申请退款'
        }
        if(item.state===-2){
          return '同意退款'
        }
        if(item.state===-3){
          return '已退款'
        }
        if(item.state===-4){
          return '已失效'
        }
      },
      style(item){
        let style={'color':''}
        if(item.state===0){
          style.color='#8c8c8c'
        }
        if(item.state===1){
          style.color='#67c23a'
        }
        if(item.state===-1){
          style.color='#ff1a06'
        }
        if(item.state===-2){
          style.color='#FF794F'
        }
        if(item.state===-3){
          style.color='#67c23a'
        }
        if(item.state===-4){
          style.color='#409eff'
        }
        return style
      },
      okRefund(item){
        this.$axios.get(baseURL+'/okRefund',{params:{id:item.id,aid:item.aid}})
      },
      toAreaRefund(item){
        this.$axios.get(baseURL+'/toAreaRefund',{params:{id:item.id}})
      },
      updateState(item){
        this.$axios.get(baseURL+'/updateState',{params:{oid:item.oid}})
      }
    }
  }
</script>

<style scoped>
  .row{
    width: 85%;
    margin: 0 auto;
  }
</style>

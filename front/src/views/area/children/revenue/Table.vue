<template>
  <div class="row">
    <el-card shadow="hover">
    <el-table :data="tableData"  show-summary style="width: 100%" :summary-method="getSummaries">
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column label="日期">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.times }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="ordernums" sortable label="预定人数"></el-table-column>
      <el-table-column prop="outs" sortable label="出票数"></el-table-column>
      <el-table-column prop="other" sortable label="余票数"></el-table-column>
      <el-table-column sortable label="总均价">
        <template slot-scope="scope">
          <span>￥</span>
          <span>{{scope.row.aveprice}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="total" sortable label="总营收额">
        <template slot-scope="scope"><span>￥</span><span>{{scope.row.aveprice*scope.row.outs}}</span></template>
      </el-table-column>
    </el-table>
      <div style="width: 100%;height: 50px;display: flex;justify-content: center;margin-top: 20px">
        <div style="position:relative;top: 10px">
          <el-pagination background layout="prev, pager, next"
                         :current-page="pageConf.pageCode" @current-change="handleCurrentChange"
                         :page-size="pageConf.pageSize"
                         :total="pageConf.totalPage"></el-pagination>
        </div>
      </div>


    </el-card>
  </div>
</template>

<script>
  import {baseURL} from "../../../../utils/constant";

  export default {
    name: "Table",
    computed:{
      isCollapse(){
        return this.$store.state.isCollapse
      }
    },
    data(){
      return{
        tableData: [
          // {id:'1',date: '2020-2-1',  men: '45', outBill: '2', overBill: '4', average:'422', total:'4564'},
          // {id:'2',date: '2020-2-2',  men: '25', outBill: '4', overBill: '5', average:'225', total:'4565'},
          // {id:'3',date: '2020-2-3',  men: '75', outBill: '7', overBill: '6', average:'455', total:'5445'},
          // {id:'4',date: '2020-2-4',  men: '42', outBill: '8', overBill: '7', average:'422', total:'5454'},
          // {id:'5',date: '2020-2-5',  men: '35', outBill: '9', overBill: '8', average:'422', total:'4112'},
          // {id:'6',date: '2020-2-6',  men: '68', outBill: '4', overBill: '9', average:'455', total:'5421'},
          // {id:'7',date: '2020-2-7',  men: '17', outBill: '3', overBill: '10', average:'789', total:'4548'},
        ],
        pageConf: {
          //设置一些初始值(会被覆盖)
          pageCode: 1, //当前页
          pageSize: 7, //每页显示的记录数
          totalPage: 0, //总记录数
          pageOption: [4, 10, 20], //分页选项
        },
      };
    },
    created() {
      this.$axios.get(baseURL+'/getSell',{params:{uid:this.$store.getters.userInfo.id,pageCode:1}}).then(res=>{
        this.pageConf.totalPage=res.data.total
        this.tableData=res.data.rows
      })
    },
    methods:{
      getSummaries(param){
        const { columns, data } = param;
        const sums = [];
        let ordernums = data.map(item => item[columns.property='ordernums']).reduce((pre,curr)=>{
          return pre+Number(curr)
        },0)+' 人次';
        let outs = data.map(item => item[columns.property='outs'])
          .reduce((pre,curr)=>{return pre+Number(curr)},0)+' 张';
        let other = data.map(item => item[columns.property='other'])
          .reduce((pre,curr)=>{return pre+Number(curr)},0)+' 张';
        let aveprice = data.map(item => item[columns.property='aveprice'])
          .reduce((pre,curr)=>{return pre+Number(curr)},0)+' 元';
        let total = data.map(item =>item[columns.property='outs']*item[columns.property='aveprice'])
          .reduce((pre,curr)=>{return pre+Number(curr)},0)+' 元';

          // const values = data.map(item => item[column.property]);
          // if (!values.every(value => isNaN(value))) {
          //   sums[index] = values.reduce((prev, curr) => {
          //     return prev + curr
          //   },0)
          // } else if (values.every(value =>String(value).startsWith('￥'))) {
          //   sums[index] = values.map(n =>{
          //     return String(n).substring(1)
          //   }).reduce((pre,curr)=>{
          //     return pre+Number(curr)
          //   })
          // }else {
          //   sums[index] = '';
          // }


        columns.forEach((column, index) => {
          if (index === 0) {
            sums[index] = '合计';
            return;
          }
          sums[2] = ordernums;
          sums[3] = outs;
          sums[4] = other;
          sums[5] = aveprice;
          sums[6] = total;
        });
          return sums;
      },
      handleCurrentChange(val){
        this.$axios.get(baseURL+'/getSell',{params:{uid:this.$store.getters.userInfo.id,pageCode:val}}).then(res=>{
          this.tableData=res.data.rows
        })
      },
    }

  }
</script>

<style scoped>
  .row{
    position: relative;
    top: 10px;
    left: 10px;
    width: 98.5%;
  }
  .el-pagination{
    /*margin-top: 30px;*/
    /*position: relative;*/
    /*left: 35%;*/
    /*top: 30px;*/
  }
  .el-card{
    /*margin-bottom: 50px;*/
  }
</style>

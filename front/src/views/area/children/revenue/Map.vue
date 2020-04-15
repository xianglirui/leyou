<template>
  <div>
    <div id="echart" style="height:500px;"></div>
    <div style="width: 100%;height: 50px;display: flex;justify-content: center;margin-top: 10px">
      <div style="position:relative;top: 10px">
        <el-pagination background layout="prev, pager, next"
                       :current-page="pageConf.pageCode" @current-change="handleCurrentChange"
                       :page-size="pageConf.pageSize"
                       :total="pageConf.totalPage"></el-pagination>
      </div>
    </div>
  </div>

</template>

<script>
  // 引入 ECharts 主模块
  import {baseURL} from "../../../../utils/constant";

  const echarts = require('echarts/lib/echarts');
  // 引入柱状图
  require('echarts/lib/chart/bar');
  // 引入提示框和标题组件
  require('echarts/lib/component/tooltip');
  require('echarts/lib/component/title');
  require('echarts/lib/component/legend');
  require('echarts/lib/component/dataZoom');
  require('echarts/lib/component/markLine');

  export default {
    name: "Map",
    mounted() {
      this.initCharts();
    },
    data(){
      return{
        option :{
          title: {
            text: '一周营收柱状图'
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
              type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
          },
          legend: {
            data: ['预订人数', '出票数', '余票数', '票价', '当天总收入',]
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '15%',
            textStyle: {
              color: "#fff"
            },
            containLabel: true
          },
          xAxis: [
            {
              type: 'category',
              data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
            }
          ],
          yAxis: [
            {
              type: 'value'
            }
          ],
          dataZoom: [
            {   // 这个dataZoom组件，默认控制x轴。
              type: 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
              start: 0,      // 左边在 10% 的位置。
              end: 100         // 右边在 60% 的位置。
            },
            {   // 这个dataZoom组件，也控制x轴。
              type: 'inside', // 这个 dataZoom 组件是 inside 型 dataZoom 组件
              start: 0,      // 左边在 10% 的位置。
              end: 100         // 右边在 60% 的位置。
            }
          ],
          series: [
            {
              name: '预订人数',
              type: 'bar',
              data: [320, 332, 301, 334, 390, 330, 320]
            },
            {
              name: '出票数',
              type: 'bar',
              stack: '票数',
              data: [120, 132, 101, 134, 90, 230, 210]
            },
            {
              name: '余票数',
              type: 'bar',
              stack: '票数',
              data: [220, 182, 191, 234, 290, 330, 310]
            },
            {
              name: '票价',
              type: 'bar',
              stack: '价格',
              data: [620, 732, 701, 734, 1090, 1130, 1120]
            },
            {
              name: '当天总收入',
              type: 'bar',
              data: [862, 1018, 964, 1026, 1679, 1600, 1570],
              markLine: {
                lineStyle: {
                  type: 'dashed'
                },
                data: [
                  [{type: 'min'}, {type: 'max'}]
                ]
              }
            },
          ]
        },
        pageConf: {
          //设置一些初始值(会被覆盖)
          pageCode: 1, //当前页
          pageSize: 7, //每页显示的记录数
          totalPage: 0, //总记录数
          pageOption: [4, 10, 20], //分页选项
        },
        tableData:[]
      }
    },
    created() {
      this.$axios.get(baseURL+'/getSell',{params:{uid:this.$store.getters.userInfo.id,pageCode:1}}).then(res=>{
        this.pageConf.totalPage=res.data.total
        let outs = []
        let aveprice=[]
        let other=[]
        let ordernums=[]
        let total=[]
        for(let i of res.data.rows){
          outs.push(i.outs)
          aveprice.push(i.aveprice)
          other.push(i.other)
          ordernums.push(i.ordernums)
          total.push(i.aveprice*i.outs)
        }
        let myChart = echarts.init(document.getElementById('echart'));
        myChart.setOption({
          series: [{name: '预订人数', data: ordernums},{name:'票价',data: aveprice},{name:'出票数',data:outs},{name:'余票数',data:other},{name:'当天总收入',data:total}]
        });
        myChart.hideLoading();
      })
    },
    methods: {
      initCharts() {
        let myChart = echarts.init(document.getElementById('echart'));
        myChart.setOption(this.option)
        window.addEventListener('resize',()=>{
          myChart.resize()
        })
        myChart.showLoading();
      },
      handleCurrentChange(val){
        this.$axios.get(baseURL+'/getSell',{params:{uid:this.$store.getters.userInfo.id,pageCode:val}}).then(res=>{
          let outs = []
          let aveprice=[]
          let other=[]
          let ordernums=[]
          let total=[]
          for(let i of res.data.rows){
            outs.push(i.outs)
            aveprice.push(i.aveprice)
            other.push(i.other)
            ordernums.push(i.ordernums)
            total.push(i.aveprice*i.outs)
          }
          let myChart = echarts.init(document.getElementById('echart'));
          myChart.setOption({
            series: [{name: '预订人数', data: ordernums},{name:'票价',data: aveprice},{name:'出票数',data:outs},{name:'余票数',data:other},{name:'当天总收入',data:total}]
          });
          myChart.hideLoading();
        })
      },
    }
  }

</script>

<style scoped>

</style>

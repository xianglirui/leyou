<template>
  <div id="echart" style="height: 500px;"></div>
</template>

<script>
  const echarts = require('echarts/lib/echarts');
  // 引入柱状图
  require('echarts/map/js/china');
  require('echarts/lib/chart/bar');  // 引入柱状图组件
  require('echarts/lib/chart/pie');  // 引入饼状图组件
  // 引入提示框和标题组件
  require('echarts/lib/component/tooltip');
  require('echarts/lib/component/title');

  require('echarts/theme/macarons');//引入主题
  require('echarts/theme/shine');//引入主题
  import option from './map-option'
  export default {
    name: "map",
    mounted() {
      this.initCharts();
    },
    data(){
      return{

      }
    },
    methods:{
      initCharts() {
        let myChart = echarts.init(document.getElementById('echart'));
        myChart.setOption(option)
        window.addEventListener('resize',()=>{
          myChart.resize()
        })
      },
    }
  }
</script>

<style scoped>

</style>

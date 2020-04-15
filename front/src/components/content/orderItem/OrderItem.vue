<template>
  <div class="row">
    <el-row style="position: relative;top: 17px">
      <el-col :span="3">
        <div><slot name="img"></slot> </div>
      </el-col>
      <el-col :span="10" style="position: relative;top: 10px;">
        <div><slot name="name"></slot> </div>
        <div class="orderinfo"><slot name="time"></slot> </div>
        <div style="margin-top: 5px"></div>
        <div class="orderinfo"><slot name="nums"></slot> </div>
      </el-col>
      <el-col :span="6" style="position: relative;top: 30px;font-size: 12px">
        <div><slot name="money"></slot></div>
      </el-col>
      <el-col :span="5" style="position: relative;top: 30px;font-size: 12px">
        <div><slot name="opera"></slot></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  export default {
    name: "OrderItem",
    data(){
      return{
        url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg'
      }
    }
  }
</script>

<style scoped>
  .row{
    width: 100%;
    height: 120px;
    position: relative;

    left: 5px;
  }
  .orderinfo{
    font-size: 12px;
    position: relative;
    top: 12px;
    color: #8c8c8c;
  }

</style>

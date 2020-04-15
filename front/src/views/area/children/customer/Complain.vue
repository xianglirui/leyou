<template>
  <div class="row">
    <el-card shadow="never">
     <el-table :data="tableData">
       <el-table-column type="index" width="80" label="序号"></el-table-column>
       <el-table-column label="用户编号" prop="uid" width="130"></el-table-column>
       <el-table-column label="投诉时间" prop="time" width="230"></el-table-column>
       <el-table-column label="投诉类别" prop="state" width="180">
         <template slot-scope="scope">
           <el-tag effect="dark" type="warning" v-if="scope.row.s===1">{{scope.row.state}}</el-tag>
           <el-tag effect="dark" type="primary" v-if="scope.row.s===2">{{scope.row.state}}</el-tag>
         </template>
       </el-table-column>
       <el-table-column label="投诉内容" prop="content" width="200"></el-table-column>
       <el-table-column label="我的操作">
         <template slot-scope="scope">
           <el-button size="mini" plain type="warning" v-if="scope.row.s===1" @click="agreeRefund(scope.row)">同意退款</el-button>
           <el-button size="mini" plain type="primary" v-if="scope.row.s===2" @click="IWell(scope.row)">我已改正</el-button>
         </template>
       </el-table-column>
     </el-table>
    </el-card>
  </div>
</template>

<script>
  import {baseURL} from "../../../../utils/constant";

  export default {
    name: "Complain",
    data(){
      return{
        activeNames: '1',
        tableData:[]
      }
    },
    created() {
      let info={}
      let info1={}
      for(let i of this.$store.state.areaInfo.refund){
        info.id=i.id
        info.time = i.times
        info.uid=i.uid
        info.state='退款申请'
        info.s=1
        info.content='无'
        this.tableData.push(info)
      }
      for(let i of this.$store.state.areaInfo.complaint){
        info1.id=i.id
        info1.time = i.starttime
        info1.uid=i.uid
        info1.s=2
        info1.state=i.additional
        info1.content=i.content
        this.tableData.push(info1)
      }
    },
    computed:{

    },
    methods:{
      IWell(item){
        this.$axios.get(baseURL+'/areaAmend',{params:{index:item.starttime,id:item.id}})
      },
      agreeRefund(item){
        this.$axios.get(baseURL+'/AreaAgreeRefund',{params:{id:item.id}})
      }
    }
  }
</script>

<style scoped>

</style>

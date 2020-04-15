<template>
  <div class="row">
    <el-card shadow="never">
      <el-collapse v-model="activeNames" accordion v-if="comment.length>0">
        <el-collapse-item :title="item.times" :name="index" v-for="(item,index) in comment" :key="index">
          <div>客户留言：<div v-html="item.content"></div></div>
          <el-divider/>
          <div>
            <el-row>
              <el-col>商家回复：</el-col>
              <div style="margin: 5px"></div>
              <el-col :span="21"><el-input type="textarea" placeholder="回复客户" v-model="txt[index]"></el-input></el-col>
              <el-col :span="1">&nbsp;</el-col>
              <el-col :span="1"><el-button type="primary" size="mini" @click="updateBack(txt[index],index,item.id)">确定</el-button></el-col>
            </el-row>
          </div>
        </el-collapse-item>
      </el-collapse>
      <div v-else style="width: 100%;display:flex;justify-content: center">
        <icon-svg icon-class="box-package-parcel-office-open-unpack-delivery-ebfdc"/>
        <span style="color: #8c8c8c;font-size: 14px">这里是空的</span>
      </div>

    </el-card>

  </div>
</template>

<script>
  import {baseURL} from "../../../../utils/constant";

  export default {
    name: "talk",
    created() {
      // console.log(this.$store.state.area.id)

      // console.log()
    },
    data() {
      return {
        activeNames: '1',
      };
    },
    computed:{
      txt(){
        return this.$store.state.areaInfo.txt
      },
      comment(){
        return this.$store.state.areaInfo.comment
      }
    },
    methods:{
      updateBack(txt,i,id){
        // console.log(id)
        this.$axios.get(baseURL+'/updateBack',{params:{text:txt,aid:this.$store.state.area.id,id:id}}).then(res=>{
          if(res.data==='ok'){
            this.$message.success('提交成功')
          }
        })
      }
    }
  }
</script>

<style scoped>
  .row{

  }
</style>

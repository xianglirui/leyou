<template>
  <div class="row">
    <el-row>
      <el-col :span="3"><el-tag>基本信息</el-tag></el-col>
      <el-col :span="3">用户名：{{usernamePro}}</el-col>
      <el-col :span="3">邮箱认证&nbsp;<icon-svg icon-class="renzhengtongguo"/></el-col>
      <el-col :span="3">景区认证&nbsp;<icon-svg icon-class="auth"/></el-col>
      <el-col :span="4">负责人姓名：<span v-show="loading">{{principal}}</span></el-col>
      <el-col :span="6">联系方式：<span v-show="loading">{{principal_tel}}</span></el-col>
      <el-col :span="2"><el-link :underline="false" type="primary" @click="$router.push({path:'/area/edit',query:{okid:$store.getters.userInfo.name}})">
        <i class="el-icon-edit"></i>&nbsp;修改</el-link></el-col>
    </el-row>
    <el-divider/>
    <div style="margin: 20px"></div>
    <el-row>
      <el-col>
        <el-tag style="margin-bottom: 10px">两日内交易记录</el-tag>
        <el-table :data="tableData"  border style="width: 100%">
          <el-table-column label="日期" width="400">
            <template slot-scope="scope">
              <i class="el-icon-time"></i>
              <span style="margin-left: 10px">{{ scope.row.date }}</span>
            </template>
          </el-table-column>
          <el-table-column label="售票数" width="380">
            <template slot-scope="scope">
              <span style="margin-left: 5px">{{ scope.row.num }}张</span>
            </template>
          </el-table-column>
          <el-table-column label="营收额">
            <template slot-scope="scope">
              <span>￥{{ scope.row.total }}</span>
            </template>
          </el-table-column>
        </el-table>
        <el-button type="primary" size="mini" @click="$router.push({path:'/area/revenue/table',query:{okid:$store.getters.userInfo.name}})" style="position: relative;left: 92%">详细账单</el-button>
      </el-col>
    </el-row>
    <el-row>
      <el-col v-bind:span="24"><el-tag>今日客户留言</el-tag></el-col>
      <el-col style="margin-top: 20px" :span="2">
        <el-badge :value="value1" :max="10" class="item">
          <i class="el-icon-message" style="font-size: 25px;color: #8a8a8a"/>
        </el-badge>
      </el-col>
      <el-col :span="20" style="margin-top: 20px"><el-link type="primary" :underline="false" @click="$router.push({path:'/area/customer/talk',query:{okid:$store.getters.userInfo.name}})">
        查看留言</el-link></el-col>
      <el-col><el-divider/></el-col>
    </el-row>
    <el-row>
      <el-col><el-tag>今日退款投诉</el-tag></el-col>
      <el-col style="margin-top: 20px" :span="2">
        <el-badge :value="value2" :max="10" class="item">
          <i class="el-icon-chat-line-square" style="font-size: 25px;color: #8a8a8a"/>
        </el-badge>
      </el-col>
      <el-col :span="20" style="margin-top: 20px"><el-link type="primary" :underline="false"
          @click="$router.push({path:'/area/customer/complain',query:{okid:$store.getters.userInfo.name}})">查看投诉</el-link></el-col>
    </el-row>
  </div>
</template>

<script>
  import {baseURL} from "../../../../utils/constant";

  export default {
    name: "Home",
    data(){
      return{
        tableData: []
      }
    },
    created() {
      this.$axios.get(baseURL+'/getSell',{params:{uid:this.$store.getters.userInfo.id,pageCode:1}}).then(res=>{
        for(let i =0;i<2;i++){
          let info={}
          info.date=res.data.rows[i].times
          info.num = res.data.rows[i].outs
          info.total = res.data.rows[i].outs*res.data.rows[i].aveprice
          this.tableData.push(info)
        }
      })
    },
    computed:{
      value1(){
        return this.$store.state.areaInfo.comment.length
      },
      value2(){
        return this.$store.state.areaInfo.complaint.length+this.$store.state.areaInfo.refund.length
      },
      usernamePro(){
        return this.$store.getters.userInfo.name
      },
      loading(){
        return this.$store.state.area != null;
      },
      areas(){
        return this.$store.state.area
      },
      principal(){
        return this.areas==null?'xxx':this.areas.principal
      },
      principal_tel(){
        return this.areas==null?'xxx':this.areas.principal_tel
      }
    },
    methods:{

    }
  }
</script>

<style scoped>
  .el-col{
    line-height: 40px;
    /*border: 1px solid red;*/
  }
  .row{

  }
</style>

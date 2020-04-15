<template>
  <div>
    <el-row>
      <el-col :span="20" style="font-size: 20px;margin-left: 10px">{{name}}点评</el-col>
      <el-col :span="3"><el-link type="primary" @click="$router.back()" class="right">返回主页</el-link></el-col>
      <el-col :span="24"><el-divider/></el-col>

      <el-col :span="24">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="我的评分">
            <el-rate v-model="form.value" :colors="colors" style="position: relative;top: 10px"></el-rate>
          </el-form-item>
          <el-form-item label="评论内容">
            <simple-m-d-e id="contentEditor" ref="contentEditor" v-model="form.desc" :height="300" :zIndex="20"/>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="mini" class="right" @click="sub">提交</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import SimpleMDE from "../SimpleMDE/SimpleMDE";
  import {baseURL} from "../../../utils/constant";
  export default {
    name: "Commentedit",
    components:{
      SimpleMDE
    },
    props:{
      name:{
        type:String,
        default:""
      },
    },
    data(){
      return{
        flag:'',
        form: {
          desc: '',
          value:0,
          html:''
        },
        colors: ['#99A9BF', '#F7BA2A', '#FF9900'], // 等同于 { 2: '#99A9BF', 4: { value: '#F7BA2A', excluded: true }, 5: '#FF9900' }
        dialogImageUrl: '',
        dialogVisible: false
      }
    },
    created() {
      console.log(this.$route.params.oid)
    },
    methods:{
      sub(){
        if(this.form.value===0){
          this.$message.error('请评分')
        }else {
          import('showdown').then(showdown => {
            const converter = new showdown.Converter()
            this.form.html = converter.makeHtml(this.form.desc)
            // this.$emit('sub',this.form)
            this.$axios.get(baseURL+'/addCommentWithArea',{params:{html:this.form.html,value:this.form.value,aid:this.$route.params.aid,
                uid:this.$store.getters.userInfo.id,oid:this.$route.params.oid}}).then(res=>{
              if(res.data==='ok'){
                this.$message.success('提交成功')
              }else {
                this.$message.warning('您之前已经评价过了')
              }
            })
          })
        }
      }

    }
  }
</script>

<style scoped>

</style>

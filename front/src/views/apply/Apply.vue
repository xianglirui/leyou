<template>
  <div class="row" v-loading="loading" element-loading-text="加载中，请等候">
    <div class="top">
        <a href="/home" id="home">
          <img src="~assets/img/logo/logo_2.png" id="imgs"/>
          <span>景区服务中心</span>
        </a>
      <el-tag effect="plain" v-if="username!==''">欢迎您：{{username}}</el-tag>
      <el-tag effect="plain" v-else>您好，请登录</el-tag>
    </div>
    <div style="position: absolute;left: 47%"><el-tag effect="dark" size="small" type="success">注册步骤</el-tag></div>
    <div class="main">
      <el-row>
        <el-col :span="24">
          <el-steps :active="active" finish-status="success">
            <el-step :title="item.title" :description="item.description" v-for="(item,index) in steps" :key="index"></el-step>
          </el-steps>
        </el-col>
      </el-row>
      <div class="btn">
        <el-button style="margin-top: 12px;" @click="logins" type="primary">登录</el-button>
        <el-button style="margin-top: 12px;" @click="applys" type="primary">填表</el-button>
        <el-button style="margin-top: 12px;" @click="submits" type="primary">上传</el-button>
        <el-button style="margin-top: 12px;" @click="edits" type="primary">编辑</el-button>
      </div>
      <el-collapse-transition>
        <div class="form" v-show="isShow">
        <el-card shadow="hover">
          <el-form ref="form1" :model="form" label-width="80px" label-position="left">
            <el-form-item label="景区名称">
              <el-input v-model="form.name"  placeholder="景区名称"></el-input>
            </el-form-item>
            <el-form-item label="选择地区">
              <region @subNum="subNum"/>
            </el-form-item>
            <el-form-item label="国家A级">
              <div class="pin">
                <el-switch v-model="form.delivery"></el-switch>
                <el-rate v-if="form.delivery===false" disabled></el-rate>
                <el-rate v-model="form.star" v-else></el-rate>
              </div>
            </el-form-item>
            <el-form-item label="负责人">
              <el-input v-model="form.man" placeholder="负责人姓名"></el-input>
            </el-form-item>
            <el-form-item label="联系方式">
              <el-input v-model="form.call" placeholder="负责人联系方式"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="sub"><span v-show="icon_load"><i class="el-icon-loading"/>&nbsp;</span>提交</el-button>
              <el-button @click="resetForm">重置</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </div>
      </el-collapse-transition>

      <el-dialog title="上传文件" :visible.sync="dialogleVisible" width="400px" :before-close="handleClose">
        <el-tag>上传景区资料，旅游管理证，当地政府文件，工商执照等</el-tag>
        <div style="margin-bottom: 20px"></div>
        <el-upload
          :data="dataToken"
          drag
          :before-remove="beforeRemove"
          action="https://upload.qiniup.com"
          :multiple="true"
          :before-upload="beforeUpload"
          :on-success="handleSuccess"
          :file-list="fileList">
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div class="el-upload__tip" slot="tip">支持格式：jpg、png、pdf、doc</div>
        </el-upload>
      </el-dialog>
    </div>
  </div>
</template>

<script>
  import {baseURL} from "../../utils/constant";
  import region from "../../components/common/region/region";

  export default {
    name: "Apply",
    components:{
      region
    },
    created() {
      if(this.$store.getters.userInfo&&this.$store.getters.userInfo.role===1){
        this.$axios.get(baseURL+'/select_active',{params:{param:this.$store.getters.userInfo.name}}).then(res=>{
          if(res.data!==-1){
            this.active=res.data
            this.loading = false
          }else{
            this.$axios.get(baseURL+'/select_step',{params:{param:this.$store.getters.userInfo.name}}).then(res=>{
              this.active=res.data
              this.next()
              this.loading = false
            })
          }
        }).catch(err=>{

        })
      }else {
        this.loading = false
      }

    },
    data() {
      return {
        allowGo:false,
        fileList:[],
        dataToken:{token: '', key: '' },
        loading:true,
        dialogleVisible:false,
        active:0,
        form: {
          name: '',
          man:'',
          delivery: false,
          star:null,
          call:'',
          province:'',
          city:'',
          region:'',
          // username:localStorage.getItem('Authorization')
          username:this.$store.getters.userInfo.name,
          id:this.$store.getters.userInfo.id
        },
        isShow:false,
        icon_load:false,
        // username:localStorage.getItem('Authorization')?localStorage.getItem('Authorization'):'',
        username:this.$store.getters.userInfo?this.$store.getters.userInfo.name:'',
        steps:[{
          title:'步骤 1',
          description:'注册一个景区账户，已有账户？请登录'
        },{
          title:'步骤 2',
          description:'填写申请表'
        },{
          title:'步骤 3',
          description:'提交申请文件'
        },{
          title:'步骤 4',
          description:'编辑景区信息'
        }]
      };
    },
    methods: {
      beforeUpload(file){
        const _this = this;
        return new Promise(((resolve, reject) => {
          this.$axios.get(baseURL+'/qiniu_token').then(res=>{
            const token = res.data;
            _this.dataToken.key = '/apply/'+_this.username+'/'+file.name;
            _this.dataToken.token = token;
            resolve(true);
          }).catch(err=>{
            console.log(err);
            reject(false)
          })
        }))

      },
      subNum(p,c,r){
        this.form.province = p;
        this.form.city = c;
        this.form.region = r;
      },
      next(){
        let _this = this;
        this.$axios.get(baseURL+'/update_active',{params:{param:this.$store.getters.userInfo.name}}).then(res=>{
          _this.active = res.data
        })
      },
      //步骤1
      logins() {
        //this.active++;
        /*这个方法不行，因为当页面刷新，active会重新被赋值,
        用vuex state保存也不行，因为store里的数据是保存在运行内存中的,当页面刷新时，页面会重新加载vue实例，store里面的数据就会被重新赋值.
        active的值是永久的，只能把状态保存在数据库中*/
        let _this = this;
        if(this.active===0){
          this.$router.push({name:'Login',params:{role:'1'}})
        }else {
          this.$message.warning('请进行第'+(this.active+1)+'步')
        }

      },
      //步骤2
      applys(){
        if(this.active===1){
          this.isShow=true
        }else {
          this.$message.warning('请进行第'+(this.active+1)+'步')
        }
      },
      //步骤3
      submits(){
        if(this.active===2){
          this.dialogleVisible = true
        }else {
          this.$message.warning('请进行第'+(this.active+1)+'步')
        }

      },
      //步骤4
      edits(){
        if(this.active===3){
          this.$axios.get(baseURL+'/isTableHasData',{params:{uid:this.$store.getters.userInfo.id}}).then(res=>{
            if(res.data==='ok'){
              this.next()
              setTimeout(()=>{this.$router.push({path:'/area',query:{okid:this.$store.getters.userInfo.name}})},1000)
            }else {
              this.$message.warning('请等待审核')
            }
          })
        }else if(this.active===4){
          this.$router.push({path:'/area',query:{okid:this.$store.getters.userInfo.name}})
        }else {
          this.$message.warning('请进行第'+(this.active+1)+'步')
        }
      },
      sub(){
        this.icon_load=true
        this.$axios.post(baseURL+'/area_from',this.form).then(res=>{
          if(res.data==='ok'){
            this.icon_load=false
            this.next();
          }
        })
      },
      resetForm() {
        this.form={};
      },
      beforeRemove(){
        return this.$confirm(`文件已上传到服务器，您的操作不会删除已上传的文件`,{
          confirmButtonText: '确定',
          cancelButtonText: '',
          type: 'warning'
        });
      },
      //怎么判断上传文件完成去调用next()方法呢？，首先定义一个空数组，上传一个文件成功后钩子函数handleSuccess()给空数组赋值
      //点击对话框的关闭按钮，会检查空数组有无值，无值会提醒，有值就直接走next()
      handleClose(done){
        if(this.fileList.length===0){
          this.$confirm('您还没有上传文件哦',{type:"warning"}).then(_ => {
            done();
          }).catch(() => {});
        }else {
          this.next()
          done()
        }
      },
      handleSuccess(res){
        this.fileList.push({name:res.key})
      }
    }
  }
</script>

<style scoped>
  .btn{
    display: flex;
    justify-content: space-between;

  }
  .top{
    height: 60px;
    width: 100%;
    background-color: #88d7c4;
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
  .main{
    margin-top: 40px;
    width: 95%;
    position: relative;
    left: 2.5%;
    /*border: 1px solid red;*/
  }
  .el-input{
    width: 300px;
  }
  .pin{
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 200px;
    height: 40px;
    /*border: 1px solid red;*/
  }
  .form{
    margin: 20px auto;
    width: 600px;
  }
  .el-tag{
    /*width: 150px;*/
    margin-right: 20px;
  }
  #imgs{
    transform: scale(0.4);
    /*border: 1px solid red;*/
  }
  #home{
    /*border: 1px solid red;*/
    display: flex;
    align-items: center;
    height: 60px;
    width: 500px;
    margin-left: -50px;
  }
  #home span{
    width: 120px;
    font-size: 20px;
    margin-left: -90px;
    color: #000;
  }
</style>

<template>
  <div class="row">
    <el-card shadow="never">
    <el-form ref="form" :model="form" label-width="80px" label-position="left">
      <el-form-item label="景区名称">
        <el-input v-model="form.name" style="width: 20%;" prefix-icon="el-icon-discount" disabled></el-input>
        <i class="el-icon-warning-outline" @click="open1" style="margin-left: 10px"/>
      </el-form-item>
      <el-form-item label="国家A级">
        <el-rate v-model="value" disabled show-score text-color="#ff9900" score-template="{value}"></el-rate>
      </el-form-item>
      <el-form-item label="地点">
        <el-input v-model="form.site" placeholder="三峡人家" style="width: 45%;" prefix-icon="el-icon-map-location" disabled></el-input>
        <i class="el-icon-warning-outline" @click="open1" style="margin-left: 10px"/>
      </el-form-item>
      <el-form-item label="开放时间" style="width: 60%">
        <el-col :span="7">
          <el-input placeholder="开放时间" v-model="form.date" prefix-icon="el-icon-date"></el-input>
        </el-col>
        <el-col :span="1">&nbsp;</el-col>
        <el-col :span="7">
          <el-time-picker placeholder="开门时间" v-model="form.opentime" style="width: 100%;" value-format="HH:mm"></el-time-picker>
        </el-col>
        <el-col class="line" :span="1" style="margin-left: 10px">-</el-col>
        <el-col :span="7">
          <el-time-picker placeholder="闭门时间" v-model="form.closetime" style="width: 100%;" value-format="HH:mm"></el-time-picker>
        </el-col>
      </el-form-item>
      <el-form-item label="客流限制">
        <div id="ren">
          <el-input placeholder="每日最大客流" style="width: 133px;" prefix-icon="el-icon-s-data" v-if="delivery===false" disabled></el-input>
          <el-input v-model="form.nums" placeholder="每日最大客流" style="width: 133px;" prefix-icon="el-icon-s-data" v-else></el-input>
          <el-checkbox v-model="delivery"></el-checkbox>
        </div>
      </el-form-item>
      <el-form-item label="价格/人">
        <el-input placeholder="单价" prefix-icon="el-icon-coin" v-model="form.price" style="width: 78px;">
        </el-input>
      </el-form-item>
      <el-form-item label="景区公告">
        <el-input type="textarea" placeholder="80字以内" v-model="form.descs" resize="none" maxlength="80" show-word-limit :autosize="{ minRows: 2}"></el-input>
      </el-form-item>
      <el-form-item label="联系方式">
        <el-input placeholder="景区联系方式" v-model="form.tel" style="width: 20%" prefix-icon="el-icon-phone-outline"></el-input>
      </el-form-item>
      <el-form-item label="景区照片">
        <div style="color: #a8714a"><i class="el-icon-info"/>&nbsp;至少六张</div>
        <el-upload
          :data="dataToken"
          :multiple="true"
          action="https://upload.qiniup.com"
          list-type="picture-card"
          :on-preview="handlePictureCardPreview"
          :before-upload="beforeUpload"
          :file-list="fileList"
          :on-remove="handleRemove">
          <i class="el-icon-plus"></i>
        </el-upload>
        <el-dialog :visible.sync="dialogVisible">
          <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">提交</el-button>
        <el-button @click="reset">重置</el-button>
      </el-form-item>
    </el-form>
    </el-card>
  </div>
</template>

<script>
  import {baseURL} from "../../../../utils/constant";
  import {xiaoshou} from "../../../../assets/js/administrator";

  export default {
    name: "Form",
    data() {
      return {
        dataToken:{token: '', key: '' },
        xiaoshou: xiaoshou,
        delivery: false,
        form: {
          name: '',
          site: '',
          opentime: '',
          closetime: '',
          price:'',
          nums: '',
          descs: '',
          tel:'',
          date:'',
          star:''
        },
        value:3,
        dialogImageUrl: '',
        dialogVisible: false,
        fileList:[]
      }
    },
    created() {
      let area = this.$store.state.area
      this.form.name = area.name
      this.value = area.star
      this.form.site = area.site
      this.form.opentime  = area.opentime
      this.form.closetime = area.closetime
      this.form.descs = area.descs
      this.form.tel = area.tel
      this.form.price = area.price
      this.form.nums  = area.nums
      this.form.date = area.date
      this.$axios.get(baseURL+'/getPictureByAName',{params:{name:this.form.name}}).then(res=>{
        for(let i in res.data){
          let info={}
          info.name= i
          info.url=res.data[i]
          this.fileList.push(info)
        }
        console.log(this.fileList)
      })
    },
    methods:{
      open1(){
        this.$notify({
          title: '修改提醒',
          message: '您无法在此修改，若需修改，请联系销售经理 '+this.xiaoshou,
          type: 'warning',
          offset:130
        });
      },
      reset(){
        this.form={}
      },
      handleRemove(file) {
        this.$axios.get(baseURL+'/deletePictureByFileName',{params:{aname:this.$store.state.area.name,fname:file.name}})
      },
      handlePictureCardPreview(file) {
        this.dialogImageUrl = file.url;
        this.dialogVisible = true;
      },
      beforeUpload(file){
        const _this = this;
        return new Promise(((resolve, reject) => {
          this.$axios.get(baseURL+'/qiniu_token').then(res=>{
            const token = res.data;
            _this.dataToken.key = '/areaInfo/'+this.$store.state.area.name+'/'+file.name;
            _this.dataToken.token = token;
            resolve(true);
          }).catch(err=>{
            console.log(err);
            reject(false)
          })
        }))

      },
      onSubmit(){
        const data = {form:this.form,name:this.$store.getters.userInfo.name}
        this.$axios.post(baseURL+'/area_form_sub',data).then(res=>{
          if(res.data==='ok'){
            this.$message.success('提交成功')
          }
        })
      }
    }
  }
</script>

<style scoped>
  .el-rate{
    position: relative;
    top: 10px;
    /*color: #ff3d1b;*/
  }
  #ren{
    display: flex;
    align-content: center;
    justify-content: space-between;
    width: 17%;
    height: 40px;
    /*border: 1px solid red;*/
  }
</style>

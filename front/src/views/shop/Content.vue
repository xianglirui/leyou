<template>
  <div class="row">
    <el-row>
      <el-col :span="22">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item v-for="(item,index) in citys" :key="index">{{item}}</el-breadcrumb-item>
        </el-breadcrumb>
      </el-col>
      <el-col :span="2">
        <el-link :underline="false" type="warning" @click="$router.push({path:'/complaint',query:{area:'mis',aid:aid}})"><i class="el-icon-position"/><span>我要投诉</span></el-link>
      </el-col>
    </el-row>


    <el-card class="card1">
      <div class="card-content">

        <div class="text-info">
          <div style="width: 100%;display: flex;justify-content: space-between;margin-bottom: 10px;">
            <span style="font-size: 25px">{{name}}</span>
            <span>
              <span style="color: #8c8c8c"><icon-svg icon-class="auth"/>&nbsp;乐游景区认证</span>
            </span>
          </div>

          <div style="width: 55%;display: flex;justify-content: space-between">
            <div v-if="value!==0" style="width: 60%;display: flex;justify-content: space-between">
              <div><el-rate v-model="value" disabled show-score text-color="#ff9900" score-template="{value}"></el-rate></div>
              <div><span><span v-for="index in value" :key="index">A</span>级景区</span></div>
            </div>
            <div v-else>
              <div style="color: #8c8c8c;margin-top: 3px"><icon-svg icon-class="jingqu" style="font-size: 16px"/>&nbsp;乐游特色景区</div>
            </div>
            <div><el-link :underline="false" type="info" href="#mao">共有{{num}}条精选评论</el-link></div>
          </div>
          <el-divider/>

          <div>
            <el-row v-for="(item,index) in form" :key="item">
              <el-col style="margin-bottom: 10px" v-show="item.info!==''">{{item.name}}：{{item.info}}
                <el-link :underline="false" v-if="index===0"><icon-svg icon-class="dingwei1" id="dingwei"></icon-svg></el-link></el-col>
            </el-row>
            <el-divider/>
          </div>

          <div>
            <div>
              <span style="color: #ea3c1a"><span style="font-size: 30px">￥{{price}}</span>&nbsp;/人</span>
              <el-tooltip class="item" effect="dark" content="身高1.5以下" placement="top">
                <span>（儿童半价￥{{price/2}}）</span>
              </el-tooltip>
              <span style="color: #8a8a8a" v-if="ticket>0" v-show="form[3].info!=='不限'">门票数&nbsp;{{ticket}}</span>
              <span style="color: #8a8a8a" v-else v-show="form[3].info!=='不限'">门票已告罄</span>
            </div>

            <div style="margin-top: 20px">
              <span>出行人数：</span>
              <span v-if="form[3].info!=='不限'&&ticket>0||form[3].info==='不限'">
                <el-input-number v-model="ticket_nums_adult" :min="1" size="mini" controls-position="right" style="width: 85px"></el-input-number>&nbsp;成人&emsp;
                <el-input-number v-model="ticket_nums_kid" :min="0" size="mini" controls-position="right" style="width: 85px"></el-input-number>&nbsp;儿童
              </span>
              <span v-else>
                <el-input-number  size="mini" controls-position="right" style="width: 85px" :disabled="true"></el-input-number>&nbsp;成人&emsp;
                <el-input-number  size="mini" controls-position="right" style="width: 85px" :disabled="true"></el-input-number>&nbsp;儿童
              </span>
            </div>

            <div style="margin-top: 20px">
              <div v-if="$store.getters.userInfo">
                <el-button type="primary" round icon="el-icon-s-goods" v-if="form[3].info!=='不限'&&ticket>0||form[3].info==='不限'" @click="shop">点击购买</el-button>
                <el-button type="info" round icon="el-icon-s-goods" v-else>暂无门票</el-button>
                <el-button type="danger" round icon="el-icon-star-on" @click="join">加入收藏</el-button>
              </div>
              <div v-else><el-tag effect="dark">你好，请先登录</el-tag></div>
            </div>
          </div>
        </div>

        <div class="img-info">
          <el-image :src="imgs.length===0?'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg':imgs[0]" fit="contain" style="width: 350px;" :preview-src-list="srcList"></el-image>
          <div class="imgs">
            <el-image :src="imgs.length===0?'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg':imgs[index]" v-for="index in 5" :key="index" style="width: 65px;" :preview-src-list="srcList"></el-image>
          </div>
          <el-card  style="margin-top:15px;" :body-style="{ padding: '10px' }">
            <div style="font-size: 16px;margin-bottom: 10px;color: #409eff;font-weight: bold"><i class="el-icon-guide"/>&nbsp;景区公告</div>
            <el-row>
              <el-col :span="24">
                <div style="width: 330px;font-size: 14px" v-if="desc">{{desc}}</div>
                <div style="width: 330px;font-size: 14px" v-else>{{name}}欢迎您</div>
              </el-col>
            </el-row>
          </el-card>
        </div>
      </div>
    </el-card>
    <div class="sm">
      <comment :display="true" style="width: 76%;" @nums="nums"/>
      <div style="width: 23%;margin-top: 50px">
        <el-card shadow="never" style="height: 600px">
          <div>
            <span style="font-weight: bold">当地游记 · {{citys[1]}}</span>
            <el-divider/>
            <div v-if="localTravel.length===0">该地暂无游记</div>
            <div v-else v-for="item in localTravel" :key="item">
              <el-link :underline="false" :href="'/travel?id='+item.id" style="margin-top: 20px;line-height: 150%">
                <icon-svg icon-class="bianjibiaodanx" style="color: #167a14;font-size: 16px"/>&nbsp;{{item.title}}</el-link>
              <el-row style="color: #8c8c8c;margin-top: 5px">
                <el-col :span="5">&nbsp;<icon-svg icon-class="yanjing"/> {{item.click>=1000?String(item.click).substring(0,1)+'k':item.click}}</el-col>
                <el-col :span="6"><icon-svg icon-class="dianzan1" style="color: #faa022"/> {{item.good>=1000?String(item.good).substring(0,1)+'k':item.good}}</el-col>
              </el-row>
            </div>
            <div v-if="localTravel.length<=3" style="margin-top: 40px">
              <div style="font-weight: bold;margin-bottom: 10px">推荐游记~</div>

              <div v-for="item in recommends" :key="item">
                <el-link :underline="false" :href="'/travel?id='+item.id" style="margin-top: 20px;line-height: 150%">
                  <icon-svg icon-class="bianjibiaodanx" style="color: #167a14;font-size: 16px"/>&nbsp;{{item.title}}</el-link>
                <el-row style="color: #8c8c8c;margin-top: 5px">
                  <el-col :span="5">&nbsp;<icon-svg icon-class="yanjing"/> {{item.click>=1000?String(item.click).substring(0,1)+'k':item.click}}</el-col>
                  <el-col :span="6"><icon-svg icon-class="dianzan1" style="color: #faa022"/> {{item.good>=1000?String(item.good).substring(0,1)+'k':item.good}}</el-col>
                </el-row>
              </div>
            </div>
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script>
  import Comment from "../../components/common/comment/Commentlist";
  import {baseURL} from "../../utils/constant";
  export default {
    name: "Content",
    components:{
      Comment
    },
    data(){
      return{
        aid:'',
        value:0,
        imgs:[],
        srcList:[],
        name:'',
        num:0,
        desc:'',
        form:[
          {name:'地址',info:''},
          {name:'电话',info:''},
          {name:'营业时间',info:''},
          {name:'当日游客限量',info:''}
        ],
        price:0,
        ticket:800,
        ticket_nums_adult:1,
        ticket_nums_kid:0,
        citys:[],
        localTravel:[],
        recommend:[],
      }
    },
    watch:{

    },
    created() {
      if(this.$route.query.aid){
        this.$axios.get(baseURL+'/shopDisplay',{params:{aid:this.$route.query.aid}}).then(res=>{
          if(res.data!=='err'){
            console.log(res.data)
            this.aid = res.data.id
            this.value = res.data.star
            this.name = res.data.name
            this.desc = res.data.descs
            this.price = parseInt(res.data.price)
            this.form[0].info = res.data.site
            this.form[1].info = res.data.tel
            this.form[2].info = res.data.date+' '+res.data.opentime+'-'+res.data.closetime
            this.form[3].info = res.data.nums===0?'不限':res.data.nums
            this.citys[0] = String(res.data.site).split('省')[0]+'省'
            this.citys[1] = String(res.data.site).indexOf('市')>=0?String(res.data.site).substring(String(res.data.site).indexOf('省')+1,String(res.data.site).indexOf('市'))+'市':String(res.data.site).substring(String(res.data.site).indexOf('省')+1,String(res.data.site).indexOf('州'))+'州'
            this.citys[2] = res.data.name

            this.$axios.get(baseURL+'/displayLocalTravel',{params:{city:this.citys[1]}}).then(res=>{
              if(res.data.length!==0){
                this.localTravel = res.data
              }
              let _this=this
              if(res.data.length<=3){
                this.$axios.get(baseURL+'/queryNotCityOrderByClick',{params:{city:this.citys[1]}}).then(res=>{
                  this.recommend = res.data
                })
              }
            })

            this.$axios.get(baseURL+'/getPictureByAName',{params:{name:this.name}}).then(res=>{
              for(let i in res.data){
                this.imgs.push(res.data[i])
                this.srcList.push(res.data[i])
              }
            })
          }else {
            this.$router.push('/err')
          }
          // console.log(res.data.nums);
        })

        // this.$axios.get(baseURL+'/dnf',{params:{aid:this.$route.query.aid}}).then(res=>{
          // console.log(res.data.comment)
          // this.nums = res.data.comment.length
          // this.comment=JSON.parse(JSON.stringify(res.data.comment))
          // this.ByTime=JSON.parse(JSON.stringify(res.data.comment))
          // for (let i of this.comment){
          //   if(i.star===5||i.star===4){
          //     this.hao.push(i)
          //   }else if(i.star===2||i.star===3){
          //     this.medium.push(i)
          //   }else {
          //     this.cha.push(i)
          //   }
          // }
          // this.comment.sort((a,b)=>{
          //   return a.star < b.star ? 1 : -1;
          // })
          // this.ByTime.sort((a,b)=>{
          //   return a.times < b.times ? 1 : -1;
          // })
          // console.log(res.data.rows[0].comment)
          // console.log(this.comment[0]);
        // })

      }else {
        this.$router.push('/err')
      }

    },
    computed:{
      recommends(){
        console.log(789)
        console.log(this.recommend)
        let i = 6-this.localTravel.length;
        let info=[]
        for(let k =0;k<i;k++){
          info.push(this.recommend[k])
        }
        return info
      }
    },
    methods:{
      join(){
        console.log(this.$store.state.area)
        this.$axios.get(baseURL+'/addCollect',{params:{uid:this.$store.getters.userInfo.id,aid:this.$route.query.aid}}).then(res=>{
          if(!res.data.err){
            this.$message.success('成功加入心愿单')
          }
        })
      },
      shop(){
        let money = this.ticket_nums_kid * this.price/2 + this.ticket_nums_adult * this.price;
        this.$router.push({ name: '结算', params: {money:money,aid:this.$route.query.aid,nums:this.ticket_nums_kid+this.ticket_nums_adult}})
      },
      nums(nums){
        this.num = nums
      }
    }
  }
</script>

<style scoped lang="scss">
  *{
    font-size: 14px;
  }
  .row{
    width: 80%;
    margin: 0 auto;
  }
  .el-breadcrumb{
    margin-left: 5px;
  }
  .card1{
    margin-top: 13px;
  }
  .imgs{
    width: 350px;
    display: flex;
    justify-content: space-between;
    margin-top: 4px;
  }
  .img-info{
    /*margin-top: 50px;*/
  }
  .text-info{
    width: 100%;
    /*border: 1px solid red;*/
    margin-right: 5%;
  }
  .card-content{
    display: flex;
    width: 100%;
    /*border: 1px solid red;*/
    justify-content: space-between;
  }
  #dingwei{
    font-size: 16px;
  }
  .hot{
    margin-top: 50px
  }
  .sm{
    display: flex;
    justify-content: space-between;
    margin-top: 30px;
  }
</style>

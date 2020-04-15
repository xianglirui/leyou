<template>
  <div>
    <el-card shadow="never">
    <el-menu :default-active="this.$route.path" :router="true" mode="horizontal">
      <el-menu-item :index="item.path" v-for="item in navList" :key="item" style="margin-right: 70px;font-size: 20px">{{item.name}}</el-menu-item>
    </el-menu>

    <div style="margin-top: 20px"></div>
    <div style="margin-top: 45px" v-show="$route.path==='/user/article/travel_notes'">
      <el-row>
        <el-col :span="2">&nbsp;</el-col>
        <el-col :span="17"><div style="margin-top: 5px"><icon-svg icon-class="shijie" style="font-size: 25px"/>&emsp;<span style="color: #a3a3a3">乐游-分享你所见的世界</span></div></el-col>
        <el-col :span="5"><el-button type="danger" round @click="article_edit"><i class="el-icon-edit"/>&nbsp;写游记</el-button></el-col>
      </el-row>
      <el-divider/>
    </div>

    <div v-if="isShow">

      <el-row v-for="item in route_list" :key="item">
        <el-col :span="24">
          <!--投诉-->
          <order-item v-if="$route.path==='/user/complaint'">
            <div slot="img" style="width: 500px"><el-link :underline="false" style="position: relative;top: 35px;left: 20px" @click="complaint_name(item)">投诉序列号：{{item.id}}</el-link></div>
            <div slot="money" style="position: relative;left: -100px"><el-tag v-for="sm in complaintD(item)" :key="sm" style="margin-right: 10px">{{sm}}</el-tag></div>
            <div slot="opera">
              <el-row>
                <el-col :span="6"><div v-text="deal_state(item).name"  style="position: relative;top: 8px"></div></el-col>
                <el-col :span="14" style="position: relative;top: 7px">
                  <el-tooltip class="item" effect="dark" :content="deal_state(item).link" placement="top" v-show="deal_state(item).link">
                    <el-link :underline="false" type="primary" style="font-size: 12px">|&nbsp;处理结果信息</el-link>
                  </el-tooltip>
                </el-col>
              </el-row>
            </div>
          </order-item>

          <!--我的评论-->
          <order-item v-else-if="$route.path==='/user/article'">
            <div slot="img" style="width: 800px;height: 20px;position: relative;top: 25px;left:20px;overflow: hidden;">
              <icon-svg icon-class="bianjibiaodanx" style="color: #409eff;font-size: 20px"/>&ensp;评论表序号：{{item.id}}</div>
            <div slot="time" style="position: relative;left: -65px;top: 30px"><i class="el-icon-time"/>&nbsp;{{item.times}}</div>
            <div slot="money">&nbsp;</div>
            <div slot="opera"><el-link type="primary" @click="lookComment(item)">查看评论</el-link></div>
          </order-item>

          <!--我的游记-->
          <order-item v-else-if="$route.path==='/user/article/travel_notes'">
            <div slot="img" style="width: 800px;height: 20px;position: relative;top: 25px;left:20px;overflow: hidden;">
              <icon-svg icon-class="bianjibiaodanx" style="color: #409eff;font-size: 20px"/>&ensp;{{item.title}}</div>
            <div slot="time" style="position: relative;left: -65px;top: 30px"><i class="el-icon-time"/>&nbsp;{{item.times}}</div>
            <div slot="money">点赞数：{{item.good}}</div>
            <div slot="opera"><el-link type="primary" @click="lookArticle(item)">查看游记</el-link></div>
          </order-item>

          <!--收藏-->
          <order-item v-else-if="String($route.path).startsWith('/user/collect')">
            <div slot="img" style="position: relative;left:30px;width: 500px;">
              <el-link :underline="false" v-if="String($route.path).endsWith('/blog')">
                <icon-svg icon-class="bianjibiaodanx" style="color:#00c800;font-size: 25px;position: relative;top:5px"/>&ensp;收藏游记编号：{{item.tid}}</el-link>
              <el-link :underline="false" v-else>
                <icon-svg icon-class="jingqu" style="font-size: 25px;position: relative;top:5px"/>&ensp;收藏景区编号：{{item.aid}}</el-link>
            </div>
            <div slot="time" style="position: relative;left: -50px;top: 15px">收藏时间：{{item.times}}</div>
            <div slot="money">&nbsp;</div>
            <div slot="opera" style="position: relative;left: -80px">
              <el-link type="primary" @click="goLook(item)">去看看</el-link>&ensp;|&ensp;<el-link type="primary" @click="removeList(item)">移出心愿单</el-link></div>
          </order-item>

          <!--订单-->
          <order-item v-else>
            <div slot="img"><el-avatar :size="90" :src="url"></el-avatar></div>
            <div slot="name">{{item.area.name}}一日游</div>
            <div slot="time">下单时间：{{item.time}}</div>
            <div slot="nums">数量：{{item.nums}}</div>
            <div slot="money">总金额：￥{{item.account}}</div>
            <div slot="opera">
              <div v-if="$route.path==='/user/order'">{{state_text(item).name}}</div>
              <div v-else-if="$route.path==='/user/order/refund'">
                <el-row>
                  <el-col :span="6">{{state_text(item).name}}</el-col>
                  <el-col :span="12" v-show="item.refund===2">
                    <el-link :underline="false" type="primary" @click="$router.push({path:'/complaint',query:{customer:'refund',orderID:item.id}})">
                      <span style="font-size: 12px;position: relative;top: -1px">退款未到账?</span>
                    </el-link>
                  </el-col>
                </el-row>
              </div>
              <div v-else>
                <span v-show="state_text(item).name!==null">{{state_text(item).name}}</span>
                <el-link :underline="false" type="primary" style="font-size: 12px" @click="tiao(state_text(item).link,item)">{{state_text(item).link}}</el-link>
              </div>
            </div>
          </order-item>
          <el-divider></el-divider>
        </el-col>
      </el-row>
    </div>
    <div v-else style="position: relative;left: 40%">
      <icon-svg icon-class="box-package-parcel-office-open-unpack-delivery-ebfdc"/>
      <span style="color: #8c8c8c;font-size: 14px">这里是空的</span>
    </div>
    </el-card>
    <el-pagination background layout="prev, pager, next" :total="50" style="margin-top: 20px" v-show="false"></el-pagination>

  </div>
</template>

<script>
  import OrderItem from "../../../components/content/orderItem/OrderItem";
  import {baseURL} from "../../../utils/constant";
  export default {
    name: "Main",
    components:{OrderItem},
    data(){
      return{
        navList:[],
        info:this.$store.state.customer.info,
        evaluate:this.$store.state.customer.evaluate,
        use:this.$store.state.customer.use,
        refund:this.$store.state.customer.refund,
        complaint:this.$store.state.customer.complaint,
        article:this.$store.state.customer.article,
        collect:this.$store.state.customer.collect,
        comment:this.$store.state.customer.comment,
        collect_aid:'',
        collect_tid:'',
        mess:'',
        url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
        complaint_name_flag:true
      }
    },
    computed:{
      route_list(){
        let str = this.$route.path
        if(str==='/user/order'){
          return this.info
        }
        if(str==='/user/order/use'){
          return this.use
        }
        if(str==='/user/order/evaluate'){
          return this.evaluate
        }
        if(str==='/user/order/refund'){
          return this.refund
        }
        if(str==='/user/complaint'){
          return this.complaint
        }
        if(str==='/user/article/travel_notes'){
          return this.article
        }
        if(String(str).endsWith('/blog')){
          return this.collect_tid
        }
        if(String(str).endsWith('/collect')){
          return this.collect_aid
        }
        if(str==='/user/article'){
          return this.comment
        }
      },
      isShow(){
        let str = this.$route.path
        return str === '/user/order' && this.info.length !== 0 || str === '/user/order/use' && this.use.length !== 0 ||
          str === '/user/order/evaluate' && this.evaluate.length !== 0 || str === '/user/order/refund' && this.refund.length !== 0
          || str==='/user/complaint' && this.complaint.length!==0 || str==='/user/article/travel_notes' && this.article.length!==0
          || String(str).endsWith('/blog') && this.collect_tid.length!==0 ||String(str).endsWith('/collect') && this.collect_aid.length!==0
          || str==='/user/article'&&this.comment.length!==0;
      },
    },
    created() {
      this.collect_tid = this.collect.filter(c=>{
        return c.tid!==0
      });
      this.collect_aid =this.collect.filter(c=>{
        return c.aid!==0
      });
      // if(this.info.length===0||this.use.length===0||this.evaluate.length===0||this.refund.length===0){
      //   this.$router.push('/user')
      // }
      // console.log(this.article);
      this.routeItem()
    },
    watch:{
      $route(){
        this.routeItem()
      }
    },
    methods:{
      complaint_name(item){
        // if(this.complaint_name_flag){
        //   if(item.oid!==0&&item.aid===0&&item.tid===0){
        //     let info=''
        //     for(let i of this.$store.state.customer.info){
        //       if(i.id===item.oid){
        //         return '门票：'+i.area.name+'一日游'
        //       }
        //     }
        //   }
        //   if(item.aid!==0&&item.oid===0&&item.tid===0){
        //     let _this = this
        //     this.$axios.get(baseURL+'/selectById',{params:{param:1,id:item.aid}}).then(res=>{
        //       _this.mess = res.data
        //     })
        //     return '景区：'+this.mess
        //   }
        //   if(item.aid===0&&item.oid===0&&item.tid!==0){
        //     this.$axios.get(baseURL+'/getArticle',{params:{id:item.tid}}).then(res=>{
        //       _this.mess = res.data.article.title
        //     })
        //     return '游记：'+this.mess
        // }
        // this.complaint_name_flag=false
        // }
        if(item.oid!==0){
          this.$router.push('/user/order/refund')
        }
        if(item.tid!==0){
          this.$router.push({path:'/travel',query:{id:item.tid}})
        }
        if(item.aid!==0){
          this.$router.push({path:'/display',query:{aid:item.aid}})
        }
      },
      complaintD(item){
        let info = []
        if (item.types===1){
          info.push('游记投诉')
        }
        if (item.types===2){
          info.push('退款未到账')
        }
        if (item.types===3){
          info[0]='景区投诉'
          info[1]=item.additional
        }
        return info
      },
      deal_state(item){
        let info={name:'',link:''}
        if(item.state===0||item.state===1){
          info.name = '处理中'
        }else {
          info.name = '已处理'
          info.link = item.feedback
        }
        return info
      },
      routeItem(){
        this.navList=[]
        let str = this.$route.path
        if(str.match('order')){
          this.navList.push({name:'全部订单',path:'/user/order'},
            {name:'待使用',path:'/user/order/use'},
            {name:'待评价',path:'/user/order/evaluate'},
            {name:'退款信息',path:'/user/order/refund'})
        }
        if(str.match('collect')){
          this.navList.push({name:'收藏的景区',path:'/user/collect'},
            {name:'收藏的游记',path:'/user/collect/blog'})
        }
        if(str.match('article')){
          this.navList.push({name:'我的评论',path:'/user/article'},
            {name:'我的游记',path:'/user/article/travel_notes'})
        }
        if(str.match('complaint')){
          this.navList.push({name:'我的投诉',path:'/user/complaint'})
        }
      },

      state_text(item){
        let info = {name:'',link:''}
        if(item.state===0){
          info.name='待使用'
          info.link='退款'
        }
        if(item.state===1){
          info.name='待评价'
          info.link='去评价'
        }
        if(item.state===2){
          info.name='已评论'
          info.link='查看评论'
        }
        if(item.state===4){
          info.name='已失效'
        }
        if(item.refund===1){
          info.name='退款中'
        }
        if(item.refund===2){
          info.name='已退款'
        }
        if(item.refund===3){
          info.name='未到账已处理'
        }
        return info
      },
      tiao(link,item){
        let routeUrl
        if(link==='查看评论'){
          routeUrl=this.$router.resolve({
            path:'/display#mao',
            query:{
              aid:item.area.id
            },
          })
          window.open(routeUrl.href, '_blank');
        }
        if(link==='退款'){
          this.$router.push({
            // path:'/
            name:'Refund',
            params:{
              money:item.account,
              oid:item.oid,
              id:item.id,
              time:item.time,
              payWay:item.payWay
            }
          })
        }
        if(link==='去评价'){
          this.$router.push({name:'edit_comment',params:{name:item.area.name,aid:item.area.id, oid:item.id}})
        }
      },
      article_edit(){
        let routeUrl=this.$router.resolve({
          path:'/article_edit'
        })
        window.open(routeUrl.href, '_blank');
      },
      lookArticle(item){
        // console.log(item)
        this.$router.push({path:'/travel',query:{id:item.id}})
      },
      lookComment(item){
        if(item.aid!==0){
          this.$router.push({path:'/display#mao',query:{aid:item.aid}})
        }
        if(item.tid!==0){
          this.$router.push({path:'/travel#mao',query:{id:item.tid}})
        }
      },

      goLook(item){
        if(this.$route.path==='/user/collect'&&item.aid!==0){
          this.$router.push({path:'/display',query:{aid:item.aid}})
        }
        if(this.$route.path==='/user/collect/blog'&&item.tid!==0){
          this.$router.push({path:'/travel',query:{id:item.tid}})
        }
      },
      removeList(item){
        console.log(item)
        this.$axios.get(baseURL+'/removeCollect',{params:{aid:item.aid,tid:item.tid}}).then(res=>{
          if(res.data==='ok'){
            location.reload()
          }
        })
      }
    },
  }
</script>

<style scoped>

</style>

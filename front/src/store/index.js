import Vue from 'vue';
import Vuex from 'vuex';
import {asyncRouterMap,constantRouterMap} from "../router";

Vue.use(Vuex);


const store = new Vuex.Store({
  state: {
    //储存是否进行了邮箱验证
    mailState: localStorage.getItem('mailState') ? localStorage.getItem('mailState') : '',
    //折叠信息
    isCollapse:false,
    //area对象
    area:null,
    customer:{info:[],use:[],evaluate:[], refund:[],complaint:[],article:[],collect:[],comment:[]},

    routers: constantRouterMap,
    addRouters: [],
    userInfos:'',

    areaInfo:{refund:[],complaint:[],comment:[], txt:[]},

    hotArea:[],
    hotArea1:[],
    hotArticle:[]

  },
  getters:{
    StepInfo:state => {
      return state.Step
    },
    addRouters:state => {
      return state.addRouters
    },
    userInfo:state => {
      return state.userInfos
    }
  },
  mutations: {
    // 修改token，并将token存入localStorage
    changeLogin (state, user) {
      state.Authorization = user.Authorization;
      localStorage.setItem('Authorization', user.Authorization);
    },

    changeMailState(state,payload){
      state.mailState = payload;
      localStorage.setItem('mailState',payload)
    },

    changeIsCollapse(state,payload){
      state.isCollapse = payload;
    },

    changeArea(state,payload){
      state.area = payload
    },
    changeInfo(state,payload){
      state.customer.info=payload
    },
    changeEvaluate(state,payload){
      state.customer.evaluate=payload
    },
    changeUse(state,payload){
      state.customer.use=payload
    },
    changeRefund(state,payload){
      state.customer.refund=payload
    },

    changeComplaint(state,payload){
      state.customer.complaint=payload
    },
    SET_ROUTERS: (state, routers) => {
      state.addRouters = routers;
      state.routers = constantRouterMap.concat(routers);
    },
    SET_COOKIE:(state, payload) => {
      state.cookie = payload
    },
    SET_USER:(state,payload)=>{
      state.userInfos = payload
    },
    SET_ARTICLE:(state,payload)=>{
      state.customer.article = payload
    },
    SET_COLLECT:(state,payload)=>{
      state.customer.collect = payload
    },
    SET_COMMENT:(state ,payload)=>{
      state.customer.comment=payload
    },
    SET_Refund:(state ,payload)=>{
      state.areaInfo.refund=payload
    },
    SET_Complaint:(state ,payload)=>{
      state.areaInfo.complaint=payload
    },
    SET_COMMENTS:(state ,payload)=>{
      state.areaInfo.comment=payload
    },
    SET_TXT:(state ,payload)=>{
      state.areaInfo.txt=payload
    },
    SET_HOTAREA:(state ,payload)=>{
      state.hotArea = payload
    },
    SET_HOTAREA1:(state ,payload)=>{
      state.hotArea1 = payload
    },
    SET_HOTARTICLE:(state ,payload)=>{
      state.hotArticle = payload
    }
  },
  actions:{
    GenerateRoutes({ commit }, data) {
      return new Promise(resolve => {
        const accessedRouters = asyncRouterMap.filter(v => {
          return v.path===data.path
        });
        commit('SET_ROUTERS', accessedRouters);
        resolve();
      })
    }
  }

});

export default store;

import Vue from 'vue'
import App from './App'
import router, {asyncRouterMap, constantRouterMap, GenerateRoutes} from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import store from './store'
import axios from 'axios';
import Vuex from 'vuex'
import { MessageBox, Message } from 'element-ui'

import IconSvg from '@/components/common/iconsvg/IconSvg'
import VueSimplemde from 'vue-simplemde'
import 'simplemde/dist/simplemde.min.css'
Vue.use(VueSimplemde)
//全局注册icon-svg
Vue.component('icon-svg', IconSvg)
import VueCookies from 'vue-cookies'
import {getToken, setUser} from "./utils/token";



Vue.use(VueCookies)


Vue.config.productionTip = false
Vue.prototype.$axios = axios;
axios.defaults.headers.post['Content-Type'] = 'application/json';
Vue.use(ElementUI);
Vue.use(Vuex) ;



// 添加请求拦截器，在请求头中加token
axios.interceptors.request.use(
  config => {
    if (getToken()) {
      config.headers['token'] = getToken() // 让每个请求携带token--['X-Token']为自定义key 请根据实际情况自行修改
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  });
axios.interceptors.response.use(
  response=>{
    const res = response.data;
    // console.log(res)
    if(res.code==='401'){
      Message({
        message: '出错了，请重新登录',
        type: 'error',
        duration: 5 * 1000
      })
      return response
    }else if(res.code==='200'){
      setUser(res.userInfo.role)
      store.commit('SET_USER',res.userInfo)
      return response
    }else {
      return response
    }

  }
)



/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})

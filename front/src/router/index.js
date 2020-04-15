import Vue from 'vue'
import Router from 'vue-router'
import jsCookie from 'js-cookie';
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

//全局进度条的配置
NProgress.configure({
  showSpinner: true,  //加载微调器设置,默认为true
  //使用缓动（CSS缓动字符串）和速度（以毫秒为单位）调整动画设置。（默认：ease和200）
  easing: 'ease',
  speed: 2000,
  minimum: 0,  //更改启动时使用的最小百分比
})


Vue.use(Router)

const Home = () => import('@/views/home/Home')
const Login = () => import('@/views/login/Login')
const Register = () => import('@/views/login/Register')
const Reset = () => import('@/views/login/Reset')
const Verify = () => import('@/views/login/Verify')
const Apply = () => import('@/views/apply/Apply')
const Area = () => import('@/views/area/Area')
const Display = () => import('@/views/shop/Display')
const Customer = () => import('@/views/customer/Customer')
const MainContent = ()=>import('views/customer/children/MainContent')


export const constantRouterMap =[
  {
    path: '',
    redirect: '/home'
  },
  {
    path:'/tip',
    component:()=>import('components/common/404/tip')
  },
  {
    path: '/home',
    name:Home,
    component: Home
  },
  {
    path:'/searchList',
    component:() => import('@/views/home/SearchList')
  },
  {
    path:'/complaint',
    component:()=>import('components/common/complaint/Complaint')
  },
  {
    path: '/err',
    component: () => import('@/components/common/404/404Page')
  },
  {
    path: '/download',
    component: () => import('components/common/download/Download')
  },
  {
    path: '/scenic',
    component: () => import('components/common/center/PlaceCenter')
  },
  {
    path:'/apply',
    name:'apply',
    component:Apply,
    // meta: { role: ['1'] }, //页面需要的权限
  },
  {
    path:'/login',
    name:'Login',
    component: Login
  },
  {
    path:'/register',
    name:'Register',
    component: Register
  },
  {
    path:'/verify/reset',
    component: Reset
  },
  {
    path:'/verify',
    component:Verify
  },
  {
    path:'/edit_comment',
    name:'edit_comment',
    component:()=>import('views/customer/children/commentPage')
  },
  {
    path:'/refund',
    name:'Refund',
    component:()=>import('views/customer/children/refund')
  },
  {
    path:'/travel',
    name:'travel',
    component:()=>import('views/travel/Travel')
  },
  {
    path:'/display',
    component:Display,
    redirect:"/display",
    children:[{
      path:'',
      name:'display',
      component:()=> import('views/shop/Content')
    },{
      path:'shopping',
      name:'shopping',
      component:()=> import('views/shop/Shopping')
    }]
  },

]

//异步挂载的路由
//动态需要根据权限加载的路由表

export const asyncRouterMap = [
  {
    path:'/article_edit',
    name:'article_edit',
    meta: { role: ['0'] }, //页面需要的权限
    component:()=>import('views/edit/Edit')
  },
  {
    path:'/user',
    component:Customer,
    meta: { role: ['0'] }, //页面需要的权限
    redirect:"/user",
    children:[{
      path:'',
      name:'index',
      component:()=>import('views/customer/children/index')
    },{
      path:'order',
      name:'order',
      meta: { role: ['0'] }, //页面需要的权限
      component:MainContent,
      children:[{
        path:'',
        name:'all',
        component:MainContent
      },{
        path:'use',
        name:'use',
        meta: { role: ['0'] }, //页面需要的权限
        component:MainContent
      },{
        path:'evaluate',
        name:'evaluate',
        meta: { role: ['0'] }, //页面需要的权限
        component:MainContent
      },{
        path:'refund',
        name:'refund',
        meta: { role: ['0'] }, //页面需要的权限
        component:MainContent
      }]
    },{
      path:'collect',
      name:'收藏',
      meta: { role: ['0'] }, //页面需要的权限
      component:MainContent,
      children:[{
        path:'',
        name:'area',
        component:MainContent
      },{
        path:'blog',
        name:'blog',
        meta: { role: ['0'] }, //页面需要的权限
        component:MainContent
      }]
    },{
      path:'article',
      name:'文章',
      meta: { role: ['0'] }, //页面需要的权限
      component:MainContent,
      children:[{
        path:'',
        name:'comment',
        component:MainContent
      },{
        path:'travel_notes',
        name:'travel_notes',
        meta: { role: ['0'] }, //页面需要的权限
        component:MainContent
      }]
    },{
      path:'account',
      name:'账户',
      component:()=>import('views/customer/children/Account')
    },{
      path:'complaint',
      name:'投诉',
      component:MainContent
    }]
  },
  {
    path:'/background',
    // redirect:'/background/page',
    name:'background',
    meta: { role: ['2','3','4','5'] },  //页面需要的权限
    component:()=>import('views/background/index'),
    children:[{
      path:'complaint',
      name:'Complaint',
      component:()=>import('views/background/complaint/Complaint'),
      meta: { role: ['2','3','4','5'] }  //页面需要的权限
    },{
      path:'',
      name:'myleyou',
      meta: { role: ['2','3','4','5'] }, //页面需要的权限
      component:()=>import('views/background/myLY/MyYL'),
    },{
      path:'co',
      name:'co',
      meta: { role: ['3','4','5'] }, //页面需要的权限
      component:()=>import('views/background/co'),
    },{
      path:'account',
      name:'account',
      meta: { role: ['4','5'] }, //页面需要的权限
      component:()=>import('views/background/account'),
    },{
      path:'boss',
      name:'boss',
      meta: { role: ['5'] }, //页面需要的权限
      component:()=>import('views/background/boss'),
    }]
  },

  {
    path:'/area',
    component:Area,
    name:'area',
    redirect:"/area",
    meta: { role: ['1'] }, //页面需要的权限
    children: [{
      path: '',
      name: 'area',meta: { role: ['1'] }, //页面需要的权限
      component: () => import('views/area/children/home/Home'),
    },{
      path: 'edit',
      name: '账户信息',meta: { role: ['1'] }, //页面需要的权限
      component: () => import('views/area/children/home/Account'),
    },{
      path:'form',
      name: '景区信息表',meta: { role: ['1'] }, //页面需要的权限
      component:()=>import('views/area/children/form/Form'),
    },{
      path:'revenue/table',
      name:'营收表',meta: { role: ['1'] }, //页面需要的权限
      component:()=>import('views/area/children/revenue/Table')
    },{
      path:'revenue/map',
      name:'营收图',meta: { role: ['1'] }, //页面需要的权限
      component:()=>import('views/area/children/revenue/Map')
    },{
      path:'customer/talk',
      name:'留言回复',meta: { role: ['1'] }, //页面需要的权限
      component:()=>import('views/area/children/customer/Talk')
    },{
      path:'customer/complain',
      name:'投诉处理',meta: { role: ['1'] }, //页面需要的权限
      component:()=>import('views/area/children/customer/Complain')
    },{
      path:'cooperation',
      name:'合作',meta: { role: ['1'] }, //页面需要的权限
      component:()=>import('views/area/children/cooperation/Cooperation')
    }]
  },
  {path: '*',redirect:'/err',hidden:true}
]
const createRouter = () => new Router({
  mode: 'history',
  routes: constantRouterMap
})

const router = createRouter()

export function hasPermission(role, route) {
  if (route.meta && route.meta.role) {
    return route.meta.role.indexOf(role.toString()) >= 0
  } else {
    return true
  }
}
export function GenerateRoutes(route,role) {
  return route.filter(v => {
    if (hasPermission(role, v)) {
      if (v.children && v.children.length > 0) {
        v.children = v.children.filter(c => {
          if (hasPermission(role, c)) {
            return c
          }
          return false
        });
        return v
      } else {
        return v
      }
    } else {
      return false
    }
  })
}


function whiteList(routes) {
  const Lists = []
  for(let i of routes){
    Lists.push(i.path)
    if(i.children&& i.children.length > 0){
      for(let k of i.children){
        if(k!==''){
          Lists.push(i.path+'/'+k.path)
          if(k.children&& k.children.length > 0){
            for(let m of k.children){
              if(m!==''){
                Lists.push(i.path+'/'+k.path+'/'+m.path)
              }
            }
          }
        }
      }
    }
  }
  return Lists
}

// 导航守卫
// 使用 router.beforeEach 注册一个全局前置守卫，判断用户是否登陆
router.beforeEach((to, from, next) => {
  //能读取的到store.state，但是读取不到store.state.userInfos，貌似因为vuex组件内通讯，router不是组件
  // console.log(router.app.$options.store.state.userInfos)
  console.log(whiteList(asyncRouterMap))
  if(whiteList(asyncRouterMap).indexOf(to.path)!==-1){
    if(jsCookie.get('Authorization')){
      if(jsCookie.get('user')){
        let i = GenerateRoutes(asyncRouterMap,jsCookie.get('user'))
        router.options.routes = constantRouterMap.concat(i)
        router.addRoutes(i)
        whiteList(asyncRouterMap).length=0
        jsCookie.set('user','')
        next(to)

      }else {
        next()
      }
    }else {
      next('/login')
    }
  }else {
    next()
  }

  if(whiteList(asyncRouterMap).indexOf(to.path)===-1&&whiteList(constantRouterMap).indexOf(to.path)===-1){
    next('/err')
    whiteList(asyncRouterMap).length=whiteList(constantRouterMap).length=0
  }else {
    next()
  }
  // next()
  // if (to.path === '/login') {
  //   next();
  // } else {
  //   // let token = localStorage.getItem('Authorization');
  //   let token = jsCookie.get('Token')
  //   console.log(token)
  //   if (!token) {
  //     next('/login');
  //   } else {
  //     next();
  //   }
  // }

  if(to.path === '/verify/reset' ){
    if(localStorage.getItem('mailState')===''){
      next('/verify')
    }
  }

  // constantRouterMap.filter(v =>{
  //   if(v.path===to.path){
  //     next()
  //   }else {
  //     next('/login')
  //   }
  // })
  // router.options.routes = constantRouterMap.concat(asyncRouterMap)
  //
  // // router.match = createRouter(constantRoutes).match;
  // router.addRoutes(asyncRouterMap)
  //   if (store.getters.token) { // 判断是否有token
  //     if (to.path === '/login') {
  //       next({ path: '/' });
  //     } else {
  //       if (store.getters.roles.length === 0) { // 判断当前用户是否已拉取完user_info信息
  //         store.dispatch('GetInfo').then(res => { // 拉取info
  //           const roles = res.data.role;
  //           store.dispatch('GenerateRoutes', { roles }).then(() => { // 生成可访问的路由表
  //             router.addRoutes(store.getters.addRouters) // 动态添加可访问路由表
  //             next({ ...to, replace: true }) // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
  //           })
  //         }).catch(err => {
  //           console.log(err);
  //         });
  //       } else {
  //         next() //当有用户权限的时候，说明所有可访问路由已生成 如访问没权限的全面会自动进入404页面
  //       }
  //     }
  //   } else {
  //     if (whiteList.indexOf(to.path) !== -1) { // 在免登录白名单，直接进入
  //       next();
  //     } else {
  //       next('/login'); // 否则全部重定向到登录页
  //     }
  //   }

});
// router.afterEach(() => {
//   NProgress.done()
// })


export default router

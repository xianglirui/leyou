// import { asyncRouterMap, constantRouterMap } from 'src/router';
//
// function hasPermission(roles, route) {
//   if (route.meta && route.meta.role) {
//     return roles.some(role => route.meta.role.indexOf(role) >= 0)
//   } else {
//     return true
//   }
// }
//
// const permission = {
//   state: {
//     routers: constantRouterMap,
//     addRouters: []
//   },
//   mutations: {
//     SET_ROUTERS: (state, routers) => {
//       state.addRouters = routers;
//       state.routers = constantRouterMap.concat(routers);
//     }
//   },
//   actions: {
//     GenerateRoutes({ commit }, data) {
//       return new Promise(resolve => {
//         const { roles } = data;
//         const accessedRouters = asyncRouterMap.filter(v => {
//           if (roles.indexOf('admin') >= 0) return true;
//           if (hasPermission(roles, v)) {
//             if (v.children && v.children.length > 0) {
//               v.children = v.children.filter(child => {
//                 if (hasPermission(roles, child)) {
//                   return child
//                 }
//                 return false;
//               });
//               return v
//             } else {
//               return v
//             }
//           }
//           return false;
//         });
//         commit('SET_ROUTERS', accessedRouters);
//         resolve();
//       })
//     }
//   }
// };

// export default permission;
import router from "../router";
import jsCookie from 'js-cookie';

const whiteList = ['/login']

router.beforeEach(async(to, from, next) => {

  const hasToken = jsCookie.get('Token')

  if (hasToken) {
    if (to.path === '/login') {
      next({ path: '/' })
    } else {
      const hasGetUserInfo = store.getters.name
      if (hasGetUserInfo) {
        next()
      } else {
        try {
          await store.dispatch('user/getInfo')
          next()
        } catch (error) {
          await store.dispatch('user/resetToken')
          next(`/login?redirect=${to.path}`)
        }
      }
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      next(`/login?redirect=${to.path}`)
    }
  }
})

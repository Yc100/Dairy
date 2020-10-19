/**
 * 核心
 * vue/vue-router
 */
import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter) //调用

/**
 * vuex
 */
import store from 'vuexStore/store.js'  //引用vuex

/**
 * 引入路由表
 */
import index from './modules/index.js'  //首页


/**
 *  配置路由
 */
const routes =  [
  ...index,
]

/**
 *  路由对象
 */
const router = new VueRouter({ // 使用配置文件规则
  routes,
  mode:'hash',
  strict: process.env.NODE_ENV !== 'production'
})


/**
 *  路由钩子
 */

/* 路由跳转前 */
router.beforeEach((to, from, next) => {
    if(localStorage.token != '') {  // 本地存储是否有token,
      if(store.getters.loginStatus){  //已获取用户信息
        console.log('router拦截器：用户已登录，可以跳转')
        next()
      }else{  //未获取用户信息
        store.dispatch('getUserInfo').then(()=>{
          console.log('router拦截器：获取用户信息中')
          next()
        })
      }
    }else{  //用户未登录
      next()
    }
})

/* 路由跳转后 */
router.afterEach(route => {
  window.scrollTo(0,0)  //跳转后返回页面顶部
})

export default router

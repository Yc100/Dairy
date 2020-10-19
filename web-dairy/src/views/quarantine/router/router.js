/**
 * 核心
 * vue/vue-router
 */
import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter) //调用
import store from 'vuexStore/store.js'  //引用vuex
// import { Message } from 'iview'

/**
 * 引入组件
 */

import epidemicStation from './modules/epidemicStation.js'  // 防疫站
import husbandryManage from './modules/husbandryManage.js'  //区级畜牧局

// 主页
const index = resolve => require(['views/quarantine/page/index.vue'], resolve) 




let route=[
  ...epidemicStation,
  ...husbandryManage,
]


/**
 *  配置路由
 */
const routes =  [
  {
      path: '/',
      redirect: '/index'
  },
  {
    path: '/index',
    component: index,
    name:'平台管理系统',
    meta: {
        loggedIn: true //登录验证
    },
    children: [
      ...route,
      // 独立页面路由
      // {
      //   name:'待审批列表',
      //   path: '/waitAuditList',
      //   component: waitAuditList,
      //   meta:{
      //   loggedIn:true,  //登录验证
      //   }+
      // },
    ]
  },
]


/**
 *  路由对象
 */
const router = new VueRouter({ // 使用配置文件规则
  routes,
  mode:'hash',
  strict: process.env.NODE_ENV !== 'production'
})


// 防止手动输入url越权限制
//orgClass 1-蒙牛集团 2-牧业公司 3-法人牧场 4-单体牧场
const checkPreMethod = (to,from,next)=>{
  // 涉及多页面跳转提前封装
  let loc = window.location
  let url = ''
  url = loc.protocol + '//' + loc.host + '/views/'    

  // 当前登录orgClass为1 蒙牛集团
  if(store.getters.userOrgInfo && store.getters.userOrgInfo.orgClass === 1){
    if(to.path === '/index' || to.path === '/MNPostManage' || to.path === '/MNRoleUser' || to.path ==='/MNPrivilege' || to.path === '/pastureListForMuye' || to.path === '/societyPastureList' || to.path === '/waitAuditList' || to.path === '/alreadyAuditList' || to.path === '/waitCooperatePasture'){
      next()
    }else{
      alert('您当前不具备进入该模块的权限！')
      window.location.href = url + 'index/index.html#/login'  //多页跳转登录
    }
  }
  // 当前登录者orgClass为2 牧业公司
  else if(store.getters.userOrgInfo && store.getters.userOrgInfo.orgClass === 2){
    if(to.path === '/index' || to.path === '/muyeCompanyInfo' || to.path === '/muyePostManage' || to.path === '/muyeRoleUser' || to.path === '/allPastureList' || to.path === '/waitAuditList' || to.path === '/alreadyAuditList' || to.path === '/applyRelateOption' || to.path === '/cancelRelateOption'|| to.path === '/muyeToPasturePostManage' || to.path === '/muyeToPastureRoleUser' || to.path === '/allPastureList/newPasture' || to.path === '/muyePlaceOrders' || to.path === '/legalPlaceOrders' || to.path === '/muyeAuditOrders' || to.path === '/legalAuditOrders' || to.path === '/personalPlaceORder'){
      next()
    }else{
      alert('您当前不具备进入该模块的权限！')
      window.location.href = url + 'index/index.html#/login'  //多页跳转登录
    }
  }
  // 当前登录者orgClass为3 法人牧场
  else if(store.getters.userOrgInfo && store.getters.userOrgInfo.orgClass === 3){
    if(to.path === '/index' || to.path === '/legalPastureInfo' || to.path === '/legalPostManage' || to.path === '/legalRoleUser' || to.path === '/singlePastureList' || to.path==='/singlePastureList/newSinglePasture' || to.path === '/applyRelateOption' || to.path === '/cancelRelateOption' || to.path === '/legalToSinglePostManage' || to.path==='/legalToSingleRoleUser' || to.path === '/waitAuditList' || to.path === '/alreadyAuditList'){
      next()
    }else{
      alert('您当前不具备进入该模块的权限！')
      window.location.href = url + 'index/index.html#/login'  //多页跳转登录
    }
  }
  // 当前登录者orgClass为4 单体牧场
  else if(store.getters.userOrgInfo && store.getters.userOrgInfo.orgClass === 4){
    if(to.path === '/index' || to.path === '/personalInfo' || to.path === '/applyRelateOption' || to.path === '/cancelRelateOption' || to.path === '/singlePostManage' || to.path === '/personalRoleUser' || to.path === '/waitAuditList' || to.path === '/alreadyAuditList'){
      next()
    }else{
      alert('您当前不具备进入该模块的权限！')
      window.location.href = url + 'index/index.html#/login'  //多页跳转登录
    }
  }
  // 当前登录者orgClass为5 admin
  else if(store.getters.userOrgInfo && store.getters.userOrgInfo.orgClass === 5){
    if(to.path === '/index' || to.path === '/MNPrivilege'  ){
      next()
    }else{
      alert('您当前不具备进入该模块的权限！')
      window.location.href = url + 'index/index.html#/login'  //多页跳转登录
    }
  }
  // 身份未知
  else{
    alert('您当前不具备进入该模块的权限！')
    window.location.href = url + 'index/index.html#/login'  //多页跳转登录
  }
}


/**
 *  路由钩子
 */

/* 路由跳转前 */
router.beforeEach((to, from, next) => {
    if(localStorage.token != '') {  // 本地存储是否有token,
      if(store.getters.loginStatus){  //已获取用户信息
        console.log('router拦截器：用户已登录，可以跳转')
        
        // 验证手动输入路由的权限限制
          
          // ----------------------------------------------路由防线----------------------------------------------
          // checkPreMethod(to,from,next);
          // ----------------------------------------------路由防线----------------------------------------------
        next()

      }else{  //未获取用户信息
        store.dispatch('getUserInfo').then(()=>{
          console.log('router拦截器：获取用户信息中')
          
          // 验证手动输入路由的权限限制
            // ----------------------------------------------路由防线----------------------------------------------
            // checkPreMethod(to,from,next);
            // ----------------------------------------------路由防线----------------------------------------------

          next()
        })
      }
    }else{  //用户未登录
      next({path:'login'})
    }
})

export default router

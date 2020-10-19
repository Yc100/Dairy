// author:zhangb
// time:2020-01-08
// desc:本页为防疫站提供各路由配置

// 防疫站个人管理
const epidemicInfo = resolve => require(['views/dairy/epidemicStation/epidemicInfo.vue'], resolve)
const datav = resolve => require(['views/dairy/epidemicStation/datav.vue'], resolve)

// 实际路由
const routes =  [
    {
        path: '/datav',
        component: datav,
        name:'平台管理系统',
        meta: {
            loggedIn: true //登录验证
        },
    },
    {
        path: '/epidemicInfo',
        component: epidemicInfo,
        name:'个人信息',
        meta: {
            loggedIn: true //登录验证
        },
    },

  ]

  export default routes
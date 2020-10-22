// author:zhangb
// time:2020-01-19
// desc:本页为区级畜牧局路由管理

const dairyManage = resolve => require(['views/dairy/dairyManage/dairyManage.vue'], resolve)
const dairyDetail = resolve => require(['views/dairy/dairyManage/dairyDetail.vue'], resolve)









// 实际路由
const routes =  [
    {
        path: '/dairyManage',
        component:dairyManage,
        name:'dairy信息',
        meta: {
            loggedIn: true //登录验证
        },
    },
    {
        path: '/dairyDetail',
        component:dairyDetail,
        name:'dairy详情',
        meta: {
            loggedIn: true //登录验证
        },
    },

  ]

  export default routes
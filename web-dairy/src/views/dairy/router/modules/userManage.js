// author:zhangb
// time:2020-01-19
// desc:本页为区级畜牧局路由管理

// 畜牧局个人信息管理
const userManage = resolve => require(['views/dairy/userManage/userManage.vue'], resolve)








// 实际路由
const routes =  [
    {
        path: '/userManage',
        component: userManage,
        name:'用户信息',
        meta: {
            loggedIn: true //登录验证
        },
    },

  ]

  export default routes
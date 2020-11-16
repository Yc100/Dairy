// author:zhangb
// time:2020-01-19
// desc:本页为区级畜牧局路由管理

const dairyManage = resolve => require(['views/dairy/dairyManage/dairyManage.vue'], resolve)
const myDairyManage = resolve => require(['views/dairy/dairyManage/myDairyManage.vue'], resolve)
const dairyDetail = resolve => require(['views/dairy/dairyManage/dairyDetail.vue'], resolve)
const editDairy = resolve => require(['views/dairy/dairyManage/editDairy.vue'], resolve)
const writeDairy = resolve => require(['views/dairy/dairyManage/writeDairy.vue'], resolve)
const nettyTest = resolve => require(['views/dairy/dairyManage/nettyTest.vue'], resolve)
const privateLetter = resolve => require(['views/dairy/dairyManage/privateLetter.vue'], resolve)
const myFocu = resolve => require(['views/dairy/dairyManage/myFocu.vue'], resolve)









// 实际路由
const routes =  [
    {
        path: '/dairyManage',
        component:dairyManage,
        name:'公开的Dairy',
        meta: {
            loggedIn: true //登录验证
        },
    },
    {
        path: '/myDairyManage',
        component:myDairyManage,
        name:'我的Dairy',
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
    {
        path: '/editDairy',
        component:editDairy,
        name:'编辑dairy',
        meta: {
            loggedIn: true //登录验证
        },
    },
    {
        path: '/writeDairy',
        component:writeDairy,
        name:'writeDairy',
        meta: {
            loggedIn: true //登录验证
        },
    },
    {
        path: '/nettyTest',
        component:nettyTest,
        name:'nettyTest',
        meta: {
            loggedIn: true //登录验证
        },
    },
    {
        path: '/privateLetter',
        component:privateLetter,
        name:'privateLetter',
        meta: {
            loggedIn: true //登录验证
        },
    },
    {
        path: '/myFocu',
        component:myFocu,
        name:'myFocu',
        meta: {
            loggedIn: true //登录验证
        },
    },


  ]

  export default routes
// author:zhangb
// time:2020-01-19
// desc:本页为区级畜牧局路由管理

// 畜牧局个人信息管理
const husbandryInfo = resolve => require(['views/quarantine/husbandryManage/husbandryInfo.vue'], resolve)
const epidemicStationManage = resolve => require(['views/quarantine/husbandryManage/epidemicStationManage.vue'], resolve)
const addNewEpidemicStation = resolve => require(['views/quarantine/husbandryManage/components/addNewEpidemicStation.vue'], resolve)
const arrangeStationVaccine = resolve => require(['views/quarantine/husbandryManage/arrangeStationVaccine.vue'], resolve)
const addNewStationVaccine = resolve => require(['views/quarantine/husbandryManage/components/addNewStationVaccine.vue'], resolve)








// 实际路由
const routes =  [
    {
        path: '/husbandryInfo',
        component: husbandryInfo,
        name:'个人信息',
        meta: {
            loggedIn: true //登录验证
        },
    },
    {
        path: '/epidemicStationManage',
        component: epidemicStationManage,
        name:'防疫站管理',
        meta: {
            loggedIn: true //登录验证
        },
    },
    {
        path: '/epidemicStationManage/addNewEpidemicStation',
        component: addNewEpidemicStation,
        name:'新增防疫站',
        meta: {
            loggedIn: true //登录验证
        },
    },
    {
        path: '/arrangeStationVaccine',
        component: arrangeStationVaccine,
        name:'疫苗管理',
        meta: {
            loggedIn: true //登录验证
        },
    },
    {
        path: '/arrangeStationVaccine/addNewStationVaccine',
        component: addNewStationVaccine,
        name:'增加疫苗发送记录',
        meta: {
            loggedIn: true //登录验证
        },
    },
  ]

  export default routes
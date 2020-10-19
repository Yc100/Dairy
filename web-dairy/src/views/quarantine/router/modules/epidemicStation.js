// author:zhangb
// time:2020-01-08
// desc:本页为防疫站提供各路由配置

// 防疫站个人管理
const epidemicInfo = resolve => require(['views/quarantine/epidemicStation/epidemicInfo.vue'], resolve)
const epidemicPersonList = resolve => require(['views/quarantine/epidemicStation/epidemicPersonList.vue'], resolve)
const addNewEpidemic = resolve => require(['views/quarantine/epidemicStation/components/addNewEpidemic.vue'], resolve)
const manageAreaList = resolve => require(['views/quarantine/epidemicStation/manageAreaList.vue'], resolve)
const animalManage = resolve => require(['views/quarantine/epidemicStation/animalManage.vue'], resolve)
const vaccineManage = resolve => require(['views/quarantine/epidemicStation/vaccineManage.vue'], resolve)
const arrangeVaccine = resolve => require(['views/quarantine/epidemicStation/arrangeVaccine.vue'], resolve)
const addNewSendVaccine = resolve => require(['views/quarantine/epidemicStation/components/addNewSendVaccine.vue'], resolve)
const animalClassify = resolve => require(['views/quarantine/epidemicStation/animalClassify.vue'], resolve)
const postList = resolve => require(['views/quarantine/epidemicStation/postList.vue'], resolve)
const exportAreaData = resolve => require(['views/quarantine/epidemicStation/exportAreaData.vue'], resolve)
const exportPersonData = resolve => require(['views/quarantine/epidemicStation/exportPersonData.vue'], resolve)
const exportStocksData = resolve => require(['views/quarantine/epidemicStation/exportStocksData.vue'], resolve)
const exportFarmerData = resolve => require(['views/quarantine/epidemicStation/exportFarmerData.vue'], resolve)
const farmerManager = resolve => require(['views/quarantine/epidemicStation/farmerManager.vue'], resolve)
const taskManager = resolve => require(['views/quarantine/epidemicStation/taskManager.vue'], resolve)
const checkTaskDetail = resolve => require(['views/quarantine/epidemicStation/checkTaskDetail.vue'], resolve)
const addNewTask = resolve => require(['views/quarantine/epidemicStation/addNewTask.vue'], resolve)
const newsManager = resolve => require(['views/quarantine/epidemicStation/newsManager.vue'], resolve)
const datav = resolve => require(['views/quarantine/epidemicStation/datav.vue'], resolve)

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
    {
        path: '/postList',
        component: postList,
        name:'岗位列表',
        meta: {
            loggedIn: true //登录验证
        },
    },
    {
        path: '/epidemicPersonList',
        component: epidemicPersonList,
        name:'员工列表',
        meta: {
            loggedIn: true //登录验证
        },
    },
    {
        path: '/epidemicPersonList/addNewEpidemic',
        component: addNewEpidemic,
        name:'新增员工',
        meta: {
            loggedIn: true //登录验证
        },
    },
    {
        path: '/manageAreaList',
        component: manageAreaList,
        name:'区域列表',
        meta: {
            loggedIn: true //登录验证
        },
    },
    {
        path: '/animalManage',
        component: animalManage,
        name:'畜禽管理',
        meta: {
            loggedIn: true //登录验证
        },
    },
    {
        path: '/farmerManager',
        component: farmerManager,
        name:'养殖户管理',
        meta: {
            loggedIn: true //登录验证
        },
    },
    {
        path: '/vaccineManage',
        component: vaccineManage,
        name:'疫苗管理',
        meta: {
            loggedIn: true //登录验证
        },
    },
    {
        path: '/arrangeVaccine',
        component: arrangeVaccine,
        name:'疫苗发放管理',
        meta: {
            loggedIn: true //登录验证
        },
    },
    {
        path: '/arrangeVaccine/addNewSendVaccine',
        component: addNewSendVaccine,
        name:'新增疫苗发放记录',
        meta: {
            loggedIn: true //登录验证
        },
    },
    {
        path: '/animalClassify',
        component: animalClassify,
        name:'畜禽分类管理',
        meta: {
            loggedIn: true //登录验证
        },
    },
    {
        path: '/exportAreaData',
        component: exportAreaData,
        name:'区域数据导出',
        meta: {
            loggedIn: true //登录验证
        },
    },
    {
        path: '/exportPersonData',
        component: exportPersonData,
        name:'人员信息导出',
        meta: {
            loggedIn: true //登录验证
        },
    },
    {
        path: '/exportStocksData',
        component: exportStocksData,
        name:'畜禽信息导出',
        meta: {
            loggedIn: true //登录验证
        },
    },
    {
        path: '/exportFarmerData',
        component: exportFarmerData,
        name:'养殖户信息导出',
        meta: {
            loggedIn: true //登录验证
        },
    },
    {
        path: '/taskManager',
        component: taskManager,
        name:'任务发布',
        meta: {
            loggedIn: true //登录验证
        },
    },
    {
        path: '/checkTaskDetail',
        component: checkTaskDetail,
        name:'任务详情',
        meta: {
            loggedIn: true //登录验证
        },
    },
    {
        path: '/addNewTask',
        component: addNewTask,
        name:'新建任务',
    },
    {
        path: '/newsManager',
        component: newsManager,
        name:'消息中心',
        meta: {
            loggedIn: true //登录验证
        },
    },
  ]

  export default routes
/**
* 登录注册
* 作者：suliyu
* 创建时间：2017年05月15日16:02:29
*/

const login = resolve => require(['index/page/login/login.vue'], resolve) //用户登录
const findPassword = resolve => require(['index/page/login/findPassword.vue'],resolve) //忘记密码
const register = resolve => require(['index/page/login/register.vue'],resolve) //用户注册
export default [
  {
    name:'用户登录',
    path: '/',
    component: login
  },{
    name:'用户登录',
    path: '/login',
    component: login
  },{
    name:'找回密码',
    path:'/findPassword',
    component:findPassword
  },{
    name:'用户注册',
    path:'/register',
    component:register
  }
]

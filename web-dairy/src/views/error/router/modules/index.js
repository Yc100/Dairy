/**
 * 主路由
 *
 */

const index = resolve => require(['error/page/index.vue'], resolve) //根页面
const page404 = resolve => require(['error/page/404.vue'], resolve) //404
export default[//顶层路由
  {
    path : '/',
    redirect : '/404'
  },
  //地址为空时跳转index页面
  {
    path : '',
    redirect : '/404'
  },{
    path : '/404',
    component : page404
  }
]

import axios from 'axios'
// import vm from '../main'
import api from './apiConfig'
// import store from '../vuex/store';
import router from '../views/dairy/router/router'

// const PRODUCT_URL = 'https://xxxx.com'
// const MOCK_URL = 'http://mock.com'
let http = axios.create({
  // baseURL: process.env.NODE_ENV === 'production' ? PRODUCT_URL : MOCK_URL
})
// 请求拦截器
axios.interceptors.request.use(
  config => {
    // 设置token，Content-Type
    var token = localStorage.getItem('token')
    config.headers['token'] = token
    // config.headers['Content-Type'] = 'application/json;charset=UTF-8'
    // 请求显示loading效果
    if (config.loading === true) {
      // vm.$loading.show()
    }
    return config
  },
  error => {
    // vm.$loading.hide()
    return Promise.reject(error)
  }
)
// 响应拦截器
axios.interceptors.response.use(
  res => {
    // vm.$loading.hide()
    if (res.data.code === 0 || (res.request && res.request.response && res.request.response.type == 'application/vnd.ms-excel') ) {
      return res;
    }
    // token失效，重新登录
    switch (res.data.code) {
      //401：未登录
      //未登录则跳转登陆页面，并携带当前页面的路径
      case -1:
        console.log(router.currentRoute);
        if(router.currentRoute.fullPath.indexOf('/login') === -1){
          router.push({
            path:'/login',
            query:{redirect:router.currentRoute.fullPath}
          })
        }else{
          return false;
        }

        break;

      //403 token过期
      //登录过期对用户进行提示  清除本地token和清空vuex中的token
      // 跳转登陆页面
      case 403:
        this.$Message.error({
          message:'登录过期，请重新登录',
          duration:1000,
        })
        localStorage.removeItem('token')
        // store.commit('getUserInfo',null)
        setTimeout(() => {
          router.push({
            path:'/login',
            query:{redirect:router.currentRoute.fullPath}
          });
        },1000)
          break;

        //404请求不存在
        case 403:
          this.$Message.error({
            message:'网络请求不存在',
            duration:1000,
          })
          break;
        // case 0 :
        //   break;

        // 其他错误，直接抛出错误提示
        default:
          // this.$Message.error({
          //     message: res.data.msg,
          //     duration: 1500,
          // });
          break;
    }
    return Promise.reject(res.data)
  },
  error => {
    if(error.response.status === 401){
      if(router.currentRoute.fullPath.indexOf('/login') === -1){
        router.push({
          path: '/login',
          query: {redirect:router.currentRoute.fullPath}
        })
      }
    }
    // vm.$loading.hide()
    return Promise.reject(error)
  }
)

function get (url, data, loading) {
  return new Promise((resolve, reject) => {
    http.get(url)
      .then(
        response => {
          resolve(response)
        },
        err => {
          reject(err)
        }
      )
      .catch(error => {
        reject(error)
      })
  })
}

function post (url, data, loading) {
  return new Promise((resolve, reject) => {
    http.post(url, data, { loading: loading })
      .then(
        response => {
          resolve(response)
        },
        err => {
          reject(err)
        }
      )
      .catch(error => {
        reject(error)
      })
  })
}

export { get, post }

/**
  * 二次封装axios请求
  */

export default async (type, apiName, url, data)=>{
  var vm = this
  if(type === 'get'){
    await axios({
      method:type,
      url:(apiName !== '')? api[apiName] + url:url,
      params:data,
      headers:{
        'Content-Type' : 'application/x-www-form-urlencoded;charset=UTF-8'
      }
    }).then(response=>{
      vm.result = response.data
    })
  }else{
    await axios({
      method:type,
      url:(apiName !== '')? api[apiName] + url:url,
      data:data,
      headers:{
        'Content-Type' : 'application/json;charset=UTF-8'
        // 'token': '262a90ba-18f2-4f3f-8808-ef65c4a43795'
      }
    }).then(response=>{
      vm.result = response.data
    })
  }

  return vm.result
}

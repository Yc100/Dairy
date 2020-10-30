/*
  真实接口地址配置
 */
let env = process.env.NODE_ENV
// window.env = env
// console.log("env",env)
let api
if (env === 'development') {  //开发环境
  api = {
    apiNomal : 'http://172.168.1.177:8081'
  }
} else if (env === 'testing') {
  api = {
    apiNomal : 'http://172.168.1.177:8081'
  }
} else { //生产环境
    api = {
      apiNomal : 'http://172.168.1.177:8081'
      
    }
}


export default api

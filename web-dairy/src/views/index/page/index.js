/* base */
import Vue from 'assets/js/lib' // 引用 vue

/* vuex状态管理 */
import store from 'vuexStore/store'

/* 路由 */
import router from '../router/router' // 引用路由配置文件

/* vuex-router-sync 将router放入store进行管理 */
import { sync } from 'vuex-router-sync'
sync(store, router)

// 百度地图
// import BaiduMap from 'vue-baidu-map'
// Vue.use(BaiduMap, {
//     ak: '7rFAh6uPXLIb0Sc8Lpt1X5goEGkyFr9R' //官方提供的ak秘钥
// })

/* UI/动效 */
// import iView from 'iview'
// import 'iview/dist/styles/iview.css'    // //iview css
// import 'src/assets/css/iview-theme.less' //自定义主题
// Vue.use(iView)
import ViewUI from 'view-design';
import 'view-design/dist/styles/iview.css';
Vue.use(ViewUI);

import 'animate.css' //css3动效

window.vue = new Vue({
  el:'#index',
  router,
  store
})

/* base */
import Vue from 'assets/js/lib' // 引用 vue

/* vuex状态管理 */
import store from 'vuexStore/store'

/* 路由 */
import router from '../router/router' // 引用路由配置文件

/* vuex-router-sync 将router放入store进行管理 */
import { sync } from 'vuex-router-sync'
sync(store, router)

/* UI/动效 */

// element
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);
// import 'element-ui/lib/theme-default/index.css'
// import { Loading,Table,TableColumn,Rate,Card,Row,Col,Radio} from 'element-ui'
// import CollapseTransition from 'element-ui/lib/transitions/collapse-transition';
// Vue.use(Loading.directive)
// Vue.use(Table)
// Vue.use(TableColumn)
// Vue.use(Rate)
// Vue.use(Card)
// Vue.use(Row)
// Vue.use(Col)
// Vue.use(Radio)
// Vue.component(CollapseTransition.name, CollapseTransition)
// Vue.prototype.$loading = Loading.service

// import iView from 'iview'
// import 'iview/dist/styles/iview.css'    // //iview css
// import 'src/assets/css/iview-theme.less' //自定义主题
// Vue.use(iView)


import ViewUI from 'view-design';
import 'view-design/dist/styles/iview.css';
Vue.use(ViewUI);

import Chat from 'jwchat';
Vue.use(Chat)

import 'animate.css' //css3动效

window.vue = new Vue({
  el:'#index',
  router,
  store
})

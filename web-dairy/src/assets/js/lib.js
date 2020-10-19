/* 核心 */
import Vue from 'vue' // 引用 vue

/* polyfill */
import 'es6-promise/auto'
/* 调试 */
Vue.config.devtools = process.env.NODE_ENV === 'development'?true:false

/* mixins */
import mixins from './mixins'
Vue.mixin(mixins)

/* 过滤器 */
import vueFilter from 'vue-filter'
Vue.use(vueFilter)

/* lodash */
import _ from 'lodash'

/* 阿里图标库 */
import 'assets/font/iconfont.css'
import 'assets/font/iconfont.js'
import icon from 'components/icon/icon'
Vue.component('ayn-icon',icon)

/* 优化 */
import FastClick from 'fastclick'
FastClick.attach(document.body)

window.Vue = Vue

export default Vue

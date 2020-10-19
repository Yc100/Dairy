/**
 * 核心
 * vue/vuex
 */
import Vue from 'vue'
import Vuex from 'vuex'


import index from 'index/vuex/store'      //商城
import dairy from 'views/dairy/vuex/store'
/* 调试 */
const debug = process.env.NODE_ENV !== 'production'

// Vue.use(Vuex)
export default new Vuex.Store({
  // 组合各个模块
  modules: {
    ...index,
    ...dairy,
  },
  strict: debug
})

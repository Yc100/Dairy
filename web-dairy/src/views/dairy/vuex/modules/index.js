import * as types from 'vuexStore/mutation-types'
// import {
//     manager
// } from 'service/api'
const state = {
  menu:[]
}

const mutations = {
  /* 获取用户菜单 */
  [types.HOME_MENU_GET](state,res){
    state.menu = res.code === 101 ? res.data : []
  }
}
const actions = {
  getHomeMenu : ({commit}, params) => {
      let result = manager.GetHomeMenu(params)
      commit(types.HOME_MENU_GET, result)
  }
}

const getters = {
  menu: state => state.menu
}

export default{
	state,
	mutations,
  getters,
  actions
}

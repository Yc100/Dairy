import * as types from 'vuexStore/mutation-types'
const state = {
  abc:'1',
}

const mutations = {
  /* init */
  [types.INIT](state){
    state.transitionName = 1
  }
}
const actions = {
  init : ({commit}) =>{
    commit(types.INIT)
  }
}

const getters = {
  abc: state => state.abc
}

export default{
	state,
	mutations,
  getters,
  actions
}

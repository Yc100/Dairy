import * as types from 'vuexStore/mutation-types'
import {
    GetUserToken,getLogout,GetUserInfo,getWorkList
  } from '../../../../service/api.js'
const state = {
    userToken: '', //token
    userInfo: {}, //用户信息
    isGetToken: false, //是否获取token
    loginStatus: false, //登录状态
    loginMessage: '', //登录的提示信息
    loginBg: {}, //登录页背景
}

const mutations = {
    // /* 用户登录,获取token */
    [types.USER_GET_TOKEN](state, info) {
        if (info.code == 0) {
            state.userToken = info.data //token
            localStorage.token = info.data //把token放入本地存储
            state.isGetToken = true
        } else {
            state.userToken = ''
            localStorage.token = ''
            state.isGetToken = false
        }
        state.loginMessage = info.message //登录的提示信息
    },
    /* 用户登录,获取用户信息 */
    [types.USER_GET_USERINFO](state, info) {
        if (info.code == 0) {            
            state.userInfo = info.data || {} //用户信息
            localStorage.userId = info.data.id
            state.loginStatus = true //登录状态
        } else {
            state.loginMessage = info.message //登录失败提示信息
            localStorage.userId = ''
        }
    },
    /* 登录页背景 */
    [types.USER_GET_LOGIN_BG](state, res) {
        state.loginBg = res.code === 0 ? res.data[0] : []
    },

    /* 用户退出登录 */
    [types.USER_LOGIN_EXIT](state) {
        state.isGetToken = false
        state.loginStatus = false
        state.userToken = ''
        state.userInfo = ''
        localStorage.token = ''
        localStorage.userId = ''
    },
}
const actions = {

    /* 用户登录,获取token */
    getUserToken: async({commit}, params) => {
        let token = await GetUserToken(params) //获取token
        await commit(types.USER_GET_TOKEN, token)
        return token
    },
    /* 获取用户信息 */
    getUserInfo: async({
        commit
    }) => {
        let result = await GetUserInfo() //获取用户信息
        await commit(types.USER_GET_USERINFO, result)
    },
    /* 获取登录页面背景 */
    // getLoginBg: async({
    //     commit
    // }, params) => {
    //     let result = await GetContent(params)
    //     await commit(types.USER_GET_LOGIN_BG, result)
    // },
    /* 退出登录 */
    exitSuccess: ({
        commit
    }) => {
        commit(types.USER_LOGIN_EXIT)
    },
}

const getters = {
    userToken: state => state.userToken,
    userInfo: state => state.userInfo,
    isGetToken: state => state.isGetToken,
    loginStatus: state => state.loginStatus,
    loginMessage: state => state.loginMessage,
    loginBg: state => state.loginBg,
}

export default {
    state,
    mutations,
    getters,
    actions
}
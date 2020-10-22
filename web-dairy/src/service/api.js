import res from 'service/ajax'


/*
  文件上传
*/
export const FileUpload = (params) => res('post', 'apiNomal', '/file/upload', params)

//登录
export const GetUserToken = (params) => res('post', 'apiNomal', '/api/login', params)
//退出登录
export const getLogout = (params) => res('get', 'apiNomal', '/api/logout', params)
//获取用户信息
export const GetUserInfo = (params) => res('get', 'apiNomal', '/api/getUserInfo', params)
//上传用户头像
export const updateHeadUrl = (data) => res('post', 'apiNomal', '/api/updateHeadUrl', data)

/*==================================================================================================================*/
//获取用户信息
export const getUserlistPage = (data) => res('get', 'apiNomal', '/user/listPage', data)
export const createUser = (data) => res('post', 'apiNomal', '/user/create', data)
export const deleteUser = (data) => res('post', 'apiNomal', '/user/delete', data)
export const getDairyList = (data) => res('get', 'apiNomal', '/manage/dairy/listPage', data)
export const getDairyDetail = (data) => res('get', 'apiNomal', '/manage/dairy/getOne', data)
export const readDairy = (data) => res('post', 'apiNomal', '/manage/dairy/readDairy', data)
/*==================================================================================================================*/


// 消息中心

//消息中心列表
export const listWorkMsgs = (data) => res('get', 'apiNomal', '/workMsg/listWorkMsgs', data)
//某消息详情
export const getWorkMsgById = (data) => res('get', 'apiNomal', '/workMsg/getWorkMsgById', data)
//标为已读
export const readWorkMsg = (data) => res('post', 'apiNomal', '/workMsg/readWorkMsg',data)
//未读消息数量
export const newUserWorkMsg = (data) => res('get', 'apiNomal', '/workMsg/newUserWorkMsg', data)
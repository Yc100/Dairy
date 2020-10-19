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

// 疫苗领取记录
export const getReceiveRecordPage = (params) => res('get', 'apiNomal', '/vaccine/getReceiveRecordPage', params)
// 防疫员领取疫苗详情记录
export const getReceiveRecordDetail = (params) => res('get', 'apiNomal', '/vaccine/getReceiveRecordDetail', params)
// 防疫员确认领取疫苗接口
export const confirmReceive = (params) => res('post', 'apiNomal', '/vaccine/confirmReceive', params)
// 疫苗管理主页疫苗库存数据数据分页列表查询
export const getVaccineStockPage = (params) => res('get', 'apiNomal', '/vaccine/getVaccineStockPage', params)
// 下拉展示畜禽种类信息列表
export const selectLivestockKinds = (params) => res('get', 'apiNomal', '/livestockKind/selectLivestockKinds', params)
// 获取疫苗档案主页仪表盘数据
export const getVaccineDashboard = (params) => res('get', 'apiNomal', '/vaccine/getVaccineDashboard', params)
// 疫苗详情信息查询接口
export const getVaccineStockDetail = (params) => res('get', 'apiNomal', '/vaccine/getVaccineStockDetail', params)
// 疫苗库存详情信息查询接口
export const getVaccineStockDetailByFactory = (params) => res('get', 'apiNomal', '/vaccine/getVaccineStockDetailByFactory', params)
// 根据畜禽id获取该畜禽接种疫苗记录
export const getVaccinationListByLivestockId = (params) => res('get', 'apiNomal', '/vaccination/getVaccinationListByLivestockId', params)
// 分页查询疫苗已使用列表
export const getVaccineUseRecordPage = (params) => res('get', 'apiNomal', '/vaccine/getVaccineUseRecordPage', params)
// 分页查询疫苗入库记录列表
export const getVaccineInRecordPage = (params) => res('get', 'apiNomal', '/vaccine/getVaccineInRecordPage', params)
// 获取副反应记录添加页面 养殖户 畜禽数据
export const getReaction = (params) => res('get', 'apiNomal', '/reaction/getReaction', params)
// 疫苗使用副反应记录保存接口
export const vaccineReactionSubmit = (params) => res('post', 'apiNomal', '/reaction/vaccineReactionSubmit', params)
// 获取疫苗批次列表
export const getSelectBatchNums = (params) => res('get', 'apiNomal', '/vaccination/getSelectBatchNums', params)
// 获取疫苗厂家下拉框列表
export const getSelectFactories = (params) => res('get', 'apiNomal', '/vaccination/getSelectFactories', params)
// 获取 养殖户、畜禽信息 以及疫苗名称下拉框列表 添加接种面页使用
export const getVaccination = (params) => res('get', 'apiNomal', '/vaccination/getVaccination', params)
// 添加疫苗使用记录提交接口
export const vaccineUseSubmit = (params) => res('post', 'apiNomal', '/vaccination/vaccineUseSubmit', params)
// 获取区域接口
export const getAreaSelectList = (params) => res('get', 'apiNomal', '/vaccination/getAreaSelectList', params)
// 获取养殖户接口
export const getFarmerSelectList = (params) => res('get', 'apiNomal', '/vaccination/getFarmerSelectList', params)
// 获取畜禽接口
export const getLivestockSelectList = (params) => res('get', 'apiNomal', '/vaccination/getLivestockSelectList', params)
// 获取疫苗名称接口
export const getVaccineNamesSelectList = (params) => res('get', 'apiNomal', '/vaccination/getVaccineNamesSelectList', params)
// 疫苗批量使用提交接口
export const vaccineUseBatchSubmit = (params) => res('post', 'apiNomal', '/vaccination/vaccineUseBatchSubmit', params)

// 畜禽档案管理--列表
export const getListLivestocks = (params) => res('get', 'apiNomal', '/livestock/listLivestocks', params)
// 畜禽档案管理--详情
export const getLivestockById = (params) => res('get', 'apiNomal', '/livestock/getLivestockById', params)
// 畜禽档案管理--新增
export const addLivestock = (params) => res('post', 'apiNomal', '/livestock/addLivestock', params)
// 畜禽档案管理--编辑
export const updateLivestock = (params) => res('post', 'apiNomal', '/livestock/updateLivestock', params)
// 畜禽档案管理--删除
export const deleteLivestock = (params) => res('post', 'apiNomal', '/livestock/deleteLivestock', params)
//按种类统计各畜禽数量
export const countByKind = (params) => res('get', 'apiNomal', '/workarea/countByKind', params)
//按养殖户统计各畜禽数量
export const countByFarmer = (params) => res('get', 'apiNomal', '/workarea/countByFarmer', params)
//获取畜禽健康状态
export const selectLivestockStatuss = (params) => res('get', 'apiNomal', '/livestockStatus/selectLivestockStatuss', params)
//获取一级畜禽种类信息
export const firstLivestockKinds = (params) => res('get', 'apiNomal', '/livestockKind/firstLivestockKinds', params)


// 销售记录--列表
export const getListSaleLogs = (params) => res('get', 'apiNomal', '/salelog/listSaleLogs', params)
// 销售记录--详情
export const getSaleLogById = (params) => res('get', 'apiNomal', '/salelog/getSaleLogById', params)
// 销售记录--新增
export const addSaleLog = (params) => res('post', 'apiNomal', '/salelog/addSaleLog', params)
// 销售记录--编辑
export const updateSaleLog = (params) => res('post', 'apiNomal', '/salelog/updateSaleLog', params)
// 销售记录--删除
export const deleteSaleLog = (params) => res('post', 'apiNomal', '/salelog/deleteSaleLog', params)

// 调运记录--列表
export const getListTransporteds = (params) => res('get', 'apiNomal', '/salelog/listTransporteds', params)
// 调运记录--详情
export const getTransportedById = (params) => res('get', 'apiNomal', '/salelog/getTransportedById', params)
// 调运记录--新增
export const addTransported = (params) => res('post', 'apiNomal', '/salelog/addTransported', params)
// 调运记录--编辑
export const updateTransported = (params) => res('post', 'apiNomal', '/salelog/updateTransported', params)
// 调运记录--删除
export const deleteTransported = (params) => res('post', 'apiNomal', '/salelog/deleteTransported', params)

// 养殖户档案--列表
export const getFarmerList = (params) => res('get', 'apiNomal', '/farmer/farmerList', params)
// 养殖户档案--详情
export const getFarmerDetail = (params) => res('get', 'apiNomal', '/farmer/getFarmerDetail', params)
// 养殖户档案--新增
export const addFammer = (params) => res('post', 'apiNomal', '/farmer/addFammer', params)
// 养殖户档案--编辑
export const changeFammer = (params) => res('post', 'apiNomal', '/farmer/changeFammer', params)
// 养殖户档案--删除
export const delFarmer = (params) => res('post', 'apiNomal', '/farmer/delFarmer', params)
// 养殖户档案--获取片区下的养殖户
export const selectFarmers = (params) => res('get', 'apiNomal', '/farmer/selectFarmers', params)

/*
  首页--工作安排
*/
// 工作安排列表
export const getWorkList = (params) => res('get', 'apiNomal', '/workInfo/list', params)
//新增一个工作安排
export const addWork = (params) => res('post', 'apiNomal', '/workInfo/add', params)
//工作任务详情
export const getWorkDetail = (params) => res('get', 'apiNomal', '/workInfo/getWorkInfo', params)
//首页工作安排轮播列表
export const getIndexWorkList = (params) => res('get', 'apiNomal', '/work/listForMain', params)
//我要完成表单提交
export const finishWork = (params) => res('post', 'apiNomal', '/workInfo/finish', params)
//我要延期任务表单提交
export const delayWork = (params) => res('post', 'apiNomal', '/workInfo/apply', params)
//首页大区数据展示
export const getIndexAreaList = (params) => res('get', 'apiNomal', '/work/areaForMain', params)



// 片区

// 片区列表
export const getAreaListPage = (data) => res('get', 'apiNomal', '/workarea/listWorkAreas', data)
// 片区详情
export const getAreaDetail = (data) => res('get', 'apiNomal', '/workarea/getAreaById', data)
// 下拉片区列表
export const selectWorkAreas = (data) => res('get', 'apiNomal', '/workarea/selectWorkAreas', data)
// 按养殖户统计
export const getFowlByFaramer = (data) => res('get', 'apiNomal', '/workarea/countByFarmer', data)
// 按畜禽统计
export const getFaramerByClass = (data) => res('get', 'apiNomal', '/workarea/countByKind', data)
// 新增片区
export const addWorkArea = (data) => res('post', 'apiNomal', '/workarea/addWorkArea', data)
// 删除片区
export const deleteWorkArea = (data) => res('post', 'apiNomal', '/workarea/deleteWorkArea', data)
// 修改片区
export const updateWorkArea = (data) => res('post', 'apiNomal', '/workarea/updateWorkArea', data)
// 未分配管理员的片区列表
export const selectWorkUserAreas = (data) => res('get', 'apiNomal', '/workarea/selectWorkUserAreas', data)

// 调入

// 调入分页列表
export const getImportedLogsPage = (data) => res('get', 'apiNomal', '/importedLog/listImportedLogs', data)
// 查询单个
export const getImportedLogById = (data) => res('get', 'apiNomal', '/importedLog/getImportedLogById', data)
// 今日汇总
export const summaryImportedLogNow = (data) => res('get', 'apiNomal', '/importedLog/listImportedLogNow', data)
// 新增调入
export const addImportedLog = (data) => res('post', 'apiNomal', '/importedLog/addImportedLog', data)
// 修改
export const updateImportedLog = (data) => res('post', 'apiNomal', '/importedLog/updateImportedLog', data)
// 删除调入
export const deleteImportedLog = (data) => res('post', 'apiNomal', '/importedLog/deleteImportedLog', data)


// 调出

// 调出分页列表
export const getExportedLogsPage = (data) => res('get', 'apiNomal', '/exportedLog/listExportedLogs', data)
// 查询单个
export const getExportedLogById = (data) => res('get', 'apiNomal', '/exportedLog/getExportedLogById', data)
// 今日汇总
export const summaryExportedLogNow = (data) => res('get', 'apiNomal', '/exportedLog/listExportedLogNow', data)
// 新增调出
export const addExportedLog = (data) => res('post', 'apiNomal', '/exportedLog/addExportedLog', data)
// 修改
export const updateExportedLog = (data) => res('post', 'apiNomal', '/exportedLog/updateExportedLog', data)
// 删除调出
export const deleteExportedLog = (data) => res('post', 'apiNomal', '/exportedLog/deleteExportedLog', data)


//周报

// 新增周报
export const addWeekDiary = (data) => res('post', 'apiNomal', '/weekDiary/addWeekDiary', data)
// 获取汇总数据信息
export const countByUserId = (data) => res('get', 'apiNomal', '/weekDiary/countByUserId', data)
// 删除周报
export const deleteWeekDiary = (data) => res('post', 'apiNomal', '/weekDiary/deleteWeekDiary', data)
// 获取周报详情
export const getWeekDiaryById = (data) => res('get', 'apiNomal', '/weekDiary/getWeekDiaryById', data)
// 获取周报列表
export const listWeekDiary = (data) => res('get', 'apiNomal', '/weekDiary/listWeekDiary', data)
// 修改周报
export const updateWeekDiary = (data) => res('post', 'apiNomal', '/weekDiary/updateWeekDiary', data)


//岗位管理

// 新增岗位
export const addSysRole = (data) => res('post', 'apiNomal', '/sysRole/addSysRole', data)
// 删除岗位
export const deleteSysRole = (data) => res('post', 'apiNomal', '/sysRole/deleteSysRole', data)
// 获取岗位详情
export const getById = (data) => res('get', 'apiNomal', '/sysRole/getById', data)
// 获取岗位列表
export const listSysRoles = (data) => res('get', 'apiNomal', '/sysRole/listSysRoles', data)
// 获取岗位列表--下拉选择框
export const selectSysRoles = (data) => res('get', 'apiNomal', '/sysRole/selectSysRoles', data)
// 修改岗位
export const updateSysRole = (data) => res('post', 'apiNomal', '/sysRole/updateSysRole', data)
//获取权限列表
export const selectSysMods = (data) => res('get', 'apiNomal', '/sysModule/selectSysMods', data)
//查看岗位权限
export const selectedMods = (data) => res('get', 'apiNomal', '/sysModule/selectedMods', data)

//员工管理

// 统计用户下的各总数
export const countByUser = (data) => res('get', 'apiNomal', '/sysUser/countByUser', data)
// 新增员工
export const addSysUser = (data) => res('post', 'apiNomal', '/sysUser/addSysUser', data)
// 删除员工
export const deleteSysUser = (data) => res('post', 'apiNomal', '/sysUser/deleteSysUser', data)
// 获取员工详情
export const getSysUserById = (data) => res('get', 'apiNomal', '/sysUser/getSysUserById', data)
// 获取员工列表
export const listSysUsers = (data) => res('get', 'apiNomal', '/sysUser/listSysUsers', data)
// 获取员工列表--下拉选择框
export const selectSysUsers = (data) => res('get', 'apiNomal', '/sysUser/selectSysUsers', data)
// 修改员工信息
export const updateSysUser = (data) => res('post', 'apiNomal', '/sysUser/updateSysUser', data)
// 修改员工岗位信息
export const updateUserRole = (data) => res('post', 'apiNomal', '/sysUser/updateUserRole', data)
// 修改员工区域管理信息
export const updateUserArea = (data) => res('post', 'apiNomal', '/sysUser/updateUserArea', data)

// 数据统计
export const getCount = data => res('get', 'apiNomal', '/datav/getCount', data)
export const getAdminArea = data => res('get', 'apiNomal', '/datav/getAdminArea', data)
export const getNumLivestock = data => res('get', 'apiNomal', '/datav/getNumLivestock', data)
export const getNumLivestockByArea = data => res('get', 'apiNomal', '/datav/getNumLivestockByArea', data)
export const getImmunityMonth = data => res('get', 'apiNomal', '/datav/getImmunityMonth', data)
export const getDistributionFarmers = data => res('get', 'apiNomal', '/datav/getDistributionFarmers', data)
export const getAdminAreaList = data => res('get', 'apiNomal', '/datav/getAdminAreaList', data)


//导出

//区域列表导出
export const exportWorkAreas = (data) => res('get', 'apiNomal', '/workarea/exportWorkAreas', data)
//人员列表导出
export const exportUsers = (data) => res('get', 'apiNomal', '/api/exportUsers', data)
//畜禽列表导出
export const exportLivestocks = (data) => res('get', 'apiNomal', '/livestock/exportLivestocks', data)
//养殖户列表导出
export const exportFarmerList = (data) => res('get', 'apiNomal', '/farmer/exportFarmerList', data)

// 工作任务

// 获取任务列表
export const getWorkTaskPage = (data) => res('get', 'apiNomal', '/workTask/listWorkTasks', data)
// 获取任务详情
export const getWorkTaskDetail = (data) => res('get', 'apiNomal', '/workTask/getWorkTaskById', data)
// 新增任务
export const addWorkTask = (data) => res('post', 'apiNomal', '/workTask/addWorkTask', data)
// 获取区域用户选项
export const getAreaUsersOptions = () => res('get', 'apiNomal', '/sysUser/selectAreaUsers')
// 获取用户选项
export const getUsersOptions = () => res('get', 'apiNomal', '/sysUser/selectSysUsers')
//管理员提醒员工未完成的工作
export const addWorkMsg = (data) => res('post', 'apiNomal', '/workMsg/addWorkMsg',data)
//工作任务申请延期审核
export const checkDeferre = (data) => res('post', 'apiNomal', '/workTask/checkDeferre',data)

//畜禽分类

// 畜禽分类列表
export const listLivestockKinds = (data) => res('get', 'apiNomal', '/livestockKind/listLivestockKinds', data)
// 单一畜禽分类详情详情
export const getLivestockKindById = (data) => res('get', 'apiNomal', '/livestockKind/getLivestockById', data)
// 新增畜禽分类
export const addLivestockKind = (data) => res('post', 'apiNomal', '/livestockKind/addLivestock', data)
// 修改畜禽分类
export const updateLivestockKind = (data) => res('post', 'apiNomal', '/livestockKind/updateLivestockKind', data)
// 删除畜禽分类
export const deleteLivestockKind = (data) => res('post', 'apiNomal', '/livestockKind/deleteLivestockKind', data)

//为区域分配管理员
export const setAreaToUser = (data) => res('post', 'apiNomal', '/workarea/setAreaToUser',data)

// 消息中心

//消息中心列表
export const listWorkMsgs = (data) => res('get', 'apiNomal', '/workMsg/listWorkMsgs', data)
//某消息详情
export const getWorkMsgById = (data) => res('get', 'apiNomal', '/workMsg/getWorkMsgById', data)
//标为已读
export const readWorkMsg = (data) => res('post', 'apiNomal', '/workMsg/readWorkMsg',data)
//未读消息数量
export const newUserWorkMsg = (data) => res('get', 'apiNomal', '/workMsg/newUserWorkMsg', data)
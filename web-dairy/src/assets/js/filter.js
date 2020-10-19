/**
 * 自定义过滤器
 */
export default {
  /* 公司管理员 */
  isAdmin(val){
    switch (val) {
      case 0:
        return '管理员'
      case 1:
        return '成员'
    }
  },
  /* 权限类型 */
  privilege(val){
    switch (val) {
      case 1:
        return '菜单'
      case 2:
        return '页面'
      case 3:
        return '按钮'
    }
  },

  /* 小数转百分 */
  toPercent(val){
    return (val * 100).toFixed(2) + "%"
  }, 

  // 时间显示格式更正
  timeCorrections(val){
    let newVal = val.replace(/T/,' ')
    return newVal
  },
  genderValue(val){
    switch (val) {
      case 1:
        return '男'
      case 2:
        return '女'
    }
  },
  //消息状态-已读，未读
  messageStatus(val){
    switch(val){
      case 1:
        return '未读'
      case 2:
        return '已读'
    }
  },
  //单位
  unitName(val){
    switch (val) {
      case 1:
        return '头'
      default:
       return '只'
    }
  },

  //任务状态
  taskStatus(val){
    switch (val) {
      case 1:
        return '待完成'
      case 2:
        return '已完成'
    }
  },
  //任务优先级
  level(val){
    switch (val) {
      case 1:
        return '普通'
      case 2:
        return '重要不紧急'
      case 3:
        return '紧急不重要'
      case 4:
        return '紧急且重要'
    }
  },
  //申请顺延审核状态
  checkedStatus(val){
    switch (val) {
      case 1:
        return '待审核'
      case 2:
        return '审核通过'
      case 3:
        return '审核拒绝'
    }
  },
}

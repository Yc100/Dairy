<template lang="html">
  <div class="headerNav">
    <Row type="flex" justify="space-between" align="middle">
        <Col span="24" order="1" offset="0" style="height:55px">
          <Row :gutter="0">
              <!-- <Col span="1"><img src="../../assets/imgs/yjh.png" width="40" style="position:relative; top:8px;left:10px;"></img></Col> -->
              <Col span="5" class="hiddenText">疫控管理端</Col>
              <!-- 未登录 -->
              <Col span="18" class-name="right" v-if="!loginStatus">
                <a class="center" @click="$loginVerify">请登录</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <a @click="$href('index/index.html#/register')">免费注册</a>
              </Col>
              <!-- 已登录 -->
              <Col span="18" class-name="right" v-if="loginStatus">
                <a class="center">
                  <el-avatar size="small" :src="userInfo.headUrl || avatarImg" class="avatar-style"></el-avatar>&nbsp;&nbsp;
                  {{userInfo.nickName || '新用户'}}
                </a>
                 <a class="center" style="margin-left:13px;">
                  <Tooltip :content="messageNum == 0 ? '当前暂无新的消息':'您有新的消息请查看'" placement="bottom" theme="light">
                    <Badge :count="messageNum"  :offset='newsPosition' class="badge-size">
                      <Icon type="ios-notifications-outline" class="iconNews" size="28" @click="$jump('/newsManager')"></Icon>
                    </Badge>
                  </Tooltip>
                </a>
                <a class="center" style="margin:0 10px;">
                  {{nowTme | date('%F')}}
                </a>
                <a class="mLeft" @click="loginout">
                  <ayn-icon type="niu" size="12" color="#FBBA24"/>
                  退出系统
                </a>
              </Col>
          </Row>
        </Col>
    </Row>
  </div>
</template>

<script>

// import {cart} from 'service/api'
import { mapGetters,mapActions } from 'vuex'
import { setTimeout } from 'timers';
// import Vue from 'assets/js/lib' // 引用 vue
// import {MessageBox} from 'element-ui'
// Vue.use(MessageBox)
import {newUserWorkMsg} from 'service/api'
export default {
  name:'headerNav',
  data() {
    return {
      cartCount: 0,
      avatarImg:require('../../assets/imgs/headImg.jpg'), //头像图片
      newsPosition:[10,0], //消息提醒图标位置
      currentNews:[], //当前消息
      nowTme:new Date(), //当前时间
      waitAuditNum:[], //待办事项
      messageNum:0,
    }
  },
  computed:{
    ...mapGetters([
      'loginStatus',    //登录状态
      'isAuitCart',     //是否显示采购申请
      'userInfo',        //用户信息
    ])
  },
  methods:{
    ...mapActions([
      'exitSuccess',   //退出登录
    ]),
    // 退出登录
    async loginout(){
      await this.exitSuccess()    //退出登录
      this.$Message.success('退出成功,正在跳转...') //登录信息提示
      setTimeout(()=>{
        this.$loginVerify()       //登录验证跳转
      },1500)
    },
     //获取消息条数
		async getNumber(){
			await newUserWorkMsg().then(res=>{
				if(res.code === 0){
					this.messageNum = res.data !=0 ?res.data : '';
				}else{
					this.$Message.success(res.msg ||'获取成功');
				}
			})
		},
  },
  mounted() {

  },
  created(){
    this.getNumber()
  }
}
</script>

<style lang="less" scoped>
@import '~src/assets/css/web-theme.less';
@height:55px;
.headerNav{
  position: relative;
  z-index: 1000;
  // height: 5rem!important;
  background:#3d83ff !important;
  box-sizing: border-box !important;
  line-height: @height;
  color: #fff;
  font-size: 12px;
  width: 100%;
  .center{
    text-align: center;
  }
  .right{
    text-align: right;
  }
  .mLeft{
    margin-left: 10px;
  }
  .hiddenText{
    font-size: 20px;
    // padding-left: 30px;
    font-weight: bold;
    overflow:hidden;
    white-space:nowrap;
    text-overflow:ellipsis;
    margin-left: 20px;
  }

  a{
    color: #fff;
    &:hover{
      color:yellow;
    }
  }
  .avatar-style{
    position: relative;
    top: 8px;
  }
  .badge-size{
    size: 10px;
  }
  .iconNews{
    position: relative;
    top: 2px;
  }
  .badge-size{
    transform: scale(0.75);
  }
}
</style>

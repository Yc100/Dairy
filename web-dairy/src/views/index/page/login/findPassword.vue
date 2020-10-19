<template lang="html">
<div class="forgetPassword">
  <!-- 步骤条 -->
  <Row>
    <Col span="20" offset="4">
      <Steps :current="current">
        <Step title="登录账号"></Step>
        <Step title="重置密码"></Step>
        <Step title="修改成功"></Step>
    </Steps>
    </Col>
</Row>
<!-- 第一步步骤内容 -->
<div class="step1" v-show="showStep1">
  <Row >
    <Col span="7" offset="8">
    <Form :model="findInfo" :label-width="80" ref="findInfo" :rules="findInfoRule">
      <Form-item label="用户名" prop="username">
        <Input v-model="findInfo.username" placeholder="请输入登陆用户名"></Input>
      </Form-item>
    </Form>
    </Col>
  </Row>
</div>
<div class="" style="text-align:center" v-show="showStep1">
  <i-button type="primary" @click="next1">下一步</i-button>
</div>
<!-- 第二步步骤内容 -->
<div class="step2" v-show="showStep2">
  <Row>
    <Col span="7" offset="8">
      <Form ref="findPwd" :model="findPwd" :rules="findPwdRule" :label-width="80">
        <Form-item label="新密码" prop="passwd">
          <Input type="password" v-model="findPwd.passwd" placeholder="请输入新的密码"></Input>
        </Form-item>
        <Form-item label="确认密码" prop="passwdCheck">
          <Input type="password" v-model="findPwd.passwdCheck" placeholder="请再次输入新密码" ></Input>
        </Form-item>
        <Form-item label="短信验证" prop="code">
          <Row :gutter="16">
            <Col span="14">
              <Input type="text" v-model="findPwd.code" placeholder="请输入手机验证码"></Input>
            </Col>
            <Col span="3">
              <Button @click="getCode" v-show="showCodeBtn">获取验证码</Button>
              <Button v-show="!showCodeBtn">{{count}}秒后重发</Button>
            </Col>
          </Row>
        </Form-item>
      </Form>

    </Col>
  </Row>
</div>
<div class="" style="text-align:center" v-show="showStep2">
  <i-button type="primary" @click="last">上一步</i-button>
  <i-button type="primary" :disabled="isCheck" @click="next2">确 定</i-button>
</div>
</div>
</template>

<script>
import {
  mapGetters,
  mapActions
} from 'vuex'
// import { manager } from 'service/api.js'
export default {
  name: 'forgetPassword',
  data () {
    //姓名号码的验证规则
    const validateIphone = (rule, value, callback) => {//修改数量的验证规则
      if (value !== '' && value.length >= 6) {//限定输入的是正数
        callback()
      } else {
        callback(new Error('请输入正确的登陆用户名'));
      }
    };
    //第二次密码跟第一次密码是否一致的验证规则
    const validatePassCheck = (rule, value, callback) => {
      if (value !== this.findPwd.passwd) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      isCheck: false,      //防止用户多点(false，true不能点击)
      current: 0,
      showStep1: true,     //第一步验证内容是否显示
      showStep2: false,    //第二步验证内容是否显示
      count: '',           //计算验证码多久发送一次
      showCodeBtn: true,   //显示是否发送短信验证码的按钮
      findInfo: {         //第一步内容信息
        username: ''        //手机号码
      },
      findInfoRule: {      //第一步内容验证
        username: [
          { required: true, message: '用户名不能为空' },
          { validator: validateIphone, trigger: 'blur' }
        ]
      },
      findPwd: {          //第二步内容信息
        passwd: '',       //新密码
        passwdCheck: '',  //确认新密码
        code: ''          //短信验证码
      },
      findPwdRule: {      //第二步内容验证
        passwd: [         //新密码的验证规则
          { required: true, message: '新密码不能为空' },
          { min: 6, message: '密码长度不能小于6位', trigger: 'blur' }
        ],
        passwdCheck: [   //再次确认密码验证规则
          { required: true, message: '请再次输入新密码' },
          { min: 6, message: '密码长度不能小于6位' },
          { validator: validatePassCheck, trigger: 'blur' }
        ],
        code: [{ required: true, message: '请输入手机短信验证码' }]  //手机短信验证码验证规则
      },
    }
  },
  methods: {
    // 点击保存时，步骤条样式变化
    next2 () {
      this.$refs['findPwd'].validate(async (valid) => {
        if (valid) {
          this.current = 2
          if (this.isCheck == false) {
            this.isCheck = !this.isCheck
            await manager.FindPassword({
              username: this.findInfo.username,
              password: this.findPwd.passwd,
              confirmPassword: this.findPwd.passwdCheck,
              messageCode: this.findPwd.code
            }).then((res) => {
              if (res.code == '101') {
                this.showStep1 = false   //第一步的内容隐藏
                this.showStep2 = false  //第二步的内容显示
                this.$Message.success("密码修改成功")
                this.current = 3
                localStorage.token = ''
                setTimeout(() => {  //修改成功后跳转到登录页面
                  this.$jump('/login')
                }, 1000)
              } else {
                this.isCheck = false
                this.$Message.error(res.message)
              }
            })
          }
        } else {
          return false
        }
      })
    },
    //next1点击下一步时，步骤条样式变化
    next1 () {
      this.$refs['findInfo'].validate((valid) => {
        if (valid) {
          this.current = 1
          this.showStep1 = false   //第一步的内容隐藏
          this.showStep2 = true   //第二步的内容显示
          this.isCheck = false
        } else {
          return false
        }
      })
    },
    // 点击上一步时，步骤条样式变化
    last () {
      this.current = 0
      this.showStep2 = false
      this.showStep1 = true
      this.isCheck = false
    },
    //点击获取短信验证码
    async getCode () {
      await manager.FindMsgCode({
        username: this.findInfo.username
      }).then((res) => {
        if (res.code == '101') {
          localStorage.token = res.data
          this.$Message.success("短信验证码已成功发送,请注意查收")
          this.showCodeBtn = false
          this.isCheck = false
          this.count = 60
          let setTime = setInterval(() => {
            this.count--
            if (this.count == 0) {
              this.showCodeBtn = true
              clearInterval(setTime)
            }
          }, 1000)
        } else {
          this.$Message.error(res.message)
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
.forgetPassword {
  width: 1200px !important;
  margin: 25px auto 0;
  .step1 {
    margin-top: 30px;
    .ivu-col-span-18 {
      padding-left: 0 !important;
    }
  }
  .step2 {
    margin-top: 30px;
    .ivu-col-span-18 {
      padding-left: 0 !important;
    }
  }
}
</style>

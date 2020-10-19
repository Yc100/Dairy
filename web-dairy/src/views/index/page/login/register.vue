<template lang="html">
<div class="register">
  <!-- 步骤条 -->
  <Row>
    <Col span="20" offset="4">
      <Steps :current="current">
        <Step title="设置登录账号"></Step>
        <Step title="填写账号信息"></Step>
        <Step title="注册成功"></Step>
    </Steps>
    </Col>
</Row>
<!-- 第一步步骤内容 -->
<div class="step1" v-show="showStep1">
  <Row >
    <Col span="7" offset="8">
    <Form :model="findInfo" :label-width="80" ref="findInfo" :rules="findInfoRule">
      <Form-item label="用户名" prop="username">
        <Input v-model="findInfo.username" placeholder="用户名,可为数字、字母组合，长度至少6位"></Input>
      </Form-item>
      <Form-item label="手机号" prop="contactCell">
        <Input v-model="findInfo.contactCell" placeholder="请输入联系方式，账号为手机号时可不填"></Input>
      </Form-item>
    </Form>
    </Col>
    <!-- 用户协议还没有做，没有相关接口 -->
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
        <Form-item label="密码" prop="passwd">
          <Input type="password" v-model="findPwd.passwd" placeholder="请输入密码"></Input>
        </Form-item>
        <Form-item label="确认密码" prop="passwdCheck">
          <Input type="password" v-model="findPwd.passwdCheck" placeholder="请再次输入你的密码" ></Input>
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
// import { user } from 'service/api.js'
export default {
  name: 'register',
  components: {
  },
  data () {
    //手机号码的验证规则
    const validateIphone = (rule, value, callback) => {//修改数量的验证规则
      if (value !== '' && value.length >= 6) {//限定输入的是正数
        callback()
      } else {
        callback(new Error('请输入用户名,可为数字、字母组合，长度至少6位'));
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
    const validateContactCell = (rule, value, callback) => {
      // //如果username不是电话号码，则contactCell必须是电话号码
      // if(!/^1[3|5|7|8][0-9]\d{8}$/.test(this.findInfo.username)){
      //     if(!/^1[3|5|7|8][0-9]\d{8}$/.test(value)){//输入正确的手机号
      //         callback(new Error('请输入正确的手机号码！'));
      //     }else{
      //       callback()
      //     }
      // } else {
      //   callback();
      // }

    };
    return {
      isCheck: false,      //防止用户多点(false，true不能点击)
      current: 0,
      showStep1: true,     //第一步验证内容是否显示
      showStep2: false,    //第二步验证内容是否显示
      count: '',           //计算验证码多久发送一次
      showCodeBtn: true,   //显示是否发送短信验证码的按钮
      findInfo: {         //第一步内容信息
        username: '',       //手机号码  修改为注册帐号，格式可以是字符串，电话号码
        contactCell: ''     //注册联系方式，如果注册账号为手机号，则可不填，否则必填，并必须为手机号
      },
      findInfoRule: {      //第一步内容验证
        username: [
          { required: true, message: '用户名不能为空' },
          { validator: validateIphone, trigger: 'blur' }
        ],
        contactCell: [
          { required: false },
          // {validator:validateContactCell,trigger:'blur'}
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
            await user.UserSignup({
              username: this.findInfo.username,
              contactCell: this.findInfo.contactCell,
              password: this.findPwd.passwd,
              messageCode: this.findPwd.code
            }).then((res) => {
              if (res.code == '101') {
                this.showStep1 = false   //第一步的内容隐藏
                this.showStep2 = false  //第二步的内容显示
                this.$Message.success("注册成功")
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
      await user.GetMsgCode({
        userName: this.findInfo.username,
        contactCell: this.findInfo.contactCell
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
.register {
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

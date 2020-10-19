<template lang="html">
<div class="login" @keyup.enter="validate('loginInfo')" :style='heightValue'>
  <!-- nav -->
  <div class="content">
    <!-- 背景图片 -->
    <div class="login-newbg" :style="bg">
      <!-- <img src="../../../../assets/imgs/login-bg.jpg"></img> -->
     <!-- <div class='titleBox'>疫控更易控</div>-->
      <div class="content-layout">
        <div class="login-box-warp">
          <div class="login-box">
            <h2 class="title">登陆账号</h2>
            <br>
            <Form ref="loginInfo" :model="loginInfo" :rules="ruleInline">
              <Form-item prop="username">
                  <Input type="text" class="input-class" v-model="loginInfo.username" placeholder="请输入用户名" size="large">
                    <!-- <ayn-icon type="31wode" slot="prepend" size="18"/> -->
                    <i class="iconfont iconyonghuming" slot="prepend" />
                  </Input>
              </Form-item>
              <Form-item prop="password">
                  <Input type="password" v-model="loginInfo.password" placeholder="请输入密码" size="large">
                    <!-- <ayn-icon type="31mima" slot="prepend" size="18"/> -->
                    <i class="iconfont iconmima" slot="prepend"/>
                  </Input>
              </Form-item>
              <Form-item>
                  <Button type="success" long size="large" @click="validate('loginInfo')">登录</Button>
              </Form-item>
            </Form>
            <!-- <Row type="flex" align="middle" justify="end" :gutter="10">
              <Col :span="5" >
                <span @click="$jump('/findPassword')">忘记密码</span>
              </Col>
              <Col :span="5">
                <span @click="$jump('/register')">免费注册</span>
              </Col>
            </Row> -->
          </div>
        </div>
      </div>
    </div>
    <!-- 图片链接 -->
    <!-- <div class="login-adLink">
      <a href="www.aiyangniu.cn" target="_blank"></a>
    </div> -->
    <!-- 登陆框 -->
    
  </div>
</div>
</template>

<script>
import {mapGetters,mapActions} from 'vuex'
export default {
  name:'index',
  components: {
  },
  data () {
    return {
      loginInfo: {      //登录表单
        username: '',     //用户名
        password: '',      //密码
        superAdmin: 1
      },
      ruleInline: {
        username: [
            { required: true, message: '请填写用户名', trigger: 'blur' }
        ],
        password: [
            { required: true, message: '请填写密码', trigger: 'blur' },
            // { type: 'string', min: 6, message: '密码长度不能小于6位', trigger: 'blur' }
        ]
      },
      heightValue: {height:''},
    }
  },
  computed:{
    ...mapGetters([
      'userToken',         //用户token
      'isGetToken',        //是否成功获取token
      'userInfo',          //用户信息
      'loginMessage',      //登录返回的提示信息
      'loginBg',            //登录背景
    ]),
    //背景图片
    bg(){
      // let url = this.loginBg.filePath || ''
      // let bg = 'background-image: url('+url+')'
      // return bg
    }
  },
  methods:{
    ...mapActions([
      'getUserToken',     //用户登录,获取token
      'getUserInfo',      //获取用户信息
      'getLoginBg',       //获取登录背景
    ]),
    hh(){
　　　　this.heightValue.height=window.innerHeight+'px';
　　},
    //验证表单
    validate (name) {
      this.$refs[name].validate((valid) => {
        valid?this.login():this.$Message.error('登录失败!')
      })
    },
    //获取token,获取用户信息
    async login(){
      await this.getUserToken(this.loginInfo).then((res) => { //获取用户信息
        if(res.code == 0){ //检测是否登录成功
          localStorage.token = res.data
          this.$Message.success('登录成功,正在跳转...') //登录信息提示
          setTimeout(()=>{
            let lastPath = this.$route.query.lastPath || '/views/quarantine/index.html#/datav'
            window.location.href = lastPath
          },1000)
        }else{
          this.$Message.error(res.msg)  //登录信息提示
        }
      })
      await this.getUserInfo().then((res) => { }) //获取用户信息
    }
  },
  mounted(){
    // this.getLoginBg({
    //   contentTypeId:'11',
    //   index:true
    // })
    this.hh()
  }
}
</script>

<style lang="less" scoped>
@width:390px;
@height:600px;
.login{
  height: 100%;
  width: 100%;
  padding: 3% 5%;
  /*background: url('../../../../assets/imgs/bgImg.png');*/
  background-size: 100% 100%;
}
.titleBox{
    margin-top: 3%;
    margin-left: 4%;
    font-size: 24px;
    color: #3d83ff;
    font-weight: bold;
    letter-spacing: 5px;
    position: absolute;
    top: 2%;
    left: 1%;
}
.content{
  width: 100%;
  height: 100%;
  position: relative;
  /*background: url('../../../../assets/imgs/login-bg.jpg');*/
  background-size: 100% 100%;
  .title{
    margin-top:60px;
    text-align: center;
    color:#f9fafc;
    word-spacing:1em;
  }
  .login-newbg{
    position: absolute;
    z-index: 9;
    top: 0;
    left: 0;
    background-size: cover;
    background-repeat: no-repeat;
    background-position: center center;
    width: 100%;
    height:100%;
  }
  .login-adLink{
    width: @width;
    margin: 0 auto;
    position: relative;
    z-index: 1000;
    a{
      position: absolute;
      width: 630px;
      height: 440px;
      left: 80px;
      top: 80px;
    }
  }
  .content-layout{
    width: @width;
    margin: auto 60% 0;
    overflow: hidden;
    position: relative;
    height: 100%;
    // z-index: 999;
    .login-box-warp{
      width: 350px;
      position: absolute;
      top: 50%;
      margin-top: -50%;
      .login-box{
        box-sizing:initial;
        width: 300px;
        // padding: 25px 25px 23px;
        color: #6c6c6c;
        // position: relative;
        margin: 0 auto;
        .iconyonghuming,.iconmima{
          font-size:1.2em;
        }
      }
    }
  }
}
</style>
<style lang="less">
  .login{
    .ivu-form-item{
      margin-bottom:35px !important;
    }
  }
</style>
<template>
<div style="background:#f0f2f5;margin-top: -20px;">
  <div class="wscn-http404">
    <div class="pic-404">
      <img class="pic-404--parent" src="../assets/img/404.png" alt="404">
      <img class="pic-404--child left" src="../assets/img/404-cloud.png" alt="404">
      <img class="pic-404--child mid" src="../assets/img/404-cloud.png" alt="404">
      <img class="pic-404--child right" src="../assets/img/404-cloud.png" alt="404">
    </div>
    <div class="bullshit">
      <div class="bullshit--oops">不存在的页面</div>
      <div class="bullshit--info">版权所有
        <a class="link-type" @click="$open('index/index.html#/home')" target="_blank">爱养牛</a>
      </div>
      <div class="bullshit--headline">{{ message }}</div>
      <div class="bullshit--info">请检查您输入的网址是否正确，请点击以下按钮返回主页</div>
      <a href="/views/index/index.html#/home" class="bullshit--return-home">返回爱养牛首页</a>
    </div>
  </div>
</div>
</template>

<script>
export default {
  computed: {
    message() {
      return '攻城狮们挤破头都进不了......'
    }
  }
}
</script>

<style rel="stylesheet/lesss" lang="less" scoped>
.wscn-http404 {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    width: 1200px;
    margin: 20px auto 60px;
    padding: 0 100px;
    overflow: hidden;

    .pic-404 {
        position: relative;
        float: left;
        width: 600px;
        padding: 150px 0;
        overflow: hidden;
        &--parent {
            width: 100%;
        }
        &--child {
            position: absolute;
            &.left {
                width: 80px;
                top: 17px;
                left: 220px;
                opacity: 0;
                animation-name: cloudLeft;
                animation-duration: 2s;
                animation-timing-function: linear;
                animation-fill-mode: forwards;
                animation-delay: 1s;
            }
            &.mid {
                width: 46px;
                top: 10px;
                left: 420px;
                opacity: 0;
                animation-name: cloudMid;
                animation-duration: 2s;
                animation-timing-function: linear;
                animation-fill-mode: forwards;
                animation-delay: 1.2s;
            }
            &.right {
                width: 62px;
                top: 100px;
                left: 500px;
                opacity: 0;
                animation-name: cloudRight;
                animation-duration: 2s;
                animation-timing-function: linear;
                animation-fill-mode: forwards;
                animation-delay: 1s;
            }
            @keyframes cloudLeft {
                0% {
                    top: 17px;
                    left: 220px;
                    opacity: 0;
                }
                20% {
                    top: 33px;
                    left: 188px;
                    opacity: 1;
                }
                80% {
                    top: 81px;
                    left: 92px;
                    opacity: 1;
                }
                100% {
                    top: 97px;
                    left: 60px;
                    opacity: 0;
                }
            }
            @keyframes cloudMid {
                0% {
                    top: 10px;
                    left: 420px;
                    opacity: 0;
                }
                20% {
                    top: 40px;
                    left: 360px;
                    opacity: 1;
                }
                70% {
                    top: 130px;
                    left: 180px;
                    opacity: 1;
                }
                100% {
                    top: 160px;
                    left: 120px;
                    opacity: 0;
                }
            }
            @keyframes cloudRight {
                0% {
                    top: 100px;
                    left: 500px;
                    opacity: 0;
                }
                20% {
                    top: 120px;
                    left: 460px;
                    opacity: 1;
                }
                80% {
                    top: 180px;
                    left: 340px;
                    opacity: 1;
                }
                100% {
                    top: 200px;
                    left: 300px;
                    opacity: 0;
                }
            }
        }
    }
    .bullshit {
        position: relative;
        float: left;
        width: 300px;
        padding: 150px 0;
        overflow: hidden;
        &--oops {
            font-size: 32px;
            font-weight: bold;
            line-height: 40px;
            color: #1482f0;
            opacity: 0;
            margin-bottom: 20px;
            animation-name: slideUp;
            animation-duration: 0.5s;
            animation-fill-mode: forwards;
        }
        &--headline {
            font-size: 20px;
            line-height: 24px;
            color: #1482f0;
            opacity: 0;
            margin-bottom: 10px;
            animation-name: slideUp;
            animation-duration: 0.5s;
            animation-delay: 0.1s;
            animation-fill-mode: forwards;
        }
        &--info {
            font-size: 13px;
            line-height: 21px;
            color: grey;
            opacity: 0;
            margin-bottom: 30px;
            animation-name: slideUp;
            animation-duration: 0.5s;
            animation-delay: 0.2s;
            animation-fill-mode: forwards;
        }
        &--return-home {
            display: block;
            float: left;
            width: 150px;
            height: 36px;
            background: #1482f0;
            border-radius: 100px;
            text-align: center;
            color: #ffffff;
            opacity: 0;
            font-size: 14px;
            line-height: 36px;
            cursor: pointer;
            animation-name: slideUp;
            animation-duration: 0.5s;
            animation-delay: 0.3s;
            animation-fill-mode: forwards;
        }
        @keyframes slideUp {
            0% {
                transform: translateY(60px);
                opacity: 0;
            }
            100% {
                transform: translateY(0);
                opacity: 1;
            }
        }
    }
}
</style>

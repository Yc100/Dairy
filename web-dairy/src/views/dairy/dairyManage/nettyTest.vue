<template>
  <div class="dairyManage">
    <el-input type="textarea" placeholder="请输入内容" v-model="textarea"></el-input>
    <el-button type="success" @click="senMsg()">发送</el-button>

    <div v-for="(list,index) in msgList">
      <P>{{list}}</P>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import api from "service/apiConfig";
import axios from "axios";
import { getOneFriendId } from "service/api";
import Ueditor from "components/editor/Ueditor";
export default {
  name: "nettyTest",
  components: {
    Ueditor
  },
  props: {},
  data() {
    return {
      textarea: "",
      msgList: [],
      webSocket: null,
      dataContent: {
        action: 2,
        chatMsg: {
          senderId: "",
          receiverId: "",
          msg: ""
        }
      },
      friend: {},
      // 心跳定时器
      webSocketPingTimer: null,
      webSocketPingTime: 10000 // 心跳的间隔，当前为 10秒,
    };
  },
  computed: {
    ...mapGetters([
      "userToken", //用户token
      "isGetToken", //是否成功获取token
      "userInfo" //用户信息
    ])
  },
  methods: {
    ...mapActions([]),

    initWebSocket() {
      this.webSocket = new WebSocket("ws://172.168.1.177:8088/ws");
      this.webSocket.onopen = this.webSocketHandleOpen;
      this.webSocket.onerror = this.webSocketHandleError;
      this.webSocket.onmessage = this.webSocketHandleMessage;
      this.webSocket.onclose = this.webSocketHandleClose;
    },

    // WebSocket 打开成功后
    webSocketHandleOpen() {
      console.log("与后端websocket服务连接建立成功");
      this.webSocketIsOpen = true;
      // 清空重连的次数
      this.webSocketReconnectCount = 0;
      // 发送登录信息
      this.dataContent.action = 1;
      this.dataContent.chatMsg.senderId = this.userInfo.id;
      this.webSocket.send(JSON.stringify(this.dataContent));
      // 开启定时心跳
      this.webSocketPing();
    },

    async getOneFriend() {
      await getOneFriendId()
        .then(res => {
          //获取用户信息
          if (res.code == 0) {
            //检测是否登录成功
            this.friend.friendId = res.data.friendId;
            this.friend.name = res.data.userName;
          } else {
            this.$Message.error(err.msg);
          }
        })
        .catch(err => {
          this.loading = false;
          this.$Message.error(err.msg);
        });
    },

    senMsg() {
      console.log("我要看账号的信息", this.userInfo.id);
      this.dataContent.action = 2;
      this.dataContent.chatMsg.senderId = this.userInfo.id;
      this.dataContent.chatMsg.receiverId = this.friend.friendId;
      this.dataContent.chatMsg.msg = this.textarea;
      this.msgList.push("我：" + this.textarea);
      this.textarea = "";
      this.webSocket.send(JSON.stringify(this.dataContent));
    },
    // WebSocket 发生错误时
    webSocketHandleError(err) {
      console.log("连接报错：", err);
      // 关闭心跳
      //this.webSocketClose();
    },
    // 接收到消息时
    webSocketHandleMessage(event) {
      // 响应体的message
      const data = event.data;
      console.log("接收到的消息" + data);
      let msgObj = JSON.parse(data);
      console.log(msgObj);
      msgObj.chatMsg.msg;
      this.msgList.push(this.friend.name + "说：" + msgObj.chatMsg.msg);
    },
    webSocketPing() {
      this.webSocketPingTimer = setTimeout(() => {
        console.log("心跳");
        if (!this.webSocketIsOpen) {//这个地方终止了前端定时
          return false;
        }

        const dataContent = {
          action: 4
        };
        this.webSocket.send(JSON.stringify(dataContent));
        clearTimeout(this.webSocketPingTimer);
        // 重新执行
        this.webSocketPing();
      }, this.webSocketPingTime);
    },
    // WebSocket 关闭
    webSocketHandleClose() {
      console.log("与后端websocket服务断开连接");
      // 关闭心跳
      this.webSocketClose();
    },
    // 断开连接时
    webSocketClose() {
      // 修改状态为未连接
      this.webSocketIsOpen = false;
      this.webSocket = null;
      // 判断是否重连
    },
    wsOut() {
      // 修改重连状态
      this.webSocketIsReconnect = false;
      if (this.webSocket) {
        console.log("关闭websocket");
        // 关闭 websocket
        this.webSocket.close();
      }
    }
  },

  mounted() {
    //模拟获取一个好友的id
    this.getOneFriend();
    this.initWebSocket();
  },

  watch: {},
  // 组件销毁时触发
  destroyed() {
    // 关闭 websocket 链接
    this.wsOut();
  }
};
</script>

<style lang="less" scoped>
.dairyManage {
  width: 95%;
  margin: auto auto;
  padding-top: 2em;
  .belowArea {
    font-size: 15px;
    font-weight: bold;
    .iconyiliaohangyedeICON- {
      color: red;
    }
  }
}
</style>
<style lang="less">
</style>

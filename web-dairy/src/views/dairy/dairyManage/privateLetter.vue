<template>
  <div id="app" class="wrapper">
   <el-alert v-if="online"
    title="对方在线"
    type="success"/>

    <el-alert v-if="!online"
    title="对方离线"
    type="info"/>

      <JwChat-index
          :config="config"
          :taleList="msgList"
          @enter="talkEvent"
          v-model="inputMsg"
          :showRightBox='false'
          scrollType  ="scroll"
          :toolConfig="tool"
          @clickTalk="talkEvent"
          >
          <!-- @clickTalk="talkEvent" -->
          <!-- <JwChat-rightbox class="rightSlot" :config="rightConfig" @click="rightClick" /> -->
          <!-- <JwChat :taleList="msgList" @enter="talkEvent" v-model="inputMsg" :toolConfig="tool"  /> -->
          <b slot="tools">H</b>
      </JwChat-index>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import api from "service/apiConfig";
import axios from "axios";
import { getOneFriendId ,getFriendMsgHistoryList} from "service/api";
import Ueditor from "components/editor/Ueditor";
export default {
  name: "privateLetter",
  components: {
    Ueditor
  },
  props: {},
  data() {
    return {
      online:false,
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
      friendId:'',
      // 心跳定时器
      webSocketPingTimer: null,
      webSocketPingTime: 10000, // 心跳的间隔，当前为 10秒,
      msgList:[{
        date: "2020/04/25 21:19:07",
        text: { "text": "起床不" },
        mine: false,
        name: "只盼流星不盼雨",
        img: "image/two.jpeg"
    },
    {
        date: "2020/04/16 21:19:07",
        text: { "text": "我不饿" },
        mine: true,
        name: "留恋人间不羡仙",
        img: "image/three.jpeg"
    },],
      inputMsg:'',
      tool:{},
      rightConfig: {
        listTip: '当前在线',
        // notice: '【公告】这是一款高度自由的聊天组件，基于AVue、Vue、Element-ui开发。点个赞再走吧 ',
        list: [
          {
            name: 'JwChat',
            "img": "image/three.jpeg"
          },
          {
            name: 'JwChat',
            "img": "image/three.jpeg"
          },
          {
            name: '留恋人间不羡仙',
            "img": "image/one.jpeg"
          },
          {
            name: '只盼流星不盼雨',
            "img": "image/two.jpeg"
          }
        ]
      },
      config: {
        img: '/image/cover.png',
        name: 'yc',
        dept: 'Memory Dairy',
        // callback: this.bindCover,
        historyConfig:{
          tip: '查看全部',
          callback: this.bindLoadHistory,
        }
      },
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

bindEnter(){

},
    initWebSocket() {
      //this.webSocket = new WebSocket("ws://172.168.1.177:8088/ws");
      this.webSocket = new WebSocket("ws://localhost:8088/ws");
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

    async getOneFriend(friendId) {
      await getOneFriendId({friendId:friendId})
        .then(res => {
          //获取用户信息
          if (res.code == 0) {
            //检测是否登录成功
            this.friend.friendId = res.data.friendId;
            this.config.name = res.data.name
            this.config.img = res.data.img
            this.msgList = res.data.chatMsgDtoList

         
           
     
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
      this.dataContent.action = 2;
      this.dataContent.chatMsg.senderId = this.userInfo.id;
      this.dataContent.chatMsg.receiverId = this.friend.friendId;
      this.dataContent.chatMsg.msg = this.textarea;
      this.msgList.push("我：" + this.textarea);
      this.textarea = "";
      this.webSocket.send(JSON.stringify(this.dataContent));
    },
    talkEvent(){
      this.dataContent.action = 2;
      this.dataContent.chatMsg.senderId = this.userInfo.id;
      this.dataContent.chatMsg.receiverId = this.friend.friendId;
      this.dataContent.chatMsg.msg = this.inputMsg;

      let date = new Date();
      let y = date.getFullYear(); // 年
      let MM = date.getMonth() + 1; // 月
      MM = MM < 10 ? "0" + MM : MM;
      let d = date.getDate(); // 日
      d = d < 10 ? "0" + d : d;
      let h = date.getHours(); // 时
      h = h < 10 ? "0" + h : h;
      let m = date.getMinutes(); // 分
      m = m < 10 ? "0" + m : m;
      let s = date.getSeconds(); // 秒
      s = s < 10 ? "0" + s : s;
      let cuDate = y + "-" + MM + "-" + d + " " + h + ":" + m + ":" + s;

      let msg = {
        date: cuDate,
        text: { text: this.inputMsg },
        mine: true,
        name: this.userInfo.nickName,
        img: this.userInfo.headUrl
      }
      this.msgList.push(msg)
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
      if(msgObj.extAnd){
          //心跳返回
          if(msgObj.extAnd == '1'){
            this.online = true
          }else{
            this.online = false
          }
      }else{
msgObj.chatMsg.msg;
      //this.msgList.push(this.friend.name + "说：" + msgObj.chatMsg.msg);

      let date = new Date();
      let y = date.getFullYear(); // 年
      let MM = date.getMonth() + 1; // 月
      MM = MM < 10 ? "0" + MM : MM;
      let d = date.getDate(); // 日
      d = d < 10 ? "0" + d : d;
      let h = date.getHours(); // 时
      h = h < 10 ? "0" + h : h;
      let m = date.getMinutes(); // 分
      m = m < 10 ? "0" + m : m;
      let s = date.getSeconds(); // 秒
      s = s < 10 ? "0" + s : s;
      let cuDate = y + "-" + MM + "-" + d + " " + h + ":" + m + ":" + s;

      let msg = {
        date: cuDate,
        text: { text: msgObj.chatMsg.msg},
        mine: false,
        name: this.config.name,
        img: this.config.img
      }
      this.msgList.push(msg)
      // this.$emit('scrollRefresh');
     
      // this.$nextTick(() => {
      //     let obj = document.getElementsByClassName('iScrollIndicator');
      //         obj.scrollTop = this.msgList.scrollHeight // 滚动高度
      // })
      }
      

    },
    webSocketPing() {
      this.webSocketPingTimer = setTimeout(() => {
        console.log("心跳");
        if (!this.webSocketIsOpen) {//这个地方终止了前端定时
          return false;
        }

        const dataContent = {
          action: 4,
          extAnd:this.friendId//用来获取判断对方是否在线
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
    },
    async bindLoadHistory () {
      await getFriendMsgHistoryList({friendId:this.friend.friendId})
        .then(res => {
          //获取用户信息
          if (res.code == 0) {
            //检测是否登录成功
            this.msgList = res.data
          } else {
            this.$Message.error(err.msg);
          }
        })
        .catch(err => {
          this.loading = false;
          this.$Message.error(err.msg);
        });
    },
  },

  mounted() {
    //模拟获取一个好友的id
    this.friendId = this.$route.query.friendId
    this.getOneFriend(this.$route.query.friendId);
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

<style  lang="less" scoped>
.rightSlot {
  width: 100%;
  height: 100%;
  overflow: hidden;
  flex-direction: column;
}
</style>
<style>
.active {
  color: red;
}
* {
  padding: 0;
  margin: 0;
}
body {
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  background-image: linear-gradient(to bottom right, #7a88ff, #7affaf);
}
#app {
  height: 100vh;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

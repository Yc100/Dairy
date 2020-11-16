<template>
  <div v-loading="loading" class="dairyManage">
    <Row>
      <Col span="4">
        <h2 style>文章标题:</h2>
      </Col>
      <Col span="8">
        <Input v-model="title" style="margin-left: -16%;" placeholder="请输入标题" />
      </Col>
      <Col span="4">
        <h2 style>描述:</h2>
      </Col>
      <Col span="8">
        <Input
          v-model="desc"
          style="margin-left: -22%;margin-bottom: 20px;"
          type="textarea"
          :autosize="{minRows: 2,maxRows: 2}"
          placeholder="描述一下..."
        ></Input>
      </Col>
    </Row>
    <!-- <Row>
        
    </Row>-->
    <div>
      <!-- <Ueditor @ready="editorReady" style="display:inline-block;width: 800px; height: 100%; margin: 0px auto;"></Ueditor>-->
      <Ueditor @ready="editorReady" style="margin: 0px auto;"></Ueditor>
    </div>
    <div style="text-align: center; margin-top: 10px;margin-bottom: 10px;">
      <Button type="primary" @click="saveDairy">保存</Button>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import api from "service/apiConfig";
import axios from "axios";
import { getDairyDetail, updateDairyUser } from "service/api";
import Ueditor from "components/editor/Ueditor";
export default {
  name: "writeDairy",
  components: {
    Ueditor
  },
  props: {},
  data() {
    return {
      loading: false,
      dairyId: "",
      tableData: {},
      content: "",
      title: "",
      desc: ""
    };
  },
  computed: {},
  methods: {
    ...mapActions([]),
    //ueditor编辑模板
    async editorReady(instance) {
      this.editorReadyInstance = instance;
      if (!instance) {
        return false;
      }
      setTimeout(
        function() {
          instance.setContent(_.cloneDeep(this.content));
          instance.setHeight(433); //设置富文本的高度
          instance.addListener("contentChange", () => {
            this.content = instance.getContent(); // 获取内容方法
            //console.log(this.content);
          });
        }.bind(this),
        20
      );
    },
    saveDairy() {
      if (!this.title || this.title.trim() == "") {
        this.$Message["error"]({
          background: true,
          content: "请输入标题！"
        });
        this.title = "";
        return;
      }
      if (!this.content || this.content.trim() == "") {
        this.$Message["error"]({
          background: true,
          content: "保存前请填写正文哦~！"
        });
        this.content = "";
        this.editorReadyInstance.setContent(_.cloneDeep(this.content));
        return;
      }

      //通过校验
      let dairy = {
        title: this.title,
        dairyDescribe: this.desc,
        mainText: this.content,
        id:this.dairyId
      };
      this.updateDairy(dairy)
    },
    async updateDairy(data) {
      await updateDairyUser(data)
        .then(res => {
          //获取用户信息
          if (res.code == 0) {
            //检测是否登录成功
            //保存成功
            this.$Message["success"]({
              background: true,
              content: "保存成功",
              onClose: ()=>{
                  this.goList()
              },
            });
           
          } else {
            this.$Message.error(err.msg);
          }
        })
        .catch(err => {
          this.loading = false;
          this.$Message.error(err.msg);
        });
    },
    goList(){
        this.$router.push({path: "/myDairyManage"});
    },
    async getDairyDetail(id){
            await getDairyDetail({dairyId:id}).then((res) => { //获取用户信息
                this.loading = false;
                if(res.code == 0){ //检测是否登录成功
                    this.tableData=res.data
                    //this.readDairy(this.tableData.id)
                    this.title=res.data.title
                    this.desc=res.data.dairyDescribe
                    this.content=res.data.mainText
                    this.dairyId=res.data.id
                    this.editorReadyInstance.setContent(_.cloneDeep(this.content));
                    //console.log(this.content)
                }else{
                    this.$Message.error(err.msg);
                }
            }).catch(err=>{
                this.loading = false;
                this.$Message.error(err.msg);
            })
        },
  },

  mounted() {
      this.dairyId=this.$route.query.dairyId
      this.getDairyDetail(this.$route.query.dairyId)

  },

  watch: {}
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

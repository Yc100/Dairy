/**
 * 描述：畜牧局的个人信息管理
 * 作者：zhangb
 * 时间：2020-01-08
 * 依赖：
 * 注意：
 * props：
 */
<template>
  <div class="">
    <div style="width:100%;margin-top:1em;padding:1em;">
      <div style="font-size:20px; color:#333333; padding:0 0px 10px 5px; font-weight:bolder;" >基本信息</div>
      <Card style="width:100%">
       <Row>
          <Col offset="1" span="10">组织类型：畜牧局（区）</Col>
          <Col span="13">组织名称：赛罕区畜牧管理局</Col>
        </Row>
        <Row style="margin-top:10px;">
          <Col offset="1" span="10">组织缩写：SHQXQGLJ</Col>
          <Col span="12">单位地址：内蒙古呼和浩特市赛罕区乌兰察布东</Col>
        </Row>
        <Row style="margin-top:10px;">
          <Col offset="1" span="10">联系人：张三</Col>
          <Col span="12">联系电话：0471-6521245</Col>
        </Row>
        <Row style="margin-top:10px;">
          <Col offset="1" span="20">组织简介：本组织致力为各县及地区动物畜禽防疫工作安排处理，积极响应各级政府工作安排，国家重要防疫办法管理通知等，做好畜禽防疫，保证卫生安全。</Col>
        </Row>
        <Row style="margin-top:30px;">
          <Col offset="1" span="10">注册单位：内蒙古呼和浩特市级畜牧局</Col>
          <Col span="12">注册编号：A00HHHT00251421221Z</Col>
        </Row>
        <Row style="margin-top:10px;">
          <Col offset="1" span="10">机构证书扫描件：</Col>
          <Col span="12"><div style="width:400px; height:250px; border:1px solid #eee;"><img src="../../../assets/imgs/noimg.png" width="100%" height="100%" alt=""></div></Col>
        </Row>
        <!-- <Row style="margin-top:10px;">
          <Col offset="1" span="10">法人身份证扫描件（反面）：</Col>
          <Col span="12"><div style="width:400px; height:250px;"><img src="" width="100%" height="100%" alt=""></div></Col>
        </Row>
        <Row style="margin-top:20px;">
          <Col offset="1" span="10">营业执照扫描件：</Col>
          <Col span="12"><div style="width:400px; height:250px;"><img src="" width="100%" height="100%" alt=""></div></Col>
        </Row> -->
      </Card>
    </div>
    <br>

  </div>
</template>
<script>
import {mapGetters,mapActions} from 'vuex'
export default {
  name: '',
  components:{
  },
  props:{

  },
  computed:{
    ...mapGetters([

    ])
  },
  data(){
    return {

    }
  },

  methods:{
    ...mapActions([
     
    ]),
 
  },

  mounted(){
  },
}
</script>
<style lang="less" scoped>
.echars-container{
  // border:1px solid #000;
  width: 100%;
  height: 500px;
}
.orgLogo{
  width: 100%;
  text-align:center;
}
</style>

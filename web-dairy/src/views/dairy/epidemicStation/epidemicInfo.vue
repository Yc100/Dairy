/**
 * 描述：防疫站的个人信息管理
 * 作者：zhangb
 * 时间：2020-01-08
 * 依赖：
 * 注意：
 * props：
 */
<template>
  <div class="epidemicInfo">
    <div style="width:100%;margin-top:1em;padding:1em;">
      <div style="font-size:20px; color:#333333; padding:0 0px 10px 5px; font-weight:bolder;" >基本信息</div>
        <Card style="width:100%">
          <Row>
            <Col offset="1" span="10">用户名称：{{userInfo.nickName}}</Col>
            <Col span="13">邮箱：{{userInfo.email}}</Col>
          </Row>
          <Row style="margin-top:10px;">
            <Col offset="1" span="10">性别：{{userInfo.gender | genderValue}}</Col>
            <Col span="12">联系方式：{{userInfo.mobile}}</Col>
          </Row>
          <Row style="margin-top:10px;">
            <Col offset="1" span="10">所属地区：{{userInfo.areaName}}</Col>
          </Row>
          <!-- <Row style="margin-top:10px;">
          <Col offset="1" span="10">机构证书扫描件：</Col>
          <Col span="12"><div style="width:400px; height:250px; border:1px solid #eee;"><img src="../../../assets/imgs/noimg.png" width="100%" height="100%" alt=""></div></Col>
          </Row> -->
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
  </div>
</template>
<script>
import {mapGetters,mapActions} from 'vuex' 
import {countByUser} from 'service/api'
export default {
  name: 'epidemicInfo',
  components:{
  },
  props:{

  },
  computed:{
    ...mapGetters([
      'userInfo',        //用户信息
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
  created(){
	}
}
</script>
<style lang="less" scoped>
.epidemicInfo{
  
}
.messageBox{
  width: 95%;
  height: 140px;
}
</style>

/**
 * 模板名称:新增防疫站
 * 作者：zhangb
 * 时间：2020-01-21
 * 依赖：
 * 注意：
 * props：
 */
<template>
  <div class="addNewEpidemicStation">
    <Button type="primary" size="small" @click="$router.back()" style="float:right;">返回</Button>
    <div style="width:70%;">
    	<Tag color="success">新增防疫站</Tag>
      	<br><br>
      	<Form ref="EpidemicStationValidate" :model="EpidemicStationValidate" :rules="ruleEpidemicStationValidate" :label-width="170">
          <FormItem label="组织类型" prop="epidemicStationType">
            <Input v-model="EpidemicStationValidate.epidemicStationType" placeholder="请选择组织类型" style="width:50%;"></Input>
          </FormItem>
          <FormItem label="组织名称" prop="epidemicStationName">
            <Input v-model="EpidemicStationValidate.epidemicStationName" placeholder="请输入组织名称" style="width:50%;"></Input>
          </FormItem>
          <FormItem label="组织名称缩写" prop="epidemicStaticShort">
            <Input v-model="EpidemicStationValidate.epidemicStaticShort" placeholder="请输入组织名称缩写" style="width:50%;"></Input>
          </FormItem>
          <FormItem label="单位地址" prop="epidemicStaticAddress">
            <Input v-model="EpidemicStationValidate.epidemicStaticAddress" placeholder="请输入单位地址" style="width:70%;"></Input>
          </FormItem>
          <FormItem label="联系人" prop="contentPeople">
            <Input v-model="EpidemicStationValidate.contentPeople" placeholder="请输入联系人" style="width:70%;"></Input>
          </FormItem>
          <FormItem label="联系电话" prop="contentPhone">
            <Input v-model="EpidemicStationValidate.contentPhone" placeholder="请输入联系电话" style="width:50%;"></Input>
          </FormItem>
          <FormItem label="注册单位" prop="regOrganization">
            <Input v-model="EpidemicStationValidate.regOrganization" placeholder="请输入注册单位" style="width:50%;"></Input>
          </FormItem>
          <FormItem label="注册编号" prop="regNumber">
            <Input v-model="EpidemicStationValidate.regNumber" placeholder="请输入注册编号" style="width:50%;"></Input>
          </FormItem>
          <Form-item label="机构证书扫描件" prop="epidemicQualificationUrl">
            <Upload :headers="{'X-AIYANGNIU-SIGNATURE':'token'}" :accept="accept" :action="uploadUrl" :on-success="uploadQualification" :show-upload-list="false">
              <div v-if="!EpidemicStationValidate.epidemicQualificationUrl" style="width:400px; height:240px; border:1px dotted #ccc;text-align:center; line-height:240px; font-size:25px; color:#ccc;cursor:pointer; border-radius:5px;">+</div>
              <div v-if="EpidemicStationValidate.epidemicQualificationUrl" style="width:400px; height:240px;border:1px dotted #eee;"><img :src="EpidemicStationValidate.epidemicQualificationUrl" alt="" width="100%" height="100%"></div>
            </Upload>
          </Form-item>
          <FormItem style="text-align:center;">
            <Button type="primary" @click="handleSubmit('EpidemicStationValidate')">提交</Button>
            <Button @click="handleReset('EpidemicStationValidate')" style="margin-left: 20px">重置</Button>
          </FormItem>
      	</Form>
    </div>
  </div>
</template>
<script>
import api from 'service/apiConfig' //真实接口配置
// import {org} from 'service/api'
import {mapGetters} from 'vuex'
export default {
  name: 'addNewEpidemicStation',
  components:{
  },
  props:{

  },
  computed:{
    ...mapGetters([
      'userToken',         //用户token
      'isGetToken',        //是否成功获取token
      'userInfo',          //用户信息
    ]),
    //上传图片的url地址
    uploadUrl(){
      return api.apiItem +'/images/upload?service=account'
    }
  },
  data(){
    return {
      accept:'image/jpg,image/jpeg,image/png',  //上传图片类型
      EpidemicStationValidate: {
      epidemicStationType: '',     //组织类型
      epidemicStationName:'',  //组织名称
      epidemicStaticShort:'',  //组织缩写名称
      epidemicStaticAddress:'', //组织详细地址
      regOrganization:'', //注册单位
      regNumber:'', // 注册编号
      epidemicQualificationUrl:'', //请上传机构证书扫描件
		}, 
		ruleEpidemicStationValidate: {
			epidemicStationType: [{ required: true, message: '请选择组织类型', trigger: 'change' }],
			epidemicStationName: [{ required: true, message: '请输入组织名称', trigger: 'blur' }],
			epidemicStaticShort:[{ required: true, message: '请输入组织名称缩写', trigger: 'change' }],
			epidemicStaticAddress:[{ required: true, message: '请输入单位地址', trigger: 'blur' }],
			regOrganization:[{ required: true, message: '请输入注册单位', trigger: 'blur' }],
			regNumber:[{ required: true, message: '请输入注册编号', trigger: 'blur' }],
			epidemicQualificationUrl:[{ required: true, message: '请上传机构证书扫描件', trigger: 'blur' }],
		},
		areaList:[
			{label:'人民路街道',value:1},
			{label:'大学西路街道',value:2},
			{label:'乌兰察布路街道',value:3},
			{label:'大学东路街道',value:4},
			{label:'中专路街道',value:5},
			{label:'巧报镇',value:6},
			{label:'榆林镇',value:7},
		],
		addAreaVal:'',  //新增的区域值
    }
  },

  methods:{
	
    // 提交新增
    handleSubmit (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
        
        //   await org.addNewPasture().then((res) =>{
        //     if(res.code == 101){
        //       this.$Message.success(res.message || '新增成功');
        //       this.$router.back()
        //     }else{
        //       this.$Message.error({
        //         duration:3,
        //         content: res.message || '新建失败'
        //       });
        //     }
        //   })
          
        } else {
          this.$Message.error({
            duration: 3,
            content:'请检查各项内容是否填写完整！'
          });
        }
      })
    },

    // 重置表单
    handleReset (name) {
      this.$refs[name].resetFields(); 
    },

    //上传法人身份证扫描件
    uploadCard(res){
      if(res.code == '101'){
        this.EpidemicStationValidate.idCardUrl = res.data ? res.data : ''
        this.$Message.success(res.message)
      }else{
        this.EpidemicStationValidate.idCardUrl = ''
        this.$Message.error(res.message)
      }
    },

    //上传法人身份证扫描件反面
    uploadCardBack(res){
      if(res.code == '101'){
        this.EpidemicStationValidate.idCardBackUrl = res.data ? res.data : ''
        this.$Message.success(res.message)
      }else{
        this.EpidemicStationValidate.idCardBackUrl = ''
        this.$Message.error(res.message)
      }
    },

    // 上传营业执照
    uploadQualification(res){
      if(res.code == '101'){
        this.EpidemicStationValidate.bussLicenseUrl = res.data ? res.data : ''
        this.$Message.success(res.message)
      }else{
        this.EpidemicStationValidate.bussLicenseUrl = ''
        this.$Message.error(res.message)
      }
	},
	
	// 增加一个管辖区域
    addOneArea(){
      if(this.addAreaVal){
        console.log('addareaval',this.addAreaVal)
        this.EpidemicStationValidate.manageAreaList.push({area:this.addAreaVal.split('|')[1]})
      }else{
        this.$Message.warning('请先选择一个区域')
      }
	},
	
	// 删除一个区域
    delOneArea(item,index){
      let _this = this
      console.log('item',item)
      this.$Modal.confirm({
        title: '温馨提示',
        content: '<p>您确定删除该管辖区域吗？</p>',
        onOk:() => {
          _this.EpidemicStationValidate.manageAreaList.splice(index,1)
          this.$Message.success('删除成功')
        },
        onCancel: () => {
          this.$Message.info('您已取消操作！');
        }
      });
    },
  },

  mounted(){

  },
}
</script>
<style lang="less" scoped>
.addNewEpidemicStation{
	width: 95%; margin: 1.5em auto;
}
</style>

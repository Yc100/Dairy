/**
 * 模板名称:新增员工
 * 作者：zhangb
 * 时间：2020-01-09
 * 依赖：
 * 注意：
 * props：
 */
<template>
  <div class="addNewEpidemic">
    <Button type="primary" size="small" @click="$jump('/epidemicPersonList')" style="float:right;">返回</Button>
    <div style="width:70%;">
    	<Tag color="success">新增员工</Tag>
      	<br><br>
      	<Form ref="checkEpidemicData" :model="checkEpidemicData" :rules="ruleAddEpidemicValidate" :label-width="170">
          <FormItem label="员工姓名" prop="realName">
            <Input v-model="checkEpidemicData.realName" placeholder="请输入员工姓名" style="width:50%;"></Input>
          </FormItem>
          <FormItem label="员工年龄" prop="age">
            <Input v-model="checkEpidemicData.age" :min="1" placeholder="请输入员工年龄" style="width:50%;"></Input>
          </FormItem>
          <FormItem label="性别" prop="gender">
            <RadioGroup v-model="checkEpidemicData.gender">
              <Radio label='1'>男</Radio>
              <Radio label='2'>女</Radio>
            </RadioGroup>
          </FormItem>
          <FormItem label="账号" prop="username">
            <Input v-model="checkEpidemicData.username"  placeholder="请输入账号" style="width:50%;"></Input>
          </FormItem>
          <FormItem label="密码" prop="password">
            <Input v-model="checkEpidemicData.password"  placeholder="请输入密码" style="width:50%;"></Input>
          </FormItem>
          <!-- <FormItem label="身份证号" prop="epidemicIdCard">
            <Input v-model="checkEpidemicData.epidemicIdCard" placeholder="请输入员工身份证号" style="width:50%;"></Input>
          </FormItem>
          <FormItem label="籍贯" prop="epidemicJG">
            <Input v-model="checkEpidemicData.epidemicJG" placeholder="请输入员工籍贯信息" style="width:70%;"></Input>
          </FormItem> -->
          <FormItem label="联系电话" prop="mobile">
            <Input v-model="checkEpidemicData.mobile" placeholder="请输入员工联系电话" style="width:50%;"></Input>
          </FormItem>
          <Form-item label="身份证扫描件" prop="idCard1">
            <Upload 
              ref="upload"
              :format="['jpg','jpeg','png']" 
              :action="uploadUrl" 
              :on-success="uploadCard" 
              :show-upload-list="false" 
              :max-size="2048"
              :headers="{'token':token}"
              :on-format-error="handleFormatError"
              :on-exceeded-size="handleMaxSize">
              <div v-if="!checkEpidemicData.idCard1" style="width:400px; height:240px; border:1px dotted #ccc;text-align:center; line-height:240px; font-size:25px; color:#ccc;cursor:pointer; border-radius:5px;">+</div>
              <div v-if="checkEpidemicData.idCard1" style="width:400px; height:240px;border:1px dotted #eee;"><img :src="checkEpidemicData.idCard1" alt="" width="100%" height="100%"></div>
            </Upload>
          </Form-item>
          <Form-item label="身份证扫描件(反面)" prop="idCard2">
            <Upload :accept="accept" :action="uploadUrl" :on-success="uploadCardBack" :show-upload-list="false" :max-size="2048"
              :headers="{'token':token}"
              :on-format-error="handleFormatError"
              :on-exceeded-size="handleMaxSize">
              <div v-if="!checkEpidemicData.idCard2" style="width:400px; height:250px; border:1px dotted #ccc;text-align:center; line-height:250px; font-size:25px; color:#ccc;cursor:pointer; border-radius:5px;">+</div>
              <div v-if="checkEpidemicData.idCard2" style="width:400px; height:250px;border:1px dotted #eee;"><img :src="checkEpidemicData.idCard2" alt="" width="100%" height="100%"></div>
            </Upload>
          </Form-item>
          <!-- <Form-item label="员工资格证书扫描件" prop="epidemicQualificationUrl">
            <Upload :headers="{'X-AIYANGNIU-SIGNATURE':'token'}" :accept="accept" :action="uploadUrl" :on-success="uploadQualification" :show-upload-list="false">
              <div v-if="!checkEpidemicData.epidemicQualificationUrl" style="width:400px; height:240px; border:1px dotted #ccc;text-align:center; line-height:240px; font-size:25px; color:#ccc;cursor:pointer; border-radius:5px;">+</div>
              <div v-if="checkEpidemicData.epidemicQualificationUrl" style="width:400px; height:240px;border:1px dotted #eee;"><img :src="checkEpidemicData.epidemicQualificationUrl" alt="" width="100%" height="100%"></div>
            </Upload>
          </Form-item> -->
          <FormItem label="岗位分配" prop="roleId">
            <RadioGroup v-model="checkEpidemicData.roleId" vertical>
              <Radio :label="item.roleId+''" v-for="item in postList" :key="item.roleId">
                <span>{{item.roleName}}</span>
              </Radio>
            </RadioGroup>
          </FormItem>
          <Form-item label="管辖区域分配" >
            <Card v-if="areaList.length>0">
              <Row>
                <Col span="18">选择区域：
                  <Select v-model="addAreaVal" clearable style="width:60%;" placeholder="请选择新增的区域">
                    <Option v-for="item in areaList" :value="item.areaId+'|'+item.areaName" :key="item.areaId">{{ item.areaName }}</Option>
                  </Select>
                </Col>
                <Col span="6" >
                  <Button type="primary" @click="addOneArea">确定新增</Button>
                </Col>
              </Row>

              <div style="border-top:1px solid #eee; width:97%; margin:1.5em auto;"></div>
              <p style="padding-left:0.5em;padding-bottom:0.3em;">已管辖区域：</p>
              <Tag closable type="dot" color="success" v-for="(item,index) in manageAreaList" :key="index" style="margin-left:0.5em;" @on-close="delOneArea(item,index)">{{item.areaName}}</Tag>
            </Card>
            <Card v-else>
              暂无空闲的管理区域可分配，您可在“区域管理”里对应更换管理人员!
            </Card>
          </Form-item>
          <FormItem style="text-align:center;">
            <Button type="primary" @click="handleSubmit('checkEpidemicData')">提交</Button>
            <Button @click="handleReset('checkEpidemicData')" style="margin-left: 20px">重置</Button>
          </FormItem>
      	</Form>
    </div>
  </div>
</template>
<script>
import api from 'service/apiConfig' //真实接口配置
import {selectWorkAreas,FileUpload,addSysUser,selectWorkUserAreas} from "service/api";
import {mapGetters} from 'vuex'
export default {
  name: 'addNewEpidemic',
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
      return api.apiNomal +'/file/upload'
    }
  },
  data(){
    return {
      accept:'image/jpg,image/jpeg,image/png',  //上传图片类型
      checkEpidemicData:{realName:'',age:'',gender:'',mobile:'',idCard1:'',idCard2:'',areas:'',superAdmin:0,username:'',password:'',roleId:''}, //员工数据
      manageAreaList:[],
		  ruleAddEpidemicValidate: {
			  realName: [
          { required: true, message: '请输入员工姓名', trigger: 'blur' }
        ],
        age: [
          { required: true, message: '请输入员工年龄', trigger: 'blur'}
        ],
        gender:[
          { required: true, message: '请选择员工性别', trigger: 'change'}
        ],
        username:[
          { required: true, message: '请输入账号', trigger: 'blur'}
        ],
        password:[
          { required: true, message: '请输入密码', trigger: 'blur'}
        ],
        // epidemicIdCard:[
        //   { required: true, message: '请输入员工身份证号', trigger: 'blur' }
        // ],
        // epidemicJG:[
        //   { required: true, message: '请输入员工籍贯信息', trigger: 'blur' }  
        // ],
        mobile:[
          { required: true, message: '请输入员工联系电话', trigger: 'blur' }
        ],
        idCard1:[
          { required: true, message: '请上传身份证正面扫描件', trigger: 'blur' }  
        ],
        idCard2:[
          { required: true, message: '请上传身份证反面扫描件', trigger: 'blur' }  
        ],
        roleId:[
          { required: true, message: '请选择岗位', trigger: 'change'}
        ]
        // epidemicQualificationUrl:[
        //   { required: true, message: '请上员工资格扫描件', trigger: 'blur' }  
        // ],
      },
      areaList:[],
      addAreaVal:'',  //新增的区域值
      token: localStorage.token || '',
      postList:[]
    }
  },

  methods:{
    // 提交新增
    handleSubmit (name) {
      this.$refs[name].validate(async(valid) => {
        if (valid) {
          let chooseArea = []
          chooseArea = this.manageAreaList.map((item) => {
            return item.areaId
          })
          this.checkEpidemicData.areas = chooseArea.join(',')
          await addSysUser(this.checkEpidemicData).then((res)=>{
            if(res.code == 0){
              this.$Message.success(res.msg ||'新增信息成功');
              this.$router.push({
                path: '/epidemicPersonList',
              });
            }else{
              this.$Message.error({
                duration:3,
                content:res.msg || '新增失败！'
              });
            }
          })
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

    //上传身份证扫描件正面
    uploadCard(res,file){
      if(res.code == 0){
        this.checkEpidemicData.idCard1 = res.msg ? api.apiNomal + res.msg : ''
      }else{
        this.$Message.error(res.message)
      }
    },

    //上传身份证扫描件反面
    uploadCardBack(res){
      if(res.code == 0){
        this.checkEpidemicData.idCard2 = res.msg ? api.apiNomal + res.msg : ''
      }else{
        this.$Message.error(res.message)
      }
    },

    handleFormatError (file) {
      this.$Notice.warning({
        title: '文件格式不正确',
        desc: '文件 ' + file.name + ' 格式不正确，请上传 jpg 或 png 格式的图片。'
      });
    },
    handleMaxSize (file) {
      this.$Notice.warning({
          title: '超出文件大小限制',
          desc: '文件 ' + file.name + ' 太大，不能超过 2M。'
      });
    },

    // 员工资格证书扫描件
    // uploadQualification(res){
    //   if(res.code == '101'){
    //     this.addEpidemicValidate.bussLicenseUrl = res.data ? res.data : ''
    //     this.$Message.success(res.message)
    //   }else{
    //     this.addEpidemicValidate.bussLicenseUrl = ''
    //     this.$Message.error(res.message)
    //   }
	  // },
  
    //获取未分配管理员的片区列表
    async getAreaList() {
      await selectWorkUserAreas({admin:1}).then(res=>{
        if(res.code === 0){
          this.areaList = res.data;
        }else{
          this.$Message.error(res.msg || '获取失败')
        }
      }).catch(err=>{
        this.$Message.error(res.msg || '获取失败')
      })
    },
  
	  // 增加一个管辖区域
    addOneArea(){
      if(this.addAreaVal){
        this.manageAreaList.push({areaName:this.addAreaVal.split('|')[1],areaId:this.addAreaVal.split('|')[0]})
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
          _this.manageAreaList.splice(index,1)
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
  created(){
    this.postList = this.$route.query.postList
    this.getAreaList()
  }
}
</script>
<style lang="less" scoped>
.addNewEpidemic{
	width: 95%; margin: 1.5em auto;
}
</style>

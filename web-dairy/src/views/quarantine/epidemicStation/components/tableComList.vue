/**
 * 模板名称:嵌套表格公共组件
 * 作者：zhangb
 * 时间：2020-01-08
 * 依赖：
 * 注意：该组件将用于防疫站对员工列表的管理
 * props：
 */
<template>
  <div class="">
    <!-- 表格列表内容 -->
    <el-table :data="tableEpidemicData" style="width: 100%" @selection-change="legalSel">
      <el-table-column label="姓名" prop="realName"></el-table-column>
      <el-table-column label="岗位名称" prop="roleName"></el-table-column>
      <el-table-column label="年龄" prop="age"></el-table-column>
      <el-table-column label="性别">
        <template slot-scope="scope">
          <span>{{scope.row.gender | genderValue}}</span>
        </template>
      </el-table-column>
      <el-table-column label="联系电话" prop="mobile" width="120px"></el-table-column>
      <el-table-column label="管辖区域" width="290px">
        <template slot-scope="scope">
          <Tag checkable color="red" v-for="(list,index) in scope.row.areaEntityList" :key="index">{{list.areaName}}</Tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="320px">
      <template slot-scope="scope">
        <Button type="default" size="small" @click="checkModal(scope.row)">查看</Button>
        <Button type="primary" size="small" @click="editEpidemicData(scope.row)">编辑</Button>
        <Button type="warning" size="small" @click="adjustArea(scope.row)">区域调整</Button>
        <Button type="info" size="small" @click="editPost(scope.row)">修改岗位</Button>
        <Button type="error" size="small" @click="delEpidemic(scope.row)">删除</Button>
      </template>
    </el-table-column>
    </el-table>

    <!-- 查看员工详情信息 -->
    <Modal v-model="checkEpidemicModal" title="员工信息" width="60%">
      <Tag color="primary" style="margin-bottom:10px;">基本信息</Tag>
      <Card style="width:100%">
        <Row>
          <Col offset="1" span="10">人员姓名：{{checkEpidemicData.realName}}</Col>
          <Col span="13">年龄：{{checkEpidemicData.age}}</Col>
        </Row>
        <Row style="margin-top:10px;">
          <Col offset="1" span="10">性别：{{checkEpidemicData.gender | genderValue}}</Col>
          <Col span="12">联系方式：{{checkEpidemicData.mobile}}</Col>
        </Row>
        <Row style="margin-top:10px;">
          <Col offset="1" span="10">账号：{{checkEpidemicData.username}}</Col>
          <Col span="12">密码：{{checkEpidemicData.password || '******'}}</Col>
        </Row>
        <Row style="margin-top:10px;">
          <Col offset="1" span="10">身份证扫描件（正面）：</Col>
          <Col span="12"><div style="width:300px; height:150px;"><img :src="checkEpidemicData.idCard1" width="100%" height="100%" alt=""></div></Col>
        </Row>
        <Row style="margin-top:10px;">
          <Col offset="1" span="10">身份证扫描件（反面）：</Col>
          <Col span="12"><div style="width:300px; height:150px;"><img :src="checkEpidemicData.idCard2" width="100%" height="100%" alt=""></div></Col>
        </Row>
        <!-- <Row style="margin-top:20px;">
          <Col offset="1" span="10">员工资格证书：</Col>
          <Col span="12"><div style="width:300px; height:150px;"><img :src="checkEpidemicData.epidemicQualificationUrl" width="100%" height="100%" alt=""></div></Col>
        </Row> -->
        <Row style="margin-top:20px;">
          <Col offset="1" span="23">管辖区域：
            <Tag checkable color="red" v-for="item in checkEpidemicData.areaEntityList" :key="item.areaId">{{item.areaName}}</Tag>
          </Col>
        </Row>
      </Card>
      <div slot="footer" style="text-align:center;">
        <Button @click="checkEpidemicModal=false" type="primary">确定</Button>
      </div>
    </Modal>

    <!--编辑员工信息 -->
    <Modal v-model="editEpidemicModal" title="编辑员工信息" width="60%">
      <Form ref="checkEpidemicData" :model="checkEpidemicData" :rules="ruleEditPastureValidate" :label-width="170">
       <FormItem label="员工姓名" prop="realName">
          <Input v-model="checkEpidemicData.realName" placeholder="请输入员工姓名" style="width:50%;"></Input>
        </FormItem>
        <FormItem label="员工年龄" prop="age">
          <Input v-model="checkEpidemicData.age"  placeholder="请输入员工年龄" style="width:50%;"></Input>
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
          <Upload :accept="accept" :action="uploadUrl" :on-success="uploadQualification" :show-upload-list="false" :max-size="2048"
            :headers="{'token':token}"
            :on-format-error="handleFormatError"
            :on-exceeded-size="handleMaxSize">
            <div v-if="!checkEpidemicData.epidemicQualificationUrl" style="width:400px; height:240px; border:1px dotted #ccc;text-align:center; line-height:240px; font-size:25px; color:#ccc;cursor:pointer; border-radius:5px;">+</div>
            <div v-if="checkEpidemicData.epidemicQualificationUrl" style="width:400px; height:240px;border:1px dotted #eee;"><img :src="checkEpidemicData.epidemicQualificationUrl" alt="" width="100%" height="100%"></div>
          </Upload>
        </Form-item> -->
      </Form>
      <div slot="footer" style="text-align:center;">
        <Button type="primary" @click="handleSubmit">提交</Button>
        <Button @click="editEpidemicModal=false" style="margin-left: 8px">取消</Button>
      </div>
    </Modal>

    <!-- 编辑员工管辖区域 -->
    <Modal v-model="managerAreaModel"  title="管辖区域调整">
      <p><Icon type="md-person" size="17" /> 员工：{{setArea.realName}}</p>
      <Row style="margin-top:1.5em;padding-left:0.5em;" v-if="areaList.length>0">
        <Col span="18">选择区域：
          <Select v-model="addAreaVal" clearable style="width:60%;" placeholder="请选择新增的区域">
            <Option v-for="item in areaList" :value="item.areaId+'|'+item.areaName" :key="item.areaId">{{ item.areaName }}</Option>
          </Select>
        </Col>
        <Col span="6" >
          <Button type="primary" @click="addOneArea">确定新增</Button>
        </Col>
      </Row>
      <Row v-else style="margin-top:1.5em;padding-left:0.5em;">
        暂无空闲的管理区域可分配，您可在“区域管理”里对应更换管理人员!
      </Row>
      <div style="border-top:1px solid #eee; width:97%; margin:1.5em auto;"></div>
      <p style="padding-left:0.5em;padding-bottom:0.3em;">已管辖区域：</p>
      <Tag closable type="dot" color="success" v-for="(item,index) in manageAreaList" :key="index" style="margin-left:0.5em;" @on-close="delOneArea(item,index)">{{item.areaName}}</Tag>
      <div slot="footer" style="text-align:center;">
        <Button type="primary" @click="addAreaFunc">确定</Button>
        <Button @click="cancelControl" style="margin-left: 8px">取消</Button>
      </div>
    </Modal>
    
    <!-- 修改岗位 -->
    <Modal v-model="editPostModel"  title="修改岗位">
      <p style="margin-bottom:10px;"><Icon type="md-person" size="17" /> 员工：{{userEntity.realName}}</p>
      <RadioGroup v-model="userEntity.roleId" vertical>
        <Radio :label="item.roleId" v-for="item in postList" :key="item.roleId">
          <span>{{item.roleName}}</span>
        </Radio>
      </RadioGroup>
      <div slot="footer" style="text-align:center;">
        <Button type="primary" @click="editPostFunc">确定修改</Button>
        <Button @click="editPostModel=false" style="margin-left: 8px">取消</Button>
      </div>
    </Modal>
  </div>
</template>
<script>
import api from 'service/apiConfig' //真实接口配置
import {deleteSysUser,updateUserRole,getSysUserById,selectWorkAreas,updateUserArea,FileUpload,updateSysUser,selectWorkUserAreas} from "service/api";
export default {
  name: '',
  components:{
  },
  props:{
    tableEpidemicData:{ //员工列表数据
      type:Array,
      default:()=>{  //默认返回工厂function
        return []
      }
    }, 
    parentPage:{
      type:Number,
      default:1
    },
    postList: {
      type: Array,
      required: true
    }
  },
  computed:{
    //上传图片的url地址
    uploadUrl(){
      return api.apiNomal +'/file/upload'
    }
  },
  data(){
    const phoneTest = (rule, value, callback) => {
      const reg = /^1[3|4|5|7|8][0-9]\d{8}$/
        if (!reg.test(value)) {
          callback(new Error('联系电话格式不正确'));
        } else {
          callback();
        }
      };
    return {
      accept:'image/jpg,image/jpeg,image/png',  //上传图片类型
      checkEpidemicModal:false, //查看员工信息model
      checkEpidemicData:{realName:'',age:'',gender:'',mobile:'',idCard1:'',idCard2:'',password:'',username:''}, //查看员工数据
      editEpidemicModal:false, //编辑员工信息
      ruleEditPastureValidate: {
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
          { required: true, message: '请输入员工联系电话', trigger: 'blur' },
          {validator:phoneTest,trigger:'blur'}
        ],
        idCard1:[
          { required: true, message: '请上传身份证正面扫描件', trigger: 'blur' }  
        ],
        idCard2:[
          { required: true, message: '请上传身份证反面扫描件', trigger: 'blur' }  
        ],
        // epidemicQualificationUrl:[
        //   { required: true, message: '请上员工资格扫描件', trigger: 'blur' }  
        // ],
      },
      managerAreaModel:false, //编辑员工管辖区域
      areaList:[],
      addAreaVal:'',  //新增的区域值
      manageAreaList:[] , //新增的区域数组
      editAreaData:{}, //编辑区域的行数据
      userEntity:{id:'',roleId:'',realName:''},
      editPostModel:false,
      setArea:{
				id:'',
        areas:'',
        realName:''
      },   //分配管理员
      token: localStorage.token || '',
    }
  },

  methods:{
    // 查看员工详情
    checkModal(row){
      this.getDetail(row.id)
      this.checkEpidemicModal = true
    },

    //获取员工详情
    async getDetail(id){
      await getSysUserById({userId:id}).then(res=>{
        if(res.code === 0){
          this.checkEpidemicData = res.data;
          this.checkEpidemicData.gender = String(this.checkEpidemicData.gender)
          this.checkEpidemicData.age = this.checkEpidemicData.age?String(this.checkEpidemicData.age):''
          this.checkEpidemicData.password = '******' ;
        }else{
          this.$Message.error(res.msg || '获取失败')
        }
      }).catch(err=>{
        this.$Message.error(res.msg || '获取失败')
      })
    },

    // 编辑员工
    editEpidemicData(row){
      this.getDetail(row.id)
      this.editEpidemicModal = true
    },

    // 提交编辑
    handleSubmit () {
      this.$refs['checkEpidemicData'].validate(async(valid) => {
        if (valid) {
          await updateSysUser(this.checkEpidemicData).then((res)=>{
            if(res.code == 0){
              this.$Message.success(res.msg ||'编辑信息成功');
              this.editEpidemicModal = false
              this.$parent.getList(); //重新获取列表
              this.getAreaList()
              this.$forceUpdate()
            }else{
              this.$Message.error({
                duration:3,
                content:res.msg || '编辑失败！'
              });
            }
          })
        } else {
            this.$Message.error('请检查各项内容是否填写完整！');
        }
      })
    },

    // 删除员工信息
    delEpidemic(data){
      let _this = this;
      _this.userEntity.id = data.id;
      _this.userEntity.roleId = data.roleId;
      this.$Modal.confirm({
        title: '温馨提示',
        content: '<p>您确定删除该员工吗？</p>',
        onOk:async() => {
          await deleteSysUser(_this.userEntity).then((res)=>{
            if(res.code == 0){
              this.$Message.success(res.msg || '删除成功')
              this.$parent.getList(); //重新获取列表
              this.getAreaList()
              this.$forceUpdate()
            }else{
              this.$Message.error(res.msg || '删除失败')
            }
          })
        },
        onCancel: () => {
          this.$Message.info('您已取消操作！');
        }
      });
    },

    //修改岗位
    editPost(data){
      this.userEntity.id = data.id;
      this.userEntity.roleId = data.roleId;
      this.userEntity.realName = data.realName;
      this.editPostModel = true;
    },

    //修改岗位 -- 确定
    async editPostFunc(){
      await updateUserRole(this.userEntity).then(res=>{
        if(res.code === 0){
          this.$Message.success(res.msg || '修改成功')
          this.editPostModel=false;
          this.$parent.getList(); //重新获取列表
          this.getAreaList()
          this.$forceUpdate()
        }else{
          this.$Message.error(res.msg || '修改失败')
        }
      }).catch(err=>{
        this.$Message.error(res.msg || '修改失败')
      })
    },

    // 法人牧场选择项
    legalSel(selItem){
      console.log('当前法人选择的项',selItem)
    },
    // 单体牧场选择项
    singleSel(selItem){
      console.log('当前单体选择的项',selItem)
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
    
    // 上传员工资格证书
    // uploadQualification(res){
    //   if(res.code == 101){
    //     this.checkEpidemicData.epidemicQualificationUrl = res.data ? res.data : ''
    //     this.$Message.success(res.message)
    //   }else{
    //     this.$Message.error(res.message)
    //   }
    // },

    // 调整员工的管辖区域
    adjustArea(row){
      this.setArea.realName = row.realName
      this.setArea.id = row.id
      this.manageAreaList = row.areaEntityList || []
      this.managerAreaModel = true
    },

    //确定修改区域
    async addAreaFunc(){
      let chooseArea = []
      chooseArea = this.manageAreaList.map((item) => {
        return item.areaId
      })
      this.setArea.areas = chooseArea.join(',')
      await updateUserArea(this.setArea).then(res=>{
        if(res.code === 0){
          this.managerAreaModel = false
          this.$Message.success(res.msg || '区域调整成功')
          this.$parent.getList(); //重新获取列表
          this.getAreaList()
          this.$forceUpdate()
        }else{
          this.$Message.error(res.msg || '获取失败')
        }
      }).catch(err=>{
        this.$Message.error(res.msg || '获取失败')
      })
    },

    //取消操作
    cancelControl(){
      this.managerAreaModel=false
      this.$parent.getList(); //重新获取列表
      this.getAreaList()
      this.$forceUpdate()
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

    //获取片区列表
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

  },

  mounted(){
      
  },
  created(){
    this.getAreaList()
  }
}
</script>
<style lang="less" scoped>

</style>

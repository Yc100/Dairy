// author：gy
// time:2020-10-14
// desc:本页面为发布新任务页面

<template>
	<div class="addNewTask">
  <div class="form">
   <Form ref="formItem" :model="formItem" :rules="ruleValidate" :label-width="120">
        <Divider orientation="left">任务详情</Divider>
        <FormItem label="标题:" prop="taskName">
            <Input v-model="formItem.taskName" placeholder="请输入任务标题" style="width:300px"></Input>
        </FormItem>
        <FormItem label="主要内容:" prop="content">
            <Input v-model="formItem.content" type="textarea" placeholder="请输入主要内容描述" style="width:300px"></Input>
        </FormItem>
        
        <FormItem label="任务起止时间">
            <Row>
                <Col span="6">
                <FormItem prop="beginDate">
                    <DatePicker type="date" v-model="formItem.beginDate" placeholder="选择开始时间" style="width:300px" @on-change="(data) =>{ changeBeginDate(data)}"></DatePicker>
                </FormItem>
                </Col>
                <Col span="2" style="text-align: center">-</Col>
                <Col span="6">
                <FormItem prop="endDate">
                    <DatePicker type="date" v-model="formItem.endDate" placeholder="选择截止时间" style="width:300px" @on-change="(data) =>{ changeEndDate(data)}"></DatePicker>
                </FormItem>
                </Col>
            </Row>
        </FormItem>
        <FormItem label="紧急程度：" prop="level">
            <Select v-model="formItem.level" style="width:300px" placeholder="请选择紧急程度">
                <Option v-for='(item,index) in options'
                  :key='index'
                  :value='item.value'>{{item.label}}</Option>
            </Select>
        </FormItem>
        <FormItem label="上传附件：">
          <div class="demo-upload-list" v-for="(item,index) in uploadList" :key="index">
            <template v-if="item">
                <img :src="item">
                <div class="demo-upload-list-cover">
                    <Icon type="ios-eye-outline" @click.native="handleView(item)"></Icon>
                    <Icon type="ios-trash-outline" @click.native="handleRemove(item)"></Icon>
                </div>
            </template>
            <template v-else>
                <Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
            </template>
          </div>
          <Upload
              ref="upload"
              :show-upload-list="false"
              :on-success="handleSuccess"
              :action="uploadUrl"
              :format="['jpg','jpeg','png']"
              :headers="{'token':token}"
              :max-size="2048"
              :on-format-error="handleFormatError"
              :on-exceeded-size="handleMaxSize"
              multiple
              type="drag"
              style="display: inline-block;width:58px;">
              <div style="width: 58px;height:58px;line-height: 58px;">
                  <Icon type="ios-camera" size="20"></Icon>
              </div>
          </Upload>
          <Modal title="图片预览" v-model="visible">
              <img :src="curImg" v-if="visible" style="width: 100%">
          </Modal>
        </FormItem>
        <FormItem label="备注:">
            <Input style="width:300px" v-model="formItem.remark" type="textarea" placeholder="请输入备注"></Input>
        </FormItem>
        <Divider orientation="left">接收人员</Divider>
        <FormItem label="选择区域：" prop="areaIds">
          <div style="width:90%;border-bottom: 1px dashed #e9e9e9;padding-bottom:6px;margin-bottom:6px;">
           <Checkbox
               :indeterminate="indeterminate"
               :value="checkAll"
               @click.prevent.native="handleCheckAll">全选</Checkbox>
          </div>
          <CheckboxGroup v-model="formItem.areaIds" @on-change="checkAllGroupChange">
              <Checkbox v-for="item in areaList" :key="item.id" :label="item.id">{{item.nickName}}</Checkbox>
          </CheckboxGroup>
        </FormItem>
        <FormItem label="选择人员：" prop="userIds">
          <div style="width:90%;border-bottom: 1px dashed #e9e9e9;padding-bottom:6px;margin-bottom:6px;">
           <Checkbox
               :indeterminate="indeterminate2"
               :value="checkAll2"
               @click.prevent.native="handleCheckAll2">全选</Checkbox>
          </div>
          <CheckboxGroup v-model="formItem.userIds" @on-change="checkAllGroupChange2">
              <Checkbox v-for="item in userList" :key="item.id" :label="item.id">{{item.nickName}}</Checkbox>
          </CheckboxGroup>
        </FormItem>
        <div style="height:50px;text-align:center">
             <Button type="primary" @click="handleSubmit('formItem')">提交</Button>
            <Button @click="handleReset('formItem')" style="margin-left: 20px">重置</Button>
        </div>
    </Form>
  </div>
	</div>
</template>

<script>
import api from 'service/apiConfig' //真实接口配置
import {getAreaUsersOptions,getUsersOptions,addWorkTask} from 'service/api'
export default {
	name: 'addNewTask',
	components: {

	},
	props:{
		
	},
	data() {
		return {
   options: [
     {
       label: '普通',
       value: '1'
     },
     {
       label: '重要不紧急',
       value: '2'
     },
     {
       label: '紧急不重要',
       value: '3'
     },
     {
       label: '紧急且重要',
       value: '4'
     }
   ],
   visible: false,
   curImg:'',
   uploadList: [],
   formItem: {
    taskName: '',
    content:'',
    remark:'',
    workType:1,
    level:'',
    beginDate:'',
    endDate:'',
    userIds: [],
    areaIds: [],

   },
   ruleValidate: {
     taskName: [
         { required: true, message: '任务标题不能为空', trigger: 'blur' }
     ],
     content: [
         { required: true, message: '任务标题不能为空', trigger: 'blur' }
     ],
     level: [
        { required: true, message: '请选择紧急程度', trigger: 'change' }
     ],
     beginDate: [
         { required: true, type: 'string', message: '请选择任务开始时间', trigger: 'change' }
     ],
     endDate: [
         { required: true, type: 'string', message: '请选择任务截止时间', trigger: 'change' }
     ],
     userIds: [
         { required: true, type: 'array', min: 1, message: '至少选择一个接收人', trigger: 'change' },
     ],
     areaIds: [
         { required: true, type: 'array', min: 1, message: '至少选择一个区域', trigger: 'change' },
     ],
   },
   token: localStorage.token || '',
   indeterminate: false,   //选择区域
   checkAll: false,
   indeterminate2: false,   //选择人员
   checkAll2: false,
   areaList:[],
   userList:[]
		}
	},
	computed: {
        //上传图片的url地址
        uploadUrl(){
            return api.apiNomal +'/file/upload'
        }
	},
	methods: {
        logLevel(val){
            console.log(val);
            console.log(this.formItem.level);
        },
        changeBeginDate(data){
            this.formItem.beginDate = data
            console.log('任务起始时间',data);
        },
        changeEndDate(data){
            console.log('任务截止时间',data);
            this.formItem.endDate = data
        },
        handleView (item) {
            console.log(item);
            this.visible = true
            this.curImg = item
        },
        handleRemove (file) {
            const fileList = this.$refs.upload.fileList;
            this.$refs.upload.fileList.splice(fileList.indexOf(file), 1);
        },
        handleSuccess(res,file){
            console.log(res);
            if(res.code == 0){
                this.uploadList.push(api.apiNomal + res.msg)
                console.log(this.uploadList);
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
            //提交任务
        handleSubmit (name) {
            this.$refs[name].validate(async(valid) => {
                if (valid) {
                this.formItem.areaIds = this.formItem.areaIds.join(',')
                this.formItem.userIds = this.formItem.userIds.join(',')
                this.formItem.fileUrls = this.uploadList.join(',')
                console.log(this.formItem);
                await addWorkTask(this.formItem).then((res)=>{
                    if(res.code == 0){
                    this.$Message.success(res.msg ||'新增信息成功');
                    this.$router.push({
                        path: '/taskManager',
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
            }).catch(err=>{
                console.log(err);
            })
        },
        //重置任务
        handleReset (name) {
            this.$refs[name].resetFields();
        },
        handleCheckAll () {
            if (this.indeterminate) {
                this.checkAll = false;
            } else {
                this.checkAll = !this.checkAll;
            }
            this.indeterminate = false;

            if (this.checkAll) {
                this.areaList.forEach(item=>{
                this.formItem.areaIds.push(item.id)
                })
            } else {
                this.formItem.areaIds = [];
            }
        },
        checkAllGroupChange (data) {
            if (data.length === this.areaList.length) {
                this.indeterminate = false;
                this.checkAll = true;
            } else if (data.length > 0) {
                this.indeterminate = true;
                this.checkAll = false;
            } else {
                this.indeterminate = false;
                this.checkAll = false;
            }
        },
        handleCheckAll2 () {
            if (this.indeterminate2) {
                this.checkAll2 = false;
            } else {
                this.checkAll2 = !this.checkAll2;
            }
            this.indeterminate2 = false;

            if (this.checkAll2) {
                this.userList.forEach(item=>{
                this.formItem.userIds.push(item.id)
                })
            } else {
                this.formItem.userIds = [];
            }
        },
        checkAllGroupChange2 (data) {
            if (data.length === this.userList.length) {
                this.indeterminate2 = false;
                this.checkAll2 = true;
            } else if (data.length > 0) {
                this.indeterminate2 = true;
                this.checkAll2 = false;
            } else {
                this.indeterminate2 = false;
                this.checkAll2 = false;
            }
        },
        async getOptions(){
            await getAreaUsersOptions().then(res=>{
            if(res.code == 0){
            this.areaList = res.data
            }
            })
            await getUsersOptions().then(res=>{
            if(res.code == 0){
            this.userList = res.data
            }
            })
        }
	},

	mounted() {
        this.getOptions()
	},

	watch: {

	}
}
</script>

<style lang="less" scoped>
.addNewTask{
 .form{
  width: 90%;
  margin: auto;
  padding-top: 20px;
 }
 .demo-upload-list{
     display: inline-block;
     width: 60px;
     height: 60px;
     text-align: center;
     line-height: 60px;
     border: 1px solid transparent;
     border-radius: 4px;
     overflow: hidden;
     background: #fff;
     position: relative;
     box-shadow: 0 1px 1px rgba(0,0,0,.2);
     margin-right: 4px;
 }
 .demo-upload-list img{
     width: 100%;
     height: 100%;
 }
 .demo-upload-list-cover{
     display: none;
     position: absolute;
     top: 0;
     bottom: 0;
     left: 0;
     right: 0;
     background: rgba(0,0,0,.6);
 }
 .demo-upload-list:hover .demo-upload-list-cover{
     display: block;
 }
 .demo-upload-list-cover i{
     color: #fff;
     font-size: 20px;
     cursor: pointer;
     margin: 0 2px;
 }
}
</style>

/**
 * Created by: chenzhe
 * On: 2017.7.22
 *
 * Description:
 * 开店信息填写
 */

<template lang="html">
    <div class="putInfo">
      <Form ref="putInfoForm" :model="info" :label-width="120" :rules="infoValidator">
        <h1>基本信息</h1>
        <Form-item label="公司名称" prop="company">
          <Input v-model="info.company" placeholder="请输入公司名称" style="width: 480px"></Input>
        </Form-item>
        <Form-item label="公司地址" prop="companyAddress">
          <Input v-model="info.companyAddress" placeholder="请输入公司地址" style="width: 480px"></Input>
        </Form-item>
        <Form-item label="店招图" prop="logoUrl">
	        <Upload
	            ref="uploadLogo"
	            type="drag"
	            :action="uploadUrl"
                :headers="{'X-AIYANGNIU-SIGNATURE':token}"
                :show-upload-list="false"
                :on-success="handleSuccess"
                :format="['jpg','jpeg','png']"
                :max-size="2048"
                :on-format-error="handleFormatError"
                :on-exceeded-size="handleMaxSize"
	            style="display: inline-block;width:632px;">
	            <div v-if="uploadLogoList.length == 0" style="width: 632px;height:60px;line-height: 30px;">
		            <Icon type="ios-cloud-upload" size="20" style="color: #3399ff;top: 10px;
                    position: relative;"></Icon>
		            <p>点击或将文件拖拽到这里上传</p>
			    </div>
                <div v-else style="width: 632px;height:60px;line-height: 30px;" v-for="item in uploadLogoList">
                    <template v-if="item.status === 'finished'">
                        <img :src="item.url" style="width: 100%;height: 100%;">
                    </template>
                    <template v-else>
  <Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
</template>
                </div>
			</Upload>
	        <div>图片格式为jpg,jpeg,png,图片尺寸1265*120</div>
        </Form-item>
        <Form-item label="公司简介" prop="aboutUrl">
          <Ueditor @ready="editorReady" style="display:inline-block;width: 800px;"></Ueditor>
        </Form-item>
        <Form-item label="经营范围" prop="businessScope">
          <Input v-model="info.businessScope" type="textarea" :rows="4" placeholder="请输入经营范围" style="width: 480px"></Input>
        </Form-item>
        <Form-item label="营业执照" prop="licenseUrl">
            <Upload
                ref="uploadLicense"
                type="drag"
                :action="uploadUrl"
                :headers="{'X-AIYANGNIU-SIGNATURE':token}"
                :show-upload-list="false"
                :on-success="handleSuccess"
                :format="['jpg','jpeg','png']"
                :max-size="2048"
                :on-format-error="handleFormatError"
                :on-exceeded-size="handleMaxSize"
                style="display: inline-block;width:256px;">
                <div v-if="uploadLicenseList.length == 0" style="width: 256px;height:100px;">
                    <Icon type="ios-cloud-upload" size="20" style="color: #3399ff;top: 10px;
                    position: relative;"></Icon>
                    <p>点击或将文件拖拽到这里上传</p>
                </div>
                <div v-else style="width: 256px;height:100px;" v-for="item in uploadLicenseList">
                    <template v-if="item.status === 'finished'">
  <img :src="item.url" style="width: 100%;height: 100%;">
</template>
                    <template v-else>
  <Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
</template>
                </div>
            </Upload>
        </Form-item>
        <Form-item label="营业执照号" prop="licenseNum">
          <Input v-model="info.licenseNum" placeholder="请输入营业执照号" style="width: 480px"></Input>
        </Form-item>
        <Form-item label="税务登记证编号" prop="taxNum">
          <Input v-model="info.taxNum" placeholder="请输入税务登记证编号" style="width: 480px"></Input>
        </Form-item>
        <Form-item label="一般纳税人" prop="taxStatus">
            <Radio-group v-model="info.taxStatus" type="button" size="large">
                <Radio label="0">是</Radio>
                <Radio label="1">否</Radio>
            </Radio-group>
        </Form-item>
        <Form-item label="税务登记证" prop="taxUrl">
            <Upload
                ref="uploadTax"
                type="drag"
                :action="uploadUrl"
                :headers="{'X-AIYANGNIU-SIGNATURE':token}"
                :show-upload-list="false"
                :on-success="handleSuccess"
                :format="['jpg','jpeg','png']"
                :max-size="2048"
                :on-format-error="handleFormatError"
                :on-exceeded-size="handleMaxSize"
                style="display: inline-block;width:256px;">
                <div v-if="uploadTaxList.length == 0" style="width: 256px;height:100px;">
                    <Icon type="ios-cloud-upload" size="20" style="color: #3399ff;top: 10px;
                    position: relative;"></Icon>
                    <p>点击或将文件拖拽到这里上传</p>
                </div>
                <div v-else style="width: 256px;height:100px;" v-for="item in uploadTaxList">
                    <template v-if="item.status === 'finished'">
  <img :src="item.url" style="width: 100%;height: 100%;">
</template>
                    <template v-else>
  <Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
</template>
                </div>
            </Upload>
        </Form-item>
        <Form-item label="账期" prop="accountPeriod">
          <Input v-model="info.accountPeriod" number style="width: 200px">
              <span slot="append">天</span>
          </Input>
          <span>订单默认支持的账期周期</span>
        </Form-item>
        <Form-item label="首次付款比例" prop="percentPay">
          <Input v-model="info.percentPay" number style="width: 200px">
              <span slot="append">%</span>
          </Input>
          <span>订单默认支持的首付比例</span>
        </Form-item>

        <h1>银行信息</h1>
        <Form-item label="开户行账户名称" prop="bankAccountName">
          <Input v-model="info.bankAccountName" placeholder="请输入开户行账户名称" style="width: 480px"></Input>
        </Form-item>

        <Form-item label="开户行支行名称" prop="bankName">
          <Input v-model="info.bankName" placeholder="请输入开户行支行名称" style="width: 480px"></Input>
        </Form-item>
        <Form-item label="银行账号" prop="bankNum">
          <Input v-model="info.bankNum" placeholder="请输入银行账号" style="width: 480px"></Input>
        </Form-item>
        <h1>店铺信息</h1>
        <Form-item label="店铺名称" prop="name">
          <Input v-model="info.name" placeholder="请输入店铺名称" style="width: 480px"></Input>
        </Form-item>
        <Form-item label="服务热线" prop="serviceHotline">
          <Input v-model="info.serviceHotline" placeholder="请输入服务热线" style="width: 480px"></Input>
        </Form-item>
        <Form-item label="地区" prop="serviceArea">
          <selectAddress :list="addressData" v-model="info.serviceArea"></selectAddress>
        </Form-item>

        <h1>联系人信息</h1>
        <Form-item label="联系人姓名" prop="contactName">
          <Input v-model="info.contactName" placeholder="请输入店铺联系人姓名" style="width: 480px"></Input>
        </Form-item>
        <Form-item label="联系手机" prop="contactCell">
          <Input v-model="info.contactCell" placeholder="请输入联系手机" style="width: 480px"></Input>
        </Form-item>
        <Form-item label="联系人身份证" prop="contactUrl">
            <Upload
                ref="uploadId"
                type="drag"
                :action="uploadUrl"
                :headers="{'X-AIYANGNIU-SIGNATURE':token}"
                :show-upload-list="false"
                :on-success="handleSuccess"
                :format="['jpg','jpeg','png']"
                :max-size="2048"
                :on-format-error="handleFormatError"
                :on-exceeded-size="handleMaxSize"
                style="display: inline-block;width:256px;">
                <div v-if="uploadIdList.length == 0" style="width: 256px;height:100px;">
                    <Icon type="ios-cloud-upload" size="20" style="color: #3399ff;top: 10px;
                    position: relative;"></Icon>
                    <p>点击或将文件拖拽到这里上传</p>
                </div>
                <div v-else style="width: 256px;height:100px;" v-for="item in uploadIdList">
                    <template v-if="item.status === 'finished'">
  <img :src="item.url" style="width: 100%;height: 100%;">
</template>
                    <template v-else>
  <Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
</template>
                </div>
            </Upload>
        </Form-item>
        <div class="div-btn-wraper">
            <el-button type="error" @click.prevent="addShop('putInfoForm', 1)">保存信息</el-button>
            <el-button type="primary" :disabled="!continueWay" @click.prevent="getChangeStep(2)">下一步</el-button>
        </div>
      </Form>
    </div>
</template>

<script>
import {
  ChinaAddressV3Data,
  Value2nameFilter as value2name, //在vux里面的插件
  Name2valueFilter as name2value //同上
} from 'vux'
import { mapGetters, mapActions } from 'vuex'
import selectAddress from 'components/select/selectAddress.vue'
// import {shop} from 'service/api'
import api from 'service/apiConfig' //真实接口配置
import Ueditor from 'components/editor/Ueditor'

export default {
  name: 'putInfo',
  components: {
    Ueditor,
    selectAddress
  },
  props: {
    shopInfo: Object
  },
  computed: {
    ...mapGetters([
      'userInfo'
    ])
  },
  data () {
    const validateImgs = (list, rule, value, callback) => {
      console.log('list', list)
      if (this[list].length == 0) {
        callback(new Error('请上传一张图片'));
      } else {
        callback();
      }
    };
    //手机号码的验证规则
    const validateContactCell = (rule, value, callback) => {
      if (value.replace(/\s/g, "") !== '') {
        let num = value.replace(/\s/g, "")
        if (!/^1[34578]\d{9}$/.test(num)) {//不为空时，校验手机号
          callback(new Error('请输入正确的手机号码！'));
        } else {
          callback()
        }
      } else {
        callback(new Error('请填写该信息'))
      }
    };

    const validateName = (rule, value, callback) => {
      if (value.replace(/\s/g, "") !== '') {
        let textInfo = value.replace(/\s/g, "")
        if (!/^[\u4E00-\u9FA5]+$/.test(textInfo)) {//不为空时，校验中文
          callback(new Error('请输入中文'));
        } else {
          callback()
        }
      } else {
        callback(new Error('请填写该信息'))
      }
    };
    const validateNum = (rule, value, callback) => {
      if (value.replace(/\s/g, "") !== '') {
        let num = value.replace(/\s/g, "")
        if (!/^\d{1,100}$/.test(num)) {//不为空时，校验数字
          callback(new Error('银行账号请输入数字'));
        } else {
          callback()
        }
      } else {
        callback(new Error('请填写该信息'))
      }
    };
    //验证服务热线
    const validatePhone = (rule, value, callback) => {
      // if(value.replace(/\s/g, "") !== ''){
      //     let num = value.replace(/\s/g, "")
      //      if(!/^\d{3,4}-?\d{7,8}$/.test(num)){//不为空时，校验数字
      //         callback(new Error('请输入正确的热线号码'));
      //     }else{
      //         callback()
      //     }
      // }else{
      //     callback(new Error('请填写该信息'))
      // }
    };

    return {
      status: '',
      continueWay: false,
      uploadUrl: api.apiItem + '/apiManager/images/upload?service=account',
      uploadTaxList: [],
      uploadLicenseList: [],
      uploadLogoList: [],
      uploadIdList: [],
      addressData: ChinaAddressV3Data,
      token: localStorage.token || '',
      info: {
        company: "",
        companyAddress: "",
        logoUrl: "",
        aboutUrl: "",
        businessScope: "",
        licenseNum: "",
        licenseUrl: "",
        taxNum: "",
        taxStatus: "",
        taxUrl: "",
        accountPeriod: 0,
        percentPay: 0,
        bankAccountName: "",
        bankName: "",
        bankNum: "",
        name: "",
        serviceHotline: "",
        serviceArea: [],
        contactName: "",
        contactCell: "",
        contactUrl: "",
      },

      infoValidator: { //第一步内容验证
        company: [{
          required: true,
          message: '公司名称不能为空',
          trigger: 'blur'
        }],
        companyAddress: [{
          required: true,
          message: '公司地址不能为空',
          trigger: 'blur'
        }],
        businessScope: [{
          required: true,
          message: '经营范围不能为空',
          trigger: 'blur'
        }],
        licenseUrl: [{
          required: true,
          validator: validateImgs.bind(this, 'uploadLicenseList'),
          trigger: 'blur'
        }],
        licenseNum: [{
          required: true,
          message: '请填写营业执照号',
          trigger: 'blur'
        }],
        bankAccountName: [
          { required: true, message: '开户行账户名称' },
          { validator: validateName, trigger: 'blur' }
        ],
        bankName: [
          { required: true, message: '开户行支行名称' },
          { validator: validateName, trigger: 'blur' }
        ],
        bankNum: [
          { required: true, message: '请输入银行账号' },
          { validator: validateNum, trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入店铺名称' },
          { validator: validateName, trigger: 'blur' }
        ],
        serviceHotline: [
          { required: true, message: '请输入服务热线' },
          // {validator:validatePhone,trigger:'blur'}
        ],
        serviceArea: [{
          required: true,
          message: '请选择地区'
        }],
        contactName: [
          { required: true, message: '请输入联系人姓名' },
          { validator: validateName, trigger: 'blur' }
        ],
        contactCell: [
          { required: true, message: '请输入联系电话' },
          // {validator:validateContactCell,trigger:'blur'}
        ],
        contactUrl: [{
          required: true,
          validator: validateImgs.bind(this, 'uploadIdList'),
          trigger: 'blur'
        }],
      }

    }

  },
  methods: {
    addShop (name, status) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          let logoUrl = this.uploadLogoList[0] ? this.uploadLogoList[0].url : '',
            licenseUrl = this.uploadLicenseList[0] ? this.uploadLicenseList[0].url : '',
            taxUrl = this.uploadTaxList[0] ? this.uploadTaxList[0].url : '',
            contactUrl = this.uploadIdList[0] ? this.uploadIdList[0].url : '',
            operationFn = this.status == '' ? shop.AddShop : shop.EditShop,
            // status = this.status == 2 ? 2 : 1,
            params = Object.assign(this.info, {
              logoUrl,
              licenseUrl,
              taxUrl,
              contactUrl,
              status
            })

          operationFn(params).then(res => {
            if (res.code == '101') {
              this.$Notice.success({
                title: '保存店铺信息成功，请点击下一步'
              })
              this.continueWay = true
            } else {
              this.$message(res.message || "保存店铺信息出错")
              this.continueWay = false
            }
          })
        } else {
          this.$Message.error('表单验证失败!')
          this.continueWay = false
        }
      })

    },
    editorReady (instance) {
      let text = this.info.aboutUrl || ''
      instance.setContent(text)
      instance.addListener('contentChange', () => {
        this.info.aboutUrl = instance.getContent();
      })
    },
    async getChangeStep (num) {
      await this.addShop('putInfoForm', 2)
      this.$emit('pullNum', num)
    },

    handleSuccess (res, file) {
      // 因为上传过程为实例，这里模拟添加 url
      file.url = res.data || '';
      file.name = file.name || '图片';
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
    }
  },

  mounted () {
    this.uploadLogoList = this.$refs.uploadLogo.fileList;
    this.uploadLicenseList = this.$refs.uploadLicense.fileList;
    this.uploadTaxList = this.$refs.uploadTax.fileList;
    this.uploadIdList = this.$refs.uploadId.fileList;
    this.info = this.shopInfo == {} ? this.info : _.cloneDeep(this.shopInfo)
    this.status = this.userInfo.shop ? this.userInfo.shop.status : ''
  }
}
</script>

<style lang="less">
.putInfo {
  width: 1000px;
  margin: 50px auto;
  h1 {
    border-left: 5px solid #3399ff;
    padding-left: 10px;
    margin-bottom: 20px;
    font-size: 18px;
  }
  .div-btn-wraper {
    text-align: center;
    padding: 15px 10px 0;
  }
}
</style>

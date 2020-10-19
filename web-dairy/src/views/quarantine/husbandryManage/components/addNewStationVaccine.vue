/**
 * 模板名称:新增疫苗发放记录
 * 作者：zhangb
 * 时间：2020-01-14
 * 依赖：
 * 注意：
 * props：
 */
<template>
  <div class="addNewSendVaccine">
    <Button type="primary" size="small" @click="$router.back()" style="float:right;">返回</Button>
    <div style="width:70%;">
    	<Tag color="success">新疫苗发放记录</Tag>
      <br><br>
      <Form ref="addSendVaccineValidate" :model="addSendVaccineValidate" :rules="ruleAddSendVaccineValidate" :label-width="170">
        <FormItem label="领取防疫站" prop="recivePerson">
          <Select v-model="addSendVaccineValidate.recivePerson" clearable style="width:35%;" placeholder="请选择领取防疫站">
            <Option v-for="item in epidemicList" :value="item.label" :key="item.label">{{ item.label }}</Option>
          </Select>
        </FormItem>
        <FormItem label="防疫站所属区域" prop="blongArea">
          <Select v-model="addSendVaccineValidate.blongArea" clearable placeholder="请选择区域" style="width:35%;">
            <Option v-for="item in areaList" :value="item.label" :key="item.label">{{ item.label }}</Option>
          </Select>
        </FormItem>
        <FormItem label="发放疫苗详情" prop="">
          <Tag color="red">现有储存疫苗</Tag>
          <div class="vaccine-list">
            <div style="margin-left:1em; margin-top:1em;">
              <Card style="width:130px; margin:auto auto;">
                <p style="font-size:13px;">疫苗蓄种：马</p>
                <p style="font-size:13px;">疫苗总数：50</p>
                <p style="font-size:13px;">亚单位疫苗：20</p>
                <p style="font-size:13px;">活疫苗：15</p>
                <p style="font-size:13px;">灭活疫苗：15</p>
              </Card>
            </div>
            <div style="margin-left:1em; margin-top:1em;">
              <Card style="width:130px; margin:auto auto;">
                <p style="font-size:13px;">疫苗蓄种：牛</p>
                <p style="font-size:13px;">疫苗总数：50</p>
                <p style="font-size:13px;">亚单位疫苗：20</p>
                <p style="font-size:13px;">活疫苗：15</p>
                <p style="font-size:13px;">灭活疫苗：15</p>
              </Card>
            </div>
            <div style="margin-left:1em; margin-top:1em;">
              <Card style="width:130px; margin:auto auto;">
                <p style="font-size:13px;">疫苗蓄种：羊</p>
                <p style="font-size:13px;">疫苗总数：50</p>
                <p style="font-size:13px;">亚单位疫苗：20</p>
                <p style="font-size:13px;">活疫苗：15</p>
                <p style="font-size:13px;">灭活疫苗：15</p>
              </Card>
            </div>
          </div>
          <br>

          <Tag color="green">预计下发疫苗</Tag>
          <Row style="margin-top:1em;">
            <Col span="5" style="">可选疫苗畜禽种类 </Col>
            <Col offset="1" span="7">
              <Select v-model="curSelAnimalKind" clearable placeholder="请选择畜禽种类" size="small" style="width:80%;" @on-change="changeSelAnimal">
                <Option v-for="item in animalKindList" :value="item.label" :key="item.label">{{ item.label }}</Option>
              </Select>
            </Col>
          </Row>
          
          <div style="border-top:1px solid #eee;margin:1em 0;"></div>

          <Row v-if="curSelAnimalKind">
            <Col span="10">
              <Row >
                <Col span="11">亚单位疫苗</Col>
                <Col span="13"><InputNumber size="small" :max="10" :min="0" v-model="vaccineInfo.yadanwei"></InputNumber></Col>
              </Row>
              <Row>
                <Col span="11">活疫苗</Col>
                <Col span="13"><InputNumber size="small" :max="10" :min="0" v-model="vaccineInfo.huoyimiao"></InputNumber></Col>
              </Row>
              <Row>
                <Col span="11">灭活疫苗</Col>
                <Col span="10"><InputNumber size="small" :max="10" :min="0" v-model="vaccineInfo.miehuo"></InputNumber></Col>
              </Row>
            </Col>
            <Col offset="7" span="5">
              <Button type="success" size="small" @click="newOneVaccineInfo">生成单类疫苗清单</Button>
            </Col>
          </Row>

          <div style="border-top:1px solid #eee;margin:1em 0;" v-show="addVaccineList.length>0 && curSelAnimalKind"></div>
          <Tag color="blue" v-show="addVaccineList.length>0">已生成疫苗清单</Tag>
          <div class="vaccine-list" v-show="addVaccineList.length>0">
            <div style="margin-left:1em; margin-top:1em;" v-for="(list,index) in addVaccineList" :key="index">
              <Card style="width:130px; margin:auto auto;">
                <div slot="title">
                  <Row>
                    <Col span="12">{{list.animalKind}}</Col>
                    <Col span="12" style="text-align:right;">
                      <a href="#" @click.prevent="delOneVanccine(index)">删除</a>
                    </Col>
                  </Row>
                </div>
                <p style="font-size:13px;">疫苗总数：{{list.miehuo+list.yadanwei+list.huoyimiao}}</p>
                <p v-if="list.yadanwei" style="font-size:13px;">亚单位疫苗：{{list.yadanwei}}</p>
                <p v-if="list.huoyimiao" style="font-size:13px;">活疫苗：{{list.huoyimiao}}</p>
                <p v-if="list.miehuo" style="font-size:13px;">灭活疫苗：{{list.miehuo}}</p>
              </Card>
            </div>
          </div>
        </FormItem>
        <FormItem style="text-align:center;">
          <Button type="primary" @click="handleSubmit('addSendVaccineValidate')">提交</Button>
          <Button @click="handleReset('addSendVaccineValidate')" style="margin-left: 20px">重置</Button>
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
  name: 'addNewSendVaccine',
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
    	addSendVaccineValidate: {
      recivePerson: '',     //领取的员工
      blongArea:'',         //员工所属区域

      },
      ruleAddSendVaccineValidate: {
        recivePerson: [{ required: true, message: '请选择员工姓名', trigger: 'change' }],
        blongArea: [{ required: true, message: '请选择员工所属区域', trigger: 'change' }],

      },
      areaList:[
        {label:'赛罕区',value:1},
        {label:'玉泉区',value:2},
        {label:'回民区',value:3},
        {label:'新城区',value:4}
      ],
      epidemicList:[
        {label:'A防疫站'},{label:'B防疫站'},{label:'C防疫站'},
      ],
      animalKindList:[
				{label:'牛'},{label:'马'},{label:'羊'}
      ],
      curSelAnimalKind:'', //当前选中的疫苗畜禽种类
      vaccineInfo:{  //单次添加疫苗信息
        yadanwei:0,
        huoyimiao:0,
        miehuo:0
      },
      addVaccineList:[], //添加的疫苗种类汇总
      
    }
  },

  methods:{
    
    // 改变疫苗畜禽的种类
    changeSelAnimal(val){
      console.log('val',val)
    },

    // 生成一个单项疫苗清单
    newOneVaccineInfo(){
      let newone = {
        animalKind:this.curSelAnimalKind,
        ...this.vaccineInfo
      }
      console.log('new了一个新得',newone)
      this.addVaccineList.push(newone)

      this.curSelAnimalKind = ''
      this.vaccineInfo={
        yadanwei:0,
        huoyimiao:0,
        miehuo:0
      }
    },

    // 删除一个卡片信息
    delOneVanccine(index){
      this.addVaccineList.splice(index,1)
    },

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

  },

  mounted(){

  },
}
</script>
<style lang="less" scoped>
.addNewSendVaccine{
	width: 95%; margin: 1.5em auto;
}
.vaccine-list{
  display: flex;
  flex-wrap:wrap;
}
.vaccine-list p{
  line-height: 170%;
}
</style>

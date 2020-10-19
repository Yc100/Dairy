// author：gy
// time:2020-10-12
// desc:本页面为人员信息导出列表页面

<template>
	<div class="epidemicPersonList">
		<!-- 操作 -->
		<Row>
			<Col span="6">
				<Row>
					<Col span="20">
							<Select v-model="pageParams.areaId" clearable placeholder="请选择区域" @on-change="getList">
										<Option v-for="item in areaList" :value="item.areaId" :key="item.areaId">{{ item.areaName }}</Option>
								</Select>
					</Col>
				</Row>
			</Col>
			<Col offset="15" span="2"><Button type="primary" @click="exprt">导出</Button></Col>
		</Row>
		<br>
  <!-- 表格列表内容 -->
    <el-table :data="EpidemicData.data" style="width: 100%" @selection-change="legalSel">
      <el-table-column label="姓名" prop="nickName" width="200"></el-table-column>
      <el-table-column label="年龄" prop="age" width="100"></el-table-column>
      <el-table-column label="性别" width="90">
        <template slot-scope="scope">
          <span>{{String(scope.row.gender) | genderValue}}</span>
        </template>
      </el-table-column>
      <el-table-column label="联系电话" prop="mobile" width="200"></el-table-column>
      <el-table-column label="管辖区域">
        <template slot-scope="scope">
          <Tag color="red" v-for="(list,index) in scope.row.areaEntityList" :key="index">{{list.areaName}}</Tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="140px">
      <template slot-scope="scope">
        <Button type="primary" size="small" @click="checkModal(scope.row)">查看</Button>
      </template>
    </el-table-column>
    </el-table>
		<div style="text-align:right;margin:15px 0;">
			<Page show-total :total="parseInt(EpidemicData.total) || 0" :page-size="pageParams.limit" :current="pageParams.current" @on-change='changepage'></Page>
		</div>

   <!-- 查看员工详情信息 -->
	<Modal v-model="checkEpidemicModal" title="员工信息" width="60%">
      <Tag color="primary" style="margin-bottom:10px;">基本信息</Tag>
      <Card style="width:100%">
        <Row>
          <Col offset="1" span="10">人员姓名：{{checkEpidemicData.nickName}}</Col>
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
        <Button @click="checkEpidemicModal=false" type="primary">关闭</Button>
      </div>
    </Modal>
	</div>
</template>

<script>
import {mapGetters,mapActions} from 'vuex'
import api from 'service/apiConfig'
import axios from 'axios'
import { listSysUsers,selectWorkAreas ,getSysUserById } from 'service/api'
export default {
	name: 'epidemicPersonList',
	components: {
		
	},
	props:{
		
	},
	data() {
		return {
			pageParams:{
				limit:10,
				offset:0,
				current:1,
				areaId:'',
			},
			areaList:[],
			EpidemicData:{
				data:[],
				total:''
   },
   checkEpidemicData:{}, //查看员工数据
   checkEpidemicModal:false, //查看员工信息model
		}
	},
	computed: {
		...mapGetters([
			'userInfo',        //用户信息
		])
	},
	methods: {
		...mapActions([

		]),
		//获取列表数据
		async getList() {
			await listSysUsers(this.pageParams).then(res=>{
				if(res.code === 0){
					this.EpidemicData.data = res.data.list;
					this.EpidemicData.total = res.data.total
				}else{
					this.$Message.error('获取失败！');
				}
			}).catch(err=>{
				this.$Message.error('获取失败！');
			})
		},
		//分页
		changepage(index){
			this.pageParams.offset = (index-1)*this.pageParams.limit;
			this.getList();
  },
  
  // 查看员工详情
  checkModal(row){
    this.getDetail(row.id)
    this.checkEpidemicModal = true
  },
   // 法人牧场选择项
  legalSel(selItem){
    console.log('当前法人选择的项',selItem)
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
  //导出
  exprt(){
			axios({
					method: 'get',
					url: api.apiNomal+"/api/exportUsers",
					// headers里面设置token
					headers: {
							'Content-Type': 'application/json',
							'token':localStorage.token,
					},
					params: {
							areaId: this.pageParams.areaId,
					},
					dataType:'json',
					// 二进制流文件，一定要设置成blob，默认是json
					// responseType: 'blob'
			}).then(res=>{
					let link = document.createElement('a');
					link.style.display = 'none'; 
					link.href = api.apiNomal + res.data.data; 
					document.body.appendChild(link);
					link.click();
					document.body.removeChild(link);
			})
		},
		
		//获取畜禽区域列表
		async getAreasList(){
			await selectWorkAreas({admin:1}).then(res=>{
				if(res.code === 0){
					this.areaList = res.data;
				}
			})
		},
	},

	mounted() {

	},

	watch: {

	},
	created(){
		this.getList()
		this.getAreasList()
	}
}
</script>

<style lang="less" scoped>
.epidemicPersonList{
	width: 95%;
	margin: auto auto;
	padding-top: 2em;
	// padding: 1em;
	.belowArea{
		font-size: 15px; font-weight: bold;
		.iconyiliaohangyedeICON-{
			color: red;
		}
	}
}
</style>

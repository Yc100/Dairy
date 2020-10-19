// author：gy
// time:2020-10-12
// desc:本页面为养殖户管理列表页面

<template>
	<div class="animalManage">
		<!-- 操作 -->
		<div class="belowArea"><i class="iconfont iconyiliaohangyedeICON-"></i> 所属地区：{{userInfo.areaName}}</div><br>
		<div style="margin-bottom:0.5em;"><i class="iconfont iconshaixuan-"></i> 筛选条件</div>
		<Row>
			<Col span="4"><Input v-model="filtItem.name" icon="ios-search" placeholder="请输入养殖户户主姓名" @on-change="initData" /></Col>
			<Col offset="1" span="3">
				<Select v-model="filtItem.areaId" clearable placeholder="请选择区域" @on-change="initData">
					<Option v-for="item in areaList" :value="item.areaId" :key="item.areaId">{{ item.areaName }}</Option>
				</Select>
			</Col>
			<Col offset="1" span="3">
				<Select v-model="filtItem.kindId" clearable placeholder="请选择畜禽种类" @on-change="initData">
					<Option v-for="item in animalKindList" :value="item.kindId" :key="item.kindId">{{ item.name }}</Option>
				</Select>
			</Col>
			<Col offset="1" span="3">
				<Select v-model="filtItem.count" clearable placeholder="请选择养殖规模" @on-change="initData">
					<Option v-for="item in sizeOptions" :value="item.value" :key="item.value">{{ item.text }}</Option>
				</Select>
			</Col>
			<!-- <Col offset="1" span="2"><Button type="primary" @click="exprt">导出</Button></Col> -->
		</Row>
		<br>
		
		<!-- 表格列表内容 -->
		<el-table :data="farmerListData.list" style="width: 100%">
			<el-table-column label="养殖户" prop="fullName" width="150" align="center"></el-table-column>
			<el-table-column label="户主性别" width="110">
				<template slot-scope="scope">
					<span>{{scope.row.gender===1?'女':(scope.row.gender===0?'男':'保密')}}</span>
				</template>
			</el-table-column>
			<el-table-column label="家庭人口" width="110">
				<template slot-scope="scope">
					<span>{{scope.row.familyPopulation}}</span>
				</template>
			</el-table-column>
			<el-table-column label="居住类型" width="110">
				<template slot-scope="scope">
					<span>{{scope.row.liveType===1?'常住人口':'临时居住'}}</span>
				</template>
			</el-table-column>
			<el-table-column label="联系电话" prop="mobile" width="140"></el-table-column>
			<el-table-column label="家庭住址">
				<template slot-scope="scope">
					{{ scope.row.address || '无' }}
				</template>
			</el-table-column>
			<el-table-column label="操作" width="140">
			<template slot-scope="scope">
				<Button type="primary" size="small" @click="viewThisAnimal(scope.row)">查看</Button>
			</template>
			</el-table-column>
		</el-table>	

		<!-- 分页 -->
		<div style="text-align:right;margin:15px 0;">
			<Page show-total :total="parseInt(farmerListData.total) || 0" :page-size="pageParams.limit" :current="current" @on-change="changepage"></Page>
		</div>

		<!-- 查看畜禽 -->
		<Modal v-model="viewAnimalModal" title="养殖户详情" width="50%">
			<h2 style="line-height:40px;font-size:18px">养殖畜禽信息</h2>
			<ul v-if="farmerAnimalList.length>0" style="display:flex;align-items:center;list-style:none;margin-left:30px;margin-top:10px;margin-bottom:10px">
				<li v-for="(item,index) in farmerAnimalList" :key="item.id">
					<div class="typeDiv" :style="getStyle(index)">
						<div style="font-size: 18px">{{item.kindName}}</div>
						<div style="font-size: 14px;margin-top:3px;">数量:{{item.allCount}}</div>
						<div style="font-size: 14px;">接种率:{{item.vaccPercent}}%</div>
					</div>
				</li>
			</ul>
			<div v-else style="width:50%;text-align:center">
				<el-tag type="info" style="font-size:16px">该养殖户暂无养殖畜禽信息~</el-tag>
			</div>
			<h2 style="line-height:40px;font-size:18px">基本信息</h2>
			<Form :model="farmerFormData" :label-width="170">
				<FormItem label="户主:">
					<span>{{farmerFormData.fullName}}</span>
				</FormItem>
				<FormItem label="户主性别:">
					<span>{{farmerFormData.gender===1?'女':(farmerFormData.gender===0?'男':'保密')}}</span>
				</FormItem>
				<FormItem label="居住类型:">
					<span>{{farmerFormData.liveType===1?'常住人口':'临时居住'}}</span>
				</FormItem>
				<FormItem label="家庭人口:">
					<span>{{farmerFormData.familyPopulation}}</span>
				</FormItem>
				<FormItem label="联系电话:">
					<span>{{farmerFormData.mobile}}</span>
				</FormItem>
				<FormItem label="籍贯:">
					<span>{{farmerFormData.nativePlace}}</span>
				</FormItem>
				<FormItem label="家庭住址:">
					<span>{{farmerFormData.address + farmerFormData.addressInfo || '无'}}</span>
				</FormItem>
				<FormItem label="身份证图片(正面):">
					<img :src="farmerFormData.idCover1" alt="" width="300px" height="150px">
				</FormItem>
				<FormItem label="身份证图片(反面):">
					<img :src="farmerFormData.idCover2" alt="" width="300px" height="150px">
				</FormItem>
			</Form>
			<div slot="footer" style="text-align:center;">
				<Button @click="viewAnimalModal=false" style="margin-left: 8px" type="primary">关闭</Button>
			</div>
		</Modal>
		
	</div>
</template>

<script>
const colors = ['255, 109, 53', '68, 173, 254', '236, 83, 75','0, 175, 128']
import api from 'service/apiConfig' //真实接口配置
import {countByKind,selectWorkAreas,getFarmerList,getFarmerDetail,firstLivestockKinds } from 'service/api'
import {mapGetters,mapActions} from 'vuex'
// import { manager } from 'service/api'
export default {
	name: 'animalManage',
	components: {

	},
	props:{
		
	},
	data() {
		return {
			accept:'image/jpg,image/jpeg,image/png',  //上传图片类型
			filtItem:{
				name:'',
				areaId:'',
				kindId:'',
				count:''
			},
			animalKindList:[],
			areaList:[],
			farmerListData:{},   //畜禽列表数据
			pageParams:{
				limit:10,
				offset:0,
			},
			current:1,
			viewAnimalModal:false,   //查看畜禽moda
			newAnimalModal:false,//新增畜禽modal
			editAnimalModal:false, //编辑畜禽modal
			AnimalValidate:{
				belongKind:'',
				number:'',
				animalPicture:'',
				lastTimeImmune:'',
				medicalHistory:'',
				belongArea:'',
			},
			ruleAnimalValidate:{
				belongKind: [
					{ required: true, message: '请选择畜禽所属种类', trigger: 'change' }
				],
				lastTimeImmune: [
					{ required: true, type: 'date', message: '请选择上次接种时间', trigger: 'change' }
				],
				medicalHistory: [
					{ required: true, message: '请选择是否存在病史', trigger: 'change' }
				],
				belongArea: [
					{ required: true, message: '请选择畜禽归属区域', trigger: 'change' }
				],
			},
			farmerFormData:{},
			sizeOptions: [
					{ text: '全部', value: '' },
					{ text: '1~50', value: 1 },
					{ text: '50~150', value: 2 },
					{ text: '150~200', value: 3 },
					{ text: '200以上', value: 4 },
			],
			farmerAnimalList:{},

		}
	},
	computed: {
		...mapGetters([
			'userInfo',        //用户信息
		]),

		//上传图片的url地址
		uploadUrl(){
			return api.apiItem +'/images/upload?service=account'
		}
	},
	methods: {
		...mapActions([

		]),

		//获取畜禽种类列表
		async getStocksList(){
			await firstLivestockKinds().then(res=>{
				if(res.code === 0){
					this.animalKindList = res.data;
				}
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

		//初始化列表信息
		async initData(){
			let params = Object.assign({},this.filtItem,this.pageParams)
			await getFarmerList(params).then(res=>{
				if(res.code === 0){
					this.farmerListData = res.data;
				}
			})
		},

		//分页
		changepage(index){
			this.pageParams.offset = (index-1)*this.pageParams.limit;
			this.initData();
		},

		//查看畜禽信息
	 	async	viewThisAnimal(row){
			await countByKind({farmerId:row.farmerId,admin:1}).then(res=>{
				if(res.code == 0){
					this.farmerAnimalList = res.data   //获取养殖畜禽信息
				}else{
					this.$Message.error(res.message || '系统错误')
				}
			})

			await getFarmerDetail({farmerId:row.farmerId}).then(res=>{
				if(res.code == 0){
					this.viewAnimalModal = true
					this.farmerFormData = res.data   //获取养殖户基本信息
				}else{
					this.$Message.error(res.message || '系统错误')
				}
			})
			
		},

		getStyle(i) {
			return {
				background: `rgba(${colors[i % 4]}, 0.2)`,
				color: `rgb(${colors[i % 4]})`
			}
		}
	
	},

	mounted() {
		this.getStocksList()		//获取畜禽种类列表
		this.getAreasList()		//获取畜禽区域列表
		this.initData()   //初始化列表信息

	},

	watch: {

	}
}
</script>

<style lang="less" scoped>
.animalManage{
	width: 95%;
	margin: auto auto;
	padding-top: 2em;
	.belowArea{
		font-size: 15px; font-weight: bold;
		.iconyiliaohangyedeICON-{
			color: red;
		}
	}

}
	.typeDiv{
		width: 110px;
		height: 100px;
		margin-right: 15px;
		border-radius: 15px;
		padding: 10px;
	}
</style>

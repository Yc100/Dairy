// author：zhangb
// time:2020-01-10
// desc:本页面为防疫站对畜禽管理支持

<template>
	<div class="animalManage">
		<!-- 操作 -->
		<div class="belowArea"><i class="iconfont iconyiliaohangyedeICON-"></i> 所属地区：{{userInfo.areaName}}</div><br>
		<div style="margin-bottom:0.5em;"><i class="iconfont iconshaixuan-"></i> 筛选条件</div>
		<Row>
			<Col span="4"><Input v-model="filtItem.number" icon="ios-search" placeholder="请输入畜禽编号" @on-change="initData" /></Col>
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
			 	<DatePicker v-model="filtItem.beginDate" type="date" placeholder="接种开始时间" style="width: 100%" @on-change="initData"></DatePicker>
			</Col>
			<Col span="1" style="text-align:center;">_</Col>
			<Col span="3">
			 	<DatePicker v-model="filtItem.endDate" type="date" placeholder="接种结束时间" style="width: 100%" @on-change="initData"></DatePicker>
			</Col>
			<!-- <Col offset="1" span="2"><Button type="primary" @click="addNewAnimal">新增畜禽信息</Button></Col> -->
		</Row>
		<br>
		
		<!-- 表格列表内容 -->
		<el-table :data="animalsListData.list" style="width: 100%">
			<el-table-column label="所属畜种" prop="name" width="100" align="center"></el-table-column>
			<el-table-column label="采集照片" width="110">
				<template slot-scope="scope">
					<img :src="scope.row.imgUrl" width="100%" alt="">
				</template>
			</el-table-column>
			<el-table-column label="编号" prop="livestockNo" width="140"></el-table-column>
			<el-table-column label="上次接种时间" width="180">
				<template slot-scope="scope">
					{{ scope.row.lastVacTime || '无' }}
				</template>
			</el-table-column>
			<el-table-column label="是否投保" width="120">
				<template slot-scope="scope">
					{{ scope.row.insured === 1?'已投保':'未投保' }}
				</template>
			</el-table-column>
			<el-table-column label="归属区域">
				<template slot-scope="scope">
					{{ scope.row.city + scope.row.district + scope.row.addressInfo || '无' }}
				</template>
			</el-table-column>
			<el-table-column label="操作" width="150px">
			<template slot-scope="scope">
				<Button type="primary" size="small" @click="viewThisAnimal(scope.row)">查看</Button>
			</template>
			</el-table-column>
		</el-table>	

		<!-- 分页 -->
		<div style="text-align:right;margin:15px 0;">
			<Page show-total :total="parseInt(animalsListData.total) || 0" :page-size="pageParams.limit" :current="current" @on-change="changepage"></Page>
		</div>

		<!-- 查看畜禽 -->
			<Modal v-model="viewAnimalModal" title="查看畜禽信息" width="50%">
			<Form :model="AnimalFormData" :label-width="170">
				<FormItem label="所属畜种:">
					<span>{{AnimalFormData.name}}</span>
				</FormItem>
				<FormItem label="畜禽编号:">
					<span>{{AnimalFormData.livestockNo}}</span>
				</FormItem>
				<FormItem label="上次接种时间:">
					<span>{{AnimalFormData.livestockNo}}</span>
				</FormItem>
				<FormItem label="有无病史:">
					<span>{{AnimalFormData.sickHistory}}</span>
				</FormItem>
				<FormItem label="归属区域:">
					<span>{{AnimalFormData.city + AnimalFormData.district + AnimalFormData.addressInfo || '无'}}</span>
				</FormItem>
				<FormItem label="采集照片:">
					<img :src="AnimalFormData.imgUrl" alt="" width="300px" height="150px">
				</FormItem>
			</Form>
			<div slot="footer" style="text-align:center;">
				<Button @click="viewAnimalModal=false" style="margin-left: 8px" type="primary">关闭</Button>
			</div>
		</Modal>
		
		<!-- 编辑畜禽 -->
		<Modal v-model="editAnimalModal" title="编辑畜禽信息" width="50%">
			<Form ref="AnimalValidate" :model="AnimalValidate" :rules="ruleAnimalValidate" :label-width="170">
				<FormItem label="所属蓄种" prop="belongKind">
					<Select v-model="AnimalValidate.belongKind" clearable placeholder="请选择所属蓄种" style="width:50%;">
						<Option v-for="item in animalKindList" :value="item.kindId" :key="item.kindId">{{ item.name }}</Option>
					</Select>
				</FormItem>
				<FormItem label="畜禽编号" prop="number">
					<Input v-model="AnimalValidate.number" placeholder="请输入畜禽编号" style="width:50%;"></Input>
				</FormItem>
				<FormItem label="上次接种时间" prop="lastTimeImmune">
					<DatePicker v-model="AnimalValidate.lastTimeImmune" type="datetime" placeholder="请选择上次接种时间" style="width: 50%"></DatePicker>
				</FormItem>
				<FormItem label="有无病史" prop="medicalHistory">
					<RadioGroup v-model="AnimalValidate.medicalHistory">
						<Radio label="存在病史">存在病史</Radio>
						<Radio label="无病史">无病史</Radio>
					</RadioGroup>
				</FormItem>
				<FormItem label="归属区域" prop="belongArea">
					<Select v-model="AnimalValidate.belongArea" clearable style="width:50%;" placeholder="请选择归属区域">
						<Option v-for="item in areaList" :value="item.areaId" :key="item.areaId">{{ item.areaName }}</Option>
					</Select>
				</FormItem>
				<FormItem label="采集照片" prop="animalPicture">
					<Upload :headers="{'X-AIYANGNIU-SIGNATURE':'token'}" :accept="accept" :action="uploadUrl" :on-success="uploadAnimalPic" :show-upload-list="false">
						<div v-if="!AnimalValidate.animalPicture" style="width:300px; height:150px; border:1px dotted #ccc;text-align:center; line-height:150px; font-size:25px; color:#ccc;cursor:pointer; border-radius:5px;">+</div>
						<div v-if="AnimalValidate.animalPicture" style="width:300px; height:150px;border:1px dotted #eee;"><img :src="AnimalValidate.animalPicture" alt="" width="100%" height="100%"></div>
					</Upload>
				</FormItem>
			</Form>
			<div slot="footer" style="text-align:center;">
				<Button @click="editAnimalModal=false" style="margin-left: 8px">取消</Button>
				<Button type="primary" @click="confirmUpdate">提交</Button>
			</div>
		</Modal>

		<!-- 新增畜禽 -->
		<Modal v-model="newAnimalModal" title="新增畜禽信息" width="50%">
			<Form ref="AnimalValidate" :model="AnimalValidate" :rules="ruleAnimalValidate" :label-width="170">
				<FormItem label="所属蓄种" prop="belongKind">
					<Select v-model="AnimalValidate.belongKind" clearable placeholder="请选择所属蓄种" style="width:50%;">
						<Option v-for="item in animalKindList" :value="item.kindId" :key="item.kindId">{{ item.name }}</Option>
					</Select>
				</FormItem>
				<FormItem label="畜禽编号" prop="number">
					<Input v-model="AnimalValidate.number" placeholder="请输入畜禽编号" style="width:50%;"></Input>
				</FormItem>
				<FormItem label="上次接种时间" prop="lastTimeImmune">
					<DatePicker v-model="AnimalValidate.lastTimeImmune" type="datetime" placeholder="请选择上次接种时间" style="width: 50%"></DatePicker>
				</FormItem>
				<FormItem label="有无病史" prop="medicalHistory">
					<RadioGroup v-model="AnimalValidate.medicalHistory">
						<Radio label="存在病史">存在病史</Radio>
						<Radio label="无病史">无病史</Radio>
					</RadioGroup>
				</FormItem>
				<FormItem label="归属区域" prop="belongArea">
					<Select v-model="AnimalValidate.belongArea" clearable style="width:50%;" placeholder="请选择归属区域">
						<Option v-for="item in areaList" :value="item.areaId" :key="item.areaId">{{ item.areaName }}</Option>
					</Select>
				</FormItem>
				<FormItem label="采集照片" prop="animalPicture">
					<Upload :headers="{'X-AIYANGNIU-SIGNATURE':'token'}" :accept="accept" :action="uploadUrl" :on-success="uploadAnimalPic" :show-upload-list="false">
						<div v-if="!AnimalValidate.animalPicture" style="width:300px; height:150px; border:1px dotted #ccc;text-align:center; line-height:150px; font-size:25px; color:#ccc;cursor:pointer; border-radius:5px;">+</div>
						<div v-if="AnimalValidate.animalPicture" style="width:300px; height:150px;border:1px dotted #eee;"><img :src="AnimalValidate.animalPicture" alt="" width="100%" height="100%"></div>
					</Upload>
				</FormItem>
			</Form>
			<div slot="footer" style="text-align:center;">
				<Button @click="addAnimalModal=false" style="margin-left: 8px">取消操作</Button>
				<Button type="primary" @click="confirmAdd">确定增加</Button>
			</div>
		</Modal>

	</div>
</template>

<script>
import api from 'service/apiConfig' //真实接口配置
import {countByKind,selectWorkAreas,getListLivestocks,getLivestockById,firstLivestockKinds} from 'service/api'
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
				number:'',
				areaId:'',
				kindId:'',
				beginDate:'',
				endDate:''
			},
			animalKindList:[],
			areaList:[],
			animalsListData:{},   //畜禽列表数据
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
			AnimalFormData:{}
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
		
		// 新增畜禽
		addNewAnimal(){
			this.AnimalValidate = {
				belongKind:'',
				animalPicture:'',
				lastTimeImmune:'',
				medicalHistory:'',
				belongArea:'',
			}
			this.newAnimalModal = true
		},

		// 编辑当前畜禽
		editThisAnimal(row){
			this.AnimalValidate = row
			console.log('=-===',this.AnimalValidate)
			this.editAnimalModal = true
		},

		
		// 上传畜禽图片
		uploadAnimalPic(res){
			if(res.code == 101){
				this.AnimalValidate.animalPicture = res.data ? res.data : ''
				this.$Message.success(res.message)
			}else{
				this.$Message.error(res.message)
			}
		},

		// 确定提交
		confirmUpdate(){
			this.$Message.success('修改成功')
			this.editAnimalModal = false
		},

		// 删除畜禽信息
		delAnimal(row,index){
			this.$Modal.confirm({
				title: '温馨提示',
				content: '<p>您确定删除该员工吗？</p>',
				onOk:() => {
				// await org.deletePasture({id:id}).then((res)=>{
				//   if(res.code == 101){
				//     this.$Message.success(res.message || '删除成功')
				//     this.$parent.getPastureList(); //重新获取列表
				//   }else{
				//     this.$Message.error(res.message || '删除失败')
				//   }
				// })
				this.animalsListData.data.splice(index,1)
				this.$Message.success('删除成功')
				},
				onCancel: () => {
				this.$Message.info('您已取消操作！');
				}
			});
		},

		// 新增畜禽
		confirmAdd(){
			console.log('新增内容',this.AnimalValidate)
			this.animalsListData.data.push(this.AnimalValidate)
			this.newAnimalModal = false
			this.$Message.success('新增成功')
		},

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
			await getListLivestocks(params).then(res=>{
				if(res.code === 0){
					this.animalsListData = res.data;
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
			await getLivestockById({livestockId:row.livestockId}).then(res=>{
				if(res.code === 0){
					this.AnimalFormData = res.data;
					this.viewAnimalModal = true
				}else{
					this.$Message.error(res.message || '系统错误')
				}
			})
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
</style>

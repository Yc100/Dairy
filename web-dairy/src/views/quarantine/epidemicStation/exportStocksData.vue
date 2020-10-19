// author：zhangb
// time:2020-01-10
// desc:本页面为防疫站对畜禽管理支持

<template>
	<div class="animalManage">
		<!-- 操作 -->
		<Row>
			<Col span="6">
				<Row>
					<Col span="20">
						<Select v-model="filtItem.areaId" clearable placeholder="请选择区域" @on-change="initData">
								<Option v-for="item in areaList" :value="item.areaId" :key="item.areaId">{{ item.areaName }}</Option>
						</Select>
					</Col>
				</Row>
			</Col>
			<Col offset="15" span="2"><Button type="primary" @click="exprt">导出</Button></Col>
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
			<el-table-column label="操作" width="140px">
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
		
	</div>
</template>

<script>
import api from 'service/apiConfig' //真实接口配置
import axios from 'axios'
import {countByKind,selectWorkAreas,getListLivestocks,getLivestockById} from 'service/api'
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

		//导出
		exprt(){
			axios({
					method: 'get',
					url: api.apiNomal+"/livestock/exportLivestocks",
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
		
		//获取畜禽种类列表
		async getStocksList(){
			await countByKind({admin:1}).then(res=>{
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
					this.AnimalFormData.name=''
					if(res.data.kindEntityList && res.data.kindEntityList.length>0){
						for(let i = res.data.kindEntityList.length-1;i>=0;i--){
							if(i !== 0){
								this.AnimalFormData.name+=res.data.kindEntityList[i].name+'-'
							}else{
								this.AnimalFormData.name+=res.data.kindEntityList[i].name
							}
						}
					}
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

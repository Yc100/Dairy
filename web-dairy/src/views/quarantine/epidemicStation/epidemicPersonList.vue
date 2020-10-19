// author：zhangb
// time:2020-01-08
// desc:本页面为员工的列表管理，关联相关员工的各种下属职能

<template>
	<div class="epidemicPersonList">
		<!-- 操作 -->
		<div class="belowArea"><i class="iconfont iconyiliaohangyedeICON-"></i> 所属地区：{{userInfo.areaName}}</div><br>
		<Row>
			<Col span="6">
				<Row>
					<Col span="16"><Input v-model="pageParams.name" icon="ios-search" placeholder="请输入员工姓名" style="" clearable /> </Col>
					<Col span="4" offset="2"><Button type="primary" @click="getList">搜索</Button></Col>
				</Row>
			</Col>
			<Col offset="15" span="2"><Button type="primary" @click="addStaff">新增员工</Button></Col>
		</Row>
		<br>
		<epidemic-tab-list :tableEpidemicData="EpidemicData.data" :postList='postList'></epidemic-tab-list>
		<div style="text-align:right;margin:15px 0;">
			<Page show-total :total="parseInt(EpidemicData.total) || 0" :page-size="pageParams.limit" :current="pageParams.current" @on-change='changepage'></Page>
		</div>
	</div>
</template>

<script>
import {mapGetters,mapActions} from 'vuex'
import { listSysUsers,selectSysRoles } from 'service/api'
import epidemicTabList from './components/tableComList.vue' 
export default {
	name: 'epidemicPersonList',
	components: {
		epidemicTabList
	},
	props:{
		
	},
	data() {
		return {
			pageParams:{
				limit:10,
				offset:0,
				current:1,
				name:'',
			},
			EpidemicData:{
				data:[],
				total:''
			},
			postList:[],
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
		//获取岗位列表
		async getModels(){
			await selectSysRoles().then(res=>{
				if(res.code === 0){
				this.postList = res.data;
				}else{
				this.$vux.toast.show({
					type:'cancel',
					text:res.msg || '获取失败'
				})
				}
			}).catch(err=>{
				this.$vux.toast.show({
				type:'cancel',
				text:err || '获取失败'
				})
			})
		},
		addStaff(){
			this.$router.push({
				path: '/epidemicPersonList/addNewEpidemic',
				query:{
					postList:this.postList,
				}
			});
		}
	},

	mounted() {

	},

	watch: {

	},
	created(){
		this.getList()
		this.getModels()
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

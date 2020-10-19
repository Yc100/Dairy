// author：zhangb
// time:2020-10-13
// desc:本页面为疫控任务分布管理

<template>
	<div class="taskManager">
		
		<Row class="filt-header">
			<Col span="8">
				当前查询时间： &nbsp;<DatePicker type="date" placeholder="选择时间" format="yyyy-MM-dd" style="width: 50%" @on-change="(data) =>{ changeDateTime(data)}"></DatePicker>
			</Col>
			<Col offset="1" span="3">
				<Select v-model="pageParams.status" clearable placeholder="请选择状态" @on-change="initData">
					<Option v-for="item in optinons" :value="item.value" :key="item.value">{{ item.label }}</Option>
				</Select>
			</Col>
			<Col offset="6" span="3">
				<Button type="primary" @click="$jump('/addNewTask')">发布新任务</Button>
			</Col>
		</Row>
		<br><br>
		
		<!-- 消息列表 -->
		<div v-if="taskList.list && taskList.list.length>0" >
				<Card class="card-list" style="width:85%;" v-for="i in taskList.list" :key="i.taskId" 
				@click.native="$jump({path:'/checkTaskDetail',query:{taskId:i.taskId}})">
				<Row>
					<Col span="22"><b>{{i.taskName}}</b></Col>
					<Col span="2">
						<Tag :color="i.status !== 2?'error':'green'" style="border-radius:10px;">
							{{i.status | taskStatus}}
						</Tag>
					</Col>
				</Row>
				<div class="card-content">
					内容：{{i.content}}
				</div>
				<Row>
					<Col span="22"><div class="card-time">{{i.createTime}}</div></Col>
					<Col span="2" style="color:#6d9eeb;">查看详情...</Col>
				</Row>
				
			</Card>
		</div>
	
		<div v-else>
			<div style="text-align:center">
				<img :src="imgUrl" width="130px" height="100%" alt="">
			</div>
			<h5 style="font-size:16px;color:#ccc;text-align:center;line-height:30px">暂时没有任务~</h5>
		</div>

		<!-- 分页 -->
		<div style="text-align:right;margin:15px 0;">
			<Page show-total :total="parseInt(taskList.total) || 0" :page-size="pageParams.limit" :current="current" @on-change="changepage"></Page>
		</div>


		
		
	</div>
</template>

<script>
import api from 'service/apiConfig' //真实接口配置
import {getWorkTaskPage } from 'service/api'
import {mapGetters,mapActions} from 'vuex'
export default {
	name: 'taskManager',
	components: {

	},
	props:{
		
	},
	data() {
		return {
			accept:'image/jpg,image/jpeg,image/png',  //上传图片类型
			pageParams:{
				limit:10,
				offset:0,
				beginDate:'',
				status:''
			},
			optinons:[
				{
					label:'全部',
					value:''
				},
				{
					label:'待完成',
					value:'1'
				},
				{
					label:'已完成',
					value:'2'
				},
			],
			taskList:[],
			current:1,
			imgUrl:require('src/assets/imgs/no_data.png')
		}
	},
	computed: {
		...mapGetters([
			'userInfo',        //用户信息
		]),

	},
	methods: {
		...mapActions([

		]),
		changepage(index){
			this.pageParams.offset = (index-1)*this.pageParams.limit;
			this.initData();
		},
		//获取任务列表
	 async	initData(){
			await getWorkTaskPage(this.pageParams).then(res=>{
				if(res.code == 0){
					this.taskList = res.data
				}else{
					this.$Message.error(res.message || '系统错误')
				}
			})
		},
		//时间选择器
		changeDateTime(data){
			console.log(data);
			this.pageParams.beginDate = data
			this.initData()
		}
		
	},

	mounted() {
			this.initData()

	},

	watch: {

	}
}
</script>

<style lang="less" scoped>
.taskManager{
	width: 95%;
	margin: auto auto;
	padding-top: 2em;
	.filt-header{
		margin-top:1em;
	} 
	.card-list{
		margin-bottom: 1em;
		cursor: pointer;
		.card-time{
			font-size: .9em; color: #bbb;text-indent: 0.5em;margin-top: .5em;
		}
	}
	.card-content{
		margin-top: 1.5em; text-indent: 0.5em;
	}

}
</style>

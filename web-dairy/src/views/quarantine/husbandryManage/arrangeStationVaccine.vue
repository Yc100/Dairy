// author：zhangb
// time:2020-01-14
// desc:防疫站安排疫苗得发放记录

<template>
	<div class="arrangeVaccine">
		<!-- 操作 -->
		<div class="belowArea"><i class="iconfont iconyiliaohangyedeICON-"></i> 所属地区：内蒙古呼和浩特市...</div><br>
		<div style="margin-bottom:0.5em;"><i class="iconfont iconshaixuan-"></i> 筛选条件</div>
		<Row>
			<!-- <Col span="4"><Input v-model="filtItem.animalName" icon="ios-search" placeholder="请输入畜禽编号" style="" /></Col> -->
			<Col span="4">
				<Select v-model="filtItem.area" clearable placeholder="请选择防疫站">
					<Option v-for="item in areaList" :value="item.label" :key="item.label">{{ item.label }}</Option>
				</Select>
			</Col>
			<Col offset="1" span="3">
				<Select v-model="filtItem.area" clearable placeholder="请选择领取状态">
					<Option value="">全部</Option>
					<Option value="已领取">已领取</Option>
					<Option value="未领取">未领取</Option>
				</Select>
			</Col>
			<Col offset="1" span="3">
			 	<DatePicker type="date" placeholder="发放开始时间" style="width: 100%"></DatePicker>
			</Col>
			<Col span="1" style="text-align:center;">_</Col>
			<Col span="3">
			 	<DatePicker type="date" placeholder="发放结束时间" style="width: 100%"></DatePicker>
			</Col>
			<Col offset="1" span="2"><Button type="primary" @click="$jump('/arrangeStationVaccine/addNewStationVaccine')">新增发放记录</Button></Col>
		</Row>
		<br>

		<!-- 表格列表内容 -->
		<el-table :data="manageAreaListData.data" style="width: 100%">
			<el-table-column label="发放时间" prop="sendTime" width="170"></el-table-column>
			<el-table-column label="领取防疫站" prop="receivePerson" width="140"></el-table-column>
			<el-table-column label="所属区域" prop="belongArea" width="180"></el-table-column>
			<el-table-column label="领取状态" prop="reciveStatus" width="120"></el-table-column>
			<el-table-column label="领取时间" prop="reciveTime" width="160"></el-table-column>
			<el-table-column label="下发疫苗详细信息" prop="vaccineDetail" align="center">
				<template slot-scope="scope"> 
					<span class="span-click" @click="checkRowDetail(scope.row)">点击查看</span>
				</template>
			</el-table-column>
			<el-table-column label="操作" width="">
			<template slot-scope="scope">
				<!-- <Button type="default" size="small">查看</Button> -->
				<!-- <Button type="primary" size="small" @click="editManageArea(scope.row)">编辑</Button> -->
				<Button type="error" v-if="scope.row.reciveStatus != '未领取'" size="small" @click="">删除</Button>
				<Button type="success" size="small" @click="checkLogistics(scope.row)">物流信息</Button>
			</template>
			</el-table-column>
		</el-table>	

		<Modal v-model="sendVaccineDetailModal" title="下发疫苗详情">
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
			</div>
		</Modal>

		<!-- 查看物流 -->
		<Modal v-model="logisticsModal" title="查看物流信息">
			<div style="padding-left:1em;">
				<Timeline>
					<TimelineItem :color="index != deliverList.length-1 ?  'green':'red'" v-for="(list,index) in deliverList" :key="index">
						<Icon type="ios-radio-button-on" slot="dot"> </Icon>
						<p>{{list.time}}</p>
						<p>{{list.context}}</p>
					</TimelineItem>
				</Timeline>
			</div>
		</Modal>

	</div>
</template>

<script>
import {mapGetters,mapActions} from 'vuex'
// import { manager } from 'service/api'
export default {
	name: 'arrangeVaccine',
	components: {

	},
	props:{
		
	},
	data() {
		return {
			filtItem:{
				area:'',
				startTime:'',
				endTime:'',
			},
			areaList:[
				{label:'A防疫站'},{label:'B防疫站'},{label:'C防疫站'},{label:'D防疫站'}
			],
			manageAreaListData:{
				data:[
					{sendTime:'2020-01-05 00:00:00',receivePerson:'A防疫站',belongArea:'赛罕区',reciveStatus:'已领取',reciveTime:'2020-01-14 00:00:00',vaccineDetail:''},
					{sendTime:'2020-01-05 00:00:00',receivePerson:'B防疫站',belongArea:'玉泉区',reciveStatus:'未领取',reciveTime:'-',vaccineDetail:''}
				]
			},
			sendVaccineDetailModal:false,
			logisticsModal:false,  //物流modal
			deliverList:[
				{"time":"2012-07-07 13:35:14","context":"客户已签收"},
				{"time":"2012-07-07 09:10:10","context":"离开[北京石景山营业厅]派送中，递送员[温]，电话[15200001111]"},
				{"time":"2012-07-06 19:46:38","context":"到达[北京石景山营业厅]"},
				{"time":"2012-07-06 15:22:32","context":"离开[北京石景山营业厅]派送中，递送员[温]，电话[15200001111]"},
				{"time":"2012-07-06 15:05:00","context":"到达[北京石景山营业厅]"},
				{"time":"2012-07-06 13:37:52","context":"离开[北京_同城中转站]发往[北京石景山营业厅]"},
				{"time":"2012-07-06 12:54:41","context":"到达[北京_同城中转站]"},
				{"time":"2012-07-06 11:11:03","context":"离开[北京运转中心驻站班组] 发往[北京_同城中转站]"},
				{"time":"2012-07-06 10:43:21","context":"到达[北京运转中心驻站班组]"},
				{"time":"2012-07-05 21:18:53","context":"离开[福建_厦门支公司] 发往 [北京运转中心_航空]"},
				{"time":"2012-07-05 20:07:27","context":"已取件，到达 [福建_厦门支公司]"}
			],              //物流信息数据
		}
	},
	computed: {
		...mapGetters([

		])
	},
	methods: {
		...mapActions([

		]),
		
		// 查看当前行疫苗详情
		checkRowDetail(){
			this.sendVaccineDetailModal = true
		},

		// 查看物流信息
		checkLogistics(row){
			this.logisticsModal = true
		},
	},

	mounted() {

	},

	watch: {

	}
}
</script>

<style lang="less" scoped>
.arrangeVaccine{
	width: 95%;
	margin: auto auto;
	padding-top: 2em;
	.span-click{
		color: #6d9eeb;		
	}
	.span-click:hover{
		cursor: pointer;
		color: #3c78d8;
		transition: 2s;
	}
}
.vaccine-list{
		display: flex;
		flex-wrap:wrap;
	}
</style>

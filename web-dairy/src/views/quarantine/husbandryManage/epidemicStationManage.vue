// author：zhangb
// time:2020-01-19
// desc:本页面为畜牧局管理防疫站列表

<template>
	<div class="epidemicStationManage">
		<!-- 操作 -->
		<div class="belowArea"><i class="iconfont iconyiliaohangyedeICON-"></i> 所属上级区域：内蒙古呼和浩特市...</div><br>
		<div style="margin-bottom:0.5em;"><i class="iconfont iconshaixuan-"></i> 筛选条件</div>
		<Row>
			<Col span="4"><Input v-model="filtItem.epidemicName" icon="ios-search" placeholder="请输入防疫站名称" style="" /></Col>
			<Col offset="1" span="3">
				<Select v-model="filtItem.area" clearable placeholder="请选择归属区域">
					<Option v-for="item in epidemicBlongArea" :value="item.label" :key="item.label">{{ item.label }}</Option>
				</Select>
			</Col>
			<Col offset="1" span="3">
			 	<DatePicker type="date" placeholder="注册开始时间" style="width: 100%"></DatePicker>
			</Col>
			<Col span="1" style="text-align:center;">_</Col>
			<Col span="3">
			 	<DatePicker type="date" placeholder="注册结束时间" style="width: 100%"></DatePicker>
			</Col>
			<Col offset="1" span="2"><Button type="primary" @click="$jump('/epidemicStationManage/addNewEpidemicStation')">新增防疫站</Button></Col>
		</Row>
		<br>
		
		<!-- 表格列表内容 -->
		<el-table :data="husbandryListData.data" style="width: 100%">
			<el-table-column label="防疫站名称" prop="epidamicStationName" width="260"></el-table-column>
			<el-table-column label="归属上级区域" prop="belongUpArea"></el-table-column>
			<el-table-column label="注册时间" width="180">
				<template slot-scope="scope">
					{{scope.row.regTime | date('%F %T')}}
				</template>
			</el-table-column>
			<el-table-column label="操作" width="450">
			<template slot-scope="scope">
				<Button type="default" size="small" @click="checkEpidemicStationInfo(scope.row)">查看</Button>
				<Button type="warning" size="small" @click="editEpidemicStation(scope.row)">编辑</Button>
				<Button type="error" size="small" @click="delEpidemicStatic(scope.row,scope.$index)">删除</Button>
				<Button type="primary" size="small" @click="nextEpidemicManage(scope.row)">下属员工</Button>
				<Button type="primary" size="small" @click="nextAreaManage(scope.row)">下属区域</Button>
				<Button type="primary" size="small" @click="nextAnimalManage(scope.row)">畜禽管理</Button>
			</template>
			</el-table-column>
		</el-table>	

		<!-- 分页 -->
		<div style="text-align:right;margin:15px 0;">
			<Page show-total show-elevator :total="parseInt(husbandryListData.total) || 0" :page-size="pageParams.limit" :current="pageParams.current"></Page>
		</div>
		
		<!-- 查看防疫站详情信息 -->
		<Modal v-model="checkEpidemicStationModal" title="防疫站信息" width="60%">
		<Tag color="primary" style="margin-bottom:10px;">基本信息</Tag>
		<Card style="width:100%">
			<Row>
				<Col offset="1" span="10">组织类型：防疫站（县）</Col>
				<Col span="13">组织名称：赛罕区畜禽防疫站</Col>
				</Row>
			<Row style="margin-top:10px;">
				<Col offset="1" span="10">组织缩写：SHQXQFYZ</Col>
				<Col span="12">单位地址：内蒙古呼和浩特市赛罕区腾飞路腾飞大厦B座</Col>
			</Row>
			<Row style="margin-top:10px;">
				<Col offset="1" span="10">联系人：张三</Col>
				<Col span="12">联系电话：0471-6521245</Col>
			</Row>
			<Row style="margin-top:10px;">
				<Col offset="1" span="20">组织简介：本组织致力为各县及地区动物畜禽防疫工作安排处理，积极响应各级政府工作安排，国家重要防疫办法管理通知等，做好畜禽防疫，保证卫生安全。</Col>
			</Row>
			<Row style="margin-top:30px;">
				<Col offset="1" span="10">注册单位：内蒙古呼和浩特农牧局</Col>
				<Col span="12">注册编号：A00HHHT00251421221Z</Col>
			</Row>
			<Row style="margin-top:10px;">
				<Col offset="1" span="10">机构证书扫描件：</Col>
				<Col span="12"><div style="width:400px; height:250px; border:1px solid #eee;"><img src="../../../assets/imgs/noimg.png" width="100%" height="100%" alt=""></div></Col>
			</Row>
			<!-- <Row style="margin-top:10px;">
				<Col offset="1" span="10">法人身份证扫描件（反面）：</Col>
				<Col span="12"><div style="width:400px; height:250px;"><img src="" width="100%" height="100%" alt=""></div></Col>
			</Row>
			<Row style="margin-top:20px;">
				<Col offset="1" span="10">营业执照扫描件：</Col>
				<Col span="12"><div style="width:400px; height:250px;"><img src="" width="100%" height="100%" alt=""></div></Col>
			</Row> -->
			</Card>
		</Modal>

	</div>
</template>

<script>
import api from 'service/apiConfig' //真实接口配置
import {mapGetters,mapActions} from 'vuex'
// import { manager } from 'service/api'
export default {
	name: 'epidemicStationManage',
	components: {

	},
	props:{
		
	},
	data() {
		return {
			accept:'image/jpg,image/jpeg,image/png',  //上传图片类型
			filtItem:{
				area:'',
				epidemicName:'',
			},
			animalKindList:[
				{label:'牛'},{label:'马'},{label:'猪'},{label:'羊'},{label:'狗'},{label:'鸡'}
			],
			areaList:[
				{label:'腾飞路腾飞大厦'},{label:'大学东街'},{label:'人民路街道'},{label:'乌兰察布路街道'}
			],
			epidemicBlongArea:[
				{label:'赛罕区'},{label:'回民区'},{label:'玉泉区'},{label:'新城区'}
			],
			husbandryListData:{  //防疫站列表数据
				data:[
					{epidamicStationName:'赛罕区腾飞路防疫站',belongUpArea:'赛罕区',regTime:'2020-01-19 00:00:00'}
				]
			},
			pageParams:{
				limit:10,
				offset:0,
				current:1,
			},
			checkEpidemicStationModal:false, //查看防疫站modal
		}
	},
	computed: {
		...mapGetters([

		]),

		//上传图片的url地址
		uploadUrl(){
			return api.apiItem +'/images/upload?service=account'
		}
	},
	methods: {
		...mapActions([

		]),
		
		// 查看防疫站的信息
		checkEpidemicStationInfo(){
			this.checkEpidemicStationModal = true		
		},

		// 编辑防疫站信息
		editEpidemicStation(){
			this.$jump('/epidemicStationManage/addNewEpidemicStation')
		},

		// 删除防疫站
		delEpidemicStatic(row,index){
			this.$Modal.confirm({
				title: '温馨提示',
				content: '<p>您确定删除该防疫站码？</p>',
				onOk:() => {
				// await org.deletePasture({id:id}).then((res)=>{
				//   if(res.code == 101){
				//     this.$Message.success(res.message || '删除成功')
				//     this.$parent.getPastureList(); //重新获取列表
				//   }else{
				//     this.$Message.error(res.message || '删除失败')
				//   }
				// })
				this.husbandryListData.data.splice(index,1)
				this.$Message.success('删除成功')
				},
				onCancel: () => {
				this.$Message.info('您已取消操作！');
				}
			});
		},

		// 管理下级防疫站的员工
		nextEpidemicManage(row){
			this.$jump('epidemicPersonList');
		},

		// 管理下级防疫站的管理区域
		nextAreaManage(row){
			this.$jump('manageAreaList')
		},

		// 管理下级防疫站畜禽信息
		nextAnimalManage(row){
			this.$jump('animalManage')
		},

		
	},

	mounted() {

	},

	watch: {

	}
}
</script>

<style lang="less" scoped>
.epidemicStationManage{
	width: 95%;
	margin: auto auto;
	padding-top: 2em;
}
</style>

// author：zhangb
// time:2020-01-09
// desc:本页面为员工管理各员工职能区域


<template>
	<div class="manageAreaList">
		<!-- 操作 -->
		<div class="belowArea"><i class="iconfont iconyiliaohangyedeICON-"></i> 所属地区：{{userInfo.areaName}}</div><br>
		<Row>
			<Col span="6">
				<Row>
					<Col span="16"><Input v-model="pageParams.areaName" icon="ios-search" placeholder="请输入区域名称" style="" clearable /> </Col>
					<Col span="4" offset="2"><Button type="primary" @click="getList">搜索</Button></Col>
				</Row>
			</Col>
			<Col offset="15" span="2"><Button type="primary" @click="addNewArea">新增区域</Button></Col>
		</Row>
		<br>

		<!-- 表格列表内容 -->
		<el-table :data="manageAreaListData.data" style="width: 100%">
			<el-table-column label="区域名称" prop="areaName"></el-table-column>
			<el-table-column label="所属省" prop="province" ></el-table-column>
			<el-table-column label="所属市" prop="city"></el-table-column>
			<el-table-column label="所属县（区）" prop="district"></el-table-column>
			<el-table-column label="详细地址" prop="addressInfo"></el-table-column>
			<el-table-column label="管理员" prop="userName"></el-table-column>
			<el-table-column label="操作" width="290px">
			<template slot-scope="scope">
				<Button type="warning" size="small" @click="lookDetail(scope.row)">查看</Button>
				<Button type="primary" size="small" @click="editManageArea(scope.row)">分配管理员</Button>
				<Button type="error" size="small" @click="delManageArea(scope.row)">删除</Button>
			</template>
			</el-table-column>
		</el-table>	

		<!-- 分页 -->
		<div style="text-align:right;margin:15px 0;">
			<Page show-total :total="parseInt(manageAreaListData.total) || 0" :page-size="pageParams.limit" :current="pageParams.current" @on-change='changepage'></Page>
		</div>

		<!--查看畜禽详情信息 -->
		<Modal v-model="checkAnimalModal" title="查看详情" width="50%">
			<Card>
				<p slot="title">
					<Icon type="ios-information-circle-outline" size="18" /> 基本信息
				</p>
				<Row>
					<Col span="6">区域名称:</Col>
					<Col span="14">{{detailData.areaName}}</Col>
				</Row>	
				<Row style="margin-top:0.5em;">
					<Col span="6">位置:</Col>
					<Col span="14">{{detailData.province}}{{detailData.city}}{{detailData.district}}</Col>
				</Row>
				<Row style="margin-top:0.5em;">
					<Col span="6">详细地址:</Col>
					<Col span="14">{{detailData.addressInfo}}</Col>
				</Row>
				<Row style="margin-top:0.5em;">
					<Col span="6">管理员:</Col>
					<Col span="14">{{detailData.executor || '暂无'}}</Col>
				</Row>
				<Row style="margin-top:0.5em;"  v-if="detailData.addTime">
					<Col span="6">创建时间:</Col>
					<Col span="14">{{detailData.addTime.split(' ')[0]}}</Col>
				</Row>
        	</Card>
			<div class="mapWarp">
       			<!--接入百度地图-->
				<baidu-map class="mapBox" :center="mapInfo.center" :zoom="mapInfo.zoom" @ready="handler" :double-click-zoom="false" ak="7rFAh6uPXLIb0Sc8Lpt1X5goEGkyFr9R">
					<!-- 在地图中添加可编辑的多边形 -->
					<bm-polygon :path="polygonPathArr" stroke-color="blue" fill-color="#74C4F7" :fill-opacity="0.3" :stroke-opacity="0.5" :stroke-weight="3" :editing="false"/>
					<!-- 缩放地图控件 -->
					<bm-navigation anchor="BMAP_ANCHOR_BOTTOM_RIGHT"></bm-navigation>
				</baidu-map>
			</div>
			<div slot="footer" style="text-align:right;">
				<Button type="primary" @click="checkAnimalModal = false">关闭</Button>
			</div>
		</Modal>

		<!--分配管理员 -->
		<Modal v-model="editManageAreaModal" title="分配管理员" width="50%">
			<Form ref="editManagAreaValidate" :model="ManagAreaValidate" :label-width="170">
				<FormItem label="区域管理人员" prop="chargePerson">
					<Select v-model="ManagAreaValidate.userId" clearable style="width:50%;" placeholder="请选择区域管理人员">
						<Option :value="item.id" v-for="item in userArr" :key="item.id">{{item.nickName}}</Option>
					</Select>
				</FormItem>
			</Form>
			<div slot="footer" style="text-align:center;">
				<Button type="primary" @click="handleSubmit">确定</Button>
				<Button @click="editManageAreaModal=false" style="margin-left: 8px">取消</Button>
			</div>
		</Modal>


		<!--新增区域信息 -->
		<Modal v-model="addManageAreaModal" title="新增区域信息" width="50%">
			<Form ref="basicNew" :model="basicNew" :rules="validateBasicNew" :label-width="100">
				<FormItem label="区域名称" prop="areaName">
					<Input v-model="basicNew.areaName"  placeholder="请填写区域名称" style="width:96%;"></Input>
				</FormItem>
				<FormItem label="省市区" prop="district">
					<selectAddress :list="addressData" v-model="basicNew.district" @input="getNewAddr"></selectAddress>
				</FormItem>
				<FormItem label="详细地址" prop="addressInfo">
					<Input v-model="basicNew.addressInfo" placeholder="请填写详细区域信息" style="width:96%;"></Input>
				</FormItem>
			</Form>
			<div class="mapWarp">
				<!--接入百度地图-->
				<baidu-map class="mapBox" :center="mapInfo.center" :zoom="mapInfo.zoom" @ready="handler" 
				:double-click-zoom="false" ak="7rFAh6uPXLIb0Sc8Lpt1X5goEGkyFr9R"
				@click="drawLine" @mousemove="syncPolygon">
					<!-- 在地图中添加可编辑的多边形 -->
					<bm-polygon :path="item"  v-for="(item,i) in polygonPath.paths"
						:key="item.toString()" stroke-color="blue" fill-color="#74C4F7"
						:fill-opacity="0.3" :stroke-opacity="0.5" :stroke-weight="3" 
						:editing="polygonPath.editing" @lineupdate="updatePolygonPath($event,i)"/>
					<!-- 缩放地图控件 -->
					<bm-navigation anchor="BMAP_ANCHOR_BOTTOM_RIGHT"></bm-navigation>
					
					<bm-local-search :auto-viewport="true" :location="basicNew.district.join(',')"></bm-local-search>
					<!--点-->
					<bm-marker 
						v-for="(item, index) in polygonPath.paths[0]"
						:position="item"
						:key="index"
						:dragging="false" @click="toggle('polygonPath')">
					</bm-marker>
					<bm-control style="margin-top:10px">
						<Button class="controlBtn" size="small" @click="toggle('polygonPath')">{{ polygonPath.editing ? '结束绘制' : '绘制区域' }}</Button>
						<Button class="controlBtn" size="small" @click="clear">清空</Button>
					</bm-control>
				</baidu-map>
			</div>
			<div slot="footer" style="text-align:center;">
				<Button @click="addManageAreaModal=false" style="margin-left: 8px">取消操作</Button>
				<Button type="primary" @click="addNewAreaConfirm('basicNew')">确定新增</Button>
			</div>
		</Modal>

	</div>
</template>

<script>
import {mapGetters,mapActions} from 'vuex'
import {getAreaListPage,deleteWorkArea,selectSysUsers,setAreaToUser,getAreaDetail,addWorkArea} from 'service/api'
import { BaiduMap, BmPolygon, BmNavigation,BmMarker, BmLocalSearch,BmControl } from 'vue-baidu-map'
import selectAddress from 'components/select/selectAddress.vue'
import { ChinaAddressV3Data,Value2nameFilter as value2name, Name2valueFilter as name2value} from 'vux'
export default {
	name: 'manageAreaList',
	components: {
		BaiduMap,BmPolygon,BmNavigation,BmMarker,BmLocalSearch,selectAddress,BmControl
	},
	props:{
		
	},
	data() {
		return {
			pageParams:{   //分页参数
				limit:10,
				offset:0,
				current:1,
				areaName:'', 
				admin:1,
			},
			manageAreaListData:{
				data:[],
				total:''
			},  //列表
			editManageAreaModal:false, //分配管理员modal
			addManageAreaModal:false, //新增区域的信息modal
			checkAnimalModal:false, //查看modal
			userArr:[] , //区域管理员
			userId:'',
			ManagAreaValidate:{
				userId:'',
				areaId:''
			},   //分配管理员
			ruleManagAreaValidate:{
				belongProvince:[{ required: true, message: '请填写所属省', trigger: 'blur' }],
				belongCity:[{ required: true, message: '请填写所属市', trigger: 'blur' }],
				belongArea:[{ required: true, message: '请填写所属区、县', trigger: 'blur' }],
				detailAddress:[{ required: true, message: '请填写所详细区域地址', trigger: 'blur' }],
				chargePerson:[{ required: true, message: '请选择责任员工', trigger: 'change' }],
			},
			detailData:{},  //详情
			polygonPathArr: [],
			BMap:null,
			map:null,
			mapInfo:{
				center: {lng: 111.76528118824002, lat: 40.81792291425712},
				zoom: 3,
				show: true,
				dragging: true,
				point:''
			},
			point:'',
			polygonPath: {
				editing: false,
				paths: [] // 绘制完成后的经纬度，其实是在画的时候动态push的，因为在点击的时候触发了 paintPolygon 函数
			},
			basicNew:{
				areaName:'',
				district:[],
				addressInfo:""
			},
			validateBasicNew:{
					areaName:[
						{required:true,message:'请输入区域名称',trigger:'blur'}
					],
					district:[
						{required:true,type:'array',message:'请选择省市区',trigger:'change'}
					],
					addressInfo:[
						{required:true,message:'请输入详细地址',trigger:'blur'}
					],
			},
			addressData: ChinaAddressV3Data,
			addressName:[]
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
			await getAreaListPage(this.pageParams).then(res=>{
				if(res.code === 0){
					this.manageAreaListData.data = res.data.list;
					this.manageAreaListData.total = res.data.total
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
		//获取管理员列表数据
		async getUserList() {
			await selectSysUsers().then(res=>{
				if(res.code === 0){
					this.userArr = res.data;
				}else{
					this.$Message.error('获取失败！');
				}
			}).catch(err=>{
				this.$Message.error('获取失败！');
			})
		},
		// 新增一个区域信息
		addNewArea(){
			this.basicNew={
				areaName:'',
				district:[],
				addressInfo:""
			}
			this.addManageAreaModal = true
		},
		// 确定新增
		async addNewAreaConfirm(name){
				this.$refs[name].validate(async(valid) => {
				if (valid) {
					if(this.polygonPath.paths.length>0){
						let str = JSON.stringify(this.polygonPath.paths[0])
						this.addressName = _.map(this.basicNew.district, (val) => {
							return value2name([val], ChinaAddressV3Data)
						})
						await addWorkArea({
							areaName:this.basicNew.areaName,
							province:this.addressName[0],
							city:this.addressName[1],
							district:this.addressName[2],
							addressInfo:this.basicNew.addressInfo,
							areas:str
						}).then((res)=>{
							if(res.code == 0){
								this.$Message.success(res.message || '新增成功');
								this.addManageAreaModal = false
							}else{
								this.$Message.error({
									duration:3,
									content:res.message || '新增失败！'
								});
							}
						})
					}else{
						this.$Message.error('请先绘制管理区域')
					}
				} else {
					this.$Message.error('请检查各项内容是否填写完整！');
				}
			})

		},

		// 编辑某区域信息
		editManageArea(row){
			this.ManagAreaValidate = {
				userId:'',
				areaId:''
			}
			this.ManagAreaValidate.areaId = row.areaId
			this.editManageAreaModal = true
		},
		// 删除区域
		delManageArea(data){
			this.$Modal.confirm({
				title: '温馨提示',
				content: '<p>您确定删除该区域吗？</p>',
				onOk:async() => {
					await deleteWorkArea({areaId:data.areaId}).then((res)=>{
						if(res.code == 0){
							this.$Message.success(res.msg || '删除成功')
							this.getList()
						}else{
							this.$Message.error(res.msg || '删除失败')
						}
					})
				},
				onCancel: () => {
					this.$Message.info('您已取消操作！');
				}
			});
		},
		// 分配管理员
		async handleSubmit () {
			await setAreaToUser(this.ManagAreaValidate).then((res)=>{
				if(res.code == 0){
					this.$Message.success(res.msg || '分配管理员成功');
					this.editManageAreaModal = false
					this.getList(); //重新获取列表
				}else{
					this.$Message.error({
						duration:3,
						content:res.msg || '分配管理员失败！'
					});
				}
			})
		},
		//查看
		async lookDetail(data){
			await getAreaDetail({areaId:data.areaId}).then((res)=>{
				if(res.code == 0){
					this.detailData = res.data;
					this.point = res.data.province + res.data.city + res.data.district;
					this.map.centerAndZoom(this.point,15); 
					if(res.data.areas){
						let arr = JSON.parse(res.data.areas)
						arr.forEach(item=>{
							this.polygonPathArr.push(item)
						})
					}
					// console.log(this.polygonPathArr,'000');
					this.checkAnimalModal = true;
				}else{
					this.$Message.error({
						duration:3,
						content:res.msg || '获取详情失败！'
					});
				}
			})
		},
		//地图初始化定位
		handler ({BMap, map}) {
			// console.log(BMap, map)
			this.BMap = BMap
			this.map = map
			// 鼠标缩放
			map.enableScrollWheelZoom(true);
			// 点击事件获取经纬度
			this.point = "内蒙古呼和浩特"
			map.centerAndZoom(this.point,15); 
		},
		//获取新地址地图
		getNewAddr(){
			this.addressName = _.map(this.basicNew.district, (val) => {
				return value2name([val], ChinaAddressV3Data)
			})
			this.point = this.addressName.join('')
			this.map.centerAndZoom(this.point,15); 
		},
		//切换地图编辑状态
		toggle(name){
			this[name].editing = !this[name].editing;
		},
		// 鼠标左键键多边形绘制
		drawLine(e) {
			if (!this.polygonPath.editing) {
				return;
			}
			const { paths } = this.polygonPath;
			!paths.length && paths.push([]);
			paths[paths.length - 1].push(e.point)
		},
		//鼠标移动时
		syncPolygon(e){
			if (!this.polygonPath.editing) {
				return
			}
			const { paths } = this.polygonPath
			if (!paths.length) {
				return
			}
			const path = paths[paths.length - 1]
			if (!path.length) {
				return
			}
			if (path.length === 1) {
				path.push(e.point)
			}
			this.$set(path, path.length - 1, e.point)
		},
		// 修改拖拽坐标
		updatePolygonPath(e,i) {
			this.polygonPath.paths[i] = e.target.getPath();
		},
		//清空
		clear(){
			if (this.polygonPath.paths) {
				this.polygonPath.paths = [];
			}
		}
	},

	mounted() {

	},

	watch: {

	},
	created(){
		this.getList()
		this.getUserList()
	}
}
</script>

<style lang="less" scoped>
.manageAreaList{
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
.mapBox{
	height: 300px;
	margin-top: 20px;
}
.controlBtn{
    background: #fefefe;
    border-radius: 5px;
    padding: 0px 10px;
}
</style>

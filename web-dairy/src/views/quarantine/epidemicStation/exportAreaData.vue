// author：gy
// time:2020-10-12
// desc:本页面为区域管理导出列表页面


<template>
	<div class="manageAreaList">
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
		<el-table :data="manageAreaListData.data" style="width: 100%">
			<el-table-column label="区域名称" prop="areaName"></el-table-column>
			<el-table-column label="所属省" prop="province" ></el-table-column>
			<el-table-column label="所属市" prop="city"></el-table-column>
			<el-table-column label="所属县（区）" prop="district"></el-table-column>
			<el-table-column label="详细地址" prop="addressInfo"></el-table-column>
			<el-table-column label="管理员" prop="userName"></el-table-column>
			<el-table-column label="操作" width="290px">
			<template slot-scope="scope">
				<Button size="small" @click="lookDetail(scope.row)" type="primary">查看</Button>
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
					<Col span="14">{{detailData.userName || '暂无'}}</Col>
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

	</div>
</template>

<script>
import {mapGetters,mapActions} from 'vuex'
import axios from 'axios'
import api from 'service/apiConfig'
import {getAreaListPage,getAreaDetail,selectWorkAreas} from 'service/api'
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
				areaId:'', 
				admin:1,
			},
			areaList:[],
			manageAreaListData:{
				data:[],
				total:''
			},  //列表
			checkAnimalModal:false, //查看modal
			userId:'',
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
		// 导出列表数据
		exprt(){
			axios({
					method: 'get',
					url: api.apiNomal+"/workarea/exportWorkAreas",
					// headers里面设置token
					headers: {
							'Content-Type': 'application/json',
							'token':localStorage.token,
					},
					params: {
							areaId: this.pageParams.areaId,
							admin:1,
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
					console.log(this.polygonPathArr,'000');
					this.checkAnimalModal = true;
				}else{
					this.$Message.error({
						duration:3,
						content:res.message || '获取详情失败！'
					});
				}
			})
		},
		//地图初始化定位
		handler ({BMap, map}) {
			console.log(BMap, map)
			this.BMap = BMap
			this.map = map
			// 鼠标缩放
			map.enableScrollWheelZoom(true);
			// 点击事件获取经纬度
			this.point = "内蒙古呼和浩特"
			map.centerAndZoom(this.point,15); 
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

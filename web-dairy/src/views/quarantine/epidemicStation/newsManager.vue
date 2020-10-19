// author：jiana
// time:2020-10-14
// desc:本页面为消息中心

<template>
	<div class="newsManager">
		<Row class="filt-header">
			<Col span="8">
				开始时间： &nbsp;<DatePicker  type="datetime" placeholder="选择时间" format="yyyy-MM-dd HH:mm"  @on-change="getPublishDtBegin" style="width: 50%" v-model="dateValue"></DatePicker>
			</Col>
			<Col span="4" offset="2"><Button type="primary" @click="getlistWorkMsgs">搜索</Button></Col>
		</Row>
		<br><br>
		<!-- 消息列表 -->
		<Card class="card-list" style="width:85%;" v-for="(item,index) in workList.data" :key="index">
			<div @click="lookDetail(item)">
				<Row>
					<Col span="22"><b>{{item.realName}} 《{{item.title}}》</b></Col>
					<Col span="2">
						<Tag color="error" style="border-radius:10px;" v-if="item.status == 1">{{item.status | messageStatus}}</Tag>
						<Tag color="success" style="border-radius:10px;" v-if="item.status == 2">{{item.status | messageStatus}}</Tag>
					</Col>
				</Row>
				<Row>
					<Col span="22"><div class="card-time">{{item.createTime}}</div></Col>
					<Col span="2" style="color:#6d9eeb;font-size: 13px;margin-top: 10px;"><span>查看详情</span> </Col>
				</Row>
			</div>
		</Card>
		<!-- 分页 -->
		<div style="text-align:right;margin:15px 0;">
			<Page show-total :total="parseInt(workList.total) || 0" :page-size="pageParams.limit" :current="pageParams.current" @on-change='changepage'></Page>
		</div>
		<!-- 周报详情 -->
		<Modal v-model="reportModel" title="查看周报" width="60%">
			<div class="reportBox">
				<Card>
					<p slot="title">
						<Icon type="ios-information-circle-outline" size="18" /> 基本信息
					</p>
					<Row>
						<Col span="2">日期范围：</Col>
						<Col span="14">{{detailData.beginDate}} 至 {{detailData.endDate}}</Col>
					</Row>	
					<Row>
						<Col span="6" offset='1'>
							<div class="bgColor" :style="'background-image:url('+bcImage+')'">
								<p>片区数量</p>
								<p>{{detailData.nowAreas}}</p>
							</div>
						</Col>
						<Col span="6" offset='1'>
							<div class="bgColor" :style="'background-image:url('+bcImage+')'">
								<p>养殖户数量(户)</p>
								<p>{{detailData.nowFarmers}}</p>
							</div>
						</Col>
						<Col span="6" offset='1'>
							<div class="bgColor" :style="'background-image:url('+bcImage+')'">
								<p>畜禽总数(牛单位)</p>
								<p>{{detailData.nowLivestocks}}</p>
							</div>
						</Col>
					</Row>
					<div class="animalWrap">
						<ul class="dataLoop">
							<li v-for="item in detailData.infoList" :key="item.id">
								<Row >
									<Col span="3">
										<span class="sortNum">{{item.kindName}}：{{item.nowCount}}（{{item.kindId | unitName}}）</span>
									</Col>
									<Col span="16">
										<div class="datashowLine">
											<span class="jzpercent">接种率：</span>
											<Progress :percent="item.nowVaccine || 0" :stroke-width="8" text-inside class="progressClass" status="active"/>
											<span class="jzpercent">{{item.nowVaccine || 0}}%</span>
										</div>
									</Col>
								</Row>
							</li>
						</ul>
					</div>
				</Card>
				<Card style="margin-top:15px;">
					<p slot="title">
						<Icon type="ios-information-circle-outline" size="18" /> 调出统计
					</p>
					<Row v-if='detailData.infoList && detailData.infoList.length>0'>
						<Col span="3" v-for="item in detailData.infoList" :key="item.id">
							<span class="sortNum">{{item.kindName}}&nbsp;:&nbsp;</span>
							<span class="newAddData">
								<span>{{item.expCount}} {{item.kindId | unitName}}</span>
							</span>
						</Col>
					</Row>	
					<div v-else>
						无调出记录！
					</div>
				</Card>
				<Card style="margin-top:15px;">
					<p slot="title">
						<Icon type="ios-information-circle-outline" size="18" /> 调入统计
					</p>
					<Row v-if='detailData.infoList && detailData.infoList.length>0'>
						<Col span="3" v-for="item in detailData.infoList" :key="item.id">
							<span class="sortNum">{{item.kindName}}&nbsp;:&nbsp;</span>
							<span class="newAddData">
								<span>{{item.impCount}} {{item.kindId | unitName}}</span>
							</span>
						</Col>
					</Row>
					<div v-else>
						无调入记录！
					</div>	
				</Card>
				<Card style="margin-top:15px;">
					<p slot="title">
						<Icon type="ios-information-circle-outline" size="18" /> 死亡统计
					</p>
					<Row v-if='detailData.infoList && detailData.infoList.length>0'>
						<Col span="3" v-for="item in detailData.infoList" :key="item.id">
							<span class="sortNum">{{item.kindName}}&nbsp;:&nbsp;</span>
							<span class="newAddData">
								<span>{{item.diedCount}} {{item.kindId | unitName}}</span>
							</span>
						</Col>
					</Row>
					<div v-else>
						无死亡记录！
					</div>	
				</Card>
			</div>
			<div slot="footer" style="text-align:center;">
				<Button type="success" @click="submitReport" v-if="status == 1">标为已读</Button>
				<Button type="primary" @click="reportModel = false">关闭</Button>
			</div>
		</Modal>
		<!-- 疫情上报详情 -->
		<Modal v-model="epidemicModel" title="查看疫情上报" width="60%">
			<Card style="width:100%">
				<Row>
					<Col offset="1" span="10">所在位置：</Col>
					<Col span="13">联系电话：</Col>
				</Row>
				<Row style="margin-top:10px;">
					<Col offset="1" span="10">现场状况描述：</Col>
				</Row>
				<Row style="margin-top:10px;">
					<Col offset="1" span="10">畜禽或其他表现症状：</Col>
				</Row>
				<Row style="margin-top:10px;">
					<Col>
						<div class="cdPoszition">
							<p class="titleFont">现场照片上传</p>
							<p class="contentFont">
								<!-- <span v-for="(item,index) in reportDetail.fileList" :key="index"
								:style="{marginRight:'10px'}"
								@click="preview(item)">
								<img :src="item.url" width="100px" height="100px">
								</span> -->
							</p>
						</div>
					</Col>
				</Row>
				<Row style="margin-top:10px;">
					<Col>
						<div class="cdPoszition">
							<p class="titleFont">其他附件上传</p>
							<p class="contentFont">
								<!-- <span v-for="(item,index) in reportDetail.fileList" :key="index"
								:style="{marginRight:'10px'}"
								@click="preview(item)">
								<img :src="item.url" width="100px" height="100px">
								</span> -->
							</p>
						</div>
					</Col>
				</Row>
			</Card>
		</Modal>
	</div>
</template>

<script>
import api from 'service/apiConfig' //真实接口配置
import { listWorkMsgs,getWeekDiaryById,readWorkMsg} from 'service/api'
import {mapGetters,mapActions} from 'vuex'
export default {
	name: 'newsManager',
	components: {

	},
	props:{
		
	},
	data() {
		return {
			accept:'image/jpg,image/jpeg,image/png',  //上传图片类型
			pageParams:{
				offset:0,
				limit:10,
				status:0,
				beginDate:''
			},
			workList:{
				data:[],
				total:''
			},
			dateValue:'',
			reportModel:false,
			epidemicModel:false,
			detailData:{},
			bcImage:require('../../../assets/imgs/one.png'),
			status:'',
			detailObj:{detailId:''},
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
		//时间格式转化
		getPublishDtBegin(dateTime){
			this.pageParams.beginDate = dateTime
		},
		//获取消息列表
		async getlistWorkMsgs(){
			await listWorkMsgs(this.pageParams).then(res=>{
				if(res.code === 0){
					this.workList.data = res.data.list;
					this.workList.total = res.data.total
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
			this.getlistWorkMsgs();
		},
		//查看详情
		lookDetail(item){
			this.status = item.status;
			this.detailObj.detailId = item.detailId
			if(item.kind == 1){
				//kind为1是周报
				this.getDetail(item.infoId);
				this.reportModel = true;
			}else if(item.kind == 2){
				//kind为2是疫情上报
				this.epidemicModel = true;
			}
		},
		//获取周报详情
		async getDetail(weekId){
			await getWeekDiaryById({
				weekId:weekId
			}).then(res=>{
				if(res.code === 0){
					this.detailData = res.data;
				}else{
					this.$Message.error('获取失败！');
				}
			})
		},
		//标为已读
		async submitReport(){
			await readWorkMsg(this.detailObj).then(res=>{
				if(res.code === 0){
					this.reportModel = false;
					this.$Message.success(res.msg || '获取失败！');
				}else{
					this.$Message.error(res.msg || '获取失败！');
				}
			})
		}
	},

	mounted() {
	

	},

	watch: {

	},
	created(){
		this.getlistWorkMsgs()
	}
}
</script>

<style lang="less" scoped>
.newsManager{
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
.bgColor{
	border-radius: 5px;
	// background-image: url('../../assets/img/one.png');
	background-size: 100% 100%;
	height: 70px;
	margin: 10px 0;
	padding-left:5px;
	font-size: 14px;
	color: #ffffff;
	p{
		line-height: 35px;
	}
}
.dataLoop{
	width: 100%;
	padding-left: 5px;
	li{
		// position: relative;
		list-style: none;
		line-height: 40px;
	}
	.sortNum{
		font-weight: 300;
		font-size: 0.35rem;
	}
}
.jzpercent{
	font-size: 12px;
}
.progressClass{width: 80%;}
.newAddData{
	color: #F00909;
}
</style>

// author：zhangb
// time:2020-01-14
// desc:防疫站疫苗信息管理


<template>
	<div class="vaccineManage">
		<!-- 操作 -->
		<Row>
			<Col span="12">
				<div class="belowArea"><i class="iconfont iconyiliaohangyedeICON-"></i> 所属地区：内蒙古呼和浩特市...</div>
			</Col>
			<Col span="12">
				<!-- <Button type="primary">新增疫苗进储</Button> -->
			</Col>
		</Row>
		<br>

		<!-- 疫苗总数统计 -->
		
		<Row :gutter="20">
			<i-col :xs="12" :md="8" :lg="4" v-for="(infor, i) in inforCardData" :key="`infor-${i}`" style="height: 120px;padding-bottom: 10px;">
				<infor-card shadow :color="infor.color" :icon="infor.icon" :icon-size="36">
				<count-to :end="infor.count" count-class="count-style"/>
				<p>{{ infor.title }}</p>
				<p class="detail-info" @click="checkVaccineDetail(infor)">查看详情</p>
				</infor-card>
			</i-col>
		</Row>

		<!-- 疫苗详情信息 -->
		<Modal v-model="vaccineDetailModal" title="疫苗数量详细">
			<Row>
				<Col span="8">
					<Card style="width:130px; margin:auto auto;">
						<div style="border-bottom:1px solid #eee;margin-bottom:0.5em;"><i class="iconfont font-size iconyang"></i></div>
						<p style="font-size:13px;">疫苗蓄种：羊</p>
						<p style="font-size:13px;">疫苗总数：50</p>
						<p style="font-size:13px;">亚单位疫苗：20</p>
						<p style="font-size:13px;">活疫苗：15</p>
						<p style="font-size:13px;">灭活疫苗：15</p>
					</Card>
				</Col>
				<Col span="8">
					<Card style="width:130px; margin:auto auto;">
						<div style="border-bottom:1px solid #eee;margin-bottom:0.5em;"><i class="iconfont font-size iconma"></i></div>
						<p style="font-size:13px;">疫苗蓄种：马</p>
						<p style="font-size:13px;">疫苗总数：50</p>
						<p style="font-size:13px;">亚单位疫苗：20</p>
						<p style="font-size:13px;">活疫苗：15</p>
						<p style="font-size:13px;">灭活疫苗：15</p>
					</Card>
				</Col>
				<Col span="8">
					<Card style="width:130px; margin:auto auto;">
						<div style="border-bottom:1px solid #eee;margin-bottom:0.5em;"><i class="iconfont font-size iconniu"></i></div>
						<p style="font-size:13px;">疫苗蓄种：牛</p>
						<p style="font-size:13px;">疫苗总数：50</p>
						<p style="font-size:13px;">亚单位疫苗：20</p>
						<p style="font-size:13px;">活疫苗：15</p>
						<p style="font-size:13px;">灭活疫苗：15</p>
					</Card>
				</Col>
			</Row>
			<Row style="margin-top:1em;">
				<Col span="8">
					<Card style="width:130px; margin:auto auto;">
						<div style="border-bottom:1px solid #eee;margin-bottom:0.5em;"><i class="iconfont font-size iconzhu"></i></div>
						<p style="font-size:13px;">疫苗蓄种：猪</p>
						<p style="font-size:13px;">疫苗总数：50</p>
						<p style="font-size:13px;">亚单位疫苗：20</p>
						<p style="font-size:13px;">活疫苗：15</p>
						<p style="font-size:13px;">灭活疫苗：15</p>
					</Card>
				</Col>
				<Col span="8">
					<Card style="width:130px; margin:auto auto;">
						<div style="border-bottom:1px solid #eee;margin-bottom:0.5em;"><i class="iconfont font-size iconjixiao"></i></div>
						<p style="font-size:13px;">疫苗蓄种：鸡</p>
						<p style="font-size:13px;">疫苗总数：50</p>
						<p style="font-size:13px;">亚单位疫苗：20</p>
						<p style="font-size:13px;">活疫苗：15</p>
						<p style="font-size:13px;">灭活疫苗：15</p>
					</Card>
				</Col>
			</Row>
		</Modal>
	</div>
</template>

<script>
import {mapGetters,mapActions} from 'vuex'
// import { manager } from 'service/api'

import InforCard from 'components/info-card/infor-card.vue'
import CountTo from 'components/info-card/count-to.vue'
export default {
	name: 'vaccineManage',
	components: {
		InforCard,CountTo
	},
	props:{
		
	},
	data() {
		return {
			inforCardData: [
				{ title: '疫苗总数', icon: 'iconyimiao1', count: 803, color: '#2d8cf0' },
				{ title: '已下发疫苗总数', icon: 'icondazhen', count: 620, color: '#19be6b' },
				{ title: '未下发疫苗总数', icon: 'iconyimiao', count: 142, color: '#ff9900' },
				// { title: '', icon: 'md-share', count: 657, color: '#ed3f14' },
				// { title: '', icon: 'md-chatbubbles', count: 12, color: '#E46CBB' },
				// { title: '', icon: 'md-map', count: 14, color: '#9A66E4' }
			],
			vaccineDetailModal:false, //详情得modal
		}
	},
	computed: {
		...mapGetters([

		])
	},
	methods: {
		...mapActions([

		]),
		
		// 查看详情
		checkVaccineDetail(info){
			console.log('详情',info)
			this.vaccineDetailModal = true
		},
	
	},

	mounted() {

	},

	watch: {

	}
}
</script>

<style lang="less" scoped>
.vaccineManage{
	width: 95%;
	margin: auto auto;
	padding-top: 2em;
	.count-style{
		font-size:50px;
	}
	.detail-info{
		font-size: 13px;
		color: #a2c4c9;
		margin-top:0.3em;
		cursor: pointer;
	}
	.flex-style{
		display: flex;
		flex-direction:column;
	}

}
.font-size{
		font-size:2em;
	}
</style>

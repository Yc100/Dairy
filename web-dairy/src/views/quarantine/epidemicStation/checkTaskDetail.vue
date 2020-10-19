// author：zhangb
// time:2020-10-13
// desc:本页面为查看任务详情

<template>
	<div class="checkTaskDetail">
		
		<span><i class="iconfont iconrenwuxiangqing"></i> 任务详情</span>
		
		<!-- 内容 -->
		<div class="card-border">
			<Card style="width:70%;margin:auto auto;" >
				<p><b>{{taskDetail.taskName}}</b></p>
				<div class="font-detail">
					<p class="font-info">{{taskDetail.content}}</p>
				</div>

				<div class="card-note">
					<div>优先级等级：<span style="color:red;">{{taskDetail.level | level}}</span></div>
					<div>起止时间：<span style="color:orange;">{{taskDetail.beginDate}}  至  {{taskDetail.endDate}}</span></div>
					<div>备注：{{taskDetail.remarks}}</div>
					<div>附件：
							<div class="imgRow">
									<div class="flexItem" v-for="(img,index) in taskDetail.imgList" :key="index">
										<h5>附件{{index+1}}:</h5>
										<img :src="img" @click="seeDetail(img)">
									</div>
							</div>
							<div class="fileRow">
									<div class="flexItem" v-for="(file,index) in taskDetail.fileList" :key="index">
											<a :href="file" download>{{file}}</a>
									</div>
							</div>
					</div>

					<div v-if="taskDetail.detailEntityList && taskDetail.detailEntityList.length>0">
					<div style="border-top:2px dotted #6d9eeb;margin: 2em 0;"></div>

						<div v-for="item in taskDetail.detailEntityList" :key="item.detailId">
							<Row>
									<Col span="2"><img :src="item.headUrl?item.headUrl:defaultHeadUrl" class="user-avatar" alt=""></Col>
									<Col span="3">
										<span> {{item.nickName}}</span>
									</Col>
									<Col span="3">
											<Tag color="error" style="border-radius:10px;">
												{{item.roleName}}
											</Tag>
									</Col>
									<Col span="3">
											<i class="el-icon-phone" style="margin-right:5px"></i>
											{{item.mobile}}
									</Col>
								</Row>
							<Row>
									<Col offset="2" span="16" style="position:relative">
										<div>
											<Steps :current="item.status" direction="vertical">
												<Step :title="step1(item)"></Step>
												<Step :title="step2(item)">
												</Step>
												
												<Step v-if="item.checkedStatus !== 0" title="申请顺延" :content="step3(item)">
												</Step>
											</Steps>
													<div v-if="item.checkedStatus !== 0" >
														<span @click="applyCheck(item)" 
													style="color:#2D8CF0;margin-left:20px;cursor:pointer">申请详情...</span>
													<span :class="'staText'+item.checkedStatus">
              ({{item.checkedStatus | checkedStatus}})
            </span>
													</div>

										</div>
											<div class="finishText" v-if="item.status == 2" >
													<span @click="finishCheck(item)" 
													style="color:#2D8CF0;margin-left:20px;cursor:pointer">完成详情...</span>
											</div>
														<Button v-if="item.status == 1" type="warning" @click="tipWork(item)">提醒工作</Button>
									</Col>
									<Col span="2">
										<div class="status">
												<img v-if="item.status == 1" :src="waitSucceed" alt="">
												<img v-if="item.status == 2 " :src="succeed" alt="">
												<img v-if="item.status == 3" :src="overTime" alt="">
										</div>
									</Col>
								</Row>
						</div>
							
					</div>
				</div>
			</Card>
		</div>
		
				<!-- 申请顺延弹出层 -->
    <Modal v-model="applyModel" title="申请详情" width="40%">
          <!-- 表单 -->
          <Form>
											<Row>
            <b>顺延至：</b>
												<span>{{applyDetail.deferredTime}}</span>
											</Row>
										<Row>
											<b>附件：</b>
												<div class="imgRow">
														<div class="flexItem" v-for="(img,index) in deferredList" :key="index">
															<h5>附件{{index+1}}:</h5>
															<img :src="img" width="200px" @click="seeDetail(img)">
														</div>
												</div>
												<div class="fileRow">
														<div class="flexItem" v-for="(file,index) in deferredFile" :key="index">
																<a :href="file" download>{{file}}</a>
														</div>
												</div>
												<span v-if="deferredList.length == 0 && deferredFile.length == 0">
														未上传附件~
												</span>
										</Row>
										<Row>
            <b>延期原因：</b>
												<span>{{applyDetail.deferredReason}}</span>
											</Row>
											<Row>
												<p><b>备注：</b>{{applyDetail.deferredRemark}}</p>
											</Row>

          </Form>
							<div slot="footer" class="subBtn">
									<div v-if="applyDetail.checkedStatus === 1">
										<Button type="error" @click="rejectDelay">
											拒绝
									</Button>
									<Button type="success" @click="resolveDelay">
											同意
									</Button>
									</div>
									<div v-else>
										<Button type="primary" @click="applyModel = false">
											关闭
									</Button>
									</div>
							</div>
      </Modal>
		
			<!-- 完成情况弹出框 -->
    <Modal v-model="finishModel" title="完成详情" width="40%">
          <!-- 表单 -->
          <Form>
										<Row>
											<b>附件：</b>
												<div class="imgRow">
														<div class="flexItem" v-for="(img,index) in finishList" :key="index">
															<h5>附件{{index+1}}:</h5>
															<img :src="img" width="200px" @click="seeDetail(img)">
														</div>
												</div>
												<div class="fileRow">
														<div class="flexItem" v-for="(file,index) in finishFile" :key="index">
																<a :href="file" download>{{file}}</a>
														</div>
												</div>
												<span v-if="finishList.length == 0 && finishFile.length == 0">
														未上传附件~
												</span>
										</Row>
											<Row>
												<p><b>备注：</b>{{finishDetail.remarks || '无'}}</p>
											</Row>

          </Form>
							<div slot="footer" class="subBtn">
									<div>
										<Button type="primary" @click="finishModel = false">
											关闭
									</Button>
									</div>
							</div>
      </Modal>
		
		
	</div>
</template>

<script>
import api from 'service/apiConfig' //真实接口配置
import {getWorkTaskDetail,addWorkMsg,checkDeferre  } from 'service/api'
import {mapGetters,mapActions} from 'vuex'
export default {
	name: 'checkTaskDetail',
	components: {

	},
	props:{
		
	},
	data() {
		return {
			accept:'image/jpg,image/jpeg,image/png',  //上传图片类型
			taskId:this.$route.query.taskId,
			taskDetail:{},
			defaultHeadUrl:require('assets/imgs/avatar.png'),
			waitSucceed:require('assets/imgs/taskStatus/waitSucceed.png'),
			succeed:require('assets/imgs/taskStatus/succeed.png'),
			overTime:require('assets/imgs/taskStatus/overTime.png'),
			applyModel:false,
			applyDetail:{},
			deferredList:[],
			deferredFile:[],
			finishModel:false,
			finishDetail:{},
			finishList:[],
			finishFile:[]
		}
	},
	computed: {
		...mapGetters([
			'userInfo',        //用户信息
		]),
		step1() {
			return function(item){
				return `接受任务 ${item.createTime}`
			}
		},
		step2() {
			return function(item){
				return item.finishTime ? `完成任务 ${item.finishTime}` : '待完成'
			}
		},
		step3(){
			return function(item){
				return item.deferredTime ? `申请顺延 ${item.deferredTime}` : ''
			}
		}

	},
	methods: {
		...mapActions([

		]),
		async getDetail(){
			await getWorkTaskDetail({taskId:this.taskId}).then(res=>{
				if(res.code == 0){
					console.log(res.data);
					this.taskDetail = res.data
					if(this.taskDetail.fileUrls){
							let fileArr = this.taskDetail.fileUrls.indexOf('@_@') === -1?[this.taskDetail.fileUrls]:this.taskDetail.fileUrls.split('@_@')
							console.log(fileArr);
							this.taskDetail.imgList = []
							this.taskDetail.fileList = []
							fileArr.forEach(item=>{
									if(item.indexOf('.png') !==-1 ||item.indexOf('.jpg') !==-1 ||item.indexOf('.gif') !==-1 || item.indexOf('.webp') !==-1){
											this.taskDetail.imgList.push(item)
											return;
									}else{
											this.taskDetail.fileList.push(item)
									}
							})
					}
				}else{
					this.$Message.error(res.message || '系统错误')
				}
			})
		},
		//查看附件图片
		seeDetail(url){
			window.open(url)
		},
		// 时间格式化 2020-08-11  00:00:00
		timeFormat(time) {
				let year = time.getFullYear()
				let month = time.getMonth() + 1
				let day = time.getDate()
				let hour = time.getHours()
				let minute = time.getMinutes()
				if (month >= 1 && month <= 9) { month = `0${month}` }
				if (day >= 1 && day <= 9) { day = `0${day}` }
				if (hour >= 0 && hour <= 9) { hour = `0${hour}` }
				if (minute >= 0 && minute <= 9) { minute = `0${minute}` }
				return `${year}-${month}-${day} ${hour}:${minute}:00`
		},

		//提醒工作
	 async	tipWork(item){
			await addWorkMsg({infoId:item.detailId}).then(res=>{
				if(res.code == 0){
					this.$Message.success(res.message || '提醒成功')
					this.getDetail()
				}else{
					this.$Message.error(res.message || '系统错误')
				}
			})
		},
		applyCheck(item){
			this.applyModel = true
			this.applyDetail = item
			if(item.deferredUrls){
			let fileArr = item.deferredUrls.indexOf('@_@') === -1?[item.deferredUrls]:item.deferredUrls.split('@_@')
			console.log(fileArr);
			this.deferredList = []
			this.deferredFile = []
			fileArr.forEach(item=>{
					if(item.indexOf('.png') !==-1 ||item.indexOf('.jpg') !==-1 ||item.indexOf('.gif') !==-1 || item.indexOf('.webp') !==-1){
							this.deferredList.push(item)
							return;
					}else{
							this.deferredFile.push(item)
					}
			})
			}
			
		},
		finishCheck(item){
				this.finishModel = true
				this.finishDetail = item
				if(item.fileUrls){
				let fileArr = item.fileUrls.indexOf('@_@') === -1?[item.fileUrls]:item.fileUrls.split('@_@')
				console.log(fileArr);
				this.finishList = []
				this.finishFile = []
				fileArr.forEach(item=>{
						if(item.indexOf('.png') !==-1 ||item.indexOf('.jpg') !==-1 ||item.indexOf('.gif') !==-1 || item.indexOf('.webp') !==-1){
								this.finishList.push(item)
								return;
						}else{
								this.finishFile.push(item)
						}
				})
				}
		},
		//同意申请顺延
		resolveDelay(){
					this.$confirm('确定同意顺延申请吗?', '提示', {
							confirmButtonText: '确定',
							cancelButtonText: '取消',
							type: 'warning'
					}).then(async () => {
						let obj={
								checkedStatus:2,
								detailId:this.applyDetail.detailId
						}
						await checkDeferre(obj).then(res=>{
								if(res.code == 0){
										this.applyModel = false
										this.$Message.success(res.message || '提交成功')
										this.getDetail()
								}else{
										this.$Message.error(res.message || '系统错误')
								}
							})
					}).catch(() => {
							this.$message({
									type: 'info',
									message: '已取消'
							});          
					});
				},
				//拒绝申请顺延
    rejectDelay(){
      this.$prompt('请输入拒绝原因','拒绝原因', {
								confirmButtonText: '确定',
								cancelButtonText: '取消',
						}).then(async ({value})=>{
							let obj={
									checkedStatus:3,
									detailId:this.applyDetail.detailId,
									checkedReason:value
							}
							await checkDeferre(obj).then(res=>{
								if(res.code == 0){
										this.applyModel = false
										this.$Message.success(res.message || '提交成功')
										this.getDetail()
								}else{
										this.$Message.error(res.message || '系统错误')
								}
							})
						}).catch(()=>{
							this.$message({
									type: 'info',
									message: '已取消'
							});  
						})
       
    },
		
	},

	mounted() {
		this.getDetail()

	},

	watch: {

	}
}
</script>

<style lang="less" scoped>
.checkTaskDetail{
	width: 95%;
	margin: auto auto;
	padding-top: 2em;
	.card-border{
		padding: 1em 0;
		.font-detail{
			margin-top:.5em;
			.font-info{
				text-indent: 0.5em;
				color: red;
			}
		}
		.card-note{
			margin-top: 1em;
			// text-indent: 0.5em;
			line-height: 200%;
		}
		.user-avatar{
			width: 3.5em; height: 3.5em;
			border-radius: 30px;
		}
	}
	
	.status{
		width: 80px;
		height: 80px;
		img{
			width: 100%;
			height: 100%;
			transform: rotate(30deg);
		}
	}
}
.imgRow{
		display: flex;
		align-items: top;
		img{
			width: 200px;
			height: auto;
			margin-top: 10px;
		}
	}
	.fileRow{
		display: flex;
		align-items: top;
	}
	.flexItem{
		margin-top: 10px;
		margin-left: 20px;
		display: inline-block;
		width: auto;
	}
	.staText1{
  color: #E1BA15;
}
.staText2{
  color: #14AC65;
}
.staText3{
  color: #F00909;
}
.finishText{
	position: absolute;
	top: 52px;
	left:260px ;
}
</style>

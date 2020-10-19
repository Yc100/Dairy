// author：jiana
// time:2020-09-30
// desc:本页面为岗位管理列表


<template>
	<div class="postList">
		<!-- 操作 -->
		<Row>
			<Col span="6">
				<Row>
					<Col span="16"><Input v-model="pageParams.name" icon="ios-search" placeholder="请输入岗位名称" style="" clearable /> </Col>
					<Col span="4" offset="2"><Button type="primary" @click="getlistSysRoles">搜索</Button></Col>
				</Row>
			</Col>
			<Col offset="15" span="2"><Button type="primary" @click="addNewPost">新增岗位</Button></Col>
		</Row>
		<br>

		<!-- 表格列表内容 -->
		<el-table :data="manageAreaListData.data" style="width: 100%">
			<el-table-column label="岗位名称" prop="roleName" ></el-table-column>
			<el-table-column label="岗位编码" prop="roleCode" ></el-table-column>
			<el-table-column label="操作" width="290px">
			<template slot-scope="scope">
				<Button type="warning" size="small" @click="lookPower(scope.row)">查看权限</Button>
				<Button type="primary" size="small" @click="editManageArea(scope.row)">编辑</Button>
				<Button type="error" size="small" @click="delManageArea(scope.row)">删除</Button>
			</template>
			</el-table-column>
		</el-table>	

		<!-- 分页 -->
		<div style="text-align:right;margin:15px 0;">
			<Page show-total :total="parseInt(manageAreaListData.total) || 0" :page-size="pageParams.limit" :current="pageParams.current" @on-change='changepage'></Page>
		</div>


		<!--编辑、新增岗位 -->
		<Modal v-model="addManageAreaModal" :title="titleName" width="50%">
			<Form ref="ManagAreaValidate" :model="ManagAreaValidate" :rules='ruleManagAreaValidate' :label-width="120">
				<FormItem label="岗位名称" prop="roleName">
					<Input v-model="ManagAreaValidate.roleName"  placeholder="请填写岗位名称" style="width:50%;"></Input>
				</FormItem>
				<FormItem label="岗位编码" prop="roleCode">
					<Input v-model="ManagAreaValidate.roleCode"  placeholder="请填写岗位编码" style="width:50%;"></Input>
				</FormItem>
				<FormItem label="权限列表" prop="result">
					<CheckboxGroup v-model="ManagAreaValidate.result">
						<Checkbox  v-for="item in postList" :key="item.modId" :label="item.modCode">
							<span>{{item.modName}}</span>
						</Checkbox>
					</CheckboxGroup>
				</FormItem>
			</Form>
			<div slot="footer" style="text-align:center;">
				<Button @click="addManageAreaModal=false" style="margin-left: 8px">取消操作</Button>
				<Button type="primary" @click="addNewPostConfirm">确定新增</Button>
			</div>
		</Modal>

		<!-- 查看权限 -->
		<Modal v-model="lookPowerModal" title="查看权限" width="30%">
			<div class="ullistBox">
				<ul class="ulStyle">
					<li v-for="item in powerList" :key='item.modId'>{{item.modName}}</li>
				</ul>
			</div>
		</Modal>
	</div>
</template>

<script>
import {mapGetters,mapActions} from 'vuex'
import {addSysRole,deleteSysRole,getById,updateSysRole,selectSysMods,listSysRoles,selectedMods} from 'service/api'
export default {
	name: 'postList',
	components: {

	},
	props:{
		
	},
	data() {
		return {
			pageParams:{   //分页参数
				limit:10,
				offset:0,
				current:1,
				name:'',
			},
			manageAreaListData:{
				data:[],
				total:''
			},
			editManageAreaModal:false, //编辑区域信息modal
			addManageAreaModal:false, //新增区域的信息modal
			lookPowerModal:false, //查看畜禽详情modal
			ManagAreaValidate:{
				roleName:'',
				roleCode:'',
				modCodes:'',
				result:[]
			},
			ruleManagAreaValidate:{
				roleName:[{ required: true, message: '请填写岗位名称', trigger: 'blur' }],
				roleCode:[{ required: true, message: '请填写岗位编码', trigger: 'blur' }],
				result:[{ required: true,type:'array', message: '请选择权限', trigger: 'change' }],
			},
			postList:[],
			powerList:[],
			sysRole:{roleId:''},
			titleName:'新增岗位'
		}
	},
	computed: {
		...mapGetters([

		])
	},
	methods: {
		...mapActions([

		]),
		
		// 查看权限
		async lookPower(row){
			this.lookPowerModal = true
			await selectedMods({roleId:row.roleId}).then(res=>{
				if(res.code === 0){
					this.powerList = res.data;
				}else{
					this.$vux.toast.show({
						type:'cancel',
						text:res.msg || '提交失败'
					})
				}
			}).catch(err=>{
				this.$vux.toast.show({
					type:'cancel',
					text:err || '提交失败'
				})
			})
		},
		// 新增岗位
		addNewPost(){
			this.addManageAreaModal = true
			this.titleName = '新增岗位'
			this.ManagAreaValidate = {
				roleName:'',
				roleCode:'',
				modCodes:'',
				result:[]
			}
		},
		// 确定新增、编辑
		addNewPostConfirm(){
			this.$refs['ManagAreaValidate'].validate(async(valid) => {
				if (valid) {
					this.ManagAreaValidate.modCodes = this.ManagAreaValidate.result.join(',')
					if(this.titleName == '新增岗位'){
						await addSysRole(this.ManagAreaValidate).then(res=>{
							if(res.code === 0){
								this.$Message.success('新增成功');
								this.getlistSysRoles();
							}else{
								this.$Message.error('新增失败！');
							}
						})
					}else{
						await updateSysRole(this.ManagAreaValidate).then(res=>{
							if(res.code === 0){
								this.$Message.success('编辑成功');
								this.getlistSysRoles();
							}else{
								this.$Message.error('编辑失败！');
							}
						})
					}
					this.addManageAreaModal = false
				} else {
					this.$Message.error('请检查各项内容是否填写完整！');
				}
			})
		},

		// 编辑某区域信息
		async editManageArea(row){
			this.titleName = '编辑岗位'
			await getById({roleId:row.roleId}).then(res=>{
				if(res.code === 0){
					this.ManagAreaValidate = res.data
					this.ManagAreaValidate.result = this.ManagAreaValidate.modCodes.split(',')
					console.log(this.ManagAreaValidate.result,'this.ManagAreaValidate.result')
					this.addManageAreaModal = true
				}else{
					this.$Message.error('获取失败！');
				}
			}).catch(err=>{
				this.$Message.error('获取失败！');
			})
		},
		
		// 删除岗位
		delManageArea(row){
			let _this = this
			_this.sysRole.roleId = row.roleId
			this.$Modal.confirm({
				title: '温馨提示',
				content: '<p>您确定删除该岗位吗？</p>',
				onOk:async() => {
					await deleteSysRole(_this.sysRole).then((res)=>{
						if(res.code == 0){
							_this.$Message.success(res.msg || '删除成功')
							_this.getlistSysRoles(); //重新获取列表
						}else{
							_this.$Message.error(res.msg || '删除失败')
						}
					})
				},
				onCancel: () => {
					_this.$Message.info('您已取消操作！');
				}
			});
		},

		//获取权限列表
		async getModels(){
			await selectSysMods().then(res=>{
				if(res.code === 0){
				this.postList = res.data;
				}else{
				this.$Message.error('获取失败！');
				}
			}).catch(err=>{
				this.$Message.error('获取失败！');
			})
		},
		//获取岗位列表
		async getlistSysRoles(){
			await listSysRoles(this.pageParams).then(res=>{
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
			this.getlistSysRoles();
		},
	},

	mounted() {

	},

	watch: {

	},
	created(){
		this.getModels()
		this.getlistSysRoles()
	}
}
</script>

<style lang="less" scoped>
.postList{
	width: 95%;
	margin: auto auto;
	padding-top: 2em;
}
.basicTitle{margin-left:50px;}
.ullistBox{padding: 10px 20px 0;}
.ulStyle{padding: 0 10px;}
</style>

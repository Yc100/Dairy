// author：zhangb
// time:2020-08-20
// desc:畜牧局管理畜禽的三级分类

<template>
	<div class="animalClassify">
		<Row>
			<!-- <Col span="12">
				<Select style="width:200px">
					<Option v-for="item in cityList" :value="item.value" :key="item.value">{{ item.label }}</Option>
				</Select>
			</Col> -->
			<Col span="12">
				<Button type="primary" @click="addSort">新增一级分类</Button>
			</Col>
		</Row>
		
		<!-- 树列表 -->
		<div class="tree-style">
			<el-tree :data="sortList" :props="defaultProps" node-key="kindId" :expand-on-click-node="false" style="width:90%;">
			<span slot-scope="{ node, data }" style="width:90%;">
				<Row>
					<Col span="8"><div class="tree-height">{{ node.label }}</div></Col>
					<Col span="8"><div class="tree-height">当前序号{{data.sort}}</div></Col>
					<Col span="8">
						<el-button type="text" size="mini" @click="() => addSort(data)">增加下级分类</el-button>
						<el-button type="text" size="mini" @click="() => editNode(data)">编辑</el-button>
						<el-button type="text" size="mini" @click="() => delNode(data)">删除</el-button>
					</Col>
				</Row>

			</span>
			</el-tree>
		</div>

		<!-- 新增一级分类标签 -->
		<Modal v-model="addNewOneModel" :title="edit?'编辑分类':'新增分类'" width="28%">
			<Tag v-if="!firstGrade" color="warning" style="margin:1em 0; margin-bottom:2em;">所属上级：{{upGrade}}</Tag>
			<Form ref="newFirstClaValidate" :model="newFirstClaValidate" :rules="ruleNewClaValidate" :label-width="80">
				<FormItem label="分类名称" prop="name">
					<Input v-model="newFirstClaValidate.name" placeholder="请输入畜禽分类名称" style="width:90%;"></Input>
				</FormItem>
				<FormItem label="当前排序" prop="sort">
					<Input v-model="newFirstClaValidate.sort" placeholder="请输入畜禽排序" style="width:90%;"></Input>
				</FormItem>
			</Form>
			<div slot="footer" style="text-align:center;">
				<Row>
					<Col span="12"><Button @click="addNewOneModel = false">取消操作</Button></Col>
					<Col span="12"><Button type="success" @click="submitNewClassify">确定提交</Button></Col>
				</Row>
			</div>
		</Modal>

	</div>
</template>

<script>
import {listLivestockKinds,addLivestockKind,updateLivestockKind,deleteLivestockKind} from 'service/api'
import {mapGetters,mapActions} from 'vuex'
export default {
	name: 'animalClassify',
	components: {
		
	},
	props:{
		
	},
	data() {
		return {
			cityList:[],
			addNewOneModel:false,
			sortList:[],
			defaultProps:{
					children: 'kindEntityList',
					label: 'name',
			},

			newFirstClaValidate:{
				name:'',
				sort:'',
			},

			ruleNewClaValidate:{
				name:[{required: true, message: '请填写一级分类', trigger: 'blur'}],
				sort:[{required: true, message: '请填写当前排序下标', trigger: 'blur'}]
			},
			firstGrade:false,   //增加非一级分类
			edit:false,
			upGrade:'无'
		}
	},
	computed: {
		...mapGetters([

		])
	},
	methods: {
		...mapActions([

		]),
		
		// 增加一级分类提交
		submitNewClassify(){
			this.$refs['newFirstClaValidate'].validate(async (valid) => {
				if (valid) {
					if(this.edit){
						await updateLivestockKind(this.newFirstClaValidate).then(res=>{
							if(res.code == 0){
								this.$Message.success(res.message || '提交成功');
								this.addNewOneModel = false
								this.getList()
							}else{
								this.$Message.error(res.message || '系统错误');
							}
						})
					}else{
						await addLivestockKind(this.newFirstClaValidate).then(res=>{
							if(res.code == 0){
								this.$Message.success(res.message || '提交成功');
								this.addNewOneModel = false
								this.getList()
							}else{
								this.$Message.error(res.message || '系统错误');
							}
						})
					}
				} else {
					this.$Message.error('请填写分类内容');
				}
			})
		},

		//获取畜禽分类列表数据
		async getList(){
			await listLivestockKinds().then(res=>{
				if(res.code == 0){
					this.sortList = res.data
					// console.log('this.sortList',this.sortList);
				}else{
					this.$Message.error(res.message || '查询失败!')
				}
			})
		},
		//编辑
		editNode(item){
			this.newFirstClaValidate = JSON.parse(JSON.stringify(item))
			this.edit = true
			this.addNewOneModel = true
			if(item.parentName){
					this.upGrade = item.parentName
			}
		},
		//删除
		delNode(item){
			console.log(item);
			this.$confirm('确定删除当前分类吗?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
			}).then(async () => {
				await deleteLivestockKind({kindId:item.kindId}).then(res=>{
					if(res.code == 0){
							this.$Message.success(res.message || '删除成功');
							this.getList()
					}else{
						this.$Message.error(res.message || '系统错误')
					}
				})
			}).catch(() => {
					this.$message({
							type: 'info',
							message: '已取消删除'
					});          
			});
		},
		//增加一级分类
		addSort(item){
			this.addNewOneModel = true
			this.firstGrade = true
			if(item.parentId){
				this.newFirstClaValidate.parentId = item.parentId
			}else{
				this.newFirstClaValidate.parentId = 0
			}
		}
	},

	mounted() {
		this.getList()
	},

	watch: {

	}
}
</script>

<style lang="less" scoped>
.animalClassify{
	padding: 2em 2em;
	.tree-style{
		margin-top: 3em;
		.tree-height{ height: 2em; line-height: 2.1em;}
	}
}
</style>

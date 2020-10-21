<template>
    <div v-loading="loading">
        <div style="margin-top: 20px;margin-bottom: 10px; display: flex;justify-content: center">
            <el-button  type="primary" plain  size="small" style="margin-right: 10px"  @click="addUser">添加用户</el-button>
            <el-input
                    placeholder="默认展示部分用户，可以通过用户名搜索用户..."
                    prefix-icon="el-icon-search"
                    v-model="pageParams.nickName" style="width: 400px" size="small">
            </el-input>
            <el-button  plain icon="el-icon-search" size="small" style="margin-left: 3px" @click="searchClick">搜索
            </el-button>
        </div>
        <div style="display: flex;justify-content: space-around;flex-wrap: wrap">
            <el-card style="width:330px;margin-top: 10px;" v-for="(user,index) in users" :key="index"
                     v-loading="cardloading[index]">
                <div slot="header" style="text-align: left">
                    <span>{{user.nickName}}</span>
                    <el-button style="float: right; padding: 3px 0;color: #ff0509" type="text" icon="el-icon-delete"
                               @click="deleteUser(user.id)">删除
                    </el-button>
                </div>
                <div>
                    <div><img :src="user.headUrl" :alt="user.nickName" style="width: 70px;height: 70px"></div>
                    <div style="text-align: left;color:#20a0ff;font-size: 12px;margin-top: 13px">
                        <span>用户名:</span><span>{{user.username}}</span>
                    </div>
                    <div style="text-align: left;color:#20a0ff;font-size: 12px;margin-top: 13px">
                        <span>电子邮箱:</span><span>{{user.email}}</span>
                    </div>
                    <div style="text-align: left;color:#20a0ff;font-size: 12px;margin-top: 13px">
                        <span>注册时间:</span><span>{{user.createDate}}</span>
                    </div>
                    <div
                            style="text-align: left;color:#20a0ff;font-size: 12px;margin-top: 13px;display: flex;align-items: center">
                        <span>用户状态:</span>
                       <!-- <el-tooltip :content="'Switch status: ' + user.status===1?'已启用':'已停用'" placement="top">-->
                        <el-switch

                                v-model="user.status"
                                active-text="启用"
                                active-color="#13ce66"
                                @change="enabledChange(user.status,user.id,index)"
                                inactive-text="禁用" style="font-size: 12px"
                                :active-value="1"
                                :inactive-value="0">
                        </el-switch>
                        <!--</el-tooltip>-->
                    </div>
                </div>
            </el-card>
            <div style="text-align:right;margin:15px 0;">
                <Page show-total :total="parseInt(tableData.total) || 0" :page-size="pageParams.limit" :current="pageParams.current" @on-change='changepage'></Page>
            </div>
        </div>
        <!--新增区域信息 -->
        <Modal v-model="addUserModal" title="添加用户" width="50%">
            <Form ref="userNew" :model="userNew" :rules="validateUserNew" :label-width="100">
                <FormItem label="用户名" prop="username">
                    <Input v-model="userNew.username"  placeholder="请填写用户名" style="width:96%;"></Input>
                </FormItem>
                <FormItem label="密码" prop="password">
                    <Input v-model="userNew.password" type="password"  placeholder="请填写密码" style="width:96%;"></Input>
                </FormItem>
                <FormItem label="昵称" prop="nickName">
                    <Input v-model="userNew.nickName"  placeholder="请填写昵称" style="width:96%;"></Input>
                </FormItem>
                <FormItem label="头像" prop="headUrl">
                    <el-upload
                            class="avatar-uploader"
                            action="https://www.mocky.io/v2/5185415ba171ea3a00704eed/posts/"
                            :show-file-list="false"
                            :on-success="handleAvatarSuccess"
                            :before-upload="beforeAvatarUpload">
                        <img v-if="userNew.headUrl" :src="userNew.headUrl" style="width: 100px; height: 100px" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                    <!--<el-image style="width: 100px; height: 100px":src="userNew.headUrl"></el-image>-->
                </FormItem>
                <FormItem label="性别" prop="gender">
                    <el-radio v-model="userNew.gender" :label="0">男</el-radio>
                    <el-radio v-model="userNew.gender" :label="1">女</el-radio>
                    <el-radio v-model="userNew.gender" :label="2">保密</el-radio>
                </FormItem>
                <FormItem label="邮箱" prop="email">
                    <Input v-model="userNew.email"  placeholder="请填写邮箱" style="width:96%;"></Input>
                </FormItem>
                <FormItem label="电话号码" prop="mobile">
                    <Input v-model="userNew.mobile"  placeholder="请填写电话号码" style="width:96%;"></Input>
                </FormItem>
            </Form>
            <div slot="footer" style="text-align:center;">
                <Button @click="addUserModal=false" style="margin-left: 8px">取消操作</Button>
                <Button type="primary" @click="createUser('userNew')">确定新增</Button>
            </div>
        </Modal>
    </div>


</template>

<script>
    import {mapGetters,mapActions} from 'vuex'
    import api from 'service/apiConfig'
    import axios from 'axios'
    import {getUserlistPage,createUser,deleteUser} from 'service/api'
export default {
	name: 'userManage',
	components: {

	},
	props:{
		
	},
	data() {
		return {
            pageParams:{
                limit:5,
                offset:0,
                current:1,
                nickName:'',
            },
            loading: false,
            keywords: '',
            users: [],
            userNew:{
                username:'',
                password:'',
                nickName:'',
                headUrl:'',
                gender:0,
                email:'',
                mobile:'',
            },
            validateUserNew:{
                username:[
                    {required:true,message:'请输入用户名',trigger:'blur'}
                ],
                password:[
                    {required:true,message:'请输入密码',trigger:'blur'}
                ],
                nickName:[
                    {required:true,message:'请输入昵称',trigger:'blur'}
                ],
            },
            tableData: {},
            cardloading: [],
            addUserModal:false
		}
	},
	computed: {

	},
	methods: {
        ...mapActions([

        ]),
        async loadUserList(){
            let params ={}
            params.page=1
            params.limit=10

            await getUserlistPage(this.pageParams).then((res) => { //获取用户信息
                this.loading = false;
                if(res.code == 0){ //检测是否登录成功
                    this.users=res.data.list
                    this.tableData=res.data
                }else{
                    this.$Message.error(err.msg);
                }
            }).catch(err=>{
                this.loading = false;
                this.$Message.error(err.msg);
            })
        },
        searchClick(){
            this.loading = true;
            this.pageParams.offset = 0
            this.pageParams.limit=5
            this.pageParams.current=1
            this.loadUserList();
        },

        enabledChange(enabled, id, index){
            alert(enabled)
            /*var _this = this;
            _this.cardloading.splice(index, 1, true)
            putRequest("/admin/user/enabled", {enabled: enabled, uid: id}).then(resp=> {
                if (resp.status != 200) {
                    _this.$message({type: 'error', message: '更新失败!'})
                    _this.loadOneUserById(id, index);
                    return;
                }
                _this.cardloading.splice(index, 1, false)
                _this.$message({type: 'success', message: '更新成功!'})
            }, resp=> {
                _this.$message({type: 'error', message: '更新失败!'})
                _this.loadOneUserById(id, index);
            });*/
        },

        changepage(index){
            this.pageParams.offset = (index-1)*this.pageParams.limit;
            this.loadUserList();
        },

        addUser(){
            this.userNew={
                    username:'',
                    password:'',
                    nickName:'',
                    headUrl:'',
                    gender:0,
                    email:'',
                    mobile:'',
            },
          this.addUserModal=true
        },
        handleAvatarSuccess(res, file) {
            this.userNew.headUrl = URL.createObjectURL(file.raw);
        },
        beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg';
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPG) {
                this.$message.error('上传头像图片只能是 JPG 格式!');
            }
            if (!isLt2M) {
                this.$message.error('上传头像图片大小不能超过 2MB!');
            }
            return isJPG && isLt2M;
    },
        async createUser(name){
            this.$refs[name].validate(async(valid) => {
                if (valid) {
                    await createUser(this.userNew).then((res) => { //获取用户信息
                        if(res.code == 0){ //检测是否登录成功
                            this.addUserModal=false
                            this.loadUserList()
                        }else{
                            this.$Message.error(err.msg);
                        }
                    }).catch(err=>{
                        this.loading = false;
                        this.$Message.error(err.msg);
                    })
                }else{
                    this.$Message.error('请检查各项内容是否填写完整！');
                }
            })
        },
        async deleteUser(id){
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                let params={
                    'userId':id
                }
                 deleteUser(params).then((res) => { //获取用户信息
                    if(res.code == 0){ //检测是否登录成功
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                        this.loadUserList()
                    }else{
                        this.$Message.error(err.msg);
                    }
                }).catch(err=>{
                    this.loading = false;
                    this.$Message.error(err.msg);
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        }

	},

	mounted() {
        this.loading = true;
        this.loadUserList();
        this.cardloading = Array.apply(null, Array(20)).map(function (item, i) {
            return false;
        });

	},

	watch: {

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
</style>

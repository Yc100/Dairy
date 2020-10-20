<template>
    <div v-loading="loading">
        <div style="margin-top: 10px;display: flex;justify-content: center">
            <el-input
                    placeholder="默认展示部分用户，可以通过用户名搜索用户..."
                    prefix-icon="el-icon-search"
                    v-model="pageParams.nickName" style="width: 400px" size="small">
            </el-input>
            <el-button type="primary" icon="el-icon-search" size="small" style="margin-left: 3px" @click="searchClick">搜索
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
                        <el-tooltip :content="'Switch status: ' + user.status" placement="top">
                        <el-switch

                                v-model="user.status"
                                active-text="启用"
                                active-color="#13ce66"
                                @change="enabledChange(user.status,user.id,index)"
                                inactive-text="禁用" style="font-size: 12px"
                                :active-value="1"
                                :inactive-value="0">
                        </el-switch>
                        </el-tooltip>
                    </div>
                </div>
            </el-card>
            <div style="text-align:right;margin:15px 0;">
                <Page show-total :total="parseInt(tableData.total) || 0" :page-size="pageParams.limit" :current="pageParams.current" @on-change='changepage'></Page>
            </div>
        </div>
    </div>
</template>

<script>
    import {mapGetters,mapActions} from 'vuex'
    import api from 'service/apiConfig'
    import axios from 'axios'
    import {getUserlistPage} from 'service/api'
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
            tableData: {},
            cardloading: [],
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

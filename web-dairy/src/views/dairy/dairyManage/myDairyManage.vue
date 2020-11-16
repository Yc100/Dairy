<template>
    <div v-loading="loading" class="dairyManage">
        <div class="manageTop">
            <el-input
                    placeholder="可以通过标题搜索Dairy..."
                    prefix-icon="el-icon-search"
                    v-model="pageParams.title" style="width: 400px" size="small">
            </el-input>
            <el-button type="primary" plain icon="el-icon-search" size="small" style="margin-left: 3px" @click="searchClick">搜索
            </el-button>
        </div>
        <el-table :data="tableData.list" style="width: 100%" border >
            <el-table-column label="标题" >
                <template slot-scope="scope">
                    <span>{{scope.row.title}}</span>
                </template>
            </el-table-column>
            <el-table-column label="文章描述">
                <template slot-scope="scope">
                    <span>{{scope.row.dairyDescribe}}</span>
                </template>
            </el-table-column>
            <el-table-column label="创建时间" >
                <template slot-scope="scope">
                    <span>{{scope.row.createDate |formatDateTime }}</span>
                </template>
            </el-table-column>
            <el-table-column label="用户" >
                <template slot-scope="scope">
                    <!--<el-image style="width: 100px; height: 100px":src="scope.row.headUrl"></el-image>-->
                    <el-avatar :size="30" :src="scope.row.headUrl"></el-avatar>
                    <span>{{scope.row.nickName}}</span>
                </template>
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <!--<Button type="primary" size="small" >查看</Button>-->
                    <el-button  size="small" icon="el-icon-zoom-in" @click="detail(scope.row.id)">查看</el-button>
                    <el-button  size="small" icon="el-icon-zoom-in" @click="edit(scope.row.id)">编辑</el-button>
                </template>
            </el-table-column>
        </el-table>

        <div style="text-align:right;margin:15px 0;">
            <Page show-total :total="parseInt(tableData.total) || 0" :page-size="pageParams.limit" :current="pageParams.current" @on-change='changepage'></Page>
        </div>
    </div>


</template>

<script>
    import {mapGetters,mapActions} from 'vuex'
    import api from 'service/apiConfig'
    import axios from 'axios'
    import {getDairyList} from 'service/api'
export default {
	name: 'myDairyManage',
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
                title:''
            },
            loading: false,
            keywords: '',
            tableData: {},
		}
	},
	computed: {

	},
	methods: {
        ...mapActions([

        ]),
        async loadTableList(){
            let params ={}
            params.page=1
            params.limit=10
            this.loading = false;
            await getDairyList(this.pageParams).then((res) => { //获取用户信息
                this.loading = false;
                if(res.code == 0){ //检测是否登录成功
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
            this.loadTableList();
        },

        enabledChange(enabled, id, index){
            alert(enabled)
        },

        changepage(index){
            this.pageParams.offset = (index-1)*this.pageParams.limit;
            this.loadTableList();
        },
        detail(id){
            this.$router.push({
                   path: '/dairyDetail',
                  query: {
                    dairyId: id
                  }
            })
        },
        edit(id){
            this.$router.push({
                   path: '/editDairy',
                  query: {
                    dairyId: id
                  }
            })
        }

	},

	mounted() {
        this.loading = true;
        this.loadTableList();

	},

	watch: {

	}
}
</script>

<style lang="less" scoped>
    .dairyManage{
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

    .manageTop{
        margin-bottom: 10px;
        display: flex;
        justify-content: center;
    }
</style>

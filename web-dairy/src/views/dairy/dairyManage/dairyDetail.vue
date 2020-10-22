<template>
    <div v-loading="loading" class="dairyManage">
        <h1 class="title-article" >{{tableData.title}}</h1>
        <div class="article-info-box">
            <div class="article-bar-top">
                <!--<el-image  :src="tableData.sysUserEntity.headUrl"></el-image>-->
                <el-avatar class="article-type-img" shape="square" :src="tableData.sysUserEntity.headUrl"></el-avatar>
                <div class="bar-content">
                    <!--_江南一点雨-->
                    <a class="follow-nickName" href="#" target="_blank" rel="noopener">{{tableData.sysUserEntity.nickName}}</a>
                    <span class="time">{{tableData.createDate}}</span>
                    <Icon type="md-eye" />
                    <span class="read-count">{{tableData.readCount}}</span>
                </div>
            </div>
            <div class="blog-tags-box">
                <div class="tags-box artic-tag-box">
                    <span class="label">分类专栏：</span>
                    <a class="tag-link" href="#" target="_blank" rel="noopener">默认</a>
                    <span class="label">文章标签：</span>
                    <a class="tag-link" href="#l" target="_blank" rel="noopener">默认</a>
                </div>
            </div>
        </div>

        <div class="post-content-container"  v-if="tableData" v-html="tableData.mainText"></div>
    </div>


</template>

<script>
    import {mapGetters,mapActions} from 'vuex'
    import api from 'service/apiConfig'
    import axios from 'axios'
    import {getDairyDetail,readDairy} from 'service/api'
export default {
	name: 'dairyDetail',
	components: {

	},
	props:{

	},
	data() {
		return {
            loading: false,
            dairyId: '',
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
            /*await getDairyList(this.pageParams).then((res) => { //获取用户信息
                this.loading = false;
                if(res.code == 0){ //检测是否登录成功
                    this.tableData=res.data
                }else{
                    this.$Message.error(err.msg);
                }
            }).catch(err=>{
                this.loading = false;
                this.$Message.error(err.msg);
            })*/
        },


        async getDairyDetail(id){
            await getDairyDetail({dairyId:id}).then((res) => { //获取用户信息
                this.loading = false;
                if(res.code == 0){ //检测是否登录成功
                    this.tableData=res.data
                    this.readDairy(this.tableData.id)
                }else{
                    this.$Message.error(err.msg);
                }
            }).catch(err=>{
                this.loading = false;
                this.$Message.error(err.msg);
            })
        },

        async readDairy(dairyId){
            await readDairy({dairyId:dairyId}).then((res) => { //获取用户信息
                if(res.code == 0){ //检测是否登录成功

                }else{
                    this.$Message.error(err.msg);
                }
            }).catch(err=>{
                this.$Message.error(err.msg);
            })
        }

	},

	mounted() {
        this.dairyId=this.$route.query.dairyId
        this.getDairyDetail(this.$route.query.dairyId)

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
    .post-content-container {
        word-break: break-word;
        line-height: 28px;
        font-weight: 400;
        font-size: 20px;
        padding-bottom: 40px;
        margin:20px 0;
        color: #333;
    }
</style>
<style lang="less">

    .post-content-container{
        img{
            width: 100% !important;
        }
        ul li{
            list-style-type: disc;
            margin: 8px 0 0 32px;
        }
        p{
            font-size: 18px;
            color: #4d4d4d;
            font-weight: 400;
            line-height: 26px;
            margin: 20px 0 16px 0;
        }

        h2{
            font-size: 24px;
            line-height: 32px;
        }
    }
    .article-type-img {
        width: 36px;
        height: 32px;
        margin-right: 12px;
        line-height: 32px;
    }
    .title-article {
        font-size: 28px;
        word-wrap: break-word;
        color: #222226;
        font-weight: 600;
        margin: 0;
        word-break: break-all;
    }
    .article-bar-top {
        color: #999aaa;
        width: 88%;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
    }
    .article-info-box {
        margin-top: 10px;
        position: relative;
        background: #e6e6ec;
        border-radius: 4px;
    }
    .follow-nickName{
        color: #5893c2;
        margin-right: 20px;
    }
    .tag-link{
        color: #5893c2;
        margin-right: 20px;
    }
    .time{
        margin-right: 12px;
    }
</style>

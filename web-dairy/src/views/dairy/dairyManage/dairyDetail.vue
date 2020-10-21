<template>
    <div v-loading="loading" class="dairyManage">
        <div class="post-content-container"  v-if="tableData" v-html="tableData.mainText"></div>

    </div>


</template>

<script>
    import {mapGetters,mapActions} from 'vuex'
    import api from 'service/apiConfig'
    import axios from 'axios'
    import {getDairyDetail } from 'service/api'
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
                    console.log(this.tableData.mainText)
                }else{
                    this.$Message.error(err.msg);
                }
            }).catch(err=>{
                this.loading = false;
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
    }
</style>

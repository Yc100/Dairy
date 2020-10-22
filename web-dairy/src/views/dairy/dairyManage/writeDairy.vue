<template>
    <div v-loading="loading" class="dairyManage">
        <div>
           <!-- <Ueditor @ready="editorReady" style="display:inline-block;width: 800px; height: 100%; margin: 0px auto;"></Ueditor>-->
            <Ueditor @ready="editorReady" style="margin: 0px auto; height:800px;"></Ueditor>
        </div>
    </div>
</template>

<script>
    import {mapGetters,mapActions} from 'vuex'
    import api from 'service/apiConfig'
    import axios from 'axios'
    import {getDairyDetail,readDairy} from 'service/api'
    import Ueditor from 'components/editor/Ueditor'
export default {
	name: 'writeDairy',
    components: {
        Ueditor
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
        //ueditor编辑模板
        async editorReady(instance) {
            this.editorReadyInstance = instance
            if(!this.dataDone || !instance){
                return false
            }
            this.ueEditorInstance = instance
            setTimeout(function (){
                // console.log("editorReady",_.cloneDeep(this.item.content) )
               /* instance.setContent(_.cloneDeep(this.item.content) )
                instance.addListener('contentChange', () => {
                    this.item.content = instance.getContent();      // 获取内容方法
                })*/
            }.bind(this), 20)
        },






	},

	mounted() {


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

</style>
<style lang="less">

</style>

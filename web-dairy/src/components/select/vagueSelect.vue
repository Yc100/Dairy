<template lang="html">
  <span>
      <Select  v-if="this.func=='shop'"  v-model="selectVal" placeholder="请输入供应商名称" :label-in-value="true" filterable remote clearable :remote-method="searchList" :loading="loading" @on-change="selectionChange">
        <Option v-for="(data,index) in list" :label="data.name || data.orgName" :value="index" :key="data.id">{{data.name || data.orgName}}</Option>
      </Select>
      <Select v-if="this.func=='org'"  v-model="selectVal" placeholder="请输入牧场名称" :label-in-value="true" filterable remote clearable :remote-method="searchList" :loading="loading" @on-change="selectionChange">
        <Option v-for="(data,index) in list" :label="data.name || data.orgName" :value="index" :key="data.id">{{data.name || data.orgName}}</Option>
      </Select>
  </span>
</template>

<script>
// import filter from 'array-filter'
// import {ChinaAddressV3Data} from 'vux'
// import {finance,shop,user} from 'service/api'

export default {
  name:'vagueSelect',
  model: {
    prop: 'selected',
    event: 'change'
  },
  props: {
    func:'',
    selected: ''
  },
  data: function(){
    return {
        loading : false,  //搜索时，是否还在加载中（远程搜索时的加载状态）
        selectVal : '',
        list : [],
        placeholder:""
    }
  },
  mounted () {

  },
  methods: {
      //清理数据
      clearData(){

      },
      //获取数据
      async getData(query) {
          let res
          if (this.func == "shop") {
            res = await shop.GetShopList({
                name: query
              }) //调用搜索供应商接口
          } else if (this.func == "org") {
            res = await user.searchOrgRanchlist({
              ranchName: query
            })
          }
          this.loading = false
          if (res.code == '101') {
            this.list = res.data ? res.data : []
          } else {
            this.list = []
            this.$Message.error('获取供应商列表失败，请重试')
          }
        },
        //远程搜索供应商(自动补全)
      searchList(query) {
        if (query == '') {
          // this.list = [];
          return
        }
        this.loading = true
        setTimeout(() => {
            this.getData(query)
        }, 200);
      },
      //下拉框改变时触发事件
      async selectionChange(val){
        // this.selectVal = val.value?val.value:''
        // console.log(this.selectVal)
        let data
        if(this.selectVal===''){
            data={
              func:this.func,
              id:'',
              name:'',
              orgName:''
            }
        }else{
            data = this.list[this.selectVal],
            data.func = this.func
        }
        this.$emit("change", data)
      },
      mounted(){
        // this.placeholder = "请输入供应商名称"
      }
  }
}
</script>

<style lang="less" scoped>

</style>

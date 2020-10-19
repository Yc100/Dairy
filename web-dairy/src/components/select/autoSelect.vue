<template lang="html">
    <Select  v-model="selectVal" :label-in-value="true" filterable remote clearable :remote-method="searchList" :loading="loading" @on-change="selectionChange">
       <Option v-for="data in list" :label="data.orgName || data.name" :value="data.id" :key="data.id">{{data.orgName || data.name}}</Option>
    </Select>
</template>

<script>

export default {
  name: 'autoSelect',
  model: {
    prop: 'selected',
    event: 'change'
  },
  props: {
    list: { //地址json数据表
      type: Array,
      default: []
    },
    selected: ''
  },
  data: function() {
    return {
      loading: false, //搜索时，是否还在加载中（远程搜索时的加载状态）
      selectVal: ''
    }
  },
  mounted() {

  },
  methods: {
    //清理数据
    clearData() {

    },
    //获取数据
    getData() {

    },
    //远程搜索供应商(自动补全)
    searchList(query) {
      if (query == '') {
        // this.list = [];
        return
      }

      this.loading = true
      this.$emit("search-list", query)
      this.$watch('list', function() {
          this.loading = false
        })
    },
    //下拉框改变时触发事件
    async selectionChange(val) {
      this.selectVal = val.value ? val.value : ''
      this.$emit("change", this.selectVal)
    }
  }
}
</script>

<style lang="less" scoped>

</style>

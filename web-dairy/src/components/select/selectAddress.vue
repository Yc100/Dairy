/**
 * 地址选择三级联动
 * 作者：yinhailan
 * 时间：2017年05月22日09:44:11
 * 依赖：iview: select,row,col
 * 注意：若多页面没有引入iview,请在此组件引入iview，确保组件能够正常使用
 * 传参：value:[],是省市区数组
        list:[]  是所以地址的json数据
        proWidth:number,省的样式宽度
        cityWidth：number,市的样式宽度
        areaWidth：number,区的样式宽度
 */
<template lang="html">
  <div class="">
    <Row>
         <Col :span="proWidth">
            <Select placeholder="请选择所在地" v-model="province" @on-change='proChange($event)' v-bind:disabled="!isable">
               <Option v-for='item in provinces'
                  :key='item.value'
                  :value='item.value'>{{ item.name }}</Option>
           </Select>
         </Col>
         <Col :span="cityWidth" :offset="1">
           <Select placeholder="请选择所在地" v-model='city' @on-change='cityChange($event)' v-bind:disabled="!isable" >
              <Option v-for='item in citys'
                  :key='item.value'
                  :value='item.value'>{{item.name}}</Option>
          </Select>
         </Col>
         <Col :span="areaWidth" :offset="1">
           <Select placeholder="请选择所在地" v-model="area" v-bind:disabled="!isable">
              <Option v-for='item in areas'
                  :key='item.value'
                  :value='item.value'>{{item.name}}</Option>
          </Select>
         </Col>
     </Row>
  </div>
</template>

<script>
import filter from 'array-filter'
import {ChinaAddressV3Data} from 'vux'
export default {
  name:'address',
  props: {
    list:{         //地址json数据表
      type:Array,
      default:ChinaAddressV3Data
    },
    value:{       //是省市区总数组
      type:Array,
      default: function () {
        return []
      }

    },
    proWidth:{    //省的样式宽度
      type:Number,
      default:7
    },
    cityWidth:{
      type:Number,//市的样式宽度
      default:7
    },
    areaWidth:{  //区的样式宽度
      type:Number,
      default:7
    },
    isable:{ 
      type:Boolean,
      default:true
    }
  },
  data: function(){
    return {
      province:'',
      city:'',
      area:'',
      provinces:[],  //将生成的省放在此数组中
      citys:[],      //将生成的城市放在此数组中
      areas:[] ,     //将生成的区域放在此数组中
      selectValue:[]
    }
  },
  mounted () {
    this.initData(this.value)   //初始下拉框数据
  },
    methods: {
      initData(v) {
        if(v.length>0){ //如果是修改就直接显示
          this.province = v[0]
          this.city = v[1]
          this.area = v[2]
        }else { // 新增的话，清空修改的数据
          this.provinces = []
          this.citys = []
          this.areas = []
          this.province = ''
          this.city = ''
          this.area = ''
        }
          this.provinces = this.getFirstColumn()  //获取省份的数据信息
      },
      //省份切换，生成城市的下拉数据
      proChange: function (val, oldVal) {
      var data = this.getChildren(val)
        for(var i =0; i< data.length; i++){
          this.$set(this.citys, i, data[i]);
        }

        //this.province != '' && (this.city = this.citys[0].value);  //可以设置默认，选择省时，市是否直接显示
        // this.province ? this.value[0] = this.province : this.value[0] = ''
      },
      //城市切换，生成区的下拉数据
      cityChange: function (val,  oldVal) {
       var data = this.getChildren(val)
         for(var i =0; i< data.length; i++){
           this.$set(this.areas, i, data[i]);
         }
         //this.city != '' &&  (this.area = this.areas[0].value); //可以设置默认，选择市时，区是否直接显示
         // this.city ? this.value[1] = this.city : this.value[1] = ''
         // this.area ? this.value[2] = this.area : this.value[2] =''
     },
     //生成省的下拉框
     getFirstColumn () {
       return filter(this.list, one => {
         return !one.parent || one.parent === 0 || one.parent === '0'
       })
     },
     //获取市，区下拉框函数
     getChildren (value) {
       return filter(this.list, one => {
         return one.parent === value
       })
     }

   },
   watch:{
    value(n, o) {
        this.initData(n)
      },
      province(n, o) {
        this.$emit('input', [this.province,this.city,this.area])
      },
      city(n, o) {
       this.$emit('input', [this.province,this.city,this.area])
      },
      area(n, o) {
       this.$emit('input', [this.province,this.city,this.area])
      },
  }
}
</script>

<style lang="less" scoped>

</style>

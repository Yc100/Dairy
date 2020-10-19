/**
 * 描述：防疫站的个人信息管理
 * 作者：zhangb
 * 时间：2020-01-08
 * 依赖：
 * 注意：
 * props：
 */
<template>
  <div class="datav">
    <div style="width:100%;margin-top:1em;padding:0 1em;">
      <Row>
        <Col span="8">
          时间范围：
          <DatePicker type="datetimerange" format="yyyy-MM-dd" placeholder="选择日期" style="width:75%" :start-date='new Date(new Date().getFullYear() - 1, new Date().getMonth(), new Date().getDate())' @on-change="(data) =>{ changeDate(data)}"></DatePicker>
        </Col>
        <Col span="8">
          区域：
          <Select v-model="searchParams.area" style="width:78%" @on-change="changeArea">
            <Option v-for="item in areaOptions" :value="item.value" :key="item.value">{{ item.label }}</Option>
          </Select>
        </Col>
        <Col span="8">
          畜禽种类：
          <Select v-model="searchParams.livestockSpecies" style="width:78%" @on-change="changeLivestockKinds">
            <Option v-for="item in livestockKinds" :value="item.value" :key="item.value">{{ item.label }}</Option>
          </Select>
        </Col>
      </Row>
      <Row style="margin-top:15px;">
        <Col span="6" >
          <div class="messageBox bgOne">
            <div class="titleName">畜禽免疫率</div>
            <div class="numName">{{countObj.immunizationRate || 0}}%</div>
          </div>
        </Col>
        <Col span="6" >
          <div class="messageBox bgTwo">
            <div class="titleName">员工人数</div>
            <div class="numName">{{countObj.userNum || 0}} （人）</div>
          </div>
        </Col>
        <Col span="6">
          <div class="messageBox bgThree">
            <div class="titleName">养殖户总数</div>
            <div class="numName">{{countObj.farmersNum || 0}} （户）</div>
          </div>
        </Col>
        <Col span="6">
          <div class="messageBox bgFour">
            <div class="titleName">畜禽总数</div>
            <div class="numName">{{countObj.livestockNum || 0}}（头）</div>
          </div>
        </Col>
      </Row>
    </div>
    <div class="echartBox">
      <Row>
        <Col span="12">
          <Card title='区域总免疫次数趋势图' style="width:98%">
            <div class="line" ref="line"></div>
          </Card>
        </Col>
        <Col span="12">
          <Card title='养殖户分布图（牛单位）' style="width:98%">
            <div class="map" ref="map"></div>
          </Card>
        </Col>
      </Row>
      <Row style="margin-top:20px;">
        <Col span="12">
          <Card title='地区畜禽总数柱状图（牛单位）' style="width:98%">
            <div class="bar" ref="bar"></div>
          </Card>
        </Col>
        <Col span="12">
          <Card title='总畜禽占比（牛单位%）' style="width:98%">
            <div class="pie" ref="pie"></div>
          </Card>
        </Col>
      </Row>
    </div>
  </div>
</template>
<script>
const echarts = require('echarts');
import chinajsondata from './static/json/china.json';
echarts.registerMap('china',chinajsondata)

import {mapGetters,mapActions} from 'vuex' 
import {getAdminAreaList,selectLivestockKinds,getCount,getAdminArea,getNumLivestock,getNumLivestockByArea,getImmunityMonth,getDistributionFarmers} from 'service/api'
export default {
  name: 'datav',
  components:{
  },
  props:{

  },
  computed:{
    ...mapGetters([
      'userInfo',        //用户信息
    ])
  },
  data(){
    return {
      searchParams: {
        date: '',
        livestockSpecies: 0,
        area: 0
      },
      livestockKinds: [{
        value: 0,
        label: '全部'
      }],
      areaOptions: [{
        value: 0,
        label: '全部'
      }],
      countObj: {
        farmersNum: 0,
        livestockNum: 0,
        userNum: 0,
        immunizationRate: 0
      },
      userArea: {},
			pie: {
				pieChart: {},
				isDisplay: true
			},
			bar: {
				barChart: {},
				isDisplay: true
			},
			line: {
				lineChart: {},
				isDisplay: true
			},
			map: {
				mapChart: {}
      },
    }
  },

  methods: {
    ...mapActions([
     
    ]),
    //获取统计数字
		async getMessage(){
			await countByUser().then(res=>{
				if(res.code === 0){
					this.detailData = res.data;
				}else{
					this.$vux.toast.show({
					type:'cancel',
					text:res.msg || '提交失败'
					})
				}
				}).catch(err=>{
				this.$vux.toast.show({
					type:'cancel',
					text:err || '提交失败'
				})
			})
    },
    async dataLoadArea() {
      await getAdminAreaList().then(res=>{
				if(res.code === 0){
					this.areaOptions = this.areaOptions.concat(res.data.map(item => {
            return {
              label: item.province + item.city + item.district + item.addressInfo,
              value: item.areaId
            }
          }))
				}else{
					this.$vux.toast.show({
            type:'cancel',
            text:res.msg || '获取失败'
					})
				}
			})
    },
    async dataLoadSelect() {
      await selectLivestockKinds({parentId: 0}).then(res=>{
				if(res.code === 0){
					this.livestockKinds = this.livestockKinds.concat(res.data.map(item => {
            return {label: item.name, value: item.kindId}
          }))
				}else{
					this.$vux.toast.show({
            type:'cancel',
            text:res.msg || '获取失败'
          })
				}
			})
    },
    async dataLoadCount() {
      await getCount({
        beginDate: this.searchParams.date[0],
        endDate: this.searchParams.date[1],
        area: this.searchParams.area,
        livestockSpecies: this.searchParams.livestockSpecies
      }).then(res=>{
				if(res.code === 0){
					this.countObj = Object.assign(res.data)
				}else{
					this.$vux.toast.show({
            type:'cancel',
            text:res.msg || '获取失败'
          })
				}
			})
    },
    async dataLoadAdminArea() {
      await getAdminArea().then(res=>{
				if(res.code === 0){
					this.userArea = Object.assign(res.data)
				}else{
					this.$vux.toast.show({
            type:'cancel',
            text:res.msg || '获取失败'
          })
				}
			})
    },
    async dataLoadPie() {
      await getNumLivestock({
        beginDate: this.searchParams.date[0],
        endDate: this.searchParams.date[1],
        area: this.searchParams.area,
        livestockSpecies: this.searchParams.livestockSpecies
      }).then(res=>{
				if(res.code === 0){
					const resList = !!res.data ? res.data : []
          this.pie.isDisplay = resList.length > 0
          this.pie.pieChart.setOption({
            tooltip: {
              formatter: p => {
                return p.marker + p.name + '：' + p.value + p.data.unit + '(' + p.percent + '%)'
              }
            },
            series: [{
              name: '畜禽占比',
              type: 'pie',
              data: resList
            }]
          })
				}else{
					this.$vux.toast.show({
            type:'cancel',
            text:res.msg || '获取失败'
          })
				}
			})
    },
    async dataLoadBar() {
      await getNumLivestockByArea({
        beginDate: this.searchParams.date[0],
        endDate: this.searchParams.date[1],
        area: this.searchParams.area,
        livestockSpecies: this.searchParams.livestockSpecies
      }).then(res=>{
				if(res.code === 0){
					const resList = !!res.data ? res.data : []
          this.bar.isDisplay = resList.length > 0
          this.bar.barChart.setOption({
            dataZoom: {
              type: 'inside',
              start: 0,
              end: resList.length < 10 ? 100 : 50
            },
            xAxis: {
              data: !!resList ? resList.map(item => {
                return item.name
              }) : []
            },
            series: [{
              name: '畜禽数',
              type: 'bar',
              data: resList
            }]
          })
				}else{
					this.$vux.toast.show({
            type:'cancel',
            text:res.msg || '获取失败'
          })
				}
			})
    },
    async dataLoadLine() {
      await getImmunityMonth({
        beginDate: this.searchParams.date[0],
        endDate: this.searchParams.date[1],
        area: this.searchParams.area,
        livestockSpecies: this.searchParams.livestockSpecies
      }).then(res=>{
				if(res.code === 0){
					const resList = !!res.data ? res.data : []
          this.line.isDisplay = resList.length > 0
          this.line.lineChart.setOption({
            xAxis: {
              data: !!resList ? resList.map(item => {
                return item.name
              }) : []
            },
            series: [{
              name: '免疫率',
              type: 'line',
              data: resList
            }]
          })
				}else{
					this.$vux.toast.show({
            type:'cancel',
            text:res.msg || '获取失败'
          })
				}
			})
    },
    async dataLoadMap() {
      await getDistributionFarmers({
        beginDate: this.searchParams.date[0],
        endDate: this.searchParams.date[1],
        area: this.searchParams.area,
        livestockSpecies: this.searchParams.livestockSpecies
      }).then(res=>{
				if(res.code === 0){
					let resList = !!res.data ? res.data : []
          this.map.mapChart.setOption({
            series: [{
              name: '0~50',
              type: 'effectScatter',
              data: resList[0]
            }, {
              name: '50~100',
              type: 'effectScatter',
              data: resList[1]
            }, {
              name: '100≦',
              type: 'effectScatter',
              data: resList[2]
            }]
          })
				}else{
					this.$vux.toast.show({
            type:'cancel',
            text:res.msg || '获取失败'
          })
				}
			})
    },
    initPie() {
      this.pie.pieChart = echarts.init(this.$refs.pie)
      let option = {
        color: ['#cd94f3', '#f0c75e', '#74e2fb', '#488ee7', '#dc5e8e', '#cd4692', '#9658c3', '#6c6be2', '#01aebf', '#18b794'],
        legend: {
          show: true,
          orient: 'vertical',
          right: '3%',
          top: 'middle'
        },
        tooltip: {
          show: true,
          confine: true
        },
        series: [{
          name: '畜禽占比',
          type: 'pie',
          center: ['44%', '50%'],
          label: {
            formatter: '{b}{d}%'
          },
          data: []
        }]
      }
      this.pie.pieChart.setOption(option)
      this.dataLoadPie()
    },
    initBar() {
      this.bar.barChart = echarts.init(this.$refs.bar)
      let option = {
        dataZoom: {
          type: 'inside',
          start: 0,
          end: 100
        },
        grid: {
          show: false,
          containLabel: true,
          top: 30,
          right: 5,
          left: 5,
          bottom: 5
        },
        xAxis: {
          show: true,
          axisLabel: {
            interval: 0,
            rotate: 30
          },
          data: []
        },
        yAxis: {
          show: true,
          name: '（牛单位）',
          splitLine: {show: false}
        },
        series: [{
          name: '畜禽数',
          type: 'bar',
          barWidth: '20',
          itemStyle: {
            color: '#74e2fb'
          },
          label: {
            show: true,
            position: 'top'
          },
          data: []
        }]
      }
      this.bar.barChart.setOption(option)
      this.dataLoadBar()
    },
    initLine() {
      this.line.lineChart = echarts.init(this.$refs.line)
      let option = {
        grid: {
          show: false,
          containLabel: true,
          top: 30,
          right: 5,
          left: 14,
          bottom: 5
        },
        xAxis: {
          show: true,
          data: []
        },
        yAxis: {
          show: true,
          name: '（免疫次数）',
          splitLine: {show: false}
        },
        series: [{
          name: '免疫率',
          type: 'line',
          itemStyle: {
            color: 'red'
          },
          areaStyle: {
            color: {
              type: 'linear',
              x: 0,
              y: 0,
              x2: 0,
              y2: 1,
              colorStops: [{
                offset: 0, color: '#a3deac'
              }, {
                offset: 1, color: 'rgba(190, 222, 206, 0)'
              }]
            }
          },
          label: {
            show: true,
            position: 'top'
          },
          data: []
        }]
      }
      this.line.lineChart.setOption(option)
      this.dataLoadLine()
    },
    initMap() {
      this.map.mapChart = echarts.init(this.$refs.map)
      let option = {
        legend: {
          show: true,
          orient: 'vertical',
          left: '5%'
        },
        tooltip: {
          show: true,
          confine: true,
          formatter: p => {
            return p.marker + p.name + '<br/>' + '畜禽：' + p.data.number
          }
        },
        geo: {
          map: 'china', 
          roam: true,
          layoutCenter: ['50%', '50%'],
          layoutSize: 210
        },
        series: [{
          name: '0~50',
          type: 'effectScatter',
          coordinateSystem: 'geo',
          symbolSize: 3,
          rippleEffect: {
            scale: 7
          },
          itemStyle: {
            color: '#74e2fb'
          },
          data: []
        }, {
          name: '50~100',
          type: 'effectScatter',
          coordinateSystem: 'geo',
          itemStyle: {
            color: '#4083f5'
          },
          data: []
        }, {
          name: '100≦',
          type: 'effectScatter',
          coordinateSystem: 'geo',
          itemStyle: {
            color: '#23f40b'
          },
          data: []
        }]
      }
      this.map.mapChart.setOption(option)
      this.dataLoadMap()
    },
    changeDate(date) {
      this.searchParams.date = date
      this.dataLoadCount()
      this.dataLoadPie()
      this.dataLoadBar()
      this.dataLoadLine()
      this.dataLoadMap()
    },
    changeArea() {
      this.dataLoadCount()
      this.dataLoadPie()
      this.dataLoadBar()
      this.dataLoadLine()
      this.dataLoadMap()
    },
    changeLivestockKinds() {
      this.dataLoadCount()
      this.dataLoadPie()
      this.dataLoadBar()
      this.dataLoadLine()
      this.dataLoadMap()
    }
  },

  mounted() {
    this.dataLoadArea()
    this.dataLoadSelect()
    this.dataLoadCount()
    this.dataLoadAdminArea()
    this.initPie()
    this.initBar()
    this.initLine()
    setTimeout(()=>{
      this.initMap()
    },0)


    // window.onresize = function () {
    //   this.pie.pieChart.resize()
    //   this.bar.barChart.resize()
    //   this.line.lineChart.resize()
    //   this.map.mapChart.resize()
    // }
  },
  beforeDestroy(){
    this.pie.pieChart.clear()
    this.bar.barChart.clear()
    this.line.lineChart.clear()
    this.map.mapChart.clear()
  },
  created(){
	}
}
</script>
<style lang="less" scoped>
.datav{
  
}
.messageBox{
  width: 95%;
  height: 140px;
}
.bgOne{
  background: url('../../../assets/imgs/card1.png');
  background-size: 100% 100%;
}
.bgTwo{
  background: url('../../../assets/imgs/card2.png');
  background-size: 100% 100%;
}
.bgThree{
  background: url('../../../assets/imgs/card3.png');
  background-size: 100% 100%;
}
.bgFour{
  background: url('../../../assets/imgs/card4.png');
  background-size: 100% 100%;
}
.echars-container{
  // border:1px solid #000;
  width: 100%;
  height: 500px;
}
.orgLogo{
  width: 100%;
  text-align:center;
}
.titleName{
  padding-top: 30px;
  color: #ffffff;
  padding-left: 75px;
}
.numName{
  color: #ffffff;
  font-size: 28px;
  margin-left: 25px;
  margin-top: 15px;
}
.echartBox{
  padding:1em;
}
.pie {
  width: 100%;
  height: 180px;
}

.bar {
  width: 100%;
  height: 180px;
}

.line {
  width: 100%;
  height: 180px;
}

.map {
  width: 100%;
  height: 180px;
}
</style>

<template>
	<div class="index">
		<headerNav/>
		<div class="row main">
			<!-- 左侧菜单 -->
			<div :class="[{hideMenu: !isShowMenu}, 'div-row', 'index-menu-left', 'div-row-menu']">
				
				<Menu theme="primary" accordion width="auto" class="menu" :active-name="activeMenu" ref="menu" @on-select="jumpUrl">
					
					<!-- <div class="div-.-menu" @click="isShowMenu = !isShowMenu">
						<i :class="['icon', 'iconfont', isShowMenu ? 'icon-31fanhui1' : 'icon-31fanhui2']"></i>
					</div> -->
					
					<div v-for="(list,index) in menu" :key="list.id" :class="{hideMenuList: !isShowMenu}" class="div-menu-wrapper">

						<!-- 单级菜单 -->
					  	<MenuItem  v-if="isArray(list.privileges) && !(list.privileges.length) && list.admin==0" :name="index+'-'+list.url" :to="list.url">
							<i :class="'iconfont'+' '+list.iconName"></i>{{list.name}}
						</MenuItem>
					  	<MenuItem  v-else-if="isArray(list.privileges) && !(list.privileges.length) && list.admin==1 && userInfo.superAdmin==1 " :name="index+'-'+list.url" :to="list.url">
							<i :class="'iconfont'+' '+list.iconName"></i>{{list.name}}
						</MenuItem>

						<!-- 多级菜单 -->
						<Submenu v-if="isArray(list.privileges) && (list.privileges.length) && list.admin==0" :name="index+'-'+list.url">    <!-- 一级菜单-->
							<template slot="title">
								<i :class="'iconfont'+' '+list.iconName"></i>{{list.name}}
							</template>
							<MenuItem v-for="(child,childIndex) in list.privileges" :key="child.id" :name="childIndex+'-'+child.url">   <!-- 二级菜单-->
								<i class="iconfont"></i> {{child.name}}
							</MenuItem>
						</Submenu>
						<Submenu v-else-if="isArray(list.privileges) && (list.privileges.length) && list.admin==1 && userInfo.superAdmin==1" :name="index+'-'+list.url">    <!-- 管理员一级菜单-->
							<template slot="title">
								<i :class="'iconfont'+' '+list.iconName"></i>{{list.name}}
							</template>
							<MenuItem v-for="(child,childIndex) in list.privileges" :key="child.id" :name="childIndex+'-'+child.url">   <!--管理员 二级菜单-->
								<i class="iconfont"></i> {{child.name}}
							</MenuItem>
						</Submenu>

					</div>
				</Menu>

			</div>

			
			<div :class="[{fullCtx: !isShowMenu}, 'div-row', 'div-row-ctx']" style="padding-left:3px;">
				<div class="index-content">
					<v-tags></v-tags>
					<div class="index-content-main">
						<router-view></router-view>
					</div>
				</div>
				<!-- footer --> 
				<div class="index-copy">
					&copy; aiyangniu
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import {mapGetters,mapActions} from 'vuex'
// import { manager,org } from 'service/api'
import headerNav from 'components/nav/headerNav'
import vTags from 'components/nav/Tags.vue'
import Ueditor from 'components/editor/Ueditor.vue'
export default {
	name: 'index',
	components: {
		headerNav,vTags,Ueditor
	},
	data() {
		return {
			isShowMenu: true, //当前菜单  
			activeMenu: "",
			openMenu:"",
			manager: 'manager/index.html#',  //多页面-前缀
      		menu:[],  // 权限菜单
		}
	},
	computed: {
		...mapGetters([
			'userToken',         //用户token
			'isGetToken',        //是否成功获取token
			'userInfo',          //用户信息
		])
	},
	methods: {
		...mapActions([
          //  'getHomeMenu'
        ]),
		/* 更新菜单 */
		async update(route) {
			console.log("在这更新",this.menu)
			for(let i=0;i<this.menu.length;i++){
				if(this.$route.path.split('/')[1] == this.menu[i].url){
					this.activeMenu = i+'-'+this.$route.path.split('/')[1]
					break;
				}else{
					for(let j=0;j<this.menu[i].privileges.length;j++){
						if(this.menu[i].privileges[j].url == this.$route.path.split('/')[1]){
							this.activeMenu = j+'-'+this.$route.path.split('/')[1]
							break;
						}
					}
				}
			}
			// this.activeMenu = this.manager + this.$route.path //选中当前子菜单
			this.$nextTick(() => { //展开当前子菜单所在的菜单项
				this.$refs.menu.$children.forEach((item) => {
					item.opened = item.active ? true : false
				})
			})
		},

		// 菜单初始化
		async init(){
			if(this.userInfo){
				this.menu=[
					//首页
					/*{name:'平台管理系统',url:'datav',iconName:'iconkeshihua',privileges:[
						// {name:'畜禽列表',url:'animalManage',privileges:[]},
					]},*/
					// =========防疫站========
					{name:'个人管理',url:'',iconName:'icongeren',privileges:[
						{name:'个人信息',url:'epidemicInfo',privileges:[],admin:0},
					],admin:0},
					{name:'用户管理',url:'userManage',iconName:'el-icon-user-solid',privileges:[],admin:1},

					{name:'Dairy管理',url:'',iconName:'el-icon-notebook-1',privileges:[
                        {name:'WriteDairy',url:'writeDairy',privileges:[],admin:0},
						{name:'我的Dairy列表',url:'myDairyManage',privileges:[],admin:0},
						{name:'公开的Dairy',url:'dairyManage',privileges:[],admin:0},
					],admin:0},
					//{name:'netty测试',url:'nettyTest',iconName:'el-icon-user-solid',privileges:[],admin:0},
					{name:'我的关注',url:'myFocu',iconName:'el-icon-user-solid',privileges:[],admin:0},
					//{name:'私信',url:'privateLetter',iconName:'el-icon-user-solid',privileges:[],admin:0},


				]
			}
		},

		// 菜单跳转地址
		jumpUrl(val){
			this.$href('dairy/index.html#/'+val.split('-')[1])
		}


	},

	mounted() {
		setTimeout(async ()=>{
			await this.init()
			await this.update()
		},20)
		console.log('我要看账号的信息',this.userInfo)
	},

	watch: {
		$route() {
			this.update()
		}
	}
}
</script>

<style lang="less" scoped>
@import "~src/assets/css/web-theme.less";
.hideMenu {
  transition: width 1s;
  width: 0 !important;
  // margin-left: 20px;
}

.fullCtx {
  transition: width 1s;
  width: 100% !important;
}

.hideMenuList {
  // transition: visibility s !important;
  visibility: hidden;
}

.div-row-ctx {
  transition: width 1s;
  width: 84%;
  display: flex;
  flex-direction: column;
}

.div-row-menu {
  transition: width 1s;
  width: 16%;
}

.index {
  height: 100%;
  display: flex;
  flex-direction: column;
  .main {
    flex: 1;
  }
  .row {
    min-width: 1200px;
    display: flex;
  }
  .code-row-bg {
    text-align: center;
  }
  .index-breadcrumb {
    padding: 20px 15px 0;
  }
  .index-content {
    // min-height: 600px; // margin: 15px 0;
    // overflow: scroll;
    background: #fff;
    border-radius: 4px;
    flex: 1;
  }
  .index-content-main {
    // padding: 50px 30px 0 30px;
    // height: 100%;
  }
  .index-copy {
    text-align: center;
    padding: 10px 0 20px;
    color: #eee;
  }
  .index-menu-left {
    background: #;
    // min-height: 600px;
    // min-width: 25px;
    // margin: 0 20px;
    box-shadow: 0 3px 6px 1px rgba(50, 50, 50, 0.08);
    .menu {
      &::after {
        width: 0px; // height: 50px;
        // display: inline-block;
        // border-radius: 0 50% 50% 0;
        // color: #eee
      }
      .div-menu-wrapper {
        // transition: visibility 2s;
      }
      .div-toggle-menu {
        text-align: center;
        line-height: 50px;
        right: -25px;
        top: 0;
        width: 25px;
        height: 50px;
        display: inline-block;
        position: absolute;
        opacity: 0.5;
        color: #fff;
        z-index: 999999;
        cursor: pointer;
        background-color: @--primary-color; // border: 2px solid #801515;
      }
    }
  }
}
</style>

import filters from './filter'
export default {
    filters,
    computed: {
        /* 获取多页面的view与route */
        $viewUrl() {
            let loc = window.location
                // let pathname = loc.pathname.substring(7,loc.pathname.length)  //去掉views前缀
            let href = loc.href
            let url = encodeURIComponent(href) //编码转换
            return url
        }
    },
    methods: {
        /* 路由跳转 */
        $jump(to) {
            if (this.$router) {
                this.$router.push(to)
            }
        },
        /* 多页面跳转 */
        $href(to, query) {
            console.log("我要跳转",to, query)
            let loc = window.location
            let env = process.env.NODE_ENV
            let url = ''
            url = loc.protocol + '//' + loc.host + '/views/' + to        
            url = query ? url + '?' + query : url
            console.log(url, to, query, "url")
            loc.href = url
        },
        /* 新页面跳转 */
        $open(to, query) {
            let loc = window.location
            let env = process.env.NODE_ENV
            let url = ''
            url = loc.protocol + '//' + loc.host + '/views/' + to
            url = query ? url + '?' + query : url
            window.open(url)
        },
        /* 登录验证跳转 */
        $loginVerify() {
            let viewUrl = 'lastPath=' + this.$viewUrl
            this.$href('index/index.html#/login', viewUrl) // 将跳转的路由path作为参数，登录成功后跳转到该路由
        },
        /* 用户身份验证跳转 */
        $roleVerify() {
            let viewUrl = 'lastPath=' + this.$viewUrl
            // this.$href('index/index.html#/join', viewUrl) // 将跳转的路由path作为参数，登录成功后跳转到该路由
            this.$href('index/index.html#/login', viewUrl) // 将跳转的路由path作为参数，登录成功后跳转到该路由
        },
        /* 权限错误 */
        $privVerify() {
            // this.$href('manager/index.html#/index') 
        },

        /**
         * 权限验证
         * @param  {Array}  rules  eg: [{name: 'isAdmin', redirect: 'index/home'}, {name: 'isLogin', redirect: 'index/login'}] || ['isAdmin', 'islogin']
         * @param  {Object} status eg: {isAdmin: false, isLogin: true}
         * @return {[type]}        [description]
         */
        $verifyRules(rules = [], status = {}) {
            // let status = Object.assign({}, store.getters.baseAccessinfo, store.getters.showSuplierInfo, store.getters.mengniu);
            typeof rules[0] == 'string' ? rules.forEach(val => {
                !status[val] && this.$href('index/index.html#/home')
            }) : rules.forEach(val => {
                !status[val.name] && this.$href(val.redirect || 'index/index.html#/home')
            })

        },
        /* 开发中的提示 */
        $devInfo() {
            this.$Message.info('此功能正在开发中', 2)
        },
        isArray(v){
            return Object.prototype.toString.call(v)=='[object Array]';
        },
    }
}
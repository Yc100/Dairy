webpackJsonp([21,24],{153:function(e,t,o){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n=o(70),a=o.n(n),r=o(12),c=o(71),u=o(25),l=o(83);r.default.use(c.a);var i=[].concat(a()(l.default)),s=new c.a({routes:i,mode:"hash",strict:!1});s.beforeEach(function(e,t,o){""!=localStorage.token?u.a.getters.loginStatus?(console.log("router拦截器：用户已登录，可以跳转"),o()):u.a.dispatch("getUserInfo").then(function(){console.log("router拦截器：获取用户信息中"),o()}):o()}),s.afterEach(function(e){window.scrollTo(0,0)}),t.default=s},83:function(e,t,o){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n=function(e){return o.e(12).then(function(){var t=[o(204)];e.apply(null,t)}.bind(this)).catch(o.oe)};t.default=[{path:"/",redirect:"/404"},{path:"",redirect:"/404"},{path:"/404",component:n}]}},[153]);
/**
 * icon封装组件
 * 作者：suliyu
 * 时间：2017年05月15日17:21:47
 * 依赖：无
 * 注意：无
 * props: {
     type: String,             //图标的class
     size: [Number, String],   //图标的大小
     color: String             //图标的颜色
   },
 */
<template>
    <i :class="classes" :style="styles"></i>
</template>
<script>
    const prefixCls = 'iconfont';
    const prefixClsTwo = 'icon';
    export default {
        name: 'ayn-icon',
        props: {
            type: String,             //图标的class
            size: [Number, String],   //图标的大小
            color: String             //图标的颜色
        },
        computed: {
            classes () {
                return `${prefixCls} ${prefixClsTwo}-${this.type}`;
            },
            styles () {
                let style = {};

                if (this.size) {
                    style['font-size'] = `${this.size}px`;
                }

                if (this.color) {
                    style.color = this.color;
                }

                return style;
            }
        }
    };
</script>

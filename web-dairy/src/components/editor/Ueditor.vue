<template>
  <div>
    <!--下面通过传递进来的id完成初始化-->
    <script :id="randomId"  type="text/plain"></script>
  </div>
</template>

<script>

//主体文件引入
import '../../../static/utf8-jsp/ueditor.config'
import '../../../static/utf8-jsp/ueditor.all.min'
import '../../../static/utf8-jsp/lang/zh-cn/zh-cn'
import api from 'service/apiConfig'

export default {
  props: {
    //配置可以传递进来
    // ueditorConfig:{}
  },
  data() {
    return {
      //每个编辑器生成不同的id,以防止冲突
      randomId: 'editor_' + (Math.random() * 100000000000000000),
      //编辑器实例
      instance: null,

      /*
      ueditor配置，如果有不同需求，也可从父组件传入，如这里去除了头部的地图，单图上传等功能
       */
      ueditorConfig: {
        toolbars: [
          [
            'fullscreen', 'source', '|', 'undo', 'redo', '|',
            'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
            'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
            'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
            'directionalityltr', 'directionalityrtl', 'indent', '|',
            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
            'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|', 'insertimage', 'emotion', 'scrawl', 'attachment', 'insertframe', 'insertcode', 'webapp', 'pagebreak', 'template', 'background', '|',
            'horizontal', 'date', 'time', 'spechars', 'snapscreen', 'wordimage', '|',
            'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
            'print', 'preview', 'searchreplace', 'drafts', 'help'
          ]
        ]
      }
    };
  },

  //此时--el挂载到实例上去了,可以初始化对应的编辑器了
  mounted() {
    /*
    重写上传路径，需要与服务器上传地址接口一致
     */
    // UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
    let _bkGetActionUrl = UE.Editor.prototype.getActionUrl;
    UE.Editor.prototype.getActionUrl = function(action) {
      if (action == 'uploadimage') {
        return api.apiItem + '/images/upload';
        // } else if (action == 'uploadvideo') {
        // return 'http://a.b.com/video.php';
      } else {
        return _bkGetActionUrl.call(this, action);
      }
    }
    this.initEditor()
  },

  beforeDestroy() {
    // 组件销毁的时候，要销毁 UEditor 实例
    if (this.instance !== null && this.instance.destroy) {
      this.instance.destroy();
    }
  },
  methods: {
    initEditor() {
      //dom元素已经挂载上去了
      this.$nextTick(() => {
        this.instance = UE.getEditor(this.randomId, this.ueditorConfig);
        // 绑定事件，当 UEditor 初始化完成后，将编辑器实例通过自定义的 ready 事件交出去
        this.instance.addListener('ready', () => {
          this.$emit('ready', this.instance);
        });
      });
    }
  }
};
</script>
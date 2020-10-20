package com.yc.common.constant;

/**
 * 常量
 *
 * @author YC
 * @createDate 2020/10/16 14:28
 **/
public interface Constant {
    /**
     *
     */
    /**
     * 成功
     */
    int SUCCESS = 1;
    /**
     * 失败
     */
    int FAIL = 0;
    /**
     * OK
     */
    String OK = "OK";
    /**
     * 参数错误
     */
    String PARAM_ERROR = "参数错误";
    /**
     * status
     */
    String STATUS = "status";
    /**
     * 用户标识
     */
    String USER_ID = "userId";
    /**
     * 菜单根节点标识
     */
    String MENU_ROOT = "0";
    /**
     * 部门根节点标识
     */
    String DEPT_ROOT = "0";
    /**
     * 岗位根节点标识
     */
    String POST_ROOT = "0";
    /**
     * 数据字典根节点标识
     */
    String DICT_ROOT = "0";
    /**
     *  升序
     */
    String ASC = "asc";
    /**
     * 降序
     */
    String DESC = "desc";
    /**
     * 创建时间字段名
     */
    String CREATE_DATE = "create_date";
    /**
     * 创建时间字段名
     */
    String CREATE_TIME = "create_time";

    /**
     * 创建时间字段名
     */
    String ID = "id";

    /**
     * 数据权限过滤
     */
    String SQL_FILTER = "sqlFilter";

    /**
     * 当前页码
     */
    String PAGE = "page";

    String OFFSET = "offset";
    /**
     * 每页显示记录数
     */
    String LIMIT = "limit";
    /**
     * 排序字段
     */
    String ORDER_FIELD = "orderField";
    /**
     * 排序方式
     */
    String ORDER = "order";
    /**
     * 平台普通用户session存储字段属性
     */
    String SYSTEM_USER_KEYWORD = "user";
    /**
     * token header
     */
    String TOKEN_HEADER = "token";

    /**
     * token header X-ec.SIGNATURE
     */
    String REQUEST_HEADERS_AUTH_KEY = "token";

    /**
     * 云存储配置KEY
     */
    String CLOUD_STORAGE_CONFIG_KEY = "CLOUD_STORAGE_CONFIG_KEY";
    /**
     * 短信配置KEY
     */
    String SMS_CONFIG_KEY = "SMS_CONFIG_KEY";
    /**
     * 邮件配置KEY
     */
    String MAIL_CONFIG_KEY = "MAIL_CONFIG_KEY";

    /**
     * 定时任务状态
     */
    enum ScheduleStatus {
        /**
         * 暂停
         */
        PAUSE(0),
        /**
         * 正常
         */
        NORMAL(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3),
        /**
         * FASTDFS
         */
        FASTDFS(4),
        /**
         * 本地
         */
        LOCAL(5);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 短信服务商
     */
    enum SmsService {
        /**
         * 阿里云
         */
        ALIYUN(1),
        /**
         * 腾讯云
         */
        QCLOUD(2);

        private int value;

        SmsService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 数据状态
     */
    enum DataStatus {
        /**
         * 停用
         */
        UNUSED(0),
        /**
         * 正常
         */
        USE(1),
        /**
         * 删除
         */
        DELETE(2),
        /**
         * 未保存
         */
        UNSAVE(3);

        private int value;

        DataStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


    enum FileSubTypeSys{
        /**
         * 头像
         */
        AVATAR(0);
        private int value;
        FileSubTypeSys (int value){
            this.value=value;
        }
        public int getValue(){
            return value;
        }
    }

    enum FileSubTypeDuty{
        /**
         * 缩略图
         */
        THUMBNAIL(0),
        /**
         * 报告
         */
        REPORT(1),
        /**
         * 记录
         */
        RECORD(2),
        /**
         * 文件
         */
        FILE(3);

        private int value;
        FileSubTypeDuty (int value){
            this.value=value;
        }
        public int getValue(){
            return value;
        }
    }

    enum OperationType{
        /**
         * 运营系统
         */
        SYS(0),
        /**
         * 疫控业务
         */
        HIS(1);

        private int value;
        OperationType(int value){
            this.value=value;
        }
        public int getValue() {
            return value;
        }
    }
}
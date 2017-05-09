package org.itachi.cms.error;

/**
 * Created by itachi on 2017/3/18.
 * User: itachi
 * Date: 2017/3/18
 * Time: 10:56
 */
public abstract class CmsError {
    protected CmsError() {}

    public  interface ErrorType {
        int getCode();
        String getReasonPhrase();
    }

    public enum Error implements ErrorType {

        ILLEGAL_REQUEST(101, "非法请求"),
        UNSUPPORT_MEDIATYPE(102, "不支持的数据类型"),
        REMOTE_SERVICE_ERROR(103, "远程服务错误"),
        PARAMETER_INVALID(104, "参数错误"),
        ACCOUNT_INVALID(105, "非法的用户名"),
        PASSWORD_INVALID(106, "非法的密码"),
        USER_NOT_EXISTS(107, "用户名或密码错误"),
        ADD_OR_MODIFY_GROUP_FAILURE(201,"用户组添加或者修改失败"),
        AUTHORITY_GROUP_FAILURE(202,"部分权限添加失败，请补充添加权限。"),
        DEL_GROUP_FAILURE(203,"用户组删除失败"),
        UNKNOWN(999, "未知错误");

        private final int code;
        private final String reason;

        Error(final int errorCode, final String reasonPhrase) {
            code = errorCode;
            reason = reasonPhrase;
        }

        @Override
        public int getCode() {
            return code;
        }

        @Override
        public String getReasonPhrase() {
            return reason;
        }

        @Override
        public String toString() {
            return reason;
        }

        public static Error fromErrorCode(final int errorCode) {
            for (Error error : Error.values()) {
                if (error.code == errorCode) {
                    return error;
                }
            }

            return null;
        }
    }
}

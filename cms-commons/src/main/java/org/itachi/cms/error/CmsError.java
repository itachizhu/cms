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

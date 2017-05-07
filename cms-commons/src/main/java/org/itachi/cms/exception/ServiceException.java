package org.itachi.cms.exception;

import org.itachi.cms.error.CmsError;

/**
 * Created by itachi on 2017/3/18.
 * User: itachi
 * Date: 2017/3/18
 * Time: 10:51
 */
public class ServiceException extends BaseException {
    public ServiceException(Integer code, String message) {
        super(412, code, message);
    }

    public ServiceException(CmsError.Error error) {
        this(error.getCode(), error.getReasonPhrase());
    }
}

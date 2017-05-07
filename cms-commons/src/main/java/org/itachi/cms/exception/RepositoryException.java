package org.itachi.cms.exception;

import org.itachi.cms.error.CmsError;

/**
 * Created by itachi on 2017/3/18.
 * User: itachi
 * Date: 2017/3/18
 * Time: 10:50
 */
public class RepositoryException extends BaseException {
    public RepositoryException(Integer code, String message) {
        super(417, code, message);
    }

    public RepositoryException(CmsError.Error error) {
        this(error.getCode(), error.getReasonPhrase());
    }
}

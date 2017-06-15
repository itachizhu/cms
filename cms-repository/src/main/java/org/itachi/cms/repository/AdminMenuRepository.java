package org.itachi.cms.repository;

import org.itachi.cms.dto.AdminMenuDTO;

import java.util.List;

/**
 * Created by itachi on 2017/6/15.
 * User: itachi
 * Date: 2017/6/15
 * Time: 15:24
 */
public interface AdminMenuRepository {
    List<AdminMenuDTO> menus() throws Exception;
}

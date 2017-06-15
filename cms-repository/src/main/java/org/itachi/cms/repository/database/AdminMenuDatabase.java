package org.itachi.cms.repository.database;

import org.itachi.cms.dto.AdminMenuDTO;
import org.itachi.cms.mapper.AdminMenuMapper;
import org.itachi.cms.repository.AdminMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by itachi on 2017/6/15.
 * User: itachi
 * Date: 2017/6/15
 * Time: 15:25
 */
@Component("adminMenuDatabase")
public class AdminMenuDatabase implements AdminMenuRepository {
    @Autowired
    private AdminMenuMapper adminMenuMapper;

    @Override
    public List<AdminMenuDTO> menus() throws Exception {
        return adminMenuMapper.select();
    }
}

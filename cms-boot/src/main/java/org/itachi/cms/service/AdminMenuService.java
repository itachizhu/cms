package org.itachi.cms.service;

import org.itachi.cms.dto.AdminMenuDTO;
import org.itachi.cms.repository.AdminMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by itachi on 2017/6/15.
 * User: itachi
 * Date: 2017/6/15
 * Time: 15:28
 */
@Component
public class AdminMenuService {
    @Autowired
    @Qualifier("adminMenuDatabase")
    private AdminMenuRepository adminMenuRepository;

    public List<AdminMenuDTO> menus(String contextPath) throws Exception {
        List<AdminMenuDTO> menus = adminMenuRepository.menus();
        if (menus != null && !menus.isEmpty()) {
            for (AdminMenuDTO menu : menus) {
                if (menu.getUrl() != null && !menu.getUrl().isEmpty()) {
                    menu.setUrl(contextPath + menu.getUrl());
                }
            }
        }
        return menus;
    }
}

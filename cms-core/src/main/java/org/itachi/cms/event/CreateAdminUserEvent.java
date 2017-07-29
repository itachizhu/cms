package org.itachi.cms.event;

import org.itachi.cms.dto.AdminUserDTO;

/**
 * Created by itachi on 2017/7/29.
 * User: itachi
 * Date: 2017/7/29
 * Time: 14:56
 */
public class CreateAdminUserEvent {
    private String identifier;
    private AdminUserDTO adminUser;

    public String getIdentifier() {
        return identifier;
    }

    public AdminUserDTO getAdminUser() {
        return adminUser;
    }

    public CreateAdminUserEvent(String identifier, AdminUserDTO adminUser) {
        this.identifier = identifier;
        this.adminUser = adminUser;
    }
}

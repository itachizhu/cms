package org.itachi.cms.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.itachi.cms.dto.AdminUserDTO;

/**
 * Created by itachi on 2017/7/29.
 * User: itachi
 * Date: 2017/7/29
 * Time: 14:52
 */
public class CreateAdminUserCommand {
    @TargetAggregateIdentifier
    private String identifier;

    private AdminUserDTO adminUser;

    public String getIdentifier() {
        return identifier;
    }

    public AdminUserDTO getAdminUser() {
        return adminUser;
    }

    public CreateAdminUserCommand(String identifier, AdminUserDTO adminUser) {
        this.identifier = identifier;
        this.adminUser = adminUser;
    }
}

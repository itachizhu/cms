package org.itachi.cms.domain;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.itachi.cms.dto.AdminUserDTO;
import org.itachi.cms.repository.AdminUserRepository;

/**
 * Created by itachi on 2017/7/29.
 * User: itachi
 * Date: 2017/7/29
 * Time: 11:59
 */
public class AdminUser {
    private AdminUserRepository repository;
    private CommandGateway commandGateway;
    private AdminUserDTO dto;

    public AdminUser(AdminUserRepository repository, CommandGateway commandGateway, AdminUserDTO dto) {
        this.repository = repository;
        this.commandGateway = commandGateway;
        this.dto = dto;
    }
}

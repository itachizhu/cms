package org.itachi.cms.controller;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.itachi.cms.command.CreateAdminUserCommand;
import org.itachi.cms.dto.AdminUserDTO;
import org.itachi.cms.snowflake.IdWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by itachi on 2017/7/29.
 * User: itachi
 * Date: 2017/7/29
 * Time: 15:06
 */
@RestController
public class HelloController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private CommandGateway commandGateway;

    @RequestMapping(value = "/command", method = RequestMethod.GET)
    public String command() throws Exception {
        commandGateway.send(new CreateAdminUserCommand(String.valueOf(IdWorker.getInstance().nextId()), new AdminUserDTO()));
        LOGGER.debug("====对不起，我先返回了，你们后面慢慢响应!====");
        return "Hello World";
    }
}

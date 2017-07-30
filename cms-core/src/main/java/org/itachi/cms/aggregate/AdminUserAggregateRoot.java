package org.itachi.cms.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.itachi.cms.command.CreateAdminUserCommand;
import org.itachi.cms.event.CreateAdminUserEvent;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

/**
 * Created by itachi on 2017/7/29.
 * User: itachi
 * Date: 2017/7/29
 * Time: 21:13
 */
@Aggregate(repository = "domainRepository")
public class AdminUserAggregateRoot extends CmsAggregateRoot {
    @CommandHandler
    public AdminUserAggregateRoot(CreateAdminUserCommand command) {
        this.identifier = command.getIdentifier();
        apply(new CreateAdminUserEvent(command.getIdentifier(), command.getAdminUser()));
    }
}

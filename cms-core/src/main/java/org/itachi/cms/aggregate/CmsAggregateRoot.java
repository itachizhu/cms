package org.itachi.cms.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.itachi.cms.command.CreateAdminUserCommand;
import org.itachi.cms.event.CreateAdminUserEvent;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

/**
 * Created by itachi on 2017/7/29.
 * User: itachi
 * Date: 2017/7/29
 * Time: 14:37
 */
@Aggregate(repository = "domainRepository")
public class CmsAggregateRoot {
    @AggregateIdentifier
    private String identifier;

    public String getIdentifier() {
        return identifier;
    }

    public CmsAggregateRoot() {}

    @CommandHandler
    public CmsAggregateRoot(CreateAdminUserCommand command) {
        this.identifier = command.getIdentifier();
        apply(new CreateAdminUserEvent(command.getIdentifier(), command.getAdminUser()));
    }
}

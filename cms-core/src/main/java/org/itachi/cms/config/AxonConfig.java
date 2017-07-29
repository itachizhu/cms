package org.itachi.cms.config;

import org.axonframework.commandhandling.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.AsynchronousCommandBus;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.eventhandling.AnnotationEventListenerAdapter;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.itachi.cms.aggregate.CmsAggregateRoot;
import org.itachi.cms.handler.CmsEventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by itachi on 2017/7/28.
 * User: itachi
 * Date: 2017/7/28
 * Time: 15:06
 */
@Configuration
public class AxonConfig {
    @Bean
    public EventBus eventBus() {
        return new SimpleEventBus();
    }

    @Bean
    public CommandBus commandBus() {
        return new AsynchronousCommandBus();
    }

    @Bean
    public CommandGateway commandGateway() {
        return new DefaultCommandGateway(commandBus());
    }

    @Bean
    public EventStore eventStore() {
        EventStore eventStore = new EmbeddedEventStore(new InMemoryEventStorageEngine());
        eventStore.subscribe(eventMessages -> eventMessages.forEach(e -> {
            try {
                annotationEventListenerAdapter().handle(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }));
        return eventStore;
    }

    @Bean
    public EventSourcingRepository<CmsAggregateRoot> domainRepository() {
        return new EventSourcingRepository<>(CmsAggregateRoot.class, eventStore());
    }

    @Bean
    public AggregateAnnotationCommandHandler<CmsAggregateRoot> domainCommandHandler() {
        AggregateAnnotationCommandHandler<CmsAggregateRoot> commandHandler = new AggregateAnnotationCommandHandler<>(CmsAggregateRoot.class, domainRepository());
        commandHandler.subscribe(commandBus());
        return commandHandler;
    }

    @Bean
    public AnnotationEventListenerAdapter annotationEventListenerAdapter() {
        return new AnnotationEventListenerAdapter(new CmsEventHandler());
    }
}

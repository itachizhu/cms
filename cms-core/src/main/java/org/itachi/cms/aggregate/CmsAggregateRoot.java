package org.itachi.cms.aggregate;

import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

/**
 * Created by itachi on 2017/7/29.
 * User: itachi
 * Date: 2017/7/29
 * Time: 14:37
 */
@Aggregate(repository = "domainRepository")
public class CmsAggregateRoot {
    @AggregateIdentifier
    protected String identifier;

    public String getIdentifier() {
        return identifier;
    }

    public CmsAggregateRoot() {}
}

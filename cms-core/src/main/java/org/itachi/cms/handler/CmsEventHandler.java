package org.itachi.cms.handler;

import org.axonframework.eventhandling.EventHandler;
import org.itachi.cms.event.CreateAdminUserEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by itachi on 2017/7/29.
 * User: itachi
 * Date: 2017/7/29
 * Time: 12:00
 */
public class CmsEventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CmsEventHandler.class);

    @EventHandler
    public void handle(CreateAdminUserEvent event) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.debug("=====滞后执行=====");
    }
}

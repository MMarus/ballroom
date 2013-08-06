package org.jboss.ballroom.client.spi;

import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import org.jboss.ballroom.client.rbac.SecurityService;

/**
 *
 * Shared components the widget library does rely on.
 *
 * @author Heiko Braun
 * @date 7/12/11
 */
public interface Framework {

    EventBus getEventBus();
    PlaceManager getPlaceManager();
    AutoBeanFactory getBeanFactory();
    SecurityService getSecurityService();
}

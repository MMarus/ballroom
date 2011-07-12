/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */

package org.jboss.as.console.client.widgets;

import com.google.gwt.user.client.ui.DisclosurePanel;
import org.jboss.as.console.client.widgets.icons.Icons;

/**
 * @author Heiko Braun
 * @date 4/4/11
 */
public class DisclosureStackHeader {

    private DisclosurePanel panel;

    public DisclosureStackHeader(String title) {

        panel = new DisclosurePanel(Icons.INSTANCE.stack_opened(), Icons.INSTANCE.stack_closed(), title);
        panel.setOpen(true);
        panel.getElement().setAttribute("style", "width:100%;");
        panel.getHeader().setStyleName("stack-section-header");
        panel.setWidth("100%"); // IE 7

    }

    public DisclosurePanel asWidget() {
        return panel;
    }
}

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

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Heiko Braun
 * @date 4/26/11
 */
public class SplitEditorPanel {

    private SplitLayoutPanel dockLayout;
    private LayoutPanel topLayout;
    private TabLayoutPanel bottomLayout;

    public SplitEditorPanel() {
        dockLayout = new SplitLayoutPanel(5);

        topLayout = new LayoutPanel();

        bottomLayout = new TabLayoutPanel(25, Style.Unit.PX);
        bottomLayout.addStyleName("default-tabpanel");

        dockLayout.addSouth(bottomLayout, 250);
        dockLayout.add(topLayout);
    }

    public Widget asWidget()
    {
        return dockLayout;
    }

    public LayoutPanel getTopLayout() {
        return topLayout;
    }

    public TabLayoutPanel getBottomLayout() {
        return bottomLayout;
    }
}

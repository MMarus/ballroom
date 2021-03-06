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

package org.jboss.ballroom.client.widgets.tables;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.view.client.HasRows;
import com.google.gwt.view.client.Range;
import org.jboss.ballroom.client.widgets.tables.pager.PagerResources;

/**
 * @author Heiko Braun
 * @date 5/13/11
 */
public class DefaultPager extends SimplePager {

    static SimplePager.Resources pagerResources = GWT.create(PagerResources.class);

    public DefaultPager() {
        super(SimplePager.TextLocation.CENTER, pagerResources, false, 0, true);
        getElement().addClassName("default-pager");
        setRangeLimited(true);
    }

    //
    @Override
    public void setPageStart(int index) {

        HasRows display = getDisplay();

        if (display != null) {
            Range range = display.getVisibleRange();
            int pageSize = range.getLength();
            if ((!isRangeLimited() || display.getRowCount() <= pageSize) && display.isRowCountExact()) {
                index = Math.min(index, display.getRowCount() - pageSize);
            }
            index = Math.max(0, index);
            if (index != range.getStart()) {
                display.setVisibleRange(index, pageSize);
            }
        }
    }

    @Override
    public void setDisplay(HasRows display) {
        super.setDisplay(display);

        // debug util (selenium tests)
        getElement().setId("pager");
        if(display instanceof DefaultCellTable)
        {
            String tableId = ((DefaultCellTable) display).getElement().getId();
            getElement().setAttribute("data-ref", tableId);
        }
    }

    protected String createText() {
        if(getDisplay().getRowCount()==0){
            return "";
        }else{
            return super.createText();
        }
    }
}

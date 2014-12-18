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

package org.jboss.ballroom.client.widgets.forms;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import org.jboss.ballroom.client.spi.Framework;

/**
 * @author Heiko Braun
 * @date 3/28/11
 */
public class InputElementWrapper extends VerticalPanel {

    private final HTML rbacConstraintIcon;
    private final HTML expr;
    private final Widget widget;
    private final HTML errorText;
    private final HTML helpText;
    //private Image err = new Image(Icons.INSTANCE.exclamation());

    private final static Framework framework = GWT.create(Framework.class);
    private final InputElement parent;

    public InputElementWrapper(Widget widget, final InputElement input) {
        super();
        this.widget = widget;
        this.parent = input;

        setStyleName("fill-layout-width");

        HorizontalPanel panel = new HorizontalPanel();
        panel.add(widget);
        widget.getElement().getParentElement().setAttribute("class", "form-input");

        rbacConstraintIcon = new HTML("<i class='icon-lock'></i>");
        panel.add(rbacConstraintIcon);
        rbacConstraintIcon.setVisible(false);
        rbacConstraintIcon.getElement().getParentElement().setAttribute("style", "width:16px;vertical-align:middle");

        expr = new HTML("<i class='icon-link'></i>");
        panel.add(expr);

        expr.setStyleName("expression-icon");
        expr.setVisible(false);
        expr.getElement().getParentElement().setAttribute("class", "form-expr");

        expr.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                framework.getEventBus().fireEvent(
                        new ResolveExpressionEvent(input.asExpressionValue())
                );
            }
        });

        errorText = new HTML();
        errorText.addStyleName("form-item-error-desc");

        helpText = new HTML();
        helpText.addStyleName("form-item-help-desc");
        helpText.setVisible(false);

        add(panel);
        add(helpText);
        add(errorText);

        errorText.setVisible(false);
    }

    /**
     * An optional help text (below the item)
     * @param text
     */
    public void setHelpText(String text) {
        helpText.setText(text);
        helpText.setVisible(true);
    }

    public void setErroneous(boolean hasErrors)
    {
        if(hasErrors)
            widget.addStyleName("form-item-error");
        else
            widget.removeStyleName("form-item-error");

        errorText.setText(parent.getErrMessage());
        errorText.setVisible(hasErrors);
    }

    public void setExpression(boolean isExpression)
    {
        expr.setVisible(isExpression);
    }

    public void setConstraintsApply(boolean b)
    {
        rbacConstraintIcon.setVisible(b);
    }

}

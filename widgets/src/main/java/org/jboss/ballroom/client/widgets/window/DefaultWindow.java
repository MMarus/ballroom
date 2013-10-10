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

package org.jboss.ballroom.client.widgets.window;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import org.jboss.ballroom.client.widgets.Aria;

/**
 * @author Heiko Braun
 * @date 2/23/11
 */
public class DefaultWindow extends ResizePanel {


    private final static boolean isIE = Window.Navigator.getUserAgent().contains("MSIE");

    public final static double GOLDEN_RATIO = 1.618;
    private static final int ESCAPE = 27;

    LayoutPanel content;

    int width, height;

    private boolean dragged       = false;
    private int     dragStartX;
    private int     dragStartY;

    private DockLayoutPanel layout;
    private Focus focus = null;

    private Element lastFocus = null;

    public DefaultWindow(String title) {

        layout = new DockLayoutPanel(Style.Unit.PX) ;
        setStyleName("default-window");

        final WindowHeader header = new WindowHeader(title, this);
        layout.getElement().setAttribute(Aria.ROLE, Aria.DIALOG);
        layout.getElement().setAttribute(Aria.LABELLED_BY, header.getHeaderId());


        // dnd
        header.addMouseDownHandler( new MouseDownHandler() {

            public void onMouseDown(MouseDownEvent event) {
                dragged = true;
                dragStartX = event.getRelativeX( getElement() );
                dragStartY = event.getRelativeY( getElement() );
                DOM.setCapture(header.getElement());
            }
        } );
        header.addMouseMoveHandler( new MouseMoveHandler() {

            public void onMouseMove(MouseMoveEvent event) {
                if ( dragged ) {
                    setPopupPosition( event.getClientX() - dragStartX,
                            event.getClientY() - dragStartY );
                }
            }
        } );
        header.addMouseUpHandler( new MouseUpHandler() {

            public void onMouseUp(MouseUpEvent event) {
                dragged = false;
                DOM.releaseCapture( header.getElement() );
            }
        } );

        layout.addNorth(header, 40);

        HorizontalPanel footer = new HorizontalPanel();
        footer.setStyleName("default-window-footer");

        HTML footerLabel = new HTML("&nbsp;");
        footer.add(footerLabel);

        footerLabel.getElement().getParentElement().setAttribute("width", "100%");

        layout.addSouth(footer, 16);

        content = new LayoutPanel();
        content.setStyleName("default-window-content");
        layout.add(content);


        super.setWidget(layout);

        // default width(height
        int winWidth = (int)(Window.getClientWidth()*0.9);
        int winHeight = (int) ( winWidth / GOLDEN_RATIO );

        setWidth(winWidth);
        setHeight(winHeight);

    }

    @Override
    protected void onPreviewNativeEvent(Event.NativePreviewEvent event) {
        if (Event.ONKEYUP == event.getTypeInt()) {
            if (event.getNativeEvent().getKeyCode() == ESCAPE) {
                // Dismiss when escape is pressed
                hide();
            }
        }
    }

    public void trapWidget(Widget w) {
        content.clear();

        TrappedFocusPanel trap = new TrappedFocusPanel(w);

        content.add(trap);
    }

    @Override
    public void setWidget(Widget w) {
        content.clear();
        content.add(w);
    }

    @Override
    public void center() {
        setPopupPosition(
                (Window.getClientWidth()/2)-(width/2),
                (Window.getClientHeight()/2)-(height/2)
        );
        show();

        super.setWidth(width+"px");
        super.setHeight(height+"px");
    }

    public void setWidth(int width) {
        int adjusted = Double.valueOf(width * 1.2).intValue();
        this.width = adjusted;
    }

    public void setHeight(int height) {
        int adjusted = Double.valueOf(height * 1.2).intValue();
        this.height = adjusted;
    }

    @Override
    public void setWidth(String width) {
        super.setWidth(width);
    }

    @Override
    public void setHeight(String height) {
        super.setHeight(height);
    }

    @Override
    public void show() {
        super.show();

        if(isIE)
        {
            Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
                @Override
                public void execute() {
                    layout.forceLayout();
                }
            });
        }

        //focus = new Focus(layout.getElement());
        //focus.setDefault();

        lastFocus = Focus.getActiveElement();

    }

    @Override
    public void hide() {
        super.hide();

        if(lastFocus!=null)
        {
            Scheduler.get().scheduleDeferred(
                    new Scheduler.ScheduledCommand() {
                        @Override
                        public void execute() {
                            lastFocus.focus();
                        }
                    }
            );
        }
    }
}

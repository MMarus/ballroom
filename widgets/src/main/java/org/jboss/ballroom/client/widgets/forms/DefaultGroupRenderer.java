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

import com.google.gwt.dom.client.Element;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.Map;

/**
 * The default renderer for a group of form items.
 *
 * @see Form
 *
 * @author Heiko Braun
 * @author David Bosschaert
 * @date 3/3/11
 */
public class DefaultGroupRenderer implements GroupRenderer
{
    private final String id = "form-"+ DOM.createUniqueId()+"_";
    private final String tablePrefix = "<table class='form-item-table' border=0 id='"+id+"' border=0 cellpadding=0 cellspacing=0 role='presentation'>";
    private final static String tableSuffix = "</table>";

    @Override
    public Widget render(RenderMetaData metaData, String groupName, Map<String, FormItem> groupItems)
    {
        SafeHtmlBuilder builder = new SafeHtmlBuilder();
        builder.appendHtmlConstant(tablePrefix);

        // build html structure
        ArrayList<String> itemKeys = new ArrayList<String>(groupItems.keySet());
        ArrayList<FormItem> values = new ArrayList<FormItem>(groupItems.values());

        // Remove the hidden items from both lists. Iterate from the back so that removal doesn't
        // require adjustment of the numbering.
        for (int i=values.size() - 1; i >= 0; i--) {
            if (!values.get(i).render()) {
                values.remove(i);
                itemKeys.remove(i);
            }
        }

        //int colWidth = 100/(metaData.getNumColumns()*2);

        int numColumns = metaData.getNumColumns();
        builder.appendHtmlConstant("<colgroup class='cols_"+ numColumns +"'>");
        for(int col=0; col< numColumns; col++)
        {
            // it's two TD's per item (title & value)
            builder.appendHtmlConstant("<col class='form-item-title-col'/>");
            builder.appendHtmlConstant("<col class='form-item-col'/>");

        }
        builder.appendHtmlConstant("</colgroup>");

        int i=0;
        while(i<itemKeys.size())
        {
            builder.appendHtmlConstant("<tr class='form-attribute-row' data-dmr-attr='"+values.get(i).getName()+"'>");  // TODO only works with single column

            int col=0;
            for(col=0; col< numColumns; col++)
            {
                int next = i + col;
                if(next<itemKeys.size())
                {
                    FormItem item = values.get(next);
                    createItemCell(metaData, builder, itemKeys.get(next), item);
                }
                else
                {
                    break;
                }
            }

            builder.appendHtmlConstant("</tr>");
            i+=col;
        }

        builder.appendHtmlConstant(tableSuffix);
        builder.appendHtmlConstant("<p style='color:#999999;padding-left:8px'> Required fields are marked with an asterisk (<abbr class='req' title='required'>*</abbr>).</p>");

        HTMLPanel panel = new HTMLPanel(builder.toSafeHtml());

        // inline widget
        for(String key : itemKeys)
        {
            FormItem item = groupItems.get(key);
            final String widgetId = id + key;
            final String labelId = id + key+"_l"; // aria property key
            final String insertId = id + key+"_i";

            Element input = item.getInputElement();
            if(input!=null)
            {
                input.setAttribute("id", widgetId);
                //widget.getElement().setAttribute("tabindex", "0");
                input.setAttribute("aria-labelledby", labelId);
                input.setAttribute("aria-required", String.valueOf(item.isRequired()));
            }
            panel.add(item.asWidget(), insertId);

        }

        return panel;
    }

    private void createItemCell(RenderMetaData metaData, SafeHtmlBuilder builder, String key, FormItem item) {

        final String labelId = id + key+"_l"; // aria property key

        final String widgetId = id + key;
        final String insertId = id + key+"_i";

        builder.appendHtmlConstant("<td class='form-item-title'>"); // style='width:"+metaData.getTitleWidth()*5+"pt'
        String text = !item.getTitle().isEmpty() ? item.getTitle() : "";
        builder.appendHtmlConstant("<label for='" + widgetId + "' id='" + labelId + "'>");
        builder.appendEscaped(text);
        if(item.isRequired()) {
            builder.appendHtmlConstant("<abbr class=\"req\" title=\"required\">*</abbr>");
        }

        if(!item.getTitle().isEmpty())
            builder.appendHtmlConstant(":");
        builder.appendHtmlConstant("</label>");
        builder.appendHtmlConstant("</td>");

        builder.appendHtmlConstant("<td class='form-item form-item-value' id='"+insertId+"'>").appendHtmlConstant("</td>");
        // contents added later
        builder.appendHtmlConstant("</td>");
    }

    @Override
    public Widget renderPlain(RenderMetaData metaData, String groupName, PlainFormView plainView) {
        return plainView.asWidget(metaData);
    }
}

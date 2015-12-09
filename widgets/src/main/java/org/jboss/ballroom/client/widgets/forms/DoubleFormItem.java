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

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Heiko Braun
 * @date 3/28/11
 */
public class DoubleFormItem extends FormItem<Number> {

    /**
     * As defined by https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Number/MIN_SAFE_INTEGER
     */
    public static final double MIN_SAFE_VALUE = -9007199254740991.0;

    /**
     * As defined by https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Number/MAX_SAFE_INTEGER
     */
    public static final double MAX_SAFE_VALUE = 9007199254740991.0;


    private boolean allowNegativeNumber;
    private TextBox textBox;
    private InputElementWrapper wrapper;

    private double min = 0;
    private double max = MAX_SAFE_VALUE;

    public DoubleFormItem(String name, String title) {
        this(name, title, false);
    }

    public DoubleFormItem(String name, String title, double min, double max) {
        this(name, title, min<0);
        setMin(min);
        setMax(max);
    }
    
    public DoubleFormItem(String name, String title, boolean allowNegativeNumber) {
        super(name, title);
        this.allowNegativeNumber = allowNegativeNumber;
        setMin(allowNegativeNumber ? MIN_SAFE_VALUE : 0);

        textBox = new TextBox();
        textBox.setName(name);
        textBox.setTitle(title);
        textBox.setTabIndex(0);

        textBox.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                setModified(true);
                setUndefined(event.getValue().equals(""));
            }
        });

        textBox.setVisibleLength(6);

        wrapper = new InputElementWrapper(textBox, this);

        this.errMessage = "Invalid numeric value";

    }

    public void setMin(double min) {
        if (allowNegativeNumber) {
            this.min = Math.max(MIN_SAFE_VALUE, min);
        } else {
            this.min = Math.max(0, min);
        }
    }

    public void setMax(double max) {
        this.max = Math.min(MAX_SAFE_VALUE, max);
    }

    @Override
    public void setFiltered(boolean filtered) {
        super.setFiltered(filtered);
        super.toggleAccessConstraint(textBox, filtered);
        textBox.setEnabled(!filtered);
        wrapper.setConstraintsApply(filtered);
    }

    public void setVisibleLength(int length)
    {
        textBox.setVisibleLength(length);
    }

    @Override
    public Element getInputElement() {
        return textBox.getElement();
    }

    @Override
    public Widget asWidget() {
        return wrapper;
    }

    @Override
    public Number getValue() {

        String value = textBox.getValue().equals("") ? "0" : textBox.getValue();
        try {
            return Double.valueOf(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Override
    public String asExpressionValue() {
        return textBox.getValue();
    }

    @Override
    public void resetMetaData() {
        super.resetMetaData();
        textBox.setValue(null);
    }

    @Override
    public void setExpressionValue(String expr) {

        this.expressionValue = expr;
        if(expressionValue!=null)
        {
            toggleExpressionInput(textBox, true);
            textBox.setValue(expressionValue);
        }
    }

    @Override
    public void setValue(Number number) {
        toggleExpressionInput(textBox, false);

        if(number.doubleValue()>=0 || allowNegativeNumber)
        {
            textBox.setValue(String.valueOf(number));
        }
    }

    @Override
    public void setEnabled(boolean b) {
        textBox.setEnabled(b);
    }

    @Override
    public void setErroneous(boolean b) {
        super.setErroneous(b);
        wrapper.setErroneous(b);
    }

    @Override
    public boolean validate(Number value) {

        boolean outcome = true;
        boolean isEmpty = textBox.getValue().equals("");


        if(expressionValue!=null || isExpressionScheme(textBox.getValue()))
        {
            outcome = true;
        }
        else if(isRequired() && isEmpty)
        {
            outcome = false;
        }
        else if(!isEmpty)
        {
            try {
                double l = Double.valueOf(textBox.getValue());
                outcome = (l >= min) &&  l<=max;
            } catch (NumberFormatException e) {
                outcome = false;
            }
        }

        return outcome;
    }

    @Override
    public void clearValue() {
        this.textBox.setText("");
    }

    @Override
    protected void toggleExpressionInput(Widget target, boolean flag) {
        wrapper.setExpression(flag);
    }
}

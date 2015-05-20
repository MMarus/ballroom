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

import java.util.List;
import java.util.Map;

import com.google.gwt.user.cellview.client.CellTable;

/**
 * This adapter interface extracts the essential methods from Form so that other classes can wrap a Form
 * and still maintain a Form's characteristics without extending org.jboss.as.console.client.widgets.forms.Form.
 *
 * @author Stan Silvert ssilvert@redhat.com (C) 2011 Red Hat Inc.
 */
public interface FormAdapter<T> extends FormControl {

    /**
     * Binds a default single selection model to the table
     * that displays selected rows in a form.
     *
     * @param instanceTable
     */
    void bind(CellTable<T> instanceTable);


    /**
     * Use the Form to edit the bean.
     * @param bean The bean to be edited.
     */
    void edit(T bean);

    /**
     * Edit a bean (usually providing default values) by treat it as transient.
     * Transient beans will be treated as non-persisted entities and undergo validation
     * @param newBean
     */
    void editTransient(T newBean);

    /**
     * Get changed values since last {@link #edit(Object)} ()}
     * @return
     */
    Map<String, Object> getChangedValues();

    /**
     * Get the bean in its unedited state.
     * @return The bean.
     */
    T getEditedEntity();

    /**
     * Get the bean in its edited state.
     * @return The bean.
     */
    T getUpdatedEntity();

    /**
     * Get the names of all the FormItems
     * @return The names.
     */
    public List<String> getFormItemNames();

    public String getFormItemTitle(String ref);

    /**
     * Get the type of the bean that can be edited with this form.
     * @return The type.
     */
    @Deprecated
    Class<?> getConversionType();

    void setToolsCallback(FormCallback callback);

    void addFormValidator(FormValidator formValidator);
}

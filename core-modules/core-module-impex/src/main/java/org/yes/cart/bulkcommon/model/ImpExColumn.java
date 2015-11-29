/*
 * Copyright 2009 Denys Pavlov, Igor Azarnyi
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.yes.cart.bulkcommon.model;

/**
 * Single import/export column description.
 * <p/>
 * User: denispavlov
 * Date: 26/11/2015
 * Time: 08:00
 */
public interface ImpExColumn {

    /**
     * Get the field value. Regular expression will be used for obtain value if reg exp is set.
     *
     * @param rawValue the whole value from property
     * @param adapter value adapter
     * @return value
     */
    Object getValue(Object rawValue, ValueAdapter adapter);

    /**
     * In case if column has reg exp.
     *
     * @param value the whole value from {@link #getValue(Object, ValueAdapter)}
     * @return matched groups or 0 if column has not reg exp.
     */
    int getGroupCount(String value);

    /**
     * Get the {@link FieldTypeEnum}.
     *
     * @return filed type
     */
    FieldTypeEnum getFieldType();

    /**
     * Get the {@link DataTypeEnum}.
     *
     * @return data type (used for converting data correctly)
     */
    DataTypeEnum getDataType();

    /**
     * Field name in object in java beans notation.
     *
     * @return field name.
     */
    String getName();

    /**
     * Regular expression for get value for specified field name
     * in case if csv field contains more than one object field.
     *
     * Example value: "gold,855"
     * Example pattern to capture number: ".*,([0-9]*)"
     * Group 1 matches: "855"
     *
     * @return regular expression to get the value from csv field
     */
    String getValueRegEx();

    /**
     * Return matching group as the value for given field.
     *
     * Example value: "gold,855"
     * Example pattern to capture number: ".*,([0-9]*)"
     * Example group number to extract value: 1
     * Group 1 matches: "855"
     *
     * @return group that defines the value in regex specified
     */
    Integer getValueRegExGroup();

    /**
     * Transform value matched by regex using this template.
     *
     * Example value: "gold,855"
     * Example pattern to capture number: ".*,([0-9]*)"
     * Example regex template: "Number is $1"
     * Resulting template value: "Number is 855"
     *
     * @return regex template.
     */
    String getValueRegExTemplate();

    /**
     * Get the HQL lookup query to get the foreign key object to output csv field value
     * or value from csv gathered via regexp.
     *
     * @return HQL lookup query.
     */
    String getLookupQuery();


    /**
     * Get included export descriptor for complex fields.
     *
     * @return {@link ImpExDescriptor}
     */
    ImpExDescriptor getDescriptor();


    /**
     * Boolean flag needed to use master object for fk in case of subimport.
     *
     * @return true if need to use master object.
     */
    boolean isUseMasterObject();

    /**
     * Set use master object in case of fk subimport.
     *
     * @param useMasterObject use master object flag.
     */
    void setUseMasterObject(boolean useMasterObject);

    /**
     * Get the constant for field. Some fields can be field with constants
     *
     * @return filed constant
     */
    String getValueConstant();

    /**
     * @return entity type for FK's
     */
    String getEntityType();

    /**
     * @return language of the localisable value (or null if this is not localisable)
     */
    String getLanguage();

}

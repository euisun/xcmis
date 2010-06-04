/*
 * Copyright (C) 2010 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.xcmis.client.gwt.model.property;

import java.util.List;

import org.xcmis.client.gwt.model.Choice;
import org.xcmis.client.gwt.model.EnumCardinality;
import org.xcmis.client.gwt.model.EnumPropertyType;
import org.xcmis.client.gwt.model.EnumUpdatability;

/**
 * @author <a href="mailto:oksana.vereshchaka@gmail.com">Oksana Vereshchaka</a>
 * @version $Id: $
 */
public class StringPropertyDefinition extends BasePropertyDefinition<String>
{
   private Long maxLength;

   /**
    * Default constructor.
    */
   public StringPropertyDefinition()
   {
      super();
   }
   
   /**
    * @param id id 
    * @param localName local name
    * @param localNamespace local name space
    * @param queryName query name
    * @param displayName display name
    * @param description description
    * @param cardinality cardinality
    * @param updatability updatability
    * @param inherited inherited
    * @param required required
    * @param queryable queryable
    * @param orderable orderable
    * @param openChoice open choice
    * @param choices choices
    * @param defaultValue default value
    * @param maxLength max lenght
    */
   public StringPropertyDefinition(String id, String localName, String localNamespace,
      String queryName, String displayName, String description, 
      EnumCardinality cardinality,EnumUpdatability updatability, 
      Boolean inherited, Boolean required, Boolean queryable, Boolean orderable, 
      Boolean openChoice, List<Choice<String>> choices, String[] defaultValue,
      Long maxLength)
   {
      super(id, localName, localNamespace, queryName, displayName, description, cardinality, 
         updatability, inherited, required, queryable, orderable, openChoice, choices, 
         defaultValue);
      this.maxLength = maxLength;
   }
   
   /**
    * {@inheritDoc}
    */
   public EnumPropertyType getPropertyType()
   {
      return EnumPropertyType.STRING;
   }
   
   /**
    * @return String maxLength
    */
   public Long getMaxLength()
   {
      return maxLength;
   }
   
   /**
    * Setter for maxLength
    * 
    * @param maxLength maxLength
    */
   public void setMaxLength(Long maxLength)
   {
      this.maxLength = maxLength;
   }

}
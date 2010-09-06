/**
 *  Copyright (C) 2010 eXo Platform SAS.
 *
 *  This is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU Lesser General Public License as
 *  published by the Free Software Foundation; either version 2.1 of
 *  the License, or (at your option) any later version.
 *
 *  This software is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this software; if not, write to the Free
 *  Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 *  02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.xcmis.client.gwt.model.restatom;


/**
 * Created by The eXo Platform SAS.
 * 
 * @author <a href="mailto:zhulevaanna@gmail.com">Anna Zhuleva</a>
 * @version $Id:
 */

public class AtomLink
{
   /**
    * Relation.
    */
   private EnumLinkRelation relation;

   /**
    * Href.
    */
   private String href;

   /**
    * Type.
    */
   private String type;

   /**
    * @return the relation
    */
   public EnumLinkRelation getRelation()
   {
      return relation;
   }

   /**
    * @param relation
    *            the relation to set
    */
   public void setRelation(EnumLinkRelation relation)
   {
      this.relation = relation;
   }

   /**
    * @return the href
    */
   public String getHref()
   {
      return href;
   }

   /**
    * @param href
    *            the href to set
    */
   public void setHref(String href)
   {
      this.href = href;
   }

   /**
    * @return the type
    */
   public String getType()
   {
      return type;
   }

   /**
    * @param type
    *            the type to set
    */
   public void setType(String type)
   {
      this.type = type;
   }

}

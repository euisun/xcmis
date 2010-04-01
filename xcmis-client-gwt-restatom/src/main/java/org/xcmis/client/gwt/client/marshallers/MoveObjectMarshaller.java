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

package org.xcmis.client.gwt.client.marshallers;

import org.xcmis.client.gwt.client.marshallers.builder.ObjectXMLBuilder;
import org.xcmis.client.gwt.client.model.actions.MoveObject;
import org.xcmis.client.gwt.client.rest.Marshallable;

/**
 * Created by The eXo Platform SAS.
 * 
 * @author <a href="mailto:zhulevaanna@gmail.com">Anna Zhuleva</a>
 * @version $Id:
 */
public class MoveObjectMarshaller extends MoveObject implements Marshallable
{

   /**
    * Data for moving object.
    */
   private MoveObject moveObject;

   /**
    * @param moveObject moveObject
    */
   public MoveObjectMarshaller(MoveObject moveObject)
   {
      this.moveObject = moveObject;
   }

   
   /**
    * @see org.exoplatform.gwtframework.commons.rest.Marshallable#marshal()
    * @return String
    */
   public String marshal()
   {
      return ObjectXMLBuilder.moveItem(moveObject);
   }

}
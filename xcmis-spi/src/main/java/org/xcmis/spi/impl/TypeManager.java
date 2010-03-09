/**
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

package org.xcmis.spi.impl;

import org.xcmis.core.CmisTypeDefinitionType;
import org.xcmis.spi.CmisRuntimeException;
import org.xcmis.spi.ItemsIterator;
import org.xcmis.spi.StorageException;
import org.xcmis.spi.TypeNotFoundException;

/**
 * Produces type definition.
 * 
 * @author <a href="mailto:andrey.parfonov@exoplatform.com">Andrey Parfonov</a>
 * @version $Id$
 */
public interface TypeManager
{

   /**
    * Add new type in repository.
    * 
    * @param type type definition
    * @throws StorageException if type can't be added cause to storage internal
    *         problem
    * @throws CmisRuntimeException if any others errors occur
    */
   void addType(CmisTypeDefinitionType type) throws StorageException, CmisRuntimeException;

   /**
    * Get type definition for type <code>typeId</code> .
    * 
    * @param typeId type Id
    * @param includePropertyDefinition if <code>true</code> property definition
    *        should be included
    * @return type definition
    * @throws TypeNotFoundException if type <code>typeId</code> does not exists
    * @throws CmisRuntimeException if any others errors occur
    */
   CmisTypeDefinitionType getTypeDefinition(String typeId, boolean includePropertyDefinition)
      throws TypeNotFoundException, CmisRuntimeException;

   /**
    * Iterator over object types.
    * 
    * @param typeId the type id, if not <code>null</code> then return only
    *        specified Object Type and its direct descendant. If
    *        <code>null</code> then return base types
    * @param includePropertyDefinition <code>true</code> if property definition
    *        should be included <code>false</code> otherwise
    * @return set of base types or specified object type and its direct children
    * @throws TypeNotFoundException if type <code>typeId</code> does not exist
    * @throws CmisRuntimeException if any others errors occur
    */
   ItemsIterator<CmisTypeDefinitionType> getTypeChildren(String typeId, boolean includePropertyDefinitions)
      throws TypeNotFoundException, CmisRuntimeException;

   /**
    * Remove type definition for type <code>typeId</code> .
    * 
    * @param typeId type Id
    * @throws TypeNotFoundException if type <code>typeId</code> not found in
    *         repository
    * @throws StorageException if type can't be added cause to storage internal
    *         problem
    * @throws CmisRuntimeException if any others errors occur
    */
   void removeType(String typeId) throws TypeNotFoundException, StorageException, CmisRuntimeException;

}

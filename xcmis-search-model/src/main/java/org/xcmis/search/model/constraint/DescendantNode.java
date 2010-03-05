/*
 * Copyright (C) 2009 eXo Platform SAS.
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
package org.xcmis.search.model.constraint;

import org.xcmis.search.QueryObjectModelVisitor;
import org.xcmis.search.VisitException;
import org.xcmis.search.Visitors;
import org.xcmis.search.model.source.SelectorName;

/**
 * A constraint requiring that the selected node is a descendant of the node reachable by the supplied absolute path
 */
public class DescendantNode extends Constraint
{
   private static final long serialVersionUID = 1L;

   private final String ancestorPath;

   private final SelectorName selectorName;

   /**
    * Create a constraint requiring that the node identified by the selector is a descendant of the node reachable by the
    * supplied absolute path.
    * 
    * @param selectorName the name of the selector
    * @param ancestorPath the absolute path to the ancestor
    */
   public DescendantNode(SelectorName selectorName, String ancestorPath)
   {
      this.selectorName = selectorName;
      this.ancestorPath = ancestorPath;
   }

   /**
   * @see org.xcmis.search.model.QueryElement#accept(org.xcmis.search.QueryObjectModelVisitor)
   */
   public void accept(QueryObjectModelVisitor visitor) throws VisitException
   {

      visitor.visit(this);
   }

   /**
    * {@inheritDoc}
    * 
    * @see java.lang.Object#equals(java.lang.Object)
    */
   @Override
   public boolean equals(Object obj)
   {
      if (obj == this)
      {
         return true;
      }
      if (obj instanceof DescendantNode)
      {
         DescendantNode that = (DescendantNode)obj;
         if (!this.selectorName.equals(that.selectorName))
         {
            return false;
         }
         if (!this.ancestorPath.equals(that.ancestorPath))
         {
            return false;
         }
         return true;
      }
      return false;
   }

   /**
    * Get the path of the node that is to be the ancestor of the target node.
    * 
    * @return the path of the ancestor node; never null
    */
   public final String getAncestorPath()
   {
      return ancestorPath;
   }

   /**
    * Get the name of the selector for the node.
    * 
    * @return the selector name; never null
    */
   public final SelectorName getSelectorName()
   {
      return selectorName;
   }

   /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
   @Override
   public int hashCode()
   {
      return getSelectorName().hashCode();
   }

   /**
    * {@inheritDoc}
    * 
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString()
   {
      return Visitors.readable(this);
   }
}

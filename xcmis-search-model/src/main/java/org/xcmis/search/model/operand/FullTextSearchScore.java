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
package org.xcmis.search.model.operand;

import org.xcmis.search.QueryObjectModelVisitor;
import org.xcmis.search.VisitException;
import org.xcmis.search.Visitors;
import org.xcmis.search.model.constraint.Comparison;
import org.xcmis.search.model.ordering.Ordering;
import org.xcmis.search.model.source.SelectorName;

/**
 * A dynamic operand that evaluates to the full-text search score of a node given by a selector, used in a {@link Comparison}
 * constraint and {@link Ordering}s.
 */
public class FullTextSearchScore extends DynamicOperand
{
   private static final long serialVersionUID = 1L;

   /**
    * Create a dynamic operand that evaluates to the full-text search score of the node identified by the selector.
    * 
    * @param selectorName the name of the selector
    */
   public FullTextSearchScore(SelectorName selectorName)
   {
      super(selectorName);
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
      if (obj instanceof FullTextSearchScore)
      {
         FullTextSearchScore that = (FullTextSearchScore)obj;
         if (!this.getSelectorNames().equals(that.getSelectorNames()))
         {
            return false;
         }
         return true;
      }
      return false;
   }

   /**
    * Get the selector symbol upon which this operand applies.
    * 
    * @return the one selector names used by this operand; never null
    */
   public SelectorName getSelectorName()
   {
      return getSelectorNames().iterator().next();
   }

   /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
   @Override
   public int hashCode()
   {
      return getSelectorNames().hashCode();
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
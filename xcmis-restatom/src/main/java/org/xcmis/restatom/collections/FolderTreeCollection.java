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

package org.xcmis.restatom.collections;

import org.apache.abdera.i18n.iri.IRI;
import org.apache.abdera.model.Element;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.protocol.server.RequestContext;
import org.apache.abdera.protocol.server.context.ResponseContextException;
import org.xcmis.restatom.AtomCMIS;
import org.xcmis.spi.CMIS;
import org.xcmis.spi.Connection;
import org.xcmis.spi.IncludeRelationships;
import org.xcmis.spi.InvalidArgumentException;
import org.xcmis.spi.ItemsTree;
import org.xcmis.spi.ObjectNotFoundException;
import org.xcmis.spi.StorageException;
import org.xcmis.spi.StorageProvider;
import org.xcmis.spi.object.CmisObject;

import java.util.List;

/**
 * @author <a href="mailto:andrey.parfonov@exoplatform.com">Andrey Parfonov</a>
 * @version $Id: FolderTreeCollection.java 216 2010-02-12 17:19:50Z andrew00x $
 */
public class FolderTreeCollection extends FolderDescentantsCollection
{

   /**
    * Instantiates a new folder tree collection.
    * @param storageProvider TODO
    */
   public FolderTreeCollection(StorageProvider storageProvider)
   {
      super(storageProvider);
      setHref("/foldertree");
   }

   /**
    * {@inheritDoc}
    */
   public String getTitle(RequestContext request)
   {
      return "Folder Tree";
   }

   /**
    * {@inheritDoc}
    */
   @Override
   protected void addFeedDetails(Feed feed, RequestContext request) throws ResponseContextException
   {
      boolean includeAllowableActions = getBooleanParameter(request, AtomCMIS.PARAM_INCLUDE_ALLOWABLE_ACTIONS, false);
      boolean includePathSegments = getBooleanParameter(request, AtomCMIS.PARAM_INCLUDE_PATH_SEGMENT, false);
      // XXX At the moment get all properties from back-end. We need some of them for build correct feed.
      // Filter will be applied during build final Atom Document.
      //      String propertyFilter = request.getParameter(AtomCMIS.PARAM_FILTER);
      String propertyFilter = null;
      String renditionFilter = request.getParameter(AtomCMIS.PARAM_RENDITION_FILTER);
      IncludeRelationships includeRelationships;
      try
      {
         includeRelationships =
            request.getParameter(AtomCMIS.PARAM_INCLUDE_RELATIONSHIPS) == null
               || request.getParameter(AtomCMIS.PARAM_INCLUDE_RELATIONSHIPS).length() == 0 ? IncludeRelationships.NONE
               : IncludeRelationships.fromValue(request.getParameter(AtomCMIS.PARAM_INCLUDE_RELATIONSHIPS));
      }
      catch (IllegalArgumentException iae)
      {
         String msg = "Invalid parameter " + request.getParameter(AtomCMIS.PARAM_INCLUDE_RELATIONSHIPS);
         throw new ResponseContextException(msg, 400);
      }
      int depth = getIntegerParameter(request, AtomCMIS.PARAM_DEPTH, CMIS.DEPTH);
      Connection conn = null;
      try
      {
         conn = getConnection(request);
         List<ItemsTree<CmisObject>> tree =
            conn.getFolderTree(getId(request), depth, includeAllowableActions, includeRelationships,
               includePathSegments, true, propertyFilter, renditionFilter);
         if (tree.size() > 0)
         {
            // add cmisra:numItems
            Element numItems = feed.addExtension(AtomCMIS.NUM_ITEMS);
            numItems.setText(Integer.toString(tree.size()));

            for (ItemsTree<CmisObject> oifContainer : tree)
            {
               Entry e = feed.addEntry();
               IRI feedIri = new IRI(getFeedIriForEntry(oifContainer.getContainer(), request));
               addEntryDetails(request, e, feedIri, oifContainer.getContainer());
               if (oifContainer.getContainer().getPathSegment() != null)
               {
                  // add cmisra:pathSegment
                  Element pathSegment = e.addExtension(AtomCMIS.PATH_SEGMENT);
                  pathSegment.setText(oifContainer.getContainer().getPathSegment());
               }
               if (oifContainer.getChildren().size() > 0)
               {
                  addChildren(e, oifContainer.getChildren(), feedIri, request);
               }
            }
         }
      }
      catch (StorageException re)
      {
         throw new ResponseContextException(createErrorResponse(re, 500));
      }
      catch (ObjectNotFoundException onfe)
      {
         throw new ResponseContextException(createErrorResponse(onfe, 404));
      }
      catch (InvalidArgumentException iae)
      {
         throw new ResponseContextException(createErrorResponse(iae, 400));
      }
      catch (Throwable t)
      {
         throw new ResponseContextException(createErrorResponse(t, 500));
      }
      finally
      {
         if (conn != null)
         {
            conn.close();
         }
      }
   }

}

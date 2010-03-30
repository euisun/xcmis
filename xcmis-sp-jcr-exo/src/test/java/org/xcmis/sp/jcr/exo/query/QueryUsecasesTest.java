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

package org.xcmis.sp.jcr.exo.query;

import org.xcmis.spi.CMIS;
import org.xcmis.spi.ItemsIterator;
import org.xcmis.spi.UnfileObject;
import org.xcmis.spi.data.BaseContentStream;
import org.xcmis.spi.data.ContentStream;
import org.xcmis.spi.data.Document;
import org.xcmis.spi.data.Folder;
import org.xcmis.spi.object.impl.DecimalProperty;
import org.xcmis.spi.object.impl.StringProperty;
import org.xcmis.spi.query.Query;
import org.xcmis.spi.query.Result;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by The eXo Platform SAS. <br/>
 * Date:
 * 
 * @author <a href="karpenko.sergiy@gmail.com">Karpenko Sergiy</a>
 * @version $Id: QueryUsecasesTest.java 27 2010-02-08 07:49:20Z andrew00x $
 */
public class QueryUsecasesTest extends BaseQueryTest
{

   private final static String CONTENT_TYPE = "text/plain";

   private final static String NASA_DOCUMENT = "cmis:nasa-mission";

   private final static String PROPERTY_BOOSTER = "cmis:booster-name";

   private final static String PROPERTY_COMMANDER = "cmis:commander";

   private final static String PROPERTY_COMMAND_MODULE_PILOT = "cmis:command-module-pilot";

   private final static String PROPERTY_LUNAR_MODULE_PILOT = "cmis:lunar-module-pilot";

   private final static String PROPERTY_BOOSTER_MASS = "cmis:booster-mass";

   private final static String PROPERTY_SAMPLE_RETURNED = "cmis:sample-returned";

   private Folder testRoot;

   /**
    * @see org.xcmis.sp.jcr.exo.query.BaseQueryTest#setUp()
    */
   @Override
   public void setUp() throws Exception
   {
      super.setUp();
      testRoot = createFolder(rootFolder, "QueryUsecasesTest", "cmis:folder");
      // create data

   }

   /**
    * Test query with one 'and' two or' constraints.
    * <p>
    * Initial data:
    * <ul>
    * <li>document1: <b>Title</b> - Apollo 7 <b>exo:Booster</b> - Saturn 1B
    * <b>exo:Commander</b> - Walter M. Schirra</li>
    * <li>document2: <b>Title</b> - Apollo 8 <b>exo:Booster</b> - Saturn V
    * <b>exo:Commander</b> - Frank F. Borman, II</li>
    * <li>document3: <b>Title</b> - Apollo 13<b> exo:Booster</b> - Saturn V
    * <b>exo:Commander</b> - James A. Lovell, Jr.</li>
    * </ul>
    * <p>
    * Query : Select all documents where exo:Booster is 'Saturn V' and
    * exo:Commander is Frank F. Borman, II or James A. Lovell, Jr.
    * <p>
    * Expected result: document2 and document3
    * 
    * @throws Exception if an unexpected error occurs
    */
   public void testAndOrConstraint() throws Exception
   {

      List<Document> appolloContent = createNasaContent(testRoot);
      StringBuffer sql = new StringBuffer();
      sql.append("SELECT * ");
      sql.append("FROM ");
      sql.append(NASA_DOCUMENT);
      sql.append(" WHERE ");
      sql.append(PROPERTY_BOOSTER + " = " + "'Saturn V'");
      sql.append(" AND ( " + PROPERTY_COMMANDER + " = 'Frank F. Borman, II' ");
      sql.append("       OR " + PROPERTY_COMMANDER + " = 'James A. Lovell, Jr.' )");

      Query query = new Query(sql.toString(), true);

      ItemsIterator<Result> result = storage.query(query);
      // check results
      checkResult(result, new Document[]{appolloContent.get(1), appolloContent.get(2)});

   }

   /**
    * Test IN_FOLDER constraint.
    * <p>
    * Initial data:
    * <ul>
    * <li>folder1:
    * <li>doc1: <b>Title</b> - node1
    * <li>folder2
    * <li>doc2: <b>Title</b> - node2
    * </ul>
    * <p>
    * Query : Select all documents that are in folder1.
    * <p>
    * Expected result: doc1
    * 
    * @throws Exception if an unexpected error occurs
    */
   public void testDocumentInFolderConstrain() throws Exception
   {
      // create data
      Folder testRoot1 = createFolder(testRoot, "testDocumentInFolderConstrain1", "cmis:folder");
      Folder testRoot2 = createFolder(testRoot, "testDocumentInFolderConstrain2", "cmis:folder");

      List<Document> appolloContent = createNasaContent(testRoot1);
      List<Document> appolloContent2 = createNasaContent(testRoot2);

      String statement = "SELECT * FROM " + NASA_DOCUMENT + " WHERE IN_FOLDER( '" + testRoot2.getObjectId() + "')";

      Query query = new Query(statement, true);
      ItemsIterator<Result> result = storage.query(query);

      // check results
      checkResult(result, appolloContent2.toArray(new Document[appolloContent2.size()]));

   }

   /**
    * Test IN_FOLDER constraint.
    * <p>
    * Initial data:
    * <p>
    * folder1:
    * <p>
    * -doc1: <b>Title</b> - node1
    * <p>
    * folder2:
    * <p>
    * -doc2: <b>Title</b> - node2
    * <p>
    * -folder3:
    * <p>
    * --folder4 </ul>
    * <p>
    * Query : Select all folders that are in folder1.
    * <p>
    * Expected result: folder3
    * 
    * @throws Exception if an unexpected error occurs
    */
   public void testFolderInFolderConstrain() throws Exception
   {
      // create data
      Folder folder1 = createFolder(testRoot, "folder1", "cmis:folder");
      storage.saveObject(folder1);

      Document doc1 = createDocument(folder1, "node1", NASA_DOCUMENT, "hello world".getBytes(), "text/plain");
      storage.saveObject(doc1);

      Folder folder2 = createFolder(testRoot, "folder2", "cmis:folder");
      storage.saveObject(folder2);

      Document doc2 = createDocument(folder2, "node2", NASA_DOCUMENT, "hello world".getBytes(), "text/plain");
      storage.saveObject(doc2);

      Folder folder3 = createFolder(folder1, "folder3", "cmis:folder");
      storage.saveObject(folder3);

      Document doc3 = createDocument(folder3, "node3", NASA_DOCUMENT, "hello world".getBytes(), "text/plain");
      storage.saveObject(doc3);

      Folder folder4 = createFolder(folder3, "folder4", "cmis:folder");
      storage.saveObject(folder4);

      String statement = "SELECT * FROM cmis:folder  WHERE IN_FOLDER( '" + folder1.getObjectId() + "')";

      Query query = new Query(statement, true);
      ItemsIterator<Result> result = storage.query(query);

      // check results
      checkResult(result, new Folder[]{folder3});

   }

   //
   //   /**
   //    * Constraints with multi-valued properties is not supported.
   //    * 
   //    * @throws Exception
   //    */
   //   // XXX temporary excluded
   //   public void _testAnyInConstraint() throws Exception
   //   {
   //      // create data
   //      String name = "fileCS2.doc";
   //      String name2 = "fileCS3.doc";
   //      String contentType = "text/plain";
   //
   //      Document folder = createFolder(root, "CASETest");
   //
   //      Document doc1 = createDocument(folder.getObjectId(), name, new byte[0], contentType);
   //      doc1.setDecimals("multivalueLong", new BigDecimal[]{new BigDecimal(3), new BigDecimal(5), new BigDecimal(10)});
   //      doc1.setStrings("multivalueString", new String[]{"bla-bla"});
   //      doc1.save();
   //
   //      Document doc2 = createDocument(folder.getObjectId(), name2, new byte[0], contentType);
   //      doc2.setDecimals("multivalueLong", new BigDecimal[]{new BigDecimal(15), new BigDecimal(10)});
   //      doc2.setStrings("multivalueString", new String[]{"bla-bla"});
   //      doc2.save();
   //
   //      String statement =
   //         "SELECT * FROM " + NASA_DOCUMENT + " WHERE ANY multivalueLong IN ( 3 , 5, 6 ) ";
   //
   //      Query query = new Query(statement, true);
   //
   //      ItemsIterator<Result> result = storage.query(query);
   //
   //      // check results
   //      checkResult(result, new Document[]{doc1});
   //   }
   //
   /**
    * Test fulltext constraint.
    * <p>
    * Initial data:
    * <p>
    * see createApolloContent()
    * <p>
    * Query : Select all documents where data contains "moon" word.
    * <p>
    * Expected result: document1 and document2
    * 
    * @throws Exception if an unexpected error occurs
    */
   public void testFulltextConstraint() throws Exception
   {

      List<Document> appolloContent = createNasaContent(testRoot);

      String statement1 = "SELECT * FROM " + NASA_DOCUMENT + " WHERE CONTAINS(\"moon\")";
      Query query = new Query(statement1, true);
      ItemsIterator<Result> result = storage.query(query);

      assertEquals(2, result.size());
      checkResult(result, new Document[]{appolloContent.get(1), appolloContent.get(2)});

      String statement2 = "SELECT * FROM " + NASA_DOCUMENT + " WHERE CONTAINS(\"Moon\")";
      query = new Query(statement2, true);
      ItemsIterator<Result> result2 = storage.query(query);

      assertEquals(2, result2.size());
      checkResult(result2, new Document[]{appolloContent.get(1), appolloContent.get(2)});

   }

   /**
    * Test 'IN' constraint.
    * <p>
    * Initial data:
    * <ul>
    * <li>document1: <b>Title</b> - node1 <b>name</b> - supervisor
    * <li>document2: <b>Title</b> - node2 <b>name</b> - anyname
    * </ul>
    * <p>
    * Query : Select all documents where name is in set {'admin' , 'supervisor' ,
    * 'Vasya' }.
    * <p>
    * Expected result: document2 and document3
    * 
    * @throws Exception if an unexpected error occurs
    */
   public void testINConstraint() throws Exception
   {

      List<Document> appolloContent = createNasaContent(testRoot);

      String statement =
         "SELECT * FROM " + NASA_DOCUMENT + " WHERE " + PROPERTY_COMMANDER
            + " IN ('Virgil I. Grissom', 'Frank F. Borman, II', 'Charles Conrad, Jr.')";

      Query query = new Query(statement, true);
      ItemsIterator<Result> result = storage.query(query);
      assertEquals(1, result.size());
      checkResult(result, new Document[]{appolloContent.get(1)});

   }

   //
   //   /**
   //    * Test JOIN with condition constraint.
   //    * <p>
   //    * Initial data:
   //    * <ul>
   //    * <li>folder1: <b>folderName</b> - folderOne
   //    * <li>doc1: <b>Title</b> - node1 <b>parentFolderName</b> - folderOne
   //    * <li>folder2: b>folderName</b> - folderTwo
   //    * <li>doc2: <b>Title</b> - node2 <b>parentFolderName</b> - folderThree
   //    * </ul>
   //    * <p>
   //    * Query : Select all documents and folders where folders folderName equal to
   //    * document parentFolderName.
   //    * <p>
   //    * Expected result: doc1 and folder1
   //    * 
   //    * @throws Exception if an unexpected error occurs
   //    */
   //   // XXX temporary excluded
   //   public void _testJoinWithCondition() throws Exception
   //   {
   //      // create data
   //      Document folder1 = this.createFolder(root, "folder1");
   //      folder1.setString("folderName", "folderOne");
   //
   //      Document doc1 = createDocument(folder1.getObjectId(), "node1", "hello world".getBytes(), "text/plain");
   //      doc1.setString("parentFolderName", "folderOne");
   //
   //      Document folder2 = this.createFolder(root, "folder2");
   //      folder2.setString("folderName", "folderTwo");
   //
   //      Document doc2 = createDocument(folder2.getObjectId(), "node1", "hello world".getBytes(), "text/plain");
   //      doc2.setString("parentFolderName", "folderThree");
   //
   //      String statement =
   //         "SELECT doc.* FROM " + NASA_DOCUMENT + " AS doc LEFT JOIN " + JcrCMIS.NT_FOLDER
   //            + " AS folder ON (doc.parentFolderName = folder.folderName)";
   //
   //      Query query = new Query(statement, true);
   //      ItemsIterator<Result> result = storage.query(query);
   //
   //      assertEquals(1, result.size());
   //      // TODO check results - must doc1 and folder1
   //   }
   //
   /**
    * Test LIKE constraint.
    * <p>
    * Initial data:
    * <ul>
    * <li>doc1: <b>Title</b> - node1 <b>prop</b> - administrator
    * <li>doc2: <b>Title</b> - node2 <b>prop</b> - admin
    * <li>doc3: <b>Title</b> - node2 <b>prop</b> - radmin
    * </ul>
    * <p>
    * Query : Select all documents where prop begins with "ad".
    * <p>
    * Expected result: doc1, doc2
    * 
    * @throws Exception if an unexpected error occurs
    */
   public void testLIKEConstraint() throws Exception
   {
      List<Document> appolloContent = createNasaContent(testRoot);

      String statement = "SELECT * FROM " + NASA_DOCUMENT + " AS doc WHERE " + PROPERTY_COMMANDER + " LIKE 'James%'";

      Query query = new Query(statement, true);

      ItemsIterator<Result> result = storage.query(query);

      // check results
      assertEquals(1, result.size());
      checkResult(result, new Document[]{appolloContent.get(2)});

   }

   /**
    * Test LIKE constraint with escape symbols.
    * <p>
    * Initial data:
    * <ul>
    * <li>doc1: <b>Title</b> - node1 <b>prop</b> - ad%min master
    * <li>doc2: <b>Title</b> - node2 <b>prop</b> - admin operator
    * <li>doc3: <b>Title</b> - node2 <b>prop</b> - radmin
    * </ul>
    * <p>
    * Query : Select all documents where prop like 'ad\\%min%'.
    * <p>
    * Expected result: doc1
    * 
    * @throws Exception if an unexpected error occurs
    */
   public void testLIKEConstraintEscapeSymbols() throws Exception
   {

      Document doc1 = createDocument(testRoot, "node1", NASA_DOCUMENT, "hello world".getBytes(), "text/plain");
      doc1.setProperty(new StringProperty(PROPERTY_COMMANDER, PROPERTY_COMMANDER, PROPERTY_COMMANDER,
         PROPERTY_COMMANDER, "ad%min master"));

      Document doc2 = createDocument(testRoot, "node2", NASA_DOCUMENT, "hello world".getBytes(), "text/plain");
      doc2.setProperty(new StringProperty(PROPERTY_COMMANDER, PROPERTY_COMMANDER, PROPERTY_COMMANDER,
         PROPERTY_COMMANDER, "admin operator"));

      Document doc3 = createDocument(testRoot, "node3", NASA_DOCUMENT, "hello world".getBytes(), "text/plain");
      doc3.setProperty(new StringProperty(PROPERTY_COMMANDER, PROPERTY_COMMANDER, PROPERTY_COMMANDER,
         PROPERTY_COMMANDER, "radmin"));

      storage.saveObject(doc1);
      storage.saveObject(doc2);
      storage.saveObject(doc3);

      String statement =
         "SELECT * FROM " + NASA_DOCUMENT + " AS doc WHERE  " + PROPERTY_COMMANDER + " LIKE 'ad\\%min%'";

      Query query = new Query(statement, true);
      ItemsIterator<Result> result = storage.query(query);

      // check results
      assertEquals(1, result.size());
      checkResult(result, new Document[]{doc1});

   }

   /**
    * Test NOT constraint.
    * <p>
    * Initial data:
    * <ul>
    * <li>doc1: <b>Title</b> - node1 <b>content</b> - hello world
    * <li>doc2: <b>Title</b> - node2 <b>content</b> - hello
    * </ul>
    * <p>
    * Query : Select all documents that not contains "world" word.
    * <p>
    * Expected result: doc2
    * 
    * @throws Exception if an unexpected error occurs
    */
   public void testNOTConstraint() throws Exception
   {

      Document doc1 = createDocument(testRoot, "node1", NASA_DOCUMENT, "hello world".getBytes(), "text/plain");

      Folder folder2 = createFolder(testRoot, "folder2", "cmis:folder");
      Document doc2 = createDocument(folder2, "node2", NASA_DOCUMENT, "hello".getBytes(), "text/plain");

      String statement = "SELECT * FROM " + NASA_DOCUMENT + " WHERE NOT CONTAINS(\"world\")";

      Query query = new Query(statement, true);
      ItemsIterator<Result> result = storage.query(query);

      checkResult(result, new Document[]{doc2});

   }

   /**
    * Test NOT IN constraint.
    * <p>
    * Initial data:
    * <ul>
    * <li>doc1: <b>Title</b> - node1 <b>long</b> - 3
    * <li>doc2: <b>Title</b> - node2 <b>long</b> - 15
    * </ul>
    * <p>
    * Query : Select all documents where long property not in set {15 , 20}.
    * <p>
    * Expected result: doc1
    * 
    * @throws Exception if an unexpected error occurs
    */
   public void testNotINConstraint() throws Exception
   {
      List<Document> appolloContent = createNasaContent(testRoot);

      String statement =
         "SELECT * FROM " + NASA_DOCUMENT + " WHERE " + PROPERTY_COMMANDER
            + " NOT IN ('Walter M. Schirra', 'James A. Lovell, Jr.')";

      Query query = new Query(statement, true);
      ItemsIterator<Result> result = storage.query(query);

      checkResult(result, new Document[]{appolloContent.get(1), appolloContent.get(3)});

   }

   /**
    * Test NOT NOT (not counteraction).
    * <p>
    * Initial data:
    * <ul>
    * <li>doc1: <b>Title</b> - node1 <b>long</b> - 3
    * <li>doc2: <b>Title</b> - node2 <b>long</b> - 15
    * </ul>
    * <p>
    * Query : Select all documents where long property in set {15 , 20} (NOT NOT
    * IN).
    * <p>
    * Expected result: doc2
    * 
    * @throws Exception if an unexpected error occurs
    */
   public void testNotNotINConstraint() throws Exception
   {

      List<Document> appolloContent = createNasaContent(testRoot);

      String statement =
         "SELECT * FROM " + NASA_DOCUMENT + " WHERE  NOT (" + PROPERTY_COMMANDER + " NOT IN ('James A. Lovell, Jr.'))";

      Query query = new Query(statement, true);
      ItemsIterator<Result> result = storage.query(query);

      checkResult(result, new Document[]{appolloContent.get(2)});

   }

   /**
    * Test Order By desc.
    * <p>
    * Initial data:
    * <ul>
    * <li>doc1: <b>Title</b> - node1 <b>long</b> - 3
    * <li>doc2: <b>Title</b> - node2 <b>long</b> - 15
    * </ul>
    * <p>
    * Query : Order by exo:Commander property value
    * <p>
    * Expected result: doc2
    * 
    * @throws Exception if an unexpected error occurs
    */
   public void testOrderByFieldDesc() throws Exception
   {

      List<Document> appolloContent = createNasaContent(testRoot);

      StringBuffer sql = new StringBuffer();
      sql.append("SELECT  ");
      sql.append(CMIS.LAST_MODIFIED_BY + " as last , ");
      sql.append(CMIS.OBJECT_ID + " , ");
      sql.append(CMIS.LAST_MODIFICATION_DATE);
      sql.append(" FROM ");
      sql.append(NASA_DOCUMENT);
      sql.append(" ORDER BY ");
      sql.append(PROPERTY_COMMANDER);
      sql.append(" DESC");

      String statement = sql.toString();

      Query query = new Query(statement, true);
      ItemsIterator<Result> result = storage.query(query);
      // Walter M. Schirra (0)
      // James A. Lovell, Jr. (2)
      // Frank F. Borman, II (1)
      //Eugene A. Cernan  (3)
      checkResultOrder(result, new Document[]{appolloContent.get(0), appolloContent.get(2), appolloContent.get(1),
         appolloContent.get(3)});

   }

   /**
    * Test ORDER BY ASC.
    * <p>
    * Initial data: see createApolloContent
    * <p>
    * Query : Select all documents and order by propertyComander values
    * ascending.
    * <p>
    * Expected result: doc2, doc3, doc1
    * 
    * @throws Exception if an unexpected error occurs
    */
   public void testOrderByFieldAsk() throws Exception
   {

      List<Document> appolloContent = createNasaContent(testRoot);

      StringBuffer sql = new StringBuffer();
      sql.append("SELECT ");
      sql.append(CMIS.LAST_MODIFIED_BY + ", ");
      sql.append(CMIS.OBJECT_ID + ", ");
      sql.append(CMIS.LAST_MODIFICATION_DATE);
      sql.append(" FROM ");
      sql.append(NASA_DOCUMENT);
      sql.append(" ORDER BY ");
      sql.append(PROPERTY_COMMANDER);

      String statement = sql.toString();

      Query query = new Query(statement, true);
      ItemsIterator<Result> result = storage.query(query);
      //Eugene A. Cernan  (3)
      // Frank F. Borman, II (1)
      // James A. Lovell, Jr. (2)
      // Walter M. Schirra (0)

      checkResultOrder(result, new Document[]{appolloContent.get(3), appolloContent.get(1), appolloContent.get(2),
         appolloContent.get(0)});

   }

   /**
    * Test ORDER BY default.
    * <p>
    * Initial data: see createApolloContent
    * <p>
    * Query : Select all documents and order by propertyComander values
    * ascending.
    * <p>
    * Expected result: doc3, doc1, doc2
    * 
    * @throws Exception if an unexpected error occurs
    */
   public void testOrderByDefault() throws Exception
   {

      List<Document> appolloContent = createNasaContent(testRoot);

      StringBuffer sql = new StringBuffer();
      sql.append("SELECT ");
      sql.append(CMIS.LAST_MODIFIED_BY + ", ");
      sql.append(CMIS.OBJECT_ID + ", ");
      sql.append(CMIS.LAST_MODIFICATION_DATE);
      sql.append(" FROM ");
      sql.append(NASA_DOCUMENT);

      String statement = sql.toString();

      Query query = new Query(statement, true);
      ItemsIterator<Result> result = storage.query(query);
      // Apollo 13 (2)
      // Apollo 17 (3)
      // Apollo 7 (0)
      // Apollo 8 (1)
      checkResultOrder(result, new Document[]{appolloContent.get(2), appolloContent.get(3), appolloContent.get(0),
         appolloContent.get(1)});

   }

   /**
    * Test ORDER BY SCORE().
    * <p>
    * Initial data: see createApolloContent
    * <p>
    * Query : Select all documents and order by propertyComander values
    * ascending.
    * <p>
    * Expected result: doc3, doc1, doc2
    * 
    * @throws Exception if an unexpected error occurs
    */
   public void testOrderByScore() throws Exception
   {

      List<Document> appolloContent = createNasaContent(testRoot);

      StringBuffer sql = new StringBuffer();
      sql.append("SELECT ");
      sql.append(" SCORE() AS scoreCol, ");
      sql.append(CMIS.LAST_MODIFIED_BY + ", ");
      sql.append(CMIS.OBJECT_ID + ", ");
      sql.append(CMIS.LAST_MODIFICATION_DATE);
      sql.append(" FROM ");
      sql.append(NASA_DOCUMENT);
      sql.append(" WHERE CONTAINS(\"moon\") ");
      sql.append(" ORDER BY SCORE() ");

      String statement = sql.toString();

      Query query = new Query(statement, true);
      ItemsIterator<Result> result = storage.query(query);
      // Apollo 13 (2)
      // Apollo 8 (1)
      checkResultOrder(result, new Document[]{appolloContent.get(2), appolloContent.get(1)});

   }

   //
   /**
    * Test property existence constraint (IS [NOT] NULL) .
    * <p>
    * Initial data:
    * <ul>
    * <li>doc1: <b>Title</b> - node1 <b>prop</b> - test string
    * <li>doc2: <b>Title</b> - node2
    * </ul>
    * <p>
    * Query : Select all documents that has "prop" property (IS NOT NULL).
    * <p>
    * Expected result: doc1
    * 
    * @throws Exception if an unexpected error occurs
    */
   public void testPropertyExistence() throws Exception
   {
      // Document folder1 = createFolder(root, "CASETest");

      Document doc1 = createDocument(testRoot, "node1", NASA_DOCUMENT, "hello world".getBytes(), "text/plain");
      doc1.setProperty(new StringProperty(PROPERTY_COMMANDER, PROPERTY_COMMANDER, PROPERTY_COMMANDER,
         PROPERTY_COMMANDER, "James A. Lovell, Jr."));
      storage.saveObject(doc1);
      Document doc2 = createDocument(testRoot, "node2", NASA_DOCUMENT, "hello".getBytes(), "text/plain");
      storage.saveObject(doc2);

      String statement = "SELECT * FROM " + NASA_DOCUMENT + " WHERE " + PROPERTY_COMMANDER + " IS NOT NULL";
      Query query = new Query(statement, true);

      ItemsIterator<Result> result = storage.query(query);
      checkResult(result, new Document[]{doc1});

   }

   /**
    * Test SCORE as column.
    * <p>
    * Initial data:
    * <ul>
    * <li>doc1: <b>Title</b> - node1 <b>content</b> - hello world
    * <li>doc2: <b>Title</b> - node2 <b>content</b> - hello
    * </ul>
    * <p>
    * Query : Select all documents that contains hello or world words, and show
    * search score .
    * <p>
    * Expected result: doc1 and doc2 score numbers.
    * 
    * @throws Exception if an unexpected error occurs
    */
   public void testScoreAsColumn() throws Exception
   {
      List<Document> appolloContent = createNasaContent(testRoot);

      String statement =
         "SELECT SCORE() AS scoreCol , " + CMIS.NAME + " AS id FROM " + NASA_DOCUMENT
            + " WHERE CONTAINS(\"hello OR world\") ORDER BY SCORE()";

      Query query = new Query(statement, true);
      ItemsIterator<Result> result = storage.query(query);

      // check result
      while (result.hasNext())
      {
         Result next = result.next();
         assertTrue(next.getScore().getScoreValue().doubleValue() > 0);
      }

   }

   /**
    * Test IN_TREE constraint.
    * <p>
    * Initial data:
    * <p>
    * folder1
    * <p>
    * - document doc1
    * <p>
    * - folder2
    * <p>
    * -- document doc2
    * <p>
    * Query : Select all documents that are in tree of folder1.
    * <p>
    * Expected result: doc1,doc2.
    * 
    * @throws Exception if an unexpected error occurs
    */
   public void testTreeConstrain() throws Exception
   {
      // create data
      Folder folder1 = createFolder(testRoot, "folder1", "cmis:folder");

      Document doc1 = createDocument(folder1, "node1", NASA_DOCUMENT, "hello world".getBytes(), "text/plain");

      Folder subfolder1 = createFolder(folder1, "folder2", "cmis:folder");

      Document doc2 = createDocument(subfolder1, "node1", NASA_DOCUMENT, "hello world".getBytes(), "text/plain");

      String statement = "SELECT * FROM " + NASA_DOCUMENT + " WHERE IN_TREE('" + folder1.getObjectId() + "')";

      Query query = new Query(statement, true);
      ItemsIterator<Result> result = storage.query(query);

      checkResult(result, new Document[]{doc1, doc2});
   }

   /**
    * Test not equal comparison (<>).
    * <p>
    * Initial data:
    * <ul>
    * <li>doc1: <b>Title</b> - node1 <b>long</b> - 3
    * <li>doc2: <b>Title</b> - node2 <b>long</b> - 15
    * </ul>
    * <p>
    * Query : Select all documents property long not equal to 3.
    * <p>
    * Expected result: doc2.
    * 
    * @throws Exception if an unexpected error occurs
    */
   public void testNotEqualDecimal() throws Exception
   {
      // create data
      String name = "fileCS2.doc";
      String name2 = "fileCS3.doc";
      String contentType = "text/plain";

      Folder folder = createFolder(testRoot, "NotEqualDecimal", "cmis:folder");
      storage.saveObject(folder);
      Document doc1 = createDocument(folder, name, NASA_DOCUMENT, new byte[0], contentType);
      doc1.setProperty(new DecimalProperty(PROPERTY_BOOSTER_MASS, PROPERTY_BOOSTER_MASS, PROPERTY_BOOSTER_MASS,
         PROPERTY_BOOSTER_MASS, new BigDecimal(3)));
      storage.saveObject(doc1);

      Document doc2 = createDocument(folder, name2, NASA_DOCUMENT, new byte[0], contentType);
      doc2.setProperty(new DecimalProperty(PROPERTY_BOOSTER_MASS, PROPERTY_BOOSTER_MASS, PROPERTY_BOOSTER_MASS,
         PROPERTY_BOOSTER_MASS, new BigDecimal(15)));
      storage.saveObject(doc2);
      String statement = "SELECT * FROM " + NASA_DOCUMENT + " WHERE " + PROPERTY_BOOSTER_MASS + " <> 3";

      Query query = new Query(statement, true);
      ItemsIterator<Result> result = storage.query(query);

      checkResult(result, new Document[]{doc2});
   }

   /**
    * Test more than comparison (>).
    * <p>
    * Initial data:
    * <ul>
    * <li>doc1: <b>Title</b> - node1 <b>long</b> - 3
    * <li>doc2: <b>Title</b> - node2 <b>long</b> - 15
    * </ul>
    * <p>
    * Query : Select all documents property long more than 5.
    * <p>
    * Expected result: doc2.
    * 
    * @throws Exception if an unexpected error occurs
    */
   public void testMoreThanDecimal() throws Exception
   {
      // create data
      String name = "fileCS2.doc";
      String name2 = "fileCS3.doc";
      String contentType = "text/plain";

      Folder folder = createFolder(testRoot, "CASETest", "cmis:folder");
      storage.saveObject(folder);

      Document doc1 = createDocument(folder, name, NASA_DOCUMENT, new byte[0], contentType);
      doc1.setProperty(new DecimalProperty(PROPERTY_BOOSTER_MASS, PROPERTY_BOOSTER_MASS, PROPERTY_BOOSTER_MASS,
         PROPERTY_BOOSTER_MASS, new BigDecimal(3)));
      storage.saveObject(doc1);

      Document doc2 = createDocument(folder, name2, NASA_DOCUMENT, new byte[0], contentType);
      doc2.setProperty(new DecimalProperty(PROPERTY_BOOSTER_MASS, PROPERTY_BOOSTER_MASS, PROPERTY_BOOSTER_MASS,
         PROPERTY_BOOSTER_MASS, new BigDecimal(15)));
      storage.saveObject(doc2);

      String statement = "SELECT * FROM " + NASA_DOCUMENT + " WHERE " + PROPERTY_BOOSTER_MASS + " > 5";

      Query query = new Query(statement, true);
      ItemsIterator<Result> result = storage.query(query);

      checkResult(result, new Document[]{doc2});
   }

   /**
    * Test not equal comparison (<>) string.
    * <p>
    * Initial data:
    * <ul>
    * <li>doc1: <b>Title</b> - node1 <b>strprop</b> - test word first
    * <li>doc2: <b>Title</b> - node2 <b>strprop</b> - test word second
    * </ul>
    * <p>
    * Query : Select all documents property strprop not equal to
    * "test word second".
    * <p>
    * Expected result: doc1.
    * 
    * @throws Exception if an unexpected error occurs
    */
   public void testNotEqualString() throws Exception
   {
      // create data
      String name = "fileCS2.doc";
      String name2 = "fileCS3.doc";
      String contentType = "text/plain";

      Folder folder = createFolder(testRoot, "CASETest", "cmis:folder");
      storage.saveObject(folder);

      Document doc1 = createDocument(folder, name, NASA_DOCUMENT, new byte[0], contentType);
      doc1.setProperty(new StringProperty(PROPERTY_COMMANDER, PROPERTY_COMMANDER, PROPERTY_COMMANDER,
         PROPERTY_COMMANDER, "test word first"));
      storage.saveObject(doc1);

      Document doc2 = createDocument(folder, name2, NASA_DOCUMENT, new byte[0], contentType);
      doc2.setProperty(new StringProperty(PROPERTY_COMMANDER, PROPERTY_COMMANDER, PROPERTY_COMMANDER,
         PROPERTY_COMMANDER, "test word second"));
      storage.saveObject(doc2);

      String statement = "SELECT * FROM " + NASA_DOCUMENT + " WHERE " + PROPERTY_COMMANDER + " <> 'test word second'";

      Query query = new Query(statement, true);
      ItemsIterator<Result> result = storage.query(query);

      checkResult(result, new Document[]{doc1});
   }

   /**
    * Test fulltext search from jcr:content.
    * <p>
    * Initial data:
    * <ul>
    * <li>doc1: <b>Title</b> - node1 <b>content</b> - "There must be test word"
    * <li>doc2: <b>Title</b> - node2 <b>content</b> - " Test word is not here"
    * </ul>
    * <p>
    * Query : Select all documents that contains "here" word.
    * <p>
    * Expected result: doc2.
    * 
    * @throws Exception if an unexpected error occurs
    */
   public void testSimpleFulltext() throws Exception
   {
      // create data
      String name1 = "fileFirst";
      String name2 = "fileSecond";
      String contentType = "text/plain";

      Folder folder = createFolder(testRoot, "SimpleFullTextTest", "cmis:folder");
      storage.saveObject(folder);
      Document doc1 = createDocument(folder, name1, NASA_DOCUMENT, new byte[0], contentType);
      doc1.setProperty(new StringProperty(PROPERTY_COMMANDER, PROPERTY_COMMANDER, PROPERTY_COMMANDER,
         PROPERTY_COMMANDER, "There must be test word"));
      storage.saveObject(doc1);

      Document doc2 = createDocument(folder, name2, NASA_DOCUMENT, new byte[0], contentType);
      doc2.setProperty(new StringProperty(PROPERTY_COMMANDER, PROPERTY_COMMANDER, PROPERTY_COMMANDER,
         PROPERTY_COMMANDER, "Test word is not here"));
      storage.saveObject(doc2);
      String statement = "SELECT * FROM " + NASA_DOCUMENT + " WHERE CONTAINS(\"here\")";

      Query query = new Query(statement, true);
      ItemsIterator<Result> result = storage.query(query);

      checkResult(result, new Document[]{doc2});
   }

   //
   //   /**
   //    * Test complex fulltext query.
   //    * <p>
   //    * Initial data:
   //    * <ul>
   //    * <li>doc1: <b>Title</b> - node1 <b>strprop</b> - "There must be test word"
   //    * <li>doc2: <b>Title</b> - node2 <b>strprop</b> -
   //    * " Test word is not here. Another check-word."
   //    * <li>doc3: <b>Title</b> - node3 <b>strprop</b> - "There must be check-word."
   //    * </ul>
   //    * <p>
   //    * Query : Select all documents that contains "There must" phrase and do not
   //    * contain "check-word" word.
   //    * <p>
   //    * Expected result: doc1.
   //    * 
   //    * @throws Exception if an unexpected error occurs
   //    */
   //   public void testExtendedFulltext() throws Exception
   //   {
   //      // create data
   //      String name1 = "fileCS1.doc";
   //      String name2 = "fileCS2.doc";
   //      String name3 = "fileCS3.doc";
   //      String contentType = "text/plain";
   //
   //      Document folder = createFolder(root, "CASETest");
   //
   //      Document doc1 = createDocument(folder.getObjectId(), name1, new byte[0], contentType);
   //      doc1.setString("strprop", "There must be test word");
   //
   //      Document doc2 = createDocument(folder.getObjectId(), name2, new byte[0], contentType);
   //      doc2.setString("strprop", " Test word is not here. Another check-word.");
   //
   //      Document doc3 = createDocument(folder.getObjectId(), name3, new byte[0], contentType);
   //      doc3.setString("strprop", "There must be check-word.");
   //
   //      String statement =
   //         "SELECT * FROM " + NASA_DOCUMENT + " WHERE CONTAINS(\"\\\"There must\\\" -\\\"check\\-word\\\"\")";
   //
   //      Query query = new Query(statement, true);
   //      ItemsIterator<Result> result = storage.query(query);
   //
   //      checkResult(result, new Document[]{doc1});
   //   }
   //
   //   /**
   //    * Same as testNOTConstraint.
   //    */
   //   public void testNotContains() throws Exception
   //   {
   //      // create data
   //      String name = "fileCS2.doc";
   //      String name2 = "fileCS3.doc";
   //      String contentType = "text/plain";
   //
   //      Document folder = createFolder(root, "NotContains");
   //
   //      Document doc1 = createDocument(folder.getObjectId(), name, new byte[0], contentType);
   //      doc1.setString("strprop", "There must be test word");
   //      doc1.save();
   //
   //      Document doc2 = createDocument(folder.getObjectId(), name2, new byte[0], contentType);
   //      doc2.setString("strprop", " Test word is not here");
   //      doc2.save();
   //
   //      String statement = "SELECT * FROM " + NASA_DOCUMENT + " WHERE NOT CONTAINS(\"here\")";
   //
   //      Query query = new Query(statement, true);
   //      ItemsIterator<Result> result = storage.query(query);
   //
   //      checkResult(result, new Document[]{doc1});
   //   }
   //
   //   /**
   //    * Test comparison of boolean property.
   //    * <p>
   //    * Initial data:
   //    * <ul>
   //    * <li>doc1: <b>Title</b> - node1 <b>boolprop</b> - true
   //    * <li>doc2: <b>Title</b> - node2 <b>boolprop</b> - false
   //    * </ul>
   //    * <p>
   //    * Query : Select all documents where boolprop equal to false.
   //    * <p>
   //    * Expected result: doc2.
   //    * 
   //    * @throws Exception if an unexpected error occurs
   //    */
   //   public void testBooleanConstraint() throws Exception
   //   {
   //      // create data
   //      String name = "fileCS2.doc";
   //      String name2 = "fileCS3.doc";
   //      String contentType = "text/plain";
   //
   //      Document folder = this.createFolder(root, "CASETest");
   //
   //      Document doc1 = createDocument(folder.getObjectId(), name, new byte[0], contentType);
   //      doc1.setBoolean("boolprop", true);
   //      doc1.save();
   //
   //      Document doc2 = createDocument(folder.getObjectId(), name2, new byte[0], contentType);
   //      doc2.setBoolean("boolprop", false);
   //      doc2.save();
   //
   //      String statement = "SELECT * FROM " + NASA_DOCUMENT + " WHERE (boolprop = FALSE )";
   //
   //      Query query = new Query(statement, true);
   //      ItemsIterator<Result> result = storage.query(query);
   //
   //      checkResult(result, new Document[]{doc2});
   //   }
   //
   //   /**
   //    * Test comparison of date property.
   //    * <p>
   //    * Initial data:
   //    * <ul>
   //    * <li>doc1: <b>Title</b> - node1 <b>dateProp</b> - 2009-08-08
   //    * <li>doc2: <b>Title</b> - node2 <b>dateProp</b> - 2009-08-08
   //    * </ul>
   //    * <p>
   //    * Query : Select all documents where dateProp more than 2007-01-01.
   //    * <p>
   //    * Expected result: doc2.
   //    * 
   //    * @throws Exception if an unexpected error occurs
   //    */
   //   public void testDateConstraint() throws Exception
   //   {
   //      // create data
   //      String name = "fileCS2.doc";
   //      String name2 = "fileCS3.doc";
   //      String contentType = "text/plain";
   //
   //      Document folder = createFolder(root, "CASETest");
   //
   //      Document doc1 = createDocument(folder.getObjectId(), name, new byte[0], contentType);
   //
   //      Document doc2 = createDocument(folder.getObjectId(), name2, new byte[0], contentType);
   //
   //      String statement =
   //         "SELECT * FROM " + NASA_DOCUMENT
   //            + " WHERE ( cmis:lastModificationDate >= TIMESTAMP '2007-01-01T00:00:00.000Z' )";
   //
   //      Query query = new Query(statement, true);
   //      ItemsIterator<Result> result = storage.query(query);
   //
   //      checkResult(result, new Document[]{doc1, doc2});
   //   }

   /**
    * Simple test.
    * <p>
    * All documents from Apollo program
    * <p>
    * Query : Select all CMIS_DOCUMENTS.
    * <p>
    * Expected result: document1 and document1
    * 
    * @throws Exception if an unexpected error occurs
    */
   public void testSimpleQuery() throws Exception
   {
      List<Document> appolloContent = createNasaContent(testRoot);
      String statement = "SELECT * FROM " + NASA_DOCUMENT;
      Query query = new Query(statement, true);
      ItemsIterator<Result> result = storage.query(query);
      checkResult(result, appolloContent.toArray(new Document[appolloContent.size()]));

   }

   /**
    * Test fulltext constraint.
    * <p>
    * Initial data:
    * <p>
    * see createApolloContent()
    * <p>
    * Query : Select all documents where data contains "moon" word.
    * <p>
    * Expected result: document1 and document2
    * 
    * @throws Exception if an unexpected error occurs
    */
   public void testUpdateFulltextConstraint() throws Exception
   {

      Document doc3 =
         createDocument(testRoot, "Apollo 13", NASA_DOCUMENT, ("Apollo 13 was the third "
            + "manned mission by NASA intended to land on the Moon, but a mid-mission technical "
            + "malfunction forced the lunar landing to be aborted. ").getBytes(), CONTENT_TYPE);
      doc3.setProperty(new StringProperty(PROPERTY_COMMANDER, PROPERTY_COMMANDER, PROPERTY_COMMANDER,
         PROPERTY_COMMANDER, "James A. Lovell, Jr."));
      doc3.setProperty(new StringProperty(PROPERTY_BOOSTER, PROPERTY_BOOSTER, PROPERTY_BOOSTER, PROPERTY_BOOSTER,
         "Saturn V"));
      storage.saveObject(doc3);

      String statement1 = "SELECT * FROM " + NASA_DOCUMENT + " WHERE CONTAINS(\"moon\")";
      Query query = new Query(statement1, true);
      ItemsIterator<Result> result = storage.query(query);

      assertEquals(1, result.size());
      checkResult(result, new Document[]{doc3});

      //replace content
      ContentStream cs = new BaseContentStream("Sun".getBytes(), "test", CONTENT_TYPE);
      doc3.setContentStream(cs);

      storage.saveObject(doc3);

      //check old one
      result = storage.query(query);
      assertEquals(0, result.size());
      //check new  content
      String statement2 = "SELECT * FROM " + NASA_DOCUMENT + " WHERE CONTAINS(\"Sun\")";
      query = new Query(statement2, true);
      result = storage.query(query);

      assertEquals(1, result.size());
      checkResult(result, new Document[]{doc3});

   }

   protected Document createAppoloMission(Folder parentFolder, String missionName, String commander,
      String commandModulePilot, String lunarModulePilot, String boosterName, double boosterMass, long sampleReturned,
      String objectives) throws Exception
   {

      Document doc = createDocument(parentFolder, missionName, NASA_DOCUMENT, objectives.getBytes(), CONTENT_TYPE);
      doc.setProperty(new StringProperty(PROPERTY_COMMANDER, PROPERTY_COMMANDER, PROPERTY_COMMANDER,
         PROPERTY_COMMANDER, commander));
      doc.setProperty(new StringProperty(PROPERTY_COMMAND_MODULE_PILOT, PROPERTY_COMMAND_MODULE_PILOT,
         PROPERTY_COMMAND_MODULE_PILOT, PROPERTY_COMMAND_MODULE_PILOT, commandModulePilot));
      doc.setProperty(new StringProperty(PROPERTY_LUNAR_MODULE_PILOT, PROPERTY_LUNAR_MODULE_PILOT,
         PROPERTY_LUNAR_MODULE_PILOT, PROPERTY_LUNAR_MODULE_PILOT, lunarModulePilot));
      doc.setProperty(new StringProperty(PROPERTY_BOOSTER, PROPERTY_BOOSTER, PROPERTY_BOOSTER, PROPERTY_BOOSTER,
         boosterName));

      doc.setProperty(new DecimalProperty(PROPERTY_BOOSTER_MASS, PROPERTY_BOOSTER_MASS, PROPERTY_BOOSTER_MASS,
         PROPERTY_BOOSTER_MASS, new BigDecimal(boosterMass)));
      doc.setProperty(new DecimalProperty(PROPERTY_SAMPLE_RETURNED, PROPERTY_SAMPLE_RETURNED, PROPERTY_SAMPLE_RETURNED,
         PROPERTY_SAMPLE_RETURNED, new BigDecimal(sampleReturned)));
      return doc;
   }

   /**
    * @see org.xcmis.sp.jcr.exo.BaseTest#tearDown()
    */
   @Override
   protected void tearDown() throws Exception
   {

      storage.deleteTree(testRoot, true, UnfileObject.DELETE, true);
      super.tearDown();

   }

   /**
    * Create content for Apollo program.
    * 
    * @param folder
    * @return
    * @throws Exception
    */
   private List<Document> createNasaContent(Folder folder) throws Exception
   {
      List<Document> result = new ArrayList<Document>();
      result.add(createAppoloMission(folder, "Apollo 7", "Walter M. Schirra", "Donn F. Eisele", "R. Walter Cunningham",
         "Saturn 1B", 581.844, 0, "Apollo 7 (October 11-22, 1968) was the first manned mission "
            + "in the Apollo program to be launched. It was an eleven-day "
            + "Earth-orbital mission, the first manned launch of the "
            + "Saturn IB launch vehicle, and the first three-person " + "American space mission"));

      result.add(createAppoloMission(folder, "Apollo 8", "Frank F. Borman, II", "James A. Lovell, Jr",
         "William A. Anders", "Saturn V", 3038.500, 0, "Apollo 8 was the first "
            + "manned space voyage to achieve a velocity sufficient to allow escape from the "
            + "gravitational field of planet Earth; the first to escape from the gravitational "
            + "field of another celestial body; and the first manned voyage to return to planet Earth "
            + "from another celestial body - Earth's Moon"));

      result.add(createAppoloMission(folder, "Apollo 13", "James A. Lovell, Jr.", "John L. Swigert",
         "Fred W. Haise, Jr.", "Saturn V", 3038.500, 0, "Apollo 13 was the third "
            + "manned mission by NASA intended to land on the Moon, but a mid-mission technical "
            + "malfunction forced the lunar landing to be aborted. "));

      result.add(createAppoloMission(folder, "Apollo 17", "Eugene A. Cernan", "Ronald E. Evans", "Harrison H. Schmitt",
         "Saturn V", 3038.500, 111, "Apollo 17 was the eleventh manned space "
            + "mission in the NASA Apollo program. It was the first night launch of a U.S. human "
            + "spaceflight and the sixth and final lunar landing mission of the Apollo program."));
      for (Document document : result)
      {
         storage.saveObject(document);
      }
      return result;
   }

}

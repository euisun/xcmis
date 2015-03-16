_This document refers to 1.1.x and 1.2.x version of xCMIS._


# CMIS SQL query #

CMIS provides a type-based query service for discovering objects that match specified criteria, by defining a read-only projection of the CMIS data

model into a Relational View.

## Query lifecycle ##

TODO update to new SPI

We can make query:
```
Query query = new Query(statement, true);
```

Execute query:
```
ItemsIterator<Result> result = storage.query(query);
```

## Object type attributes that affects to query ##

TODO update to new SPI

Each CMIS ObjectType definition has next query attributes:

| **Attribute name** | **Type** | **Description** |
|:-------------------|:---------|:----------------|
| **query name** | String | Used for query operations on object types. In our SQL statement examples all objecttypes is a queryName. For example:`SELECT * FROM cmis:document` ,"cmis:document" is queryName preconfigured in Document object type definition. |
| **queryable** | Boolean | Indicates whether or not this object type is queryable. A non-queryable object type is not visible through the relational view that is used for query, and can not appear in the FROM clause of a query statement. |
| **fulltextIndexed** | Boolean | Indicates whether objects of this type are full-text indexed for querying via the CONTAINS() query predicate. |
| **includedInSupertypeQuery** | Boolean | Indicates whether this type and its subtypes appear in a query of this type's ancestor types. For example: if Invoice is a sub-type of Document, if this is TRUE on Invoice then for a query on Document type, instances of Invoice will be returned if they match. If this attribute is FALSE, no instances of Invoice will be returned even if they match the query. |

Property definition also contains queryName and queriable attributes with same usage.


# Get Capabilities #

Get query capabilities.

```
// Get description of storage and its capabilities
RepositoryCapabilities repCapabilities = storage.getRepositoryInfo().getCapabilities();
// Get capability
repCapabilities.getCapabilityQuery();
// Get get capability join
repCapabilities.getCapabilityJoin();
// Check for PWC searchability
repCapabilities.isCapabilityPWCSearchable();
// Check for versions searchability
repCapabilities.isCapabilityAllVersionsSearchable();
```

# Query examples #
## Constraint ##

### Simple query ###

Query : Select all NASA\_DOCUMENT.

```
String statement = "SELECT * FROM " + NASA_DOCUMENT;
// create query object
Query query = new Query(sql.toString(), true);
// execute query
ItemsIterator<Result> result = storage.query(query);
```

Query result:
All documents from Apollo program.

### AND, OR ###
#### Find document by several constraints ####

Query : Select all documents where **exo:Booster** is 'Saturn V' and **exo:Commander** is Frank F. Borman, II or James A. Lovell, Jr.

Initial data:
  * document1: **PROPERTY\_BOOSTER** - Saturn 1B, **PROPERTY\_COMMANDER** - Walter M. Schirra
  * document2: **PROPERTY\_BOOSTER** - Saturn V, **PROPERTY\_COMMANDER** - Frank F. Borman, II
  * document3: **PROPERTY\_BOOSTER** - Saturn V, **PROPERTY\_COMMANDER** - James A. Lovell, Jr.

```
StringBuffer sql = new StringBuffer();
sql.append("SELECT * ");
sql.append("FROM ");
sql.append(NASA_DOCUMENT);
sql.append(" WHERE ");
sql.append(PROPERTY_BOOSTER + " = " + "'Saturn V'");
sql.append(" AND ( " + PROPERTY_COMMANDER + " = 'Frank F. Borman, II' ");
sql.append("       OR " + PROPERTY_COMMANDER + " = 'James A. Lovell, Jr.' )");
// create query object
Query query = new Query(sql.toString(), true);
// execute query
ItemsIterator<Result> result = storage.query(query);
```

Query result:
**document2** and **document3**

### Full-text search ###
#### Simple full-text search ####

Full-text search from jcr:content

Query : Select all documents that contains **"here"** word.

Initial data:
  * document1: **content** - "There must be test word"
  * document2: **content** - "Test word is not here"

```
String statement = "SELECT * FROM " + NASA_DOCUMENT + " WHERE CONTAINS(\"here\")";
// create query object
Query query = new Query(sql.toString(), true);
// execute query
ItemsIterator<Result> result = storage.query(query);
```

Query result:
**document2**.

#### Extended full-text search ####

Query : Select all documents that contains **"There must"** phrase and do not contain **"check-word"** word.

Initial data:
  * document1: **content** - "There must be test word"
  * document2: **content** - " Test word is not here. Another check-word."
  * document3: **content** - "There must be check-word."

```
String statement =
         "SELECT * FROM " + NASA_DOCUMENT + " WHERE CONTAINS(\"\\\"There must\\\" -\\\"check\\-word\\\"\")";
// create query object
Query query = new Query(statement, true);
// execute query
ItemsIterator<Result> result = storage.query(query);
```

Query result:
**document1**.

### Comparison ###
#### Date property comparison ####

Query : Select all documents where **cmis:lastModificationDate** more than **2007-01-01**.

Initial data:
  * document1: **cmis:lastModificationDate** - 2006-08-08
  * document2: **cmis:lastModificationDate** - 2009-08-08

```
String statement =
"SELECT * FROM " + NASA_DOCUMENT
+ " WHERE ( cmis:lastModificationDate >= TIMESTAMP '2007-01-01T00:00:00.000Z' )";
// create query object
Query query = new Query(statement, true);
// execute query
ItemsIterator<Result> result = storage.query(query);
```

Query result:
**document2**.

#### Boolean property comparison ####

Query : Select all documents where property **PROPERTY\_STATUS** equals to **false**.

Initial data:
  * document1: **PROPERTY\_STATUS** - true
  * document2: **PROPERTY\_STATUS** - false

```
String statement = "SELECT * FROM " + NASA_DOCUMENT + " WHERE (" + PROPERTY_STATUS + " = FALSE )";
// create query object
Query query = new Query(statement, true);
// execute query
ItemsIterator<Result> result = storage.query(query);
```

Query result:
**document2**.

### IN Constraint ###
#### Find document where property are any value from defined set ####

Query : Select all documents where **PROPERTY\_COMMANDER** is in set {**'Virgil I. Grissom', 'Frank F. Borman, II', 'James A. Lovell, Jr.'**}.

Initial data:
  * document1: **PROPERTY\_COMMANDER** - Walter M. Schirra
  * document2: **PROPERTY\_COMMANDER** - Frank F. Borman, II
  * document3: **PROPERTY\_COMMANDER** - James A. Lovell, Jr.
  * document4: **PROPERTY\_COMMANDER** - Eugene A. Cernan

```
String statement =
         "SELECT * FROM " + NASA_DOCUMENT + " WHERE " + PROPERTY_COMMANDER
            + " IN ('Virgil I. Grissom', 'Frank F. Borman, II', 'James A. Lovell, Jr.')";
// create query object
Query query = new Query(statement, true);
// execute query
ItemsIterator<Result> result = storage.query(query);
```

Query result:
**document2** and **document3**.

#### Select all documents where longprop property NOT IN set ####

Query : Select all documents where **PROPERTY\_COMMANDER** property not in set {**'Walter M. Schirra', 'James A. Lovell, Jr.'**}.

Initial data:
  * document1: **PROPERTY\_COMMANDER** - Walter M. Schirra
  * document2: **PROPERTY\_COMMANDER** - Frank F. Borman, II
  * document3: **PROPERTY\_COMMANDER** - James A. Lovell, Jr.
  * document4: **PROPERTY\_COMMANDER** - Eugene A. Cerna

```
String statement =
         "SELECT * FROM " + NASA_DOCUMENT + " WHERE " + PROPERTY_COMMANDER
            + " NOT IN ('Walter M. Schirra', 'James A. Lovell, Jr.')";
// create query object
Query query = new Query(statement, true);
// execute query
ItemsIterator<Result> result = storage.query(query);
```

Query result:
**document2**, **document4**.

#### Select all documents where longprop property NOT NOT IN set ####

Query : Select all documents where **PROPERTY\_COMMANDER** property **NOT NOT IN** set {**'James A. Lovell, Jr.'**}.

Initial data:
  * document1: **PROPERTY\_COMMANDER** - Walter M. Schirra
  * document2: **PROPERTY\_COMMANDER** - Frank F. Borman, II
  * document3: **PROPERTY\_COMMANDER** - James A. Lovell, Jr.
  * document4: **PROPERTY\_COMMANDER** - Eugene A. Cerna

```
String statement =
         "SELECT * FROM " + NASA_DOCUMENT + " WHERE  NOT (" + PROPERTY_COMMANDER + " NOT IN ('James A. Lovell, Jr.'))";
// create query object
Query query = new Query(statement, true);
// execute query
ItemsIterator<Result> result = storage.query(query);
```

Query result:
**document3**.

### IN\_FOLDER constarint ###
#### Select all folders that are in specified folder ####

Query : Select all folders that are in **folder1**.

Initial data:
  * folder1:
> > document1: **Title** - node1
    * folder3:
> > > folder4:
  * folder2:

> > document2: **Title** - node2

```
String statement = "SELECT * FROM cmis:folder  WHERE IN_FOLDER( '" + folder1.getObjectId() + "')";
// create query object
Query query = new Query(statement, true);
// execute query
ItemsIterator<Result> result = storage.query(query);
```

Query result:
**folder3**.

#### Select all documents that are in specified folder ####

Query : Select all documents that are in **folder1**.

Initial data:
  * **folder1**:
> > document1: **Title** - node1
  * **folder2**:
> > document2: **Title** - node2

```
String statement = "SELECT * FROM " + NASA_DOCUMENT + " WHERE IN_FOLDER( '" + folder1.getObjectId() + "')";
// create query object
Query query = new Query(statement, true);
// execute query
ItemsIterator<Result> result = storage.query(query);
```

Query result:
**document1**.

#### Select all documents where query supertype is cmis:article ####

Query : Select all documents where query supertype is cmis:article.

Initial data:
  * document1: **Title** - node1 **typeID** - cmis:article-sports
  * document2: **Title** - node2 **typeID** - cmis:article-animals

```
String stat = "SELECT * FROM cmis:article WHERE IN_FOLDER( '" + testRoot.getObjectId() + "')";
// create query object
Query query = new Query(statement, true);
// execute query
ItemsIterator<Result> result = storage.query(query);
```

Query result:
**document1**,**document2**.

### IN\_TREE constraint ###
#### Select all documents that are in tree of specified folder ####

Query : Select all documents that are in tree of **folder1**.

Initial data:
  * **folder1**:
> > document1
    * **folder2**:
> > > document2

```
String statement = "SELECT * FROM " + NASA_DOCUMENT + " WHERE IN_TREE('" + folder1.getObjectId() + "')";
// create query object
Query query = new Query(statement, true);
// execute query
ItemsIterator<Result> result = storage.query(query);
```

Query result:
**document1**, **document2**.

### LIKE Comparison ###
#### Select all documents where PROPERTY\_COMMANDER begins with "James" ####

Query : Select all documents where **PROPERTY\_COMMANDER** begins with "James".

Initial data:
  * document1: **PROPERTY\_COMMANDER** - Walter M. Schirra
  * document2: **PROPERTY\_COMMANDER** - Frank F. James, II
  * document3: **PROPERTY\_COMMANDER** - James A. Lovell, Jr.
  * document4: **PROPERTY\_COMMANDER** - Eugene A. James


```
String statement = "SELECT * FROM " + NASA_DOCUMENT + " AS doc WHERE " + PROPERTY_COMMANDER + " LIKE 'James%'";
// create query object
Query query = new Query(statement, true);
// execute query
ItemsIterator<Result> result = storage.query(query);
```

Query result:
**document3**.

#### Test LIKE constraint with escape symbols ####

Query : Select all documents where **PROPERTY** like **'ad\\%min%'**.

Initial data:
  * document1: **Title** - node1, **PROPERTY** - ad%min master
  * document2: **Title** - node2, **PROPERTY** - admin operator
  * document3: **Title** - node2, **PROPERTY** - radmin

```
String statement =
         "SELECT * FROM " + NASA_DOCUMENT + " AS doc WHERE  " + PROPERTY + " LIKE 'ad\\%min%'";
// create query object
Query query = new Query(statement, true);
// execute query
ItemsIterator<Result> result = storage.query(query);
```

Query result:
**document1**.

### NOT constraint ###
#### Select all documents that not contains "world" word ####

Query : Select all documents that not contains **"world"** word.

Initial data:
  * document1: **Title** - node1, **content** - hello world
  * document2: **Title** - node2, **content** - hello

```
String statement = "SELECT * FROM " + NASA_DOCUMENT + " WHERE NOT CONTAINS(\"world\")";
// create query object
Query query = new Query(statement, true);
// execute query
ItemsIterator<Result> result = storage.query(query);
```

Query result:
**document2**.

### Property existanse ###
#### Select all documents by property existance ####

Query : Select all documents that has **PROPERTY\_COMMANDER** property **IS NOT NULL**.

Initial data:
  * document1: **PROPERTY\_COMMANDER** - Walter M. Schirra
  * document2: **PROPERTY\_COMMANDER** -
  * document3: **PROPERTY\_COMMANDER** - James A. Lovell, Jr.
  * document4: **PROPERTY\_COMMANDER** -

```
String statement = "SELECT * FROM " + NASA_DOCUMENT + " WHERE " + PROPERTY_COMMANDER + " IS NOT NULL";
// create query object
Query query = new Query(statement, true);
// execute query
ItemsIterator<Result> result = storage.query(query);
```

Query result:
**document1**, **document3**.

### ORDER BY ###
#### ORDER BY default ####

Query : Select all documents in default order (by document name).

Initial data:
  * document1: **Title** - Apollo 7
  * document2: **Title** - Apollo 8
  * document3: **Title** - Apollo 13
  * document4: **Title** - Apollo 17

```
StringBuffer sql = new StringBuffer();
sql.append("SELECT ");
sql.append(CmisConstants.LAST_MODIFIED_BY + ", ");
sql.append(CmisConstants.OBJECT_ID + ", ");
sql.append(CmisConstants.LAST_MODIFICATION_DATE);
sql.append(" FROM ");
sql.append(NASA_DOCUMENT);

String statement = sql.toString();
// create query object
Query query = new Query(statement, true);
// execute query
ItemsIterator<Result> result = storage.query(query);
```

Query result:
**document3**, **document4**, **document1**, **document2**.

#### ORDER BY ASC ####

Query : Order by **PROPERTY\_COMMANDER** property value (in ascending order).

Initial data:
  * document1: **PROPERTY\_COMMANDER** - Walter M. Schirra
  * document2: **PROPERTY\_COMMANDER** - Frank F. Borman, II
  * document3: **PROPERTY\_COMMANDER** - James A. Lovell, Jr.
  * document4: **PROPERTY\_COMMANDER** - Eugene A. Cerna

```
StringBuffer sql = new StringBuffer();
sql.append("SELECT ");
sql.append(CmisConstants.LAST_MODIFIED_BY + ", ");
sql.append(CmisConstants.OBJECT_ID + ", ");
sql.append(CmisConstants.LAST_MODIFICATION_DATE);
sql.append(" FROM ");
sql.append(NASA_DOCUMENT);
sql.append(" ORDER BY ");
sql.append(PROPERTY_COMMANDER);

String statement = sql.toString();
// create query object
Query query = new Query(statement, true);
// execute query
ItemsIterator<Result> result = storage.query(query);
```

Query result:
**document4**, **document2**, **document3**, **document1**.


#### ORDER BY DESC ####

Query : Order by **PROPERTY\_COMMANDER** property value (in decending order).

Initial data:
  * document1: **PROPERTY\_COMMANDER** - Walter M. Schirra
  * document2: **PROPERTY\_COMMANDER** - Frank F. James, II
  * document3: **PROPERTY\_COMMANDER** - James A. Lovell, Jr.
  * document4: **PROPERTY\_COMMANDER** - Eugene A. James

```
StringBuffer sql = new StringBuffer();
sql.append("SELECT  ");
sql.append(CmisConstants.LAST_MODIFIED_BY + " as last , ");
sql.append(CmisConstants.OBJECT_ID + " , ");
sql.append(CmisConstants.LAST_MODIFICATION_DATE);
sql.append(" FROM ");
sql.append(NASA_DOCUMENT);
sql.append(" ORDER BY ");
sql.append(PROPERTY_COMMANDER);
sql.append(" DESC");

String statement = sql.toString();
// create query object
Query query = new Query(statement, true);
// execute query
ItemsIterator<Result> result = storage.query(query);
```

Query result:
**document1**, **document3**, **document2**, **document4**.

#### ORDER BY SCORE (as columns) ####

Query : Select all documents which contains word "moon" ordered by score.

Initial data:
  * document1: **content** - Earth-orbital mission, the first manned launch
  * document2: **content** - from another celestial body - Earth's Moon
  * document3: **content** - NASA intended to land on the Moon, but a mid-mission technical
  * document4: **content** - It was the first night launch of a U.S. human

```
StringBuffer sql = new StringBuffer();
sql.append("SELECT ");
sql.append(" SCORE() AS scoreCol, ");
sql.append(CmisConstants.LAST_MODIFIED_BY + ", ");
sql.append(CmisConstants.OBJECT_ID + ", ");
sql.append(CmisConstants.LAST_MODIFICATION_DATE);
sql.append(" FROM ");
sql.append(NASA_DOCUMENT);
sql.append(" WHERE CONTAINS(\"moon\") ");
sql.append(" ORDER BY SCORE() ");

String statement = sql.toString();
// create query object
Query query = new Query(statement, true);
// execute query
ItemsIterator<Result> result = storage.query(query);
```

Query result:
**document2**, **document3**.

### Not equal comparsion (<>) ###
#### Not equal comparison (decimal) ####

Query : Select all documents property **PROPERTY\_BOOSTER\_MASS** not equal to **3**.

Initial data:
  * document1: **Title** - node1, **PROPERTY\_BOOSTER\_MASS** - 3
  * document2: **Title** - node2, **PROPERTY\_BOOSTER\_MASS** - 15

```
String statement = "SELECT * FROM " + NASA_DOCUMENT + " WHERE " + PROPERTY_BOOSTER_MASS + " <> 3";
// create query object
Query query = new Query(statement, true);
// execute query
ItemsIterator<Result> result = storage.query(query);
```

Query result:
**document2**.

#### Not equal comparison (string) ####

Query : Select all documents property **PROPERTY** not equals to **"test word second"**.

Initial data:
  * document1: **PROPERTY** - "test word first"
  * document2: **PROPERTY** - "test word second"

```
String statement = "SELECT * FROM " + NASA_DOCUMENT + " WHERE " + PROPERTY + " <> 'test word second'";
// create query object
Query query = new Query(statement, true);
// execute query
ItemsIterator<Result> result = storage.query(query);
```

Query result:
**document1**.

### More than comparison (>) ###

Query : Select all documents property **PROPERTY\_BOOSTER\_MASS** more than **5**.

Initial data:
  * document1: **PROPERTY\_BOOSTER\_MASS** - 3
  * document2: **PROPERTY\_BOOSTER\_MASS** - 15

```
String statement = "SELECT * FROM " + NASA_DOCUMENT + " WHERE " + PROPERTY_BOOSTER_MASS + " > 5";
// create query object
Query query = new Query(statement, true);
// execute query
ItemsIterator<Result> result = storage.query(query);
```

Query result:
**document2**.
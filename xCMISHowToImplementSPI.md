_This document refers to 1.1.x and 1.2.x version of xCMIS._




---



![http://xcmis.googlecode.com/svn/wiki/pic/dhfjtz8p_30gpcm8r5z_b.jpg](http://xcmis.googlecode.com/svn/wiki/pic/dhfjtz8p_30gpcm8r5z_b.jpg)

# Implementor of SPI must implement #

Interfaces and abstract classes:

## org.xcmis.spi.CmisRegistryFactory ##

CmisRegistryFactory provide CmisRegistry instance on getRegistry() method. CmisRegistry instance contains StorageProvider instance.

Example Inmemory: org.xcmis.sp.inmemory.tck.InmemoryCmisRegistryFactory

## org.xcmis.spi.StorageProvider ##

StorageProvider provide access to all available storages.

Example Inmemory: org.xcmis.sp.inmemory.StorageProviderImpl

## org.xcmis.spi.Storage ##

Storage able to create new instances of CMIS object, provide access to existed CMIS objects by  id or by path (for fileable objects), delete objects, etc. Storage should not be used directly by REST-Atom, WS-SOAP or any other binding layer. All operation should be done through `org.xcmis.spi.Connection`.

Example Inmemory: org.xcmis.sp.inmemory.StorageImpl

## org.xcmis.spi.ObjectData ##

Internal (storage level) representation of CMIS object. There are abstraction for each of root CMIS types such as cmis:document, cmis:folder, cmis:policy, cmis:relationship.

### CMIS types ###
|CMIS type|Corresponded Java type|Desciption|
|:--------|:---------------------|:---------|
|  |org.xcmis.spi.ObjectData|Summarized abstraction for any CMIS object of any type|
|cmis:document|org.xcmis.spi.DocumentData|CMIS document|
|cmis:folder|org.xcmis.spi.FolderData|CMIS folder|
|cmis:policy|org.xcmis.spi.PolicyData|CMIS policy|
|cmis:relationship|org.xcmis.spi.RelationshipData|CMIS relationship|

  * Implementor of SPI must implement at least two of them Document and Folder.
  * Other types is optional. Only supported types must be in collection of object types retrieved through method `TypeManager.getTypeChildren(String, boolean)`.
  * If object types other then Document or Folder are not supported then methods `Storage.createPolicy(FolderData, String)` and `Storage.createRelationship(ObjectData, ObjectData, String)` should throw `org.xcmis.spi.NotSupportedException`

## org.xcmis.spi.Connection ##
SPI (storage provider interface) provide base implementation of `org.xcmis.spi.Connection`. This implementation validates incoming and uses corresponded methods of implementation of `org.xcmis.spi.Storage`.

Example Inmemory: org.xcmis.sp.inmemory.InmemConnection

# Provide the FQN of your CmisRegistryFactory implementation #

Create in your WAR application the file with FQN:
META-INF/services/xcmis/org.xcmis.CmisRegistryFactory
Example Inmemory:
```
org.xcmis.sp.inmemory.tck.InmemoryCmisRegistryFactory
```
or provide the system property.
Example Inmemory:
```
-Dorg.xcmis.CmisRegistryFactory=org.xcmis.sp.inmemory.tck.InmemoryCmisRegistryFactory
```

# Example creation of new Document. #
```
...
Connection connection = null;
try
{
   connection = storageProvider.getConnection(storageId);
   String docId = connection.createDocument(parentId, properties, contentStream, addACL, removeACL, policies, versioningState);
   return docId;
}
catch (...)
{
...
}
finally
{
   if (connection != null)
   {
      connection.close();
   }
}
```

# How it reflected in SPI layer. #

  1. Get new unsaved instance of document by using method `Storage.createDocument(parent, typeId, versioningState)`
    * `parent` - parent folder for newly created document, may be null if document should be created in unfiled state and unfiling capability is supported;
    * `typeId` - type id of object to be created;
    * `versioningState` - initial versioning state for document;
  1. Apply to newly created object properties, policies, ACL by using corresponded methods of document.
  1. Save new document via method `Storage.saveObject(document)`. After this document should be persisted at storage level and documents gets unique identifier (id) and may be retrieved via method `Storage.getObject(String objectId)`.

# Links #
[CMIS class diagram](https://wiki-int.exoplatform.org/display/exoproducts/CMIS+class+diagram)
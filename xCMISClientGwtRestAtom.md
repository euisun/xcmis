_This document refers to 1.0 and 1.1 version of xCMIS._


# Introduction #

This article describes how to use xCMIS client REST Atom GWT framework in your application

# 1. Installing and setup #

#### 1.1 Get code into folder _xcmis/xcmis-client-gwt-restatom_ by running command ####
```
svn co http://xcmis.googlecode.com/svn/tags/1.0/xcmis/xcmis-client-gwt-restatom
```
> at the folder level.

#### 1.2 Run ####
```
mvn clean install
```
> command within _xcmis/xcmis-client-gwt-restatom_ folder.

#### 1.3 Test ####
```
mvn gwt:test
```
> command within _xcmis/xcmis-client-gwt-restatom_ folder.

# 2. Configuration #

To use xCMIS Client GWT Framework in your application, include:

1. to application's _pom.xml_ the following:

```
       <dependency>
            <groupId>org.xcmis</groupId>
            <artifactId>xcmis-client-gwt-restatom</artifactId>
            <version>1.0</version>
        </dependency> 
```

2. to _ApplicationName.gwt.xml_:

```
      <inherits name='org.xcmis.CmisClientFramework'/> 
```


# 3. CMIS Services Usage #




### 3.1 Repository service (org.xcmis.client.gwt.service.repository.RepositoryService) ###

1. _**getRepositories(String url)**_ - passes the list of CMIS available repositories from CMIS service endpoint to the particular implementation of org.xcmis.client.gwt.service.repository.event.RepositoriesReceivedHandler registered in HandlerManager.

Paramater:

  * _**url**_ - service location.

Events:

  * _**org.xcmis.client.gwt.service.repository.event.RepositoriesReceivedEvent**_ - when repositories response is succesfuly received, so the list of repositories is available :
```
 event.getRepositories().list();
```
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.repository.event.RepositoriesReceivedHandler**_ - to do actions, when repositories are received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


2. _**getRepositoryInfo(String url)**_ - passes the information about the CMIS repository, the optional capabilities it supports to the particular implementation of org.xcmis.client.gwt.service.repository.event.RepositoryInfoReceivedHandler registered in HandlerManager.

Parameter:
  * _**url**_ - repository location.

Events:
  * _**org.xcmis.client.gwt.service.repository.event.RepositoryInfoReceivedEvent**_ - when repository information response is successfully received, so the repository is retrieved :
```
 event.getRepositoryInfo();
```
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.repository.event.RepositoryInfoReceivedHandler**_ - to do actions, when repository information is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


3. _**getTypeChildren(String url, boolean includePropertyDefinitions, int maxItems, int skipCount)**_ - passes the list of object-types defined for the repository that are children of the specified type to the particular implementation of org.xcmis.client.gwt.service.repository.event.TypeChildrenReceivedHandler registered in HandlerManager.

Parameters:
  * _**url**_ - location of the type;
  * _**includePropertyDefinition**_ - boolean value that points whether property definitions of each type should be included;
  * _**maxItems**_ -maximum number of items to return in a response;
  * _**skipCount**_ - number of potential results that the repository must skip/page over before returning any results.

Events:

  * _**org.xcmis.client.gwt.service.repository.event.TypeChildrenReceivedEvent**_ - when types response is received, so the list of types is retrieved:
```
 event.getTypeCollection().getTypes();
```
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.repository.event.TypeChildrenReceivedHandler**_ - to do actions, when types are received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


4. _**getTypeDescendants(String url, String typeId, int depth, boolean includePropertyDefinition)**_ -  passes the set of descendant object-types defined for the repository under the specified type to the particular implementation of org.xcmis.client.gwt.service.repository.event.TypeDescendantsRecievedHandler registered in HandlerManager..

Parameters:
  * _**url**_ - location of the type;
  * _**typeId**_ - id of the type;
  * _**depth**_ - number of levels of depth in the type hierarchy from which to return results;
  * _**includePropertyDefinition**_ - boolean value that points whether property definitions of each type should be included.

Events:

  * _**org.xcmis.client.gwt.service.repository.event.TypeDescendantsRecievedEvent**_ - when type descendants response is received, so type descendants are  retrieved:
```
 event.getTypeCollection().getTypes();
```
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.repository.event.TypeDescendantsRecievedHandler**_ - to do actions, when types are received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


5. _**getBaseTypes(String url, boolean includePropertyDefinition)**_ - passes base types of the repository to the particular implementation of org.xcmis.client.gwt.service.repository.event.BaseTypesReceivedHandler registered in HandlerManager.

Parameters:
  * _**url**_ - location of the repository's types collection;
  * _**includePropertyDefinition**_ - boolean value that points whether property definitions of each type should be included.

  * _**org.xcmis.client.gwt.service.repository.event.BaseTypesReceivedEvent**_ - when base types response is received, so types are  retrieved:
```
 event.getTypeCollection().getTypes();
```
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.repository.event.BaseTypesReceivedHandler**_ - to do actions, when base types are received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


6. _**addType(String url, CmisTypeDefinitionType type)**_ - creates new type and passes created type to the particular implementation of org.xcmis.client.gwt.service.repository.event.TypeCreatedHandler registered in HandlerManager.

_Note_ : This method is specifically used with xCMIS server.

Parameters:
  * _**url**_ - location of parent type;
  * _**type**_ - data for creating new type (it's attributes an property definitions).

Events:

  * _**org.xcmis.client.gwt.service.repository.event.TypeCreatedEvent**_ - when creation type response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.repository.event.TypeCreatedHandler**_ - to do actions, when type is created.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.

7. _**deleteType(String url)**_ - deletes type and passes deletion response to the particular implementation of org.xcmis.client.gwt.service.repository.event.TypeDeletedHandler registered in HandlerManager..

_Note_ : This method is specifically used with xCMIS server.

Parameter:
  * _**url**_ - type's location.

Events:

  * _**org.xcmis.client.gwt.service.repository.event.TypeDeletedEvent**_ - when deletion type response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.repository.event.TypeDeletedHandler**_ - to do actions, when type is deleted.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.




### 3.2 Navigation service (org.xcmis.client.gwt.service.navigation.NavigationService) ###

1. _**getChildren(String url, int maxItems, int skipCount, String filter,
EnumIncludeRelationships includeRelationships, String renditionFilter, boolean includeAllowableActions, boolean includePathSegment)**_ - passes the list of child objects contained in the specified folder to the particular implementation of org.xcmis.client.gwt.service.navigation.event.ChildrenReceivedHandler registered in HandlerManager.

Parameters :

  * _**url**_ - location of the folder;
  * _**maxItems**_ -maximum number of items to return in a response;
  * _**skipCount**_ - number of potential results that the repository must skip/page over before returning any results;
  * _**filter**_ - service will only return the properties in the matched object if they exist on the matched object type definition and in the filter;
  * _**includeRelationships**_ - value indicating what relationships in which the objects returned participate must be returned, if any;
  * _**renditionFilter**_ - the repository must return the set of renditions whose kind matches this filter;
  * _**includeAllowableActions**_ - if true, then the repository must return the available actions for each object in the result set;
  * _**includePathSegment**_ -  if true, returns a path segment for each child object for use in constructing that object’s path.

Events:

  * _**org.xcmis.client.gwt.service.navigation.event.ChildrenReceivedEvent**_ - when list of child objects response is received, so the list of objects is retrieved :
```
 event.getChildren().getEntries();
```
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.navigation.event.ChildrenReceivedHandler**_ - to do actions, when folder's children are received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


2. _**getDescendants(String url, int depth, String filter, EnumIncludeRelationships includeRelationships, String renditionFilter, boolean includeAllowableActions, boolean includePathSegment)**_ - passes the set of descendant objects contained in the specified folder or any of its child folders to the particular implementation of org.xcmis.client.gwt.service.navigation.event.DescendantsReceivedHandler registered in HandlerManager.

Parameters :
  * _**url**_ - location of the folder;
  * _**depth**_ - number of levels of depth in the type hierarchy from which to return results;
  * _**filter**_ - service will only return the properties in the matched object if they exist on the matched object type definition and in the filter;
  * _**includeRelationships**_ - value indicating what relationships in which the objects returned participate must be returned, if any;
  * _**renditionFilter**_ - the repository must return the set of renditions whose kind matches this filter;
  * _**includeAllowableActions**_ - if true, then the repository must return the available actions for each object in the result set;
  * _**includePathSegment**_ -  if true, returns a path segment for each child object for use in constructing that object’s path.

Events:

  * _**org.xcmis.client.gwt.service.navigation.event.DescendantsReceivedEvent**_ - when the set of descendant objects response is received, so objects are retrieved :
```
 event.getDescendants().getEntries();
```
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.navigation.event.DescendantsReceivedHandler**_ - to do actions, when folder's descendants are received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


3. _**getFolderTree(String url, int depth, String filter, EnumIncludeRelationships includeRelationships, String renditionFilter, boolean includeAllowableActions, boolean includePathSegment)**_ - passes the set of descendant folder objects contained in the specified folder to the particular implementation of org.xcmis.client.gwt.service.navigation.event.FolderTreeReceivedHandler registered in HandlerManager.

Parameters :
  * _**url**_ - location of the folder;
  * _**depth**_ - number of levels of depth in the type hierarchy from which to return results;
  * _**filter**_ - service will only return the properties in the matched object if they exist on the matched object type definition and in the filter;
  * _**includeRelationships**_ - value indicating what relationships in which the objects returned participate must be returned, if any;
  * _**renditionFilter**_ - the repository must return the set of renditions whose kind matches this filter;
  * _**includeAllowableActions**_ - if true, then the repository must return the available actions for each object in the result set;
  * _**includePathSegment**_ -  if true, returns a path segment for each child object for use in constructing that object’s path.

Events:

  * _**org.xcmis.client.gwt.service.navigation.event.FolderTreeReceivedEvent**_ - when the set of descendant folder objects is received, so objects are retrieved :
```
 event.getChildren().getEntries();
```
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.navigation.event.FolderTreeReceivedHandler**_ - to do actions, when folder's tree response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


4. _**getFolderParent(String url, String filter)**_ - passes the parent folder object for the specified folder object to the particular implementation of org.xcmis.client.gwt.service.navigation.event.FolderParentReceivedHandler registered in HandlerManager.

Parameters :
  * _**url**_ - location of the folder;
  * _**filter**_ - service will only return the properties in the matched object if they exist on the matched object type definition and in the filter.

Events:

  * _**org.xcmis.client.gwt.service.navigation.event.FolderParentReceivedEvent**_ - when parent folder response is received, so the folder's parent is retrieved :
```
 event.getFolderParent();
```
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.navigation.event.FolderParentReceivedHandler**_ - to do actions, when folder's parent is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


5. _**getObjectParents(String url, String filter, EnumIncludeRelationships includeEnumIncludeRelationships, String renditionFilter, boolean includeAllowableActions, boolean includeRelativePathSegment)**_ - passes the parent folder(s) for the specified
non-folder, fileable object to the particular implementation of org.xcmis.client.gwt.service.navigation.event.ObjectParentsReceivedHandler registered in HandlerManager.

Parameters :
  * _**url**_ - location of the folder;
  * _**filter**_ - service will only return the properties in the matched object if they exist on the matched object type definition and in the filter;
  * _**includeRelationships**_ - value indicating what relationships in which the objects returned participate must be returned, if any;
  * _**renditionFilter**_ - the repository must return the set of renditions whose kind matches this filter;
  * _**includeAllowableActions**_ - if true, then the repository must return the available actions for each object in the result set;
  * _**includePathSegment**_ -  if true, returns a path segment for each child object for use in constructing that object’s path.

Events:

  * _**org.xcmis.client.gwt.service.navigation.event.ObjectParentsReceivedEvent**_ - when object parents response is received, so the list of parents is retrieved :
```
 event.getObjectParents().getEntries();
```
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.navigation.event.ObjectParentsReceivedHandler**_ - to do actions, when object's parents response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


6. _**getCheckedOut(String url, String folderId, int maxItems, int skipCount, String filter,  EnumIncludeRelationships includeRelationships, String renditionFilter, boolean includeAllowableActions)**_ - passes the list of documents that are checked out that the user has access to the particular implementation of org.xcmis.client.gwt.service.navigation.event.CheckedOutReceivedHandler registered in HandlerManager.

Parameters :
  * _**url**_ - location of the folder;
  * _**folderId**_ - id of the folder, where documents are checked-out;
  * _**maxItems**_ -maximum number of items to return in a response;
  * _**skipCount**_ - number of potential results that the repository must skip/page over before returning any results;
  * _**filter**_ - service will only return the properties in the matched object if they exist on the matched object type definition and in the filter;
  * _**includeRelationships**_ - value indicating what relationships in which the objects returned participate must be returned, if any;
  * _**renditionFilter**_ - the repository must return the set of renditions whose kind matches this filter;
  * _**includeAllowableActions**_ - if true, then the repository must return the available actions for each object in the result set;
  * _**includePathSegment**_ -  if true, returns a path segment for each child object for use in constructing that object’s path.

Events:

  * _**org.xcmis.client.gwt.service.navigation.event.CheckedOutReceivedEvent**_ - when the list of checked out documents response is received, so the list of documents is retrieved :
```
 event.getCheckedOutDocuments().getEntries();
```
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.navigation.event.CheckedOutReceivedHandler**_ - to do actions, when checked out documents are received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


7. _**getNextPage(String url, String filter, EnumIncludeRelationships includeRelationships, String renditionFilter, boolean includeAllowableActions, boolean includePathSegment)**_ - passes the next page of items to the particular implementation of org.xcmis.client.gwt.service.navigation.event.NextPageReceivedHandler registered in HandlerManager.

Parameters :
  * _**url**_ - location of the items container;
  * _**filter**_ - service will only return the properties in the matched object if they exist on the matched object type definition and in the filter;
  * _**includeRelationships**_ - value indicating what relationships in which the objects returned participate must be returned, if any;
  * _**renditionFilter**_ - the repository must return the set of renditions whose kind matches this filter;
  * _**includeAllowableActions**_ - if true, then the repository must return the available actions for each object in the result set;
  * _**includePathSegment**_ -  if true, returns a path segment for each child object for use in constructing that object’s path.

Events:

  * _**org.xcmis.client.gwt.service.navigation.event.NextPageReceivedEvent**_ - when next page of objects is received, so the list of objects is retrieved :
```
 event.getPage().getEntries();
```
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.navigation.event.NextPageReceivedHandler**_ - to do actions, when next page response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


### 3.3 Object service (org.xcmis.client.gwt.service.object.ObjectService) ###

1. _**createDocument(String url, CreateDocument createDocument)**_ - passes response of creating a document object of the specified type (given by the cmis:objectTypeId property) in the (optionally) specified location to the particular implementation of org.xcmis.client.gwt.service.object.event.DocumentCreatedHandler registered in HandlerManager.

Parameters:
  * _**url**_ - location of new document's parent folder;
  * _**createDocument**_ - bean with information about new document (repositoryId, parent folderId, properties (indicate name, typeId e.t)).

Parameters :

  * _**url**_ - location of the folder;
  * _**maxItems**_ -maximum number of items to return in a response;
  * _**skipCount**_ - number of potential results that the repository must skip/page over before returning any results;
  * _**filter**_ - service will only return the properties in the matched object if they exist on the matched object type definition and in the filter;
  * _**includeRelationships**_ - value indicating what relationships in which the objects returned participate must be returned, if any;
  * _**renditionFilter**_ - the repository must return the set of renditions whose kind matches this filter;
  * _**includeAllowableActions**_ - if true, then the repository must return the available actions for each object in the result set;
  * _**includePathSegment**_ -  if true, returns a path segment for each child object for use in constructing that object’s path.

Events:

  * _**org.xcmis.client.gwt.service.object.event.DocumentCreatedEvent**_ - when new document is created.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.object.event.DocumentCreatedHandler**_ - to do actions, when new document created response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


2. _**createDocumentFromSource(String url, CreateDocumentFromSource createDocumentFromSource)**_ - passes the response of creating a document object as a copy of the given source document in the (optionally) specified location to the to the particular implementation of org.xcmis.client.gwt.service.object.event.DocumentFromSourceCreatedHandler registered in HandlerManager.

Parameters:
  * _**url**_ - location of new document's parent folder;
  * _**createDocumentFromSource**_ - bean with information about new document (repositoryId, parent folderId, sourceId, properties (indicate name, typeId e.t)).

Events:

  * _**org.xcmis.client.gwt.service.object.event.DocumentFromSourceCreatedEvent**_ - when new document from source is created.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.object.event.DocumentFromSourceCreatedHandler**_ - to do actions, when new document from source creation response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


3.  _**createEmptyDocument(String url, CreateDocument createDocument)**_ - passes the response of creating empty document (without content) to the particular implementation of org.xcmis.client.gwt.service.object.event.EmptyDocumentCreatedHandler registered in HandlerManager.

Parameters:
  * _**url**_ - location of new document's parent folder;
  * _**createDocument**_ - bean with information about new document (repositoryId, parent folderId, properties (indicate name, typeId e.t)).


Events:

  * _**org.xcmis.client.gwt.service.object.event.EmptyDocumentCreatedEvent**_ - when new empty document is created.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.object.event.EmptyDocumentCreatedHandler**_ - to do actions, when new empty document creation response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


4. _**createFolder(String url, CreateFolder createFolder)**_ - passes the response of creating a folder object of the specified type in the specified location to the particular implementation of org.xcmis.client.gwt.service.object.event.FolderCreatedHandler registered in HandlerManager.

Parameters:
  * _**url**_ - location of new folder's parent folder;
  * _**createFolder**_ - bean with information about new folder (repositoryId, parent folderId, properties (indicate name, typeId e.t)).

Events:

  * _**org.xcmis.client.gwt.service.object.event.FolderCreatedEvent**_ - when new folder is created.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.object.event.FolderCreatedHandler**_ - to do actions, when new folder created response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


5. _**createRelationship(String url, CreateRelationship createRelationship)**_ - passes the response of creating a relationship object of the specified type to the particular implementation of org.xcmis.client.gwt.service.object.event.RelationshipCreatedHandler registered in HandlerManager.

Parameters :
  * _**url**_ - location of new relationship's parent folder;
  * _**createRelationship**_ - bean with information about new relationship (repositoryId, parent folderId, properties (indicate name, typeId e.t)).

Events:

  * _**org.xcmis.client.gwt.service.object.event.RelationshipCreatedEvent**_ - when new relationship is created.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.object.event.RelationshipCreatedHandler**_ - to do actions, when new relationship creation response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


6. _**createPolicy(String url, CreatePolicy createPolicy)**_ - passes the response of creating a policy object of the specified type to the particular implementation of org.xcmis.client.gwt.service.object.event.PolicyCreatedHandler registered in HandlerManager.

Parameters:
  * _**url**_ - location of new policy's parent folder;
  * _**createPolicy**_ - bean with information about new policy (repositoryId, parent folderId, policy text, properties (indicate name, typeId e.t)).

Events:

  * _**org.xcmis.client.gwt.service.object.event.PolicyCreatedEvent**_ - when new policy is created.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.object.event.PolicyCreatedHandler**_ - to do actions, when new policy creation response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


7. _**getAllowableActions(String url)**_ - passes the list of allowable actions for an object to the particular implementation of org.xcmis.client.gwt.service.object.event.AllowableActionsReceivedHandler registered in HandlerManager.


Parameter:
  * _**url**_ - object's location.

Events:

  * _**org.xcmis.client.gwt.service.object.event.AllowableActionsReceivedEvent**_ - when object's allowable actions are received, so they are retrieved:
```
 event.getAllowableActions();
```

  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.object.event.AllowableActionsReceivedHandler**_ - to do actions, when object's allowable action response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


8. _**getObject(String url, String filter, EnumIncludeRelationships includeRelationships, boolean includePolicyIds, String renditionFilter, boolean includeACL, boolean includeAllowableActions)**_ - passes the specified information for the object to the particular implementation of org.xcmis.client.gwt.service.object.event.ObjectReceivedHandler registered in HandlerManager.

Parameters:
  * _**url**_ - location of object's allowable actions;
  * _**filter**_ - service will only return the properties in the matched object if they exist on the matched object type definition and in the filter;
  * _**includeRelationships**_ - value indicating what relationships in which the objects returned participate must be returned, if any;
  * _**includePolicyIds**_ - if true, then the repository must return the ids of the policies applied to the object;
  * _**renditionFilter**_ - the repository must return the set of renditions whose kind matches this filter;
  * _**includeACL**_ - if true, then the repository must return the available actions for each object in the result set;
  * _**includeAllowableActions**_ - if true, then the repository must return the available actions for each object in the result set.

Events:

  * _**org.xcmis.client.gwt.service.object.event.ObjectReceivedEvent**_ - when object's information is received, so object is retrieved:
```
event.getEntry().getObject();
```
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.object.event.ObjectReceivedHandler**_ - to do actions, when object information response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


9. _**getProperties(String url, String filter)**_ - passes the list of properties for an object to the particular implementation of org.xcmis.client.gwt.service.object.event.PropertiesReceivedHandler registered in HandlerManager.

Parameters:
  * _**url**_ - location of object's allowable actions;
  * _**filter**_ - service will only return the properties in the matched object if they exist on the matched object type definition and in the filter.

Events:

  * _**org.xcmis.client.gwt.service.object.event.PropertiesReceivedEvent**_ - when object's properties are received, so they are retrieved:
```
event.getEntry().getObject().getProperties();
```
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.object.event.PropertiesReceivedHandler**_ - to do actions, when object information response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


10. _**getContentStream(String url, String streamId)**_ - passes the content stream for the specified document object, or a rendition stream for a specified rendition of a document or folder object to the particular implementation of org.xcmis.client.gwt.service.object.event.ContentStreamReceivedHandler registered in HandlerManager.


Parameters:
  * _**url**_ - location of document's content stream;
  * _**streamId**_ - the identifier for the rendition stream, when used to get a rendition stream. For documents, if not provided then this method returns the content stream. For folders, it must be provided.

Events:

  * _**org.xcmis.client.gwt.service.object.event.ContentStreamReceivedEvent**_ - when content stream is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.object.event.ContentStreamReceivedHandler**_ - to do actions, when content stream response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.

11. _**updateProperties(String url, UpdateProperties updateProperties)**_ - passes response of updating properties of the specified object (for example, rename) to the particular implementation of org.xcmis.client.gwt.service.object.event.PropertiesUpdatedHandler registered in HandlerManager.

Parameters:
  * _**url**_ - location of the object;
  * _**updateProperties**_ - data for updating properties (repositoryId, objectId, changeToken).

Events:

  * _**org.xcmis.client.gwt.service.object.event.PropertiesUpdatedEvent**_ - when object's properties were updated.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.object.event.PropertiesUpdatedHandler**_ - to do actions, when object's properties updation response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


12. _**moveObject(String url, MoveObject moveObject)**_ - passes the response of moving the specified file-able object from one folder to another to the particular implementation of org.xcmis.client.gwt.service.object.event.ObjectMovedHandler registered in HandlerManager.

Parameters:
  * _**url**_ - location of the target folder;
  * _**moveObject**_ - data for moving object (repositoryId, objectId, targetId, sourceId).

Events:

  * _**org.xcmis.client.gwt.service.object.event.ObjectMovedEvent**_ - when object was moved.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.object.event.ObjectMovedHandler**_ - to do actions, when object moving response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


13. _**deleteObject(String url, boolean allVersions)**_ -  passes the response of deleting object to the particular implementation of org.xcmis.client.gwt.service.object.event.ObjectDeletedHandler registered in HandlerManager.

Parameters
  * _**url**_ - location of the object, you want to delete;
  * _**allVersions**_ - if true, then delete all versions of the document.

Events:

  * _**org.xcmis.client.gwt.service.object.event.ObjectDeletedEvent**_ - when object was deleted.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.object.event.ObjectDeletedHandler**_ - to do actions, when object deletion response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


14. _**deleteTree(String url, boolean allVersions, EnumUnfileObject unfileObject, boolean continueOnFailure)**_ - passes the response of deleting the specified folder object and all of its child- and descendant-objects to the particular implementation of org.xcmis.client.gwt.service.object.event.TreeDeletedHandler registered in HandlerManager.

Parameters:
  * _**url**_ - location of folder;
  * _**allVersions**_ - if true, then delete all versions of the document;
  * _**unfileObject**_ - enumeration specifying how the repository must process file-able child- or descendant-objects (unfile, deletesinglefiled, delete);
  * _**continueOnFailure**_ - if true, then the repository has to continue attempting to perform this operation even if deletion of a child- or descendant-object in the specified folder cannot be deleted.

Events:

  * _**org.xcmis.client.gwt.service.object.event.TreeDeletedEvent**_ - when folder was deleted.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.object.event.TreeDeletedHandler**_ - to do actions, when folder deletion response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


15. _**setContentStream(String url, CmisContentStreamType contentStream, boolean overwriteFlag, String changeToken)**_ - passes the response of setting the content stream for the specified document object to the particular implementation of org.xcmis.client.gwt.service.object.event.ContentStreamSetHandler registered in HandlerManager.

Parameters:
  * _**url**_ - location of document's content stream;
  * _**contentStream**_ - content stream;
  * _**overwriteFlag**_ - if true, then the repository must replace the existing content stream for the object (if any) with the input contentStream;
  * _**changeToken**_ - property that a repository may use for optimistic locking and/or concurrency checking to ensure that user updates do not conflict.

Events:

  * _**org.xcmis.client.gwt.service.object.event.ContentStreamSetEvent**_ - when content stream is set.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.object.event.ContentStreamSetHandler**_ - to do actions, when content stream set response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


16. _**deleteContentStream(String url, String changeToken)**_ - passes the response of deleting the content stream for the specified document object to the particular implementation of org.xcmis.client.gwt.service.object.event.ContentStreamDeletedHandler registered in HandlerManager.

Parameters:
  * _**url**_ - location of document's content stream;
  * _**changeToken**_ - property that a repository may use for optimistic locking and/or concurrency checking to ensure that user updates do not conflict.

Events:

  * _**org.xcmis.client.gwt.service.object.event.ContentStreamDeletedEvent**_ - when document's content stream is deleted.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.object.event.ContentStreamDeletedHandler**_ - to do actions, when document's content stream deletion response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.




### 3.4 Discovery services (org.xcmis.client.gwt.service.discovery.DiscoveryService) ###

1. _**query(String url, Query query)**_ - passes the results of query performing to the particular implementation of org.xcmis.client.gwt.service.discovery.event.QueryResultReceivedHandler registered in HandlerManager.

Parameters:
  * _**url**_ - location of repository query collection;
  * _**query**_ - data to perform query (repositoryId, statement, searchAllVersions, includeAllowableActions, includeRelationships, renditionFilter, maxItems, skipCount).


Events:

  * _**org.xcmis.client.gwt.service.discovery.event.QueryResultReceivedEvent**_ - when query results are received, so they are retrieved:
```
event.getQueryResults().getEntries();
```
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.discovery.event.QueryResultReceivedHandler**_ - to do actions, when query results response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.




### 3.5 Versioning service (org.xcmis.client.gwt.service.versioning.VersioningService) ###

1. _**checkOut(String url, String objectId)**_ - passes the results of creating private working copy (PWC), check-out the document to the particular implementation of org.xcmis.client.gwt.service.versioning.event.CheckoutReceivedHandler registered in HandlerManager.

Parameters:
  * _**url**_ - location of the document;
  * _**objectId**_ - id of the document.

Events:

  * _**org.xcmis.client.gwt.service.versioning.event.CheckoutReceivedEvent**_ - when document is checked out, so document is retrieved:
```
event.getDocument();
```
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.versioning.event.CheckoutReceivedHandler**_ - to do actions, when checkout document response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


2. _**checkin(String url, CheckIn checkIn)**_ - passes the results of creating new version of the document, check-in the PWC to the particular implementation of org.xcmis.client.gwt.service.versioning.event.CheckinReceivedHandler registered in HandlerManager.

Parameters :
  * _**url**_ - location of the document;
  * _**checkIn**_ - data for creating new version (repositoryId, objectId, major, properties).

Events:

  * _**org.xcmis.client.gwt.service.versioning.event.CheckinReceivedEvent**_ - when new version is created, so document is retrieved:
```
event.getDocument();
```
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.versioning.event.CheckinReceivedHandler**_ - to do actions, when checkin document response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


3. _**getAllVersions(String url, String filter, boolean includeAllowableActions)**_ - passes all versions of the document to the particular implementation of org.xcmis.client.gwt.service.versioning.event.AllVersionsReceivedHandler registered in HandlerManager.


Parameters:

  * _**url**_ - location of version series;
  * _**filter**_ - service will only return the properties in the matched object if they exist on the matched object type definition and in the filter;
  * _**includeAllowableActions**_ - if true, then the repository must return the available actions for each object in the result set.

Events:

  * _**org.xcmis.client.gwt.service.versioning.event.AllVersionsReceivedEvent**_ - when document's version are received, so versions are retrieved:
```
event.getVersions().getEntries();
```
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.versioning.event.AllVersionsReceivedHandler**_ - to do actions, when document's versions response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


4. _**cancelCheckout(String url)**_ - passes the response of deleting private working copy from version series (canceling checkout) to the particular implementation of org.xcmis.client.gwt.service.versioning.event.CancelCheckoutReceivedHandler registered in HandlerManager.

Parameter:
  * _**url**_ - location of PWC.

Events:

  * _**org.xcmis.client.gwt.service.versioning.event.CancelCheckoutReceivedEvent**_ - when private working copy is deleted from verion series.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.versioning.event.CancelCheckoutReceivedHandler**_ - to do actions, when cancel checkout response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.

### 3.6 Relationship service (org.xcmis.client.gwt.service.relationship.RelationshipService) ###

1. _**getObjectRelationships(String url, boolean includeSubRelationshipTypes, EnumRelationshipDirection relationshipDirection, String typeId, int maxItems, int skipCount, String filter, boolean includeAllowableActions)**_ - passes all or a subset of relationships associated with an independent object to the particular implementation of org.xcmis.client.gwt.service.relationship.event.RelationshipsReceivedHandler registered in HandlerManager.

Parameters:
  * _**url**_ - location of object's relationships;
  * _**includeSubRelationshipTypes**_ - if true, then the repository must return all relationships whose object-Types are descendant-types of the given object’s cmis:objectTypeId property value as well as relationships of the specified type;
  * _**relationshipDirection**_ - enumeration specifying whether the repository must return relationships where the specified object is the source of the relationship, the targetof the relationship, or both;
  * _**typeId**_ - if specified, then the repository must return only relationships whose object type is of the type specified;
  * _**maxItems**_ -maximum number of items to return in a response;
  * _**skipCount**_ - number of potential results that the repository must skip/page over before returning any results;
  * _**filter**_ - service will only return the properties in the matched object if they exist on the matched object type definition and in the filter;
  * _**includeAllowableActions**_ - if true, then the repository must return the available actions for each object in the result set.

Events:

  * _**org.xcmis.client.gwt.service.relationship.event.RelationshipsReceivedEvent**_ - when relationships' members are received, so they are retrieved:
```
event.getRelationships().getEntries();
```
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.relationship.event.RelationshipsReceivedHandler**_ - to do actions, when relationships' members response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.



### 3.7 Policy service (org.xcmis.client.gwt.service.policy.PolicyService) ###

1. _**applyPolicy(String url, ApplyPolicy applyPolicy)**_ - passes response for applying  a specified policy to an object to the particular implementation of org.xcmis.client.gwt.service.policy.event.PolicyAppliedHandler registered in HandlerManager.

Parameters :
  * _**url**_ - the location of  object's policies;
  * _**applyPolicy**_ - data for applying new policy (repositoryid, objectId, policyId).

Events:

  * _**org.xcmis.client.gwt.service.policy.event.PolicyAppliedEvent**_ - when policy is applied, so policy is retrieved:
```
event.getPolicy();
```
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.policy.event.PolicyAppliedHandler**_ - to do actions, when policy applied response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


2. _**removePolicy(String url, RemovePolicy removePolicy)**_ - passes response of removing a specified policy from an object to the particular implementation of org.xcmis.client.gwt.service.policy.event.PolicyRemovedHandler registered in HandlerManager.

Parameters:
  * _**url**_ - location of item's policies;
  * _**removePolicy**_ - data for removing policy from object (repositoryid, objectId, policyId).

Events:

  * _**org.xcmis.client.gwt.service.policy.event.PolicyRemovedEvent**_ - when policy is removed from object.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.policy.event.PolicyRemovedHandler**_ - to do actions, when policy removed response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


3. _**getAllPolicies(String url, String repositoryId, boolean searchAllVersions, boolean includeAllowableActions, EnumIncludeRelationships includeRelationships, String renditionFilter, Long maxItems, Long skipCount)**_ - passes all policies of the repository to the particular implementation of org.xcmis.client.gwt.service.policy.event.AllPoliciesReceivedHandler registered in HandlerManager.

Parameters:
  * _**url**_ - location of the repository's query collection;
  * _**repositoryId**_ - id of the repository;
  * _**searchAllVersions**_ - if true, then the repository must include latest and non-latest versions of document objects in the query search scope;
  * _**includeAllowableActions**_ - if true, then the repository must return the available actions for each object in the result set;
  * _**maxItems**_ -maximum number of items to return in a response;
  * _**skipCount**_ - number of potential results that the repository must skip/page over before returning any results;
  * _**filter**_ - service will only return the properties in the matched object if they exist on the matched object type definition and in the filter;
  * _**includeRelationships**_ - value indicating what relationships in which the objects returned participate must be returned, if any;
  * _**renditionFilter**_ - the repository must return the set of renditions whose kind matches this filter.

Events:

  * _**org.xcmis.client.gwt.service.policy.event.AllPoliciesReceivedEvent**_ - when all policies are applied, so they are retrieved:
```
event.getPolicies().getEntries();
```
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.policy.event.AllPoliciesReceivedHandler**_ - to do actions, when all policies of the repository response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


4. _**getAppliedPolicies(String url, String filter);**_ - passes the list of policies currently applied to the specified object to the particular implementation of org.xcmis.client.gwt.service.policy.event.AppliedPoliciesReceivedHandler registered in HandlerManager.

Parameters:
  * _**url**_ - the location of item's policies;
  * _**filter**_ - service will only return the properties in the matched object if they exist on the matched object type definition and in the filter.

Events:

  * _**org.xcmis.client.gwt.service.policy.event.AppliedPoliciesReceivedEvent**_ - when applied object's policies are received, so they are retrieved:
```
event.getPolicies().getEntries();
```
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.policy.event.AppliedPoliciesReceivedHandler**_ - to do actions, when object's applied policies response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.

### 3.8 ACL service (org.xcmis.client.gwt.service.acl.ACLService) ###

1. _**getACL(String url, boolean onlyBasicPermissions)**_ - passes the ACL currently applied to the specified document or folder object to the particular implementation of org.xcmis.client.gwt.service.acl.event.ACLReceivedHandler registered in HandlerManager.

Parameters :
  * _**url**_ - object acl's location;
  * _**onlyBasicPermissions**_ - if true, then indicates that the client requests that the returned ACL be expressed using only the CMIS Basic permissions.

Events:

  * _**org.xcmis.client.gwt.service.acl.event.ACLReceivedEvent**_ - when access control list was received, so it is retrieved:
```
event.getAccessControlList();
```
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.acl.event.ACLReceivedHandler**_ - to do actions, when access control list response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.


2. _**applyACL(String url, ApplyACL applyACL)**_ - passes response of adding or removing the given ACEs to or from the ACL of document or folder object to the particular implementation of org.xcmis.client.gwt.service.acl.event.ACLAppliedHandler registered in HandlerManager.

Parameters:
  * _**url**_ - location of object's ACL;
  * _**applyACL**_ - data for applying ACL.

Events:

  * _**org.xcmis.client.gwt.service.acl.event.ACLAppliedEvent**_ - when the given ACEs are added or removed  to or from the ACL.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownEvent**_ - if request failed or some error occured.

Handlers to implement:

  * _**org.xcmis.client.gwt.service.acl.event.ACLAppliedHandler**_ - to do actions, when access control list applied response is received.
  * _**org.xcmis.client.gwt.rest.ExceptionThrownHandler**_ - to do actions, when error occurs.
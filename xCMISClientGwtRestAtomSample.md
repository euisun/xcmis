This document refers to 1.0 and 1.1 version of xCMIS.



# Introduction #

This article describes how to use GWT CMIS framework in your web application on concrete simple example.

# Description #

First, take or create the blank of GWT Web Application. You can use Google Plugin for Eclipse. How to get it and install, you can find at: http://code.google.com/intl/uk/eclipse/docs/download.html; and how to create new web application at : http://code.google.com/intl/uk/eclipse/docs/creating_new_webapp.html.

1. In your entry point method create events' handler manager, typing the following:
```
  public void onModuleLoad()
  {
      HandlerManager eventBus = new HandlerManager(null);
      /*Create main client form for displaying content*/
      new SampleForm(eventBus);
  }
```

2. SampleForm is the main form for displaying user's content. It contains following visual elements:

a) panel for veiwing repositories;

b) three buttons for
  * creating new folder,
  * creating new document,
  * deleting selected object;

c) tab panel for displaying objects of the current repository; object is represented as tab with it's property list

and presenter from MVP pattern:
```
      presenter = new SamplePresenter(eventBus);
      presenter.bindDisplay(this);
```



3. SampleForm implements SamplePresenter.Display with the following metods:
```
   public interface Display
   {
      
      /**
       * Create new folder button click handler
       * 
       * @return {@link HasClickHandlers}
       */
      HasClickHandlers getCreateFolderButton();

      /**
       * Create new document button click handler
       * 
       * @return {@link HasClickHandlers}
       */
      HasClickHandlers getCreateDocumentButton();

      /**
       * Delete object button click handler
       * 
       * @return {@link HasClickHandlers}
       */
      HasClickHandlers getDeleteButton();
      
      /**
       * Display repository
       * 
       * @param repositoryInfo
       */
      void displayRepository(CmisRepositoryInfo repositoryInfo);
      
      /**
       * Show the list of objects
       * 
       * @param entries
       */
      void showObjects(List<AtomEntry> entries);

      /**
       * Add new object to view
       * 
       * @param entry
       */
      void addObject(AtomEntry entry);
      
      /**
       * Remove object from view. 
       * Returns url for deleting object
       * 
       * @return {@link String}
       */
      String removeObject();
      
      /**
       * Enabling/disabling create buttons
       * 
       * @param enable
       */
      void setEnableCreateButtons(boolean enable);
      
      /**
       * Enabling/disabling delete button
       * 
       * @param enable
       */
      void setEnableDeleteButton(boolean enable);
   }
```

4. Create instances of repository, navigation and object services:

```
   public SamplePresenter(HandlerManager eventBus)
   {
      this.eventBus = eventBus;
      repositoryService = new RepositoryService(eventBus);
      navigationService = new NavigationService(eventBus);
      objectService = new ObjectService(eventBus);
   }

```

5. In SamplePresenter in bindDisplay we subscribe on events, which are fired when server requests are recieved. SamplePresenter has to implement event handlers. In our application we will process RepositoriesReceivedEvent, ChildrenReceivedEvent, FolderCreatedEvent and DocumentCreatedEvent. Also bindDisplay method contains user action handlers (create and delete buttons click):

```
   public void bindDisplay(Display d)
   {
      display = d;
      
      /*Subscribe presenter on events to handle them*/
      eventBus.addHandler(RepositoriesReceivedEvent.TYPE, this);
      eventBus.addHandler(ChildrenReceivedEvent.TYPE, this);
      eventBus.addHandler(FolderCreatedEvent.TYPE, this);
      eventBus.addHandler(DocumentCreatedEvent.TYPE, this);  
      eventBus.addHandler(RepositoryInfoReceivedEvent.TYPE, this);
      eventBus.addHandler(ExceptionThrownEvent.TYPE, this);

      display.getCreateFolderButton().addClickHandler(new ClickHandler()
      {
         public void onClick(ClickEvent arg0)
         {
            createFolder();
         }
      });

      display.getCreateDocumentButton().addClickHandler(new ClickHandler()
      {
         public void onClick(ClickEvent arg0)
         {
            createDocument();
         }
      });

      display.getDeleteButton().addClickHandler(new ClickHandler()
      {
         public void onClick(ClickEvent event)
         {
            String url = display.removeObject();
            if (url != null){
               deleteObject(url);
            }
         }
      });

      RepositoryServices.getInstance().getRepositories(url);
   }
```

6. Make first request to get a list of CMIS repositories available from this CMIS service endpoint:
```
repositoryService.getRepositories(url);
```

7. Handler RepositoriesReceivedEvent, display received repositories and get full information of the first repository's in the list:

```
   public void onRepositoriesReceived(RepositoriesReceivedEvent event)
   {
      List<CmisRepositoryInfo> repositories = event.getRepositories().list();
      for (CmisRepositoryInfo repository : event.getRepositories().list())
      {
         display.displayRepository(repository);
      }

      if (repositories.size() > 0)
      {
         currentRepository = repositories.get(0);
         repositoryService.getRepositoryInfo(url + "/" + currentRepository.getRepositoryName());
      }
   }
```

8. When repository information is received - get the content of it's root folder:
```
   public void onRepositoryInfoReceived(RepositoryInfoReceivedEvent event)
   {
      int maxItems = 10;
      int skipCount = 0;
      EnumIncludeRelationships includeRelationships = EnumIncludeRelationships.NONE;
      String renditionFilter = EnumRenditionFilter.NONE_FILTER.value();
      boolean includeAllowableActions = true;
      boolean includePathSegment = false;
      currentRepository = event.getRepositoryInfo();
      String rootFolderUrl = currentRepository.getCollectionValue(EnumCollectionType.ROOT);
      navigationService.getChildren(rootFolderUrl, maxItems, skipCount, null, includeRelationships, renditionFilter,
         includeAllowableActions, includePathSegment);
      display.setEnableCreateButtons(true);
   }
```

9. When root folder content response will be received:
```
   public void onChildrenReceived(ChildrenReceivedEvent event)
   {
      int count = event.getEntries().getEntries().size();
      if (count > 0)
      {
         display.showObjects(event.getEntries().getEntries());
      }
      else
      {
         Window.alert("No objects found.");
      }
   }
```

10. When user clicks on create folder or document button - it is necessary to make request for creating new object. Create bean CreateDocument or CreateFolder and fill it with parameters' values for performancing the operation and then call createDocument or createFolder Object Services' method:

```
   /**
    * Create new folder object
    */
   private void createFolder()
   {
      CreateFolder createFolder = new CreateFolder();
      createFolder.setRepositoryId(currentRepository.getRepositoryId());
      createFolder.setFolderId(currentRepository.getRootFolderId());

      newFolderCount++;
      CmisProperties properties = new CmisProperties(new HashMap<String, Property<?>>());
      String propertyId = CMIS.CMIS_NAME;
      String name = "Folder " + newFolderCount;
      properties.getProperties().put(propertyId, new StringProperty(propertyId, propertyId, propertyId, propertyId, name));
      propertyId = CMIS.CMIS_OBJECT_TYPE_ID;
      properties.getProperties().put(propertyId, new IdProperty(propertyId, propertyId, propertyId, propertyId, CMIS.BASE_TYPE_FOLDER));
      createFolder.setProperties(properties);

      String rootFolderUrl = currentRepository.getCollectionValue(EnumCollectionType.ROOT);
      objectService.createFolder(rootFolderUrl, createFolder);
   }

   /**
    * Create new document object
    */
   private void createDocument()
   {
      CreateDocument createDocument = new CreateDocument();
      createDocument.setRepositoryId(currentRepository.getRepositoryId());
      createDocument.setFolderId(currentRepository.getRootFolderId());

      newDocumentCount++;

      CmisProperties properties = new CmisProperties(new HashMap<String, Property<?>>());
      String propertyId = CMIS.CMIS_NAME;
      String name = "Document " + newDocumentCount;
      properties.getProperties().put(propertyId, new StringProperty(propertyId, propertyId, propertyId, propertyId, name));
      propertyId = CMIS.CMIS_OBJECT_TYPE_ID;
      properties.getProperties().put(propertyId, new IdProperty(propertyId, propertyId, propertyId, propertyId, CMIS.BASE_TYPE_DOCUMENT));
      createDocument.setProperties(properties);

      String rootFolderUrl = currentRepository.getCollectionValue(EnumCollectionType.ROOT);

      objectService.createDocument(rootFolderUrl, createDocument);
   }

```

11. When new object is created, then add it to view:

```
   public void onFolderCreated(FolderCreatedEvent event)
   {
      display.addNewObject(event.getFolder());
   }

   public void onDocumentCreated(DocumentCreatedEvent event)
   {
      display.addNewObject(event.getDocument());
   }
```

12. When user clicks the delete button, selected object must be removed from view and request for deleting it on server called:
```
   public void deleteObject(String url)
   {
      objectService.deleteObject(url, true);
   }
```

## Processing errors ##

There is no guarantees that everything will work perfectly, so it is also important to process errors. Do the following:

1. Add to implementation list _org.xcmis.client.gwt.rest.ExceptionThrownHandler_.

2. Register handler in you handler manager:
```
eventBus.addHandler(ExceptionThrownEvent.TYPE, this);
```

3. Add implementation to method _onError(ExceptionThrownEvent event)_:
```
 public void onError(ExceptionThrownEvent event)
   {
      Throwable error = event.getError();
      if (error instanceof ServerException)
      {
         ServerException serverException = (ServerException)error;
         if (serverException.isErrorMessageProvided())
         {
            Window.alert(serverException.getMessage());
         }
         else if (event.getErrorMessage() != null)
         {
            Window.alert(event.getErrorMessage());
         }
         else
         {
            String errorText = "" + serverException.getHTTPStatus() + "&nbsp;" + serverException.getStatusText();
            Window.alert(errorText);
         }
      }
      else
      {
         Window.alert(error.getMessage());
         error.printStackTrace();
      }
   }
```

If there is "JAXRS-Body-Provided" header in response, then error message is provided by server.
Also there may be error message in _event.getErrorMessage()_. In other case, you can see http response status code and text.
That's how, it is possible get information about what went wrong.


Here, only some base operations are described of the simple application, but you can make use of other CMIS Services and expand the functionality of you application. Source of the example is available here http://xcmis.googlecode.com/svn/trunk/xcmis-client-gwt-restatom-sample.


# Run the application from Eclipse #

## Requirements ##

  * Google Plugin for Eclipse http://code.google.com/intl/uk/eclipse/docs/download.html;
  * Google Web Toolkit SDK 1.7.1 http://code.google.com/intl/uk/webtoolkit/versions.html.
  * console SVN client.


## Step 1 ##

Download sample application on your computer here http://xcmis.googlecode.com/svn/trunk/xcmis-client-gwt-restatom-sample.

```
svn co http://xcmis.googlecode.com/svn/trunk/xcmis-client-gwt-restatom-sample xcmis-client-gwt-restatom-sample
```

## Step 2 ##

Open Eclipse IDE and import sample application by clicking "File->Import->Existing Projects into Workspace"  and select the folder with sample application pressing "Browse..." and then click "Finish".

![http://downloads.exoplatform.org/importProject.png](http://downloads.exoplatform.org/importProject.png)

Make sure that Google Web Toolkit SDK 1.7.1 is used:

![http://downloads.exoplatform.org/gwt.png](http://downloads.exoplatform.org/gwt.png)

## Step 3 ##

Click "Run->xcmis-client-gwt-restatom-sample" :

![http://downloads.exoplatform.org/Run.png](http://downloads.exoplatform.org/Run.png)

and the application will be run in GWT HostedMode, click "Compile/Browse" to view the application in your default browser:

![http://downloads.exoplatform.org/SampleApplication.png](http://downloads.exoplatform.org/SampleApplication.png)

## Troubleshooting ##
  * If during installation the plugin in your eclipse there's an error about requiring _org.eclipse.wst.sse.ui_ - look at http://code.google.com/intl/uk/eclipse/docs/faq.html#wstinstallerror
  * GWT Hosted Mode on 64 Bit Ubuntu
    * Install ia32-sun-java6-bin:
> > > ` sudo apt-get install ia32-sun-java6-bin `
    * Make sure the default Java installation remains the 64 bit one
> > > `sudo update-java-alternatives --set java-6-sun `
    * Download libstdc++5 for IA32 from Jaunty repos: http://archive.ubuntu.com/ubuntu/pool/universe/g/gcc-3.3/libstdc++5_3.3.6-17ubuntu1_i386.deb
    * Extract the libstdc++.so.5 file
> > > `dpkg-deb --fsys-tarfile libstdc++5_3.3.6-17ubuntu1_i386.deb  | tar -O --extract './usr/lib/libstdc++.so.5.0.7' > libstdc++.so.5  `
    * Move the extracted file to /usr/lib32
> > > ` sudo mv libstdc++.so.5 /usr/lib32  `
    * Run
> > > `sudo ldconfig `
  * Application in GWT Hosted Mode, where Mozilla Firefox browser engine is uses, has such trouble, described at http://code.google.com/p/google-web-toolkit/issues/detail?id=719 Nevertheless, to view the application click "Compile/Browse" on the panel of Hosted Mode Browser.
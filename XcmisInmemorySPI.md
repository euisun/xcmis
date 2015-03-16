_This document refers to 1.2.x version of xCMIS._



# Configuration #

## Provide the implementation of CmisRegistryFactory ##

In the xcmis WAR application containts the file: `META-INF/services/xcmis/`**`org.xcmis.CmisRegistryFactory`**
with content:
```
org.xcmis.sp.inmemory.tck.InmemoryCmisRegistryFactory
```

This is FQN class name of the implementation of CmisRegistryFactory.

## Properties for the InmemoryCmisRegistryFactory ##

In the xcmis WAR application containts the file:
`WEB-INF/classes/`**`xcmis-storage.properties`**

```
org.xcmis.storage.id=cmis1, cmis2
org.xcmis.storage.cmis1.description=
org.xcmis.storage.cmis1.name=cmis1
org.xcmis.storage.cmis1.maxMem=-1
org.xcmis.storage.cmis1.maxItemsNum=-1

org.xcmis.storage.cmis2.name=cmis2
org.xcmis.storage.cmis2.description=
org.xcmis.storage.cmis2.maxMem=-1
org.xcmis.storage.cmis2.maxItemsNum=-1

org.xcmis.storage.renditionProvider=org.xcmis.renditions.impl.ImageRenditionProvider
```

# Request flow of first initialization #

  * **`-> org.xcmis.spi.CmisRegistry.getInstance()`**

Registry factory stored here in the field `private static AtomicReference<CmisRegistryFactory>`.

If there is no registry factory, then looks below, otherwise return registry from stored registry factory.


  * **`-> -> org.xcmis.spi.CmisRegistryFactoryFinder.findCmisRegistry()`**

Found FQN registry factory from `org.xcmis.CmisRegistryFactory` file, initializing...


  * **`-> -> -> org.xcmis.sp.inmemory.tck.InmemoryCmisRegistryFactory.InmemoryCmisRegistryFactory()`**

Create an empty registry, creating storage provider instance...
```
new CmisRegistry().addStorage(new org.xcmis.sp.inmemory.StorageProviderImpl(....))
```

For the instantiate StorageProviderImpl used the properties from the `xcmis-storage.properties` file.

The cmis registry instance stored here in the private field.


  * **`-> -> -> -> org.xcmis.sp.inmemory.StorageProviderImpl.StorageProviderImpl(.....)`**

Creating a storage instance...

```
new StorageImpl(storageConfig, renditionManager, new PermissionService());
```

The storage instance stored here in the private field. It uses to create connection.


  * **`-> -> -> -> org.xcmis.sp.inmemory.StorageProviderImpl.getConnection()`**

This method creates the instance InmemConnection with the storage instance in it.
```
return new InmemConnection(storageImpl);
```
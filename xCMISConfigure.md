_This document refers to 1.1.0 version of xCMIS._


xCMIS is implemented as a set of components deployed on eXo (IoC) Container and exploits standard eXo configuration mechanism which uses XML files to describe the configuration to discover and deploy by eXo container mechanism.
[eXo JCR Implementation](http://docs.exoplatform.com/PLF35/index.jsp?topic=%2Forg.exoplatform.doc.35%2FJCRReferenceGuide.html)

# CMIS storage provider configuration #

In the server bundles provided with xCMIS an application server (Tomcat) launches Standalone eXo Container with exploded configuration which you may need to modify. This configuration is placed in _/**xcmis-server-war**/src/main/webapp/WEB-INF/classes/conf/**exo-configuration.xml**_.
It is available in binary at _**xcmis.war**#/WEB-INF/classes/conf/**exo-configuration.xml**_.

### Permission Service ###
```
        <component>
          <type>org.xcmis.spi.PermissionService</type>
        </component>
```


### StorageProvider ###
In particular, you may need to change the Storage Provider's type and parameters. So, the main entry point to the "interesting" part of configuration is **StorageProvider**. For the time being xCMIS supports two types of SP: eXo JCR and Inmemory (mainly for testing purpose).

This component provides configuration of:
  * ID of CMIS repository


Here is how inmemory configuration looks like:
```
   <component>
      <type>org.xcmis.sp.inmemory.StorageProviderImpl</type>
	 <init-params>
	   <object-param>
	     <name>configs</name>
		<object type="org.xcmis.sp.inmemory.StorageProviderImpl$StorageProviderConfig">
		  <field name="storage">
			<object type="org.xcmis.sp.inmemory.StorageConfiguration">
			    <field name="id">
			       <!-- ID of CMIS repository -->
			       <string>cmis1</string>
		            </field>
			    <field name="properties">
			       <map type="java.util.HashMap">
				 <entry>
				   <key>
				    <string>org.xcmis.inmemory.maxmem</string>
				   </key>
				   <value>
				    <string>50MB</string>
				   </value>
				 </entry>
				 <entry>
				   <key>
				    <string>org.xcmis.inmemory.maxitems</string>
				   </key>
				   <value>
				    <string>50</string>
				   </value>
				 </entry>
			       </map>
			   </field>
                      </object>
	    	  </field>
		</object>

```
There are additional properties for in-memory storage.
  * _org.xcmis.inmemory.maxmem_ set max memory size allowed for storage. It is not precise calculation. Only document content is calculated. Default value is 100MB.
  * _org.xcmis.inmemory.maxitems_ set max number of objects can be added in storage.Default value is 100.

If one of thresholds is exceed then org.xcmis.spi.CmisRuntimeException will be thrown and no more objects can be added in storage.

Following links will help you with the detail explanation of [configuring search](http://code.google.com/p/xcmis/wiki/xCMISSearch) and [configuring rendition providers](http://code.google.com/p/xcmis/wiki/xCMISrenditionProviders).



# Authenticator and organization service #

An Authenticator is responsible for Identity creating [Security Service Authenticator](http://docs.exoplatform.com/PLF35/topic/org.exoplatform.doc.35/Core.SecurityService.Framework.Authenticator.html)

xCMIS Tomcat bundle use default implementation of the [Organization Service](http://docs.exoplatform.com/PLF35/topic/org.exoplatform.doc.35/sect-Reference_Guide-APIs-Organization_API.html).

### DummyOrganizationService ###
```
   <component>
      <type>org.exoplatform.services.organization.impl.mock.DummyOrganizationService</type>
   </component>
```

### JAAS configuration ###
```
exo-domain {
  org.exoplatform.services.security.j2ee.TomcatLoginModule required;
};
```

# See also #

  * HOW TO configure xCMIS server JCR storage on MySQL
    * The wiki page at [xCMISConfigureJcrStorageOnMySQL](xCMISConfigureJcrStorageOnMySQL.md)
  * HOW-TO change authentication method and/or write own Authenticator.
    * The wiki page at [xCMISAuthentication](xCMISAuthentication.md)
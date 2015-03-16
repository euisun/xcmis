_This document refers to 1.0 version of xCMIS._


# Introduction #

This article describing steps how to integrate xCMIS into GateIn.
Note: This article implies using latest xCMIS version (1.0)
(http://xcmis.googlecode.com/svn/tags/1.0/) & GateIn final version  3.0.0
(http://www.jboss.org/gatein/downloads.html).

# Requirements #

  * JDK 1.6 (required by GateIn),
  * Maven 2.2.0 (or upper),
  * console SVN client.


# 1. Installing and setup #

  1. 1 Get xCMIS code into folder _xcmis_ the by running command
```
svn co http://xcmis.googlecode.com/svn/tags/1.0/ xcmis
```
> > at the parent folder level.

  1. 2 Run
```
mvn clean install
```
> > command within _xcmis_ folder.

  1. 3 Download GateIn 3.0.0-FINAL Tomcat bundle from http://www.jboss.org/gatein/downloads.html

  1. 4 Extract archive into some folder;

# 2. Configuring #


> 2.1 Creating workspace for xCMIS
> > In the folder where GateIn was extracted in step 1.4, open the config file located in _/webapps/portal.war/WEB-INF/conf/jcr/**repository-configuration.xml**_, and add an extra workace as followed:
```
<!-- Existed workspaces -->

       <workspace name="cmis">
         <container class="org.exoplatform.services.jcr.impl.storage.jdbc.JDBCWorkspaceDataContainer">
           <properties>
             <property name="source-name" value="jdbcexo" />
             <property name="dialect" value="hsqldb" />
             <property name="multi-db" value="false" />
             <property name="update-storage" value="false" />
             <property name="max-buffer-size" value="200k" />
             <property name="swap-directory" value="../temp/swap/cmis" />
           </properties>
           <value-storages>
             <value-storage id="cmis"
class="org.exoplatform.services.jcr.impl.storage.value.fs.TreeFileValueStorage">
               <properties>
                 <property name="path" value="../temp/values/cmis" />
               </properties>
               <filters>
                 <filter property-type="Binary"/>
               </filters>
             </value-storage>
           </value-storages>
         </container>
         <initializer
class="org.exoplatform.services.jcr.impl.core.ScratchWorkspaceInitializer">
           <properties>
             <property name="root-nodetype" value="nt:unstructured" />
           </properties>
         </initializer>
         <cache enabled="true"
class="org.exoplatform.services.jcr.impl.dataflow.persistent.LinkedWorkspaceStorageCacheImpl">
           <properties>
             <property name="max-size" value="10k" />
             <property name="live-time" value="1h" />
           </properties>
         </cache>
       </workspace>

<!-- Existed workspaces -->
```


> 2.2 Create file
> > _/webapps/portal.war/WEB-INF/conf/**cmis-configuration.xml**_ and add content as followed:
```
<configuration
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.exoplaform.org/xml/ns/kernel_1_1.xsd
http://www.exoplaform.org/xml/ns/kernel_1_1.xsd"
  xmlns="http://www.exoplaform.org/xml/ns/kernel_1_1.xsd">

   <component>
	<type>org.xcmis.spi.deploy.ExoContainerCmisRegistry</type>
            <init-params>
		<values-param>
   		  <name>renditionProviders</name>
		  <description>Redition providers classes.</description>
		  <value>org.xcmis.renditions.impl.PDFDocumentRenditionProvider</value>
		  <value>org.xcmis.renditions.impl.ImageRenditionProvider</value>
		</values-param>
	     </init-params>
  </component>


   <component>
      <key>org.exoplatform.services.document.DocumentReaderService</key>
      <type>org.xcmis.spi.utils.CmisDocumentReaderService</type>
   </component>

   <component>
      <type>org.xcmis.spi.PermissionService</type>
   </component>

   <component>
     <type>org.xcmis.sp.jcr.exo.StorageProviderImpl</type>
     <init-params>
        <object-param>
           <name>configs</name>
           <object type="org.xcmis.sp.jcr.exo.StorageProviderImpl$StorageProviderConfig">
              <field name="storage">
                   <object  type="org.xcmis.sp.jcr.exo.StorageConfiguration">
                          <field name="id">
                             <!-- ID of CMIS repository -->
                             <string>cmis1</string>
                          </field>
                          <field name="repository">
                             <string>repository</string>
                          </field>
                          <field name="workspace">
                             <string>cmis</string>
                          </field>
                          <field name="indexConfiguration">
                             <object type="org.xcmis.search.config.IndexConfiguration">
                                <field name="indexDir">
                                   <string>../gatein/index/repository/cmis</string>
                                </field>
                             </object>
                          </field>
                   </object>
              </field>
           </object>
        </object-param>
     </init-params>
  </component>

  <external-component-plugins>
     <target-component>org.exoplatform.services.naming.InitialContextInitializer</target-component>
     <component-plugin>
        <name>bind.datasource</name>
        <set-method>addPlugin</set-method>
        <type>org.exoplatform.services.naming.BindReferencePlugin</type>
        <init-params>
           <value-param>
              <name>bind-name</name>
              <value>jdbcexo</value>
           </value-param>
           <value-param>
              <name>class-name</name>
              <value>javax.sql.DataSource</value>
           </value-param>
           <value-param>
              <name>factory</name>
              <value>org.apache.commons.dbcp.BasicDataSourceFactory</value>
           </value-param>
           <properties-param>
              <name>ref-addresses</name>
              <description>ref-addresses</description>
              <property name="driverClassName" value="org.hsqldb.jdbcDriver" />
              <property name="url" value="jdbc:hsqldb:file:../temp/data/exodb" />
              <property name="username" value="sa" />
              <property name="password" value="" />
           </properties-param>
        </init-params>
     </component-plugin>
  </external-component-plugins>

</configuration>
```


> 2.3 Import your newly created configuration in existed
> > _/webapps/portal.war/WEB-INF/conf/**configuration.xml**_
```
<!-- Other configuration -->
<import>war:/conf/cmis-configuration.xml</import>
<!-- Other configuration -->
```


> Do not forget to save portal.war file after making changes.

> 2.4 Copy all necessary xCMIS .jar files from the _xcmis/**xcmis-server-war**/target/xcmis/WEB-INF/**lib**_ into the _/lib_ folder of GateIn Tomcat.
> > Those .jar's are:
      * xcmis-renditions-1.0.jar;
      * xcmis-restatom-1.0.jar;
      * xcmis-search-model-1.0.jar
      * xcmis-search-parser-cmis-1.0.jar
      * xcmis-search-service-1.0.jar;
      * xcmis-sp-jcr-exo-1.0.jar;
      * xcmis-spi-1.0.jar;


> 2.5 Copy necessary required thirdparty .jar files from the same location into the GateIn _/lib_ folder:
> > Those .jar's are:
      * abdera-client-0.4.0-incubating.jar
      * abdera-core-0.4.0-incubating.jar
      * abdera-i18n-0.4.0-incubating.jar
      * abdera-parser-0.4.0-incubating.jar
      * abdera-server-0.4.0-incubating.jar
      * antlr-runtime-3.1.3.jar
      * axiom-api-1.2.5.jar
      * axiom-impl-1.2.5.jar
      * fontbox-1.1.0.jar
      * jaxen-1.1.1.jar
      * lucene-memory-2.4.1.jar
      * lucene-regex-2.4.1.jar
      * lucene-spellchecker-2.4.1.jar
      * pdfbox-1.1.0.jar


> 2.6  Remove old version of pdfbox library from GateIn _/lib_ folder to avoid conflicts:
    * pdfbox-0.7.3.jar


# 3. Launching #

> Run the GateIn with the with the following command within _/bin_ folder:
  * On the Windows platform
> > Open a DOS prompt command and type the command
```
gatein.bat run
```

  * On Unix/linux/cygwin/MacOSX
> > Type the command:
```
./gatein.sh run
```


> AtomPub xCMIS service will be available at the URL:
> http://localhost:8080/rest/private/cmisatom

> Use user name: _**root**_ and password: _**gtn**_ for login.
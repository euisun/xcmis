_This document refers to 1.2.1 version of xCMIS._





---



# Description #

The download links are

  * War zipped application [xcmis.war](http://repository.exoplatform.org/content/groups/public/org/exoplatform/cmis/exo-cmis-standalone-bonita/1.2.0.4-GA/exo-cmis-standalone-bonita-1.2.0.4-GA.war)
  * Tomcat add files [eXo/xCMIS configuration files](https://jira.exoplatform.org/secure/attachment/34542/xcmis-tomcat-files-2011-04-29.zip)
  * Tomcat 6.0.32 from [official binary distributions](http://tomcat.apache.org/download-60.cgi#6.0.32)

Put the xcmis.war to the /webapps Tomcat's dir.

Unzip the conf files to the Tomcat's root dir.

The files list
```
/Readme.txt
/bin/xcmis-hsql.sh
/bin/xcmis-mysql.sh
/conf/jaas.conf
/ext-exo-conf/exo-configuration-hsql.xml
/ext-exo-conf/exo-configuration-mysql.xml
/ext-exo-conf/cmis-jcr-configuration-hsql.xml
/ext-exo-conf/cmis-jcr-configuration-mysql.xml
/ext-exo-conf/nodetypes-config.xml
/ext-exo-conf/nodetypes-config-extended.xml
/ext-exo-conf/cmis-nodetypes-config.xml
/ext-exo-conf/organization-nodetypes.xml
```

The bundle is tested on HSQLDB configuration database.

  1. Run Tomcat
    * On Unix/linux/cygwin/MacOSX
> > > Open a terminal and type the command:
> > > `./xcmis-hsql.sh run`
> > > You may need to change the permission of all `*`.sh files in the `tomcat/bin` dir by using: chmod +x `*`.sh.
  1. Available services URLs:
    * Home page at the URL "http://localhost:8080/xcmis"
    * REST Atom services will be available at the URL "http://localhost:8080/xcmis/rest/cmisatom"
    * SOAP services should be available at the URL "http://localhost:8080/xcmis/cmisws"
  1. Default service credential: username is "root" and password is "exo".

The related wiki in eXo is [here](https://wiki-int.exoplatform.org/display/PLF/xCMIS+for+Bonita+Document+Library)

# Libraries #

Libraries are placed in the xcmis.war at /WEB-INF/lib.


There are three ECMS jars

```
exo-ecms-ext-xcmis-sp-2.1.4.1-bonita.jar (!) (ECMS-2195, ECMS-2234, ECMS-2275, ECMS-2277, ECMS-2296)
exo-ecms-core-parser-2.1.4.jar
exo-ecms-core-services-2.1.4.jar
```
([ECMS-2195](https://jira.exoplatform.org/browse/ECMS-2195), [ECMS-2234](https://jira.exoplatform.org/browse/ECMS-2234), [ECMS-2275](https://jira.exoplatform.org/browse/ECMS-2275), [ECMS-2277](https://jira.exoplatform.org/browse/ECMS-2277), [ECMS-2296](https://jira.exoplatform.org/browse/ECMS-2296))

several xCMIS jars

```
xcmis.jar
xcmis-renditions-1.2.1.jar
xcmis-restatom-1.2.1.jar
xcmis-search-model-1.2.1.jar
xcmis-search-parser-cmis-1.2.1.jar
xcmis-search-service-1.2.1.jar
xcmis-spi-1.2.1.jar
xcmis-sp-inmemory-1.2.1.jar
xcmis-wssoap-1.2.1.jar
```

and other eXo jars

```
everrest-core-1.0.jar
exo.core.component.database-2.3.8-GA.jar
exo.core.component.document-2.3.8-GA.jar
exo.core.component.organization.api-2.3.8-GA.jar
exo.core.component.organization.jdbc-2.3.8-GA.jar
exo.core.component.security.core-2.3.8-GA.jar
exo.core.component.xml-processing-2.3.8-GA.jar
exo.jcr.component.core-1.12.8-bonita.jar (!) (JCR-1622)
exo.jcr.component.ext-1.12.8-GA.jar
exo-jcr-services-1.12.8-GA.jar
exo.kernel.commons-2.2.8-GA.jar
exo.kernel.component.cache-2.2.8-GA.jar
exo.kernel.component.command-2.2.8-GA.jar
exo.kernel.component.common-2.2.8-GA.jar
exo.kernel.container-2.2.8-GA.jar
exo.ws.frameworks.servlet-2.1.8-bonita.jar (!) (WS-264, WS-265)
```
([JCR-1622](https://jira.exoplatform.org/browse/JCR-1622))
([WS-264](https://jira.exoplatform.org/browse/WS-264), [WS-265](https://jira.exoplatform.org/browse/WS-265))

and other (91 more items) third-party jars.

NOTE: updated hsqldb-1.8.0.10.jar (was: hsqldb-1.8.0.7.jar) (!)

NOTE: We use pdfbox-1.1.0.jar in this bundle (was: pdfbox-1.4.0.jar). (!)

NOTE: This bundle has a mysql-connector-java-5.1.14.jar.

# Configuration #

The bundle is tested on HSQLDB configuration database.

## eXo system properties ##

### exo.data.dir ###
The "exo.data.dir" property configured the folder for the data.

### org.exoplatform.container.standalone.config ###
The "org.exoplatform.container.standalone.config" property configured the place for the eXo configuration files.

## Tomcat ##

Configured in the xcmis-hsql.sh file for UNIX OS
```
LOG_OPTS="-Dorg.apache.commons.logging.Log=org.apache.commons.logging.impl.SimpleLog"
SECURITY_OPTS="-Djava.security.auth.login.config=../conf/jaas.conf"
XCMIS_OPTS="-Xshare:auto -Xms1024m -Xmx2048m -Dexo.data.dir=../ext-exo-data -Dorg.exoplatform.container.standalone.config=../ext-exo-conf/exo-configuration-hsql.xml"

JAVA_OPTS="$JAVA_OPTS $LOG_OPTS $SECURITY_OPTS $XCMIS_OPTS"
export JAVA_OPTS
```

or configured in the setenv.bat file for Windows OS
```
set SECURITY_OPTS="-Djava.security.auth.login.config=%CATALINA_HOME%/conf/jaas.conf"
set MEMORY_OPTS="-Xshare:auto -Xms512m -Xmx1024m -XX:MaxPermSize=256m -XX:+HeapDumpOnOutOfMemoryError"
set CMIS_CONFIG=-Dexo.data.dir="%CATALINA_HOME%/ext-exo-data" -Dorg.exoplatform.container.standalone.config="%CATALINA_HOME%/ext-exo-conf/exo-configuration-hsql.xml"

set CATALINA_OPTS=%CATALINA_OPTS% %SECURITY_OPTS% %CMIS_CONFIG% %MEMORY_OPTS% -Dfile.encoding=UTF-8
```

## Security ##

### Context file ###

The context file is in the xcmis.wat at /xcmis/META-INF/context.xml
which is deployed at /conf/Catalina/localhost/xcmis.xml
with the "exo-domain" security domain name.

```

<Context path="/xcmis" docBase="xcmis" reloadable="true">
 <Logger className='org.apache.catalina.logger.SystemOutLogger'
           prefix='localhost_portal_log.' suffix='.txt' timestamp='true'/>
   <Manager className='org.apache.catalina.session.PersistentManager' saveOnRestart='false'/>
   <Realm className='org.apache.catalina.realm.JAASRealm'
          appName='exo-domain'
          userClassNames="org.exoplatform.services.security.jaas.UserPrincipal"
          roleClassNames="org.exoplatform.services.security.jaas.RolePrincipal"
          debug='0' cache='false'/>
</Context>

```

### JAAS file ###

The jaas.conf file is placed at /conf/jaas.conf

```
exo-domain {
  org.exoplatform.services.security.j2ee.TomcatLoginModule required;
};
```

configured that path in the xcmis-hsql.sh file

```
SECURITY_OPTS="-Djava.security.auth.login.config=../conf/jaas.conf"
```


### Web.xml security configuration ###

```
   <filter>
      <filter-name>SetCurrentIdentityFilter</filter-name>
      <filter-class>org.exoplatform.services.security.web.SetCurrentIdentityFilter</filter-class>
   </filter>

   <filter-mapping>
      <filter-name>SetCurrentIdentityFilter</filter-name>
      <url-pattern>/*</url-pattern>
   </filter-mapping>

   <listener>
      <listener-class>org.exoplatform.services.security.web.ConversationStateListener</listener-class>
   </listener>

...

   <security-constraint>
      <web-resource-collection>
         <web-resource-name>xCMIS Application</web-resource-name>
         <url-pattern>/cmisws/*</url-pattern>
         <url-pattern>/rest/*</url-pattern>
      </web-resource-collection>
      <auth-constraint>
         <role-name>administrators</role-name>
         <role-name>users</role-name>
      </auth-constraint>
   </security-constraint>

   <login-config>
      <auth-method>BASIC</auth-method>
      <realm-name>exo-domain</realm-name>
   </login-config>

   <security-role>
      <role-name>users</role-name>
   </security-role>
   <security-role>
      <role-name>administrators</role-name>
   </security-role>
```

### eXo security configuration in the exo-configuration-hsql.xml ###

The security components are
```
   <component>
      <key>org.exoplatform.services.security.Authenticator</key>
      <type>org.exoplatform.services.organization.auth.OrganizationAuthenticatorImpl</type>
   </component>

   <component>
      <type>org.exoplatform.services.security.IdentityRegistry</type>
   </component>

   <component>
      <key>org.exoplatform.services.security.RolesExtractor</key>
      <type>org.exoplatform.services.security.impl.DefaultRolesExtractorImpl</type>
      <init-params>
         <value-param>
            <name>user.role.parent.group</name>
            <description>authentication service use this value to authenticate</description>
            <value>platform</value>
         </value-param>
      </init-params>
   </component>
```

## Logging ##

Configured in the xcmis-hsql.sh file
```
LOG_OPTS="-Dorg.apache.commons.logging.Log=org.apache.commons.logging.impl.SimpleLog"
```




## eXo configuration ##

All configuration are placed in the /ext-exo-conf folder by default.

### exo-configuration-hsql.xml ###

For the container (standalone) the configuration in the
exo-configuration-hsql.xml file.

**NOTE:** The CMIS storage provider is configured with the `org.exoplatform.ecms.xcmis.sp.`**`DriveCmisRegistry`** so no need in configuring the `org.exoplatform.ecms.xcmis.sp.`**`StorageProviderImpl`** and `org.xcmis.spi.`**`PermissionService`**.

with WCM components:
```

<!-- BEGIN WCM components -->

   <component>
      <type>org.exoplatform.ecms.xcmis.sp.DriveCmisRegistry</type>
      <init-params>
         <value-param>
            <name>indexDir</name>
            <value>${exo.data.dir}/index/</value>
         </value-param>
         <value-param>
            <name>repository</name>
            <value>db1</value>
         </value-param>
      </init-params>
   </component>

   <component>
      <key>org.exoplatform.services.cms.drives.ManageDriveService</key>
      <type>org.exoplatform.services.cms.drives.impl.ManageDriveServiceImpl</type>
   </component>

   <component>
      <key>org.exoplatform.services.jcr.ext.hierarchy.NodeHierarchyCreator</key>
      <type>org.exoplatform.services.jcr.ext.hierarchy.impl.NodeHierarchyCreatorImpl</type>
   </component>
   <component>
      <type>org.exoplatform.services.cms.impl.DMSConfiguration</type>
   </component>

   <component>
      <key>org.exoplatform.services.cache.CacheService</key>
      <jmx-name>cache:type=CacheService</jmx-name>
      <type>org.exoplatform.services.cache.impl.CacheServiceImpl</type>
      <init-params>
         <object-param>
            <name>cache.config.default</name>
            <description>The default cache configuration</description>
            <object type="org.exoplatform.services.cache.ExoCacheConfig">
               <field name="name">
                  <string>default</string>
               </field>
               <field name="maxSize">
                  <int>300</int>
               </field>
               <field name="liveTime">
                  <long>300</long>
               </field>
               <field name="distributed">
                  <boolean>false</boolean>
               </field>
               <field name="implementation">
                  <string>org.exoplatform.services.cache.concurrent.ConcurrentFIFOExoCache</string>
               </field>
            </object>
         </object-param>
      </init-params>
   </component>

  <component>
    <key>org.exoplatform.services.cms.drives.ManageDriveService</key>
    <type>org.exoplatform.services.cms.drives.impl.ManageDriveServiceImpl</type>
  </component>

   <component>
      <key>org.exoplatform.services.idgenerator.IDGeneratorService</key>
      <type>org.exoplatform.services.idgenerator.impl.IDGeneratorServiceImpl</type>
   </component>


   <external-component-plugins>
      <target-component>org.exoplatform.services.cms.drives.ManageDriveService</target-component>
      <component-plugin>
         <name>manage.drive.plugin</name>
         <set-method>setManageDrivePlugin</set-method>
         <type>org.exoplatform.services.cms.drives.impl.ManageDrivePlugin</type>
         <description>Nothing</description>
         <init-params>
            <object-param>
               <name>driveA</name>
               <description>Drive A</description>
               <object type="org.exoplatform.services.cms.drives.DriveData">
                  <field name="name">
                     <string>driveA</string>
                  </field>
                  <field name="repository">
                     <string>db1</string>
                  </field>
                  <field name="workspace">
                     <string>cmis1</string>
                  </field>
                  <field name="permissions">
                     <string>*</string>
                  </field>
                  <field name="homePath">
                     <string>/exo:drives/driveA</string>
                  </field>
                  <field name="icon">
                     <string></string>
                  </field>
                  <field name="views">
                     <string>wcm-category-view</string>
                  </field>
                  <field name="viewPreferences">
                     <boolean>false</boolean>
                  </field>
                  <field name="viewNonDocument">
                     <boolean>true</boolean>
                  </field>
                  <field name="viewSideBar">
                     <boolean>true</boolean>
                  </field>
                  <field name="showHiddenNode">
                     <boolean>false</boolean>
                  </field>
                  <field name="allowCreateFolders">
                     <string>nt:folder,nt:unstructured</string>
                  </field>
                  <field name="allowNodeTypesOnTree">
                     <string>*</string>
                  </field>
               </object>
            </object-param>
         </init-params>
      </component-plugin>
   </external-component-plugins>


   <external-component-plugins>
      <target-component>org.exoplatform.services.cms.drives.ManageDriveService</target-component>
      <component-plugin>
         <name>manage.drive.plugin</name>
         <set-method>setManageDrivePlugin</set-method>
         <type>org.exoplatform.services.cms.drives.impl.ManageDrivePlugin</type>
         <description>Nothing</description>
         <init-params>
            <object-param>
               <name>driveB</name>
               <description>Drive B</description>
               <object type="org.exoplatform.services.cms.drives.DriveData">
                  <field name="name">
                     <string>driveB</string>
                  </field>
                  <field name="repository">
                     <string>db1</string>
                  </field>
                  <field name="workspace">
                     <string>cmis1</string>
                  </field>
                  <field name="permissions">
                     <string>*</string>
                  </field>
                  <field name="homePath">
                     <string>/exo:drives/driveB</string>
                  </field>
                  <field name="icon">
                     <string></string>
                  </field>
                  <field name="views">
                     <string>wcm-category-view</string>
                  </field>
                  <field name="viewPreferences">
                     <boolean>false</boolean>
                  </field>
                  <field name="viewNonDocument">
                     <boolean>true</boolean>
                  </field>
                  <field name="viewSideBar">
                     <boolean>true</boolean>
                  </field>
                  <field name="showHiddenNode">
                     <boolean>false</boolean>
                  </field>
                  <field name="allowCreateFolders">
                     <string>nt:folder,nt:unstructured</string>
                  </field>
                  <field name="allowNodeTypesOnTree">
                     <string>*</string>
                  </field>
               </object>
            </object-param>
         </init-params>
      </component-plugin>
   </external-component-plugins>


   <external-component-plugins>
      <target-component>org.exoplatform.services.cms.impl.DMSConfiguration</target-component>
      <component-plugin>
         <name>dmsconfiguration.plugin</name>
         <set-method>addPlugin</set-method>
         <type>org.exoplatform.services.cms.impl.DMSRepositoryConfiguration</type>
         <description>DMS Repository configuration</description>
         <init-params>
            <value-param>
               <name>repository</name>
               <value>db1</value>
            </value-param>
            <value-param>
               <name>systemWorkspace</name>
               <value>cmis1</value>
            </value-param>
         </init-params>
      </component-plugin>
   </external-component-plugins>


   <external-component-plugins>
      <target-component>org.exoplatform.services.jcr.ext.hierarchy.NodeHierarchyCreator</target-component>
      <component-plugin>
         <name>addPaths</name>
         <set-method>addPlugin</set-method>
         <type>org.exoplatform.services.jcr.ext.hierarchy.impl.AddPathPlugin</type>
         <init-params>
            <object-param>
               <name>sites.content.storage.configuration</name>
               <description>config for storage to store running sites content</description>
               <object type="org.exoplatform.services.jcr.ext.hierarchy.impl.HierarchyConfig">
                  <field name="repository">
                     <string>db1</string>
                  </field>
                  <field name="workspaces">
                     <collection type="java.util.ArrayList">
                        <value>
                           <string>cmis1</string>
                        </value>
                        <value>
                           <string>system</string>
                        </value>
                     </collection>
                  </field>
                  <field name="jcrPaths">
                     <collection type="java.util.ArrayList">
                        <value>
                           <object type="org.exoplatform.services.jcr.ext.hierarchy.impl.HierarchyConfig$JcrPath">
                              <field name="alias">
                                 <string>exoDrivesPath</string>
                              </field>
                              <field name="path">
                                 <string>/exo:drives</string>
                              </field>
                           </object>
                        </value>
                     </collection>
                  </field>
               </object>
            </object-param>
         </init-params>
      </component-plugin>
   </external-component-plugins>


<!-- END WCM components -->




```

and separated file configured the WCM registered nodetypes,

with Organization service components:

```


<!-- BEGIN Organization service components -->

  <component>
    <key>org.exoplatform.services.organization.OrganizationService</key>
    <type>org.exoplatform.services.jcr.ext.organization.JCROrganizationServiceImpl</type>
    <init-params>
      <value-param>
        <name>storage-workspace</name>
        <description>Workspace in default repository where organization storage will be created</description>
        <value>system</value>
      </value-param>
    </init-params>
  </component>

  <component>
    <type>org.exoplatform.services.jcr.ext.app.ThreadLocalSessionProviderService</type>
  </component>

  <component>
    <type>org.exoplatform.services.jcr.ext.resource.NodeRepresentationService</type>
  </component>

  <component>
    <type>org.exoplatform.services.jcr.ext.resource.representation.NtFileNodeRepresentationFactory</type>
  </component>

  <component>
    <type>org.exoplatform.services.jcr.ext.resource.representation.NtResourceNodeRepresentationFactory</type>
  </component>

   <component>
      <key>org.exoplatform.services.security.RolesExtractor</key>
      <type>org.exoplatform.services.security.impl.DefaultRolesExtractorImpl</type>
      <init-params>
         <value-param>
            <name>user.role.parent.group</name>
            <description>authentication service use this value to authenticate</description>
            <value>platform</value>
         </value-param>
      </init-params>
   </component>

   <external-component-plugins>
      <target-component>org.exoplatform.services.organization.OrganizationService</target-component>
      <component-plugin>
         <name>init.service.listener</name>
         <set-method>addListenerPlugin</set-method>
         <type>org.exoplatform.services.organization.OrganizationDatabaseInitializer</type>
         <description>this listener populate organization data for the first launch</description>
         <init-params>
            <value-param>
               <name>checkDatabaseAlgorithm</name>
               <description>check database</description>
               <value>entry</value>
            </value-param>
            <value-param>
               <name>printInformation</name>
               <description>Print information init database</description>
               <value>false</value>
            </value-param>
            <object-param>
               <name>configuration</name>
               <description>description</description>
               <object type="org.exoplatform.services.organization.OrganizationConfig">
                  <field name="membershipType">
                     <collection type="java.util.ArrayList">
                        <value>
                           <object type="org.exoplatform.services.organization.OrganizationConfig$MembershipType">
                              <field name="type">
                                 <string>member</string>
                              </field>
                              <field name="description">
                                 <string>member membership type</string>
                              </field>
                           </object>
                        </value>
                     </collection>
                  </field>

                  <field name="group">
                     <collection type="java.util.ArrayList">
                        <value>
                           <object type="org.exoplatform.services.organization.OrganizationConfig$Group">
                              <field name="name">
                                 <string>platform</string>
                              </field>
                              <field name="parentId">
                                 <string></string>
                              </field>
                              <field name="description">
                                 <string>the /platform group</string>
                              </field>
                              <field name="label">
                                 <string>IDE</string>
                              </field>
                           </object>
                        </value>
                        <value>
                           <object type="org.exoplatform.services.organization.OrganizationConfig$Group">
                              <field name="name">
                                 <string>administrators</string>
                              </field>
                              <field name="parentId">
                                 <string>/platform</string>
                              </field>
                              <field name="description">
                                 <string>the /platform/administrators group</string>
                              </field>
                              <field name="label">
                                 <string>Administrators</string>
                              </field>
                           </object>
                        </value>
                        <value>
                           <object type="org.exoplatform.services.organization.OrganizationConfig$Group">
                              <field name="name">
                                 <string>users</string>
                              </field>
                              <field name="parentId">
                                 <string>/platform</string>
                              </field>
                              <field name="description">
                                 <string>the /platform/users group</string>
                              </field>
                              <field name="label">
                                 <string>Users</string>
                              </field>
                           </object>
                        </value>
                        <value>
                           <object type="org.exoplatform.services.organization.OrganizationConfig$Group">
                              <field name="name">
                                 <string>developers</string>
                              </field>
                              <field name="parentId">
                                 <string>/platform</string>
                              </field>
                              <field name="description">
                                 <string>the /platform/developers group</string>
                              </field>
                              <field name="label">
                                 <string>Developers</string>
                              </field>
                           </object>
                        </value>
                     </collection>
                  </field>

                  <field name="user">
                     <collection type="java.util.ArrayList">
                        <value>
                           <object type="org.exoplatform.services.organization.OrganizationConfig$User">
                              <field name="userName">
                                 <string>root</string>
                              </field>
                              <field name="password">
                                 <string>exo</string>
                              </field>
                              <field name="firstName">
                                 <string>Root</string>
                              </field>
                              <field name="lastName">
                                 <string>Root</string>
                              </field>
                              <field name="email">
                                 <string>root@localhost</string>
                              </field>
                              <field name="groups">
                                 <string>member:/platform/administrators,member:/platform/developers,member:/platform/users</string>
                              </field>
                           </object>
                        </value>
                     </collection>
                  </field>
               </object>
            </object-param>
         </init-params>
      </component-plugin>
   </external-component-plugins>

<!-- END Organization service components -->

```

and organization registered nodetypes.

### JCR configuration ###

The JCR configuration is at
```
cmis-jcr-configuration-hsql.xml
```
with repository name "db1" and two workspaces "system" (system, default) and "cmis1".


```
<repository-service default-repository="db1">
  <repositories>
    <repository name="db1" system-workspace="system" default-workspace="system">
      <security-domain>exo-domain</security-domain>
      <access-control>optional</access-control>
      <session-max-age>5m</session-max-age>
      <authentication-policy>org.exoplatform.services.jcr.impl.core.access.JAASAuthenticator</authentication-policy>
      <workspaces>
        <workspace name="system">
          <!-- for system storage -->
          <container class="org.exoplatform.services.jcr.impl.storage.jdbc.JDBCWorkspaceDataContainer">
            <properties>
              <property name="source-name" value="jdbcxcmis" />
              <property name="dialect" value="hsqldb" />
              <property name="multi-db" value="false" />
              <property name="max-buffer-size" value="200k" />
              <property name="swap-directory" value="${exo.data.dir}/swap/system" />
            </properties>
          </container>
          <initializer class="org.exoplatform.services.jcr.impl.core.ScratchWorkspaceInitializer">
            <properties>
              <property name="root-nodetype" value="nt:unstructured" />
            </properties>
          </initializer>
          <cache enabled="true" class="org.exoplatform.services.jcr.impl.dataflow.persistent.LinkedWorkspaceStorageCacheImpl">
            <properties>
              <property name="max-size" value="10k" />
              <property name="live-time" value="1h" />
            </properties>
          </cache>
          <lock-manager>
            <time-out>15m</time-out>
            <persister class="org.exoplatform.services.jcr.impl.core.lock.FileSystemLockPersister">
              <properties>
                <property name="path" value="${exo.data.dir}/lock/system" />
              </properties>
            </persister>
          </lock-manager>
        </workspace>

        <workspace name="cmis1">
          <container class="org.exoplatform.services.jcr.impl.storage.jdbc.JDBCWorkspaceDataContainer">
            <properties>
              <property name="source-name" value="jdbcxcmis" />
              <property name="dialect" value="hsqldb" />
              <property name="multi-db" value="false" />
              <property name="max-buffer-size" value="200k" />
              <property name="swap-directory" value="${exo.data.dir}/swap/cmis1" />
            </properties>
          </container>
          <initializer class="org.exoplatform.services.jcr.impl.core.ScratchWorkspaceInitializer">
            <properties>
              <property name="root-nodetype" value="nt:unstructured" />
            </properties>
          </initializer>
          <cache enabled="true" class="org.exoplatform.services.jcr.impl.dataflow.persistent.LinkedWorkspaceStorageCacheImpl">
            <properties>
              <property name="max-size" value="10k" />
              <property name="live-time" value="1h" />
            </properties>
          </cache>
        </workspace>
      </workspaces>
    </repository>
  </repositories>
</repository-service>
```

The data source "jdbcxcmis" configured in the exo-configuration-hsql.xml (CMIS-512)

```
   <external-component-plugins>
      <target-component>org.exoplatform.services.naming.InitialContextInitializer</target-component>
      <component-plugin>
         <name>bind.datasource</name>
         <set-method>addPlugin</set-method>
         <type>org.exoplatform.services.naming.BindReferencePlugin</type>
         <init-params>
            <value-param>
               <name>bind-name</name>
               <value>jdbcxcmis</value>
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
              <property name="driverClassName" value="org.hsqldb.jdbcDriver"/>
              <property name="url" value="jdbc:hsqldb:file:${exo.data.dir}/data/jcr;hsqldb.lock_file=false"/>
              <property name="username" value="sa"/>
              <property name="password" value=""/>
            </properties-param>
         </init-params>
      </component-plugin>
   </external-component-plugins>
```


NOTE: Removed `<value-storages>` configuration. (CMIS-515)


### Nodetypes configuration ###

The nodetypes are at
```
cmis-nodetypes-config.xml
nodetypes-config-extended.xml
nodetypes-config.xml
organization-nodetypes.xml
```

## Web.xml configuration ##

```


<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE web-app PUBLIC "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
"http://java.sun.com/dtd/web-app_2_3.dtd">

<web-app>

   <display-name>xCMIS Application</display-name>

   <context-param>
      <param-name>javax.ws.rs.Application</param-name>
      <param-value>org.xcmis.restatom.CmisRestApplication</param-value>
   </context-param>

   <filter>
      <filter-name>SetCurrentIdentityFilter</filter-name>
      <filter-class>org.exoplatform.services.security.web.SetCurrentIdentityFilter</filter-class>
   </filter>

   <filter-mapping>
      <filter-name>SetCurrentIdentityFilter</filter-name>
      <url-pattern>/*</url-pattern>
   </filter-mapping>

   <listener>
      <listener-class>org.exoplatform.ws.frameworks.servlet.StandaloneContainerInitializedListener</listener-class>
   </listener>

   <listener>
      <listener-class>org.xcmis.restatom.AtomCmisBootstrapListener</listener-class>
   </listener>

   <listener>
      <listener-class>org.everrest.core.servlet.EverrestInitializedListener</listener-class>
   </listener>

   <servlet>
      <servlet-name>RestServer</servlet-name>
      <servlet-class>org.everrest.core.servlet.EverrestServlet</servlet-class>
   </servlet>

   <servlet>
      <servlet-name>SOAPServlet</servlet-name>
      <servlet-class>org.xcmis.wssoap.impl.server.CmisSoapServlet</servlet-class>
   </servlet>

   <servlet-mapping>
      <servlet-name>RestServer</servlet-name>
      <url-pattern>/rest/*</url-pattern>
   </servlet-mapping>

   <servlet-mapping>
      <servlet-name>SOAPServlet</servlet-name>
      <url-pattern>/cmisws/*</url-pattern>
   </servlet-mapping>

   <security-constraint>
      <web-resource-collection>
         <web-resource-name>xCMIS Application</web-resource-name>
         <url-pattern>/cmisws/*</url-pattern>
         <url-pattern>/rest/*</url-pattern>
      </web-resource-collection>
      <auth-constraint>
         <role-name>administrators</role-name>
         <role-name>users</role-name>
      </auth-constraint>
   </security-constraint>

   <login-config>
      <auth-method>BASIC</auth-method>
      <realm-name>exo-domain</realm-name>
   </login-config>

   <security-role>
      <role-name>users</role-name>
   </security-role>
   <security-role>
      <role-name>administrators</role-name>
   </security-role>

</web-app>


```
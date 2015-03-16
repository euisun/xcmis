_This document refers to 1.0 version of xCMIS._


If you want to use CMIS with your JCR workspace follow this documentation to change xCMIS bundle [Demo Tomcat](xCMISBuildDemo.md).

In the server bundles provided with xCMIS an application server (Tomcat) launches Standalone eXo Container with exploded configuration which you may need to modify. This configuration is placed in _/**xcmis-server-war**/src/main/webapp/WEB-INF/classes/conf/**exo-configuration.xml**_.
It is available in binary at _**xcmis.war**#/WEB-INF/classes/conf/**exo-configuration.xml**_.


# RepositoriesManager #

You need to change:

  * Repository (JCR),
  * Workspace (JCR),


Here is how eXo JCR's configuration looks like:
```
   <component>
      <type>org.xcmis.sp.jcr.exo.StorageProviderImpl</type>
      <init-params>
         <object-param>
            <name>configs</name>
            <object type="org.xcmis.sp.jcr.exo.StorageProviderImpl$StorageProviderConfig">
               <field name="storage">
                        <object type="org.xcmis.sp.jcr.exo.StorageConfiguration">
                           <field name="id">
                              <string>cmis1</string>
                           </field>
                           <field name="repository">
                              <string>repository</string>
                           </field>
                           <field name="workspace">
                              <string>cmis</string>
                           </field>
```


# See also #

  * HOW TO configure xCMIS server
    * The wiki page at [xCMISConfigure](xCMISConfigure.md)
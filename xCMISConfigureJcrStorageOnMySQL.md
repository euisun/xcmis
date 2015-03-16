_This document refers to 1.0 version of xCMIS._

DEPRECATEDf



# Introduction #

Here is the instruction how to configure JCR on **MySQL** storage.
By default, it is configured on Hibernate.

INFO: eXo JCR persistent data container uses the JDBC driver to communicate with the actual database software, i.e. any JDBC-enabled data storage can be used with eXo JCR implementation. Currently the data container is tested with the following RDBMS:
  * MySQL (5.x including UTF8 support)
  * PostgreSQL (8.x)
  * Oracle Database (9i, 10g)
  * Microsoft SQL Server (2005)
  * Sybase ASE (15.0)
  * Apache Derby/Java DB (10.1.x, 10.2.x)
  * IBM DB2 (8.x, 9.x)
  * HSQLDB (1.8.0.7)


# HOW TO steps: #

  1. Change workspace dialect.
  1. Change properties for BindReferencePlugin
  1. Add the mysql-connector-java-5.1.8.jar


## Change workspace dialect ##

There is JCR configuration file as described in eXo Docs site at [JCR database configuration](http://docs.exoplatform.com/PLF35/topic/org.exoplatform.doc.35/JCR.JDBCDataContainerConfig.Single-databaseConfiguration.ConfigurationWithoutDataSource.html)
It is configured the JCR workspace for the CMIS service.

The source of configuration placed at _/**xcmis-sp-jcr-exo**/src/main/resources/conf/portal/**jcr-cmis-config.xml**_.
It is available in xCMIS Server Tomcan bundle at _xcmis-tomcat/webapps/xcmis.war#/WEB-INF/lib/**xcmis-sp-jcr-exo-X.Y.jar**#/conf/portal/**jcr-cmis-config.xml**_.


To modify default configuration file based on **hsqldb** dialect with **mysql** dialect.

  * **hsqldb** dialect:
```
              <property name="dialect" value="hsqldb" />
```

  * **mysql** dialect:
```
              <property name="dialect" value="mysql" />
```


```
<repository-service default-repository="repository">
  <repositories>
    <repository name="repository" system-workspace="production" default-workspace="production">
      <security-domain>exo-domain</security-domain>
      <access-control>optional</access-control>
      <authentication-policy>org.exoplatform.services.jcr.impl.core.access.JAASAuthenticator</authentication-policy>

        <workspace name="production">
          <!-- for system storage -->
          <container class="org.exoplatform.services.jcr.impl.storage.jdbc.JDBCWorkspaceDataContainer">
            <properties>
              <property name="source-name" value="jdbcexo" />
              <property name="dialect" value="mysql" />
              <property name="multi-db" value="false" />
              <property name="update-storage" value="false" />
              <property name="max-buffer-size" value="200k" />
              <property name="swap-directory" value="../temp/swap/production" />
            </properties>
          ...
        </workspace>

        <workspace name="cmis">
          <container class="org.exoplatform.services.jcr.impl.storage.jdbc.JDBCWorkspaceDataContainer">
            <properties>
              <property name="source-name" value="jdbcexo" />
              <property name="dialect" value="mysql" />
              <property name="multi-db" value="false" />
              <property name="update-storage" value="false" />
              <property name="max-buffer-size" value="200k" />
              <property name="swap-directory" value="../temp/swap/cmis" />
            </properties>
            <value-storages>
          ...
        </workspace>
```


## Change properties for BindReferencePlugin ##

Look at [DB and data source configuration](http://docs.exoplatform.com/PLF35/topic/org.exoplatform.doc.35/JCR.JDBCDataContainerConfig.Single-databaseConfiguration.html)

The source of configuration placed at _/**xcmis-server-war**/src/main/webapp/WEB-INF/classes/conf/**exo-configuration.xml**_.
It is available in binary at _**xcmis.war**#/WEB-INF/classes/conf/**exo-configuration.xml**_.

You should change the old **hsqldb** dialect configuration within InitialContextInitializer:
```
   <external-component-plugins>
      <target-component>org.exoplatform.services.naming.InitialContextInitializer</target-component>
      <component-plugin>
         <name>bind.datasource</name>
         <set-method>addPlugin</set-method>
         <type>org.exoplatform.services.naming.BindReferencePlugin</type>
         <init-params>
            ...
            <properties-param>
               <name>ref-addresses</name>
               <description>ref-addresses</description>
               <property name="driverClassName" value="org.hsqldb.jdbcDriver" />
               <property name="url" value="jdbc:hsqldb:file:../temp/data/exodb" />
               <property name="username" value="sa" />
               <property name="password" value="" />
            </properties-param>
```

with the new one **mysql** configuration InitialContextInitializer:
```
   <external-component-plugins>
      <target-component>org.exoplatform.services.naming.InitialContextInitializer</target-component>
      <component-plugin>
         <name>bind.datasource</name>
         <set-method>addPlugin</set-method>
         <type>org.exoplatform.services.naming.BindReferencePlugin</type>
         <init-params>
            ...
            <properties-param>
              <name>ref-addresses</name>
              <description>ref-addresses</description>
              <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
              <property name="url" value="jdbc:mysql://localhost:3306/exodb?relaxAutoCommit=true&amp;autoReconnect=true&amp;useUnicode=true&amp;characterEncoding=utf8"/>
              <property name="username" value="root"/>
              <property name="password" value="exo"/>
              <property name="maxActive" value="50" />
              <property name="maxIdle" value="5" />
              <property name="initialSize" value="50" />
            </properties-param>
```

Where the properties are:
  * localhost:3306/exodb is your MySQL's HOST:PORT/DATABASE names.
  * root is username, change it up to you
  * exo is password, change it up to you


## Add the mysql required library ##
Add the mysql-connector-java-5.1.8.jar to the _xcmis-tomcat/webapps/xcmis.war#/WEB-INF/lib/_.
You can download it at the URL [mysql-connector-java 5.1.8](http://repository.exoplatform.org/content/groups/all/mysql/mysql-connector-java/5.1.8/)


# See also #

xCMIS Sources

  * [jcr-cmis-config.xml](http://code.google.com/p/xcmis/source/browse/tags/1.0-Beta01/xcmis-sp-jcr-exo/src/main/resources/conf/portal/jcr-cmis-config.xml)
  * [xcmis-server-war exo-configuration.xml](http://code.google.com/p/xcmis/source/browse/tags/1.0-Beta01/xcmis-server-war/src/main/webapp/WEB-INF/classes/conf/exo-configuration.xml)
  * [xcmis-server-tomcat-demo exo-configuration.xml](http://code.google.com/p/xcmis/source/browse/tags/1.0-Beta01/xcmis-server-tomcat-demo/src/main/webapp/WEB-INF/classes/conf/exo-configuration.xml)
  * [mysql-connector-java 5.1.8](http://repository.exoplatform.org/content/groups/all/mysql/mysql-connector-java/5.1.8/)
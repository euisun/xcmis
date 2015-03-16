_This document refers to 1.1.x and 1.2.x version of xCMIS._



# Build without tests #

By default, project build runs with JUnit tests. To decrease the build time of the project you can exclude tests run. Use the maven property:

```
mvn clean install -Dmaven.test.skip
```


# Build xCMIS server WAR with only restatom or wssoap binding #

In the sub-project "**xcmis-server-war**" there are three predefined profiles:
  * **all** (default) - to build xCMIS web application **xcmis.war** with Atom REST and WS SOAP services.
```
mvn clean install
```
  * **restatom** - to build xCMIS web application **xcmis.war** with Atom REST services ONLY.
```
mvn clean install -Prestatom
```
  * **wssoap** - to build xCMIS web application **xcmis.war** with WS SOAP services ONLY.
```
mvn clean install -Pwssoap
```

# Tomcat bundle assemble #
Read more:
  * [Assembly xCMIS with Tomcat server application](xCMISBuild#Assembly_xCMIS_with_Tomcat_server_application.md)



# Generate Javadoc #

> In the root project folder run command:

```
mvn javadoc:javadoc
```

> Then you can get the documentation at: _/target/javadoc/apidocs/index.html_


# Generate documentation #

In the sub-project "**xcmis-docs**" run the command

```
mvn clean install -Dmaven.test.skip
```

# xcmis-tests module #

The suite is for testing on running Tomcat.

To run 36 tests:
mvn clean install -Ptesting -DcmisUsername=root -DcmisPassword=exo -DcmisRepositoryId=cmis1

To run stress test:
mvn clean install -Ptesting -DcmisUsername=root -DcmisPassword=exo -DcmisRepositoryId=cmis1 -Dtest=Stress -DfilesNum=1000
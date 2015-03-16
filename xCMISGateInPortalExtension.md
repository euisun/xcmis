DEPRECATED

_This document refers to 1.0 version of xCMIS._



# Introduction #

This article describes steps how to integrate xCMIS into GateIn.
NOTE:
This article implies using GateIn version 3.0.0 (GA or FINAL).


# Installing and setup #

To chose how would you install xCMIS to GateIn:

  * get bundled archive extension
or
  * setup manually with step by step instruction


### Installing and setup with bundled archive extension for GateIn ###

**Requirements**

  * JDK 1.6 (required by GateIn),

**Configuring**

Download and unpack GateIn 3.0 FINAL Tomcat bundle from http://www.jboss.org/gatein/downloads.html

Download the [xcmis-server-gateinext-1.0.zip](http://xcmis.googlecode.com/files/xcmis-server-gateinext-1.0.zip) archive.

Unpack this archive into the ROOT of the GateIn Tomcat (GateIn-3.0.0-GA)

Remove old version of pdfbox library from GateIn /lib folder to avoid conflicts:
  * pdfbox-0.7.3.jar


### Installing and setup manually with step by step instruction ###

**Requirements**

  * JDK 1.6 (required by GateIn),
  * Maven 2.2.0 (or upper),
  * console SVN client.


**Configuring**

1. Build and Setup

Get xCMIS code into folder _xcmis_ by running command:
```
svn co http://xcmis.googlecode.com/svn/tags/1.0/ xcmis
```
> at the parent folder level.

Run command:
```
mvn clean install
```
> within _xcmis_ folder.

Download and unpack GateIn 3.0 FINAL Tomcat bundle from http://www.jboss.org/gatein/downloads.html


2. Add GateIn extension feature

2.1. Add Starter application

If you have got it already make sure, that it is renamed as to be the last war file loaded. Don't hesitate to rename it, if "xcmis.war" application file is loaded
following to the alphabetic order.

Download the http://repository.jboss.com/maven2/org/exoplatform/portal/exo.portal.starter.war/3.0.0-GA/exo.portal.starter.war-3.0.0-GA.war application
and unzip it as "zxstarter.war" named folder into the /webapps server folder.

Command lines on UNIX like systems:
```
wget http://repository.jboss.com/maven2/org/exoplatform/portal/exo.portal.starter.war/3.0.0-GA/exo.portal.starter.war-3.0.0-GA.war
unzip exo.portal.starter.war-3.0.0-GA.war -d zxstarter.war
rm exo.portal.starter.war-3.0.0-GA.war
```


2.2. Add extension libraries

Add those two libraries into /lib server folder:

  * http://repository.jboss.com/maven2/org/exoplatform/portal/exo.portal.sample.extension.config/3.0.0-GA/exo.portal.sample.extension.config-3.0.0-GA.jar
  * http://repository.jboss.com/maven2/org/exoplatform/portal/exo.portal.sample.extension.jar/3.0.0-GA/exo.portal.sample.extension.jar-3.0.0-GA.jar

3. Add xcmis.war

Copy the xcmis.war file from the _xcmis/**xcmis-server-gateinext-war**/target/**xcmis.war**_ into the _/webapps_ folder of GateIn Tomcat.

4. To add or remove the jars

4.1. Copy all necessary xCMIS .jar files from the _xcmis/**xcmis-server-war**/target/xcmis/WEB-INF/**lib**_ into the _/lib_ folder of GateIn Tomcat.
> Those .jar's are:
    * xcmis-renditions-1.0.jar;
    * xcmis-restatom-1.0.jar;
    * xcmis-search-model-1.0.jar
    * xcmis-search-parser-cmis-1.0.jar
    * xcmis-search-service-1.0.jar;
    * xcmis-sp-jcr-exo-1.0.jar;
    * xcmis-spi-1.0.jar;

4.2. Copy necessary required third party .jar files from the same location into the GateIn _/lib_ folder:
> Those .jar's are:
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
    * lucene-regex-2.4.1.jar
    * pdfbox-1.1.0.jar

4.3.  Remove old version of pdfbox library from GateIn _/lib_ folder to avoid conflicts:
  * pdfbox-0.7.3.jar



# Run GateIn #

> Run the GateIn with the following command within _/bin_ folder:
  * On the Windows platform
> > Open a DOS prompt command and type the command:
```
gatein.bat run
```

  * On Unix/linux/cygwin/MacOSX
> > Type the command:
```
./gatein.sh run
```

# Access xCMIS server #


> AtomPub xCMIS service will be available at the URL:
> http://localhost:8080/rest/private/cmisatom

> Use default user name: _**root**_ and password: _**gtn**_ for login.
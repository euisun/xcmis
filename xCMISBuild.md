_This document refers to 1.1.x and 1.2.x version of xCMIS._


# Assembly xCMIS application #


## Assembly xCMIS server WAR application ##

> The result of that you'll have the xCMIS web application archive "**xcmis.war**" placed at "_xcmis-server-war/target_".

  1. Prerequisites:
    * Maven version 2.2.0 (or higher).
  1. Run
```
svn checkout http://xcmis.googlecode.com/svn/tags/1.2.1 xcmis-read-only
cd xcmis-read-only
mvn clean install
```
> command within root project folder.

See how to build xCMIS server WAR with other predefined profiles
[xCMISBuildTricks#Build\_xCMIS\_server\_WAR\_with\_other\_predefined\_profiles](xCMISBuildTricks#Build_xCMIS_server_WAR_with_other_predefined_profiles.md)


## Assembly xCMIS with Tomcat server application ##

> The result of that you'll have the Tomcat with xCMIS web application archive bundle "**xcmis-tomcat**", placed at "_xcmis-server-tomcat/target_".

  1. Prerequisites:
    * Apache Tomcat 6.0.32 from [official binary distributions](http://tomcat.apache.org/download-60.cgi#6.0.32)
    * Maven version 2.2.0 (or higher).
    * **xcmis.war** has to be built as described in step before in "_Assembly xCMIS server WAR application_".
  1. Build
    * Go to "_xcmis-server-tomcat_"
    * Run
```
mvn clean install -Passembly -Dtomcat.distrib=/PATH/TO/TOMCAT/DISTRIBUTIVE
```
> > command within  folder.


# Run Tomcat #
  * Go to _xcmis-server-tomcat/target/xcmis-tomcat/bin_
  * On the Windows platform
> > Open a DOS prompt command and type the command
```
xcmis.bat run
```

  * On Unix/linux/cygwin/MacOSX
> > Type the command:
```
./xcmis.sh run
```
> > You may need to change the permission of all `*`.sh files in the tomcat/bin dir by using: `chmod +x *.sh`

# Access xCMIS server #
  * Available services URLs:
    * Home page at the URL http://localhost:8080/xcmis
    * REST Atom services will be available at the URL http://localhost:8080/xcmis/rest/cmisatom. WADL can be found at the URL http://localhost:8080/xcmis/wadl.html
    * SOAP services should be available at the URL http://localhost:8080/xcmis/cmisws

  * Default service credential: username is **root** and password is **exo**.

# See also #

  * HOW TO build and run xCMIS server with demo application
    * The wiki page at [xCMISBuildDemo](xCMISBuildDemo.md)
  * HOW TO configure xCMIS server
    * The wiki page at [xCMISConfigure](xCMISConfigure.md)
  * HOW TO skip tests, chose the CMIS tranpost or generate JavaDocs
    * The wiki page at [xCMISBuildTricks](xCMISBuildTricks.md)
TODO
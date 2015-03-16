_This document refers to 1.1.x and 1.2.x version of xCMIS._


# Introduction #

The result of that you'll have the Tomcat bundle with xCMIS Demo application.

# Build xCMIS Demo application and assembly it with Tomcat server application #

  * Prerequisites:
    * Apache Tomcat 6.0.32 from [official binary distributions](http://tomcat.apache.org/download-60.cgi#6.0.32)
    * Maven version 2.2.0 (or higher).
  * Get source code, see http://code.google.com/p/xcmis/source/checkout.
command within root project folder.
  * Go to the folder that contains sources and run command
```
mvn clean install
```
command within root project folder.
  * Build
    * Go to "_xcmis-server-tomcat-demo_"
    * Run
```
mvn clean install -Passembly -Dtomcat.distrib=/PATH/TO/TOMCAT/DISTRIBUTIVE
```
> > command within  folder.


> The result of that you'll have the Tomcat bundled with xCMIS Demo web application archive "**xcmis-tomcat**", placed at "_xcmis-server-tomcat-demo/target_".


# Run Tomcat #
  * Go to _xcmis-server-tomcat-demo/target/xcmis-tomcat/bin_
  * On the Windows platform
> > Open a DOS prompt command and type the command
```
xcmis.bat run
```

  * On Unix/linux/cygwin/MacOSX
> > Open a terminal and type the command:
```
./xcmis.sh run
```
> > You may need to change the permission of all `*`.sh files in the tomcat/bin dir by using: `chmod +x *.sh`

# Access xCMIS server #
  * Available services URLs:
    * Home page at the URL http://localhost:8080/xcmis
    * Demo CMIS Client http://localhost:8080/xcmis/xcmis-demo-gadget/GadgetWrapper.html
    * REST Atom services will be available at the URL http://localhost:8080/xcmis/rest/cmisatom. WADL can be found at the URL http://localhost:8080/xcmis/wadl.html
    * SOAP services should be available at the URL http://localhost:8080/xcmis/cmisws

  * Default service credential: username is **root** and password is **exo**.

# See also #

  * HOW TO configure xCMIS server
    * The wiki page at [xCMISConfigure](xCMISConfigure.md)
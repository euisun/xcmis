_This document refers to 1.1.x and 1.2.x version of xCMIS._


# Introduction #

xCMIS server allows to use multiply clients with it. This article explains how to configure & successfully launch some of them (CMIS Spaces Flex+AIR, IBM CMIS Firefox Connector) on xCMIS.


# Launching "CMIS Spaces Flex+AIR" client with xCMIS #

"CMIS Spaces" is an open source clients for Flex+AIR and Flex+Browser, based on FlexSpaces framework.
Those clients are well compatible with xCMIS server, and allows to use both atom rest and soap bindings.

  * Installing & launching CMIS Spaces from project page at: http://code.google.com/p/cmisspaces/

  * See CMIS Spaces wiki at http://code.google.com/p/cmisspaces/w/list

Installing client is quite simple - [download](http://code.google.com/p/cmisspaces/downloads/list) the archive and extract it into webapps folder of your server for browser version or run set-up application for AIR version. Then, it is necessary to make some configuration changes (examples are good for current build 17 of client):

Open the **CMISSpacesConfig.xml**, it is located in folder where CMISSpaces was installed:

  * Set the **cmisUrl** property to correct location, e.g: http://localhost:8080/xcmis/rest/cmisatom

  * Set the **useProxy** property depending of client version: "_false_" for AIR (flex) version and "_true_" for non-AIR browser version

Start the server & launch application - using CMISSpacesAir.exe for AIR or CMISSpaces.html for browser client version - after login process you must see your repository contents;

http://downloads.exoplatform.org/cmis-air2.PNG


# Launching "IBM CMIS Firefox Connector" client with xCMIS #

Installing IBM CMIS Firefox Connector at: http://www.ibm.com/developerworks/lotus/library/quickr-cmis/index.html#N101CC

> Currently IBM CMIS Firefox Connector is available ONLY in source codes, packaged as Eclipse project. So, we need to [download plugin sources](http://www.ibm.com/developerworks/lotus/library/quickr-cmis/index.html#download), and build it with **Ant** ([Apache Ant](http://ant.apache.org/)). The result will be _**cmisconnector@ibm.com.xpi**_ file, which can be imported into Firefox as a plugin. After plugin is successfully installed, restart Firefox and run plugin via main menu (Tools->CMIS Connector) or Ctrl-Alt-M.  You can add multiply repositories to browser using "Add repository" button on plugin-s panel.

http://downloads.exoplatform.org/cmis-ff-plugin.PNG
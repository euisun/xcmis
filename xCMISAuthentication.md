_This document refers to 1.1.x version of xCMIS._
# Introduction #

Currently, xCMIS uses authentication mechanism provided by the eXo organization service. This article shortly describes how to adopt this mechanism to necessary conditions or write own authenticator .


# Details #

eXo organization service includes the following components:

> - ConversationState object which stores all information about the state of the current user;

> - Identity object, which is a set of principals to identify a user, stored in ConversationState;

> - An Authenticator is responsible for Identity creating and validating User;

In case of need to write own authenticator, is good thing to  write it as a container component. Basically, authenticator must contain two methods:

  * _validateUser()_ accepts an array of credentials and returns the userId (which can be something different from the username). Method must do the comparison of incoming credentials (username/password, digest etc) with those credentials that are stored in an implementation specific database. By default, eXo organization service uses _DummyOrganizationService.authenticate()_ method for this operation.

  * _createIdentity()_ accepts the userId and returns a newly created Identity object; Identity can contain additionally groups membership info, or some other user information.

Example configuration might look like:
```
  <component>
    <key>org.exoplatform.services.security.Authenticator</key> 
    <type>org.exoplatform.services.organization.auth.OrganizationAuthenticatorImpl</type>
  </component>
```

This example of configuration is located in exo.core.component.organization.api-`*`.jar at "/conf/portal/configuration.xml".

As we can see, this is quite simple.

It is up to the application developer (and deployer) whether to use the Authenticator component(s) and how many implementations of this components can be deployed in container. The developer is free to create an Identity object using a different way, but the Authenticator component is the highly recommended way from architectural considerations.

For more detailed explanation & examples, please refer to http://docs.exoplatform.com/PLF35/topic/org.exoplatform.doc.35/sect-Reference_Guide-APIs-Organization_API.html docs page.

For more security configuration look at [xCMISwithWCMconfiguration#Security](xCMISwithWCMconfiguration#Security.md)
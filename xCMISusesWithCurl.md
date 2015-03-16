_This document refers to 1.1.x and 1.2.x version of xCMIS._



# Introduction #

Here is described several methods to interact on CMIS with cURL via ATOM.


# Get repositories #

REQUEST:

`curl -u root:exo http://localhost:8080/xcmis/rest/cmisatom`

RESPONSE:
```
<?xml version="1.0" ?>
<service xmlns="http://www.w3.org/2007/app" xmlns:atom="http://www.w3.org/2005/Atom"
	xmlns:cmisra="http://docs.oasis-open.org/ns/cmis/restatom/200908/">
	<workspace>
		<atom:title type="text">cmis1</atom:title>
		<cmisra:repositoryInfo
			xmlns:cmis="http://docs.oasis-open.org/ns/cmis/core/200908/">
			<cmis:repositoryId>cmis1</cmis:repositoryId>
			<cmis:repositoryName>cmis1</cmis:repositoryName>
			<cmis:rootFolderId>abcdef12-3456-7890-0987-654321fedcba</cmis:rootFolderId>
...
```

Copy the property value of cmis:rootFolderId for the next step - folder creation.


# Create folder #

REQUEST:

`curl -u root:exo --data-binary @post-folder.xml http://localhost:8080/xcmis/rest/cmisatom/cmis1/children/abcdef12-3456-7890-0987-654321fedcba`

post-folder.xml:
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<entry xmlns="http://www.w3.org/2005/Atom" xmlns:cmisra="http://docs.oasis-open.org/ns/cmis/restatom/200908/">
	<title type="text">1</title>
	<cmisra:object xmlns:cmis="http://docs.oasis-open.org/ns/cmis/core/200908/">
		<cmis:properties>
			<cmis:propertyId localName="cmis:objectTypeId" propertyDefinitionId="cmis:objectTypeId">
				<cmis:value>cmis:folder</cmis:value>
			</cmis:propertyId>
		</cmis:properties>
	</cmisra:object>
</entry>
```

or post-folder.xml:
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<entry xmlns="http://www.w3.org/2005/Atom" xmlns:cmisra="http://docs.oasis-open.org/ns/cmis/restatom/200908/">
	<cmisra:object xmlns:cmis="http://docs.oasis-open.org/ns/cmis/core/200908/">
		<cmis:properties>
			<cmis:propertyString localName="cmis:name" propertyDefinitionId="cmis:name">
				<cmis:value>1</cmis:value>
			</cmis:propertyString>
			<cmis:propertyId localName="cmis:objectTypeId" propertyDefinitionId="cmis:objectTypeId">
				<cmis:value>cmis:folder</cmis:value>
			</cmis:propertyId>
		</cmis:properties>
	</cmisra:object>
</entry>
```

RESPONSE:

```
<?xml version="1.0" ?>
<entry xmlns="http://www.w3.org/2005/Atom" xmlns:cmisra="http://docs.oasis-open.org/ns/cmis/restatom/200908/">
	<id>8f9a812b-3ea8-44d1-9b4d-2cfd1804b481</id>
	<published>2011-05-18T12:55:13.220Z</published>
	<updated>2011-05-18T12:55:13.220Z</updated>
	<summary type="text"></summary>
	<author>
		<name>root</name>
	</author>
	<title type="text">1</title>
	<link href="http://localhost:8080/xcmis/rest/cmisatom/cmis1" rel="service"
		type="application/atomsvc+xml"></link>
	<link
		href="http://localhost:8080/xcmis/rest/cmisatom/cmis1/object/8f9a812b-3ea8-44d1-9b4d-2cfd1804b481"
		rel="self"></link>
        ...
	<content type="text">1</content>
	<cmisra:object xmlns:cmis="http://docs.oasis-open.org/ns/cmis/core/200908/"
		xmlns:cmisra="http://docs.oasis-open.org/ns/cmis/restatom/200908/">
		<cmis:properties>
			<cmis:propertyString propertyDefinitionId="cmis:path"
				queryName="cmis:path" localName="cmis:path" displayName="cmis:path">
				<cmis:value>/1</cmis:value>
			</cmis:propertyString>
			<cmis:propertyId propertyDefinitionId="cmis:objectTypeId"
				queryName="cmis:objectTypeId" localName="cmis:objectTypeId"
				displayName="cmis:objectTypeId">
				<cmis:value>cmis:folder</cmis:value>
			</cmis:propertyId>
			<cmis:propertyString propertyDefinitionId="cmis:name"
				queryName="cmis:name" localName="cmis:name" displayName="cmis:name">
				<cmis:value>1</cmis:value>
			</cmis:propertyString>
			<cmis:propertyId propertyDefinitionId="cmis:objectId"
				queryName="cmis:objectId" localName="cmis:objectId" displayName="cmis:objectId">
				<cmis:value>8f9a812b-3ea8-44d1-9b4d-2cfd1804b481</cmis:value>
			</cmis:propertyId>
...
```

Copy the property value of cmis:objectId for the step - Create document.


# Get children #

Get children of the root folder.

REQUEST:

`curl -u root:exo http://localhost:8080/xcmis/rest/cmisatom/cmis1/children/abcdef12-3456-7890-0987-654321fedcba`

RESPONSE:

```
<?xml version="1.0" ?>
<feed xmlns="http://www.w3.org/2005/Atom" xmlns:cmisra="http://docs.oasis-open.org/ns/cmis/restatom/200908/">
	<id>abcdef12-3456-7890-0987-654321fedcba</id>
	<title type="text">Folder Children</title>
	<author>
		<name>root</name>
	</author>
	<updated>2011-05-24T08:57:36.257Z</updated>
	<link href="http://localhost:8080/xcmis/rest/cmisatom/cmis1" rel="service"
		type="application/atomsvc+xml"></link>
	<link
		href="http://localhost:8080/xcmis/rest/cmisatom/cmis1/children/abcdef12-3456-7890-0987-654321fedcba?null"
		rel="self" type="application/atom+xml; type=feed"></link>
        ...
	<cmisra:numItems>1</cmisra:numItems>
	<entry>
		<id>8f9a812b-3ea8-44d1-9b4d-2cfd1804b481</id>
		<published>2011-05-24T08:32:15.237Z</published>
		<updated>2011-05-24T08:32:15.237Z</updated>
		<summary type="text"></summary>
		<author>
			<name>root</name>
		</author>
		<title type="text">1</title>
		<link href="http://localhost:8080/xcmis/rest/cmisatom/cmis1" rel="service"
			type="application/atomsvc+xml"></link>
		<link
			href="http://localhost:8080/xcmis/rest/cmisatom/cmis1/object/8f9a812b-3ea8-44d1-9b4d-2cfd1804b481"
			rel="self"></link>
...
```


# Update properties #

Update properties of the folder.

REQUEST:

`curl -u root:exo -X PUT --data-binary @put-update-properties.xml http://localhost:8080/xcmis/rest/cmisatom/cmis1/children/8f9a812b-3ea8-44d1-9b4d-2cfd1804b481`

put-update-properties.xml:
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<entry xmlns="http://www.w3.org/2005/Atom" xmlns:cmisra="http://docs.oasis-open.org/ns/cmis/restatom/200908/">
	<title type="text">newName</title>
</entry>
```

or put-update-properties.xml:
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<entry xmlns="http://www.w3.org/2005/Atom" xmlns:cmisra="http://docs.oasis-open.org/ns/cmis/restatom/200908/">
	<cmisra:object xmlns:cmis="http://docs.oasis-open.org/ns/cmis/core/200908/">
		<cmis:properties>
			<cmis:propertyString localName="cmis:name" propertyDefinitionId="cmis:name">
				<cmis:value>newName</cmis:value>
			</cmis:propertyString>
		</cmis:properties>
	</cmisra:object>
</entry>
```

RESPONSE:

```
<?xml version="1.0" ?>
<entry xmlns="http://www.w3.org/2005/Atom" xmlns:cmisra="http://docs.oasis-open.org/ns/cmis/restatom/200908/">
	<id>8f9a812b-3ea8-44d1-9b4d-2cfd1804b481</id>
	<published>2011-05-24T08:32:15.237Z</published>
	<updated>2011-05-24T09:13:33.085Z</updated>
	<summary type="text"></summary>
	<author>
		<name>root</name>
	</author>
	<title type="text">newName</title>
...
```


# Create document #

REQUEST:

`curl -u root:exo --data-binary @post-document.xml http://localhost:8080/xcmis/rest/cmisatom/cmis1/children/8f9a812b-3ea8-44d1-9b4d-2cfd1804b481`

post-document.xml

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<atom:entry xmlns:app="http://www.w3.org/2007/app"
	xmlns:atom="http://www.w3.org/2005/Atom" xmlns:cmis="http://docs.oasis-open.org/ns/cmis/core/200908/"
	xmlns:cmism="http://docs.oasis-open.org/ns/cmis/messaging/200908/"
	xmlns:cmisra="http://docs.oasis-open.org/ns/cmis/restatom/200908/">
        <atom:content type="text">this is the content of the new document</atom:content>
	<cmisra:object>
		<cmis:properties>
			<cmis:propertyString localName="cmis:name" propertyDefinitionId="cmis:name">
				<cmis:value>doc1</cmis:value>
			</cmis:propertyString>
			<cmis:propertyId localName="cmis:objectTypeId" propertyDefinitionId="cmis:objectTypeId">
				<cmis:value>cmis:document</cmis:value>
			</cmis:propertyId>
		</cmis:properties>
	</cmisra:object>
</atom:entry>
```

RESPONSE:

```
<?xml version="1.0" ?>
<entry xmlns="http://www.w3.org/2005/Atom" xmlns:cmisra="http://docs.oasis-open.org/ns/cmis/restatom/200908/">
	<id>97bfeeda-7728-441d-ad77-963867447b88</id>
	<published>2011-05-18T13:11:51.351Z</published>
	<updated>2011-05-18T13:11:51.351Z</updated>
	<summary type="text"></summary>
	<author>
		<name>root</name>
	</author>
	<title type="text">doc1</title>
	<link href="http://localhost:8080/xcmis/rest/cmisatom/cmis1" rel="service"
		type="application/atomsvc+xml"></link>
	<link
		href="http://localhost:8080/xcmis/rest/cmisatom/cmis1/object/97bfeeda-7728-441d-ad77-963867447b88"
		rel="self"></link>
        ...
	<content type="text/plain"
		src="http://localhost:8080/xcmis/rest/cmisatom/cmis1/file/97bfeeda-7728-441d-ad77-963867447b88"></content>
	<cmisra:object xmlns:cmis="http://docs.oasis-open.org/ns/cmis/core/200908/"
		xmlns:cmisra="http://docs.oasis-open.org/ns/cmis/restatom/200908/">
		<cmis:properties>
			<cmis:propertyId propertyDefinitionId="cmis:objectTypeId"
				queryName="cmis:objectTypeId" localName="cmis:objectTypeId"
				displayName="cmis:objectTypeId">
				<cmis:value>cmis:document</cmis:value>
			</cmis:propertyId>
			<cmis:propertyString propertyDefinitionId="cmis:name"
				queryName="cmis:name" localName="cmis:name" displayName="cmis:name">
				<cmis:value>doc1</cmis:value>
			</cmis:propertyString>
			<cmis:propertyId propertyDefinitionId="cmis:objectId"
				queryName="cmis:objectId" localName="cmis:objectId" displayName="cmis:objectId">
				<cmis:value>97bfeeda-7728-441d-ad77-963867447b88</cmis:value>
			</cmis:propertyId>
...
```

Copy the property value of cmis:objectId for the step - Multifile the document.


# Multifile the document #

Create an another folder without the document.

`curl -u root:exo --data-binary @multifiling.xml http://localhost:8080/xcmis/rest/cmisatom/cmis1/children/24d58060-64e8-4882-90de-f102ebbf7e84`

REQUEST:

multifiling.xml

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<atom:entry xmlns:app="http://www.w3.org/2007/app"
	xmlns:atom="http://www.w3.org/2005/Atom" xmlns:cmis="http://docs.oasis-open.org/ns/cmis/core/200908/"
	xmlns:cmism="http://docs.oasis-open.org/ns/cmis/messaging/200908/"
	xmlns:cmisra="http://docs.oasis-open.org/ns/cmis/restatom/200908/">
	<atom:title type="text">doc1</atom:title>
        <atom:content type="text">this is the content of the new document</atom:content>
	<cmisra:object>
		<cmis:properties>
         <cmis:propertyId localName="cmis:objectId" propertyDefinitionId="cmis:objectId">
            <cmis:value>97bfeeda-7728-441d-ad77-963867447b88</cmis:value>
         </cmis:propertyId>
			<cmis:propertyId localName="cmis:objectTypeId" propertyDefinitionId="cmis:objectTypeId">
				<cmis:value>cmis:document</cmis:value>
			</cmis:propertyId>
		</cmis:properties>
	</cmisra:object>
</atom:entry>
```

RESPONSE:

```
<?xml version="1.0" ?>
<entry xmlns="http://www.w3.org/2005/Atom" xmlns:cmisra="http://docs.oasis-open.org/ns/cmis/restatom/200908/">
	<id>97bfeeda-7728-441d-ad77-963867447b88</id>
	<published>2011-05-18T13:11:51.351Z</published>
	<updated>2011-05-18T13:11:51.351Z</updated>
	<summary type="text"></summary>
	<author>
		<name>root</name>
	</author>
	<title type="text">doc1</title>
	<link href="http://localhost:8080/xcmis/rest/cmisatom/cmis1" rel="service"
		type="application/atomsvc+xml"></link>
	<link
		href="http://localhost:8080/xcmis/rest/cmisatom/cmis1/object/97bfeeda-7728-441d-ad77-963867447b88"
		rel="self"></link>
        ...
	<content type="text/plain"
		src="http://localhost:8080/xcmis/rest/cmisatom/cmis1/file/97bfeeda-7728-441d-ad77-963867447b88"></content>
	<cmisra:object xmlns:cmis="http://docs.oasis-open.org/ns/cmis/core/200908/"
		xmlns:cmisra="http://docs.oasis-open.org/ns/cmis/restatom/200908/">
		<cmis:properties>
			<cmis:propertyId propertyDefinitionId="cmis:objectTypeId"
				queryName="cmis:objectTypeId" localName="cmis:objectTypeId"
				displayName="cmis:objectTypeId">
				<cmis:value>cmis:document</cmis:value>
			</cmis:propertyId>
			<cmis:propertyString propertyDefinitionId="cmis:name"
				queryName="cmis:name" localName="cmis:name" displayName="cmis:name">
				<cmis:value>doc1</cmis:value>
			</cmis:propertyString>
			<cmis:propertyId propertyDefinitionId="cmis:objectId"
				queryName="cmis:objectId" localName="cmis:objectId" displayName="cmis:objectId">
				<cmis:value>97bfeeda-7728-441d-ad77-963867447b88</cmis:value>
			</cmis:propertyId>

```

As we see there is the same objectId.


# Remove object #

REQUEST:

`curl -u root:exo -X DELETE http://localhost:8080/xcmis/rest/cmisatom/cmis1/object/8f9a812b-3ea8-44d1-9b4d-2cfd1804b481`

RESPONSE:

nothing.


# Search #

REQUEST:

curl -u root:exo "http://localhost:8080/xcmis/rest/cmisatom/cmis1/query?q=SELECT%20*%20Fis:document%20WHERE%20IN_FOLDER('24d58060-64e8-4882-90de-f102ebbf7e84')"

RESPONSE:

```
<?xml version="1.0" ?>
<feed xmlns="http://www.w3.org/2005/Atom" xmlns:cmisra="http://docs.oasis-open.org/ns/cmis/restatom/200908/">
	<id>cmis:query:cmis1</id>
	<title type="text">Query</title>
	<author>
		<name>root</name>
	</author>
	<updated>2011-05-18T14:43:05.010Z</updated>
	<link href="http://localhost:8080/xcmis/rest/cmisatom/cmis1" rel="service"
		type="application/atomsvc+xml"></link>
	<link
		href="http://localhost:8080/xcmis/rest/cmisatom/cmis1/query/?q=SELECT%20%2A%20FROM%20cmis%3Adocument%20WHERE%20IN_FOLDER%28%2724d58060-64e8-4882-90de-f102ebbf7e84%27%29&amp;maxItems=2147483647&amp;skipCount=0"
		rel="first" type="application/atom+xml; type=feed"></link>
	<cmisra:numItems>1</cmisra:numItems>
	<entry>
		<id>f6e4932c-e89c-4bd7-a83a-eaa7b61b613a</id>
		<published>2011-05-18T14:38:28.964Z</published>
		<updated>2011-05-18T14:38:28.964Z</updated>
		<summary type="text"></summary>
		<author>
			<name>root</name>
		</author>
		<title type="text">doc1</title>
		<link href="http://localhost:8080/xcmis/rest/cmisatom/cmis1" rel="service"
			type="application/atomsvc+xml"></link>
		<link
			href="http://localhost:8080/xcmis/rest/cmisatom/cmis1/object/f6e4932c-e89c-4bd7-a83a-eaa7b61b613a"
			rel="self"></link>
      ...
		<content type="text/plain"
			src="http://localhost:8080/xcmis/rest/cmisatom/cmis1/file/f6e4932c-e89c-4bd7-a83a-eaa7b61b613a"></content>
		<cmisra:object xmlns:cmis="http://docs.oasis-open.org/ns/cmis/core/200908/"
			xmlns:cmisra="http://docs.oasis-open.org/ns/cmis/restatom/200908/">
			<cmis:properties>
				<cmis:propertyId propertyDefinitionId="cmis:objectTypeId"
					queryName="cmis:objectTypeId" localName="cmis:objectTypeId"
					displayName="cmis:objectTypeId">
					<cmis:value>cmis:document</cmis:value>
				</cmis:propertyId>
				<cmis:propertyId propertyDefinitionId="cmis:objectId"
					queryName="cmis:objectId" localName="cmis:objectId" displayName="cmis:objectId">
					<cmis:value>97bfeeda-7728-441d-ad77-963867447b88</cmis:value>
				</cmis:propertyId>
...
```
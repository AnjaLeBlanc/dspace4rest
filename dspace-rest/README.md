#DSpace REST API (Jersey)

A RESTful web services API for DSpace, built using JAX-RS1 JERSEY.

##Getting Started

```
mvn clean package
#If you run into an issue with Maven, try manually importing the Jar/War.
mvn install:install-file -DgroupId=org.dspace -DartifactId=dspace-rest -Dversion=4.0-SNAPSHOT -Dclassifier=classes -Dpackaging=jar -Dfile=dspace-rest/target/dspace-rest.war
mvn clean package
ant update
```


Configure tomcat to know about the rest webapp.
    <Context path="/rest"  docBase="/dspace/webapps/rest"  allowLinking="true"/>

At this point, this is a READ ONLY API for DSpace, for the anonymous user. Only Anonymous READ Communities, Collections, Items, and Bitstreams are available.

##Endpoints
###Communities
View the list of top-level communities
- http://localhost:8080/rest/communities

View a specific community
- http://localhost:8080/rest/communities/:ID

View a specific community, list its subcommunities, and subcollections
- http://localhost:8080/rest/communities/:ID?expand=all

###Collections
View the list of collections
- http://localhost:8080/rest/collections

View a specific collection
- http://localhost:8080/rest/collections/:ID

View a specific collection, and its items
- http://localhost:8080/rest/collections/:ID?expand=all

###Items
View an Item, and see its bitstreams
- http://localhost:8080/rest/items/:ID

###Bitstreams
View information about a bitstream
- http://localhost:8080/rest/bitstreams/:ID

View/Download a specific Bitstream
- http://localhost:8080/rest/bitstreams/:ID/retrieve?userIP={ip of original user}&userAgent={user Agent from original user request}&xforwarderfor={XForwarderFor of original user request}

###Handles
Lookup a DSpaceObject by its Handle, this produces the name/ID, that you lookup in /bitstreams, /items, /collections, /communities
- http://localhost:8080/rest/handle/{prefix}/{suffix}

##Expand
There is an ?expand= query parameter for more expensive operations. You can tack it on the end of endpoints.
It is optional, all, some or none. The response will usually indicate what the available "expand" options are.

##HTTP Responses
* 200 OK            - We have the requested object/objects
* 401 Unauthorized  - The anonymous user does not have READ access to that object
* 404 Not Found     - That object doesn't exist
* 500 Server Error  - Likely a SQLException, IOException, more details in the logs.
# Introduction 
This is a custom login module based on LDAP authentication.

Groups assignment logic is hardcoded in the source code and does not take into account LDAP groups.

# Build
**Prerequisite:** in order to successfully build this artifact, it needs some Payara libraries which are not available into public Maven repositories. 

So there are two options: 
- you can build a Payara server with Maven
- you can manually copy the needed Payara libraries from *modules* directory within a Payara installation into local Maven repository.
You can check the pom file to determine the exact libraries and versions needed.

Then, to build this artifact, just run `mvn clean install` from the project root folder.
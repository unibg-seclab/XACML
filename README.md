
# AT&T XACML

AT&amp;T's reference implementation of the OASIS XACML 3.0 Standard. The AT&T framework represents the entire XACML 3.0 object set as a collection of Java interfaces and standard implementations of those interfaces.  The AT&T PDP engine is built on top of this framework and represents a complete implementation of a XACML 3.0 PDP, including all of the multi-decision profiles. In addition, the framework also contains an implementation of the OASIS XACML 3.0 RESTful API v1.0 and XACML JSON Profile v1.0 WD 14. The PEP API includes annotation functionality, allowing application developers to simply annotate a Java class to provide attributes for a request. The annotation support removes the need for application developers to learn much of the API.

The AT&T framework also includes interfaces and implementations to standardize development of PIP engines that are used by the AT&T PDP implementation, and can be used by other implementations built on top of the AT&T framework. The framework also includes interfaces and implementations for a PAP distributed cloud infrastructure of PDP nodes that includes support for policy distribution and pip configurations. This PAP infrastructure includes a web application administrative console that contains a XACML 3.0 policy editor, attribute dictionary support, and management of PDP RESTful node instances. In addition, there are tools available for policy simulation.

The [wiki](https://github.com/att/XACML/wiki) has a lot of information on the organization of the software. 

Click here to view the [JavaDoc](http://att.github.io/XACML/javadocs/index.html).

Tutorials and other useful information is also located on our [Github Pages site](http://att.github.io/XACML). TBD - working on this 10/14.

# Release

There is a branch v1.0 that is the official release. Master branch is WIP.

# Requirements

* Java JDK 1.8, you will need to ensure Eclipse has that JDK installed in your development environment.

*  Apache Maven to compile, install and run the software.

# Building the source code

From the directory you downloaded the source to, just type 'mvn clean install'.

# Running the projects

There are 3 webapps that need to run. Easiest to start them in this order in separate terminal windows:

1. XACML-PAP-REST

Open a terminal window and cd to the XACML-PAP-REST subdirectory.

'mvn jetty:run-war'

By default it will be running at localhost:9090/pap

If you point your browser to that URL, you should get a JSON back listing the groups of pdps, the policies and PIP configurations for those groups.

2. XACML-PDP-REST

Open a terminal window and cd to the XACML-PDP-REST subdirectory.

'mvn jetty:run-war'

By default it will be running at localhost:8080/pdp

If you point your browser to this URL you can get the current configuration for the PDP:

http://localhost:8080/pdp/?type=config

3. XACML-PAP-ADMIN

Open a terminal window and cd to the XACML-PAP-ADMIN subdirectory.

'mvn jetty:run-war'

By default it will be running at localhost:8888/admin

If you point your browser to this URL you should see the Admin Console web page come up.

---

# How to use

## Install

Import the `XACML` folder as `maven project` in Eclipse.

Run maven `clean install` using the pom.xml of XACML.

Run maven `test`.

## Run test

### Test a single request

Execute `TestRequest.java` file in `XACML-PDP/src/main/java/eu/mosaicrown/test`.

The test directory (`dir`) must be passed as arguments (mandatory for any test).
Two more arguments must be passed in order to execute our test: the policy (`policy`) and the request (`request`).

Example:
```
-dir ~/git/XACML/XACML-PDP/src/main/resources
-policy ~/git/XACML/XACML-PDP/src/main/resources/policies/policy1.xml
-request ~/git/XACML/XACML-PDP/src/main/resources/requests/request1.xml
```

The test directory passed as argument must have this structure:
```
my_test_dir
 |- logging.properties
 |- xacml.properties
 |- results (folder, not mandatory for our test)
 |  `- here are the results produced by the evaluation
 |- requests (folder)
 |  `- here are the xacml requests
 `- policies (folder)
    `- here are our policies
```

For logger settings, pass the logging.properties file as VM argument (when using
`java.util.logging.Logger`).
```
-Djava.util.logging.config.file=/home/marco/git/XACML/XACML-PDP/src/main/resources/logging.properties
```

### Automatic test for all policies and request

Execute `AutoTestRequest.java` file in `XACML-PDP/src/main/java/eu/mosaicrown/test`.

This test automatically executes single TestRequest instances using each request
in the `requests` folder, and each policy in the `policies` folder.

#### Requests implemented
1. _userA_ wants to _read_ resource _object1_
2. userA wants to read (with purpose _good_) resource object1
3. userA wants to read (with purpose _scam_) resource object1

#### Policies implemented
1. _userA_ can _read_ resource _object1_
2. userA can read resource object1 with purpose good (in the Target).  
   userA can not read resource object1 with purpose scam (in the Target).
3. userA can read resource object1 if her purpose is not equal to _scam_ (in the Condition)

#### Test cases

|                  | policy1.xml | policy2.xml | policy3.xml |
| :--------------  | :---------: | :---------: | :---------: |
| **request1.xml** | Permit      | Deny        | Indeterminate (missing purpose in the request) |
| **request2.xml** | Permit      | Permit      | Permit      |
| **request3.xml** | Permit      | Deny        | Deny        |






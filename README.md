# RMI Banker example
## Michael J. May, Kinneret College on the Sea of Galilee

This code shows a two-tiered example using Java RMI.  We show how the classes and interfaces of RMI are used to make a simple server offers a bank interface over RMI.  The client and server both have command line interfaces and enable multi-threaded unsecured access.

The example shows the use of a directory object (AccountList) that includes the names and references of other remotely available objects (Account).  All objects are published and made avaiable using RMI. 

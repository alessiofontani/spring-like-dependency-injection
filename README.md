# Dependency Injection (DI) Example

This is a simplified implementation of a Dependency Injection framework, inspired by Spring Framework.
The reasons i came up with this project:
* Learning exercise to understand how Dependency Injection work
* Demonstrate how a DI framework works behind the scenes

The project is divided into two parts:

* The `org.di` package contains the implementation of the DI framework, including the
  `DependencyInjectionApp` class that is responsible for initializing the context and
  scanning for classes with the `@Component` annotation.
* The `org.client` package contains an example of how to use the DI framework to inject
  dependencies into a service class.

The project is built using Maven and the example can be run using the following command: `mvn clean compile exec:java -Dexec.mainClass=org.client.Main`
Spring Boot Angular JS demo
============================
- The project requires Java 8 and gradle.

Purpose of the project:
===========================
- showing how to put together a small spring-boot / angular js app.
- try out the new Spring-data CrudRepository interface.
- use of Money class from joda, including its persistence configuration using org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmount.
- use of Dozer mapper.
- use of custom JSON serializers/deserializers (for Joda Money).
- use of new features provided by Java 8 (Lambdas, Streams, Filters and Collectors).
- use of Apache POI to create .xls reports.

Setup IntelliJ:
===========================
The project uses lombok project. In order to enable IDE compile do the following:

1) Intellij Idea -> Preferences -> Compiler -> Annotation Processors

If you are on a Mac, enable annotation processing (tick the checkbox) from both:

1) Intellij Idea -> Preferences -> Compiler -> Annotation Processors
2) File -> Other Settings -> Default Settings -> Compiler -> Annotation Processors
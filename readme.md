# Spring Boot with Cassandra Demo

This is a simple demo application in Spring Boot + Kotlin with Cassandra CQL implementation.

## Post Example

    curl -d '{"title":"Attack of the 90''''s", "publisher":"Black Books", "tags":["Action","Fiction"]}' -H "Content-Type: application/json" -X POST http://localhost:8080/raw/book

Notes: 
1. There are other endpoints to test just check ```BookReadCtrl``` and ```BookWriteCtrl```
1. Cassandra implementation can be seen in ```*.data.*``` package and in ```*.ioc.CassandraConfig```
1. Tests are available for the ```*.data.*```
    - Important: Tests use Testcontainers which need docker installed
# xml-parser
Simple XML parser written in Java

## Requirements
- Java 11 or higher

## Build
```shell
# unix
./mvn clean package

# windows
./mvnw clean package
```

## Usage
```shell
java -jar .\target\xml-parser-1.0-SNAPSHOT-jar-with-dependencies.jar <xml string>
```

## Assumptions
Since the XML parser is a simple one, these are not supported: 
- XML attributes
- XML comments
- XML CDATA
- XML entity references (`&lt;`, `&amp;` etc. are treated as normal text)
- XML namespaces


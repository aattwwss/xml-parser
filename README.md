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

This also means that angle brackets (`<`, `>`) are not allowed as text, as they are treated as special characters in our syntax.

## Implementation
I perform a basic lexical analysis on a string and breaks them down into meaningful tokens. With the tokens, I feed it through a syntax analyser to check if the tokens are in the correct order. If the syntax is correct, I will then build a tree structure to represent the XML document.

## Tokens
The tokens are defined as follows:
- L_START_TAG: `<`
- R_TAG: `>`
- L_END_TAG: `</`
- TAG_NAME: `anything between < and > or </ and >`
- CONTENT: `anything between > and </`

## Syntax
The syntax is defined as follows:
- A single root tag
- Start with start tag, end with end tag
- TAG_NAME is case and whitespace sensitive
- TAG_NAME must be the same for both start and end tags
- CONTENT can be empty
- A valid start tag is defined as `[L_START_TAG] [TAG_NAME] [R_TAG]`
- A valid end tag is defined as `[L_END_TAG] [TAG_NAME] [R_TAG]`
- A valid tag is defined as `[START_TAG] [CONTENT] [END_TAG]` and `[L_END_TAG] [TAG_NAME] [R_TAG]` where `[TAG_NAME]` is the same for both
- A valid content is defined as `[CONTENT]` between a valid start tag and valid end tag

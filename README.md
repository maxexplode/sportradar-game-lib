# Live Football World Cup Scoreboard Library

A simple library to track and display live football World Cup matches and their scores. It supports starting new matches, updating scores, finishing matches, and generating a live summary ordered by total score and recency.

## Features

* **Start a new match**: Add a match with initial score 0–0.
* **Update score**: Update the current score of any ongoing match.
* **Finish match**: Remove a match from the live scoreboard when it ends.
* **Get summary**: Retrieve all ongoing matches, ordered by:

    1. Total score (descending)
    2. Most recently started match when tied

## Requirements

* Java 21 or higher
* Maven (for building)

## Installation

Add the library to your project via Maven:

```xml
<dependency>
  <groupId>com.maxexplode</groupId>
  <artifactId>sportrader-game-lib</artifactId>
  <version>1.0.0</version>
</dependency>
```

Then build your project:

```bash
mvn clean install
```

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

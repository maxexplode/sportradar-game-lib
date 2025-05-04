# Live Football World Cup Scoreboard Library

A simple and flexible Java library to track and manage live football World Cup matches and scores. Ideal for simulations, demos, and backend systems that need live match tracking.

---

## ✨ Features

- ✅ **Start a new match**: Add a match to the scoreboard with an initial score of 0–0.
- ✅ **Update scores**: Modify the current scores of ongoing matches.
- ✅ **Finish a match**: Remove a match from the scoreboard when it's over.
- ✅ **Get match summary**: List all ongoing matches, ordered by:
  1. **Total score (descending)**
  2. **Most recently started match when scores are equal**

---

## 📦 Requirements

- Java 21+
- Maven 3.9+

---

## 🚀 Installation

Add the dependency to your `pom.xml`:

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

---

## 🛠️ Usage

### 🏗 Initialize the scoreboard

```java
import com.maxexplode.ScoreBoardBuilder;
import com.maxexplode.core.IScoreboard;

IScoreboard scoreboard = new ScoreBoardBuilder().build();
```

### 🟢 Start a match

```java
scoreboard.startMatch("Mexico", "Canada");
```

### 🔁 Update scores

```java
scoreboard.updateScore("Mexico", "Canada", 0, 5);
```

### 🔚 Finish match

```java
scoreboard.finishMatch("Mexico", "Canada");
```

### 📊 Get summary

```java
List<Match> summary = scoreboard.getSummary();
summary.forEach(System.out::println);
//or
scoreboard.getSummaryAsString();
```

---

## 📝 Logging

This library uses **SLF4J** with **Logback** as the default backend.

### Sample `logback.xml`

```xml
<configuration>
  <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
    <encoder>
      <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
    </encoder>
  </appender>
  
  <logger name="com.maxexplode" level="DEBUG"/>

  <root level="INFO">
    <appender-ref ref="STDOUT"/>
  </root>
</configuration>
```

This logs:

- Match start, update, and finish events (`INFO`)
- Summary generation and internal storage lookups (`DEBUG`)

---

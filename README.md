# Lucene Analyzers

[![Java CI with Maven](https://github.com/codelibs/analyzers/actions/workflows/maven.yml/badge.svg)](https://github.com/codelibs/analyzers/actions/workflows/maven.yml)
[![Maven Central](https://img.shields.io/maven-central/v/org.codelibs/analyzers.svg?label=Maven%20Central)](https://central.sonatype.com/search?q=g%3Aorg.codelibs%20a%3Aanalyzers)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

A comprehensive Java library providing extended Lucene analyzers with specialized support for Japanese and English text processing. Built on Apache Lucene 10.2.2 with Java 21 compatibility.

## 🚀 Features

### Japanese Text Analysis
- **ProlongedSoundMarkCharFilter**: Normalizes prolonged sound marks (ー) in Japanese text
- **KanjiNumberFilter**: Converts kanji numerals to Arabic numerals
- **CharTypeFilter**: Filters tokens based on Japanese character types
- **IterationMarkCharFilter**: Handles Japanese iteration marks (々, ヽ, ヾ)
- **PosConcatenationFilter**: Concatenates tokens based on part-of-speech tags
- **PatternConcatenationFilter**: Pattern-based token concatenation
- **NumberConcatenationFilter**: Specialized number concatenation
- **StopTokenPrefixFilter** & **StopTokenSuffixFilter**: Advanced stop word filtering

### English Text Analysis
- **ReloadableStopFilter**: Dynamic stop word filtering with reload capability
- **FlexiblePorterStemFilter**: Enhanced Porter stemming with flexibility controls
- **ReloadableKeywordMarkerFilter**: Dynamic keyword protection during stemming
- **AlphaNumWordFilter**: Alphanumeric word processing

### Base Components
- **StopTokenFilter**: Core stop word filtering functionality
- **ConcatenationFilter**: Token concatenation base class
- **BufferedCharFilter**: Character-level filtering with buffering

## 📦 Installation

### Maven

```xml
<dependency>
    <groupId>org.codelibs</groupId>
    <artifactId>analyzers</artifactId>
    <version>10.2.2.0</version>
</dependency>
```

### Gradle

```gradle
implementation 'org.codelibs:analyzers:10.2.2.0'
```

## 🏗️ Requirements

- **Java**: 21 or higher
- **Apache Lucene**: 10.2.2
- **Maven**: 3.6+ (for building from source)

## 🔧 Usage Examples

### Japanese Text Processing

```java
// Create analyzer with Japanese filters
Analyzer analyzer = new Analyzer() {
    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer tokenizer = new StandardTokenizer();
        
        // Apply prolonged sound mark normalization
        TokenStream stream = tokenizer;
        stream = new KanjiNumberFilter(stream);
        stream = new CharTypeFilter(stream, CharType.KATAKANA);
        
        return new TokenStreamComponents(tokenizer, stream);
    }
    
    @Override
    protected Reader initReader(String fieldName, Reader reader) {
        reader = new ProlongedSoundMarkCharFilter(reader);
        reader = new IterationMarkCharFilter(reader);
        return reader;
    }
};
```

### English Text Processing

```java
// Create analyzer with English filters
Analyzer analyzer = new Analyzer() {
    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer tokenizer = new StandardTokenizer();
        
        TokenStream stream = tokenizer;
        stream = new ReloadableStopFilter(stream, stopWords);
        stream = new FlexiblePorterStemFilter(stream);
        stream = new AlphaNumWordFilter(stream);
        
        return new TokenStreamComponents(tokenizer, stream);
    }
};
```

## 🏛️ Architecture

The library is organized into language-specific packages:

```
org.codelibs.analysis/
├── Base classes (StopTokenFilter, ConcatenationFilter, BufferedCharFilter)
├── ja/ - Japanese-specific analyzers
│   ├── Character filters (ProlongedSoundMark, IterationMark)
│   ├── Token filters (KanjiNumber, CharType, various concatenation)
│   └── Stop word filters (Prefix/Suffix variants)
└── en/ - English-specific analyzers
    ├── Stop word filtering (Reloadable variants)
    ├── Stemming (FlexiblePorter)
    └── Word processing (AlphaNumWord)
```

## 🔨 Building from Source

```bash
# Clone the repository
git clone https://github.com/codelibs/analyzers.git
cd analyzers

# Build with Maven
mvn clean compile

# Run tests
mvn test

# Create package
mvn clean package
```

### Development Commands

```bash
# Format code (required before commits)
mvn formatter:format

# Apply license headers
mvn license:format

# Generate coverage report
mvn jacoco:report

# Run specific test class
mvn test -Dtest=KanjiNumberFilterTest
```

## 🧪 Testing

The library uses Lucene's comprehensive testing framework:
- All tests extend `BaseTokenStreamTestCase`
- JUnit 4 for test structure
- Lucene test utilities for token stream validation
- Comprehensive coverage of analyzer behavior

## 📄 License

Licensed under the Apache License, Version 2.0. See [LICENSE](http://www.apache.org/licenses/LICENSE-2.0.txt) for details.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Run `mvn formatter:format` and `mvn license:format`
4. Ensure all tests pass with `mvn clean package`
5. Submit a pull request

## 📚 Documentation

- [Apache Lucene Documentation](https://lucene.apache.org/core/documentation.html)
- [API Documentation](https://javadoc.io/doc/org.codelibs/analyzers)
- [CodeLibs Project](https://www.codelibs.org/)

## 🐛 Issues & Support

- [GitHub Issues](https://github.com/codelibs/analyzers/issues)
- [Maven Repository](https://repo1.maven.org/maven2/org/codelibs/analyzers/)

## 📈 Version History

The version number follows Lucene's versioning scheme with an additional patch level:
- Format: `{lucene.version}.{patch}`
- Current: `10.2.2.0` (based on Lucene 10.2.2)


# CLAUDE.md - AI Assistant Guide for Lucene Analyzers

## Project Overview

**Lucene Analyzers** is a Java library providing extended Apache Lucene text analyzers with specialized support for Japanese and English language processing. Maintained by the CodeLibs Project since 2011.

- **Organization**: CodeLibs Project
- **License**: Apache License 2.0
- **Repository**: github.com/codelibs/analyzers

## Quick Commands

```bash
mvn clean compile          # Build the project
mvn test                   # Run all tests
mvn clean package          # Build package (includes tests)
mvn formatter:format       # Format code (required before commits)
mvn license:format         # Apply license headers
mvn jacoco:report          # Generate coverage report
mvn test -Dtest=ClassName  # Run specific test class
```

## Project Structure

```
analyzers/
├── src/
│   ├── main/java/org/codelibs/analysis/
│   │   ├── BufferedCharFilter.java      # Abstract char filter with buffering
│   │   ├── ConcatenationFilter.java     # Base token concatenation filter
│   │   ├── StopTokenFilter.java         # Base stop word filter
│   │   ├── ja/                          # Japanese-specific filters
│   │   └── en/                          # English-specific filters
│   └── test/java/org/codelibs/analysis/
│       ├── ja/                          # Japanese filter tests
│       └── en/                          # English filter tests
├── pom.xml
└── .github/workflows/maven.yml
```

## Technology Stack

- **Java**: 21 (required)
- **Build Tool**: Maven 3.6+
- **Core Dependency**: Apache Lucene (`lucene-analysis-common`)
- **Test Framework**: JUnit + Lucene Test Framework
- **Test Tokenizer**: Kuromoji (Japanese morphological analyzer, test scope only)

## Code Conventions

### Package Organization
- `org.codelibs.analysis` - Base abstract classes
- `org.codelibs.analysis.ja` - Japanese-specific filters
- `org.codelibs.analysis.en` - English-specific filters

### Naming Conventions
- Token filters: `{FeatureName}Filter` (e.g., `KanjiNumberFilter`)
- Character filters: `{FeatureName}CharFilter` (e.g., `ProlongedSoundMarkCharFilter`)
- Test classes: `{ClassName}Test` (e.g., `KanjiNumberFilterTest`)

### Lucene Filter Patterns
- **Token Filters** extend `TokenFilter` with `final` on `incrementToken()`
- **Character Filters** extend `BaseCharFilter` or `BufferedCharFilter`
- **Attribute caching**: Cache attribute references in fields (performance critical)

### Design Patterns Used
- **Template Method Pattern**: `BufferedCharFilter.processInput()`, `ConcatenationFilter.isTarget()`/`isConcatenated()`, `StopTokenFilter.accept()`
- **State Capture for Lookahead**: Use `captureState()`/`restoreState()` for token buffering

### Code Quality Requirements
- **License Headers**: Run `mvn license:format` for new files
- **Code Formatting**: Run `mvn formatter:format` before commits
- **Javadoc**: All public classes and methods should have documentation
- **Internationalization**: Use `Locale.ROOT` for case operations, UTF-8 for file operations

## Testing Conventions

- All tests extend `BaseTokenStreamTestCase` from Lucene
- 1:1 ratio between main sources and test files
- Tests use `JapaneseTokenizer` (Kuromoji) for Japanese filter tests
- Use `assertAnalyzesTo()` for token output verification with offsets
- Use `checkRandomData()` for fuzz testing
- Tests use fixed seed (`DEADBEEF`) for reproducible randomization

## CI/CD

GitHub Actions workflow (`.github/workflows/maven.yml`):
- Triggers on: push/PR to `master` and `*.x` branches
- Environment: Ubuntu latest, Java 21 (Temurin)
- Command: `mvn -B package`

## Development Workflow

1. **Before making changes**: Run `mvn clean compile` to ensure clean state
2. **After making changes**:
   - Run `mvn formatter:format` to format code
   - Run `mvn license:format` to ensure license headers
   - Run `mvn test` to verify all tests pass
3. **Before committing**: Run `mvn clean package` for full validation

## Common Pitfalls

1. **Forgetting to format**: Always run `mvn formatter:format` before commits
2. **Missing license headers**: Run `mvn license:format` for new files
3. **Incorrect offsets**: Token filters must maintain correct character offsets
4. **Resource leaks**: Use try-with-resources for Reader/TokenStream
5. **Locale issues**: Always use `Locale.ROOT` for case operations

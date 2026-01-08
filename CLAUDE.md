# CLAUDE.md - AI Assistant Guide for Lucene Analyzers

## Project Overview

**Lucene Analyzers** is a Java library providing extended Apache Lucene text analyzers with specialized support for Japanese and English language processing. Maintained by the CodeLibs Project since 2011.

- **Organization**: CodeLibs Project
- **License**: Apache License 2.0
- **Repository**: github.com/codelibs/analyzers
- **Current Version**: 10.3.2.1-SNAPSHOT (based on Lucene 10.3.2)

## Quick Commands

```bash
# Build the project
mvn clean compile

# Run all tests
mvn test

# Build package (includes tests)
mvn clean package

# Format code (required before commits)
mvn formatter:format

# Apply license headers
mvn license:format

# Generate coverage report
mvn jacoco:report

# Run specific test class
mvn test -Dtest=KanjiNumberFilterTest
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
│   │   │   ├── ProlongedSoundMarkCharFilter.java
│   │   │   ├── IterationMarkCharFilter.java
│   │   │   ├── KanjiNumberFilter.java
│   │   │   ├── CharTypeFilter.java
│   │   │   ├── PosConcatenationFilter.java
│   │   │   ├── PatternConcatenationFilter.java
│   │   │   ├── NumberConcatenationFilter.java
│   │   │   ├── StopTokenPrefixFilter.java
│   │   │   └── StopTokenSuffixFilter.java
│   │   └── en/                          # English-specific filters
│   │       ├── ReloadableStopFilter.java
│   │       ├── FlexiblePorterStemFilter.java
│   │       ├── FlexiblePorterStemmer.java
│   │       ├── ReloadableKeywordMarkerFilter.java
│   │       └── AlphaNumWordFilter.java
│   └── test/java/org/codelibs/analysis/
│       ├── ja/                          # Japanese filter tests
│       └── en/                          # English filter tests
├── pom.xml
├── README.md
├── LICENSE
└── .github/workflows/maven.yml
```

## Technology Stack

- **Java**: 21 (required)
- **Build Tool**: Maven 3.6+
- **Core Dependency**: Apache Lucene 10.3.2
- **Test Framework**: JUnit 4.13.2 + Lucene Test Framework
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

1. **Token Filters** extend `TokenFilter`:
```java
public class MyFilter extends TokenFilter {
    private final CharTermAttribute termAttr = addAttribute(CharTermAttribute.class);

    @Override
    public final boolean incrementToken() throws IOException {
        // Use 'final' on incrementToken (Lucene convention)
        // Return true if token available, false at EOF
    }
}
```

2. **Character Filters** extend `BaseCharFilter` or `BufferedCharFilter`:
```java
public class MyCharFilter extends BufferedCharFilter {
    @Override
    protected String processInput(String input) {
        // Transform input string
        return transformed;
    }
}
```

3. **Attribute caching** (performance critical):
```java
// Correct: Cache attribute references in fields
private final CharTermAttribute termAttr = addAttribute(CharTermAttribute.class);

// Avoid: Looking up attributes per token
```

### Design Patterns Used

1. **Template Method Pattern** - Primary pattern for filter extensibility:
   - `BufferedCharFilter.processInput()` - subclasses implement transformation
   - `ConcatenationFilter.isTarget()` / `isConcatenated()` - subclasses define concatenation logic
   - `StopTokenFilter.accept()` - subclasses define matching logic

2. **State Capture for Lookahead**:
```java
State state = captureState();
// ... process next token ...
restoreState(state);  // Restore previous state
```

### Code Quality Requirements

1. **License Headers** - All Java files must have Apache 2.0 license header
   - Run `mvn license:format` to add/update headers

2. **Code Formatting** - Eclipse formatter configuration
   - Run `mvn formatter:format` before commits

3. **Javadoc** - All public classes and methods should have documentation

4. **Internationalization**:
   - Use `Locale.ROOT` for case operations (e.g., `toLowerCase(Locale.ROOT)`)
   - UTF-8 encoding for all file operations

## Testing Conventions

### Test Structure
- All tests extend `BaseTokenStreamTestCase` from Lucene
- 1:1 ratio between main sources and test files
- Tests use `JapaneseTokenizer` (Kuromoji) for Japanese filter tests

### Common Test Patterns

```java
public class MyFilterTest extends BaseTokenStreamTestCase {

    private final Analyzer analyzer = new Analyzer() {
        @Override
        protected TokenStreamComponents createComponents(String fieldName) {
            Tokenizer tokenizer = new JapaneseTokenizer(null, false, JapaneseTokenizer.Mode.SEARCH);
            return new TokenStreamComponents(tokenizer, new MyFilter(tokenizer));
        }
    };

    @Test
    public void testBasics() throws IOException {
        // Assert token output with offsets
        assertAnalyzesTo(analyzer, "input text",
            new String[] { "expected", "tokens" },
            new int[] { 0, 9 },   // start offsets
            new int[] { 8, 15 }   // end offsets
        );
    }

    @Test
    public void testRandom() throws IOException {
        // Fuzz testing for robustness
        checkRandomData(random(), analyzer, 100 * RANDOM_MULTIPLIER, 8192);
    }
}
```

### Test Seed
Tests use a fixed seed (`DEADBEEF`) for reproducible randomization, configured in pom.xml.

## CI/CD

GitHub Actions workflow (`.github/workflows/maven.yml`):
- Triggers on: push/PR to `master` and `*.x` branches
- Environment: Ubuntu latest, Java 21 (Temurin)
- Command: `mvn -B package`

## Versioning

Version format: `{lucene.version}.{patch}` (e.g., `10.3.2.0`)
- Follows Lucene versioning with an additional patch level
- SNAPSHOT versions during development

## Key Components Reference

### Base Classes
| Class | Purpose |
|-------|---------|
| `BufferedCharFilter` | Buffers entire input for full-text transformations |
| `ConcatenationFilter` | Base for token concatenation with lookahead |
| `StopTokenFilter` | Array-based stop word filtering |

### Japanese Filters
| Class | Purpose |
|-------|---------|
| `KanjiNumberFilter` | Converts kanji numerals (一, 二, 万, 億) to Arabic |
| `ProlongedSoundMarkCharFilter` | Normalizes prolonged sound marks (ー) |
| `IterationMarkCharFilter` | Handles iteration marks (々, ヽ, ヾ) |
| `CharTypeFilter` | Filters by character type (hiragana, katakana, kanji) |
| `PosConcatenationFilter` | Concatenates by part-of-speech tags |
| `PatternConcatenationFilter` | Pattern-based concatenation |
| `NumberConcatenationFilter` | Number token concatenation |

### English Filters
| Class | Purpose |
|-------|---------|
| `ReloadableStopFilter` | Dynamic stop word filtering with file reload |
| `FlexiblePorterStemFilter` | Configurable Porter stemming (6 steps) |
| `ReloadableKeywordMarkerFilter` | Dynamic keyword protection |
| `AlphaNumWordFilter` | Alphanumeric token concatenation |

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

## Dependencies (compile scope)

```xml
<dependency>
    <groupId>org.apache.lucene</groupId>
    <artifactId>lucene-analysis-common</artifactId>
    <version>10.3.2</version>
</dependency>
```

Test-only dependencies include `lucene-analysis-kuromoji`, `lucene-test-framework`, and `junit`.

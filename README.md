# 2D Chess

![Gradle Build](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/actions/workflows/main.yml/badge.svg)

## Contributors
- Larry Ling
- Raymond Gu
- Sebastian Pacheco

## Dependencies
- JDK 11
- JUnit 5.10
- Gradle 8.10
- Swing
- JaCoCo 0.8.12
- PIT 1.22.1

## Exceptions
### Code Coverage
JaCoCo is configured to automatically generate a code coverage report when running:
```bash
./gradlew.bat test
```
Report: `build/reports/jacoco/test/html/index.html`

Current coverage is incomplete, particularly in `domain.piece` and `domain.state`. No minimum coverage threshold is enforced.
### Mutation Testing
PIT is configured to run mutation testing with:
```bash
./gradlew.bat pitest
```
Report: `build/reports/pitest/index.html`

Mutation testing currently targets the `domain` packages and excludes Swing UI classes. No minimum mutation coverage threshold is enforced.
### Others
N/A

## Acknowledgements
1. Images used in the project are under Creative Commons License and can be found at:
    - [board](https://commons.wikimedia.org/wiki/File:Chess_Board.svg)
    - [pieces](https://commons.wikimedia.org/wiki/Category:SVG_chess_pieces)

2. [Northwestern Color Palette](https://www.northwestern.edu/brand/visual-identity/color-palettes/)

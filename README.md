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
- JaCoCo
- PIT Gradle Plugin 1.19.0
- Checkstyle 10.21.0
- SpotBugs Gradle Plugin 6.5.13 (SpotBugs 4.10.5)

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
- **Checkstyle:** Configured with team-specific rules in `config/checkstyle/`. Both `checkstyleMain` and `checkstyleTest` pass.
- **SpotBugs:** Configured to analyze production and test code using `./gradlew.bat spotbugsMain spotbugsTest`. Reports are generated in `build/reports/spotbugs/`. The latest analysis identified 9 medium-priority warnings and no high-priority warnings. The remaining findings are under review, and `spotbugsMain` currently fails verification.
- **TDD Violation:** `GameState` was merged into `main` without corresponding tests, violating the required TDD workflow. This historical violation was identified in the [Week 7 instructor code review (PR #23)](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/pull/23).

## Acknowledgements
1. Images used in the project are under Creative Commons License and can be found at:
    - [board](https://commons.wikimedia.org/wiki/File:Chess_Board.svg)
    - [pieces](https://commons.wikimedia.org/wiki/Category:SVG_chess_pieces)

2. [Northwestern Color Palette](https://www.northwestern.edu/brand/visual-identity/color-palettes/)
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
- **SpotBugs:** Configured to analyze production and test code. Run `./gradlew.bat spotbugsMain spotbugsTest` to generate HTML reports under `build/reports/spotbugs/`. Only high-priority warnings are reported and treated as build failures. Medium-priority findings are reviewed and addressed as necessary. Before applying this threshold, the analysis identified two remaining medium-priority warnings: an intentional MVC controller reference in `BoardView` and an unused game-progress field in `GameState` reserved for upcoming functionality.
- **TDD Violation:** `GameState` was merged into `main` without corresponding tests, violating the required TDD workflow. This historical violation was identified in the [Week 7 instructor code review (PR #23)](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/pull/23).

## Acknowledgements
1. Images used in the project are under Creative Commons License and can be found at:
    - [board](https://commons.wikimedia.org/wiki/File:Chess_Board.svg)
    - [pieces](https://commons.wikimedia.org/wiki/Category:SVG_chess_pieces)

2. [Northwestern Color Palette](https://www.northwestern.edu/brand/visual-identity/color-palettes/)
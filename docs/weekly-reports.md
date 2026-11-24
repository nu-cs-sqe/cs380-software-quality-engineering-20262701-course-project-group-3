# Week 3 (10/12/2026-10/18/2026)
**Planning and Progress Tracking**:
1. [done] Person: Task (Links to PR)
2. [not started] Person: Task (Links to PR)
3. [80% done] Person: Task (Links to PR)
4. etc.

# Week 4 (10/19/2026-10/25/2026)
**Planning and Progress Tracking**:
1. [done] Team: Schedule the required instructor meeting and review the provided Chess reference project.
2. [done] Team: Select Java Swing for the chess GUI and organize the Game Setup Phase requirements.
3. [done] Sebastian: Update the weekly project plan ([PR #1](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/pull/1)).
4. [done] Larry: Implement the starter chess application, including pieces, board, controller, GUI, and sprites ([PR #4](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/pull/4)).
5. [done] Sebastian: Configure Gradle to run the application ([PR #7](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/pull/7)).
6. [in progress] Team: Document the chess system design and develop initial Game Setup Phase features.

# Week 5 (10/26/2026-11/01/2026)
**Planning and Progress Tracking**:
1. [done] Sebastian: Create and merge the starter PlantUML system design ([PR #9](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/pull/9)).
2. [done] Sebastian: Implement `GameClock` and `TimeControl`, including timer initialization, BVA, and unit tests ([PR #10](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/pull/10)).
3. [done] Larry: Implement player name validation, error handling, BVA, and tests ([PR #12](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/pull/12)).
4. [done] Team: Review and merge completed Game Setup Phase features into `main`.
5. [in progress] Team: Continue integrating player timers and game-state tracking with the GUI.

# Week 6 (11/02/2026-11/08/2026)
**Planning and Progress Tracking**:
1. [done] Larry: Implement game-state initialization, including active player, game progress, captured pieces, move history, and state-change listeners ([PR #16](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/pull/16)).
2. [done] Sebastian: Complete Player Timer GUI Integration with four selectable time controls, initial timer displays, BVA documentation, and GUI tests ([PR #20](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/pull/20)).
3. [done] Team: Review, approve, and merge PRs #16 and #20 into `main`.
4. [in progress] Team: Review instructor feedback and prepare to merge the code review documentation ([PR #17](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/pull/17)).
5. [done] Raymond: Configure Checkstyle linting and formatting rules ([PR #21](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/pull/21)).
6. [not started] Team: Implement initial timer countdown behavior and subsequent gameplay functionality ([Issue #14](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/issues/14)).

# Week 7 (11/09/2026-11/15/2026)
**Planning and Progress Tracking**:
1. [done] Raymond: Restructure the chess GUI, including individual player panels, history/indicator panels, and layout scaling ([PR #24](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/pull/24)).
2. [in progress] Team: Review the instructor's Week 7 code review feedback and document required fixes to `GameState`, `Board`, and `Move`, including missing tests and TDD-process concerns ([PR #23](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/pull/23)).
3. [in progress] Sebastian: Plan and design BVA test cases for the initial timer countdown and pause/resume behavior ([Issue #14](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/issues/14)).
4. [in progress] Sebastian: Address code-review feedback by improving `Move` validation and special-move representation and extending `GameState` test coverage; coordinate review and integration with the team ([PR #23](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/pull/23)).
5. [in progress] Team: Identify gameplay integration and test-coverage gaps arising from the instructor review before planning the next move-generation milestone ([PR #23](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/pull/23)).

# Week 8 (11/16/2026-11/25/2026)
**Planning and Progress Tracking**:
1. [done] Sebastian: Configure JaCoCo coverage reporting and PIT mutation testing, including Gradle integration and report generation; check remaining coverage and mutation gaps ([PR #26](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/pull/26)).
2. [done] Sebastian: Add SpotBugs static analysis for production and test code, configure HTML reports and severity handling, and document its use ([PR #28](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/pull/28)).
3. [done] Sebastian: Implement initial player-timer countdown with start/pause/resume, time-expiration boundaries, and untimed behavior, supported by BVA tests ([Issue #14](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/issues/14)).
4. [in progress] Larry: Implement internationalization with resource bundles, locale selection, and a language dropdown/startup UI; complete integration and regression verification (see `docs/bva/I18n.md`).
5. [in review] Raymond: Make `Board` testable through empty-board construction, controlled piece placement/lookup, board snapshots, and BVA/unit tests; resolve code-review comments on invalid positions ([PR #47](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/pull/47), [Issue #45](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/issues/45)).
6. [in progress] Larry: Extend `Move` with validation, accessors, value equality, hashing, and readable string representation to support later gameplay operations ([Issue #37](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/issues/37)).
7. [in progress] Sebastian: Implement pseudo-legal pawn destination generation, using TC01–TC34 BVA tests for both colors, two-square advances, captures, blockers, board boundaries, and non-mutation. Local verification is complete; Gradle quality checks and PR review are pending ([Issue #38](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/issues/38)).
8. [not started] Team: Complete pseudo-legal knight move generation and tests for the initial position and board boundaries ([Issue #39](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/issues/39)).
9. [not started] Team: Ensure rook, bishop, queen, and king move-generation calls safely return empty results in the initial position ([Issue #40](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/issues/40)).
10. [not started] Team: Implement `Board.applyMove()` for normal moves/captures and an independent `Board.copy()`, including failure-path tests ([Issue #41](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/issues/41)).
11. [in progress] Team: Address Week 8 instructor feedback and add JaCoCo/SpotBugs README badges; confirm findings and CI checks before marking the work complete ([Issue #42](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/issues/42), [Issue #44](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/issues/44)).
12. [done] Team: Close the task to rename `PROGRESS` to `Status` ([Issue #43](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/issues/43)).
13. [in progress] Team: Integrate the first-move components and verify move selection, legal destinations, game-state and clock updates, move history, and GUI behavior ([User Story #36](https://github.com/nu-cs-sqe/cs380-software-quality-engineering-20262701-course-project-group-3/issues/36)).

# One section per week until the last week of class (the week before the Finals week)


# Week X (XX/XX/2026-XX/XX/2026) TEMPLATE (You can change the format to whatever the team likes better)
**Planning and Progress Tracking**:
1. [done] Person: Task (Links to PR)
2. [not started] Person: Task (Links to PR)
3. [80% done] Person: Task (Links to PR)

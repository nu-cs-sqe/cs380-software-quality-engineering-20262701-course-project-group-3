
# BVA Analysis for Player Timer GUI Integration
# Author: Sebastian Pacheco

**Scope:** Player timer GUI integration only. Players can select a time control before starting a game and view both players' initial remaining time. Countdown, pausing, switching the active clock, and timeout handling will be implemented in a later feature. Move increments will not be supported.

**Selected presets:**

| Game mode | Time control | Initial time per player | Display  |
|-----------|--------------|-------------------------|----------|
| Untimed   | Unlimited    | -1 seconds (sentinel)   | Untimed  |
| Blitz     | 3\|0         | 180 seconds             | 03:00    |
| Rapid     | 10\|0        | 600 seconds             | 10:00    |
| Classical | 60\|0        | 3,600 seconds           | 01:00:00 |

### Method under test: `TimeControlSelector()` and `getSelectedTimeControl()`

|                           | System under test                          | Expected output                                                                 | Implemented?       |
|---------------------------|--------------------------------------------|---------------------------------------------------------------------------------|--------------------|
| TC01: Default selection   | Create selector without changing selection | Returns `TimeControl.UNTIMED`                                                   | :white_check_mark: |
| TC02: Available presets   | Inspect available dropdown options         | Contains exactly Untimed, Blitz, Rapid, and Classical                           | :white_check_mark: |
| TC03: Display names       | Inspect each dropdown option               | Displays `Untimed`, `Blitz (3 min)`, `Rapid (10 min)`, and `Classical (60 min)` | :white_check_mark: |
| TC04: Change selection    | Select each available time control         | Returns the corresponding selected `TimeControl`                                | :white_check_mark: |
| TC05: Revert selection    | Select Classical, then select Untimed      | Returns `TimeControl.UNTIMED`                                                   | :white_check_mark: |

### Method under test: `ClockTimeFormatter.format(long remainingSeconds)`

|                                      | System under test                             | Expected output                                             | Implemented?       |
|--------------------------------------|-----------------------------------------------|-------------------------------------------------------------|--------------------|
| TC06: Invalid negative time          | Call: `format(-2)`                            | Throws `IllegalArgumentException` with a meaningful message | :white_check_mark: |
| TC07: Untimed boundary               | Call: `format(-1)`                            | Returns `Untimed`                                           | :white_check_mark: |
| TC08: Zero remaining time            | Call: `format(0)`                             | Returns `00:00`                                             | :white_check_mark: |
| TC09: Smallest positive time         | Call: `format(1)`                             | Returns `00:01`                                             | :white_check_mark: |
| TC10: Below one minute               | Call: `format(59)`                            | Returns `00:59`                                             | :white_check_mark: |
| TC11: Exactly one minute             | Call: `format(60)`                            | Returns `01:00`                                             | :white_check_mark: |
| TC12: Below ten minutes              | Call: `format(599)`                           | Returns `09:59`                                             | :white_check_mark: |
| TC13: Exactly ten minutes            | Call: `format(600)`                           | Returns `10:00`                                             | :white_check_mark: |
| TC14: Below one hour                 | Call: `format(3599)`                          | Returns `59:59`                                             | :white_check_mark: |
| TC15: Exactly one hour               | Call: `format(3600)`                          | Returns `01:00:00`                                          | :white_check_mark: |
| TC16: Above one hour                 | Call: `format(3601)`                          | Returns `01:00:01`                                          | :white_check_mark: |
| TC17: Beyond 23 hours                | Call: `format(86400)`                         | Returns `24:00:00` without wrapping hours                   | :white_check_mark: |
| TC18: Maximum supported initial time | Call: `format((long) Integer.MAX_VALUE * 60)` | Returns `35791394:07:00` without overflow                   | :white_check_mark: |

### Method under test: `GameStatsView(String whiteName, String blackName, GameClock clock)`

|                                   | System under test                                                                                  | Expected output                                                                                  | Implemented?       |
|-----------------------------------|----------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------|--------------------|
| TC19: Invalid null clock          | Construct with `null` clock                                                                        | Throws `IllegalArgumentException` with a meaningful message                                      | :white_check_mark: |
| TC20: Untimed game                | Construct with `new GameClock(0)`                                                                  | Both players display `Untimed`                                                                   | :x:                |
| TC21: Blitz game                  | Construct with `new GameClock(3)`                                                                  | Both players display `03:00`                                                                     | :x:                |
| TC22: Rapid game                  | Construct with `new GameClock(10)`                                                                 | Both players display `10:00`                                                                     | :x:                |
| TC23: Classical game              | Construct with `new GameClock(60)`                                                                 | Both players display `01:00:00`                                                                  | :x:                |
| TC24: Existing player information | Construct with valid player names; call `onTurnChanged()`                                          | Player names and colors remain visible, and the current-player label displays the correct player | :x:                |
| TC25: Selected preset integration | For each `TimeControl`, initialize `GameClock` with its minute value and construct `GameStatsView` | Both players display the correct and equal initial times for all four presets                    | :x:                |
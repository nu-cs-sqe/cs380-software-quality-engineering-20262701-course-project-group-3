
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
| TC20: Untimed game                | Construct with `new GameClock(0)`                                                                  | Both players display `Untimed`                                                                   | :white_check_mark: |
| TC21: Blitz game                  | Construct with `new GameClock(3)`                                                                  | Both players display `03:00`                                                                     | :white_check_mark: |
| TC22: Rapid game                  | Construct with `new GameClock(10)`                                                                 | Both players display `10:00`                                                                     | :white_check_mark: |
| TC23: Classical game              | Construct with `new GameClock(60)`                                                                 | Both players display `01:00:00`                                                                  | :white_check_mark: |
| TC24: Existing player information | Construct with valid player names; call `onTurnChanged()`                                          | Player names and colors remain visible, and the current-player label displays the correct player | :white_check_mark: |
| TC25: Selected preset integration | For each `TimeControl`, initialize `GameClock` with its minute value and construct `GameStatsView` | Both players display the correct and equal initial times for all four presets                    | :white_check_mark: |

### Method under test: `GameStatsView` countdown refresh and `Main.startGame()`

| Test case                           | System under test                                                                | Expected output                                                       | Implemented? |
|-------------------------------------|----------------------------------------------------------------------------------|-----------------------------------------------------------------------|--------------|
| TC26: Automatic countdown startup   | Start a Blitz game through `Main.startGame()`; allow time to elapse              | White's countdown starts automatically without user interaction       | :x:          |
| TC27: Below first display change    | Start Blitz countdown; advance 999 milliseconds; refresh GUI                     | WHITE displays `03:00`; BLACK displays `03:00`                        | :x:          |
| TC28: First countdown update        | Start Blitz countdown; advance 1,000 milliseconds; trigger scheduled GUI refresh | WHITE displays `02:59` automatically                                  | :x:          |
| TC29: Black display unchanged       | Start Blitz countdown; advance 2 seconds; refresh GUI                            | WHITE displays `02:58`; BLACK displays `03:00`                        | :x:          |
| TC30: Paused countdown display      | Start; advance 1 second; pause; advance 5 more seconds; refresh GUI              | WHITE continues displaying `02:59`                                    | :x:          |
| TC31: Resumed countdown display     | Pause at 179 seconds remaining; resume; advance 1 second; refresh GUI            | WHITE displays `02:58`; BLACK still displays `03:00`                  | :x:          |
| TC32: Just before expiration        | Start Blitz countdown; advance 179 seconds; refresh GUI                          | WHITE displays `00:01`                                                | :x:          |
| TC33: Exactly at expiration         | Start Blitz countdown; advance 180 seconds; refresh GUI                          | WHITE displays `00:00`; BLACK displays `03:00`                        | :x:          |
| TC34: Beyond expiration             | Start Blitz countdown; advance 181 seconds; refresh GUI                          | WHITE remains at `00:00`; no negative or invalid display              | :x:          |
| TC35: Untimed game integration      | Start an Untimed game; advance time; refresh GUI                                 | Both labels remain `Untimed`; no countdown refresh timer is activated | :x:          |
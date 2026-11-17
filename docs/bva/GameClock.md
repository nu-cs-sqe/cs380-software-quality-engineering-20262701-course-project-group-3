# BVA Analysis for GameClock
# Author: Sebastian Pacheco

**Scope:** Player timer initialization only. Countdown, switching the active clock, and timeout handling will be implemented in a later feature. Move increments will not be supported.

**Selected presets:**

| Game mode  | Time control | Initial time per player |
|------------|--------------|-------------------------|
| Untimed    | Unlimited    | -1 seconds (sentinel)   |
| Blitz      | 3\|0         | 180 seconds             |
| Rapid      | 10\|0        | 600 seconds             |
| Classical  | 60\|0        | 3,600 seconds           |

### Method under test: `GameClock(int minutesPerPlayer)`

|                               | System under test                         | Expected output                                                                  | Implemented?       |
|-------------------------------|-------------------------------------------|----------------------------------------------------------------------------------|--------------------|
| TC01: Invalid negative time   | Call: `new GameClock(-1)`                 | Throws `IllegalArgumentException`                                                | :white_check_mark: |
| TC02: Untimed boundary        | Call: `new GameClock(0)`                  | Clock is untimed; both players have unlimited time (-1)                          | :white_check_mark: |
| TC03: Smallest positive time  | Call: `new GameClock(1)`                  | Both players start with 60 seconds                                               | :white_check_mark: |
| TC04: Blitz preset            | Call: `new GameClock(3)`                  | Both players start with 180 seconds                                              | :white_check_mark: |
| TC05: Rapid preset            | Call: `new GameClock(10)`                 | Both players start with 600 seconds                                              | :white_check_mark: |
| TC06: Classical preset        | Call: `new GameClock(60)`                 | Both players start with 3,600 seconds                                            | :white_check_mark: |
| TC07: Maximum integer input   | Call: `new GameClock(Integer.MAX_VALUE)`  | Both players start with `(long) Integer.MAX_VALUE * 60` seconds without overflow | :white_check_mark: |

### Method under test: `isTimed()`

|                             | System under test                              | Expected output | Implemented?       |
|-----------------------------|------------------------------------------------|-----------------|--------------------|
| TC08: Untimed game          | Clock: `new GameClock(0)`; call: `isTimed()`   | Returns false   | :white_check_mark: |
| TC09: Minimum positive time | Clock: `new GameClock(1)`; call: `isTimed()`   | Returns true    | :white_check_mark: |
| TC10: Blitz game            | Clock: `new GameClock(3)`; call: `isTimed()`   | Returns true    | :white_check_mark: |
| TC11: Classical game        | Clock: `new GameClock(60)`; call: `isTimed()`  | Returns true    | :white_check_mark: |

### Method under test: `getRemainingSeconds(PieceColor color)`

|                                 | System under test                                                    | Expected output                                  | Implemented?       |
|---------------------------------|----------------------------------------------------------------------|--------------------------------------------------|--------------------|
| TC12: Untimed for both colors   | Clock: `new GameClock(0)`; call with WHITE and BLACK                 | Returns -1 for both                              | :white_check_mark: |
| TC13: Minimum positive time     | Clock: `new GameClock(1)`; call with WHITE and BLACK                 | Returns 60 for both                              | :white_check_mark: |
| TC14: Blitz for both colors     | Clock: `new GameClock(3)`; call with WHITE and BLACK                 | Returns 180 for both                             | :white_check_mark: |
| TC15: Rapid for both colors     | Clock: `new GameClock(10)`; call with WHITE and BLACK                | Returns 600 for both                             | :white_check_mark: |
| TC16: Classical for both colors | Clock: `new GameClock(60)`; call with WHITE and BLACK                | Returns 3,600 for both                           | :white_check_mark: |
| TC17: Maximum integer input     | Clock: `new GameClock(Integer.MAX_VALUE)`; call with WHITE and BLACK | Returns `(long) Integer.MAX_VALUE * 60` for both | :white_check_mark: |
| TC18: Invalid null color        | Clock: `new GameClock(3)`; call: `getRemainingSeconds(null)`         | Throws `IllegalArgumentException`                | :white_check_mark: |

### Method under test: `TimeControl.getMinutesPerPlayer()`
|                        | System under test                      | Expected output | Implemented?       |
|------------------------|----------------------------------------|-----------------|--------------------|
| TC19: Untimed preset   | Call on `TimeControl.UNTIMED`          | Returns 0       | :white_check_mark: |
| TC20: Blitz preset     | Call on `TimeControl.BLITZ_3_MIN`      | Returns 3       | :white_check_mark: |
| TC21: Rapid preset     | Call on `TimeControl.RAPID_10_MIN`     | Returns 10      | :white_check_mark: |
| TC22: Classical preset | Call on `TimeControl.CLASSICAL_60_MIN` | Returns 60      | :white_check_mark: |

### Method under test: `start()`, `pause()`, and `getRemainingSeconds(PieceColor color)`

| Test case                     | System under test                                                            | Expected output                              | Implemented?       |
|-------------------------------|------------------------------------------------------------------------------|----------------------------------------------|--------------------|
| TC23: Countdown not started   | Create `GameClock(3)`; advance 5 seconds without calling `start()`           | WHITE and BLACK both remain at 180 seconds   | :white_check_mark: |
| TC24: Start countdown         | Create `GameClock(3)`; call `start()`; advance 2 seconds                     | WHITE has 178 seconds; BLACK has 180 seconds | :white_check_mark: |
| TC25: Zero elapsed time       | Call `start()`; query remaining time immediately with zero elapsed time      | WHITE remains at 180 seconds                 | :white_check_mark: |
| TC26: Below first second      | Call `start()`; advance 999 milliseconds                                     | WHITE remains at 180 seconds                 | :white_check_mark: |
| TC27: Exactly one second      | Call `start()`; advance 1,000 milliseconds                                   | WHITE has 179 seconds remaining              | :white_check_mark: |
| TC28: Just above one second   | Call `start()`; advance 1,001 milliseconds                                   | WHITE still has 179 seconds remaining        | :white_check_mark: |
| TC29: Just before expiration  | Call `start()`; advance 179,999 milliseconds                                 | WHITE has 1 second remaining                 | :white_check_mark: |
| TC30: Exactly at expiration   | Call `start()`; advance 180,000 milliseconds                                 | WHITE has exactly 0 seconds remaining        | :white_check_mark: |
| TC31: Beyond expiration       | Call `start()`; advance 180,001 milliseconds                                 | WHITE remains at 0 seconds; never negative   | :white_check_mark: |
| TC32: Black timer unchanged   | Call `start()`; advance 181 seconds; query BLACK                             | BLACK still has 180 seconds remaining        | :white_check_mark: |
| TC33: Pause before starting   | Create `GameClock(3)`; call `pause()`; advance 5 seconds                     | WHITE and BLACK remain at 180 seconds        | :white_check_mark: |
| TC34: Pause active countdown  | Call `start()`; advance 1 second; call `pause()`; advance another 10 seconds | WHITE remains at 179 seconds while paused    | :white_check_mark: |
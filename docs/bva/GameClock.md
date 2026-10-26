# BVA Analysis for GameClock

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

|                                 | System under test                                                    | Expected output                                  | Implemented? |
|---------------------------------|----------------------------------------------------------------------|--------------------------------------------------|--------------|
| TC12: Untimed for both colors   | Clock: `new GameClock(0)`; call with WHITE and BLACK                 | Returns -1 for both                              | :x:          |
| TC13: Minimum positive time     | Clock: `new GameClock(1)`; call with WHITE and BLACK                 | Returns 60 for both                              | :x:          |
| TC14: Blitz for both colors     | Clock: `new GameClock(3)`; call with WHITE and BLACK                 | Returns 180 for both                             | :x:          |
| TC15: Rapid for both colors     | Clock: `new GameClock(10)`; call with WHITE and BLACK                | Returns 600 for both                             | :x:          |
| TC16: Classical for both colors | Clock: `new GameClock(60)`; call with WHITE and BLACK                | Returns 3,600 for both                           | :x:          |
| TC17: Maximum integer input     | Clock: `new GameClock(Integer.MAX_VALUE)`; call with WHITE and BLACK | Returns `(long) Integer.MAX_VALUE * 60` for both | :x:          |
| TC18: Invalid null color        | Clock: `new GameClock(3)`; call: `getRemainingSeconds(null)`         | Throws `IllegalArgumentException`                | :x:          |

### Method under test: `TimeControl.getMinutesPerPlayer()`
|                        | System under test                      | Expected output | Implemented? |
|------------------------|----------------------------------------|-----------------|--------------|
| TC19: Untimed preset   | Call on `TimeControl.UNTIMED`          | Returns 0       | :x:          |
| TC20: Blitz preset     | Call on `TimeControl.BLITZ_3_MIN`      | Returns 3       | :x:          |
| TC21: Rapid preset     | Call on `TimeControl.RAPID_10_MIN`     | Returns 10      | :x:          |
| TC22: Classical preset | Call on `TimeControl.CLASSICAL_60_MIN` | Returns 60      | :x:          |
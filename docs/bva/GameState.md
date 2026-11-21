# BVA Analysis for GameState
# Author: Sebastian Pacheco

**Scope:** GameState initialization, player naming, and initial game state. Turn switching, move recording, captures, and game completion will be implemented in later features.

### Method under test: `GameState(String whitePlayerName, String blackPlayerName)` and `getActivePlayerName()`

|                                | System under test                                             | Expected output                           | Implemented?       |
|--------------------------------|---------------------------------------------------------------|-------------------------------------------|--------------------|
| TC01: Minimum nonempty names   | `new GameState("A", "B")`; call `getActivePlayerName()`       | Returns `"A"` (White moves first)         | :white_check_mark: |
| TC02: Typical player names     | `new GameState("Alice", "Bob")`; call `getActivePlayerName()` | Returns `"Alice"`                         | :white_check_mark: |
| TC03: Reversed player names    | `new GameState("Bob", "Alice")`; call `getActivePlayerName()` | Returns `"Bob"` (first argument is White) | :white_check_mark: |

### Initial state verification

These cases concern initialization mentioned by the instructor. They should be tested through observable behavior when the corresponding public functionality becomes available.

|                               | System under test       | Expected output                                          | Implemented?       |
|-------------------------------|-------------------------|----------------------------------------------------------|--------------------|
| TC04: Initial status          | Create a new GameState  | Status is `PLAYING`                                      | :white_check_mark: |
| TC05: Initial move history    | Create a new GameState  | Move history is empty                                    | :white_check_mark: |
| TC06: Initial captured pieces | Create a new GameState  | White and Black have zero captures for every `PieceType` | :white_check_mark: |
# BVA Analysis for Board
**Author**: Raymond Gu

**Scope:** The `Board` class stores chess pieces in an 8×8 grid. It is responsible for initializing the standard starting position and providing access to pieces on specific squares. It should also support constructing empty boards so that individual piece movement can be tested under different board configurations.

### Constructor: `Board(boolean initialize)`

|                                     | System under test        | Expected output                                                                        | Implemented? |
|-------------------------------------|--------------------------|----------------------------------------------------------------------------------------|--------------|
| TC01: Standard board initialization | Call: `new Board(true)`  | Creates an 8×8 board with all 32 pieces in their standard starting positions           | ✅           |
| TC02: Empty board initialization    | Call: `new Board(false)` | Creates an 8×8 board with all 64 squares empty                                         | ✅           |

### Method under test: `setPiece(Location location, Piece piece)`

|                                      | System under test                                         | Expected output                                        | Implemented? |
|--------------------------------------|-----------------------------------------------------------|--------------------------------------------------------|--------------|
| TC01: Place piece in center          | Call: `setPiece(new Location(4, 4), new Pawn(WHITE))`     | Pawn is placed at the specified location               | ✅           |
| TC02: Place piece at corner          | Call: `setPiece(new Location(0, 0), new Rook(WHITE))`     | Rook is placed at the corner square                    | ✅           |
| TC03: Place piece at opposite corner | Call: `setPiece(new Location(7, 7), new Knight(BLACK))`   | Knight is placed at the opposite corner                | ✅           |
| TC04: Replace existing piece         | Call: Place a second piece at a location already occupied | The new piece replaces the previously placed piece     | ✅           |

### Method under test: `getPiece(Location location)`

|                                        | System under test                                                 | Expected output                                 | Implemented? |
|----------------------------------------|-------------------------------------------------------------------|-------------------------------------------------|--------------|
| TC01: Retrieve existing piece          | Call: `getPiece(location)` after placing a piece at that location | Returns the piece occupying that location       | ✅           |
| TC02: Retrieve empty square            | Call: `getPiece(location)` on an unoccupied square                | Returns `null`                                  | ✅           |
| TC03: Retrieve corner piece            | Call: `getPiece(location)` for a piece at `(0, 0)`                | Returns the piece occupying the corner          | ✅           |
| TC04: Retrieve opposite corner piece   | Call: `getPiece(location)` for a piece at `(7, 7)`                | Returns the piece occupying the opposite corner | ✅           |
| TC05: Retrieve piece after replacement | Call: `getPiece(location)` after replacing an existing piece      | Returns the newly placed piece                  | ✅           |

### Method under test: `getSnapshot()`

|                                   | System under test                                                          | Expected output                                                                               | Implemented? |
|-----------------------------------|----------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------|--------------|
| TC01: Snapshot of standard board  | Call: `getSnapshot()` on a standard board                                  | Returns a copy containing the same piece positions as the original board                      | ✅           |
| TC02: Snapshot of empty board     | Call: `getSnapshot()` on an empty board                                    | Returns an 8×8 array containing only `null` values                                            | ✅           |
| TC03: Snapshot independence       | Call: `getSnapshot()` and modify the returned array                        | Original board remains unchanged                                                              | ✅           |
| TC04: Snapshot piece independence | Call: `getSnapshot()` and compare pieces in corresponding occupied squares | Snapshot contains copies of the original pieces rather than references to the original pieces | ✅           |
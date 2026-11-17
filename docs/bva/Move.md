# BVA Analysis for Move
# Author: Sebastian Pacheco

**Scope:** Move representation, move types, optional capture and promotion metadata, and validation of incompatible states. Actual move execution, castling, en passant, and promotion mechanics will be implemented in later features.

### Method under test: `MoveType.values()`

|                              | System under test           | Expected output                             | Implemented?        |
|------------------------------|-----------------------------|---------------------------------------------|---------------------|
| TC01: Supported move types   | Call: `MoveType.values()`   | Contains `NORMAL`, `CASTLE`, `EN_PASSANT`   | :white_check_mark:  |

### Method under test: `Move(...)` and `getMoveType()`

|                       | System under test                                                 | Expected output               | Implemented?       |
|-----------------------|-------------------------------------------------------------------|-------------------------------|--------------------|
| TC02: Normal move     | Construct a move with `MoveType.NORMAL`; call `getMoveType()`     | Returns `MoveType.NORMAL`     | :white_check_mark: |
| TC03: Castling move   | Construct a move with `MoveType.CASTLE`; call `getMoveType()`     | Returns `MoveType.CASTLE`     | :white_check_mark: |
| TC04: En passant move | Construct a move with `MoveType.EN_PASSANT`; call `getMoveType()` | Returns `MoveType.EN_PASSANT` | :white_check_mark: |

### Method under test: `capturedPiece()`

|                           | System under test                                                 | Expected output                                  | Implemented?       |
|---------------------------|-------------------------------------------------------------------|--------------------------------------------------|--------------------|
| TC05: No captured piece   | Normal move without capture; call `capturedPiece()`               | Returns `Optional.empty()`                       | :white_check_mark: |
| TC06: Captured piece      | Normal move capturing an opponent's piece; call `capturedPiece()` | Returns `Optional` containing the captured piece | :white_check_mark: |

### Method under test: `promotion()`

|                       | System under test                                   | Expected output                                 | Implemented?       |
|-----------------------|-----------------------------------------------------|-------------------------------------------------|--------------------|
| TC07: No promotion    | Normal move without promotion; call `promotion()`   | Returns `Optional.empty()`                      | :white_check_mark: |
| TC08: Valid promotion | Pawn move promoting to Queen; call `promotion()`    | Returns `Optional` containing `PieceType.QUEEN` | :white_check_mark: |

### Method under test: `Move(...)`

|                                  | System under test                                    | Expected output                     | Implemented?       |
|----------------------------------|------------------------------------------------------|-------------------------------------|--------------------|
| TC09: Invalid piece promotion    | Construct a King move with promotion to Queen        | Throws `IllegalArgumentException`   | :white_check_mark: |
| TC10: Invalid promotion target   | Construct a Pawn move promoting to King              | Throws `IllegalArgumentException`   | :white_check_mark: |
| TC11: Invalid castling promotion | Construct a castling move with promotion             | Throws `IllegalArgumentException`   | :white_check_mark: |
| TC12: Invalid castling capture   | Construct a castling move with a captured piece      | Throws `IllegalArgumentException`   | :white_check_mark: |
| TC13: Invalid en passant capture | Construct an en passant move without a captured pawn | Throws `IllegalArgumentException`   | :white_check_mark: |
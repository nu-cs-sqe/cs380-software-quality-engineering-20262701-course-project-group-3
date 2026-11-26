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
|                                      | System under test                                         | Expected output                   | Implemented?       |
|--------------------------------------|-----------------------------------------------------------|-----------------------------------|--------------------|
| TC09: Invalid piece promotion        | Construct a King move with promotion to Queen             | Throws `IllegalArgumentException` | :white_check_mark: |
| TC10: Invalid promotion target       | Construct a Pawn move promoting to King                   | Throws `IllegalArgumentException` | :white_check_mark: |
| TC11: Invalid castling promotion     | Construct a castling move with promotion                  | Throws `IllegalArgumentException` | :white_check_mark: |
| TC12: Invalid castling capture       | Construct a castling move with a captured piece           | Throws `IllegalArgumentException` | :white_check_mark: |
| TC13: Invalid en passant capture     | Construct an en passant move without a captured pawn      | Throws `IllegalArgumentException` | :white_check_mark: |
| TC14: En passant captures non-pawn   | Construct an en passant move capturing a Knight           | Throws `IllegalArgumentException` | :white_check_mark: |
| TC15: En passant with non-pawn mover | Construct an en passant move with a King capturing a Pawn | Throws `IllegalArgumentException` | :white_check_mark: |
| TC16: Castling with non-King mover   | Construct a castling move using a Rook                    | Throws `IllegalArgumentException` | :white_check_mark: |
| TC17: En passant with promotion      | Construct an en passant move with Pawn promotion to Queen | Throws `IllegalArgumentException` | :white_check_mark: |
| TC18: Null move type                 | Construct a move with `moveType = null`                   | Throws `IllegalArgumentException` | :white_check_mark: |
| TC19: Pawn promotes to Pawn          | Construct a Pawn move promoting to another Pawn           | Throws `IllegalArgumentException` | :white_check_mark: |
| TC22: Null From Location             | Construct a move with null reference                      | Throws `IllegalArgumentException` | :white_check_mark: |
| TC24: Null To Location               | Construct a move with null reference                      | Throws `IllegalArgumentException` | :white_check_mark: |
| TC24: Null moved piece               | Construct a piece with null reference                     | Throws `IllegalArgumentException` | :white_check_mark: |

### Method under test: `getRookMovement()`

|                               | System under test                                              | Expected output                        | Implemented?       |
|-------------------------------|----------------------------------------------------------------|----------------------------------------|--------------------|
| TC20: Castling Rook movement  | Construct a castling move; call `getRookMovement()`            | Returns the Rook's from/to locations   | :white_check_mark: |

### Method under test: `getCapturedLocation()`

|                                    | System under test                                              | Expected output                              | Implemented?       |
|------------------------------------|----------------------------------------------------------------|----------------------------------------------|--------------------|
| TC21: En passant captured location | Construct an en passant move; call `getCapturedLocation()`     | Returns the captured Pawn's actual location  | :white_check_mark: |

### Method under test: `getFrom()`

|                           | System under test                                           | Expected output                   | Implemented?       |
|---------------------------|-------------------------------------------------------------|-----------------------------------|--------------------|
| TC21: Valid From Location | Construct a move with valid from location; call `getFrom()` | Return Move's from location       | :white_check_mark: |


### Method under test: `getTo()`

|                         | System under test                                       | Expected output                   | Implemented?       |
|-------------------------|---------------------------------------------------------|-----------------------------------|--------------------|
| TC23: Valid To Location | Construct a move with valid to location; call `getTo()` | Return Move's to location         | :white_check_mark: |

### Method under test: `getMovedPiece()`

|                    | System under test                                            | Expected output       | Implemented?       |
|--------------------|--------------------------------------------------------------|-----------------------|--------------------|
| TC25: Moved Pawn   | Construct a move with `Piece.PAWN;` call `getMovedPiece()`   | Return `Piece.PAWN`   | :white_check_mark: |
| TC26: Moved Rook   | Construct a move with `Piece.ROOK;` call `getMovedPiece()`   | Return `Piece.ROOK`   | :white_check_mark: |
| TC27: Moved Knight | Construct a move with `Piece.KNIGHT;` call `getMovedPiece()` | Return `Piece.KNIGHT` | :white_check_mark: |
| TC28: Moved Bishop | Construct a move with `Piece.BISHOP;` call `getMovedPiece()` | Return `Piece.BISHOP` | :white_check_mark: |
| TC29: Moved Queen  | Construct a move with `Piece.QUEEN;` call `getMovedPiece()`  | Return `Piece.QUEEN`  | :white_check_mark: |
| TC30: Moved King   | Construct a move with `Piece.KING;` call `getMovedPiece()`   | Return `Piece.KING`   | :white_check_mark: |

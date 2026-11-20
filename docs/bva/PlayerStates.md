# BVA Analysis for PlayerStates
**Author**: Raymond Gu

**Scope:** The `PlayerStates` class currently stores player states that cannot be derived from only looking at the board. Future features may extend this class and track additional features.

### Constructor: `PlayerState()`

|                                | System under test         | Expected output                                    | Implemented? |
|--------------------------------|---------------------------|----------------------------------------------------|--------------|
| TC01: New player can castle    | Call: `new PlayerState()` | Kingside and queenside eligibility are both `true` | ✅           |

### Method under test: `getCastleKingsideEligible`

|                                      | System under test                                                              | Expected output | Implemented? |
|--------------------------------------|--------------------------------------------------------------------------------|-----------------|--------------|
| TC01: Initial state                  | Call: `new PlayerState().getCastleKingsideEligible()`                          | Returns `true`  | ✅           |
| TC02: After kingside revoked         | Call: `getCastleKingsideEligible()` after `revokeKingsideCastling()`           | Returns `false` | ✅           |
| TC03: Unaffected by queenside revoke | Call: `getCastleKingsideEligible()` after `revokeQueensideCastling()`          | Returns `true`  | ✅           |

### Method under test: `getCastleQueensideEligible`

|                                      | System under test                                                              | Expected output | Implemented? |
|--------------------------------------|--------------------------------------------------------------------------------|-----------------|--------------|
| TC01: Initial state                  | Call: `new PlayerState().getCastleQueensideEligible()`                         | Returns `true`  | ✅           |
| TC02: After queenside revoked        | Call: `getCastleQueensideEligible()` after `revokeQueensideCastling()`         | Returns `false` | ✅           |
| TC03: Unaffected by kingside revoke  | Call: `getCastleQueensideEligible()` after `revokeKingsideCastling()`          | Returns `true`  | ✅           |

### Method under test: `revokeKingsideCastling`

|                                  | System under test                                                        | Expected output                                                | Implemented? |
|----------------------------------|--------------------------------------------------------------------------|----------------------------------------------------------------|--------------|
| TC01: Revoke from eligible       | Call: `revokeKingsideCastling()` on a new `PlayerState`                  | Kingside eligibility is `false`; queenside remains `true`      | ✅           |
| TC02: Revoke already revoked     | Call: `revokeKingsideCastling()` twice                                   | Kingside eligibility stays `false`; no exception thrown        | ❌           |
| TC03: Revoke both sides          | Call: `revokeKingsideCastling()` then `revokeQueensideCastling()`        | Both eligibility flags are `false`                             | ❌           |

### Method under test: `revokeQueensideCastling`

|                                  | System under test                                                        | Expected output                                                | Implemented? |
|----------------------------------|--------------------------------------------------------------------------|----------------------------------------------------------------|--------------|
| TC01: Revoke from eligible       | Call: `revokeQueensideCastling()` on a new `PlayerState`                 | Queenside eligibility is `false`; kingside remains `true`      | ❌ |
| TC02: Revoke already revoked     | Call: `revokeQueensideCastling()` twice                                  | Queenside eligibility stays `false`; no exception thrown       | ❌ |
| TC03: Revoke both sides          | Call: `revokeQueensideCastling()` then `revokeKingsideCastling()`        | Both eligibility flags are `false`                             | ❌ |
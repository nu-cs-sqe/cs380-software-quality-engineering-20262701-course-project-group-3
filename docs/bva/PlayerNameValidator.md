# BVA Analysis for PlayerNameValidator

## Method: `validateLength`

- **TC1: Check on empty name** ( :white_check_mark: )
  - **State of the system**: player 1 name = `""`, player 2 name = `""`
  - **Expected output**: returns error object with messages for both players, "Player name cannot be empty."
- **TC2: Check on one character name** ( :white_check_mark: )
  - **State of the system**: player 1 name = `"a"`, player 2 name = `"b"`
  - **Expected output**: returns success object
- **TC3: Check on max length name (26 characters)** ( :white_check_mark: )
  - **State of the system**: player 1 name = `"abcdefghijklmnopqrstuvwxyz"`, player 2 name = `"zabcdefghijklmnopqrstuvwxy"`
  - **Expected output**: returns success object
- **TC4: Check name length below limit (25 characters)** ( :white_check_mark: )
  - **State of the system**: player 1 name = `"ABCDEFGHIJKLMNOPQRSTUVWXY"`, player 2 name = `"zabcdefghijklmnopqrstuvwx"`
  - **Expected output**: returns success object
- **TC5: Check name length above limit (27 characters)** ( :white_check_mark: )
  - **State of the system**: player 1 name = `"ABCDEFGHIJKLMNOPQRSTUVWXYZA"`, player 2 name = `"zabcdefghijklmnopqrstuvwxyz"`
  - **Expected output**: returns error object with messages for both players, "Player name cannot contain more than 26 characters."
- **TC6: Check name length above limit (player 1)** ( :white_check_mark: )
  - **State of the system**: player 1 name = `"ABCDEFGHIJKLMNOPQRSTUVWXYZA"`, player 2 name = `"abcdefghijklmnopqrstuvwxyz"`
  - **Expected output**: returns error object with message for player 1, "Player name cannot contain more than 26 characters."

## Method: `validateCharacters`

- **TC7: Check on names with one symbol character** ( :white_check_mark: )
  - **State of the system**: player 1 name = `"abc!"`, player 2 name = `"def#"`
  - **Expected output**: returns error object with messages for both players, "Player name must only contain alphanumeric characters."
- **TC8: Check on name with symbol characters (player 1)** ( :white_check_mark: )
  - **State of the system**: player 1 name = `"helloworld123$%^()"`, player 2 name = `"worldhello456"`
  - **Expected output**: returns error object with message for player 1, "Player name must only contain alphanumeric characters."
- **TC9: Check on names with special characters (player 2)** ( :white_check_mark: )
  - **State of the system**: player 1 name = `"helloworld123"`, player 2 name = `"ŵôřłďħëļľø¢∞§¡™£“‘"`
  - **Expected output**: returns error object with message for player 2, "Player name must only contain alphanumeric characters."
- **TC10: Check on names containing the first and last valid character of each range** ( :x: )
  - **State of the system**: player 1 name = `"09AZaz"`, player 2 name = `"z0A9Za"`
  - **Expected output**: returns success object
- **TC11: Check on names with characters adjacent to digits (`/` and `:`)** ( :x: )
  - **State of the system**: player 1 name = `"abc/"`, player 2 name = `"abc:"`
  - **Expected output**: returns error object with messages for both players, "Player name must only contain alphanumeric characters."
- **TC12: Check on names with characters adjacent to uppercase letters (`@` and `[`)** ( :x: )
  - **State of the system**: player 1 name = `"abc@"`, player 2 name = `"abc["`
  - **Expected output**: returns error object with messages for both players, "Player name must only contain alphanumeric characters."
- **TC13: Check on names with characters adjacent to lowercase letters (`` ` `` and `{`)** ( :x: )
  - **State of the system**: player 1 name = `` "abc`" ``, player 2 name = `"abc{"`
  - **Expected output**: returns error object with messages for both players, "Player name must only contain alphanumeric characters."
- **TC14: Check on name with underscore (player 1)** ( :x: )
  - **State of the system**: player 1 name = `"abc_def"`, player 2 name = `"worldhello456"`
  - **Expected output**: returns error object with message for player 1, "Player name must only contain alphanumeric characters."
- **TC15: Check on names with internal whitespace (player 1)** ( :x: )
  - **State of the system**: player 1 name = `"hello world 123"`, player 2 name = `"worldhello456"`
  - **Expected output**: returns error object with message for player 1, "Player name must only contain alphanumeric characters."
- **TC16: Check on names with leading/trailing whitespace (player 2)** ( :x: )
  - **State of the system**: player 1 name = `"helloworld123"`, player 2 name = `" worldhello456 "`
  - **Expected output**: player names are not trimmed, returns error object with message for player 2, "Player name must only contain alphanumeric characters."
- **TC17: Check on names with only one whitespace (player 1)** ( :x: )
  - **State of the system**: player 1 name = `" "`, player 2 name = `"worldhello456"`
  - **Expected output**: player names are not trimmed, returns error object with message for player 1, "Player name must only contain alphanumeric characters."

## Method: `validateDuplicate`

- **TC18: Check on duplicate names** ( :x: )
  - **State of the system**: player 1 name = `"helloworld123"`, player 2 name = `"helloworld123"`
  - **Expected output**: returns error object with message for both players, "Player names cannot match."
- **TC19: Check on duplicate names (mixed case)** ( :x: )
  - **State of the system**: player 1 name = `"HelloWorld123"`, player 2 name = `"helloworld123"`
  - **Expected output**: returns error object with message for both players, "Player names cannot match."
- **TC20: Check on unique names** ( :x: )
  - **State of the system**: player 1 name = `"helloworld123"`, player 2 name = `"worldhello456"`
  - **Expected output**: returns success object
- **TC21: Check on unique names (near duplicate)** ( :x: )
  - **State of the system**: player 1 name = `"helloworld123"`, player 2 name = `"helloworld124"`
  - **Expected output**: returns success object
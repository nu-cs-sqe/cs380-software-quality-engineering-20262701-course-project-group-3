# Instructor Code Review Feedback

**Contact**: Dr. Yiji Zhang (yiji.zhang@northwestern.edu)

**Purpose of This Document**:
The instructor will perform code review with respect to software design, error handling, format and style on the main branch every week starting Week 6 using the letter grade A standards.
The following chapters of the textbook are considered: Chapter 1, 2, 3, 4, 5, 6, 7, 9, and 10. The corresponding lectures are considered, too.

## Week 7 Code Review

1. GameState class:
You have
```
private final String player1Name; // WHITE
private final String player2Name; // BLACK
```
right now, and I suggest the following:
Option 1, if you are comfortable with it:
```
private final String whitePlayerName;
private final String blackPlayerName;
```
Option 2,
```
private final String firstPlayerName;   // moves first (White)
private final String secondPlayerName;
```
2. Be carefuly not violating TDD. Your GameState class now has no tests and has code like
```
    private Progress progress = Progress.PLAYING;

    private final List<Move> moveHistory = new ArrayList<Move>();

    private final Map<PieceType, Integer> player1CapturedPieces = new HashMap<>();
    private final Map<PieceType, Integer> player2CapturedPieces = new HashMap<>();
```
and
```
        for (PieceType type : PieceType.values()) {
            player1CapturedPieces.put(type, 0);
            player2CapturedPieces.put(type, 0);
        }
```
The fact that this code is pushed into main without tests shows a violation of TDD. Please document in your README, under "Exceptions -- Others" this violation and attach a link to this code review feedback, so this violation won't be count against you during the final assessment of your project. Please make sure to not violate TDD for future development.

3. In Board class, you have
```
        if (color == null) 
```
Uncle Bob argues that we don't want to pollute our code with null checks. Instead, we should make sure to not pass null around. 

Similarly, in your Move class, your comment says
```
    private final Piece capturedPiece;   // null if no capture
    private final PieceType promotion;   // null unless a pawn promoted
```
which directly contradicts with what Uncle Bob talks about in the book. Moreover, the nulls hint at a bigger problem -- you are using this one class  to represent several different kinds of moves. And nothing stops castle and enPassant both being true, or a promotion on a king move. En passant also captures a pawn on a square other than to, and castling moves a rook, and this class has no way to record either. To revise this, one way is to make an enum for the type of move, and hide the null value from any callers of the methods in this class by using Optional, like adding these:

```
public enum MoveType { NORMAL, CASTLE, EN_PASSANT }

public Optional<Piece> capturedPiece()  { return Optional.ofNullable(capturedPiece); }
public Optional<PieceType> promotion()  { return Optional.ofNullable(promotion); }

4. Otherwise, code looks good to me!
```
## Week 6 Code Review
I have read every line of production code currently in the main branch and I don't find any violations of the coding standards required by letter grade A! Good job!

The only incident I hesitated on is whether Board#initializeBoard() should be broken into a couple of smaller methods. But I think I can argue for either design in this case.

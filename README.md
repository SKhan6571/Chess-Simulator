# Chess Simulator

### Description

Final project for CSCI 4448 - Object-Oriented Analysis and Design (Fall 2025) by Joel Henry, Ryan Mosier, and Silas
Khan. This project simulates a game of chess between two players on one local computer. The project implements all
required rules for chess, and also implements features for enhanced usability.

### Software Requirements

1. This project requires Java for compilation and execution
2. Supports Java version 11 and up
3. Enter the command 'java --version' (without the quotation marks) in a terminal to verify your Java version and
   installation

### How to Use it

1. Download the 'Chess-Simulator-ASCII-Implementation' source code under the 'Releases' tab
    2. You may download the source code as either a 'zip' or 'tar.gz' file
3. Once downloaded, extract the source code for the chosen file format
4. Then navigate to the project directory in a terminal
    5. Directory should have this format: ../Chess-Simulator-1.0.0/Chess-Simulator-1.0.0/src/main/java
6. Enter 'javac *.java' in the terminal (without the quotation marks)
7. Enter 'java Chess.java' (without the quotation marks)
8. Enjoy!
9. You may now play the game at any time by typing 'java Chess.java' in the project directory

### Design Patterns Used

-------

#### Singleton Pattern

The singleton pattern is used by the `Game` class to ensure that only one instance of the game state exists throughout
the application's lifecycle.

##### Implementation

This pattern is implemented in the `Game` class via a private constructor, a private static singleton instance, and a
public static `getInstance()` method.
The constructor initializes the default board state and sets the `moveCount` to 1, and the `getInstance()` method uses
lazy instantiation to create the instance only
when first requested.

##### Purpose

This pattern is used to centralize the game state, which manages the turn count, board reference, and move history.
This allows the game state to be easily accessed by the main driver and command objects.

-------

#### Observer Pattern

The observer pattern is used to decouple the `Board` (Subject) from the display logic (Observer)

##### Implementation

##### Subject:

The `Board` class maintains a list of `GameObserver` objects and calls `notifyObservers()` whenever a move changes the
board state.

##### Observer:

The `ConsoleGameObserver` class implements the `GameObserver` interface. When notified, it triggers the `BoardPrinter`
class to reprint the console output.

##### Purpose

This pattern allows the UI to automatically update whenever the internal data changes without the `Board` class needing
to know how to print to the console.

-------

#### Command Pattern

The command pattern is used to encapsulate game moves as objects, enabling the 'Undo' functionality.

##### Implementation

The `Command` interface defines `execute()` and `undo()` methods. The `MoveCommand` class then implements this
interface,
storing the state required to perform an action (start tile, end tile) and the state required to reverse it (the
captured piece, if any).

##### Purpose

This pattern allows the `Game` class to act as the invoker and manage a `Stack<Command>` history.
When `undoLastMove()` is called, it pops the stack and reverses the action.

-------

#### Template Method Pattern

The template method pattern is used in the `Piece` hierarchy to define the skeleton of move calculation while letting
subclasses
define specific behaviors.

##### Implementation

The abstract `Piece` class contains the `getPossibleMoves` method (the template).
This method relies on abstract methods `getMoveDirections()` and `isSliding()`, which are implemented by concrete
classes like `Rook` or `Bishop`.

##### Purpose

This pattern allows the logic for iterating through the board and checking boundaries to be written once in the parent
`Piece` class.
Specific distinct movement rules (e.g., diagonal vs. straight) are handled by the child classes.

-------

#### Simple Factory Pattern

The `PieceFactory` class uses the simple factory pattern to encapsulate the logic for creating chess pieces.
Note that this is a simple factory because a static method is used to generate objects based on input criteria.

##### Implementation

The public static `createPiece` method takes a coordinate (rank and file) and decides which object to instantiate (e.g.,
if rank is 1, return a white pawn; if rank 0 and file 0, return a white rook).

##### Purpose

The `Board` class delegates piece creation to the factory, meaning the `Board` doesn't need to know the specific
arrangement of pieces.

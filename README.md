# Java Quiz Application

A Java-based Quiz Application developed using **Object-Oriented
Programming (OOP)** principles, **Java Swing**, and **MySQL** database
integration through **JDBC**.

The application allows users to register and log in, attempt quizzes
with different difficulty levels, track scores across rounds, view
leaderboards, and access performance reports. Administrative
functionality is also provided for managing quiz questions and viewing
statistical reports.

## Features

### User Features

-   User registration
-   User login and authentication
-   Quiz participation
-   Multiple quiz rounds
-   Questions organized by difficulty level
-   Randomized/shuffled questions
-   Score calculation
-   View leaderboard
-   View performance information

### Admin Features

-   Administrator login
-   Manage quiz questions
-   Search players by ID
-   Delete player records
-   View statistical reports
-   View player performance summaries
-   View leaderboard information
-   Terminal-based administrative reports

## Technology Stack

  Technology       Purpose
  ---------------- -------------------------------------
  Java             Core application development
  Java Swing       Graphical User Interface
  Window Builder   GUI design
  MySQL            Database management
  JDBC             Java-to-MySQL database connectivity
  Eclipse IDE      Development and testing

## System Architecture

The application follows an object-oriented structure with model, DAO,
service, and UI components.

``` text
                 +----------------------+
                 |      Java Swing UI   |
                 | Login / Home / Quiz  |
                 | Leaderboard / Reports|
                 +----------+-----------+
                            |
                            v
                 +----------------------+
                 |   Service / Logic    |
                 | Quiz & Score Logic   |
                 +----------+-----------+
                            |
                            v
                 +----------------------+
                 |      DAO Layer       |
                 | PlayerDAO            |
                 | QuestionDAO          |
                 | ReportDAO            |
                 +----------+-----------+
                            |
                         JDBC
                            |
                            v
                 +----------------------+
                 |      MySQL DB        |
                 | quizdb               |
                 +----------------------+
```

## Main Classes

### Model Classes

-   `Player` -- stores player information such as player ID, name,
    username, password, and additional descriptive information.
-   `Question` -- represents quiz questions and their difficulty
    information.
-   `RoundScore` -- stores scores for individual quiz rounds.

### DAO Classes

-   `PlayerDAO` -- handles player-related database operations.
-   `QuestionDAO` -- handles quiz question database operations.
-   `ReportDAO` -- supports report and performance-related database
    operations.

### UI Classes

-   `LoginFrame`
-   `HomeFrame`
-   `QuizFrame`
-   `LeaderboardFrame`
-   `ReportsFrame`

The application uses DAO classes to separate database operations from
the user interface and model classes.

## Database

The MySQL database is named:

``` text
quizdb
```

### Main Tables

``` text
players
questions
round_scores
results
admins
```

The database stores player information, questions, round scores, final
quiz results, and administrator login details.

Database operations such as **INSERT, UPDATE, DELETE, and SELECT** are
performed through DAO classes using prepared statements.

## Quiz Workflow

``` text
Start Application
       |
       v
   User Login
       |
       +---- New User ----> Registration
       |                       |
       |                       v
       +-------------------- Login
                               |
                               v
                         Start Quiz
                               |
                               v
                     Load Questions
                               |
                               v
                     Answer Questions
                               |
                               v
                       Calculate Score
                               |
                               v
                     Store Round Score
                               |
                               v
                    Calculate Total Score
                               |
                               v
                 Leaderboard / Reports
```

## Reports

The application generates different types of reports, including:

-   Individual player performance summaries
-   Leaderboards
-   Total number of players
-   Total quiz attempts
-   Average score
-   Highest score

Reports can be presented through the Java Swing interface and through
terminal-based administrative functionality.

## Error Handling

The application includes error handling for:

-   Invalid numeric input
-   Invalid login credentials
-   JDBC/database errors
-   Other invalid user inputs

`try-catch` blocks are used around JDBC operations to reduce the risk of
application crashes, while user-friendly error messages are displayed
through dialog boxes or terminal output.

## Testing

Testing was performed using different user and administrator scenarios.

  Test Case                        Expected Result
  -------------------------------- ----------------------------------
  Login with valid credentials     Login successful
  Login with invalid credentials   Error message displayed
  Register new player              Player added to database
  Start quiz                       Questions loaded and displayed
  View leaderboard                 Scores displayed in sorted order
  Search player by ID              Player details displayed
  Delete player                    Player removed from database

Basic unit-level testing was also performed for login and registration,
score calculation, and database insertion/retrieval operations.

## Project Structure

A typical project structure is:

``` text
QuizApplication/
├── src/
│   └── ...
├── database/
│   └── quizdb.sql
├── README.md
└── ...
```

> The exact package and folder structure may vary depending on the
> Eclipse project configuration.

## Requirements

Before running the application, ensure that you have:

-   Java Development Kit (JDK)
-   Eclipse IDE or another Java IDE
-   MySQL Server
-   MySQL Connector/J JDBC driver
-   A configured `quizdb` database

## Setup

1.  Clone the repository.

``` bash
git clone <your-repository-url>
```

2.  Open the project in Eclipse.

3.  Create the MySQL database:

``` sql
CREATE DATABASE quizdb;
```

4.  Create/import the required tables:

    -   `players`
    -   `questions`
    -   `round_scores`
    -   `results`
    -   `admins`

5.  Configure the JDBC connection in the project with your MySQL
    username, password, host, and database name.

6.  Add the MySQL Connector/J dependency to the project.

7.  Run the application from the appropriate Java entry point.

## Security Note

The current coursework implementation has a known security limitation:
passwords are stored in plain text. For a production-ready system,
password hashing and stronger authentication practices should be
implemented.

## Known Limitations

-   Passwords are currently stored in plain text.
-   The user interface focuses primarily on functionality rather than
    advanced visual design.
-   The system currently supports a fixed number of quiz rounds.
-   Quiz configuration is not fully dynamic.

## Future Improvements

Possible future improvements include:

-   Password hashing and improved authentication security
-   Improved and modernized GUI design
-   Dynamic quiz configuration
-   More flexible quiz round management
-   Additional reporting and analytics features

## Documentation

The project documentation includes:

-   Object-oriented design and implementation details
-   MySQL and JDBC integration
-   Javadoc comments
-   Class diagram
-   Test cases
-   Status report
-   Known bugs and limitations

## Course

**Object Oriented Design and Programming (5CS019)**

## License

This project was developed as an academic coursework project.

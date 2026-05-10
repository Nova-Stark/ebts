# ETBS - Event Ticket Booking System

A modern, Java-based Event Ticket Booking System featuring a polymorphic domain model, CSV-based persistence, and a clean JavaFX graphical user interface.

## Features
- **Modern GUI**: Clean and aesthetic user interface built with JavaFX.
- **Polymorphic Events**: Supports different event types including Movies, Tours, and Concerts using OOP inheritance.
- **CSV Persistence**: All data for events and bookings is stored in flat CSV files for easy portability.
- **Seat Management**: Real-time tracking of seat availability with automatic updates to the data store.
- **OOP Principles**: Demonstrates key concepts including Inheritance, Composition, Aggregation, and Encapsulation.

## Prerequisites
- Java Development Kit (JDK) 11 or higher.
- JavaFX SDK (expected at `C:\javafx-sdk`).

## Project Structure
- `src/etbs/`: Core domain models and data management logic.
- `src/gui/`: Presentation layer and JavaFX application.
- `events.csv`: The database for available events.
- `bookings.csv`: Persistent log of all successful bookings.

## Compilation and Execution

### 1. Compile the Program
Run the following command from the project root to compile all source files:

```powershell
javac --module-path "C:\javafx-sdk\lib" --add-modules javafx.controls,javafx.fxml -d out src/etbs/*.java src/gui/*.java
```

### 2. Run the GUI Application
To launch the modern graphical interface:

```powershell
java --module-path "C:\javafx-sdk\lib" --add-modules javafx.controls,javafx.fxml -cp out gui.BookingUI
```

### 3. Run the CLI Application (Legacy)
To run the original console-based version:

```powershell
java -cp out etbs.BookingApplication
```

## Data Management
- To add new events, edit `events.csv` following the header format: `id,type,name,date,venue,seats,price`.
- Bookings are appended to `bookings.csv` in real-time.

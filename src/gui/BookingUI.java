package gui;

import etbs.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class BookingUI extends Application {
    private EventCSVManager eventManager;
    private BookingCSVManager bookingManager;
    private List<Event> events;
    private ListView<String> bookedListView;
    private ListView<String> eventListView;
    private List<String> rawBookings = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        eventManager = new EventCSVManager("events.csv");
        bookingManager = new BookingCSVManager("bookings.csv");
        events = eventManager.loadEvents();

        primaryStage.setTitle("ETBS - Event Ticket Booking System");

        TabPane tabPane = new TabPane();
        tabPane.getStyleClass().add("main-tab-pane");

        Tab bookTab = new Tab("Book Tickets");
        bookTab.setClosable(false);
        VBox bookRoot = new VBox(25);
        bookRoot.setPadding(new Insets(40));
        bookRoot.getStyleClass().add("root-pane");

        Label titleLabel = new Label("ETBS Booking Portal");
        titleLabel.getStyleClass().add("title-label");

        // Event List Section
        VBox eventSection = new VBox(10);
        eventSection.getStyleClass().add("card");
        Label eventListTitle = new Label("Available Events");
        eventListTitle.getStyleClass().add("section-title");
        
        eventListView = new ListView<>();
        setupEventListView();
        refreshEventList();
        eventListView.getStyleClass().add("event-list");
        eventSection.getChildren().addAll(eventListTitle, eventListView);

        // Form Section
        VBox formSection = new VBox(15);
        formSection.getStyleClass().add("card");
        Label formTitle = new Label("Booking Information");
        formTitle.getStyleClass().add("section-title");

        GridPane formPane = new GridPane();
        formPane.setHgap(20);
        formPane.setVgap(15);
        formPane.setAlignment(Pos.CENTER_LEFT);

        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");
        nameField.getStyleClass().add("text-field");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Enter your phone");
        phoneField.getStyleClass().add("text-field");

        TextField ageField = new TextField();
        ageField.setPromptText("Enter your age");
        ageField.getStyleClass().add("text-field");

        TextField quantityField = new TextField();
        quantityField.setPromptText("Number of tickets");
        quantityField.getStyleClass().add("text-field");

        formPane.add(new Label("Full Name"), 0, 0);
        formPane.add(nameField, 1, 0);
        formPane.add(new Label("Phone Number"), 0, 1);
        formPane.add(phoneField, 1, 1);
        formPane.add(new Label("Age"), 0, 2);
        formPane.add(ageField, 1, 2);
        formPane.add(new Label("Ticket Qty"), 0, 3);
        formPane.add(quantityField, 1, 3);

        Button bookButton = new Button("Confirm Booking");
        bookButton.getStyleClass().add("book-button");
        bookButton.setMaxWidth(Double.MAX_VALUE);

        bookButton.setOnAction(e -> {
            int selectedIdx = eventListView.getSelectionModel().getSelectedIndex();
            if (selectedIdx < 0) {
                showAlert(Alert.AlertType.ERROR, "Selection Required", "Please select an event from the list.");
                return;
            }
            try {
                String name = nameField.getText();
                if (name == null || name.trim().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter your name.");
                    return;
                }
                
                long phone = Long.parseLong(phoneField.getText());
                int age = Integer.parseInt(ageField.getText());
                int quantity = Integer.parseInt(quantityField.getText());

                Event selectedEvent = events.get(selectedIdx);
                if (selectedEvent.getSeats() < quantity) {
                    showAlert(Alert.AlertType.WARNING, "Availability Error", "Not enough seats available.");
                    return;
                }

                User user = new User(name, phone, age);
                String bookingId = String.valueOf(System.currentTimeMillis() % 100000);
                double totalPrice = selectedEvent.getPrice() * quantity;
                Booking booking = new Booking(bookingId, user, selectedEvent, quantity, totalPrice);

                booking.bookTicket();
                bookingManager.addBooking(bookingId, user.getName(), selectedEvent.getEventId(), quantity, totalPrice);
                eventManager.saveEvents(events);

                refreshEventList();
                refreshBookedList();

                showAlert(Alert.AlertType.INFORMATION, "Booking Success", "Ticket booked successfully!\nID: " + bookingId + "\nTotal: $" + totalPrice);
                
                nameField.clear(); 
                phoneField.clear(); 
                ageField.clear(); 
                quantityField.clear();

            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter valid numeric values for phone, age, and quantity.");
            }
        });

        formSection.getChildren().addAll(formTitle, formPane, bookButton);
        bookRoot.getChildren().addAll(titleLabel, eventSection, formSection);
        bookTab.setContent(bookRoot);

        Tab bookedTab = new Tab("My Bookings");
        bookedTab.setClosable(false);
        VBox bookedRoot = new VBox(25);
        bookedRoot.setPadding(new Insets(40));
        bookedRoot.getStyleClass().add("root-pane");

        Label bookedTitle = new Label("Manage Bookings");
        bookedTitle.getStyleClass().add("title-label");

        VBox historySection = new VBox(15);
        historySection.getStyleClass().add("card");
        Label historyTitle = new Label("Active Bookings");
        historyTitle.getStyleClass().add("section-title");

        bookedListView = new ListView<>();
        refreshBookedList();
        bookedListView.getStyleClass().add("event-list");

        Button cancelButton = new Button("Cancel Selection");
        cancelButton.getStyleClass().add("cancel-button");
        cancelButton.setMaxWidth(Double.MAX_VALUE);

        cancelButton.setOnAction(e -> {
            int selectedIdx = bookedListView.getSelectionModel().getSelectedIndex();
            if (selectedIdx < 0) {
                showAlert(Alert.AlertType.ERROR, "Selection Required", "Please select a booking to cancel.");
                return;
            }
            
            String rawCSV = rawBookings.get(selectedIdx);
            String[] parts = rawCSV.split(",");
            String bookingId = parts[0];
            int eventId = Integer.parseInt(parts[2]);
            int quantity = Integer.parseInt(parts[3]);

            bookingManager.removeBooking(bookingId);

            for (Event ev : events) {
                if (ev.getEventId() == eventId) {
                    ev.increaseSeats(quantity);
                    break;
                }
            }
            eventManager.saveEvents(events);

            refreshEventList();
            refreshBookedList();
            showAlert(Alert.AlertType.INFORMATION, "Cancelled", "Booking " + bookingId + " has been cancelled.");
        });

        historySection.getChildren().addAll(historyTitle, bookedListView, cancelButton);
        bookedRoot.getChildren().addAll(bookedTitle, historySection);
        bookedTab.setContent(bookedRoot);

        tabPane.getTabs().addAll(bookTab, bookedTab);

        Scene scene = new Scene(tabPane, 700, 850);
        
        File cssFile = new File("src/gui/styles.css");
        if (cssFile.exists()) {
            scene.getStylesheets().add(cssFile.toURI().toString());
        }
        
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void setupEventListView() {
        eventListView.setCellFactory(lv -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    getStyleClass().removeAll("movie-item", "concert-item", "tour-item");
                } else {
                    setText(item);
                    getStyleClass().removeAll("movie-item", "concert-item", "tour-item");
                    if (item.contains("[MOVIE]")) {
                        getStyleClass().add("movie-item");
                    } else if (item.contains("[CONCERT]")) {
                        getStyleClass().add("concert-item");
                    } else if (item.contains("[TOUR]")) {
                        getStyleClass().add("tour-item");
                    }
                }
            }
        });
    }

    private void refreshEventList() {
        events = eventManager.loadEvents();
        eventListView.getItems().clear();
        for (Event e : events) {
            eventListView.getItems().add(formatEventString(e));
        }
    }

    private void refreshBookedList() {
        rawBookings = bookingManager.loadBookings();
        bookedListView.getItems().clear();
        for (String b : rawBookings) {
            String[] parts = b.split(",");
            if (parts.length >= 5) {
                String display = String.format("Booking ID: %s\nUser: %s | Event: %s | Qty: %s\nTotal: $%s", 
                        parts[0], parts[1], parts[2], parts[3], parts[4]);
                bookedListView.getItems().add(display);
            }
        }
    }

    private String formatEventString(Event e) {
        return String.format("[%s] %s\nVenue: %s | Available: %d | Price: $%.2f", 
                e.getType().toUpperCase(), e.getEventName(), e.getVenue(), e.getSeats(), e.getPrice());
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

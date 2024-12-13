import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Left side (Bus Ticket Booking System)
        VBox leftPane = new VBox(20);
        leftPane.setAlignment(Pos.CENTER);
        leftPane.setStyle("-fx-background-color: linear-gradient(to bottom, #4A5859, #769E84);");
        leftPane.setPadding(new Insets(20));

        Image busIcon = new Image("bus.png"); // Replace with an actual image path
        ImageView busIconView = new ImageView(busIcon);
        busIconView.setFitWidth(100);
        busIconView.setFitHeight(100);

        Text title = new Text("Bus Ticket Booking System");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.setStyle("-fx-fill: white;");

        leftPane.getChildren().addAll(busIconView, title);

        // Right side (Login Form)
        GridPane rightPane = new GridPane();
        rightPane.setAlignment(Pos.CENTER);
        rightPane.setPadding(new Insets(20));
        rightPane.setHgap(10);
        rightPane.setVgap(10);

        Text signInText = new Text("Sign In");
        signInText.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #5CB85C; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 20px;");

        rightPane.add(signInText, 0, 0, 2, 1);
        rightPane.add(usernameField, 0, 1, 2, 1);
        rightPane.add(passwordField, 0, 2, 2, 1);
        rightPane.add(loginButton, 0, 3, 2, 1);

        GridPane.setMargin(signInText, new Insets(0, 0, 20, 0));
        GridPane.setMargin(loginButton, new Insets(20, 0, 0, 0));

        // Combine both sides
        BorderPane root = new BorderPane();
        root.setLeft(leftPane);
        root.setCenter(rightPane);

        Scene loginScene = new Scene(root, 800, 400);

        // Dashboard Scene
        BorderPane dashboardRoot = createDashboard(primaryStage);
        Scene dashboardScene = new Scene(dashboardRoot, 800, 400);

        // Login Button Action
        loginButton.setOnAction(e -> primaryStage.setScene(dashboardScene));

        primaryStage.setTitle("Bus Ticket Booking System");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private BorderPane createDashboard(Stage primaryStage) {
        // Left navigation
        VBox navPane = new VBox(20);
        navPane.setAlignment(Pos.TOP_CENTER);
        navPane.setStyle("-fx-background-color: linear-gradient(to bottom, #4A5859, #769E84);");
        navPane.setPadding(new Insets(20));

        Text welcomeText = new Text("Welcome,\nadmin");
        welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        welcomeText.setStyle("-fx-fill: white;");

        Button dashboardButton = new Button("Dashboard");
        Button busesButton = new Button("Available Buses");
        Button ticketsButton = new Button("Booking Ticket");
        Button customersButton = new Button("Customers");

        dashboardButton.setStyle("-fx-background-color: #E57373; -fx-text-fill: white;");
        navPane.getChildren().addAll(welcomeText, dashboardButton, busesButton, ticketsButton, customersButton);

        // Top statistics bar
        HBox statsBar = new HBox(10);
        statsBar.setPadding(new Insets(10));
        statsBar.setAlignment(Pos.CENTER);

        VBox availableBusesBox = createStatBox("4", "Available Buses", "#D32F2F");
        VBox todaysIncomeBox = createStatBox("$0.0", "Today's Income", "#1976D2");
        VBox totalIncomeBox = createStatBox("$3000.0", "Total Income", "#7B1FA2");

        statsBar.getChildren().addAll(availableBusesBox, todaysIncomeBox, totalIncomeBox);

        // Income chart
        LineChart<Number, Number> incomeChart = createIncomeChart();

        VBox mainContent = new VBox(10, statsBar, incomeChart);
        mainContent.setPadding(new Insets(10));

        BorderPane dashboardRoot = new BorderPane();
        dashboardRoot.setLeft(navPane);
        dashboardRoot.setCenter(mainContent);

        busesButton.setOnAction(e -> primaryStage.setScene(createAvailableBusesScene(primaryStage)));

        return dashboardRoot;
    }

    private Scene createAvailableBusesScene(Stage primaryStage) {
        BorderPane availableBusesRoot = new BorderPane();

        // Navigation Pane
        VBox navPane = new VBox(20);
        navPane.setAlignment(Pos.TOP_CENTER);
        navPane.setStyle("-fx-background-color: linear-gradient(to bottom, #4A5859, #769E84);");
        navPane.setPadding(new Insets(20));

        Text welcomeText = new Text("Welcome,\nadmin");
        welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        welcomeText.setStyle("-fx-fill: white;");

        Button dashboardButton = new Button("Dashboard");
        Button busesButton = new Button("Available Buses");
        Button ticketsButton = new Button("Booking Ticket");
        Button customersButton = new Button("Customers");

        navPane.getChildren().addAll(welcomeText, dashboardButton, busesButton, ticketsButton, customersButton);

        dashboardButton.setOnAction(e -> primaryStage.setScene(new Scene(createDashboard(primaryStage), 800, 400)));

        // Form and Table
        GridPane formPane = new GridPane();
        formPane.setPadding(new Insets(10));
        formPane.setHgap(10);
        formPane.setVgap(10);

        TextField busIdField = new TextField();
        busIdField.setPromptText("Bus ID");

        TextField locationField = new TextField();
        locationField.setPromptText("Location");

        ComboBox<String> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("Available", "Not Available");
        statusComboBox.setPromptText("Status");

        TextField priceField = new TextField();
        priceField.setPromptText("Price");

        DatePicker datePicker = new DatePicker();

        Button addButton = new Button("Add");
        Button updateButton = new Button("Update");
        Button resetButton = new Button("Reset");
        Button deleteButton = new Button("Delete");

        formPane.add(new Label("Bus ID:"), 0, 0);
        formPane.add(busIdField, 1, 0);
        formPane.add(new Label("Location:"), 0, 1);
        formPane.add(locationField, 1, 1);
        formPane.add(new Label("Status:"), 0, 2);
        formPane.add(statusComboBox, 1, 2);
        formPane.add(new Label("Price:"), 0, 3);
        formPane.add(priceField, 1, 3);
        formPane.add(new Label("Date:"), 0, 4);
        formPane.add(datePicker, 1, 4);

        HBox buttonsBox = new HBox(10, addButton, updateButton, resetButton, deleteButton);
        formPane.add(buttonsBox, 0, 5, 2, 1);

        TableView<String> busesTable = new TableView<>();
        busesTable.setPlaceholder(new Label("No content in table"));

        VBox formAndTableBox = new VBox(10, formPane, busesTable);
        formAndTableBox.setPadding(new Insets(10));

        availableBusesRoot.setLeft(navPane);
        availableBusesRoot.setCenter(formAndTableBox);

        return new Scene(availableBusesRoot, 800, 400);
    }

    private VBox createStatBox(String value, String label, String color) {
        VBox box = new VBox(5);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: " + color + "; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        Text valueText = new Text(value);
        valueText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        valueText.setStyle("-fx-fill: white;");

        Text labelText = new Text(label);
        labelText.setFont(Font.font("Arial", 14));
        labelText.setStyle("-fx-fill: white;");

        box.getChildren().addAll(valueText, labelText);
        return box;
    }

    private LineChart<Number, Number> createIncomeChart() {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Date");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Income");

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Income Chart");

        XYChart.Series<Number, Number> dataSeries = new XYChart.Series<>();
        dataSeries.getData().add(new XYChart.Data<>(1, 1750));
        dataSeries.getData().add(new XYChart.Data<>(2, 1250));
        dataSeries.getData().add(new XYChart.Data<>(3, 500));
        dataSeries.getData().add(new XYChart.Data<>(4, 3000));

        lineChart.getData().add(dataSeries);
        return lineChart;
    }

    public static void main(String[] args) {
        launch(args);
}
}
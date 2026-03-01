package ua.opnu.list;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Comparator;

public class SortingList extends Application {

    private ObservableList<Student1> students;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Список студентів");

        students = populateList();

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(5));
        vbox.setAlignment(Pos.CENTER);

        ListView<Student1> listView = new ListView<>(students);
        listView.setPrefSize(400, 240);

        HBox hbox = setButtons();
        vbox.getChildren().addAll(listView, hbox);

        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private ObservableList<Student1> populateList() {
        Student1 s1 = new Student1("Іванов", "Борис", 75);
        Student1 s2 = new Student1("Петренко", "Петро", 92);
        Student1 s3 = new Student1("Сергієнко", "Сергій", 61);
        Student1 s4 = new Student1("Сковорода", "Григорій", 88);

        return FXCollections.observableArrayList(s1, s2, s3, s4);
    }

    private HBox setButtons() {
        Button sortByNameButton = new Button("Сортувати за ім'ям");
        Button sortByLastNameButton = new Button("Сортувати за прізвищем");
        Button sortByMarkButton = new Button("Сортувати за оцінкою");

        HBox.setHgrow(sortByNameButton, Priority.ALWAYS);
        HBox.setHgrow(sortByLastNameButton, Priority.ALWAYS);
        HBox.setHgrow(sortByMarkButton, Priority.ALWAYS);

        sortByNameButton.setMaxWidth(Double.MAX_VALUE);
        sortByLastNameButton.setMaxWidth(Double.MAX_VALUE);
        sortByMarkButton.setMaxWidth(Double.MAX_VALUE);

        final boolean[] order = {true};

        sortByNameButton.setOnAction((ActionEvent event) -> {
            students.sort(new NameSorter1(order[0]));
            order[0] = !order[0];
        });

        sortByLastNameButton.setOnAction((ActionEvent event) -> {
            students.sort(new LastNameSorter1(order[0]));
            order[0] = !order[0];
        });

        sortByMarkButton.setOnAction((ActionEvent event) -> {
            students.sort(new AvgScoreSorter1(order[0]));
            order[0] = !order[0];
        });

        HBox hb = new HBox();
        hb.setSpacing(5);
        hb.getChildren().addAll(sortByNameButton, sortByLastNameButton, sortByMarkButton);
        hb.setAlignment(Pos.CENTER);

        return hb;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

// ==== Клас студентів ====
class Student1 {
    private String lastName;
    private String firstName;
    private int avgScore;

    public Student1(String lastName, String firstName, int avgScore) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.avgScore = avgScore;
    }

    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public int getAvgScore() { return avgScore; }

    @Override
    public String toString() {
        return lastName + " " + firstName + ", середній бал: " + avgScore;
    }
}

// ==== Сортувальники ====
class NameSorter1 implements Comparator<Student1> {
    private boolean ascending;
    public NameSorter1(boolean ascending) { this.ascending = ascending; }
    @Override
    public int compare(Student1 s1, Student1 s2) {
        return ascending ? s1.getFirstName().compareTo(s2.getFirstName())
                : s2.getFirstName().compareTo(s1.getFirstName());
    }
}
class LastNameSorter1 implements Comparator<Student1> {
    private boolean ascending;
    public LastNameSorter1(boolean ascending) { this.ascending = ascending; }
    @Override
    public int compare(Student1 s1, Student1 s2) {
        return ascending ? s1.getLastName().compareTo(s2.getLastName())
                : s2.getLastName().compareTo(s1.getLastName());
    }
}

class AvgScoreSorter1 implements Comparator<Student1> {
    private boolean ascending;
    public AvgScoreSorter1(boolean ascending) { this.ascending = ascending; }
    @Override
    public int compare(Student1 s1, Student1 s2) {
        return ascending ? s1.getAvgScore() - s2.getAvgScore()
                : s2.getAvgScore() - s1.getAvgScore();
    }
}
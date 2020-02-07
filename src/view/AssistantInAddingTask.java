package view;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import components.Task;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AssistantInAddingTask {

	// private static ObservableList<Task> newTaskList =
	// FXCollections.observableArrayList();
	private String priorityValueFromChoiceBos;
	private static Task task;

	@SuppressWarnings("unused")
	private LocalDate dateNowLocal = LocalDate.now();

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;
	@FXML
	private AnchorPane allPane;

	@FXML
	private TextField nameTask;
	@FXML
	private TextField doerName;
	@FXML
	private TextField dateOfCreation;
	@FXML
	private TextArea description;

	@FXML
	private ChoiceBox<String> priority;
	@FXML
	private DatePicker expirationDate;
	@FXML
	private Button addTask;

	@FXML
	void initialize() throws IOException {
		markTheDefaultValuesField(dateNowLocal);
		priority.setItems(FXCollections.observableArrayList("Низкий", "Средний", "Высокий"));
		String[] priorityVariables = new String[] { "Низкий", "Средний", "Высокий" };
		priority.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				priorityValueFromChoiceBos = priorityVariables[newValue.intValue()];
			}
		});

		addTask.setOnAction(event -> {
			task = new Task(nameTask.getText(), description.getText(), priorityValueFromChoiceBos, LocalDate.now(),
					expirationDate.getValue(), doerName.getText());
			TableDisplayAssistant.setTasksList(TableDisplayAssistant.setNewValuesForTable());
			markTheDefaultValuesField(dateNowLocal);
		});
	}

	public void markTheDefaultValuesField(LocalDate dateNowLocal) {
		dateOfCreation.setText(dateNowLocal.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")).toString());
		expirationDate.setValue(dateNowLocal);
		nameTask.setText(null);
		description.setText(null);
		doerName.setText(null);
	}

	@SuppressWarnings("static-access")
	public void setTask(Task task) {
		this.task = task;
	}

	public static Task getTask() {
		return task;
	}

}

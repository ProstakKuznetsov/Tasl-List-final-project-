package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import components.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import view.TableDisplayAssistant;

public class Searcher {

	private ObservableList<Task> tasksList = FXCollections.observableArrayList();
	private List<Task> newTasksList = FXCollections.observableArrayList();
	private String query;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane searchingPane;

	@FXML
	private BorderPane searchingBord;

	@FXML
	private TableView<Task> allTasksTable;

	@FXML
	private TableColumn<Task, String> nameColumn;

	@FXML
	private TableColumn<Task, String> descriptionColumn;

	@FXML
	private TableColumn<Task, String> priorityColumn;

	@FXML
	private TableColumn<Task, LocalDate> dateOfCreationColumn;

	@FXML
	private TableColumn<Task, LocalDate> expirationTimeColumn;

	@FXML
	private TableColumn<Task, String> doerNameColumn;

	@FXML
	private Text greeting;

	@FXML
	private TextArea searchingText;

	@FXML
	private Button searchByTaskName;

	@FXML
	private Button searchByDescription;

	@FXML
	private Button searchByDoerName;

	@FXML
	void initialize() {

		tasksList = TableDisplayAssistant.getTasksList();

		allTasksTable.setEditable(true);
		nameColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("name"));
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
		priorityColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("priority"));
		dateOfCreationColumn.setCellValueFactory(new PropertyValueFactory<Task, LocalDate>("dateOfCreation"));
		expirationTimeColumn.setCellValueFactory(new PropertyValueFactory<Task, LocalDate>("expirationDate"));
		doerNameColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("doer"));

		allTasksTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		searchByDescription.setOnAction(event -> {
			if (!WorkerWithTable.isFilledField(searchingText.getText())) {
				query = searchingText.getText().toLowerCase();
				newTasksList = tasksList.stream() //
						.filter(task -> task.getDescription().toLowerCase().indexOf(query) >= 0) //
						.collect(Collectors.toList());
				outputTasks(newTasksList);
			} else
				notifyAboutEmptyInputFields();
		});

		searchByTaskName.setOnAction(event -> {
			if (!WorkerWithTable.isFilledField(searchingText.getText())) {
				query = searchingText.getText().toLowerCase();
				newTasksList = tasksList.stream() //
						.filter(task -> task.getName().toLowerCase().indexOf(query) >= 0) //
						.collect(Collectors.toList());
				outputTasks(newTasksList);
			} else
				notifyAboutEmptyInputFields();
		});

		searchByDoerName.setOnAction(event -> {
			if (!WorkerWithTable.isFilledField(searchingText.getText())) {
				query = searchingText.getText().toLowerCase();
				newTasksList = tasksList.stream() //
						.filter(task -> task.getDoer().toLowerCase().indexOf(query) >= 0) //
						.collect(Collectors.toList());
				outputTasks(newTasksList);
			} else
				notifyAboutEmptyInputFields();
		});
	}

	// метод вывода результата поиска или сообщения.
	public void outputTasks(List<Task> newTasksList) {
		if (!newTasksList.isEmpty()) {
			setItems(newTasksList);
		} else
			JOptionPane.showMessageDialog(null, "Совпадений не найдено");
		searchingPane.toFront();
	}

	// метод в случае наличия совпадений
	private void setItems(List<Task> newTasksList) {
		ObservableList<Task> tasksObs = FXCollections.observableArrayList(newTasksList);
		searchingText.clear();
		allTasksTable.setItems(null);
		allTasksTable.setItems(tasksObs);
	}

	// метод в случае незаполненного поля ввода
	private void notifyAboutEmptyInputFields() {
		JOptionPane.showMessageDialog(null, "Необходимо заполнить поле выше.");
		allTasksTable.setItems(null);
		searchingPane.toFront(); // toFront срабатывает через раз. не смог понять почему.
	}
}
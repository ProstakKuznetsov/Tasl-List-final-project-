package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.Main;
import components.Task;
import controller.WorkerWithFiles;
import controller.WorkerWithTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TableDisplayAssistant {

	private static ObservableList<Task> tasksList = FXCollections.observableArrayList();
	public final static ObservableList<Task> tasksListStaticOld = FXCollections.observableArrayList();
	private static TableView<Task> allTasksTableStatic = new TableView<Task>();

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane allPane;

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
	private Button addTask;

	@FXML
	private Button deleteTask;

	@FXML
	private Button searchButton;

	@FXML
	void initialize() throws IOException {

		// create a file for writing
		Main.logger.info("create and read file");
		File file = new File(Main.PRIMARY_FILE_NAME);
		file.createNewFile();

		// create a threads for reading
		FileReader primaryFileReader = new FileReader(file);
		BufferedReader bufferedFileReader = new BufferedReader(primaryFileReader);
		tasksList = WorkerWithFiles.getTasksList(bufferedFileReader);
		bufferedFileReader.close();
		primaryFileReader.close();

		tasksListStaticOld.addAll(tasksList);

		allTasksTable.setEditable(true);

		nameColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("name"));
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
		descriptionColumn.setCellFactory(new ToolTipCellFactory<>());
		priorityColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("priority"));
		dateOfCreationColumn.setCellValueFactory(new PropertyValueFactory<Task, LocalDate>("dateOfCreation"));
		expirationTimeColumn.setCellValueFactory(new PropertyValueFactory<Task, LocalDate>("expirationDate"));
		doerNameColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("doer"));

		allTasksTable.setItems(tasksList);
		allTasksTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		// Присваиваем статической тфблице значение нестатической,
		// т.к. статической таблицу сделать чревато NullPointerException
		allTasksTableStatic = allTasksTable;

		addTask.setOnAction(event -> {
			try {
				Stage primaryStage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("../view/windowAddTask.fxml"));
				primaryStage.setTitle("Добавить задачу");
				primaryStage.setScene(new Scene(root, 650, 350));
				primaryStage.setMinWidth(650);
				primaryStage.setMinHeight(350);
				primaryStage.setMaxWidth(650);
				primaryStage.setMaxHeight(350);
				primaryStage.show();
			} catch (Exception e) {
				Main.logger.debug("Exeption create windowAddTask.fxml", e);
				e.printStackTrace();
			}
		});

		deleteTask.setOnAction(event -> {
			try {
				WorkerWithTable.deleteTaskFromTable(tasksList, allTasksTable, file);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Не удалось удалить запись");
				e.printStackTrace();
			}
		});

		searchButton.setOnAction(event -> {
			Stage primaryStage = new Stage();
			primaryStage.setX(100);
			primaryStage.setY(100);
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("../view/windowForSearching.fxml"));
				primaryStage.setScene(new Scene(root, 600, 400));
				primaryStage.setResizable(true);
				primaryStage.setTitle("Поиск");
				primaryStage.show();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Ошибка поиска.");
				e.printStackTrace();
			}
		});
	}

	public static ObservableList<Task> setNewValuesForTable() {
		ObservableList<Task> tasksList = getTasksList();
		Task task = AssistantInAddingTask.getTask();
		TableView<Task> allTasksTable = getAllTasksTable();
		if (WorkerWithTable.addTaskInTable(tasksList, allTasksTable, task) != null)
			allTasksTable.setItems(tasksList);
		allTasksTableStatic.setItems(tasksList);
		return tasksList;
	}

	public static ObservableList<Task> getAllTasksTableItemsNow() {
		ObservableList<Task> allTasksTableItemsNow = allTasksTableStatic.getItems();
		return allTasksTableItemsNow;
	}

	// Метод отображения текста полностью при наведении на ячейку таблицы
	public class ToolTipCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
		@Override
		public TableCell<S, T> call(TableColumn<S, T> param) {
			return new TableCell<S, T>() {
				@Override
				protected void updateItem(T item, boolean empty) {
					super.updateItem(item, empty);
					if (item == null) {
						setTooltip(null);
						setText(null);
					} else {
						setTooltip(new Tooltip(item.toString()));
						setText(item.toString());
					}
				}
			};
		}
	}

	public static ObservableList<Task> getTasksList() {
		return tasksList;
	}

	public static void setTasksList(ObservableList<Task> tasksListNew) {
		tasksList = tasksListNew;
	}

	public static TableView<Task> getAllTasksTable() {
		return allTasksTableStatic;
	}

	public void setAllTasksTable(TableView<Task> allTasksTable) {
		this.allTasksTable = allTasksTable;
	}

}

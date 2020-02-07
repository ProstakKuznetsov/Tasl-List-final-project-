package controller;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import application.Main;
import components.Task;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;

public class WorkerWithTable {

	public static ObservableList<Task> addTaskInTable(ObservableList<Task> allTasks, TableView<Task> allTasksTable,
			Task task) {
		if (isFilled(task)) {
			allTasks.add(task);
			allTasksTable.setItems(allTasks);
			return allTasks;
		} else
			return null;
	}

	public static void deleteTaskFromTable(ObservableList<Task> tasksList, TableView<Task> allTasksTable, File file)
			throws IOException {
		TableViewSelectionModel<Task> tsm = allTasksTable.getSelectionModel();
		if (tsm.isEmpty()) {
			Main.logger.info("field not selected when trying to delete value");
			JOptionPane.showMessageDialog(null, "Ни один элемент не выбран", "Ошибка", JOptionPane.ERROR_MESSAGE);
		} else {
			int selectedIndex = tsm.getSelectedIndex();
			tasksList.remove(selectedIndex);
		}
	}

	// check the correspondence of the entered data
	public static boolean isFilled(Task task) {
		if (isFilledField(task.getName()) || isFilledField(task.getDescription()) || isFilledField(task.getPriority())
				|| task.getExpirationDate() == null || isFilledField(task.getDoer())) {
			Main.logger.info("attempt to add a record with empty fields");
			JOptionPane.showMessageDialog(null, "Необходимо заполнить все поля", "Ошибка", JOptionPane.ERROR_MESSAGE);
			return false;
		} else
			return true;
	}

	public static boolean isFilledField(String str) {
		if (str == null || str.isEmpty() || str.matches("\\s+")) {
			return true;
		} else
			return false;
	}

}

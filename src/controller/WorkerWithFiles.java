package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import application.Main;
import components.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import view.TableDisplayAssistant;

public class WorkerWithFiles {
	// the method of filling a table from a file
	public static ObservableList<Task> getTasksList(BufferedReader file) throws IOException {
		ObservableList<Task> list = FXCollections.<Task>observableArrayList();
		String[] element = new String[6];
		while (file.read() != -1) {
			element = file.readLine().split(" _ & _ ");
			list.add(new Task(element[0], element[1], element[2], LocalDate.parse(element[3]),
					LocalDate.parse(element[4]), element[5]));
		}
		return FXCollections.<Task>observableArrayList(list);
	}

	public static void addStringInFile() throws IOException {
		File file = new File(Main.PRIMARY_FILE_NAME);
		ObservableList<Task> newTaskList = TableDisplayAssistant.getAllTasksTable().getItems();
		FileWriter fileWriter = new FileWriter(file, false);
		BufferedWriter bufferedPrimaryFileWriter = new BufferedWriter(fileWriter);
		for (Task task : newTaskList) {
			bufferedPrimaryFileWriter.write("\n" + task.toString().replace("\n", " "));
		}
		bufferedPrimaryFileWriter.close();
	}
}

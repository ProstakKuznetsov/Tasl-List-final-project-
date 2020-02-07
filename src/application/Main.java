package application;

import java.awt.HeadlessException;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import components.Task;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import view.TableDisplayAssistant;

public class Main extends Application {
	public static final String PRIMARY_FILE_NAME = "src/file/list.txt";
	public static final Logger logger = Logger.getLogger(application.Main.class);

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/windowForTable.fxml"));
			primaryStage.setTitle("Добро пожаловать!");
			primaryStage.setScene(new Scene(root, 650, 400));
			primaryStage.setMinWidth(650);
			primaryStage.setMinHeight(450);
			primaryStage.show();
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					ObservableList<Task> tasksTasks = TableDisplayAssistant.tasksListStaticOld;
					ObservableList<Task> tasksTable = TableDisplayAssistant.getAllTasksTableItemsNow();
					if (!tasksTasks.equals(tasksTable)) {
						try {
							createWindowSaver();
						} catch (HeadlessException e) {
							logger.debug("HeadlessException creating windowSaver", e);
							JOptionPane.showMessageDialog(null, e.toString());
						} catch (IOException e) {
							logger.debug("IOExeption creating windowSaver", e);
							JOptionPane.showMessageDialog(null, e.toString());
						}
					}
				}
			});
		} catch (Exception e) {
			logger.debug("Error creating first table", e);
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	// данный метод в класс SaverToFile не вынес, т.к. метод getClass не статичный,
	// не
	// позволяет.
	public void createWindowSaver() throws HeadlessException, IOException {

		Stage primaryStage = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("../view/windowForSaver.fxml"));
			primaryStage.setScene(new Scene(root, 180, 60));
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (IOException e) {
			logger.debug("Exeption creating windowSaver", e);
			JOptionPane.showMessageDialog(null, "Ошибка сохранения. " + e.toString());
		}
	}

}

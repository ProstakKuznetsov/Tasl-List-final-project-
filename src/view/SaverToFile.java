package view;

import java.io.IOException;

import javax.swing.JOptionPane;

import application.Main;
import controller.WorkerWithFiles;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class SaverToFile {

	@FXML
	private Button closeWithoutSavingButton;

	@FXML
	private Button saveInFileButton;

	@FXML
	private Label questionLabel;

	@FXML
	void initialize() throws IOException {

		saveInFileButton.setOnAction(event -> {
			try {
				WorkerWithFiles.addStringInFile();
				Main.logger.info("new values added in table and saved to a file");
				System.exit(42);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Ошибка. Не найден файл для записи.", null,
						JOptionPane.ERROR_MESSAGE, null);
				Main.logger.error(e);
				e.printStackTrace();
			}
		});

		closeWithoutSavingButton.setOnAction(event -> {
			Main.logger.info("program closed without saving");
			System.exit(42);
		});
	}

}

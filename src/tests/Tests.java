package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

import components.Task;
import controller.WorkerWithTable;

public class Tests {

	@Test
	public void isFilledTask() {
		Task task = new Task("", "", "", LocalDate.now(), LocalDate.now(), "");
		assertFalse(WorkerWithTable.isFilled(task));
		assertFalse(WorkerWithTable.isFilled(new Task("abc", "123", "", LocalDate.now(), LocalDate.now(), "adsf")));
		assertTrue(
				WorkerWithTable.isFilled(new Task("abc", "123", "Высокий", LocalDate.now(), LocalDate.now(), "adsf")));
	}

}

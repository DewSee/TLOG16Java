package tlog16java;

import java.time.LocalTime;
import org.junit.Test;
import static org.junit.Assert.*;
import timelogger.exceptions.NotSeparatedTimeException;

public class UtilTest {

	@Test
	public void testRoundToMultipleQuarterHours() throws Exception {
		Task task = new Task("1111", "7:30", "7:50", "comment");

		Util.roundToMultipleQuarterHour(task);

		assertEquals(LocalTime.of(7, 45), task.getEndTime());

	}

	@Test
	public void testIsSeparatedTime1() throws Exception {
		Task task1 = new Task("1111", "6:30", "6:45", "task1");
		Task task2 = new Task("2222", "5:30", "6:30", "task2");

		WorkDay workDay = new WorkDay();

		workDay.addTask(task1);
		workDay.addTask(task2);

		assertEquals(true, Util.isSeparatedTime(task2, workDay.getTasks()));
	}

	@Test
	public void testIsSeparatedTime2() throws Exception {
		Task task1 = new Task("1111", "6:30", "6:45", "task1");
		Task task2 = new Task("2222", "6:45", "7:45", "task2");

		WorkDay workDay = new WorkDay();

		workDay.addTask(task1);
		workDay.addTask(task2);

		assertEquals(true, Util.isSeparatedTime(task2, workDay.getTasks()));
	}

	@Test
	public void testIsSeparatedTime3() throws Exception {
		Task task1 = new Task("1111", "6:30", "6:30", "task1");
		Task task2 = new Task("2222", "5:30", "6:30", "task2");

		WorkDay workDay = new WorkDay();

		workDay.addTask(task1);
		workDay.addTask(task2);

		assertEquals(true, Util.isSeparatedTime(task2, workDay.getTasks()));
	}

	@Test
	public void testIsSeparatedTime4() throws Exception {
		Task task1 = new Task("1111", "6:30", "7:30", "task1");
		Task task2 = new Task("2222", "7:30", "7:30", "task2");

		WorkDay workDay = new WorkDay();

		workDay.addTask(task1);
		workDay.addTask(task2);

		assertEquals(true, Util.isSeparatedTime(task2, workDay.getTasks()));
	}

	@Test(expected = NotSeparatedTimeException.class)
	public void testIsSeparatedTime5() throws Exception {
		Task task1 = new Task("1111", "6:30", "7:00", "task1");
		Task task2 = new Task("2222", "6:00", "6:45", "task2");

		WorkDay workDay = new WorkDay();

		workDay.addTask(task1);
		workDay.addTask(task2);
	}

	@Test(expected = NotSeparatedTimeException.class)
	public void testIsSeparatedTime6() throws Exception {
		Task task1 = new Task("1111", "6:30", "7:00", "task1");
		Task task2 = new Task("2222", "6:30", "6:45", "task2");

		WorkDay workDay = new WorkDay();

		workDay.addTask(task1);
		workDay.addTask(task2);
	}

	@Test(expected = NotSeparatedTimeException.class)
	public void testIsSeparatedTime7() throws Exception {
		Task task1 = new Task("1111", "6:30", "7:00", "task1");
		Task task2 = new Task("2222", "6:45", "7:15", "task2");

		WorkDay workDay = new WorkDay();

		workDay.addTask(task1);
		workDay.addTask(task2);
	}

	@Test(expected = NotSeparatedTimeException.class)
	public void testIsSeparatedTime8() throws Exception {
		Task task1 = new Task("1111", "6:30", "7:00", "task1");
		Task task2 = new Task("2222", "6:45", "7:00", "task2");

		WorkDay workDay = new WorkDay();

		workDay.addTask(task1);
		workDay.addTask(task2);
	}

	@Test
	public void testIsSeparatedTime9() throws Exception {
		Task task1 = new Task("1111", "6:30", "6:30", "task1");
		Task task2 = new Task("2222", "6:30", "7:00", "task2");

		WorkDay workDay = new WorkDay();

		workDay.addTask(task1);
		workDay.addTask(task2);

		assertEquals(true, Util.isSeparatedTime(task2, workDay.getTasks()));
	}

	@Test
	public void testIsSeparatedTime10() throws Exception {
		Task task1 = new Task("1111", "6:30", "7:30", "task1");
		Task task2 = new Task("2222", "6:30", "6:30", "task2");

		WorkDay workDay = new WorkDay();

		workDay.addTask(task1);
		workDay.addTask(task2);

		assertEquals(true, Util.isSeparatedTime(task2, workDay.getTasks()));
	}

}

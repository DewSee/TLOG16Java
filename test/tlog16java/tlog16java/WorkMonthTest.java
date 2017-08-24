package tlog16java;

import org.junit.Test;
import static org.junit.Assert.*;
import timelogger.exceptions.EmptyTimeFieldException;
import timelogger.exceptions.NotNewDateException;
import timelogger.exceptions.NotSameMonthException;
import timelogger.exceptions.WeekendNotEnabledException;

public class WorkMonthTest {

	@Test
	public void testGetSumPerMonth() throws Exception {
		Task task1 = new Task("1111", "7:30", "8:45", "task1");
		WorkDay workDay1 = new WorkDay(2016, 9, 1);

		workDay1.addTask(task1);

		Task task2 = new Task("2222", "8:45", "9:45", "task1");
		WorkDay workDay2 = new WorkDay(2016, 9, 2);

		workDay2.addTask(task2);

		WorkMonth workMonth = new WorkMonth(2016, 9);

		workMonth.addWorkDay(workDay1, true);
		workMonth.addWorkDay(workDay2, true);

		assertEquals(135, workMonth.getSumPerMonth());
	}

	@Test
	public void testGetSumPerMonthWithoutDays() {
		WorkMonth workMonth = new WorkMonth(2017, 5);

		assertEquals(0, workMonth.getSumPerMonth());
	}

	@Test
	public void testNegativeExtraMinPerMonth() throws Exception {
		Task task1 = new Task("1111", "7:30", "8:45", "task1");
		WorkDay workDay1 = new WorkDay(420, 2016, 9, 2);
		workDay1.addTask(task1);

		Task task2 = new Task("2222", "8:45", "9:45", "task2");
		WorkDay workDay2 = new WorkDay(420, 2016, 9, 1);
		workDay2.addTask(task2);

		WorkMonth workMonth = new WorkMonth(2017, 9);
		workMonth.addWorkDay(workDay1, true);
		workMonth.addWorkDay(workDay2, true);

		assertEquals(-705, workMonth.getExtraMintPerMonth());
	}

	@Test
	public void testZeroExtraMintPerMonth() {
		WorkMonth workMonth = new WorkMonth(2017, 2);

		assertEquals(0, workMonth.getExtraMintPerMonth());
	}

	@Test
	public void testGetRequiredMinPerMonth() throws Exception {
		WorkDay workDay1 = new WorkDay(420, 2016, 9, 1);
		WorkDay workDay2 = new WorkDay(420, 2016, 9, 2);

		WorkMonth workMonth = new WorkMonth(2016, 9);

		workMonth.addWorkDay(workDay1, true);
		workMonth.addWorkDay(workDay2, true);

		assertEquals(840, workMonth.getRequiredMinPerMonth());
	}

	@Test
	public void testGetZeroRequiredMinPerMonth() {
		WorkMonth workMonth = new WorkMonth(2016, 5);

		assertEquals(0, workMonth.getRequiredMinPerMonth());
	}

	@Test
	public void testSumMinutesWithOneTask() throws Exception {
		Task task = new Task("1111", "7:30", "8:45", "comment");
		WorkDay workDay = new WorkDay(2016, 9, 9);
		WorkMonth workMonth = new WorkMonth(2016, 9);

		workDay.addTask(task);
		workMonth.addWorkDay(workDay);

		assertEquals(workDay.getSumPerDay(), workMonth.getSumPerMonth());
	}

	@Test
	public void testWeekendIsEnabled() throws Exception {
		Task task = new Task("1111", "7:30", "8:30", "comment");
		WorkDay workDay = new WorkDay(2016, 8, 28);
		WorkMonth workMonth = new WorkMonth(2016, 8);

		workDay.addTask(task);
		workMonth.addWorkDay(workDay, true);
	}

	@Test(expected = WeekendNotEnabledException.class)
	public void testWeekendNotEnabled() throws Exception {
		Task task = new Task("1111", "7:30", "8:30", "comment");
		WorkDay workDay = new WorkDay(2016, 8, 28);
		WorkMonth workMonth = new WorkMonth(2016, 8);

		workDay.addTask(task);
		workMonth.addWorkDay(workDay, false);
	}

	@Test(expected = NotNewDateException.class)
	public void testIsNewDate() throws Exception {
		WorkDay workDay1 = new WorkDay(2016, 9, 1);
		WorkDay workDay2 = new WorkDay(2016, 9, 1);

		WorkMonth workMonth = new WorkMonth(2016, 9);

		workMonth.addWorkDay(workDay1);
		workMonth.addWorkDay(workDay2);
	}

	@Test(expected = NotSameMonthException.class)
	public void testIsSameMonth() throws Exception {
		WorkDay workDay1 = new WorkDay(2016, 9, 1);
		WorkDay workDay2 = new WorkDay(2016, 8, 30);

		WorkMonth workMonth = new WorkMonth(2016, 9);

		workMonth.addWorkDay(workDay1);
		workMonth.addWorkDay(workDay2);
	}

	@Test(expected = EmptyTimeFieldException.class)
	public void testTaskEmptyTimeField() throws Exception {
		Task task = new Task("1111");
		WorkDay workDay = new WorkDay(2016, 2, 1);
		WorkMonth workMonth = new WorkMonth(2016, 2);

		workDay.addTask(task);
		workMonth.addWorkDay(workDay);
	}

}

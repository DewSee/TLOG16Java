package tlog16java;

import org.junit.Test;
import static org.junit.Assert.*;
import timelogger.exceptions.NotNewDateException;
import timelogger.exceptions.NotNewMonthException;

public class TimeLoggerTest {

	@Test
	public void testGetSumValues() throws Exception {
		WorkDay workDay = new WorkDay(2016, 04, 14);
		WorkMonth workMonth = new WorkMonth(2016, 04);
		Task task = new Task("1111", "7:30", "10:30", "comment");

		workDay.addTask(task);
		workMonth.addWorkDay(workDay);

		TimeLogger timeLogger = new TimeLogger();
		timeLogger.addMonth(workMonth);

		assertEquals(180, timeLogger.getMonths().get(0).getSumPerMonth());
		assertEquals(180, task.getMinPerTask());
	}

	@Test(expected = NotNewMonthException.class)
	public void testIsNewMonth() throws Exception {
		WorkMonth workMonth1 = new WorkMonth(2016, 4);
		WorkMonth workMonth2 = new WorkMonth(2016, 4);

		TimeLogger timeLogger = new TimeLogger();

		timeLogger.addMonth(workMonth1);
		timeLogger.addMonth(workMonth2);

	}
}

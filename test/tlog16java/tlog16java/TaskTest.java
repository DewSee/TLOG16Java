/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tlog16java;

import java.time.LocalTime;
import org.junit.Test;
import static org.junit.Assert.*;
import timelogger.exceptions.EmptyTimeFieldException;
import timelogger.exceptions.InvalidTaskIdException;
import timelogger.exceptions.NoTaskIdException;
import timelogger.exceptions.NotExpectedTimeOrderException;

/**
 *
 * @author Gyula
 */
public class TaskTest {

	@Test(expected = NotExpectedTimeOrderException.class)
	public void testCreateTaskWithWrongTimeOrder() throws Exception {
		Task task = new Task("7637", "8:45", "7:30", "desc");
	}

	@Test
	public void testGetMinPerTask() throws Exception {
		Task task = new Task("LT-7637", "7:30", "8:45", "desc");
		long difference = task.getMinPerTask();
		assertEquals(75, difference);
	}

	@Test(expected = InvalidTaskIdException.class)
	public void testCreateTaskWithInvalidId() throws Exception {
		Task task = new Task("67", "8:45", "10:00", "desc");
	}

	@Test
	public void testCreateTaskWithoutComment() throws Exception {
		Task task = new Task("7657", "8:00", "9:00", "");
		assertEquals("", task.getComment());

	}

	@Test
	public void testRoundNotQuarterEndTime() throws Exception {
		Task task = new Task("7657", "7:30", "7:50", "desc");
		LocalTime testEndTime = LocalTime.of(7, 45);
		Util.roundToMultipleQuarterHour(task);
		assertEquals(testEndTime, task.getEndTime());
	}

	@Test
	public void testRoundNotQuarterStartTime() throws Exception {
		Task task = new Task("LT-6555", "7:20", "7:30", "desc");
		assertEquals(LocalTime.of(7, 35), task.getEndTime());
	}

	@Test(expected = NoTaskIdException.class)
	public void testNullTaskId() throws Exception {
		Task task = new Task(null, "7:00", "8:00", "desc");
	}

	@Test(expected = EmptyTimeFieldException.class)
	public void testCreateTaskWithoutTime() throws Exception {
		Task task = new Task("7659");
	}

	@Test
	public void testChangeTaskStartTime() throws Exception {
		Task task = new Task("7655", "7:30", "7:45", "desc");
		task.setStartTime(7, 0);
		assertEquals(LocalTime.of(7, 0), task.getStartTime());
	}

	@Test
	public void testChangeTaskEndTime() throws Exception {
		Task task = new Task("7655", "7:30", "7:45", "desc");
		task.setEndTime(8, 0);
		assertEquals(LocalTime.of(8, 0), task.getEndTime());
	}

	@Test
	public void testCreateTaskWithValidData() throws Exception {
		Task task = new Task("LT-2399", "8:00", "9:00", "abrakadabra");
		task.isValidTaskId();

	}
}

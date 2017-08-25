/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tlog16java;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import org.junit.Test;
import static org.junit.Assert.*;
import timelogger.exceptions.FutureWorkDayException;
import timelogger.exceptions.NegativeMinutesOfWorkException;
import timelogger.exceptions.NotSeparatedTimeException;

/**
 *
 * @author Gyula
 */
public class WorkDayTest {

	@Test
	public void testGetExtraMinutesPerDay() throws Exception {
		Task task = new Task("8888", "7:30", "8:45", "comment");
		WorkDay workDay = new WorkDay();
		workDay.getTasks().add(task);
		long difference = -375;

		assertEquals(difference, workDay.getExtraMinPerDay());
	}

	@Test
	public void testCreateDayWithoutTask() {
		WorkDay workDay = new WorkDay();
		assertEquals(-450, workDay.getExtraMinPerDay());
	}

	@Test(expected = NegativeMinutesOfWorkException.class)
	public void testSetNegativeMinPerDay() throws NegativeMinutesOfWorkException {
		WorkDay workDay = new WorkDay();
		workDay.setRequiredMinPerDay(-30);
	}

	@Test(expected = NegativeMinutesOfWorkException.class)
	public void testCreateNegativeMinPerDay() throws NegativeMinutesOfWorkException {
		WorkDay workDay = new WorkDay(-30);
	}

	@Test(expected = FutureWorkDayException.class)
	public void testCreateFutureWorkDay() throws FutureWorkDayException {
		WorkDay workDay = new WorkDay(2017, 12, 31);
	}

	@Test(expected = FutureWorkDayException.class)
	public void testSetFutureActualDay() throws FutureWorkDayException {
		WorkDay workDay = new WorkDay();
		workDay.setActualDay(2017, 12, 31);

	}

	@Test
	public void testSumPerDayWithMultipleTasks() throws Exception {
		Task task1 = new Task("1111", "7:30", "8:45", "task1Comment");
		Task task2 = new Task("2222", "8:45", "9:45", "task2Comment");
		WorkDay workDay = new WorkDay();
		workDay.addTask(task1);
		workDay.addTask(task2);

		assertEquals(135, workDay.getSumPerDay());

	}

	@Test
	public void testCreateWorkDayWithoutTasks() {
		WorkDay workDay = new WorkDay();
		assertEquals(0, workDay.getSumPerDay());
	}

	@Test
	public void testGetLastEndTime() throws Exception {
		Task task1 = new Task("1111", "7:30", "8:45", "task1");
		Task task2 = new Task("2222", "9:30", "11:45", "task2");

		WorkDay workDay = new WorkDay();
		workDay.addTask(task1);
		workDay.addTask(task2);

		assertEquals(LocalTime.of(11, 45), workDay.getLastEndTime());
	}

	@Test(expected = NotSeparatedTimeException.class)
	public void testTaskSeparatedTime() throws Exception {
		Task task1 = new Task("1111", "7:30", "8:45", "task1");
		Task task2 = new Task("2222", "8:30", "9:45", "task2");

		WorkDay workDay = new WorkDay();
		workDay.addTask(task1);
		workDay.addTask(task2);
	}

	@Test
	public void testCreateDayWithGivenData() throws Exception {
		WorkDay workDay = new WorkDay(300, 2016, 5, 3);
		assertEquals(300, workDay.getRequiredMinPerDay());
		assertEquals(LocalDate.of(2016, Month.MAY, 3), workDay.getActualDay());
	}

	@Test
	public void testCreateDayWithDefaultMinPerDay() throws Exception {
		WorkDay workDay = new WorkDay(2016, 9, 4);

		assertEquals(LocalDate.of(2016, Month.SEPTEMBER, 4), workDay.getActualDay());
		assertEquals(450, workDay.getRequiredMinPerDay());
	}

	@Test
	public void testCreateDayWithDefaultDay() throws NegativeMinutesOfWorkException {
		WorkDay workDay = new WorkDay(300);

		assertEquals(300, workDay.getRequiredMinPerDay());
		assertEquals(LocalDate.now(), workDay.getActualDay());
	}

	@Test
	public void testCreateDayWithDefaultData() {
		WorkDay workDay = new WorkDay();

		assertEquals(450, workDay.getRequiredMinPerDay());
		assertEquals(LocalDate.now(), workDay.getActualDay());
	}

	@Test(expected = NotSeparatedTimeException.class)
	public void testNotSeparatedRoundedTimes() throws Exception {
		Task task1 = new Task("1111", "8:45", "9:50", "task1");
		Task task2 = new Task("2222", "8:20", "8:45", "task2");

		WorkDay workDay = new WorkDay();
		workDay.addTask(task1);
		workDay.addTask(task2);
	}
}

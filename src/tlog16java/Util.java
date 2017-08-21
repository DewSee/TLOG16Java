package tlog16java;

import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import timelogger.exceptions.EmptyTimeFieldException;
import timelogger.exceptions.NotExpectedTimeOrderException;

public class Util {

	public static int quarter = 15;

	public static boolean isMultipleQuarterHour(Task task) {
		return task.getMinPerTask() % quarter == 0;
	}

	public static boolean isWeekday(WorkDay workDay) {
		return workDay.getActualDay().getDayOfWeek().getValue() < Calendar.SATURDAY;
	}

	public static void roundToMultipleQuarterHour(Task task) {
		if (!isMultipleQuarterHour(task)) {
			int halfQuarter = 7;

			long remainder = task.getMinPerTask() % quarter;
			if (remainder <= halfQuarter) {
				try {
					task.setEndTime(task.getEndTime().minusMinutes(remainder));
				} catch (NotExpectedTimeOrderException | EmptyTimeFieldException ex) {
					Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
				}
			} else {
				try {
					task.setEndTime(task.getEndTime().plusMinutes(quarter - remainder));
				} catch (NotExpectedTimeOrderException | EmptyTimeFieldException ex) {
					Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}

	}

	public static boolean isSeparatedTime(Task task, List<Task> tasks) {
		for (Task existingTask : tasks) {
			if ((task.getStartTime().isAfter(existingTask.getStartTime()) && task.getStartTime().isBefore(existingTask.getEndTime()))
			|| (task.getEndTime().isAfter(existingTask.getStartTime()) && task.getEndTime().isBefore(existingTask.getEndTime()))
			|| (task.getStartTime().isBefore(existingTask.getStartTime()) && task.getEndTime().isAfter(existingTask.getEndTime()))) {
				return false;
			}
		}
		return true;
	}

	public static void selectMonth(List<WorkMonth> months, WorkMonth selectedMonth, int monthNumber) {
		for (int i = 0; i < months.size(); i++) {
			if (months.get(i).getDate().getMonthValue() == monthNumber) {
				selectedMonth = months.get(i);
			}
		}
	}

	public static void selectDay(List<WorkDay> days, WorkDay selectedDay, int dayNumber) {
		for (int i = 0; i < days.size(); i++) {
			if (days.get(i).getActualDay().getDayOfMonth() == dayNumber) {
				selectedDay = days.get(i);
			}
		}
	}

	public static void selectTask(Task selectedTask, Scanner scanner, List<Task> tasks) {
		String selectedId = scanner.nextLine();
		for (Task task : tasks) {
			if (task.getTaskid().equals(selectedId)) {
				selectedTask = task;
				break;
			}
		}

	}

}

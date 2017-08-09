package tlog16java;

import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class Util {

	public static int quarter = 15;

	public static boolean isMultipleQuarterHour(Task task) {
		return task.getMinPerTask() % quarter == 0;
	}

	public static boolean isWeekday(WorkDay workDay) {
		return workDay.getActualDay().getDayOfWeek().getValue() < Calendar.SATURDAY;
	}

	public static long roundTtoMultipleQuarterHour(Task task) {
		int halfQuarter = 7;
		long result = 0;

		if (!isMultipleQuarterHour(task)) {
			long remainder = task.getMinPerTask() % quarter;
			if (remainder <= halfQuarter) {
				result = task.getMinPerTask() - remainder;
			} else {
				result = task.getMinPerTask() + (quarter - remainder);
			}
		}
		return result;
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

package tlog16java;

import java.util.Calendar;

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
}

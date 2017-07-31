package tlog16java;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WorkDay {

	private List<Task> tasks = new ArrayList<>();
	private long requiredMinPerDay;
	private LocalDate actualDay;
	private long sumPerDay;

	private int DEFAULT_MIN_PER_DAY = 450;
	private LocalDate DEFAULT_ACTUAL_DAY = LocalDate.now();

	public WorkDay(long requiredMinPerDay, int year, int month, int day) {
		this.requiredMinPerDay = requiredMinPerDay;
		this.actualDay = LocalDate.of(year, month, day);
	}

	public WorkDay(long requiredMinPerDay) {
		this.requiredMinPerDay = requiredMinPerDay;
		this.actualDay = DEFAULT_ACTUAL_DAY;
	}

	public WorkDay(LocalDate actualDay) {
		this.requiredMinPerDay = DEFAULT_MIN_PER_DAY;
		this.actualDay = actualDay;
	}

	public WorkDay() {
		this.requiredMinPerDay = DEFAULT_MIN_PER_DAY;
		this.actualDay = DEFAULT_ACTUAL_DAY;
	}

	public long getRequiredMinPerDay() {
		return requiredMinPerDay;
	}

	public LocalDate getActualDay() {
		return actualDay;
	}

	public long getSumPerDay() {
		return sumPerDay;
	}

	private long getExtraMinPerDay() {
		return sumPerDay - requiredMinPerDay;
	}

	private boolean isSeparatedTime(Task task) {
		for (Task existingTask : tasks) {
			return (!task.getStartTime().isAfter(existingTask.getStartTime()) && !task.getStartTime().isBefore(existingTask.getEndTime())
			|| (!task.getEndTime().isAfter(existingTask.getStartTime()) && !task.getEndTime().isBefore(existingTask.getEndTime())));
		}
		return false;
	}

	private void addTask(Task t) {
		if (t.isMultipleQuarterHour() && isSeparatedTime(t)) {
			tasks.add(t);
		}
	}

	protected boolean isWeekday() {
		return Calendar.DAY_OF_WEEK < 6;
	}

}

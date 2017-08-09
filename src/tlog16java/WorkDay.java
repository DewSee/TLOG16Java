package tlog16java;

import java.time.LocalDate;
import java.util.ArrayList;
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

	public WorkDay(int year, int month, int day) {
		this.requiredMinPerDay = DEFAULT_MIN_PER_DAY;
		this.actualDay = LocalDate.of(year, month, day);
	}

	public WorkDay() {
		this.requiredMinPerDay = DEFAULT_MIN_PER_DAY;
		this.actualDay = DEFAULT_ACTUAL_DAY;
	}

	public List<Task> getTasks() {
		return tasks;
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

	public void setRequiredMinPerDay(long requiredMinPerDay) {
		this.requiredMinPerDay = requiredMinPerDay;
	}

	public void setActualDay(int year, int month, int day) {
		this.actualDay = LocalDate.of(year, month, day);
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

	private void addTask(Task task) {
		if (Util.isMultipleQuarterHour(task) && isSeparatedTime(task)) {
			tasks.add(task);
		}
	}

	protected Task getLastEndTime() {
		Task latest = tasks.get(0);
		for (Task task : tasks) {
			if (latest.getEndTime().isAfter(task.getEndTime())) {
				latest = task;
			}
		}
		return latest;
	}

	@Override
	public String toString() {
		return "requiredMinPerDay: " + requiredMinPerDay + ", actualDay: " + actualDay + ", sumPerDay: " + sumPerDay;
	}

}

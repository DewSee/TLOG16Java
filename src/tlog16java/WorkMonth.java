package tlog16java;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WorkMonth {

	private List<WorkDay> workDays = new ArrayList<>();
	private YearMonth date;
	private long sumPerMonth;
	private long requiredMinPerMonth;

	public WorkMonth(int year, int month) {
		this.date = YearMonth.of(year, month);
	}

	public List<WorkDay> getWorkDays() {
		return workDays;
	}

	public YearMonth getDate() {
		return date;
	}

	public long getSumPerMonth() {
		return sumPerMonth;
	}

	public long getRequiredMinPerMonth() {
		return requiredMinPerMonth;
	}

	private boolean isNewDate(WorkDay workDay) {
		return !workDays.contains(workDay);
	}

	protected long getExtraMintPerMonth() {
		return getSumPerMonth() - getRequiredMinPerMonth();
	}

	private boolean isSameMonth(WorkDay workDay) {
		return Calendar.MONTH + 1 == workDay.getActualDay().getMonthValue();
	}

	private void addWorkDay(WorkDay workDay, boolean isWeekendEnabled) {
		if ((isWeekendEnabled || Util.isWeekday(workDay)) && isNewDate(workDay) && isSameMonth(workDay)) {
			workDays.add(workDay);
		}
	}

	protected void addWorkDay(WorkDay workDay) {
		if (Util.isWeekday(workDay) && isNewDate(workDay) && isSameMonth(workDay)) {
			workDays.add(workDay);
		}
	}
}

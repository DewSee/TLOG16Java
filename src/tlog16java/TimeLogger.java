package tlog16java;

import java.util.ArrayList;
import java.util.List;
import timelogger.exceptions.NotNewMonthException;

public class TimeLogger {

	private List<WorkMonth> months = new ArrayList<>();

	public List<WorkMonth> getMonths() {
		return months;
	}

	protected boolean isNewMonth(WorkMonth month) {
		return months.stream().noneMatch(o -> o.getDate().equals(month.getDate()));
	}

	protected void addMonth(WorkMonth month) throws NotNewMonthException {
		if (isNewMonth(month)) {
			months.add(month);
		} else {
			throw new NotNewMonthException("This month is already added!");
		}
	}

}

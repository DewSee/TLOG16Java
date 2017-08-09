package tlog16java;

import java.util.ArrayList;
import java.util.List;

public class TimeLogger {

	private List<WorkMonth> months = new ArrayList<>();

	public List<WorkMonth> getMonths() {
		return months;
	}

	protected boolean isNewMonth(WorkMonth month) {
		return !months.contains(month);
	}

	protected void addMonth(WorkMonth month) {
		if (isNewMonth(month)) {
			months.add(month);
		} else {
			System.out.println("This month is already added!");
		}
	}

}

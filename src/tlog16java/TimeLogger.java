package tlog16java;

import java.util.ArrayList;
import java.util.List;

public class TimeLogger {

	private List<WorkMonth> months = new ArrayList<>();

	public List<WorkMonth> getMonths() {
		return months;
	}

	private boolean isNewMonth(WorkMonth month) {
		return !months.contains(month);
	}

	private void addMonth(WorkMonth month) {
		if (isNewMonth(month)) {
			months.add(month);
		}
	}
}

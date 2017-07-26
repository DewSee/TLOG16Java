package tlog16java;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class WorkMonth {

	List<WorkDay> days = new ArrayList<>();
	YearMonth date;
	long sumPerMonth;
	long requiredMinPerMonth;
}

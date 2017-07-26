package tlog16java;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WorkDay {

	List<Task> tasks = new ArrayList<>();
	long requiredMinPerDay;
	LocalDate actualDay;
	long sumPerDay;
}

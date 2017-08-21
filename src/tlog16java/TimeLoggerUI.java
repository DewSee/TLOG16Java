package tlog16java;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import timelogger.exceptions.EmptyTimeFieldException;
import timelogger.exceptions.FutureWorkDayException;
import timelogger.exceptions.InvalidTaskIdException;
import timelogger.exceptions.NegativeMinutesOfWorkException;
import timelogger.exceptions.NoTaskIdException;
import timelogger.exceptions.NotExpectedTimeOrderException;
import timelogger.exceptions.NotNewDateException;
import timelogger.exceptions.NotNewMonthException;
import timelogger.exceptions.NotSameMonthException;
import timelogger.exceptions.WeekendNotEnabledException;

public class TimeLoggerUI {

	private static Scanner scanner;

	private static int yearNumber;
	private static int monthNumber;
	private static int dayNumber;

	private static WorkMonth selectedMonth;
	private static WorkDay selectedDay;
	private static Task selectedTask;

	public static void main(String[] args) {
		TimeLogger timeLogger = new TimeLogger();

		showMenu();

		chooseMenu(timeLogger);

	}

	private static void showMenu() {
		System.out.println(
		"0. Exit"
		+ "\n1. List months "
		+ "\n2. List days "
		+ "\n3. List tasks for a specific day"
		+ "\n4. Add new month"
		+ "\n5. Add day to a specific month"
		+ "\n6. Start a task for a day"
		+ "\n7. Finish a specific task"
		+ "\n8. Delete a task"
		+ "\n9. Modify task"
		+ "\n10. Statistics");
	}

	private static void chooseMenu(TimeLogger timeLogger) {
		scanner = new Scanner(System.in);

		int menu = scanner.nextInt();
		switch (menu) {
			case 0:
				System.exit(0);
				break;
			case 1:
				listMonths(timeLogger.getMonths());
				break;
			case 2:
				listDaysOfMonth(timeLogger.getMonths());
				break;
			case 3:
				listTasksOfDay(timeLogger.getMonths());
				break;
			case 4:
				addNewMonth(timeLogger);
				break;
			case 5:
				addNewDayToMonth(timeLogger.getMonths());
				break;
			case 6:
				startNewTask(timeLogger.getMonths());
				break;
			case 7:
				finishTask(timeLogger.getMonths());
				break;
			case 8:
				deleteTask(timeLogger.getMonths());
				break;
			case 9:
				modifyTask(timeLogger.getMonths());
				break;
			case 10:
				showStatistics(timeLogger.getMonths());
				break;
		}
		showMenu();
		chooseMenu(timeLogger);

	}

	private static void listMonths(List<WorkMonth> months) {
		for (int i = 0; i < months.size(); i++) {
			System.out.println(months.get(i).getDate().getMonthValue() + ". " + months.get(i).getDate().getMonth());
		}
	}

	private static void listDaysOfMonth(List<WorkMonth> months) {
		listMonths(months);

		System.out.print("Choose one from the listed months (1-12): ");
		scanner = new Scanner(System.in);
		monthNumber = scanner.nextInt();

		WorkMonth actualWorkMonth = months.get(monthNumber);

		for (WorkDay day : actualWorkMonth.getWorkDays()) {
			System.out.println(day.getActualDay().getDayOfMonth() + ". " + day.getActualDay().getDayOfWeek());
		}
	}

	private static void listTasksOfDay(List<WorkMonth> months) {
		listDaysOfMonth(months);

		System.out.println("Choose one from the listed days (1-31): ");
		dayNumber = scanner.nextInt();

		WorkDay actualWorkDay = months.get(monthNumber).getWorkDays().get(dayNumber);

		for (Task task : actualWorkDay.getTasks()) {
			System.out.println(task);
		}
	}

	private static void addNewMonth(TimeLogger timeLogger) {
		System.out.println("Year: ");
		yearNumber = scanner.nextInt();
		System.out.println("Month (1-12): ");
		monthNumber = scanner.nextInt();

		WorkMonth newMonth = new WorkMonth(yearNumber, monthNumber);
		try {
			timeLogger.addMonth(newMonth);
		} catch (NotNewMonthException ex) {
			Logger.getLogger(TimeLoggerUI.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	private static void addNewDayToMonth(List<WorkMonth> months) {

		listMonths(months);
		System.out.print("Month (1-12): ");
		monthNumber = scanner.nextInt();
		System.out.print("Day: (1-31): ");
		dayNumber = scanner.nextInt();
		System.out.print("Specify the amount of working hours (eg. 7,5): ");
		double workingHours = scanner.nextDouble();
		long workingMinutes = (long) (workingHours * 60);

		Util.selectMonth(months, selectedMonth, monthNumber);
		try {
			WorkDay newWorkDay = new WorkDay(workingMinutes, selectedMonth.getDate().getYear(), selectedMonth.getDate().getMonthValue(), dayNumber);
			selectedMonth.addWorkDay(newWorkDay);
		} catch (NegativeMinutesOfWorkException | FutureWorkDayException | WeekendNotEnabledException | NotNewDateException | NotSameMonthException ex) {
			Logger.getLogger(TimeLoggerUI.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private static void startNewTask(List<WorkMonth> months) {
		System.out.print("Month (1-12): ");
		monthNumber = scanner.nextInt();
		System.out.print("Day (1-31): ");
		dayNumber = scanner.nextInt();

		Util.selectMonth(months, selectedMonth, monthNumber);
		Util.selectDay(selectedMonth.getWorkDays(), selectedDay, dayNumber);

		System.out.print("Task ID: ");
		String taskId = scanner.nextLine();
		Task task = null;
		try {
			task = new Task(taskId);
		} catch (NoTaskIdException ex) {
			Logger.getLogger(TimeLoggerUI.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InvalidTaskIdException ex) {
			Logger.getLogger(TimeLoggerUI.class.getName()).log(Level.SEVERE, null, ex);
		} catch (EmptyTimeFieldException ex) {
			Logger.getLogger(TimeLoggerUI.class.getName()).log(Level.SEVERE, null, ex);
		}

		System.out.print("Description: ");
		task.setComment(scanner.nextLine());

		System.out.println("The latest task ended at " + selectedDay.getLastEndTime().toString() + ". If you want this time to be your new task's start, just hit Enter.");
		System.out.print("Start time (HH:MM): ");
		String startTime = scanner.nextLine();

		try {
			if (startTime.equals("")) {
				task.setStartTime(selectedDay.getLastEndTime().toString());
			} else {

				task.setStartTime(startTime);
			}
		} catch (NotExpectedTimeOrderException | EmptyTimeFieldException ex) {
			Logger.getLogger(TimeLoggerUI.class.getName()).log(Level.SEVERE, null, ex);
		}

		try {
			task.setEndTime(startTime);
		} catch (NotExpectedTimeOrderException | EmptyTimeFieldException ex) {
			Logger.getLogger(TimeLoggerUI.class.getName()).log(Level.SEVERE, null, ex);
		}

		if (task.isValidTaskId()) {
			selectedDay.getTasks().add(task);
		}

	}

	private static void finishTask(List<WorkMonth> months) {
		System.out.print("Month (1-12): ");
		monthNumber = scanner.nextInt();
		System.out.print("Day: ");
		dayNumber = scanner.nextInt();

		Util.selectMonth(months, selectedMonth, monthNumber);
		Util.selectDay(selectedMonth.getWorkDays(), selectedDay, dayNumber);

		for (Task task : selectedDay.getTasks()) {
			if (task.getEndTime().toString().equals("")) {
				System.out.println(task);
				System.out.print("Set end time for this task (HH:MM): ");
				String endTime = scanner.nextLine();
				try {
					task.setEndTime(endTime);
				} catch (NotExpectedTimeOrderException | EmptyTimeFieldException ex) {
					Logger.getLogger(TimeLoggerUI.class.getName()).log(Level.SEVERE, null, ex);
				}

			}
		}
	}

	private static void deleteTask(List<WorkMonth> months) {
		System.out.print("Month (1-12): ");
		monthNumber = scanner.nextInt();
		System.out.print("Day: ");
		dayNumber = scanner.nextInt();

		Util.selectMonth(months, selectedMonth, monthNumber);
		Util.selectDay(selectedMonth.getWorkDays(), selectedDay, dayNumber);

		System.out.print("Select the task's ID you want to delete: ");
		Util.selectTask(selectedTask, scanner, selectedDay.getTasks());

		System.out.println("Are you sure, you want to delete " + selectedTask.getTaskid() + "? (y/n)");
		String yesOrNo = scanner.next();
		do {
			switch (yesOrNo) {
				case "y":
					selectedDay.getTasks().remove(selectedTask);
					System.out.println("Task" + selectedTask.getTaskid() + " deleted");
					break;
				case "n":
					System.out.println("Deletion cancelled.");
					break;
				default:
					System.out.println("Invalid input. Please try again!");
					break;
			}
		} while (!yesOrNo.equals("y") || !yesOrNo.equals("n"));

	}

	private static void modifyTask(List<WorkMonth> months) {
		System.out.print("Month (1-12): ");
		monthNumber = scanner.nextInt();
		System.out.print("Day: ");
		dayNumber = scanner.nextInt();

		Util.selectMonth(months, selectedMonth, monthNumber);
		Util.selectDay(selectedMonth.getWorkDays(), selectedDay, dayNumber);

		System.out.print("Select the task's ID you want to modify: ");
		Util.selectTask(selectedTask, scanner, selectedDay.getTasks());

		System.out.println("If you don't want to change a value, leave it empty!");

		System.out.print("ID (current: " + selectedTask.getTaskid() + " ): ");
		String taskID = scanner.nextLine();
		try {
			selectedTask.setTaskId(taskID);
		} catch (InvalidTaskIdException | NoTaskIdException ex) {
			Logger.getLogger(TimeLoggerUI.class.getName()).log(Level.SEVERE, null, ex);
		}

		System.out.print("Start time (current: " + selectedTask.getStartTime() + " ): ");
		String startTime = scanner.nextLine();
		try {
			selectedTask.setStartTime(startTime);
		} catch (NotExpectedTimeOrderException | EmptyTimeFieldException ex) {
			Logger.getLogger(TimeLoggerUI.class.getName()).log(Level.SEVERE, null, ex);
		}

		System.out.print("End time (current: " + selectedTask.getEndTime() + " ): ");
		String endTime = scanner.nextLine();
		try {
			selectedTask.setEndTime(endTime);
		} catch (NotExpectedTimeOrderException | EmptyTimeFieldException ex) {
			Logger.getLogger(TimeLoggerUI.class.getName()).log(Level.SEVERE, null, ex);
		}

		System.out.print("Description (current: " + selectedTask.getComment() + " ): ");
		String comment = scanner.nextLine();
		selectedTask.setComment(comment);
	}

	private static void showStatistics(List<WorkMonth> months) {
		System.out.print("Choose a month (1-12): ");
		Util.selectMonth(months, selectedMonth, monthNumber);

		System.out.println("Required minutes: " + selectedMonth.getRequiredMinPerMonth());
		System.out.println("Total worked minutes:" + selectedMonth.getSumPerMonth());
		System.out.println("Extra worked minutes:" + selectedMonth.getExtraMintPerMonth());

		System.out.println("Workdays in this month: ");
		for (WorkDay day : selectedMonth.getWorkDays()) {
			System.out.println(day);
		}
	}
}

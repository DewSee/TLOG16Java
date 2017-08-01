package tlog16java;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Task {

	private String taskId;
	private LocalTime startTime;
	private LocalTime endTime;
	private String comment;

	public Task(String taskid, int startHour, int startMin, int endHour, int endMin, String comment) {
		this.taskId = taskid;
		this.startTime = LocalTime.of(startHour, startMin);
		this.endTime = LocalTime.of(endHour, endMin);
		this.comment = comment;
	}

	public Task(String taskId) {
		this.taskId = taskId;
	}

	public Task(String taskId, String startTime, String endTime, String comment) {
		String[] splitStartTime = startTime.split(":");
		String[] splitEndTime = endTime.split(":");
		this.taskId = taskId;
		this.startTime = LocalTime.of(Integer.parseInt(splitStartTime[0]), Integer.parseInt(splitStartTime[1]));
		this.endTime = LocalTime.of(Integer.parseInt(splitEndTime[0]), Integer.parseInt(splitEndTime[1]));
		this.comment = comment;
	}

	public String getTaskid() {
		return taskId;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public String getComment() {
		return comment;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setStartTime(int startHour, int startMin) {
		this.startTime = LocalTime.of(startHour, startMin);
	}

	public void setEndTime(int endHour, int endMin) {
		this.endTime = LocalTime.of(endHour, endMin);
	}

	public void setStartTime(String startTime) {
		String[] splitStartTime = startTime.split(":");
		this.startTime = LocalTime.of(Integer.parseInt(splitStartTime[0]), Integer.parseInt(splitStartTime[1]));;
	}

	public void setEndTime(String endTime) {
		String[] splitEndTime = endTime.split(":");
		this.endTime = LocalTime.of(Integer.parseInt(splitEndTime[0]), Integer.parseInt(splitEndTime[1]));
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	protected long getMinPerTask() {
		return Duration.between(startTime, endTime).get(ChronoUnit.MINUTES);
	}

	private boolean isValidTaskId() {
		return (isValidLtId() || isValidRedmineId());
	}

	private boolean isValidRedmineId() {
		return ((taskId.matches("[0-9]+") && taskId.length() == 4));
	}

	private boolean isValidLtId() {
		return ((taskId.startsWith("LT-") && taskId.substring(3).matches("[0-9]+")));
	}

}

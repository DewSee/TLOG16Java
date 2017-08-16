package tlog16java;

import java.time.LocalTime;
import org.junit.Test;
import static org.junit.Assert.*;

public class UtilTest {

	@Test
	public void testRoundToMultipleQuarterHours() throws Exception {
		Task task = new Task("1111", "7:30", "7:50", "comment");

		Util.roundToMultipleQuarterHour(task);

		assertEquals(LocalTime.of(7, 45), task.getEndTime());

	}

	@Test
	public void testIsSeparatedTime() {

	}

}

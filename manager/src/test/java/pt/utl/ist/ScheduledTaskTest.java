package pt.utl.ist;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.utl.ist.configuration.ConfigSingleton;
import pt.utl.ist.configuration.DefaultRepoxContextUtil;
import pt.utl.ist.task.DataSourceIngestTask;
import pt.utl.ist.task.ScheduledTask;
import pt.utl.ist.task.ScheduledTask.Frequency;

public class ScheduledTaskTest {
	private ScheduledTask goodTask;

	@Before
	public void setUp(){
		try {
            ConfigSingleton.setRepoxContextUtil(new DefaultRepoxContextUtil());
            ConfigSingleton.getRepoxContextUtil().getRepoxManagerTest();

			//id min hour day month weekday taskClass Parameters...

			// Jan 1st 00:00
			Calendar firstRun = new GregorianCalendar(2009, 0, 1, 0, 0);
			goodTask = new ScheduledTask("test_1", firstRun, Frequency.ONCE, null, new DataSourceIngestTask("test_1", "1", "false"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testIsNotTimeToRun() {
		GregorianCalendar notTime = new GregorianCalendar();
		notTime.set(GregorianCalendar.MONTH, 5);
		Assert.assertFalse(goodTask.isTimeToRun(notTime));
	}
	
	@Test
	public void testIsTimeToRun() {
		GregorianCalendar isTime = new GregorianCalendar(2009, 0, 1, 0, 0);
		Assert.assertTrue(goodTask.isTimeToRun(isTime));
	}
}

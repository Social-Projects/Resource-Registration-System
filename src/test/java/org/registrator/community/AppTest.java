package org.registrator.community;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
	private final String DEFAULT_DIRECTORY = "./test-output";
	private final String MAVEN_DIRECTORY = "surefire.reports.directory";

	@Test
	public void testApp() {
		String outputDirectory = System.getProperty(MAVEN_DIRECTORY);
		if ((outputDirectory == null) || (outputDirectory.isEmpty())) {
			outputDirectory = DEFAULT_DIRECTORY;
		}
		System.out.println("outputDirectory = " + outputDirectory);
		//
		Assert.assertTrue(true);
	}

}

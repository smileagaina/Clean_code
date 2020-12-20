package demo.ccc.com;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppIntegrationTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public AppIntegrationTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppIntegrationTest.class);
	}

	/**
	 * Please change file path to yours
	 * Rigourous Integration Test :-)
	 * @throws IOException 
	 */
	public void testCase1CompareFiles() throws IOException {
		File  inputFileName =new File("./testcases/case-1-input.txt");
		File  expectedFileName =new File("./testcases/case-1-output.txt");
		execCompareFiles(inputFileName, expectedFileName);
	}
	public void testCase2CompareFiles() throws IOException {
		File  inputFileName =new File("./testcases/case-2-input.txt");
		File  expectedFileName =new File("./testcases/case-2-output.txt");
		execCompareFiles(inputFileName, expectedFileName);
	}

	private void execCompareFiles(File inputFileName, File expectedFileName) throws IOException {
		App  theApp = new App();
		File yourOutputFile=theApp.yourFunction(inputFileName);
		assertTrue(FileUtils.contentEquals(expectedFileName, yourOutputFile));
	}
}

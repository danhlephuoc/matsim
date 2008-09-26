package playground.wrashid.test.root;

import playground.wrashid.test.root.PDES.util.ConcurrentListMPDSCTest;
import playground.wrashid.test.root.PDES.util.MyPriorityQueueTest;
import playground.wrashid.test.root.PDES2.ZoneMessageQueueTest;
import playground.wrashid.test.root.deqsim.DEQSimStarterTest;
import playground.wrashid.test.root.deqsim.PDESStarter2Test;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for playground.wrashid.test.root.PDES2");
		//$JUnit-BEGIN$
		suite.addTestSuite(ConcurrentListMPDSCTest.class);
		suite.addTestSuite(MyPriorityQueueTest.class);
		suite.addTestSuite(ZoneMessageQueueTest.class);
		suite.addTestSuite(DEQSimStarterTest.class);
		suite.addTestSuite(PDESStarter2Test.class);
		//$JUnit-END$
		return suite;
	}

	
	public static void main(String args[]){
		new AllTests().suite();
		
		suite();
	}
}

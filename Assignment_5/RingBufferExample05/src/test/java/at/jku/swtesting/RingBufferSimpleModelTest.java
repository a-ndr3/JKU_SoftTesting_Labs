package at.jku.swtesting;

import org.junit.jupiter.api.Test;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.RandomTester;
import nz.ac.waikato.modeljunit.StopOnFailureListener;
import nz.ac.waikato.modeljunit.Tester;
import nz.ac.waikato.modeljunit.VerboseListener;
import nz.ac.waikato.modeljunit.coverage.CoverageMetric;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionCoverage;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;

class RingBufferSimpleModelTest {

	@Test
	void randomTestWithSimpleModel() {
		Tester tester = new RandomTester(new RingBufferSimpleModel());
		
		tester.buildGraph();
		tester.addListener(new VerboseListener());
		tester.addListener(new StopOnFailureListener());
        tester.addCoverageMetric(new ActionCoverage());
        tester.addCoverageMetric(new StateCoverage());
		tester.addCoverageMetric(new TransitionCoverage());
		
		tester.generate(10);
		
		tester.printCoverage();
	}

}

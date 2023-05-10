package at.jku.swtesting;

import nz.ac.waikato.modeljunit.RandomTester;
import nz.ac.waikato.modeljunit.StopOnFailureListener;
import nz.ac.waikato.modeljunit.Tester;
import nz.ac.waikato.modeljunit.VerboseListener;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionCoverage;
import org.junit.jupiter.api.Test;

public class RingBufferModelTest {
    @Test
    void randomTestWithSimpleModel() {
        Tester tester = new RandomTester(new RingBufferModel());

        tester.buildGraph();
        tester.addListener(new VerboseListener());
//        tester.addListener(new StopOnFailureListener());
        tester.addCoverageMetric(new ActionCoverage());
        tester.addCoverageMetric(new StateCoverage());
        tester.addCoverageMetric(new TransitionCoverage());

        tester.generate(60);

        tester.printCoverage();
    }
}

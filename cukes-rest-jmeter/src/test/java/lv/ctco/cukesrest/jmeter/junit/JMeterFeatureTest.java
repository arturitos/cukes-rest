package lv.ctco.cukesrest.jmeter.junit;


import cucumber.runtime.junit.JUnitReporter;
import cucumber.runtime.model.CucumberFeature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


@RunWith(MockitoJUnitRunner.class)
public class JMeterFeatureTest {

    @InjectMocks
    JMeterFeature jMeterFeature;

    @Mock cucumber.runtime.Runtime runtime;
    @Mock CucumberFeature cukesFeature;
    @Mock JUnitReporter reporter;

    @Test
    public void shouldCheckFileNameGeneration() throws Exception {
        String filename = "My feature";
        String refactoredName = jMeterFeature.createName(filename);
        assertThat(refactoredName, is("My_feature"));
    }

}

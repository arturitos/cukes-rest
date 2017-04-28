package lv.ctco.cukesrest.run.custom;

import cucumber.api.CucumberOptions;
import lv.ctco.cukesrest.jmeter.junit.CucumberJMeter;
import org.junit.runner.RunWith;

@RunWith(CucumberJMeter.class)
@CucumberOptions(
    format = {"pretty"},
    features = "classpath:features",
    glue = {"lv.ctco.cukesrest.api", "lv.ctco.cukesrest.jmeter"},
    strict = true
)
public class RunJMeterGenerator {}

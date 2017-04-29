package lv.ctco.cukesrest.jmeter.junit;

import cucumber.runtime.Runtime;
import cucumber.runtime.junit.FeatureRunner;
import cucumber.runtime.junit.JUnitReporter;
import cucumber.runtime.model.CucumberFeature;
import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import lv.ctco.cukesrest.CukesRuntimeException;
import lv.ctco.cukesrest.jmeter.JMeterFilter;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.Logger;

class JMeterFeature extends FeatureRunner {

    public static final String OUTPUT_DIR = "target/jmeter_output";

    private final Logger logger = Logger.getLogger(JMeterFeature.class.getName());

    private final JMeterFilter filter;
    private final CucumberFeature cucumberFeature;

    public JMeterFeature(CucumberFeature cucumberFeature, Runtime runtime, JUnitReporter jUnitReporter,
                         JMeterFilter filter) throws InitializationError {
        super(cucumberFeature, runtime, jUnitReporter);
        this.filter = filter;
        this.cucumberFeature = cucumberFeature;
    }

    @Override
    public void run(RunNotifier notifier) {
        RestAssured.filters(filter);
        filter.createJMeterAction();
        super.run(notifier);

        try {
            File dir = new File(OUTPUT_DIR);
            if (!dir.exists()) {
                boolean mkdirsFailed = !dir.mkdirs();
                if (mkdirsFailed) throw new CukesRuntimeException("Failed to create Folder: " + OUTPUT_DIR);
            }

            String fileName = createName(extractFeatureName()) + "d.c";
            File file = new File(OUTPUT_DIR + File.separator + fileName);
            OutputStream out = new FileOutputStream(file);
            filter.dump(out);
            out.close();

            logger.info(file.getAbsolutePath());

            ArrayList<Filter> filtersCopy = new ArrayList<Filter>(RestAssured.filters());
            filtersCopy.remove(filter);
            RestAssured.replaceFiltersWith(filtersCopy);
        } catch (Exception e) {
            throw new CukesRuntimeException(e);
        }
    }

    private String extractFeatureName() {
        return cucumberFeature.getGherkinFeature().getName();
    }

    public String createName(String featureName){
        return featureName.replace(" ", "_");
    }
}

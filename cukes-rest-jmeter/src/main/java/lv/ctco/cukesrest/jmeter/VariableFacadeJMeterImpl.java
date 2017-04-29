package lv.ctco.cukesrest.jmeter;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lv.ctco.cukesrest.internal.VariableFacade;
import lv.ctco.cukesrest.internal.context.InflateContext;
import lv.ctco.cukesrest.jmeter.function.JMeterFunction;

@Singleton
@InflateContext
public class VariableFacadeJMeterImpl implements VariableFacade {

    @Inject
    JMeterFilter jMeterFilter;

    @Override
    public void setVariable(final String name, final String value) {
        jMeterFilter.getTrx().addFunction(new JMeterFunction() {
            @Override
            public String format() {
                return "lr_set_string(\"" + name + "\", \"" + value + "\");\n";
            }
        });
    }

    @Override
    public void setUUIDToVariable(final String name) {
        jMeterFilter.getTrx().addFunction(new JMeterFunction() {
            @Override
            public String format() {
                return "random_Generator(\"" + name + "\", 32);\n";
            }
        });
    }

    @Override
    public void setCurrentTimestampToVariable(final String name) {
        jMeterFilter.getTrx().addFunction(new JMeterFunction() {
            @Override
            public String format() {
                return "lr_save_timestamp(\"" + name + "\", \"DIGITS=10\", LAST);";
            }
        });
    }
}

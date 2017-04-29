package lv.ctco.cukesrest.jmeter.function;

public class WebRequestSaveParam implements JMeterFunction {
    @Override
    public String format() {
        return "web_reg_save_param(\"httpcode\",\n " +
            "\"LB=HTTP/1.1 \",\n " +
            "\"RB= \",\n " +
            "\"Ord=1\",\n " +
            "LAST);\n";
    }
}

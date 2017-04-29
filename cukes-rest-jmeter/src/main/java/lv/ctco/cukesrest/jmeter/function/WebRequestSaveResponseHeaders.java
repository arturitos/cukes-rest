package lv.ctco.cukesrest.jmeter.function;

public class WebRequestSaveResponseHeaders implements JMeterFunction {

    @Override
    public String format() {
        return "web_reg_save_param(\"ResponseHeaders\",\"LB=\",\"RB=\",\"Search=Headers\",LAST);\n";
    }
}

package lv.ctco.cukesrest.jmeter.function;

public class WebRequestSaveResponseHeaders implements LoadRunnerFunction {

    @Override
    public String format() {
        return "web_reg_save_param(\"ResponseHeaders\",\"LB=\",\"RB=\",\"Search=Headers\",LAST);\n";
    }
}

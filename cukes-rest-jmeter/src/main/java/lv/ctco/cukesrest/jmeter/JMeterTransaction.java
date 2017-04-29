package lv.ctco.cukesrest.jmeter;

import lv.ctco.cukesrest.jmeter.function.JMeterFunction;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class JMeterTransaction {

    private String name;
    private String trxFlag;
    private List<JMeterFunction> functions = new ArrayList<JMeterFunction>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrxFlag() {
        return trxFlag;
    }

    public void setTrxFlag(String trxFlag) {
        this.trxFlag = trxFlag;
    }

    public List<JMeterFunction> getFunctions() {
        return functions;
    }

    public void addFunction(JMeterFunction function) {
        functions.add(function);
    }

    public void setFunctions(List<JMeterFunction> functions) {
        this.functions = functions;
    }

    public String format() {
        String escapedTransactionName = StringUtils.replace(name, " ", "_");
        StringBuilder result = new StringBuilder()
                .append("lr_think_time(1);\n\n")
                .append("transactionStatus = LR_PASS;\n")
                .append("lr_start_transaction(\"").append(escapedTransactionName).append("\");\n\n");
        for (JMeterFunction function : functions) {
            result.append(function.format());
        }
        return result.append("lr_end_transaction(\"").append(escapedTransactionName).append("\", ").append(trxFlag)
            .append(");\n\n").toString();
    }
}

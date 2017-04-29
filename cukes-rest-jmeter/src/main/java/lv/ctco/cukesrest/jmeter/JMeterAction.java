package lv.ctco.cukesrest.jmeter;

import java.util.*;

public class JMeterAction {

    private List<JMeterTransaction> transactions = new ArrayList<JMeterTransaction>();

    public List<JMeterTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<JMeterTransaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(JMeterTransaction trx) {
        transactions.add(trx);
    }

    public String format() {
        StringBuilder result = new StringBuilder().append("Action() {\n" +
            "int HttpRetCode;\n" +
            "int transactionStatus;\n" +
            "int actionStatus = LR_PASS;\n" +
            "lr_continue_on_error(1);\n");
        for (JMeterTransaction transaction : transactions) {
            result.append(transaction.format());
        }
        return result.append("lr_exit(LR_EXIT_ACTION_AND_CONTINUE , actionStatus);\n" + "return 0;\n}\n\n").toString();
    }
}

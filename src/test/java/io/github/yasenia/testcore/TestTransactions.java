package io.github.yasenia.testcore;

import org.springframework.test.context.transaction.TestTransaction;

public class TestTransactions {

    public static void commitAndRestart() {
        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();
    }
}

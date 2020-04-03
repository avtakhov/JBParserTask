package parser.test;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import parser.ParserException;

import java.util.List;

public abstract class BaseTest {

    @Rule public ExpectedException thrown = ExpectedException.none();

    abstract void testOne(String test) throws ParserException;

    protected void runExpectingError(List<String> tests, Class<? extends Throwable> e) throws ParserException {
        for (String test : tests) {
            thrown.expect(e);
            testOne(test);
            thrown = ExpectedException.none();
        }
    }

    protected void run(List<String> tests) throws ParserException {
        for (String test : tests) {
            testOne(test);
        }
    }
}

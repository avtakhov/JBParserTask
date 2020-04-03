package parser.test;

import base.Asserts;
import expression.TypeException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import parser.*;
import parser.ParserException;

import java.util.List;

public class ExpressionParserTest extends BaseTest {

    ExpressionParser parser = new ExpressionParser();

    @Override
    protected void testOne(String test) throws ParserException {
        System.out.println("testing: " + test);
        Asserts.assertEquals("parser error", test, parser.parse(test).toString());
    }

    @Test
    public void runCorrectTests() throws Exception {
        List<String> tests = List.of(
                "10",
                "element",
                "(-1+2)",
                "(element+1)",
                "(-1*element)",
                "(element*(element+3))",
                "((1+element)-(element*element))",
                "(1=1)",
                "((element<0)|(element>0))",
                "(((element+123)*(3*-2))=0)",
                "((1=-2)|(element>((1--3)+(element*3))))",
                "(-1--1)"
        );
        run(tests);
    }

    @Test
    public void runSyntaxErrorTests() throws ParserException {
        List<String> tests = List.of(
                "100000000000000000000000000000",
                "102-1",
                "(102)",
                "123@",
                "(1+elements)",
                "(element^element)",
                "(element+1",
                "(elemen/element)",
                "(77/111 )",
                "(1 +3)",
                "(1++3)",
                "element-1)",
                "(1=1&1=1)",
                "((1<2)||(1>2))",
                "(element+element))",
                "(>1)",
                "(+element)",
                "(--1--1)"
        );
        runExpectingError(tests, ParserException.class);
    }

    @Test
    public void runTypeErrorTests() throws ParserException {
        List<String> tests = List.of(
                "(1+(1>element))",
                "(1=(3=3))",
                "(element<(element<0))",
                "((1>2)+(3=3))",
                "((1+2)&3)",
                "(1+((element+1)=3))",
                "((1000*1000)=(element=element))",
                "(((element*element)+(element-1))<((1+2)<(1+3))))"
        );
        runExpectingError(tests, TypeException.class);
    }
}

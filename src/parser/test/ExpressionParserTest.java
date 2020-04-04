package parser.test;

import base.Asserts;
import expression.TypeException;
import org.junit.Test;
import parser.*;
import parser.ParserException;

public class ExpressionParserTest {

    ExpressionParser parser = new ExpressionParser();

    void run(String expected, String test) throws ParserException {
        Asserts.assertEquals("", expected, parser.parse(test).toString());
    }

    @Test
    public void cnst() throws ParserException {
        run("42", "42");
        run("-10", "-10");
        run(Integer.toString(Integer.MIN_VALUE), Integer.toString(Integer.MIN_VALUE));
    }

    @Test
    public void element() throws ParserException {
        run("element", "element");
        run("(element+1)", "(element+1)");
        run("(element--42)", "(element--42)");
        run("(element+element)", "(element+element)");
    }

    @Test
    public void medium() throws ParserException {
        run("((element<0)|(element=0))", "((element<0)|(element=0))");
        run("(20391093+(-1+(-1--913993929)))", "(20391093+(-1+(-1--913993929)))");
    }

    @Test
    public void bool() throws ParserException {
        run("(((-1<2)|(1>(2-1)))|(element>1000))", "(((-1<2)|(1>(2-1)))|(element>1000))");
        run("((element+1)<element)", "((element+1)<element)");
        run("((((element<0)&(element>-42))|(element=-42))|(element=0))", "((((element<0)&(element>-42))|(element=-42))|(element=0))");
    }

    @Test
    public void MinInt() throws ParserException {
        String minInt = Integer.toString(Integer.MIN_VALUE);
        String tmp = "((" + minInt + "-" + minInt + ")>(element*" + minInt + "))";
        run(tmp, tmp);
    }

    @Test
    public void hard() throws ParserException {
        String tmp = "((((1+2)*3)-10300300)-((element*element)+(element+element)))";
        Asserts.assertEquals("", tmp, parser.parse(tmp).toString());
    }

    @Test(expected = ParserException.class)
    public void noEndBracket() throws ParserException {
        String tmp = "(1+2";
        parser.parse(tmp);
    }

    @Test(expected = ParserException.class)
    public void noStartBracket() throws ParserException {
        String tmp = "1+2)";
        parser.parse(tmp);
    }

    @Test(expected = ParserException.class)
    public void overflowConstant() throws ParserException {
        run("", "10000000000000000000000000");
    }

    @Test(expected = ParserException.class)
    public void invalidElement() throws ParserException {
        run("", "lement");
        run("", "(1-elememt)");
        run("", "elemen");
        run("", "elemeent");
        run("", "element@");
    }

    @Test(expected = ParserException.class)
    public void insertSymbol() throws ParserException {
        run("", "(1#0-3)");
        run("", "(10-3)#");
        run("", "#(-42+90)");
        run("", "(-42+#90)");
        run("", "(-42+90#)");
    }

    @Test(expected = ParserException.class)
    public void fullInvalid() throws ParserException {
        run("", "-8t4t2t");
    }

    @Test(expected = ParserException.class)
    public void noFirstArgument() throws ParserException {
        run("", "(>element)");
    }

    @Test(expected = ParserException.class)
    public void noLastArgument() throws ParserException {
        run("", "(element=))");
    }

    @Test(expected = ParserException.class)
    public void onlyBrackets() throws ParserException {
        run("", "()");
        run("", "((element+1))");
    }

    @Test(expected = ParserException.class)
    public void elementInBrackets() throws ParserException {
        run("", "(element)");
    }

    @Test(expected = ParserException.class)
    public void invalidOperator() throws ParserException {
        run("", "(element*|element)");
        run("", "(element--element)");
        run("", "(element$element)");
        run("", "(-42-+1)");
    }

    @Test(expected = ParserException.class)
    public void noBrackets() throws ParserException {
        run("", "(element>0)-(123+123)");
    }

    @Test(expected = ParserException.class)
    public void spaces() throws ParserException {
        run("", "(1 + 2)");
        run("", "- 42");
    }

    @Test(expected = TypeException.class)
    public void equalBool() throws ParserException {
        run("", "((1+2)=(element>1))");
    }

    @Test(expected = TypeException.class)
    public void lessBool() throws ParserException {
        run("", "(element<(element<element))");
    }

    @Test(expected = TypeException.class)
    public void AndInt() throws ParserException {
        run("", "((element>1)&0)");
    }

    @Test(expected = TypeException.class)
    public void OrInt() throws ParserException {
        run("", "(-42|1)");
        run("", "((element>0)|0)");
    }

    @Test(expected = TypeException.class)
    public void MulBool() throws ParserException {
        run("", "(((element+element)<42)*(77+42))");
    }
    @Test(expected = TypeException.class)
    public void SubBool() throws ParserException {
        run("", "(((1+2)<3)-((element=element)&(element>element)))");
        run("", "(element-(1=0))");
        run("", "((element=0)--42)");
    }

    @Test(expected = TypeException.class)
    public void EqBool() throws ParserException {
        run("", "((1=1)=(element=(42+42)))");
    }
}

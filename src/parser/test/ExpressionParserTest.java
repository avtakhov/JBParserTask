package parser.test;

import base.Asserts;
import expression.TypeException;
import org.junit.Test;
import parser.*;
import parser.ParserException;

public class ExpressionParserTest {

    ExpressionParser parser = new ExpressionParser();

    @Test
    public void cnst() throws ParserException {
        String tmp = "42";
        Asserts.assertEquals("", tmp, parser.parse(tmp).toString());
    }

    @Test
    public void negate() throws ParserException {
        String tmp = "-10";
        Asserts.assertEquals("", tmp, parser.parse(tmp).toString());
    }

    @Test
    public void minInt() throws ParserException {
        String tmp = Integer.toString(Integer.MIN_VALUE);
        Asserts.assertEquals("", tmp, parser.parse(tmp).toString());
    }

    @Test
    public void element() throws ParserException {
        String tmp = "element";
        Asserts.assertEquals("", tmp, parser.parse(tmp).toString());
    }

    @Test
    public void simple() throws ParserException {
        String tmp = "(1+2)";
        Asserts.assertEquals("", tmp, parser.parse(tmp).toString());
    }

    @Test
    public void elementGreater() throws ParserException {
        String tmp = "(element>0)";
        Asserts.assertEquals("", tmp, parser.parse(tmp).toString());
    }

    @Test
    public void elementLess() throws ParserException {
        String tmp = "(element<10)";
        Asserts.assertEquals("", tmp, parser.parse(tmp).toString());
    }

    @Test
    public void medium() throws ParserException {
        String tmp = "((element<0)|(element=0))";
        Asserts.assertEquals("", tmp, parser.parse(tmp).toString());
    }

    @Test
    public void mediumConst() throws ParserException {
        String tmp = "(20391093+(-1+(-1--913993929)))";
        Asserts.assertEquals("", tmp, parser.parse(tmp).toString());
    }

    @Test
    public void bool() throws ParserException {
        String tmp = "(((-1<2)|(1>(2-1)))|(element>1000))";
        Asserts.assertEquals("", tmp, parser.parse(tmp).toString());
    }

    @Test
    public void mediumMinInt() throws ParserException {
        String minInt = Integer.toString(Integer.MIN_VALUE);
        String tmp = "((" + minInt + "-" + minInt + ")>(element*" + minInt + "))";
        Asserts.assertEquals("", tmp, parser.parse(tmp).toString());
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
        String tmp = "1000000000000000000000000";
        parser.parse(tmp);
    }

    @Test(expected = ParserException.class)
    public void invalidElement() throws ParserException {
        String tmp = "elementt";
        parser.parse(tmp);
    }

    @Test(expected = ParserException.class)
    public void invalidElement2() throws ParserException {
        String tmp = "elemen";
        parser.parse(tmp);
    }

    @Test(expected = ParserException.class)
    public void invalidElement3() throws ParserException {
        String tmp = "elemeent";
        parser.parse(tmp);
    }

    @Test(expected = ParserException.class)
    public void insertSymbolMiddle() throws ParserException {
        String tmp = "(1#0-3)";
        parser.parse(tmp);
    }

    @Test(expected = ParserException.class)
    public void insertSymbolEnd() throws ParserException {
        String tmp = "(10-3)#";
        parser.parse(tmp);
    }

    @Test(expected = ParserException.class)
    public void fullInvalid() throws ParserException {
        String tmp = "-8t4t2t";
        parser.parse(tmp);
    }

    @Test(expected = ParserException.class)
    public void noFirstArgument() throws ParserException {
        String tmp = "(>element)";
        parser.parse(tmp);
    }

    @Test(expected = ParserException.class)
    public void noLastArgument() throws ParserException {
        String tmp = "(element=)";
        parser.parse(tmp);
    }

    @Test(expected = ParserException.class)
    public void onlyBrackets() throws ParserException {
        String tmp = "()";
        parser.parse(tmp);
    }

    @Test(expected = ParserException.class)
    public void elementInBrackets() throws ParserException {
        String tmp = "(element)";
        parser.parse(tmp);
    }

    @Test(expected = ParserException.class)
    public void invalidOperator() throws ParserException {
        String tmp = "(element*|*element)";
        parser.parse(tmp);
    }

    @Test(expected = ParserException.class)
    public void doubleMinus() throws ParserException {
        String tmp = "(element--element)";
        parser.parse(tmp);
    }

    @Test(expected = ParserException.class)
    public void noBrackets() throws ParserException {
        String tmp = "(element>0)-(123+123)";
        parser.parse(tmp);
    }

    @Test(expected = ParserException.class)
    public void spaces() throws ParserException {
        String tmp = "(1 + 2)";
        parser.parse(tmp);
    }

    @Test(expected = TypeException.class)
    public void equalToBool() throws ParserException {
        String tmp = "((1+2)=(element>1))";
        parser.parse(tmp);
    }

    @Test(expected = TypeException.class)
    public void lessInt() throws ParserException {
        String tmp = "(element<(element<element))";
        parser.parse(tmp);
    }

    @Test(expected = TypeException.class)
    public void AndInt() throws ParserException {
        String tmp = "((element>1)&0)";
        parser.parse(tmp);
    }

    @Test(expected = TypeException.class)
    public void OrIntInt() throws ParserException {
        String tmp = "(1|1)";
        parser.parse(tmp);
    }

    @Test(expected = TypeException.class)
    public void MulBool() throws ParserException {
        String tmp = "(((element+element)<42)*(77+42))";
        parser.parse(tmp);
    }
    @Test(expected = TypeException.class)
    public void SubBoolBool() throws ParserException {
        String tmp = "(((1+2)<3)-((element=element)&(element>element)))";
        parser.parse(tmp);
    }

    @Test(expected = TypeException.class)
    public void EqBoolBool() throws ParserException {
        String tmp = "((1=1)=(element=(42+42)))";
        parser.parse(tmp);
    }
    @Test(expected = TypeException.class)
    public void hardType() throws ParserException {
        String tmp = "(((1+2)*(3+4))*(((5+6)*(7+8))*(element>(element*element))))";
        parser.parse(tmp);
    }
}

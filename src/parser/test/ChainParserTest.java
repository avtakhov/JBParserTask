package parser.test;

import base.Asserts;
import org.junit.Test;
import parser.ChainParser;
import expression.TypeException;
import parser.ParserException;

public class ChainParserTest {

    private final ChainParser parser = new ChainParser();

    @Test
    public void map() throws ParserException {
        String tmp = "map{(element+1)}";
        System.out.println(tmp + "    " + parser.simplify(tmp));
    }

    @Test
    public void filter() throws ParserException {
        String tmp = "filter{(-1>(element*element))}";
        System.out.println(tmp + "    " + parser.simplify(tmp));
    }

    @Test
    public void simpleCompose() throws ParserException {
        String tmp = "filter{(1<2)}%>%map{element}";
        System.out.println(tmp + "    " + parser.simplify(tmp));
    }

    @Test
    public void mapFilterMap() throws ParserException {
        String tmp = "map{(element+10)}%>%filter{(element>10)}%>%map{(element*element)}";
        System.out.println(tmp + "    " + parser.simplify(tmp));
    }

    @Test
    public void mapMap() throws ParserException {
        String tmp = "map{(element+(element-element))}%>%map{((element-element)+1)}";
        System.out.println(tmp + "    " + parser.simplify(tmp));
    }

    @Test
    public void hard() throws ParserException {
        String tmp = "filter{(((element*2)*(element+1))<0)}%>%map{(element+1000)}%>%map{(element+1000)}%>%map{(element+1000)}%>%filter{(element>0)}";
        System.out.println(tmp + "    " + parser.simplify(tmp));
    }

    @Test
    public void simplifyMagic() throws ParserException {
        String tmp = "map{(((element-2)*((element-4)*(element-8)))*(element-16))}%>%filter{(element>-2)}%>%filter{(element>-4)}%>%filter{(element>-8)}%>%filter{(element>-16)}";
        System.out.println(tmp + "    " + parser.simplify(tmp));
    }

    @Test(expected = ParserException.class)
    public void endBracket() throws ParserException {
        String tmp = "filter{(1<2)}%>%map{element";
        System.out.println(tmp + "    " + parser.simplify(tmp));
    }
    @Test(expected = ParserException.class)
    public void separatorMiddleMissing() throws ParserException {
        String tmp = "filter{(element>0)}%%map{(element*2)}";
        System.out.println(tmp + "    " + parser.simplify(tmp));
    }

    @Test(expected = ParserException.class)
    public void mapName() throws ParserException {
        String tmp = "emap{(element+10)}%>%filter{(element>10)}%>%map{(element*element)}";
        System.out.println(tmp + "    " + parser.simplify(tmp));
    }

    @Test(expected = ParserException.class)
    public void separatorMiddleAnother() throws ParserException {
        String tmp = "map{12}%>%map{(element*2)}%<%filter{(1=0)}";
        System.out.println(tmp + "    " + parser.simplify(tmp));
    }

    @Test(expected = TypeException.class)
    public void mapBool() throws ParserException {
        String tmp = "map{(element>0)}";
        parser.simplify(tmp);
    }

    @Test(expected = TypeException.class)
    public void filterInt() throws ParserException {
        String tmp = "filter{42}";
        parser.simplify(tmp);
    }

}

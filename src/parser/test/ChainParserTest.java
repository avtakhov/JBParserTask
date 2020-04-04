package parser.test;

import org.junit.Test;
import parser.ChainParser;
import expression.TypeException;
import parser.ParserException;

public class ChainParserTest {

    private final ChainParser parser = new ChainParser();

    private void run(String s) throws ParserException {
        System.out.println(s);
        System.out.println(parser.simplify(s));
        System.out.println();
    }

    @Test
    public void map() throws ParserException {
        run("map{42}");
        run("map{(-42+(element*element))}");
        run("map{(1+((element*0)+((element*element)-(element*element))))}");
    }

    @Test
    public void filter() throws ParserException {
        run("filter{(0=0)}");
        run("filter{(element<1)}");
        run("filter{(-1>(element*element))}");
    }

    @Test
    public void swap() throws ParserException {
        run("map{element}%>%filter{(element=1)}");
    }

    @Test
    public void mapFilterMap() throws ParserException {
        run("map{(element+10)}%>%filter{(element>10)}%>%map{(element*element)}");
    }

    @Test
    public void mapMap() throws ParserException {
        run("map{(1+2)}%>%map{(element+1)}");
        run("map{(element+(element-element))}%>%map{((element-element)+1)}");
    }

    @Test
    public void hard() throws ParserException {
        run("filter{(((element*2)*(element+1))<0)}%>%map{(element+1000)}%>%map{(element+1000)}%>%map{(element+1000)}%>%filter{(element>0)}");
        run("map{(((element-2)*((element-4)*(element-8)))*(element-16))}%>%filter{(element>-2)}%>%filter{(element>-4)}%>%filter{(element>-8)}%>%filter{(element>-16)}");
    }

    @Test
    public void simplifyAdd() throws ParserException {
        run("map{(element+1)}%>%filter{((element-1)>((element-1)*(element-1)))}");
    }

    @Test(expected = ParserException.class)
    public void endBracket() throws ParserException {
        run("filter{(1<2)}%>%map{element");
    }
    @Test(expected = ParserException.class)
    public void separator() throws ParserException {
        run("filter{(element>0)}%%map{(element*2)}");
        run("map{element}filter{(element>0)}");
        run("map{element}%<%filter{(1=0)}");
    }

    @Test(expected = ParserException.class)
    public void mapName() throws ParserException {
        run("mape{(element+10)}%>%filter{(element>10)}%>%map{(element*element)}");
    }

    @Test(expected = TypeException.class)
    public void mapBool() throws ParserException {
        run("map{(element>0)}");
        run("filter{(element<(element*element))}%>%map{((element>0)|(element=0))}");
    }

    @Test(expected = TypeException.class)
    public void filterInt() throws ParserException {
        run("filter{42}");
        run("filter{(element+1)}");
        run("filter{(element+(element-(element+(element-42))))}");
    }
}

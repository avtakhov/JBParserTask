package parser.test;

import org.junit.Test;
import parser.ChainParser;
import expression.TypeException;
import parser.ParserException;

import java.util.List;

public class ChainParserTest extends BaseTest {

    private final ChainParser parser = new ChainParser();

    @Test
    public void runCorrect() throws ParserException {
        List<String> tests = List.of(
                "filter{(1<2)}%>%map{element}",
                "filter{(element>0)}%>%map{(element*2)}",
                "filter{(element>10)}%>%filter{(element<20)}",
                "map{(element+10)}%>%filter{(element>10)}%>%map{(element*element)}",
                "map{(element+10)}%>%filter{(element>10)}%>%map{(element*element)}",
                "filter{(element>0)}%>%filter{(element<0)}%>%map{(element*element)}",
                "map{((element*element)*(element-1))}%>%filter{(((element*2)-(element-1))=1)}",
                "map{(element+(element-element))}%>%map{((element-element)+1)}",
                "map{12}%>%map{(element*2)}%>%filter{(1=0)}",
                "filter{(((element*2)*(element+1))<0)}%>%map{(element+1000)}%>%map{(element+1000)}%>%map{(element+1000)}%>%filter{(element>0)}"
        );
        run(tests);
    }

    @Test
    public void runSyntaxError() throws ParserException {
        List<String> tests = List.of(
                "filter{(1<2)}%>%map{element",
                "filter{(element>0)}%%map{(element*2)}",
                "filter{(elemnt>10)}%>%filter{(element<20)}",
                "map{(element10)}%>%filter{(element>10)}%>%map{(element*element)}",
                "emap{(element+10)}%>%filter{(element>10)}%>%map{(element*element)}",
                "filt$er{(element>0)}%>%filter{(element<0)}%>%map{(element*element)}",
                "map{((element*element)*(element-1))}%>%filter{(((element*2)--(element-1))=1)}",
                "map{(element+(element-element)}%>%map{((element-element)+1)}",
                "map{12}%>%map{(element*2)}%>%filter{(1>=0)}",
                "filter{(((element*2)*(element+1))<0)}%>%map{(element+1000)}%>%map{(element+1000)}%>%map{(element+1000)}%>%filter{(-element>-100)}",
                "map{12}%>%map{(element*2)}%<%filter{(1=0)}",
                "map{12}%>%mapp{(element*2)}%>%filter{(1=0)}",
                "map{12}%>%mapp{(element*2)}%>%filter{(1>0)}{(1=0)}",
                "adiaspdoaisdopasd",
                "1",
                "(1+2)"
        );
        runExpectingError(tests, ParserException.class);
    }

    @Test
    public void runTypeError() throws ParserException {
        List<String> tests = List.of(
                "filter{(1+element)}",
                "map{((1+element)<1)}"

        );
        runExpectingError(tests, TypeException.class);
    }

    @Override
    void testOne(String test) throws ParserException {
        parser.simplify(test);
    }
}
package chain.test;

import chain.ChainParser;
import parser.exception.ParserException;

import java.util.List;

public class ChainParserTest {

    private static void run(List<String> tests, ChainParser parser) {
        for (String i : tests) {
            System.out.print(i + "     ");
            try {
                System.out.println(parser.simplify(i));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {

        ChainParser parser = new ChainParser();

        List<String> correctTests = List.of(
                "filter{(1<2)}%>%map{element}",
                "filter{(element>0)}%>%map{(element*2)}",
                "filter{(element>10)}%>%filter{(element<20)}",
                "map{(element+10)}%>%filter{(element>10)}%>%map{(element*element)}",
                "map{(element+10)}%>%filter{(element>10)}%>%map{(element*element)}",
                "filter{(element>0)}%>%filter{(element<0)}%>%map{(element*element)}"
        );
        run(correctTests, parser);
    }

}
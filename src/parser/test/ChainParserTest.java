package parser.test;

import parser.ChainParser;
import expression.TypeException;
import parser.ParserException;

import java.util.List;

public class ChainParserTest {

    private static void run(List<String> tests, ChainParser parser) {
        for (String i : tests) {
            System.out.print(i + "     ");
            try {
                System.out.println(parser.simplify(i));
            } catch (ParserException e) {
                System.out.println("SYNTAX ERROR " + e.getMessage());
            } catch (TypeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {

        ExpressionParserTest.main(new String[]{});

        ChainParser parser = new ChainParser();
        List<String> correctTests = List.of(
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

        List<String> syntaxTests = List.of(
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

        List<String> typeTest = List.of(
                "filter{(1+element)}",
                "map{((1+element)<1)}"
        );

        run(correctTests, parser);
        System.out.println("====================================");
        run(syntaxTests, parser);
        System.out.println("====================================");
        run(typeTest, parser);
        System.out.println("====================================");
    }

}
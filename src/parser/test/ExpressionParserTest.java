package parser.test;

import parser.*;
import parser.exception.ParserException;

import java.util.List;

public class ExpressionParserTest {

    private static void run(List<String> tests, Parser parser) {
        for (String s : tests) {
            System.out.print(s + " ");
            try {
                System.out.println(parser.parse(s).toString());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public static void main(String[] args) throws ParserException {

        Parser parser = new ExpressionParser();
        List<String> correctTests = List.of(
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

        List<String> syntaxTests = List.of(
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

        List<String> typeTests = List.of(
                "(1+(1>element))",
                "(1=(3=3))",
                "(element<(element<0))",
                "((1>2)+(3=3))",
                "((1+2)&3)",
                "(1+((element+1)=3))",
                "((1000*1000)=(element=element))"
        );

        run(correctTests, parser);
        System.out.println("============================================");
        run(syntaxTests, parser);
        System.out.println("============================================");
        run(typeTests, parser);

    }
}

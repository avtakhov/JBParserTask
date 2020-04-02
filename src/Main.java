import parser.ChainParser;
import expression.TypeException;
import parser.ParserException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ChainParser parser = new ChainParser();
        try {
            System.out.println(parser.simplify(in.nextLine()));
        } catch (ParserException e) {
            System.out.println("SYNTAX ERROR");
        } catch (TypeException e) {
            System.out.println("TYPE ERROR");
        }
    }
}

package parser;

import base.Asserts;

import java.util.Arrays;

public class BaseParser {
    final int BUFFER_SIZE = 10;
    protected final char[] buffer = new char[BUFFER_SIZE];
    private StringSource source;

    public void setSource(StringSource source) {
        this.source = source;
        skipChars(BUFFER_SIZE);
    }

    protected void skipChars(int count) {
        while (count-- > 0) {
            nextChar();
        }
    }

    protected char getCh() {
        return buffer[0];
    }

    protected void nextChar() {
        System.arraycopy(buffer, 1, buffer, 0, BUFFER_SIZE - 1);
        buffer[BUFFER_SIZE - 1] = source.hasNext() ? source.next() : '\0';
    }

    protected boolean hasNext() {
        return getCh() != '\0';
    }

    protected boolean test(char expected) {
        if (getCh() == expected) {
            nextChar();
            return true;
        }
        return false;
    }

    protected boolean test(String s) {
        Asserts.assertTrue("string test over of buffer", s.length() <= buffer.length);
        if (String.valueOf(buffer, 0, s.length()).equals(s)) {
            skipChars(s.length());
            return true;
        }
        return false;
    }

    protected void expect(final char c) throws ParserException {
        if (getCh() != c) {
            throw new ParserException("Expected '" + c + "', found " + (getCh() == '\0'? "EOF" : "'" + getCh() + "'"));
        }
        nextChar();
    }

    protected void expect(final String value) throws ParserException {
        for (char c : value.toCharArray()) {
            expect(c);
        }
    }
}
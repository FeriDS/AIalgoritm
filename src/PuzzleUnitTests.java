import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
public class PuzzleUnitTests {
    @Test
    public void testConstructor() {
        Board b = new Board("023145678");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter ( writer ) ;
        pw.println(" 23");
        pw.println("145");
        pw.println("678");
        assertEquals(b.toString(), writer.toString());
        pw.close();
    }
    @Test
    public void testConstructor2() {
        Board b = new Board("123485670");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter (writer) ;
        pw.println("123");
        pw.println("485");
        pw.println("67 ");
        assertEquals(b.toString(), writer.toString());
        pw.close();
    }

    @Test
    public void testEquals1() {
        Board b = new Board("023145678");
        Board b2 = new Board("023145678");
        assertEquals(b, b2);
    }
    @Test
    public void testEquals2() {
        Board b = new Board("023145678");
        Board b2 = new Board("013145678");
        assertNotEquals(b, b2);
    }
    @Test
    public void testEquals3() {
        int b = 2;
        Board b2 = new Board("013145678");
        assertNotEquals(b2, b);
    }
}
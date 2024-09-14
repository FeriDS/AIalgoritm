import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

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

    /*
    @Test
    public void testMoveLeft1() throws CloneNotSupportedException {
        Board b = new Board("203145678");
        Board b2 = new Board("023145678");
        assertEquals(b.moveLeft(0, 1), b2);
    }

    @Test
    public void testMoveRight1() throws CloneNotSupportedException {
        Board b = new Board("203145678");
        Board b2 = new Board("023145678");
        assertEquals(b2.moveRight(0, 0), b);
    }

    @Test
    public void testMoveUp1() throws CloneNotSupportedException {
        Board b = new Board("123045678");
        Board b2 = new Board("023145678");
        assertEquals(b.moveUp(1, 0), b2);
    }

    @Test
    public void testMoveDown1() throws CloneNotSupportedException {
        Board b = new Board("123045678");
        Board b2 = new Board("023145678");
        assertEquals(b2.moveDown(0, 0), b);
    }
     */

    @Test //0 no canto superior esquerdo
    public void testChildren1() {
        Board b = new Board("023145678");
        Board b2 = new Board("203145678");
        Board b3 = new Board("123045678");
        List<Ilayout> children2 = new ArrayList<Ilayout>();
        children2.add(b3);
        children2.add(b2);
        assertEquals(children2, b.children());
    }
    @Test //0 no canto inferior direito
    public void testChildren2() {
        Board b = new Board("123456780");
        Board b2 = new Board("123450786");
        Board b3 = new Board("123456708");
        List<Ilayout> children2 = new ArrayList<Ilayout>();
        children2.add(b2);
        children2.add(b3);
        assertEquals(children2, b.children());
    }
    @Test //0 no meio do grid
    public void testChildren3() {
        Board b = new Board("123405678");
        Board b2 = new Board("123475608");
        Board b3 = new Board("103425678");
        Board b4 = new Board("123450678");
        Board b5 = new Board("123045678");
        List<Ilayout> children2 = new ArrayList<Ilayout>();
        children2.add(b2);
        children2.add(b3);
        children2.add(b4);
        children2.add(b5);
        assertEquals(children2, b.children());
    }
}
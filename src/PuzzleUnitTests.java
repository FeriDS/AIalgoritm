import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    @Test
    public void testSolve1() throws CloneNotSupportedException {
        Board b = new Board("123405678");
        Board b2 = new Board("123405678");
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.solve(b, b2);
        while(it.hasNext()) {
            BestFirst.State i = it.next();
            assertEquals(i.toString(), b2.toString());
        }
    }

    @Test
    public void testSolve2() throws CloneNotSupportedException {
        Board b = new Board("123045678");
        Board b2 = new Board("123405678");
        Board[] a = {b, b2};
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.solve(b, b2);
        int j = 0;
        while(it.hasNext()) {
            BestFirst.State i = it.next();
            assertEquals(i.toString(), a[j++].toString());
        }
    }

    @Test
    public void testSolve3() throws CloneNotSupportedException {
        Board b = new Board("023145678");
        Board b2 = new Board("123045678");
        Board b3 = new Board("123405678");
        Board[] a = {b, b2, b3};
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.solve(b, b3);
        int j = 0;
        while(it.hasNext()) {
            BestFirst.State i = it.next();
            assertEquals(i.toString(), a[j++].toString());
        }
    }

    @Test
    public void testSolve4() throws CloneNotSupportedException {
        Board b = new Board("123456780");
        Board b2 = new Board("123456708");
        Board b3 = new Board("123406758");
        Board b4 = new Board("103426758");
        Board b5 = new Board("013426758");
        Board b6 = new Board("413026758");
        Board b7 = new Board("413726058");
        Board b8 = new Board("413726508");
        Board b9 = new Board("413706528");
        Board b10 = new Board("403716528");
        Board b11 = new Board("430716528");
        Board b12 = new Board("436710528");
        Board b13 = new Board("436718520");

        Board[] a = {b, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13};
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.solve(b, b13);
        int j = 0;
        while(it.hasNext()) {
            BestFirst.State i = it.next();
            assertEquals(i.toString(), a[j++].toString());
        }
    }
}
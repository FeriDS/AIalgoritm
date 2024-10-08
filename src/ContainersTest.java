import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;

class ContainersTest {


    @Test
    public void testConstructor1() {
        Containers c1 = new Containers("A1B2 C3");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        pw.println("[A, B]");
        pw.println("[C]");
        assertEquals(c1.toString(), sw.toString());
        pw.close();
    }
    @Test
    public void testConstructor2() {
        Containers c1 = new Containers("A1 B2 C3");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        pw.println("[A]");
        pw.println("[B]");
        pw.println("[C]");
        assertEquals(c1.toString(), sw.toString());
        pw.close();
    }
    @Test
    public void testConstructor3() {
        Containers c1 = new Containers("A1B2C3");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        pw.println("[A, B, C]");
        assertEquals(c1.toString(), sw.toString());
        pw.close();
    }
    @Test
    public void testConstructor4() {
        Containers c1 = new Containers("A5B6 C1 D1 E1 F1 G1 H1");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        pw.println("[A, B]");
        pw.println("[C]");
        pw.println("[D]");
        pw.println("[E]");
        pw.println("[F]");
        pw.println("[G]");
        pw.println("[H]");
        assertEquals(c1.toString(), sw.toString());
        pw.close();
    }
    @Test
    public void testConstructor5() {
        Containers c1 = new Containers("B2C3 A1");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        pw.println("[A]");
        pw.println("[B, C]");
        assertEquals(c1.toString(), sw.toString());
        pw.close();
    }

    @Test
    public void testClone() {
        Containers c1 = new Containers("A1 B2 C3");
        Containers c2 = new Containers("A1B2 C3");
        Containers c3 = new Containers("A1 B2C3");
        Containers c4 = new Containers("A1B2C3");
        Containers c5 = new Containers("A1 B2 C3 D2");
        assertEquals(c1, c1.clone());
        assertEquals(c2, c2.clone());
        assertEquals(c3, c3.clone());
        assertEquals(c4, c4.clone());
        assertEquals(c5, c5.clone());
    }


    @Test
    public void testEquals1() {
        Containers c1 = new Containers("A1 B2 C3");
        Containers c2 = new Containers("A1 B2 C3");
        assertEquals(c1, c2);
    }
    @Test
    public void testEquals2() {
        Containers c1 = new Containers("A1 B2 C3");
        Containers c2 = new Containers("B2 A1 C3");
        assertEquals(c1, c2);
    }
    @Test
    public void testEquals3() {
        Containers c1 = new Containers("A1 B2 C3");
        Containers c2 = new Containers("A1 B2");
        assertNotEquals(c1, c2);
        assertNotEquals(c2, c1);
    }
    @Test
    public void testEquals4() {
        Containers c1 = new Containers("A1B2 B3");
        Containers c2 = new Containers("A2C1 C3");
        assertNotEquals(c1, c2);
    }
}
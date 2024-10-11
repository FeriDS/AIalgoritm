import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;

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
    @Test
    public void testEquals5() {
        Containers c1 = new Containers("A1 B2 C3");
        Containers c2 = new Containers("A B C");
        assertEquals(c1, c2);
    }



    @Test
    public void testChildren1() {
        Containers c1 = new Containers("A1B2 C3");
        Containers c2 = new Containers("A1 B2 C3");
        Containers c3 = new Containers("A1 C3B2");
        Containers c4 = new Containers("A1B2C3");
        Containers c38 = new Containers("A5B6 C1 D1 E1G1 F1 H1");
        ArrayList<Containers> children = new ArrayList<>();
        children.add(c2);
        children.add(c3);
        children.add(c4);
        assertEquals(c1.children(), children);
    }

    @Test
    public void testChildren2() {
        Containers c1 = new Containers("A5B6 C1 D1 E1 F1 G1 H1");
        Containers c2 = new Containers("A5 B6 C1 D1 E1 F1 G1 H1");
        Containers c3 = new Containers("A5 C1B6 D1 E1 F1 G1 H1");
        Containers c4 = new Containers("A5 C1 D1B6 E1 F1 G1 H1");
        Containers c5 = new Containers("A5 C1 D1 E1B6 F1 G1 H1");
        Containers c6 = new Containers("A5 C1 D1 E1 F1B6 G1 H1");
        Containers c7 = new Containers("A5 C1 D1 E1 F1 G1B6 H1");
        Containers c8 = new Containers("A5 C1 D1 E1 F1 G1 H1B6");
        Containers c10 = new Containers("A5B6C1 D1 E1 F1 G1 H1");
        Containers c11 = new Containers("A5B6 D1C1 E1 F1 G1 H1");
        Containers c12 = new Containers("A5B6 D1 E1C1 F1 G1 H1");
        Containers c13 = new Containers("A5B6 D1 E1 F1C1 G1 H1");
        Containers c14 = new Containers("A5B6 D1 E1 F1 G1C1 H1");
        Containers c15 = new Containers("A5B6 D1 E1 F1 G1 H1C1");
        Containers c16 = new Containers("A5B6D1 C1 E1 F1 G1 H1");
        Containers c17 = new Containers("A5B6 C1D1 E1 F1 G1 H1");
        Containers c18 = new Containers("A5B6 C1 E1D1 F1 G1 H1");
        Containers c19 = new Containers("A5B6 C1 E1 F1D1 G1 H1");
        Containers c20 = new Containers("A5B6 C1 E1 F1 G1D1 H1");
        Containers c21 = new Containers("A5B6 C1 E1 F1 G1 H1D1");
        Containers c22 = new Containers("A5B6E1 C1 D1 F1 G1 H1");
        Containers c23 = new Containers("A5B6 C1E1 D1 F1 G1 H1");
        Containers c24 = new Containers("A5B6 C1 D1E1 F1 G1 H1");
        Containers c25 = new Containers("A5B6 C1 D1 F1E1 G1 H1");
        Containers c27 = new Containers("A5B6 C1 D1 F1 G1E1 H1");
        Containers c28 = new Containers("A5B6 C1 D1 F1 G1 H1E1");
        Containers c29 = new Containers("A5B6F1 C1 D1 E1 G1 H1");
        Containers c30 = new Containers("A5B6 C1F1 D1 E1 G1 H1");
        Containers c31 = new Containers("A5B6 C1 D1F1 E1 G1 H1");
        Containers c32 = new Containers("A5B6 C1 D1 E1F1 G1 H1");
        Containers c33 = new Containers("A5B6 C1 D1 E1 G1F1 H1");
        Containers c34 = new Containers("A5B6 C1 D1 E1 G1 H1F1");
        Containers c35 = new Containers("A5B6G1 C1 D1 E1 F1 H1");
        Containers c36 = new Containers("A5B6 C1G1 D1 E1 F1 H1");
        Containers c37 = new Containers("A5B6 C1 D1G1 E1 F1 H1");
        Containers c38 = new Containers("A5B6 C1 D1 E1G1 F1 H1");
        Containers c39 = new Containers("A5B6 C1 D1 E1 F1G1 H1");
        Containers c40 = new Containers("A5B6 C1 D1 E1 F1 H1G1");
        Containers c41 = new Containers("A5B6H1 C1 D1 E1 F1 G1");
        Containers c42 = new Containers("A5B6 C1H1 D1 E1 F1 G1");
        Containers c43 = new Containers("A5B6 C1 D1H1 E1 F1 G1");
        Containers c44 = new Containers("A5B6 C1 D1 E1H1 F1 G1");
        Containers c45 = new Containers("A5B6 C1 D1 E1 F1H1 G1");
        Containers c47 = new Containers("A5B6 C1 D1 E1 F1 G1H1");

        ArrayList<Containers> children = new ArrayList<>();
        children.add(c2);
        children.add(c3);
        children.add(c4);
        children.add(c5);
        children.add(c6);
        children.add(c7);
        children.add(c8);
        children.add(c10);
        children.add(c11);
        children.add(c12);
        children.add(c13);
        children.add(c14);
        children.add(c15);
        children.add(c16);
        children.add(c17);
        children.add(c18);
        children.add(c19);
        children.add(c20);
        children.add(c21);
        children.add(c22);
        children.add(c23);
        children.add(c24);
        children.add(c25);
        children.add(c27);
        children.add(c28);
        children.add(c29);
        children.add(c30);
        children.add(c31);
        children.add(c32);
        children.add(c33);
        children.add(c34);
        children.add(c35);
        children.add(c36);
        children.add(c37);
        children.add(c38);
        children.add(c39);
        children.add(c40);
        children.add(c41);
        children.add(c42);
        children.add(c43);
        children.add(c44);
        children.add(c45);
        children.add(c47);

        assertEquals(c1.children(), children);
    }

    @Test
    public void testCon() {
        Containers c1 = new Containers("B2A1 C3");
        c1.children().add(c1);
    }

    @Test
    public void testSolve() throws Exception {
        Containers c1 = new Containers("A1 B2 C3");
        Containers c2 = new Containers("A1B2 C3");
        Containers c3 = new Containers("A1B2C3");
        Containers c4 = new Containers("ABC");
        Containers[] a = {c1, c2, c3, c4};
        BestFirst s = new BestFirst();
        Iterator<BestFirst.State> it = s.solve(c1, c4);
        int j = 0;
        while(it.hasNext()) {
            BestFirst.State i = it.next();
            assertEquals(i.toString(), a[j++].toString());
        }
    }
}
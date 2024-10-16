import java.util.Iterator;
import java.util.Scanner;
@SuppressWarnings("all")
public class Main {
    public static void main (String [] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        AStar s = new AStar();
        Containers c = new Containers(sc.nextLine());
        Containers goal = new Containers(sc.nextLine());
        double result = s.solve(c, goal);
        System.out.println(goal);
        System.out.println((int) result);
        sc.close();
    }
}
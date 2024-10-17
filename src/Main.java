import java.util.Iterator;
import java.util.Scanner;
@SuppressWarnings("all")
public class Main {
    public static void main (String [] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        AStar s = new AStar();
        Containers c = new Containers(sc.nextLine());
        Containers goal = new Containers(sc.nextLine());
        ContainersStats stats = new ContainersStats();

        stats.getStartTime();
        double result = s.solve(c, goal);
        stats.getEndTime();

        stats.setExpandedNodes(s.getFechadosSize());
        stats.setGeneratedNodes(s.getFechadosSize() + s.getAbertosSize());
        stats.setSolutionLength(s.solutionLength());

        System.out.println(goal);
        System.out.println((int) result);
        //stats.printStats();

        sc.close();
    }
}
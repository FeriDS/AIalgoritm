import org.testng.internal.collections.Pair;

import java.util.List;
import java.util.Stack;


public class Containers implements Ilayout{
    private final int stackSize = 26;
    private Stack<Pair<Character, Integer>>[] containers;
    private int cachedHash = -1;
    private int cost = 0;
    public Containers() {
        containers = new Stack[stackSize];
        for (int i = 0; i < stackSize -1; i++) {
            containers[i] = new Stack<>();
        }
    }
    public Containers(String str) {
        containers = new Stack[stackSize];
        for (int i = 0; i < stackSize -1; i++) {
            containers[i] = new Stack<>();
        }
        int buffer = str.charAt(0) - 65;
        containers[buffer].push(new Pair<>(str.charAt(0), Character.getNumericValue(str.charAt(1))));
        for (int i = 2; i < str.length(); i++) {
            if(str.charAt(i)==' '){
                buffer = str.charAt(++i) - 65;
                containers[buffer].push(new Pair<>(str.charAt(i), Character.getNumericValue(str.charAt(++i))));
            }
            else
                containers[buffer].push(new Pair<>(str.charAt(i), Character.getNumericValue(str.charAt(++i))));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Containers o1)) return false;
        for (int i = 0; i < stackSize - 1; i++) {
            if ((o1.containers[i].isEmpty() && !containers[i].isEmpty()) || (!o1.containers[i].isEmpty() && containers[i].isEmpty()))
                return false;
            for (int j = 0; j < containers[i].size(); j++) {
                if (!containers[i].get(j).equals(o1.containers[i].get(j)))
                    return false;
            }
        }
        return true;
    }

    @Override
    public Object clone() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stackSize - 1; i++) {
            for (int j = 0; j < containers[i].size(); j++) {
                sb.append(containers[i].get(j).first());
                sb.append(containers[i].get(j).second());
            }
            if (i != stackSize - 2 && !containers[i + 1].isEmpty())
                sb.append(" ");
        }
        return new Containers(sb.toString());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stackSize - 1; i++) {
            if (containers[i] != null && !containers[i].isEmpty()) {
                sb.append("[");
                for (int j = 0; j < containers[i].size(); j++) {
                    sb.append(containers[i].get(j).first());
                    if (j < containers[i].size() - 1)
                        sb.append(", ");
                }
                sb.append("]\r\n");
            }
        }

        return sb.toString();
    }


    public int hashCode() {
        if (cachedHash != -1)
            return cachedHash;
        double result = 0;
        int prime1 = 3;
        int prime2 = 7;

        for (int i = 0; i < stackSize; i++) {
            for (int j = 0; j < containers[i].size(); j++) {
                result += (Character.getNumericValue(containers[i].get(j).first()) * containers[i].get(j).second())
                * Math.pow(prime1, j) * Math.pow(prime2, i);
            }
        }
        cachedHash = (int) result;
        return cachedHash;
    }

    private void setCost(int cost){
        this.cost = cost;
    }

    @Override
    public List<Ilayout> children() {
        return List.of();
    }

    @Override
    public boolean isGoal(Ilayout l) {
        return this.equals(l);
    }

    @Override
    public double getK() {
        return cost;
    }
}

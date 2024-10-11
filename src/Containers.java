import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class Containers implements Ilayout{
    static class stack {
        private final Stack<Pair<Character, Integer>> stack = new Stack<>();
    }
    private final int stackSize = 26;
    private final stack[] containers;
    private int cachedHash = -1;
    private int cost = 0;

    public Containers(String str) {
        containers = new stack[stackSize];
        int buffer = str.charAt(0) - 65;
        for (int i = 0; i < stackSize -1; i++) {
            containers[i] = new stack();
        }
        if (Character.isDigit(str.charAt(1))) {
            containers[buffer].stack.push(new Pair<>(str.charAt(0), Character.getNumericValue(str.charAt(1))));
            for (int i = 2; i < str.length(); i++) {
                if(str.charAt(i)==' '){
                    buffer = str.charAt(++i) - 65;
                    containers[buffer].stack.push(new Pair<>(str.charAt(i), Character.getNumericValue(str.charAt(++i))));
                }
                else
                    containers[buffer].stack.push(new Pair<>(str.charAt(i), Character.getNumericValue(str.charAt(++i))));
            }
        }
        else {
            containers[buffer].stack.push(new Pair<>(str.charAt(0), null));
            for (int i = 1; i < str.length(); i++) {
                if(str.charAt(i)==' '){
                    buffer = str.charAt(++i) - 65;
                    containers[buffer].stack.push(new Pair<>(str.charAt(i), null));
                }
                else
                    containers[buffer].stack.push(new Pair<>(str.charAt(i), null));
            }
        }

    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Containers o1)) return false;
        for (int i = 0; i < stackSize - 1; i++) {
            if ((o1.containers[i].stack.isEmpty() && !containers[i].stack.isEmpty()) || (!o1.containers[i].stack.isEmpty() && containers[i].stack.isEmpty()))
                return false;
            for (int j = 0; j < containers[i].stack.size(); j++) {
                if (!containers[i].stack.get(j).equals(o1.containers[i].stack.get(j)))
                    return false;
            }
        }
        return true;
    }

    @SuppressWarnings("all")
    @Override
    public Object clone() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stackSize - 1; i++) {
            for (int j = 0; j < containers[i].stack.size(); j++) {
                sb.append(containers[i].stack.get(j).first());
                sb.append(containers[i].stack.get(j).second());
            }
            if (i != stackSize - 2 && !containers[i + 1].stack.isEmpty() && !sb.isEmpty())
                sb.append(" ");
        }
        return new Containers(sb.toString());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stackSize - 1; i++) {
            if (containers[i] != null && !containers[i].stack.isEmpty()) {
                sb.append("[");
                for (int j = 0; j < containers[i].stack.size(); j++) {
                    sb.append(containers[i].stack.get(j).first());
                    if (j < containers[i].stack.size() - 1)
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

        for (int i = 0; i < stackSize - 1; i++) {
            for (int j = 0; j < containers[i].stack.size() - 1; j++) {
                result += (Character.getNumericValue(containers[i].stack.get(j).first()) * containers[i].stack.get(j).second())
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
        List<Ilayout> children = new ArrayList<>();
        Containers buffer;
        Pair<Character, Integer> pairBuffer;
        for (int i = 0; i < stackSize - 1; i++) {
            buffer = (Containers) this.clone();
            if (containers[i] != null && !containers[i].stack.isEmpty()) {
                pairBuffer = buffer.containers[i].stack.pop();
                if (containers[i].stack.size() > 1) {
                    buffer.containers[pairBuffer.first() - 65].stack.add(pairBuffer);
                    buffer.setCost(pairBuffer.second());
                    children.add(buffer);
                }
                for (int j = 0; j < stackSize - 1; j++) {
                    buffer = (Containers) this.clone();
                    pairBuffer = buffer.containers[i].stack.pop();
                    if (!containers[j].stack.isEmpty() && j != i) {
                        buffer.containers[j].stack.add(pairBuffer);
                        buffer.setCost(pairBuffer.second());
                        children.add(buffer);
                    }
                }
            }
        }
        return children;
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

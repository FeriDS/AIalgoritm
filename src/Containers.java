import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class Containers implements Ilayout{
    private static final int stackSize = 52;
    private final Stack<Pair<Character, Integer>>[] containers;
    private int cachedHash = -1;
    private int cost = 0;
    protected int lastIndex = 0;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public Containers() {
        containers = (Stack<Pair<Character, Integer>>[]) new Stack[stackSize];
    }
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Containers(String str) {
        containers = (Stack<Pair<Character, Integer>>[]) new Stack[stackSize];
        int buffer = calcIndex(str.charAt(0));
        int indexbuff = 0;
        containers[buffer] = new Stack<>();
        if (Character.isDigit(str.charAt(1))) {
            containers[buffer].push(new Pair<>(str.charAt(0), Character.getNumericValue(str.charAt(1))));
            for (int i = 2; i < str.length(); i++) {
                if(str.charAt(i)==' '){
                    buffer = calcIndex(str.charAt(++i));
                    if (lastIndex < buffer) {
                        lastIndex = buffer;
                    }
                    containers[buffer] = new Stack<>();
                    containers[buffer].push(new Pair<>(str.charAt(i), Character.getNumericValue(str.charAt(++i))));
                }
                else {
                    indexbuff =calcIndex(str.charAt(i));
                    if (lastIndex < indexbuff) {
                    lastIndex = indexbuff;
                    }
                    containers[buffer].push(new Pair<>(str.charAt(i), Character.getNumericValue(str.charAt(++i))));
                }
            }
        }
        else {
            containers[buffer].push(new Pair<>(str.charAt(0), null));
            for (int i = 1; i < str.length(); i++) {
                if(str.charAt(i)==' '){
                    buffer = calcIndex(str.charAt(++i));
                    containers[buffer] = new Stack<>();
                    containers[buffer].push(new Pair<>(str.charAt(i), null));
                }
                else
                    containers[buffer].push(new Pair<>(str.charAt(i), null));
            }
        }
    }

    private int calcIndex(Character a) {
        return a - 65 > 26 ? a - 71 : a - 65;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Containers o1)) return false;
        Stack<Pair<Character, Integer>> thisContainer;
        Stack<Pair<Character, Integer>> thatContainer;
        for (int i = 0; i < lastIndex + 1; i++) {
            thisContainer = containers[i];
            thatContainer = o1.containers[i];
            if ((thatContainer == null && thisContainer != null) || (thatContainer != null && thisContainer == null))
                return false;
            if (thisContainer == null && thatContainer == null) continue;
            if (thisContainer.size() != thatContainer.size()) return false;
            for (int j = 0; j < thisContainer.size(); j++) {
                if (thisContainer.get(j).first() != thatContainer.get(j).first())
                    return false;
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object clone() {

        Containers clone = new Containers();
        clone.lastIndex = lastIndex;
        clone.cachedHash = -1;
        for (int i = 0; i < lastIndex + 1; i++) {
            if (containers[i] == null) continue;
            clone.containers[i] = (Stack<Pair<Character, Integer>>) containers[i].clone();
        }
        return clone;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.containers.length; i++) {
            if (containers[i] == null) continue;
            sb.append("[");
            for (int j = 0; j < containers[i].size() - 1; j++) {
                sb.append(containers[i].get(j).first());
                sb.append(", ");
            }
            sb.append(containers[i].getLast().first());
            sb.append("]\r\n");
        }
        return sb.toString();
    }

    @Override
    public Stack<Pair<Character, Integer>>[] getLayout() {
        return containers;
    }

    public int hashCode() {
        if (cachedHash != -1)
            return cachedHash;
        final int stacksPrime = 7;
        final int containersPrime = 11;
        int currStacksQuotient = 1;
        int currContainersQuotient;
        int hash = 0;

        for (int i = 0; i < lastIndex + 1; i++)
        {
            currStacksQuotient *= stacksPrime;
            currContainersQuotient = 1;
            Stack<Pair<Character, Integer>> currStack = containers[i];

            if (currStack == null) continue;
            for (Pair<Character, Integer> characterIntegerPair : currStack) {
                hash += (i + 1) * currStacksQuotient  * characterIntegerPair.first() * currContainersQuotient;
                currContainersQuotient *= containersPrime;
            }
        }
        cachedHash = hash;
        return hash;
        /*
        if (cachedHash != -1)
            return cachedHash;
        double result = 0;
        int prime1 = 3;
        int prime2 = 7;

        for (int i = 0; i < lastIndex + 1; i++) {
            if (containers[i] == null) continue;
            for (int j = 0; j < containers[i].size(); j++) {
                result += (Character.getNumericValue(containers[i].get(j).first()) * containers[i].get(j).second())
                * Math.pow(prime1, j) * Math.pow(prime2, i);
            }

        }
        cachedHash = (int) result;
        return cachedHash; */
    }

    private void setCost(int cost){
        this.cost = cost;
    }

    @Override
    public List<Ilayout> children() {
        List<Ilayout> children = new ArrayList<>();
        Containers buffer;
        Pair<Character, Integer> pairBuffer;
        for (int i = 0; i < lastIndex + 1; i++) {
            if (containers[i] == null) continue;
            if (containers[i].size() > 1) {
                buffer = (Containers) this.clone();
                pairBuffer = buffer.containers[i].pop();
                if (calcIndex(pairBuffer.first()) == -16)
                    System.out.println("a");
                buffer.containers[calcIndex(pairBuffer.first())] = new Stack<>();
                buffer.containers[calcIndex(pairBuffer.first())].add(pairBuffer);
                buffer.setCost(pairBuffer.second());
                children.add(buffer);
            }
            for (int j = 0; j < lastIndex + 1; j++) {
                if (j == i) continue;
                if (containers[j] == null) continue;
                buffer = (Containers) this.clone();
                pairBuffer = buffer.containers[i].pop();
                if(buffer.containers[i].isEmpty()) buffer.containers[i] = null;
                buffer.containers[j].add(pairBuffer);
                buffer.setCost(pairBuffer.second());
                children.add(buffer);
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

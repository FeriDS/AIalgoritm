import java.util.*;

class BestFirst {
    protected Queue<State> abertos;
    private Map<Ilayout, State> fechados;
    private State actual;
    private Ilayout objective;
    static class State {
        private Ilayout layout;
        private State father;
        private double g;
        public State(Ilayout l, State n) {
            layout = l;
            father = n;
            if (father!=null)
                g = father.g + l.getK();
            else g = 0.0;
        }
        public String toString() { return layout.toString(); }
        public double getG() {return g;}
        public int hashCode() {
            return toString().hashCode();
        }
        public boolean equals (Object o) {
            if (o==null) return false;
            if (this.getClass() != o.getClass()) return false;
            State n = (State) o;
            return this.layout.equals(n.layout);
        }
    }
    final private List<State> sucessores(State n) throws CloneNotSupportedException {
        List<State> sucs = new ArrayList<>();
        List<Ilayout> children = n.layout.children();
        for(Ilayout e: children) {
            if (n.father == null || !e.equals(n.father.layout)){
                State nn = new State(e, n);
                sucs.add(nn);
            }
        }
        return sucs;
    }
    final public Iterator<State> solve(Ilayout s, Ilayout goal) throws CloneNotSupportedException {
        objective = goal;
        abertos = new PriorityQueue<>(10,
                (s1, s2) -> (int) Math.signum(s1.getG()-s2.getG()));
        fechados = new HashMap<> ();
        abertos.add(new State(s, null));
        List<Ilayout> buffer;
        State atual;
        List<State> sucs = new ArrayList<>();
        int j = 0;
        while (true) {
            if (abertos.isEmpty()) {
                break;
            }
            atual = abertos.poll();
            if (atual.layout == goal) {
                sucs = sucessores(atual);
                break;
            }
            else {
                buffer = atual.layout.children();
                for (Ilayout ilayout : buffer) {
                    sucs.add(new State(ilayout, atual));
                }
                fechados.put(atual.layout, atual.father);
                j = 0;
                for (int i = 0; i < sucs.size()+j; i++) {
                    if (!sucs.get(i - j).equals(fechados.get(atual.layout))) {
                        abertos.add(sucs.remove(i - j++));
                    }
                    else
                        sucs.remove(i - j++);
                }
            }

        }
        return sucs.iterator();
    }
}
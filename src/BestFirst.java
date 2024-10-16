import java.util.*;
@SuppressWarnings("all")
class BestFirst {
    protected Queue<State> abertos;
    private Map<Ilayout, State> fechados;
    private State actual;
    private Ilayout objective;
    private HashMap<Ilayout, State> abertosHash;
    static class State {
        private Ilayout layout;
        private State father;
        private double g;
        public State(Ilayout l, State n) {
            layout = l;
            father = n;
            if (father!=null)
                g = father.g + l.getK();
            else g = 0;
        }
        public String toString() { return layout.toString(); }
        public double getG() {return g;}
        public int hashCode() {
            return layout.hashCode();
        }
        public boolean equals (Object o) {
            if (o==null) return false;
            if (this.getClass() != o.getClass()) return false;
            State n = (State) o;
            return this.layout.equals(n.layout);
        }
    }


    /**
     * Função que retorna uma lista com os ascendentes do "atual"
     * @param atual é um estado com o seu layout, pai e preço
     * @return lista de estados dos ascendentes
     */
    private List<State> ascendants(State atual) {
        List<State> result = new ArrayList<>();
        State atualb = atual;
        while (atualb.father != null) {
            result.addFirst(atualb);
            atualb = atualb.father;
        }
        result.addFirst(atualb);
        return result;
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

    /**
     * Função que resolve um dado puzzle
     * @param s é uma variavel da interface Ilayout, que é o estado inicial do que pretendemos resolver
     * @param goal é uma variavel da interface Ilayout, que é o estado desejado do problema
     * @return retorna um iterador com todos os estados percorridos para resolver o problema
     * @throws CloneNotSupportedException
     */
    final public Iterator<State> solve(Ilayout s, Ilayout goal) throws CloneNotSupportedException {
        objective = goal;
        abertos = new PriorityQueue<>(10,
                (s1, s2) -> (int) Math.signum(s1.getG()-s2.getG()));
        fechados = new HashMap<> ();
        State state = new State(s, null);
        abertos.add(state);
        abertosHash = new HashMap<> ();
        abertosHash.put(s, abertos.peek());
        State atual = null;
        List<State> sucs = new ArrayList<>();
        while (!abertos.isEmpty()) {
            abertosHash.remove(atual);
            atual = abertos.poll();
            if (atual.layout.isGoal(objective))
                break;
            fechados.put(atual.layout, atual.father);
            sucs = sucessores(atual);
            for (State suc: sucs) {
                if (!fechados.containsKey(suc.layout) && !abertosHash.containsKey(suc.layout)) {
                    abertosHash.put(suc.layout, suc);
                    abertos.add(suc);
                }
            }
        }
        return ascendants(atual).iterator();
    }
}

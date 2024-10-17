import java.util.*;
@SuppressWarnings("all")
class AStar {
    protected Queue<State> abertos;
    private Map<Ilayout, State> fechados;
    private State atual;
    private Ilayout objective;
    private HashMap<Ilayout, State> abertosHash;
    static class State {
        private Ilayout layout;
        private State father;
        private double g;
        private double h = -1;
        private int f = -1;
        public State(Ilayout l, State n) {
            layout = l;
            father = n;
            if (father!=null)
                g = father.g + l.getK();
            else g = 0;
        }
        public String toString() { return layout.toString(); }
        public double getG() {return g;}

        public double getH0(Ilayout obj){
            return 0;
        }

        public double getH1(Ilayout obj) {
            double result = 0;
            if (h != -1)
                return h;
            for (int i = 0; i < this.layout.getLayout().length; i++) {
                if (this.layout.getLayout()[i] == null) continue;
                if (obj.getLayout()[i] == null) {
                    result += this.layout.getLayout()[i].size();
                    continue;
                }
                if (this.layout.getLayout()[i].size() > obj.getLayout()[i].size()) {
                    result += this.layout.getLayout()[i].size() - obj.getLayout()[i].size();
                }
                for (int j = 0; j < this.layout.getLayout()[i].size(); j++) {
                    if (obj.getLayout()[i].size() < j + 1) break;
                    if (!this.layout.getLayout()[i].get(j).first().equals(obj.getLayout()[i].get(j).first())) {
                        result++;
                    }
                }
            }
            h = result;
            return result;
        }

        private double getH2(Ilayout obj){
            double result = 0;
            if(h != -1)
                return h;

            for(int i = 0; i < this.layout.getLayout().length; i++){
                if (this.layout.getLayout()[i] == null) continue;

                if (obj.getLayout()[i] == null) {
                    for(int j = 0; j < this.layout.getLayout()[i].size(); j++)
                        result += this.layout.getLayout()[i].get(j).second();

                    continue;
                }

                if (this.layout.getLayout()[i].size() > obj.getLayout()[i].size()) {
                    for(int j = obj.getLayout()[i].size(); j < this.layout.getLayout()[i].size(); j++){
                        result += this.layout.getLayout()[i].get(j).second();
                    }
                }

                for (int j = 0; j < this.layout.getLayout()[i].size(); j++) {
                    if (obj.getLayout()[i].size() < j + 1) break;
                    if (!this.layout.getLayout()[i].get(j).first().equals(obj.getLayout()[i].get(j).first())) {
                        result += this.layout.getLayout()[i].get(j).second();
                    }
                }
            }

            h= result;

            return result;
        }
        public int getF(Ilayout obj) {
            if (f != -1)
                return f;
            f = (int) (g + getH2(obj));
            return f;
        }
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

    public int getAbertosSize(){
        return abertos.size();
    }

    public int getFechadosSize(){
        return fechados.size();
    }

    public int solutionLength(){
        return ascendants(atual).size();
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
    final public double solve(Ilayout s, Ilayout goal) throws CloneNotSupportedException {
        objective = goal;
        abertos = new PriorityQueue<>(10,
                (s1, s2) -> (int) Math.signum(s1.getF(goal) - s2.getF(goal)));

        fechados = new HashMap<> ();
        State state = new State(s, null);
        abertos.add(state);
        abertosHash = new HashMap<> ();
        abertosHash.put(s, abertos.peek());
        atual = null;
        List<State> sucs = new ArrayList<>();

        while (!abertos.isEmpty()) {
            atual = abertos.poll();
            abertosHash.remove(atual.layout);

            if (atual.layout.isGoal(objective))
                break;

            fechados.put(atual.layout, atual.father);
            sucs = sucessores(atual);

            for (State suc: sucs) {
                if(fechados.containsKey(suc.layout)
                        && suc.getF(goal) < fechados.get(suc.layout).getF(goal)) {
                    fechados.get(suc.layout).father = suc.father;
                    fechados.get(suc.layout).g = suc.getG();
                    fechados.get(suc.layout).f = suc.getF(goal);
                }

                if(abertosHash.containsKey(suc.layout)
                        && suc.getF(goal) < abertosHash.get(suc.layout).getF(goal)){
                    abertos.remove(suc);
                    abertos.add(suc);
                }

                if (!fechados.containsKey(suc.layout) && !abertosHash.containsKey(suc.layout)) {
                    abertos.add(suc);
                    abertosHash.put(suc.layout, suc);
                }
            }
        }
        return atual.getG();
    }
}

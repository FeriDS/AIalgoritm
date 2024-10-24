import java.util.*;

/**
 * Esta classe foi criada para o ambito de calcular o caminho de menor custo usando um algoritmo
 * chamado A* / Astar / Aestrela.
 * Ela contem uma lista com os nós abertos, ou seja, os que ainda faltam explorar, e contêm
 * uma lista com os fechados, que são os que ja foram explorados.
 */
@SuppressWarnings("all")
class AStar {
    protected Queue<State> abertos;
    private Map<Ilayout, State> fechados;
    private State atual;
    private Ilayout objective;
    private HashMap<Ilayout, State> abertosHash;

    /**
     *Esta classe está implementada para guardar cada configuração e fazer os seus cálculos até chegar
     *ao estado final/desejado.
     */
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

        /**
         * Método que vai cálcular a primeira versão da heurística a ser aplicada no algoritmo A*.
         * Sempre que acha um container num local errado irá adicionar +1 ao valor da heuristica, sem se preocupar com
         * o custo de cada container.
         * @param obj layout do estado final/desejado
         * @return valor cálculado da heurística para a configurção/estado atual
         */
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

        /**
         * Método que vai álcular a segunda versão da heurística a ser usada pelo algoritmo A*.
         * Sempre que acha um container posicionado incorretamente ele vai adicionar o seu custo de movimento
         * ao valor da heuristica.
         * @param obj layout do estado final/desejado
         * @return valor final do cáculo da heurística
         */
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


        /**
         * Método que vai álcular a segunda versão mas com algumas melhorias da heurística a ser usada pelo algoritmo A*.
         * Caso encontre um container mal posicionado ele vai ver se o mesmo se vai encontrar na mesma stack, caso sim
         * ele ira adicionar ao valor da heurística 2x o seu custo de movimentação, caso não ele vai adicionar apenas 1x,
         * depois caso se situe na mesma stack ele vai fazer a mesma verificação para os que se encontram acima dele
         * pois será necessário tirar pelo menos 1x todos os que se situam acima.
         * @param obj layout do estado final/desejado
         * @return valor final do cálculo da heurística
         */
        private double getH2_5(Ilayout obj){
            double result = 0;
            if(h != -1)
                return h;

            Stack<Pair<Character, Integer>> currentStack, goalStack;
            HashSet<Character> objHash;
            boolean foundOne = false;

            for(int i = 0; i < this.layout.getLayout().length; i++){
                currentStack = this.layout.getLayout()[i];

                if(currentStack == null)
                    continue;

                goalStack = obj.getLayout()[i];

                if (goalStack == null) {
                    for(int j = 0; j < currentStack.size(); j++)
                        result += currentStack.get(j).second();

                    continue;
                }

                objHash = new HashSet<>();
                foundOne = false;

                for(int j = 0; j < goalStack.size(); j++)
                    objHash.add(goalStack.get(j).first());

                if (currentStack.size() > goalStack.size())
                    for(int j = goalStack.size(); j < currentStack.size(); j++) {
                        if(objHash.contains(currentStack.get(j).first()))
                            result += 2 * currentStack.get(j).second();
                        else
                            result += currentStack.get(j).second();
                    }

                for(int j = 1; j < Math.min(currentStack.size(), goalStack.size()); j++) {
                    if (foundOne) {
                        if (objHash.contains(currentStack.get(j).first()))
                            result += 2 * currentStack.get(j).second();
                        else
                            result += currentStack.get(j).second();
                    }
                    else if (!currentStack.get(j).first().equals(goalStack.get(j).first())) {
                        if (objHash.contains(currentStack.get(j).first()))
                            result += 2 * currentStack.get(j).second();
                        else
                            result += currentStack.get(j).second();
                    }
                }

            }

            h = result;
            return result;
        }

        /**
         * Método que vai álcular a terceira versão da heurística a ser usada pelo algoritmo A*.
         * Caso encontre um container mal posicionado ele vai ver se o mesmo se vai encontrar na mesma stack, caso sim
         * ele ira adicionar ao valor da heurística 2x o seu custo de movimentação, caso não ele vai adicionar apenas 1.
         * @param obj layout do estado final/desejado
         * @return valor final do cálculo da heurística
         */
        private double getH3(Ilayout obj){
            double result = 0;
            if(h != -1)
                return h;

            for(int i = 0; i < this.layout.getLayout().length; i++){

                Stack<Pair<Character, Integer>> currentStack = this.layout.getLayout()[i];
                Stack<Pair<Character, Integer>> goalStack = obj.getLayout()[i];

                if (currentStack == null)
                    continue;

                if (goalStack == null) {
                    for(int j = 0; j < currentStack.size(); j++)
                        result += currentStack.get(j).second();

                    continue;
                }

                for(int j = 1; j < Math.min(goalStack.size(), currentStack.size()); j++){
                    for(int k = 1; k < Math.min(goalStack.size(), currentStack.size()); k++) {

                        if(currentStack.get(j).first() == goalStack.get(k).first() && j != k) {
                            result += 2 * currentStack.get(j).second();
                        }
                    }
                }

                if (currentStack.size() > goalStack.size()) {
                    for(int j = obj.getLayout()[i].size(); j < currentStack.size(); j++){
                        result += currentStack.get(j).second();
                    }
                }

                for (int j = 0; j < currentStack.size(); j++) {
                    if (goalStack.size() < j + 1) break;
                    if (!currentStack.get(j).first().equals(goalStack.get(j).first())) {
                        result += currentStack.get(j).second();
                    }
                }
            }

            h = result;
            return result;
        }

        private double getH4(Ilayout obj){
            double result = 0;
            if(h != -1)
                return h;

            HashSet<Character> goalHash = new HashSet<>();

            Stack<Pair<Character, Integer>> currentStack, goalStack;

            for(int i = 0; i < this.layout.getLayout().length; i++){

                currentStack = this.layout.getLayout()[i];
                goalStack = obj.getLayout()[i];

                if (currentStack == null)
                    continue;

                if (goalStack == null) {
                    for (int j = 0; j < currentStack.size(); j++) {
                        result += currentStack.get(j).second() * 2;
                    }
                    continue;
                }

                goalHash = new HashSet<>();
                for (int j = 0; j < goalStack.size(); j++) {
                    goalHash.add(goalStack.get(j).first());
                }

                for (int j = 0; j < currentStack.size(); j++) {
                    if (j >= goalStack.size() || !currentStack.get(j).first().equals(goalStack.get(j).first())) {
                        if (goalHash.contains(currentStack.get(j).first())) {
                            result += 2 * currentStack.get(j).second();
                        } else {
                            result += currentStack.get(j).second();
                        }
                    }
                }

                if (currentStack.size() > goalStack.size()) {
                    for (int j = goalStack.size(); j < currentStack.size(); j++) {
                        result += 2 * currentStack.get(j).second();
                    }
                }
            }

            h = result;
            return result;
        }

        public int getF(Ilayout obj) {
            if (f != -1)
                return f;
            f = (int) (g + getH2_5(obj));
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
            if(abertosHash.containsKey(atual.layout)
                    && atual.getF(goal) > abertosHash.get(atual.layout).getF(goal))
                atual = abertosHash.get(atual.layout);

            abertosHash.remove(atual.layout);

            if (atual.layout.isGoal(objective))
                break;

            fechados.put(atual.layout, atual.father);
            sucs = sucessores(atual);

            for (State suc: sucs) {
                if(fechados.containsKey(suc.layout)
                        && suc.getF(goal) < fechados.get(suc.layout).getF(goal)) {
                   // fechados.get(suc.layout).father = suc.father;
                    //fechados.get(suc.layout).g = suc.getG();
                    //fechados.get(suc.layout).f = suc.getF(goal);
                    //fechados.remove(suc.layout);
                    abertos.add(suc);
                    abertosHash.put(suc.layout, suc);
                }

                if(abertosHash.containsKey(suc.layout)
                        && suc.getF(goal) < abertosHash.get(suc.layout).getF(goal)){
                    //abertos.remove(suc);
                    //abertos.add(suc);
                   // abertosHash.get(suc.layout).g = suc.getG();
                   // abertosHash.get(suc.layout).f = suc.getF(goal);
                   // abertosHash.get(suc.layout).father = suc.father;
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

//Class Pair que não fui eu a fazer e sim alguem da internet.
//Poderia usar Map.Entry para submeter no mooshak, mas fica mais facil para eu ler o codigo
//com a classe Pair. Não é totalmente a classe Pair. Já que esta tem algumas alterações, pois
//no problema atual o second(B) não irá ser alterado ou seja não é importante para a comparação de
//dois objetos da classe Pair.
//TLDR: para facilitar a comparação do goal com o container pq o goal tem o B como null.

public class Pair<A, B> {
    private final A first;
    private final B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A first() {
        return this.first;
    }

    public B second() {
        return this.second;
    }

    public int hashCode() {
        int result = 1;
        result = 31 * result + (this.first == null ? 0 : this.first.hashCode());
        result = 17 * result + (this.second == null ? 0 : this.second.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            if (this.first == null) {
                if (((Pair<?, ?>) obj).first != null) {
                    return false;
                }
            } else if (!this.first.equals(((Pair<?, ?>) obj).first)) {
                return false;
            }

             return true;
        }
    }

    @Override
    public Pair<A, B> clone() {
        return this;
        //return new Pair<A, B>(this.first, this.second);
    }

}

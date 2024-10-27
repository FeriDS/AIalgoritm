/**
 * Classe que vai fazer os cálculos e imprimir os status.
 */
public class ContainersStats {
    private int expandedNodes;
    private int generatedNodes;
    private int solutionLength;
    private double penetrance;

    private double startTime;
    private double endTime;

    ContainersStats(){

    }

    public void setExpandedNodes(int expandedNodes) {
        this.expandedNodes = expandedNodes;
    }

    public void setGeneratedNodes(int generatedNodes) {
        this.generatedNodes = generatedNodes;
    }

    public void setSolutionLength(int solutionLength) {
        this.solutionLength = solutionLength;
    }

    /**
     * Método que cácula a penetrance.
     * @return
     */
    private double calcPenetrance() {
        return (double) this.solutionLength / (this.generatedNodes);
    }

    public void getStartTime(){
        startTime = System.nanoTime() * Math.pow(10, -6);
    }

    public void getEndTime(){
        endTime = System.nanoTime() * Math.pow(10, -6);
    }

    /**
     * Método que vai imprimir todos o status de quanto demoru, quantos nós gerou, quantos nós expandiu, penetrance,
     * e de quantos passos foram necessários para chegar no goal.
     */
    public void printStats(){
        System.out.println("---------------------Stats---------------------");
        System.out.println("Expanded Nodes: " + this.expandedNodes);
        System.out.println("Generated Nodes: " + this.generatedNodes);
        System.out.println("Solution Length: " + this.solutionLength);
        System.out.println("Penetrance: " + calcPenetrance());
        System.out.println("Time elapsed: " + (endTime - startTime) + "ms");
        System.out.println("-----------------------------------------------");
    }
}

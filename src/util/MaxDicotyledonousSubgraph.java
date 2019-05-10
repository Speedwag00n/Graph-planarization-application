package util;

import java.util.ArrayList;

public class MaxDicotyledonousSubgraph {

    private ArrayList<ArrayList<Integer>> psis;
    private int[][] maxMatrix;
    private int maxPsi = 0;

    public MaxDicotyledonousSubgraph(ArrayList<ArrayList<Integer>> psis){
        this.psis = psis;
        maxMatrix = findMDS();
    }

    public int[][] getMaxMatrix(){
        return maxMatrix;
    }

    private int[][] findMDS(){
        int[][] maxMatrix = new int[psis.size()][psis.size()];
        int max = 0;
        for (int i = 0; i < psis.size(); i++) {
            for (int j = i + 1; j < psis.size(); j++) {
                ArrayList<Integer> crossing = (ArrayList<Integer>) psis.get(i).clone();
                crossing.retainAll(psis.get(j));
                maxMatrix[i][j] = (psis.get(i).size() + psis.get(j).size() - crossing.size());
                if (maxMatrix[i][j] > max){
                    max = maxMatrix[i][j];
                }
            }
        }
        for (int i = 0; i < psis.size(); i++) {
            for (int j = 0; j < psis.size(); j++) {
                if (i == j || maxMatrix[i][j] != 0)
                    System.out.printf("%5s", maxMatrix[i][j] + ", ");
                else
                    System.out.printf("%5s", " ");
                if (maxMatrix[i][j] > maxPsi)
                    maxPsi = maxMatrix[i][j];
            }
            System.out.println();
        }
        System.out.println("Max: " + maxPsi);

        return maxMatrix;
    }

    public void reducePsisRibs(Psis p, MatrixOfCrossing moc){
        for (int i = 0; i < psis.size(); i++) {
            for (int j = 0; j < psis.size(); j++) {
                if (maxMatrix[i][j] == maxPsi) {
                    p.reducePsis(p.findRibsOfPair(i, j, moc));
                    return;
                }
            }
        }
    }

}

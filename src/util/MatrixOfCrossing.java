package util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class MatrixOfCrossing {

    private ArrayList<Integer> hamiltonianCycle;
    private int[][] array;
    LinkedHashMap<Rib, LinkedHashMap<Rib, Integer>> matrixOfCrossing;

    public MatrixOfCrossing(LinkedList<Integer> hamiltonianCycle, int[][] array){
        this.hamiltonianCycle = new ArrayList<>(hamiltonianCycle);
        this.array = array;
        matrixOfCrossing = findMatrixOfCrossing();
    }

    public LinkedHashMap<Rib, LinkedHashMap<Rib, Integer>> getMatrixOfCrossing(){
        return matrixOfCrossing;
    }

    private LinkedHashMap<Rib, LinkedHashMap<Rib, Integer>> findMatrixOfCrossing(){
        LinkedHashMap<Rib, LinkedHashMap<Rib, Integer>> matrixOfCrossing = new LinkedHashMap<>();
        int[][] renumberedMatrix = new int[array.length][array.length];
        for (int i = 0; i < hamiltonianCycle.size(); i++) {
            for (int j = 0; j < hamiltonianCycle.size(); j++) {
                if ((i + 1) % array.length == j || (j + 1) % array.length == i){
                    renumberedMatrix[i][j] = 0;
                    continue;
                }
                renumberedMatrix[i][j] = array[hamiltonianCycle.get(i)-1][hamiltonianCycle.get(j)-1];
            }
        }
        System.out.println("Матрица смежности с перенумерованными вершинами:");
        System.out.printf("%5s", "");
        for (int i = 0; i < hamiltonianCycle.size(); i ++){
            System.out.printf("%-5s", "x" + (i + 1));
        }
        System.out.println();
        for (int i = 0; i < hamiltonianCycle.size(); i++) {
            System.out.printf("%-5s", "x" + (i + 1) + ":");
            for (int j = 0; j < hamiltonianCycle.size(); j++) {
                System.out.printf("%-5d", renumberedMatrix[i][j]);
            }
            System.out.println();
        }
        LinkedHashMap<Rib, Integer> currentRibs = new LinkedHashMap<>();
        for (int i = 1; i < hamiltonianCycle.size() - 2; i++){
            for (int j = (i + 2); j < hamiltonianCycle.size(); j++){
                if (renumberedMatrix[i][j] != 0) {
                    Rib ribP = Rib.getRib(i + 1, j + 1);
                    LinkedHashMap<Rib, Integer> newLine = (LinkedHashMap<Rib, Integer>) currentRibs.clone();
                    for (int k = 0; k < i; k++) {
                        for (int u = (i + 1); u < j; u++) {
                            if (renumberedMatrix[k][u] != 0) {
                                Rib ribR = Rib.getRib(k + 1, u + 1);
                                if (!currentRibs.containsKey(ribR)) {
                                    currentRibs.put(ribR, 0);
                                    supplementMatrix(matrixOfCrossing, ribR);
                                }
                                newLine.put(ribR, 1);

                                if (!matrixOfCrossing.containsKey(ribR)) {
                                    LinkedHashMap<Rib, Integer> additionLine = (LinkedHashMap<Rib, Integer>) currentRibs.clone();
                                    additionLine.put(ribR, 1);
                                    matrixOfCrossing.put(ribR, additionLine);
                                }
                            }
                        }
                    }
                    if (!currentRibs.containsKey(ribP)) {
                        currentRibs.put(ribP, 0);
                        supplementMatrix(matrixOfCrossing, ribP);
                    }
                    newLine.put(ribP, 1);
                    matrixOfCrossing.put(ribP, newLine);
                    supplementCrossings(matrixOfCrossing, ribP);
                }
            }
        }

        int space = 7 + 2;
        System.out.printf("%" + (space - 2) + "s", "");
        for (Rib rib : currentRibs.keySet()){
            System.out.printf("%-" + space + "s", rib.toString());
        }
        System.out.println();
        for (Map.Entry<Rib, LinkedHashMap<Rib, Integer>> line : matrixOfCrossing.entrySet()) {
            System.out.printf("%-" + space + "s", line.getKey().toString() + ": ");
            for (Map.Entry<Rib, Integer> entry : line.getValue().entrySet()) {
                System.out.printf("%-" + space + "s", entry.getValue());
            }
            System.out.println();
        }


        return matrixOfCrossing;
    }

    public int[][] toArray(){
        int[][] matrix = new int[matrixOfCrossing.size()][matrixOfCrossing.size()];
        int i = 0, j = 0;
        for (LinkedHashMap<Rib, Integer> line : matrixOfCrossing.values()){
            j = 0;
            for (Integer value : line.values()){
                matrix[i][j] = value;
                j++;
            }
            i++;
        }
        return matrix;
    }

    public ArrayList<Rib> listOfRibs() {
        ArrayList<Rib> list = new ArrayList<>();
        for (Rib rib : matrixOfCrossing.keySet()){
            list.add(rib);
        }
        return list;
    }

    private void supplementMatrix (LinkedHashMap<Rib, LinkedHashMap<Rib, Integer>> matrix, Rib rib){
        for (LinkedHashMap<Rib, Integer> map : matrix.values()){
            if (!map.containsKey(rib))
                map.put(rib, 0);
        }
    }

    private void supplementCrossings (LinkedHashMap<Rib, LinkedHashMap<Rib, Integer>> matrix, Rib rib){
        for (Map.Entry<Rib, Integer> entry : matrix.get(rib).entrySet()){
            if (entry.getValue() != 0){
                matrix.get(entry.getKey()).put(rib, 1);
            }
        }
    }

    public void printRibs(ArrayList<Integer> numbers){
        ArrayList<Rib> ribs = listOfRibs();
        for (Integer number : numbers)
            System.out.print(ribs.get(number-1).toString());
        System.out.println();
    }

}

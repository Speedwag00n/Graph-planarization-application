package util;

import java.util.ArrayList;
import java.util.Arrays;

public class Psis {

    private int[][] matrixOfCrossing;
    private ArrayList<Rib> ribs;
    private ArrayList<ArrayList<Integer>> psis;

    public Psis(int[][] matrixOfCrossing, ArrayList<Rib> ribs){
        this.matrixOfCrossing = matrixOfCrossing;
        this.ribs = ribs;
        psis = findPsis();
    }

    public ArrayList<ArrayList<Integer>> getPsis(){
        return psis;
    }

    private ArrayList<ArrayList<Integer>> findPsis(){
        ArrayList<ArrayList<Integer>> psis = new ArrayList<>();
        for (int i = 0; i < matrixOfCrossing.length; i++){

            if (checkZeros(matrixOfCrossing[i])) {
                ArrayList<Integer> J = makeJ(matrixOfCrossing[i]);
                System.out.println("J(j) = " + J.toString());
                for (int j = 0; j < J.size(); j++) {
                    if (i + 1 >= J.get(j))
                        continue;
                    int[] M = new int[matrixOfCrossing.length];
                    disjunction(M, i, J.get(j) - 1, matrixOfCrossing);
                    System.out.println("M(" + (i + 1) + ", " + J.get(j) + ") = " + Arrays.toString(M).replace(']', '}').replace('[', '{'));
                    if (!checkZeros(M)){
                        ArrayList<Integer> psi = new ArrayList<>();
                        psi.add(i + 1);
                        psi.add(J.get(j));
                        psis.add(psi);
                        printPsi(psi, ribs);
                    }
                    else{
                        ArrayList<Integer> Jp = makeJ(M);
                        System.out.println("J'(j') = " + Jp.toString());
                        for (int y : Jp) {//+
                            if (y < J.get(j))
                                continue;
                            ArrayList<Integer> psi = new ArrayList<>();
                            psi.add(i + 1);
                            psi.add(J.get(j));
                            int[] M1 = M.clone();
                            for (int u = (y - 1); u < M.length; u++) {
                                int cur = -1;
                                for (int p = u; p < M.length; p++) {
                                    if (M1[p] == 0) {
                                        cur = p;
                                        break;
                                    }
                                }
                                if (cur == -1)
                                    break;
                                psi.add(cur + 1);
                                disjunction(M1, cur, matrixOfCrossing);
                                System.out.println("M" + psi.toString().replace('[', '(').replace(']', ')') + " = " + Arrays.toString(M1).replace(']', '}').replace('[', '{'));
                                if (!checkZeros(M1)) {
                                    psis.add(psi);
                                    System.out.println();
                                    printPsi(psi, ribs);
                                    System.out.println();
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            else {
                ArrayList<Integer> psi = new ArrayList<>();
                psi.add(i + 1);
                psis.add(psi);
            }
        }

        return psis;
    }

    public void printPsis(){
        System.out.println("Количество множеств: " + psis.size());
        for (ArrayList<Integer> psi : psis){
            printPsi(psi, ribs);
        }
    }

    private ArrayList<Integer> makeJ (int[] M){
        ArrayList<Integer> J = new ArrayList<>();
        for (int i = 0; i < M.length; i++) {
            if (M[i] == 0)
                J.add(i + 1);
        }
        return J;
    }

    private boolean checkZeros (int[] M){
        for (int m : M){
            if (m == 0)
                return true;
        }
        return false;
    }

    private void disjunction (int[] set, int r1, int r2, int[][] matrixOfCrossing){
        for (int i = 0; i < matrixOfCrossing.length; i++){
            if (matrixOfCrossing[r1][i] == 1)
                set[i] = 1;
        }
        for (int i = 0; i < matrixOfCrossing.length; i++){
            if (matrixOfCrossing[r2][i] == 1)
                set[i] = 1;
        }
    }

    private void disjunction (int[] set, int r, int[][] matrixOfCrossing){
        for (int i = 0; i < matrixOfCrossing.length; i++){
            if (matrixOfCrossing[r][i] == 1)
                set[i] = 1;
        }
    }

    private void printPsi(ArrayList<Integer> psi, ArrayList<Rib> ribs){
        System.out.println(psi.toString());
        System.out.print("ψ = {");
        for (int k : psi) {
            System.out.print(ribs.get(k - 1));
            if (psi.get(psi.size() - 1) != k)
                System.out.print(", ");
        }
        System.out.print("}");
        System.out.println();
    }

    public ArrayList<Integer> findRibsOfPair(int first, int second, MatrixOfCrossing moc){
        ArrayList<Integer> psiFirst = psis.get(first);
        ArrayList<Integer> psiSecond = psis.get(second);
        System.out.println("Множество " + (first+1) + ": " + psiFirst);
        System.out.println("Множество " + (second+1) + ": " + psiSecond);
        System.out.print("Ребра " + (first+1) + " множества: ");
        moc.printRibs(psiFirst);
        System.out.print("Ребра " + (second+1) + " множества: ");
        moc.printRibs(psiSecond);
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= ribs.size(); i++){
            if (psiFirst.contains(i) || psiSecond.contains(i))
                numbers.add(i);
        }
        System.out.print("Номера удаляемых ребер: ");
        System.out.println(numbers);
        return numbers;
    }

    public void reducePsis(ArrayList<Integer> numbers){
        for (ArrayList<Integer> psi : psis){
            for (Integer i : numbers){
                if (psi.contains(i))
                    psi.remove(i);
            }
        }
        for (int i = 0; i < psis.size(); i++){
            if (psis.get(i) == null)
                continue;
            if (psis.get(i).size() == 0){
                psis.set(i, null);
                continue;
            }
            for (int j = 0; j < psis.size(); j++){
                if (psis.get(j) == null)
                    continue;
                if (psis.get(i) == psis.get(j))
                    continue;
                if (psis.get(i).containsAll(psis.get(j))){
                    psis.set(j, null);
                }
            }
        }
        ArrayList<ArrayList<Integer>> newPsis = new ArrayList<>();
        for (ArrayList<Integer> psi : psis){
            if (psi != null)
                newPsis.add(psi);
        }
        psis = newPsis;
    }

    public boolean isEmpty(){
        return (psis.size() == 0);
    }

}

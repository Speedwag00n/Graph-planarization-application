package util;

import java.util.LinkedList;

public class HamiltonianCycle {

    private int size;
    private int[][] array;
    private LinkedList<Integer> cycle;

    public HamiltonianCycle(int[][] array){
        this.size = array.length;
        this.array = array;
        cycle = findHamiltonianCycle();
    }

    public LinkedList<Integer> getHamiltonianCycle(){
        return cycle;
    }

    private LinkedList<Integer> findHamiltonianCycle(){
        LinkedList<Integer> set = new LinkedList<>();
        set.add(1);
        boolean[][] checkedNodes = new boolean[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (array[i][j] == 0)
                    checkedNodes[i][j] = true;
        while (true) {
            int nodeNumber = set.getLast();
            if (set.size() == size) {
                if (checkCycle(set, checkedNodes[set.getLast() - 1]))
                    break;
                else {
                    nodeNumber = set.getLast();
                    regenerate(nodeNumber, checkedNodes[nodeNumber - 1]);
                    set.removeLast();
                }
                System.out.println("S = " + set.toString().replace('[', '{').replace(']', '}'));
                continue;
            }
            int nextNode = searchNext(set, checkedNodes[nodeNumber - 1]);
            if (nextNode != -1) {
                set.add(nextNode);
            }
            else {
                if (set.size() == 1)
                    return null;
                nodeNumber = set.getLast();
                regenerate(nodeNumber, checkedNodes[nodeNumber - 1]);
                set.removeLast();
            }
            System.out.println("S = " + set.toString().replace('[', '{').replace(']', '}'));
        }

        if (set == null){
            System.out.println("Гамильтонов цикл отсутствует");
        }
        else {
            System.out.println("Гамильтонов цикл: S = " + set.toString().replace('[', '{').replace(']', '}'));
        }

        return set;
    }

    private int searchNext(LinkedList<Integer> set, boolean[] ribs) {
        for (int i = 1; i <= ribs.length; i++) {
            if (ribs[i-1] == false && !set.contains(i)) {
                ribs[i-1] = true;
                return i;
            }
        }
        return -1;
    }

    private boolean checkCycle(LinkedList<Integer> set, boolean[] ribs){
        return (ribs[set.getFirst() - 1] == false);
    }

    private void regenerate (int nodeNumber, boolean[] ribs){
        for (int i = 0; i < ribs.length; i++){
            if (array[nodeNumber - 1][i] == 0)
                ribs[i] = true;
            else
                ribs[i] = false;
        }
    }

}

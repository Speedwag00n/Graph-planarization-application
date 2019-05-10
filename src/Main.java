import util.*;
import util.exceptions.EmptyFileException;
import util.exceptions.IllegalMatrixFormatException;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        int[][] array = null;
        if (args.length > 0)
            try {
                array = readArrayFromFile(args[0]);
            }
            catch (FileNotFoundException e){
                System.out.println("Файл не найден, проверьте провильность написания пути до файла");
                System.exit(0);
            }
            catch (EmptyFileException e){
                System.out.println("Файл пуст");
                System.exit(0);
            }
            catch (IllegalMatrixFormatException e){
                System.out.println("Считанная матрица не является квадратной матрицей");
                System.exit(0);
            }
        else {
            System.out.println("Введите имя файла, откуда будет загружена матрица");
            System.exit(0);
        }
        HamiltonianCycle cycle = new HamiltonianCycle(array);
        LinkedList<Integer> hamiltonianCycle = cycle.getHamiltonianCycle();
        if (hamiltonianCycle == null)
            System.exit(0);

        MatrixOfCrossing moc = new MatrixOfCrossing(hamiltonianCycle, array);
        int[][] matrixOfCrossing = moc.toArray();
        ArrayList<Rib> ribs = moc.listOfRibs();

        Psis psisFinder = new Psis(matrixOfCrossing, ribs);
        System.out.println();
        psisFinder.printPsis();
        while (!psisFinder.isEmpty()){
            ArrayList<ArrayList<Integer>> psis = psisFinder.getPsis();
            MaxDicotyledonousSubgraph maxDS = new MaxDicotyledonousSubgraph(psis);

            maxDS.reducePsisRibs(psisFinder, moc);
            System.out.println();
            psisFinder.printPsis();
        }
    }

    private static int[][] readArrayFromFile (String filePath) throws FileNotFoundException, EmptyFileException, IllegalMatrixFormatException{
        int[][] result;
        File file = new File(filePath);
        if (!file.exists())
            throw new FileNotFoundException();
        FileReader fileReader = new FileReader(file);
        Scanner scanner = new Scanner(fileReader);
        ArrayList<ArrayList<Integer>> doubleArray = new ArrayList<>();
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            Scanner lineScanner = new Scanner(line);
            ArrayList<Integer> currentArray = new ArrayList<>();
            while (lineScanner.hasNextInt()){
                int currentValue = lineScanner.nextInt();
                currentArray.add(currentValue);
            }
            doubleArray.add(currentArray);
        }
        if (doubleArray.size() != 0)
            result = new int[doubleArray.get(0).size()][doubleArray.size()];
        else
            throw new EmptyFileException();
        if (doubleArray.get(0).size() != doubleArray.size())
            throw new IllegalMatrixFormatException();
        for (int i = 0; i < doubleArray.size(); i++){
            ArrayList<Integer> array = doubleArray.get(i);
            if (doubleArray.get(0).size() != array.size())
                throw new IllegalMatrixFormatException();
            else
                for (int j = 0; j < array.size(); j++){
                    result[i][j] = array.get(j);
                }
        }
        return result;
    }

}

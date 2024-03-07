package org.example.mypackage;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

@Data
@NoArgsConstructor

public class Data_Loader {
    Scanner scanner;

    Grid grid;

    public Grid getGrid() {
        return grid;
    }


    public Global_Data getGlobalData() {
        return globalData;
    }


    Global_Data globalData;


    public Data_Loader(String path) {
        File file = new File(path);
        try {
            this.scanner = new Scanner(file);
            globalData = new Global_Data();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void loadData() {

        //loading global data
        scanner.next();
        globalData.setSimulationTime(Integer.parseInt(scanner.next()));
        scanner.next();
        globalData.setSimulationSleepTime(Integer.parseInt(scanner.next()));
        scanner.next();
        globalData.setConductivity(Integer.parseInt(scanner.next()));
        scanner.next();
        globalData.setAlfa(Integer.parseInt(scanner.next()));
        scanner.next();
        globalData.setTot(Integer.parseInt(scanner.next()));
        scanner.next();
        globalData.setInitialTemp(Integer.parseInt(scanner.next()));
        scanner.next();
        globalData.setDensity(Integer.parseInt(scanner.next()));
        scanner.next();
        globalData.setSpecificHeat(Integer.parseInt(scanner.next()));
        scanner.next();
        scanner.next();
        globalData.setNode_number(Integer.parseInt(scanner.next()));
        scanner.next();
        scanner.next();
        globalData.setElements_number(Integer.parseInt(scanner.next()));

        //loading grid

        scanner.next();
        int i = 0;

        grid = new Grid(globalData.getElements_number(), globalData.getNode_number());
        Node[] nodes = grid.getNodes();

        while (i < (grid.getNodes().length) * 3) {
            if (i % 3 != 0) {
                Node node = new Node();
                node.setX(Double.parseDouble(scanner.next().replace(",", "")));
                node.setY(Double.parseDouble(scanner.next().replace(",", "")));
                nodes[i / 3] = node;
                i += 2;
            } else {
                scanner.next();
                i++;
            }

        }
        scanner.next();
        scanner.next();
        Element[] elements = grid.getElements();
        int j = 0;
        while (j < (grid.getElements().length) * 5) {
            if (j % 5 != 0) {
                Element element = new Element();
                element.setID(new int[4]);
                element.ID[0] = Integer.parseInt(scanner.next().replace(",", ""));
                element.ID[1] = Integer.parseInt(scanner.next().replace(",", ""));
                element.ID[2] = Integer.parseInt(scanner.next().replace(",", ""));
                element.ID[3] = Integer.parseInt(scanner.next().replace(",", ""));

                elements[j / 5] = element;
                j += 4;
            } else {
                scanner.next();
                j++;
            }
        }
        j = 0;
        scanner.next();
        int[] tmp = new int[globalData.Elements_number];

        do {
            grid.getNodes()[Integer.parseInt(scanner.next().replace(",", "")) - 1].setBc(1);
            //  System.out.println(grid.getNodes()[j].getBc());
            j++;
        } while (scanner.hasNext());

    }

}


//package org.example.mypackage;
//
//
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.Scanner;
//
//@Data
//@NoArgsConstructor
//
//public class Data_Loader {
//    Scanner scanner;
//
//    Grid grid;
//
//    public Grid getGrid() {
//        return grid;
//    }
//
//
//    public Global_Data getGlobalData() {
//        return globalData;
//    }
//
//
//    Global_Data globalData;
//
//
//    public Data_Loader(String path) {
//        File file = new File(path);
//        try {
//            this.scanner = new Scanner(file);
//            globalData = new Global_Data();
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//
//    public void loadData() {
//
//        //loading global data
//        scanner.next();
//        globalData.setSimulationTime(Integer.parseInt(scanner.next()));
//        scanner.next();
//        globalData.setSimulationSleepTime(Integer.parseInt(scanner.next()));
//        scanner.next();
//        globalData.setConductivity(Integer.parseInt(scanner.next()));
//        scanner.next();
//        globalData.setAlfa(Integer.parseInt(scanner.next()));
//        scanner.next();
//        globalData.setTot(Integer.parseInt(scanner.next()));
//        scanner.next();
//        globalData.setInitialTemp(Integer.parseInt(scanner.next()));
//        scanner.next();
//        globalData.setDensity(Integer.parseInt(scanner.next()));
//        scanner.next();
//        globalData.setSpecificHeat(Integer.parseInt(scanner.next()));
//        scanner.next();
//        scanner.next();
//        globalData.setNode_number(Integer.parseInt(scanner.next()));
//        scanner.next();
//        scanner.next();
//        globalData.setElements_number(Integer.parseInt(scanner.next()));
//
//        //loading grid
//
//        scanner.next();
//        int i = 0;
//
//        grid = new Grid(globalData.getElements_number(), globalData.getNode_number());
//        Node[] nodes = grid.getNodes();
//
//        while (i < (grid.getNodes().length) * 3) {
//            if (i % 3 != 0) {
//                Node node = new Node();
//                node.setX(Double.parseDouble(scanner.next().replace(",", "")));
//                node.setY(Double.parseDouble(scanner.next().replace(",", "")));
//                nodes[i / 3] = node;
//                i += 2;
//            } else {
//                scanner.next();
//                i++;
//            }
//
//        }
//        scanner.next();
//        scanner.next();
//        Element[] elements = grid.getElements();
//        int j = 0;
//        while (j < (grid.getElements().length) * 5) {
//            if (j % 5 != 0) {
//                Element element = new Element();
//                element.setID(new int[4]);
//                element.ID[0] = Integer.parseInt(scanner.next().replace(",", ""));
//                element.ID[1] = Integer.parseInt(scanner.next().replace(",", ""));
//                element.ID[2] = Integer.parseInt(scanner.next().replace(",", ""));
//                element.ID[3] = Integer.parseInt(scanner.next().replace(",", ""));
//
//                elements[j / 5] = element;
//                j += 4;
//            } else {
//                scanner.next();
//                j++;
//            }
//        }
//
//
//    }
//
//}

package org.example;


import org.example.mypackage.*;

import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException {

        int schemat_calkowania = 2;
//Obliczanie macierzy H brzydko i schowane
        String Plik = "./src/main/resources/Test2_4_4_MixGrid.txt";
        Data_Loader dataLoader = new Data_Loader(Plik);
        dataLoader.loadData();
        Grid grid = dataLoader.getGrid();
        Global_Data globalData = dataLoader.getGlobalData();
        ElementUniversalny elementUniversalny = new ElementUniversalny(schemat_calkowania);
        elementUniversalny.oblicz();

        double[] tabX = new double[4];
        double[] tabY = new double[4];
        double[] tabBC = new double[4];


//        HBC hbc = new HBC(2, tabX, tabY, globalData);
//        hbc.Warunek();

        SoE soe = new SoE(globalData.getNode_number(), globalData);


        int j = 0;
        Macierz_H[] calka = new Macierz_H[globalData.getElements_number()];
        HBC[] hbcs = new HBC[globalData.getElements_number()];
        MacierzC macierzC = null;
        for (Element e : grid.elements) {
//
//
            int[] tmp = new int[4];
            for (int i = 0; i < 4; i++) {
                tabX[i] = grid.nodes[e.ID[i] - 1].X;
                tabY[i] = grid.nodes[e.ID[i] - 1].Y;
                tabBC[i] = grid.nodes[e.ID[i] - 1].getBc();
                //System.out.print(e.ID[i] + " ");
                if (grid.nodes[e.ID[i] - 1].getBc() == 1) {
                    tmp[i]++;
                }

            }


            hbcs[j] = new HBC(schemat_calkowania, tabX, tabY, globalData, tmp, tabBC);
            hbcs[j].Warunek();
            calka[j] = new Macierz_H(tabX, tabY, schemat_calkowania, globalData);
            calka[j].Jakobian();
            calka[j].Nazwa();
            calka[j].ObliczH();
            macierzC = new MacierzC(globalData.getSpecificHeat(), globalData.getDensity(), elementUniversalny, tabX, tabY, schemat_calkowania);
            macierzC.Oblicz();
            e.h = calka[j].getH_final();
            e.hbc = hbcs[j].getWynik();
            e.p = hbcs[j].wynikP;
            e.c = macierzC.C;
            j++;
            soe.agregacja(e, globalData);
            // System.out.println(Arrays.deepToString(e.hbc));
            System.out.println("lokalna wektor p dla elementu " + j);
            for (int i = 0; i < 4; i++) {
                System.out.print(e.p[i] + " ");
            }
            System.out.println("\n\n");
        }

//
        System.out.println("Globalna macierz H");
        for (int i = 0; i < globalData.getNode_number(); i++) {
            for (int k = 0; k < globalData.getNode_number(); k++) {
                System.out.print(soe.Hagro[i][k] + " ");
            }
            System.out.println(" ");
        }

        System.out.println("\n\n\n");
        System.out.println("Globalna macierz C");
        for (int i = 0; i < globalData.getNode_number(); i++) {
            for (int k = 0; k < globalData.getNode_number(); k++) {
                System.out.print(soe.Cagro[i][k] + " ");
            }
            System.out.println(" ");
        }
        System.out.println("\n\n\n");
        System.out.println("Globalny wektor P");
        for (int i = 0; i < globalData.getNode_number(); i++) {
            System.out.print(soe.Pagro[i] + " ");
        }

        soe.Stacjonar();
        soe.niestacjonar();
        Data_Saver dataSaver = new Data_Saver(soe, globalData, grid);
        dataSaver.zapisz();
    }


}







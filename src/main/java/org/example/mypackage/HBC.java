package org.example.mypackage;

import java.util.Arrays;

public class HBC {
    int liczba_wezlow;
    double[] tabX;
    double[] tabY;
    double[] tabBc;
    int[] brzeg;
    double[][] wynik = new double[4][4];
    public double[] wynikP = new double[4];


    // double[][] sum = new double[4][4];

    public double[][] getWynik() {
        return wynik;
    }

    Global_Data globalData;
    Gaus gaus;

    ElementUniversalny elementUniversalny;

    public HBC(int liczba_wezlow, double[] tabX, double[] tabY, Global_Data globalData, int[] brzeg, double[] tabBc) {
        this.tabX = tabX;
        this.tabY = tabY;
        this.tabBc = tabBc;
        this.liczba_wezlow = liczba_wezlow;
        this.globalData = globalData;
        this.elementUniversalny = new ElementUniversalny(liczba_wezlow);
        this.brzeg = brzeg;
        this.gaus = new Gaus(liczba_wezlow);

    }

    public void Warunek() {

        double[] det = new double[4];
        double alfa = globalData.getAlfa();
        //  double alfa = 25;
        Surface[] trans = new Surface[4];


        for (int i = 0; i < 4; i++) {
            trans[i] = new Surface(liczba_wezlow);
            trans[i].N_tab = elementUniversalny.surfaces[i].N_tab;
            //   System.out.println(Arrays.deepToString(elementUniversalny.surfaces[i].N_tab));
        }


        for (int i = 0; i < 4; i++) {
            if (i != 3) {
                det[i] = Math.sqrt(Math.pow(tabX[i] - tabX[i + 1], 2) + Math.pow(tabY[i] - tabY[i + 1], 2)) / 2;
            } else {
                det[i] = Math.sqrt(Math.pow(tabX[3] - tabX[0], 2) + Math.pow(tabY[3] - tabY[0], 2)) / 2;

            }
            // System.out.println(det[i]);
        }

        double[][][] tab = new double[liczba_wezlow][4][4];
        double[][][] tabP = new double[liczba_wezlow][4][4];

        double tmp;


        for (int l = 0; l < 4; l++) {//l
            if (tabBc[l % 4] != 0 && tabBc[(l + 1) % 4] == tabBc[l % 4]) {
                for (int i = 0; i < liczba_wezlow; i++) {//i

                    for (int j = 0; j < 4; j++) {//j
                        for (int k = 0; k < 4; k++) {//k
                            tab[i][j][k] = elementUniversalny.surfaces[l].N_tab[i][j] * elementUniversalny.surfaces[l].N_tab[i][k] * globalData.getAlfa() * det[l] * gaus.getWagi()[i];
                            wynik[j][k] += tab[i][j][k];

                            //      System.out.println(globalData.getAlfa());
                        }


//obliczanie macierzy P
                        wynikP[j] += elementUniversalny.surfaces[l].N_tab[i][j] * globalData.getTot() * gaus.getWagi()[i] * globalData.getAlfa() * det[l];


                    }

                }
            }

        }
        //  System.out.println(Arrays.deepToString(wynik));
        // System.out.println(Arrays.toString(wynikP));
        ///

    }


}

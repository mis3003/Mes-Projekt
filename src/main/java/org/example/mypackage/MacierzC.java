package org.example.mypackage;

import java.util.Arrays;

public class MacierzC {
    double c;
    double ro;
    ElementUniversalny elementUniversalny;
    double[] tabX;
    double[] tabY;
    Gaus gaus;
    double[][] TablicaN;
    public double[][] C = new double[4][4];
    double[] det;
    int n;

    public MacierzC(double c, double ro, ElementUniversalny elementUniversalny, double[] tabX, double[] tabY, int liczba_wezlow) {
        this.c = c;
        this.ro = ro;
        this.n = liczba_wezlow;
        this.elementUniversalny = elementUniversalny;
        this.tabX = tabX;
        this.tabY = tabY;
        this.gaus = elementUniversalny.gaus;
        this.TablicaN = new double[n * n][4];
        this.det = new double[n * n];

        for (int j = 0; j < n * n; j++) {
            for (int i = 0; i < 4; i++) {
                TablicaN[j][i] = elementUniversalny.N_fun[i].apply(gaus.wezly[(j % n)], gaus.wezly[(int) Math.floor((double) j / (double) n)]);
                //  System.out.print(TablicaN[j][i]);
            }


            // System.out.println("\n\n\n");

        }
        for (int i = 0; i < n * n; i++) {
            double dxEta = 0, dxKsi = 0, dyEta = 0, dyKsi = 0;
            for (int j = 0; j < 4; j++) {
                dxEta += tabX[j] * elementUniversalny.getEta()[i][j];
                dxKsi += tabX[j] * elementUniversalny.getKsi()[i][j];
                dyKsi += tabY[j] * elementUniversalny.getKsi()[i][j];
                dyEta += tabY[j] * elementUniversalny.getEta()[i][j];
            }
            det[i] = dxKsi * dyEta - dxEta * dyKsi;
        }
        // System.out.println(Arrays.deepToString(TablicaN));
    }

    public void Oblicz() {

        //  System.out.println(c + "   " + ro);
        for (int i = 0; i < n * n; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    C[j][k] += (c * ro * TablicaN[i][j] * TablicaN[i][k] * det[i]) * gaus.getWagi()[i % n] * gaus.getWagi()[(int) Math.floor((double) i / (double) n)];
                }
            }
        }
        //  System.out.println(Arrays.deepToString(C));

    }

}


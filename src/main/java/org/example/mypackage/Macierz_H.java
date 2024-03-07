package org.example.mypackage;

import java.util.Arrays;

public class Macierz_H {


    //punkty całkowania
    double[] tabX;
    double[] tabY;
    int n;
    double[][] Jakobian;
    double[][] JakobianOdwrocony;
    double[] det;
    public ElementUniversalny elementUniversalny;
    Gaus gaus;

    double[][] DNdX;
    double[][] DNdY;

    double[][] HX = new double[4][4];
    double[][] HY = new double[4][4];
    double[][][] H;
    Global_Data globalData;


    public double[][] getH_final() {
        return H_final;
    }

    double[][] H_final = new double[4][4];

    public Macierz_H(double[] tabX, double[] tabY, int liczba_wezlow, Global_Data globalData) {
        this.tabX = tabX;
        this.tabY = tabY;
        this.n = liczba_wezlow;
        elementUniversalny = new ElementUniversalny(liczba_wezlow);
        elementUniversalny.oblicz();
        gaus = new Gaus(n);
        this.globalData = globalData;
        this.Jakobian = new double[n * n][4];
        this.JakobianOdwrocony = new double[n * n][4];
        this.DNdX = new double[n * n][4];
        this.DNdY = new double[n * n][4];
        this.H = new double[n * n][4][4];
        this.det = new double[n * n];
    }

    public void Jakobian() {
        for (int i = 0; i < n * n; i++) {
            double dxEta = 0, dxKsi = 0, dyEta = 0, dyKsi = 0;
            for (int j = 0; j < 4; j++) {
                dxEta += tabX[j] * elementUniversalny.getEta()[i][j];
                dxKsi += tabX[j] * elementUniversalny.getKsi()[i][j];
                dyKsi += tabY[j] * elementUniversalny.getKsi()[i][j];
                dyEta += tabY[j] * elementUniversalny.getEta()[i][j];
            }
            det[i] = dxKsi * dyEta - dxEta * dyKsi;

            //Jakobian
            Jakobian[i][0] = dxKsi;
            Jakobian[i][1] = dyKsi;
            Jakobian[i][2] = dxEta;
            Jakobian[i][3] = dyEta;


            //Odwrótka jakobianu

            JakobianOdwrocony[i][0] = Jakobian[i][3] * 1 / det[i];
            JakobianOdwrocony[i][1] = -Jakobian[i][2] * 1 / det[i];
            JakobianOdwrocony[i][2] = -Jakobian[i][1] * 1 / det[i];
            JakobianOdwrocony[i][3] = Jakobian[i][0] * 1 / det[i];


        }
////
//        System.out.println("Wartość Jakobiana Nie odwroconego siuuu");
//        elementUniversalny.print(Jakobian);
//        System.out.println("Wartość Jakobiana odworcnoego");
//        elementUniversalny.print(JakobianOdwrocony);
    }

    public void Nazwa() {
        for (int i = 0; i < n * n; i++) {
            for (int j = 0; j < 4; j++) {
                DNdX[i][j] = JakobianOdwrocony[i][0] * elementUniversalny.getKsi()[i][j] + JakobianOdwrocony[i][2] * elementUniversalny.getEta()[i][j];
                DNdY[i][j] = JakobianOdwrocony[i][1] * elementUniversalny.getKsi()[i][j] + JakobianOdwrocony[i][3] * elementUniversalny.getEta()[i][j];
            }
        }
    }

    public void ObliczH() {
        int waga;

        for (int k = 0; k < n * n; k++) {
            // Obliczenia dla punktu całkowania k+1
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    HX[i][j] = DNdX[k][i] * DNdX[k][j];
                    HY[i][j] = DNdY[k][i] * DNdY[k][j];
                    H[k][i][j] = globalData.getConductivity() * (HX[i][j] + HY[i][j]) * det[k];

                    waga = (int) Math.floor(k / n);
                    H_final[i][j] += H[k][i][j] * gaus.getWagi()[k % n] * gaus.getWagi()[waga];

                }
            }
        }

    }


    public void print_H(double[][] tablicionen_machiiren) {
        for (int i = 0; i < (int) Math.pow(2, 2); i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(tablicionen_machiiren[i][j] + " ");
            }
            System.out.println(" ");

        }
    }


}

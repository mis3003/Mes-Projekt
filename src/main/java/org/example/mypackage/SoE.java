package org.example.mypackage;

import org.apache.commons.math3.linear.*;

import javax.swing.plaf.IconUIResource;
import java.util.Arrays;

public class SoE {//System of equations??


    Grid grid;

    int wezly;
    double[][] H = new double[4][4];
    public double[][] Hagro;
    public double[][] zbiornik_temperatury;
    public double[][] Cagro;
    public double[] Pagro;
    public double[] t0;
    Global_Data globalData;
    double tau;


    public SoE(int wezly, Global_Data globalData) {
        this.wezly = wezly;
        Hagro = new double[wezly][wezly];
        Cagro = new double[wezly][wezly];
        Pagro = new double[wezly];
        t0 = new double[wezly];
        this.globalData = globalData;
        zbiornik_temperatury = new double[(int) (globalData.getSimulationTime() / globalData.getSimulationSleepTime())][wezly];

        for (int i = 0; i < wezly; i++) {
            t0[i] = globalData.getInitialTemp();
        }
    }


    public void agregacja(Element element, Global_Data globalData) {

        tau = globalData.getSimulationSleepTime();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                H[i][j] = element.h[i][j] + element.hbc[i][j];
                // System.out.println(element.h[i][j]);

            }

        }

        //   System.out.println(Arrays.deepToString(H));
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Hagro[element.ID[i] - 1][element.ID[j] - 1] += H[i][j];
                Cagro[element.ID[i] - 1][element.ID[j] - 1] += element.c[i][j] / globalData.getSimulationSleepTime();
            }
            Pagro[element.ID[i] - 1] += element.p[i];

        }


    }

    public void Stacjonar() {//1200


        double[] t = LuMatrixsolver(Hagro, Pagro);

        System.out.print("\nWynik układu równań: t = [");
        for (int i = 0; i < wezly; i++) {
            System.out.print(t[i]);
            if (i < wezly - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");

    }

    public void niestacjonar() {
        double[][] Ctmp = new double[wezly][wezly];
        double[][] Htmp = new double[wezly][wezly];
        double[] Ptmp = new double[wezly];


        for (int k = 0; k < (int) globalData.getSimulationTime(); k += (int) globalData.getSimulationSleepTime()) {
            for (int i = 0; i < wezly; i++) {
                for (int j = 0; j < wezly; j++) {
                    Ctmp[i][j] = Cagro[i][j];
                    Htmp[i][j] = Hagro[i][j];

                }
                Ptmp[i] = Pagro[i];
            }


            for (int i = 0; i < wezly; i++) {
                for (int j = 0; j < wezly; j++) {
                    // Ctmp[i][j] /= globalData.getSimulationSleepTime();
                    Ptmp[i] += Ctmp[i][j] * t0[j];
                    Htmp[i][j] += Ctmp[i][j];
                }
            }

            t0 = LuMatrixsolver(Htmp, Ptmp);

            // System.out.println("Temeperatura obliczone po " + k + " sekundach");
//            for (int i = 0; i < t0.length; i++) {
//                System.out.println(t0[i]);
//            }
            findminmax(t0);
            for (int i = 0; i < wezly; i++) {
                zbiornik_temperatury[(int) (k / globalData.getSimulationSleepTime())][i] = t0[i];

            }
        }

//        System.out.println("Macierz Htmp");
//        for (int i = 0; i < wezly; i++) {
//            for (int j = 0; j < wezly; j++) {
//                System.out.print(Htmp[i][j]);
//            }
//            System.out.println();
//        }
//        System.out.println("Macierz Ctmp");
//        for (int i = 0; i < wezly; i++) {
//            for (int j = 0; j < wezly; j++) {
//                System.out.print(Ctmp[i][j]);
//            }
//            System.out.println();
//        }


    }

    public void findminmax(double[] t1) {
        double max = t1[0];
        double min = t1[0];
        for (int i = 0; i < t1.length; i++) {
            if (t1[i] > max) {
                max = t1[i];
            }
            if (t1[i] < min) {
                min = t1[i];
            }
        }

        System.out.println(min + "   " + max);
    }

    public double[] LuMatrixsolver(double[][] Htmp, double[] Ptmp) {
        RealMatrix Hmatrix = MatrixUtils.createRealMatrix(Htmp);
        RealVector PVector = new ArrayRealVector(Ptmp);
        DecompositionSolver solver = new LUDecomposition(Hmatrix).getSolver();
        return solver.solve(PVector).toArray();


    }


}



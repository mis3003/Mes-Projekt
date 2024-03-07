package org.example.mypackage;


import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ElementUniversalny {

    double Ksi[][];


    double Eta[][];

//    public double[] getWezly() {
//        return wezly;
//    }


    Gaus gaus;
    int liczba_wezlow;
    Function<Double, Double>[] ksi_fun = new Function[4];

    Function<Double, Double>[] eta_fun = new Function[4];
    Global_Data globalData = new Global_Data();


    public Surface[] surfaces;


    BiFunction<Double, Double, Double>[] N_fun = new BiFunction[4];


    private void InitFun() {


        ksi_fun[0] = x -> -(1d / 4d) * (1 - x);
        ksi_fun[1] = x -> (1d / 4d) * (1 - x);
        ksi_fun[2] = x -> (1d / 4d) * (1 + x);
        ksi_fun[3] = x -> -(1d / 4d) * (1 + x);

        eta_fun[0] = x -> -(1d / 4d) * (1 - x);
        eta_fun[1] = x -> -(1d / 4d) * (1 + x);
        eta_fun[2] = x -> (1d / 4d) * (1 + x);
        eta_fun[3] = x -> (1d / 4d) * (1 - x);


        N_fun[0] = (ksi, eta) -> 1d / 4d * (1d - ksi) * (1d - eta);
        N_fun[1] = (ksi, eta) -> 1d / 4d * (1d + ksi) * (1d - eta);
        N_fun[2] = (ksi, eta) -> 1d / 4d * (1d + ksi) * (1d + eta);
        N_fun[3] = (ksi, eta) -> 1d / 4d * (1d - ksi) * (1d + eta);


//wywoływanko
        //  ksi_fun[0].apply(12.0);
    }


    public ElementUniversalny(int liczba) {

        InitFun();
        this.liczba_wezlow = liczba;
        Ksi = new double[(int) Math.pow(liczba_wezlow, 2)][4];
        Eta = new double[(int) Math.pow(liczba_wezlow, 2)][4];

        //  wezly = new double[liczba_wezlow];
        this.gaus = new Gaus(liczba_wezlow);


        this.surfaces = new Surface[4];
        for (int i = 0; i < 4; i++) {
            surfaces[i] = new Surface(this.liczba_wezlow);
        }

        for (int i = 0; i < liczba_wezlow; i++) {
            for (int j = 0; j < 4; j++) {
                surfaces[0].N_tab[i][j] = N_fun[j].apply(gaus.wezly[i], -1d);
                surfaces[1].N_tab[i][j] = N_fun[j].apply(1d, gaus.wezly[i]);
                surfaces[2].N_tab[i][j] = N_fun[j].apply(gaus.wezly[(liczba_wezlow - 1) - i], 1d);
                surfaces[3].N_tab[i][j] = N_fun[j].apply(-1d, gaus.wezly[(liczba_wezlow - 1) - i]);


                //   System.out.println(Arrays.deepToString(surfaces[i].N_tab));

            }
        }

    }

    public void oblicz() {
        for (int i = 0; i < (int) Math.pow(liczba_wezlow, 2); i++) {
            for (int j = 0; j < 4; j++) {
                Ksi[i][j] = ksi_fun[j].apply(gaus.wezly[(int) Math.floor((double) i / liczba_wezlow)]);
                Eta[i][j] = eta_fun[j].apply(gaus.wezly[i % liczba_wezlow]);
            }
        }
        //System.out.println(Arrays.deepToString(Ksi));
        //  Arrays.sort(Ksi, (a, b) -> Double.compare(a[0], b[0]));
    }


    public void print(double[][] tablicionen_machiiren) {
        for (int i = 0; i < (int) Math.pow(liczba_wezlow, 2); i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(tablicionen_machiiren[i][j] + " ");
            }
            System.out.println(" ");

        }


    }


    public double[][] getKsi() {
        return Ksi;
    }

    public double[][] getEta() {
        return Eta;
    }

    public int getLiczba_wezlow() {
        return liczba_wezlow;
    }

    public Surface[] getSurfaces() {
        return surfaces;
    }

}





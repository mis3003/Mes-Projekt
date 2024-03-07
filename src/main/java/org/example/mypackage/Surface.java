package org.example.mypackage;

public class Surface {
    int liczba_wezlow;



public double N_tab[][];


    public Surface(int liczba_wezlow) {
        this.liczba_wezlow=liczba_wezlow;
        N_tab = new double[liczba_wezlow][4];

    }

    public double[][] getN_tab() {
        return N_tab;
    }
}

package org.example.mypackage;

public class Gaus {

    int liczba_wezlow;

    double[] wezly;


    double[] wagi;

    public double[] getWagi() {
        return wagi;
    }

    public Gaus(int liczba_wezlow) {
        this.liczba_wezlow = liczba_wezlow;
        wezly = new double[liczba_wezlow];
        wagi = new double[liczba_wezlow];
//zmieniałem kolejność wag może się coś zepsuć
        if (this.liczba_wezlow == 2) {

            wezly[0] = -Math.sqrt(1.0 / 3.0);
            wezly[1] = Math.sqrt(1.0 / 3.0);
            wagi[0] = 1;
            wagi[1] = 1;

        } else if (this.liczba_wezlow == 3) {
            wezly[0] = -Math.sqrt(3.0 / 5.0);
            wezly[1] = 0;
            wezly[2] = Math.sqrt(3.0 / 5.0);
            wagi[0] = 5.0 / 9.0;
            wagi[1] = 8.0 / 9.0;
            wagi[2] = 5.0 / 9.0;
        } else if (this.liczba_wezlow == 4) {

            wezly[0] = -Math.sqrt((3d / 7d) + (2d / 7d) * Math.sqrt(6d / 5d));
            wezly[1] = -Math.sqrt(3d / 7d - 2d / 7d * Math.sqrt(6d / 5d));
            wezly[2] = Math.sqrt((3d / 7d) - (2d / 7d) * Math.sqrt(6d / 5d));
            wezly[3] = Math.sqrt(3d / 7d + 2d / 7d * Math.sqrt(6d / 5d));
            wagi[0] = (18 - Math.sqrt(30)) / 36;
            wagi[1] = (18 + Math.sqrt(30)) / 36;
            wagi[2] = (18 + Math.sqrt(30)) / 36;
            wagi[3] = (18 - Math.sqrt(30)) / 36;

            //Dla tego działa liczenie H
//            wezly[0] = -Math.sqrt((3d / 7d) - (2d / 7d) * Math.sqrt(6d / 5d));
//            wezly[1] = -Math.sqrt(3d / 7d + 2d / 7d * Math.sqrt(6d / 5d));
//            wezly[2] = Math.sqrt((3d / 7d) - (2d / 7d) * Math.sqrt(6d / 5d));
//            wezly[3] = Math.sqrt(3d / 7d + 2d / 7d * Math.sqrt(6d / 5d));
//            wagi[0] = (18 + Math.sqrt(30)) / 36;
//            wagi[1] = (18 - Math.sqrt(30)) / 36;
//            wagi[2] = (18 + Math.sqrt(30)) / 36;
//            wagi[3] = (18 - Math.sqrt(30)) / 36;
        } else {
            System.out.println("Przekroczono liczbe wezlow");
            System.exit(0);
        }
    }


    public double f1(double x) {

        return 5 * Math.pow(x, 2) + 3 * x + 6;
    }

    public double f2(double x, double y) {

        return 5 * Math.pow(x, 2) * Math.pow(y, 2) + 3 * x * y + 6;
    }

    public double Gausf1() {


        double wynik = 0;
        for (int i = 0; i < this.liczba_wezlow; i++) {
            wynik += this.wagi[i] * f1(this.wezly[i]);
        }
        return wynik;

    }

    public double Gausf2() {
        double wynik = 0;

        for (int i = 0; i < this.liczba_wezlow; i++) {
            for (int j = 0; j < this.liczba_wezlow; j++) {
                wynik += wagi[i] * wagi[j] * f2(wezly[i], wezly[j]);
            }
        }
        return wynik;
    }

    public double oblicz_calke(ElementUniversalny elementUniversalny, double start, double end) {
        double wynik = 0d;
        double[] x = new double[4];
        double[] y = new double[4];
        elementUniversalny.oblicz();

        x[0] = 0;
        x[1] = 0.025;
        x[2] = 0.025;
        x[3] = 0.;

        y[0] = 0;
        y[1] = 0;
        y[2] = 0.025;
        y[3] = 0.025;

        double dy = 0;
        for (int i = 0; i < 4; i++) {
            dy += y[i] + elementUniversalny.getKsi()[1][i];

        }
        System.out.println(dy);
        return wynik;
    }


}

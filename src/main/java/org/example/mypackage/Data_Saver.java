package org.example.mypackage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public class Data_Saver {
    SoE soe;
    Scanner scanner;

    String nazwapliku;
    Global_Data globalData;
    Grid grid;
    String text;

    public Data_Saver(SoE soe, Global_Data globalData, Grid grid) {
        this.soe = soe;
        this.globalData = globalData;
        this.grid = grid;
    }

    public void UsunPliki() {
        File folder = new File("./src/main/resources/Wyniki");
        File[] pliki = folder.listFiles();
        if (pliki != null) {
            for (File p : pliki) {
                p.delete();
            }
        }

    }

    public void zapisz() throws IOException {
        UsunPliki();
        for (int k = 0; k < (int) (globalData.getSimulationTime() / globalData.getSimulationSleepTime()); k++) {
            try {
                nazwapliku = "./src/main/resources/Wyniki/Foo" + k + ".vtk";
                File plik = new File(nazwapliku);
                if (!plik.exists()) {
                    plik.createNewFile();
                }
                FileWriter writer = new FileWriter(plik);
                writer.write("# vtk DataFile Version 2.0\nUnstructured Grid Example\nASCII\nDATASET UNSTRUCTURED_GRID\n\n");

                writer.write("POINTS " + globalData.getNode_number() + " float\n");
                for (Node n : grid.nodes) {
                    writer.write(n.X + " " + n.Y + " " + 0 + "\n");
                }
                writer.write("\n");
                System.out.println("\n");
                writer.write("CELLS " + globalData.getElements_number() + " " + globalData.getElements_number() * 5 + " ");
                for (Element e : grid.elements) {
                    writer.write(4 + " ");
                    for (int i = 0; i < 4; i++) {
                        writer.write(e.ID[i] - 1 + " ");
                    }
                    writer.write("\n");

                }
                writer.write("\n");
                writer.write("CELL_TYPES " + globalData.getElements_number());
                writer.write("\n");
                for (int i = 0; i < globalData.getElements_number(); i++) {
                    writer.write(9 + "\n");
                }
                writer.write("\n");
                writer.write("POINT_DATA " + globalData.getNode_number() + "\nSCALARS Temp float 1\nLOOKUP_TABLE default\n");

                for (int i = 0; i < (soe.wezly); i++) {
                    writer.write(soe.zbiornik_temperatury[k][i] + "\n");
                }


                writer.close();

            } catch (IOException e) {
                System.err.println("Wystąpił błąd podczas zapisywania do pliku: " + e.getMessage());
            }
        }
//
    }
}

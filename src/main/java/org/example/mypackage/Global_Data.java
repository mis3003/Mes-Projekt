package org.example.mypackage;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Global_Data {
    private double SimulationTime;
    private double SimulationSleepTime;
    private double Conductivity;
    private double Alfa;
    private double Tot;
    private double InitialTemp;
    private double Density;
    private double SpecificHeat;

    int Node_number;
    int Elements_number;


}

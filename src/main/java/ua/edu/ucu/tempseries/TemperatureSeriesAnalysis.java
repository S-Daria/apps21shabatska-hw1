package ua.edu.ucu.tempseries;

import java.util.Arrays;
import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {

    private double[] temperatureSeries;
    private int minTemp = -273;

    public TemperatureSeriesAnalysis() {
        this.temperatureSeries = new double[]{};

    }


    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        for (double temperature: temperatureSeries) {
            if (temperature < minTemp) {
                throw new InputMismatchException();
            }
        }
        this.temperatureSeries = temperatureSeries;
    }

    private void ValidateSeries(){
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }
    }

    public double average() {
        ValidateSeries();
        double sum = 0;

        for (int i = 0; i < temperatureSeries.length; i++) {
            sum += temperatureSeries[i];
        }

        return sum/temperatureSeries.length;
    }

    public double deviation() {
        ValidateSeries();

        double mean = average();
        double sqDevsSum = 0;

        for (int i = 0; i < temperatureSeries.length; i++) {
            double dev = temperatureSeries[i] - mean;
            sqDevsSum += dev * dev;
        }

        return Math.sqrt(sqDevsSum/temperatureSeries.length);
    }

    public double min() {
        ValidateSeries();
        double minval = 999999999;
        for (int i = 0; i < temperatureSeries.length; i++) {
            if (temperatureSeries[i] < minval) {
                minval = temperatureSeries[i];
            }
        }
        return minval;
    }

    public double max() {
        ValidateSeries();
        double maxval = minTemp;
        for (int i = 0; i < temperatureSeries.length; i++) {
            if (temperatureSeries[i] > maxval) {
                maxval = temperatureSeries[i];
            }
        }
        return maxval;
    }

    public double findTempClosestToZero() {
        ValidateSeries();

        double closest = temperatureSeries[0];

        for (double temperature: temperatureSeries) {
            if (temperature <= Math.abs(closest) || Math.abs(temperature) < Math.abs(closest)) {
                closest = temperature;
            }
        }
        return closest;
    }

    public double findTempClosestToValue(double tempValue) {
        ValidateSeries();

        double closest = temperatureSeries[0];
        double closestDist = Math.abs(temperatureSeries[0] - tempValue);

        for (double temperature: temperatureSeries) {
            if (Math.abs((temperature - tempValue)) < closestDist) {
                closest = temperature;
                closestDist = Math.abs((temperature - tempValue));
            }
        }
        return closest;
    }

    public double[] findTempsLessThen(double tempValue) {
        ValidateSeries();

        int lessLen = 0;

        for (double temperature : temperatureSeries) {
            if (temperature < tempValue) { lessLen++; }
        }

        double[] lessTemps = new double[lessLen];

        int j = 0;
        for (double temperature : temperatureSeries) {
            if (temperature < tempValue) {
                lessTemps[j] = temperature;
                j++;
            }
        }

        return lessTemps;
    }

    public double[] findTempsGreaterThen(double tempValue) {
        ValidateSeries();

        int greaterLen = 0;

        for (double temperature : temperatureSeries) {
            if (temperature > tempValue) { greaterLen++; }
        }

        double[] greaterTemps = new double[greaterLen];

        int j = 0;
        for (double temperature : temperatureSeries) {
            if (temperature > tempValue) {
                greaterTemps[j] = temperature;
                j++;
            }
        }

        return greaterTemps;
    }

    public TempSummaryStatistics summaryStatistics() {
        ValidateSeries();
        double avgTemp = average();
        double devTemp = deviation();
        double minTemp = min();
        double maxTemp = max();
        return new TempSummaryStatistics(avgTemp, devTemp, minTemp, maxTemp);
    }

    public int addTemps(double... temps) {
        for (double temp: temps) {
            if (temp < minTemp) {
                throw new InputMismatchException();
            }
        }
        int previousLength = temperatureSeries.length;
        temperatureSeries = Arrays.copyOf(temperatureSeries, temps.length + previousLength);

        int j = 0;
        for (double temp : temps) {
            temperatureSeries[previousLength + j] = temp;
            j++;
        }

        return temperatureSeries.length;
    }
}

package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

//  static double[] p = {0.0, .15, .1, .05, .1, .2};
//  static double[] q = {.05, .1, .05, .05, .05, .1};

  public static void main(String[] args) {
    OBST obst = new OBST();

    for (int index = 1; index < 4; index++) {
      double[] p = new double[(int) Math.pow(10, index) + 1];
      double[] q = new double[(int) Math.pow(10, index) + 1];
      p = generateRandomProbability(p.length);
      q = generateRandomProbability(q.length);
      p[0] = 0;
      saveProbabilities(p);
      saveProbabilities(q);
      obst.OptimalBST(p, q, q.length);
      saveTable(obst.getRoot(), (int) Math.pow(10, index));
      saveTable(obst.getE(), (int) Math.pow(10, index));
      saveTable(obst.getW(), (int) Math.pow(10, index));
    }
  }

  public static double[] generateRandomProbability(int size) {

    double[] array = new double[size];
    BigDecimal rounded;

    for (int i = 0; i < size; i++) {
      double random = ThreadLocalRandom.current().nextDouble((.1 / (size - 1)), (1.0 / (size - 1)));
      rounded = new BigDecimal(random).setScale(6, RoundingMode.HALF_DOWN);
      array[i] = rounded.doubleValue();
    }
    return array;
  }

  public static void saveProbabilities(double[] array) {
    String letter = array[0] == 0 ? "p" : "q";

    try {
      FileWriter myWriter = new FileWriter(
          "ProbabilitiesUsed/" + letter + (array.length - 1) + "probabilities.txt");
      for (double probability : array) {
        myWriter.write(probability + "\n");
      }
      myWriter.close();
      System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

  }

  public static void saveTable(double[][] table, int size) {
    String fileName;
    if (table[0][0] == 0.1) {
      fileName = "Tables/eTables/" + size + "eTable.txt";
    } else if (table[0][0] == 1.0) {
      fileName = "Tables/wTables/" + size + "wTable.txt";
    } else {
      fileName = "Tables/rootTables/" + size + "rootTable.txt";
    }

    try {
      FileWriter myWriter = new FileWriter(fileName);
      for (double[] row : table) {
        myWriter.write(Arrays.toString(row) + "\n");
      }
      myWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}


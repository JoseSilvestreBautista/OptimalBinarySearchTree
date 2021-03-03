package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

  static double[][] e;
  static double[][] w;
  static double[][] root;
  /**
   * Number of probabilities needed for each key set
   */
  static int[] probabilitySizes = {21, 201, 2001, 20001, 200001};
  /**
   * A limit that helps distribution of probability.
   */
  static double[] boundaries = {.09, .009, .00099, .000099, .0000099};
  static String[] rootMatrixFile = {"10key_rootMatrix.txt", "100key_rootMatrix.txt",
      "1000key_rootMatrix.txt",
      "10000key_rootMatrix.txt", "100000key_rootMatrix.txt"};
  static String[] eMatrixFile = {"10key_eMatrix.txt", "100key_eMatrix.txt", "1000key_eMatrix.txt",
      "10000key_eMatrix.txt", "100000key_eMatrix.txt"};
  static String[] wMatrixFile = {"10key_wMatrix.txt", "100key_wMatrix.txt", "1000key_wMatrix.txt",
      "10000key_wMatrix.txt", "100000key_wMatrix.txt"};
  static String[] inputFile = {"10keyInput", "100keyInput.txt", "1000keyInput.txt",
      "10000keyInput.txt", "100000keyInput.txt"};
//  double[] p = {0.0, .15, .1, .05, .1, .2};
//  double[] q = {.05, .1, .05, .05, .05, .01,};

  public static void main(String[] args) {

    for (int index = 0; index < probabilitySizes.length; index++) {
      double[] p = new double[(probabilitySizes[index] / 2) + 1];
      double[] q = new double[(probabilitySizes[index] / 2) + 1];
      generateRandomNumbers(probabilitySizes[index], boundaries[index], p, q, inputFile[index]);
      double startTime = System.nanoTime();
      OptimalBST(p, q, q.length);
      double endTime = System.nanoTime();
      double duration = (endTime - startTime) / 1000000;
      System.out.println((probabilitySizes[index] / 2) + duration + " milliSeconds");
//    printMtrices();
      writeToFile(eMatrixFile[index], wMatrixFile[index], rootMatrixFile[index]);
    }
  }

  /**
   * Finds optimal BST
   *
   * @param p successful probability
   * @param q unsuccessful probability
   * @param n size of p and q array
   */
  public static void OptimalBST(double[] p, double[] q, int n) {
    e = new double[n + 1][n];
    w = new double[n + 1][n];
    root = new double[n][n];
    int k;
    double t;
    for (int i = 1; i < n + 1; i++) {

      e[i][i - 1] = q[i - 1];
      w[i][i - 1] = q[i - 1];
    }

    for (int l = 1; l < n; l++) {
      for (int i = 1; i < n - l + 1; i++) {
        k = i + l - 1;
        e[i][k] = 1000001;
        w[i][k] = w[i][k - 1] + p[k] + q[k];
        for (int r = i; r <= k; r++) {
          t = e[i][r - 1] + e[r + 1][k] + w[i][k];
          if (t < e[i][k]) {
            e[i][k] = t;
            root[i][k] = r;
          }
        }
      }
    }
  }

  /**
   * Makes probabilities and stores them in txt files
   *
   * @param size          Total number of probabilities needed
   * @param boundary      limit the size of probability generated
   * @param p             successful probability
   * @param q             unsuccessful probability
   * @param inputFileName holds the file name for each key set
   */
  public static void generateRandomNumbers(int size, double boundary, double[] p, double[] q,
      String inputFileName) {
    p[0] = 0;
    double sum = 0.0;

    for (int i = 0; i < size; i++) {
      if (i < (size / 2)) {
        p[i + 1] = ThreadLocalRandom.current().nextDouble(0, boundary);
        sum += p[i + 1];
      } else if (i == size - 1) {
        q[i - size / 2] = 1.0 - sum;
        break;
      } else {
        q[i - size / 2] = ThreadLocalRandom.current().nextDouble(0, boundary);
        sum += q[i - size / 2];
      }
    }
    if (q[q.length - 1] < 0) {
      generateRandomNumbers(size, boundary, p, q, inputFileName);
    }

    try {
      FileWriter myWriter = new FileWriter(inputFileName);
      myWriter.write("p:\n");
      for (double row : p) {
        myWriter.write(row + "\n");
      }
      myWriter.write("q:\n");
      for (double row : q) {
        myWriter.write(row + "\n");
      }
      myWriter.close();
      System.out.println("Successfully wrote Input to the file.");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

//    System.out.println("p: " + Arrays.toString(p));
//    System.out.println("q: " + Arrays.toString(q));
  }

  /**
   * Prints what is in the root, e, and w matrix to the prompt
   */
  public static void printMtrices() {
    System.out.println("root:");
    for (double[] row : root) {
      System.out.println(Arrays.toString(row));
    }
    System.out.println("\n" + "e:");
    for (double[] row : e) {
      System.out.println(Arrays.toString(row));
    }
    System.out.println("\n" + "w:");
    for (double[] row : w) {
      System.out.println(Arrays.toString(row));
    }

  }

  /**
   * Saves matrices to txt files
   *
   * @param eFileName    has the file name for the e matrices
   * @param wFileName    has the file name for the w matrices
   * @param rootFileName has the file name for the root matrices
   */
  static void writeToFile(String eFileName, String wFileName, String rootFileName) {
    try {
      FileWriter myWriter = new FileWriter(eFileName);
      myWriter.write("e:\n");
      for (double[] row : e) {
        myWriter.write(Arrays.toString(row) + "\n");
      }
      myWriter.close();

      myWriter = new FileWriter(wFileName);
      myWriter.write("w:\n");
      for (double[] row : w) {
        myWriter.write(Arrays.toString(row) + "\n");
      }
      myWriter.close();

      myWriter = new FileWriter(rootFileName);
      myWriter.write("root:\n");
      for (double[] row : root) {
        myWriter.write(Arrays.toString(row) + "\n");
      }
      myWriter.close();
      System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  /**
   * Was supposed to print the root matrix into a shape of a tree. I could not properly create a
   * method to accomplish this.
   */
  static void createTree() {

  }

}

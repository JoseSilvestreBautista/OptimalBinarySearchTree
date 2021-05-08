package com.company;

public class OBST {

  private double[][] e;
  private double[][] w;
  private double[][] root;

  public void OptimalBST(double[] p, double[] q, int n) {

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
    e[0][0] = 0.1;
    w[0][0] = 1.0;
  }

  public double[][] getE() {
    return e;
  }

  public double[][] getW() {
    return w;
  }

  public double[][] getRoot() {
    return root;
  }
}
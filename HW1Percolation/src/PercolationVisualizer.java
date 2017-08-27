/******************************************************************************
 *  Compilation:  javac PercolationVisualizer.java
 *  Execution:    java PercolationVisualizer input.txt
 *  Dependencies: Percolation.java
 *
 *  This program takes the name of a file as a command-line argument.
 *  From that file, it
 *
 *    - Reads the grid size n of the percolation system.
 *    - Creates an n-by-n grid of sites (intially all blocked)
 *    - Reads in a sequence of sites (row i, column j) to open.
 *
 *  After each site is opened, it draws full sites in light blue,
 *  open sites (that aren't full) in white, and blocked sites in black,
 *  with with site (1, 1) in the upper left-hand corner.
 *
 ******************************************************************************/

import java.awt.Font;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationVisualizer {

    // delay in miliseconds (controls animation speed)
    private static final int DELAY = 0;

    // draw n-by-n percolation system
    public static void draw(Percolation perc, int n) {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(-0.05*n, 1.05*n);
        StdDraw.setYscale(-0.05*n, 1.05*n);   // leave a border to write text
        StdDraw.filledSquare(n/2.0, n/2.0, n/2.0);

        // draw n-by-n grid
        int opened = 0;
        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= n; col++) {
                if (perc.isFull(row, col)) {
                    StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
                    opened++;
                }
                else if (perc.isOpen(row, col)) {
                    StdDraw.setPenColor(StdDraw.WHITE);
                    opened++;
                }
                else
                    StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.filledSquare(col - 0.5, n - row + 0.5, 0.45);
            }
        }

        // write status text
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 12));
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(0.25*n, -0.025*n, opened + " open sites");
        if (perc.percolates()) StdDraw.text(0.75*n, -0.025*n, "percolates");
        else                   StdDraw.text(0.75*n, -0.025*n, "does not percolate");

    }

    public static void main(String[] args) {
        //In in = new In(args[0]);      // input file
/*        In in = new In("input50.txt");
        int n = in.readInt();         // n-by-n percolation system

        // turn on animation mode
        StdDraw.enableDoubleBuffering();

        // repeatedly read in sites to open and draw resulting system
        Percolation perc = new Percolation(n);
        draw(perc, n);
        StdDraw.show();
        StdDraw.pause(DELAY);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
            draw(perc, n);
            StdDraw.show();
            StdDraw.pause(DELAY);
        }
        System.out.println();*/


        int N = 100;
        for (int i = 0; i <= 5; i++) {
            // turn on animation mode
            StdDraw.show(0);
            int attempts = 0;
            int count = 0;
            Percolation percolation = new Percolation(N);
            draw(percolation, N);
            StdDraw.show(DELAY);
            long startTime1 = System.currentTimeMillis();
            while (!percolation.percolates()) {
                attempts++;
                int row = StdRandom.uniform(1, N + 1);
                int column = StdRandom.uniform(1, N + 1);
                if (percolation.isOpen(row, column)) {
                    continue;
                }

                percolation.open(row, column);
                draw(percolation, N);
                StdDraw.show(DELAY);

                if (++count > (0.594 * N * N)) {
                    break;
                }
            }
            long endTime1 = System.currentTimeMillis();
            System.out.println(String.format("%d %d %f %d %d %d", N, N * N, 0.594 * N * N, attempts, count, (endTime1 - startTime1) / 1000));
            System.out.println("It took " + (endTime1 - startTime1) / 1000 + " seconds");
        }
    }
}

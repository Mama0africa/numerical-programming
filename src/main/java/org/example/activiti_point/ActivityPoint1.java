package org.example.activiti_point;

import org.example.numericall.algorithms.matrix_algorithms.MatrixOperations;
import org.example.numericall.algorithms.norms.Norm;
import org.example.numericall.algorithms.vector_algorithms.VectorOperations;
import org.example.numericall.plotter.Plotter;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ActivityPoint1
{
    public static void main(String[] args)
    {

        Plotter plotter = new Plotter();

        // indicates how many random balls do we want to create
        int amountOfBalls = 10;

        // order of the norm to find
        int p = 1;

        // created matrices using coordinate and color vectors, NOTE: i used third coordinate 1 for coordinate vector
        // for example if the coordinate vector is [3, 2] i've made it [3, 2, 1]
        // and the logic of creating matrix is merging these two vectors, if i have color vector [23, 40, 15]
        // the matrix will be [[3, 2, 1], [23, 40, 25]]

        // structure for holding matrices
        double[][][] matrices = new double[amountOfBalls][2][3];





        // choose vector norms 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
        // create random instance to create random locations later on
        Random random = new Random();

        // ball coordinates
        double[][] centerXYCoordinatesOfBalls = new double[amountOfBalls][amountOfBalls];




        // create all the desired amount of ball centers
        for (int i = 0; i < amountOfBalls; i++)
        {
            // create random center as vector [x, y]
            double[] coordinateVector = { random.nextDouble(0, 41), random.nextDouble(0, 41) };
            centerXYCoordinatesOfBalls[i] = coordinateVector;
        }


        // iterate trough the coordinate map and check if any coordinate satisfy our condition
        for (double[] center : centerXYCoordinatesOfBalls)
        {

            // represents the x coordinates that satisfy equation
            List<Double> xCoordinates = new ArrayList<>();

            // represents the y coordinates that satisfy equation
            List<Double> yCoordinates = new ArrayList<>();


            for (double y = -50; y < 50; y+=0.05)
            {
                for (double x = -50; x < 50; x+=0.05)
                {
                    if
                    (

                            // now norm is there but you change it as you want
                            Norm.calculateVectorNorm(Objects.requireNonNull(VectorOperations.subtractVectors(
                                    new double[] { x, y }, center)), p) < 1
                    )
                    {
                        xCoordinates.add(x);
                        yCoordinates.add(y);
                    }
                }
            }

            // merge x and y coordinates into double array to pass it in plotter draw function
            double[][] mergedXY = new double[2][xCoordinates.size()];


            for (int i = 0; i < yCoordinates.size(); i++)
            {
                mergedXY[0][i] = xCoordinates.get(i);
                mergedXY[1][i] = yCoordinates.get(i);
            }

            // plot everything that is found
            Color colorVector = new Color(random.nextInt(0, 256),
                    random.nextInt(0, 256),
                    random.nextInt(0, 256));
            plotter.drawPoints(p + " Norm", colorVector, mergedXY, center);

            // construct corresponding matrix
            double[][] correspondingMatrix = { new double[] { center[0], center[1], 1 },
                                                new double[] { colorVector.getRed(), colorVector.getGreen(), colorVector.getBlue() } };

            // insert matrices
            matrices[p - 1] = correspondingMatrix;

            // increase order
            p += 1;

        }

        // find two closest matrices using matrix norm
        // store norms of the matrices into vector
        double[] normsOfMatrices = new double[amountOfBalls];
        for (int i = 0; i < amountOfBalls; i++)
        {
            normsOfMatrices[i] = MatrixOperations.calculateFirstNorm(matrices[i]);
        }

        // make of normsOfMatrices in order to find two closest number in it
        double[] normsOfMatricesSortedClone = normsOfMatrices.clone();
        Arrays.sort(normsOfMatricesSortedClone);

        // variable that stores the two closest number as tuple
        double[] closest = { normsOfMatricesSortedClone[0], normsOfMatricesSortedClone[1] };
        // stores the absolute difference between values
        double minimalDifferenceBetween = Math.abs(normsOfMatricesSortedClone[1] - normsOfMatricesSortedClone[0]);
        for (int i = 1; i < amountOfBalls; i++)
        {
            double dif = Math.abs(normsOfMatricesSortedClone[i] - normsOfMatricesSortedClone[i - 1]);
            if (minimalDifferenceBetween < dif)
            {
                minimalDifferenceBetween = dif;
                closest[0] = normsOfMatricesSortedClone[i - 1];
                closest[1] = normsOfMatricesSortedClone[i];
            }
        }

        // represents figures with norms to connect because they are most similar
        int[] closestNormIndexes = new int[2];
        // this variable is for counting if the both of the values are found we should stop the loop
        var countHowManyWasFound = 0;
        // now find the indexes of that two number because they correlate to the norms - 1
        for (int i = 0; i < amountOfBalls; i++)
        {
            if (countHowManyWasFound == 2)
            {
                break;
            }

            if (normsOfMatrices[i] == closest[0] || normsOfMatrices[i] == closest[1])
            {
                closestNormIndexes[countHowManyWasFound] = i;
                countHowManyWasFound++;
            }

        }

        var centersToConnect = new double[][]{ centerXYCoordinatesOfBalls[closestNormIndexes[0]],
                                                centerXYCoordinatesOfBalls[closestNormIndexes[1]]} ;


        // connect closest balls
        plotter.drawLine("closest", Color.RED, centersToConnect,
                new double[] { (centersToConnect[0][0] + centersToConnect[1][0]) / 2,
                        (centersToConnect[0][1] + centersToConnect[1][1]) / 2 });



    }
}



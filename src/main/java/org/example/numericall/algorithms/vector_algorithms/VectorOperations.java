package org.example.numericall.algorithms.vector_algorithms;

public class VectorOperations
{






    public static double[] addVectors(double[]... vectorsToAdd)
    {
        if (vectorsToAdd.length < 1)
        {
            return null;
        }
        double[] newVector = createClone(vectorsToAdd[0]);
        // Check if the every vector is from same vector space
        if (vectorsAreFromSameVectorSpace(vectorsToAdd))
        {
            for (int i = 1; i < vectorsToAdd.length; i++)
            {
                for (int j = 0; j < newVector.length; j++)
                {
                    // vector addition
                    newVector[j] = newVector[j] + vectorsToAdd[i][j];
                }
            }
        }
        return newVector;

    }

    public static double[] subtractVectors(double[]... vectorsToAdd)
    {
        if (vectorsToAdd.length < 1)
        {
            return null;
        }
        double[] newVector = createClone(vectorsToAdd[0]);
        // Check if the every vector is from same vector space
        if (vectorsAreFromSameVectorSpace(vectorsToAdd))
        {
            for (int i = 1; i < vectorsToAdd.length; i++)
            {
                for (int j = 0; j < newVector.length; j++)
                {
                    // vector addition
                    newVector[j] = newVector[j] - vectorsToAdd[i][j];
                }
            }
        }
        return newVector;

    }


    public static double[] createClone(double[] vector)
    {
        var size = vector.length;
        double[] clone = new double[size];
        // creating clone

        System.arraycopy(vector, 0, clone, 0, size);

        // return the clone
        return clone;
    } // end of the createClone()





    public static double[] scalarMultiplication(double scalar, double[] vector)
    {

        double[] newVector = createClone(vector);

        // multiply everything to scalar
        for (int i = 0; i < vector.length; i++)
        {
            newVector[i] = newVector[i] * scalar;
        }

        return newVector;
    }



    public static boolean vectorsAreFromSameVectorSpace(double[]... vectorsToCheck)
    {
        if (vectorsToCheck.length > 1)
        {
            for (int i = 1; i < vectorsToCheck.length; i++)
            {
                if (vectorsToCheck[i].length != vectorsToCheck[i - 1].length)
                {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * maps rgb vector to grey scale value using formula Greyscale = 0.299 * Red + 0.587 * Green + 0.114 * Blue
     * @param rgbVector rgbVector with three values
     * @return corresponding greyscale value
     */
    public static double mapRGBVectorToGreyscale(double[] rgbVector)
    {
        if (rgbVector.length != 3)
        {
            throw new RuntimeException("illegal argument the length of the rgbVector should be 3");
        }
        else
        {
            return 0.299 * rgbVector[0] + 0.587 * rgbVector[1] + 0.114 * rgbVector[2];
        }
    }







}

package com.company;

/**
 * Created by JBrannan on 03/12/2016.
 */

public class Levenshtein {

    public static int calcMin (int a, int b, int c)
    {
        //initialising default minimum
        int min;
        min = a;
        //if b is a smaller number than a, set b to minimum
        if (b < min)
        {
            min = b;
        }
        //if c is a smaller number than b, c must be also smaller than a. Set c to minimum
        if (c < min)
        {
            min = c;
        }
        //return minimum number
        min = min + 1;
        return min;
    }

    public static int levDist(String wordOne, String wordTwo)
    {
        /*set word one to all lower case to ensure no errors due to
        unexpected capitals*/
        wordOne = wordOne.toLowerCase();
        wordTwo = wordTwo.toLowerCase();

        //length of wordOne and wordTwo
        int n = wordOne.length();
        int m = wordTwo.length();

        //empty matrix initialised to the dimensions of the two words the distance is being calculated for
        int matrix[][] = new int[n+1][m+1]; //initialising matrix with length of each word

        //empty char which will be used for specific characters of the word strings
        char charOfWordOne;
        char charOfWordTwo;

        if (n == 0)
        {
            //if wordOne is null, distance must be length of wordTwo
            return m;
        }

        if (m == 0)
        {
            //if wordTwo is null, distance must be length of wordOne
            return n;
        }


        for (int i = 0; i <= n; i++)
        {
            //initialise distances for null string of same length of wordOne along x-axis
            matrix[i][0] = i;
        }

        for (int j = 0; j <= m; j++)
        {
            //initialise distances for null string of same length of wordTwo along y-axis
            matrix[0][j] = j;
        }



        for (int k = 1; k <= n; k++ )
        {
            //set current character to one within the string for wordOne
            charOfWordOne = wordOne.charAt(k-1);

            /* once word two has determined each distance for the place on the matrix relative to
            wordOne, the next letter of wordOne is worked through */
            for (int l = 1; l <= m; l++)
            {
                //set current character to one within the string for wordTwo
                charOfWordTwo = wordTwo.charAt(l-1);

                if (charOfWordOne == charOfWordTwo)
                {
                    /*if current letter of x axis is equal to letter on y axis, make this input on the
                    matrix the same as the one diagonally (one place above and one place left of current
                    position)*/
                    matrix [k][l] = matrix [k-1][l-1];
                }
                else
                {
                    /* calculate minimum number to input on the matrix using one place to the left,
                    one place above and one place diagonally (one place above and one place left) of current
                    position*/
                    matrix[k][l] = calcMin(matrix[k-1][l], matrix[k][l-1], matrix[k-1][l-1]);
                }

            }



        }
        //return bottom right corner of matrix (distance from word one to word two)
        return matrix[n][m];
    }

}

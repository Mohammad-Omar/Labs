/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.util.*;
import java.lang.Math;

/**
 *
 * @author Mohammad Omar
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here 
        int counter = 0;
        Test t = new Test();
        String[] labels;
        final int ROWS = 15120;
        final int COLS = 56;
        final int ROWS1 = 565892;
        final int COLS1 = 55;

        int[] coverType = {1, 2, 3, 4, 5, 6, 7};
        int[] countCoverType = {0, 0, 0, 0, 0, 0, 0};
        int[] soilType = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
            31, 32, 33, 34, 35, 36, 37, 38, 39, 40};
        int[][] myDataArray = new int[ROWS][COLS];
        BufferedReader Test = new BufferedReader(new FileReader("train.csv"));
        String dataRow = Test.readLine(); // Read first line.
        labels = dataRow.split(",");
        String dataRow1;
        /*for (String item : labels) {
            System.out.print(item + "\t");
            counter++;
        }*/
        dataRow = Test.readLine();
        int i = 0, r = 0, c = 0;

        // The while checks to see if the data is null. If 
        // it is, we've hit the end of the file. If not, 
        // process the data.
        while (dataRow != null) {
            String[] numberStrings = dataRow.split(",");
            int[] dataArray = new int[numberStrings.length];
            for (i = 0; i < numberStrings.length; i++) {
                dataArray[i] = Integer.parseInt(numberStrings[i].trim());
            }
            t.insertIntoArray(myDataArray, dataArray, r);
            dataRow = Test.readLine(); // Read next line of data.
            r++;
        }
        Test.close();

        // start parsing test file
        int[][] myDataArray1 = new int[ROWS1][COLS1];
        Test = new BufferedReader(new FileReader("test.csv"));
        dataRow1 = Test.readLine(); // Read first line.
        labels = dataRow1.split(",");

        /*for (String item : labels) {
            System.out.print(item + "\t");

        }*/
        dataRow1 = Test.readLine();
        i = 0;
        r = 0;
        c = 0;

        // The while checks to see if the data is null. If 
        // it is, we've hit the end of the file. If not, 
        // process the data.
        while (dataRow1 != null) {
            String[] numberStrings1 = dataRow1.split(",");
            int[] dataArray1 = new int[numberStrings1.length];
            for (i = 0; i < numberStrings1.length; i++) {
                dataArray1[i] = Integer.parseInt(numberStrings1[i].trim());
            }
            t.insertIntoArray(myDataArray1, dataArray1, r);
            dataRow1 = Test.readLine(); // Read next line of data.
            r++;
        }
        Test.close();
        //end parsing test file

        int arrayLength = myDataArray.length;
        int seventyTrain = ((7 * arrayLength) / 10);
        int thirtyTest = r - seventyTrain;
        int[][] trainData = new int[seventyTrain][labels.length];
        int[][] testData = new int[thirtyTest][labels.length];
        int[][] testFile = new int[ROWS1][];

        int[] countForestType = {0, 0, 0, 0, 0, 0, 0};
        int[] tempType = {0, 0, 0, 0, 0, 0, 0};
        int j, k;
        double entropy = 0;
        for (i = 0; i < seventyTrain; i++) {
            trainData[i] = myDataArray[i];

        }
        //System.out.println(i);
        k = 0;
        for (j = i; j < arrayLength; j++) {
            testData[k] = myDataArray[j];
            k++;
        }
        k = 10;
        double neighbors[][] = new double[k][];
        for (i = 0; i < testFile.length; i++) {
            testFile[i] = myDataArray1[i];

        }
        Test.close();
        FileWriter writer = new FileWriter("result.csv");
        writer.append("ID");
        writer.append(",");
        writer.append("Cover_Type");
        writer.append("\n");
        for (i = 0; i < testFile.length; i++) {

            countForestType = tempType;
            for (j = 0; j < countForestType.length; j++) {
                countForestType[j] = 0;
            }
            //System.out.println("the type is inshALLAH: " + trainData[i][COLS - 1]);
            neighbors = t.getNeighbors(trainData, testFile[i], k);
            int determine, index = 0, covType;
            for (j = 0; j < neighbors.length; j++) {
                determine = (int) neighbors[j][0];

                index = determine - 1;
                covType = trainData[index][COLS - 1];
                if (covType == 1) {
                    countForestType[0]++;

                } else if (covType == 2) {
                    countForestType[1]++;

                } else if (covType == 3) {
                    countForestType[2]++;

                } else if (covType == 4) {
                    countForestType[3]++;

                } else if (covType == 5) {
                    countForestType[4]++;

                } else if (covType == 6) {
                    countForestType[5]++;

                } else {
                    countForestType[6]++;
                }
            }
            int maxVal = t.findMaxIndex(countForestType);
            int cov = maxVal + 1;
            writer.append( String.valueOf(testFile[i][0]));
            writer.append(",");
            writer.append(String.valueOf(cov));
            writer.append("\n");
            /*System.out.println("The forest Type is: " + cov);
            
             for(c = 0; c < countForestType.length; c++){
             System.out.printf("%d ", countForestType[c]);
             }
             System.out.println("");
             System.out.println("");
             */
        }
        writer.flush();
        writer.close();
        
       
       /* System.out.println("ID" + "\t" + "Distance");
        for (i = 0; i < neighbors.length; i++) {
            for (j = 0; j < 2; j++) {
                System.out.println(neighbors[i][j] + "\t" + neighbors[i][j + 1]);
                break;
            }
        }*/
        // Close the file once all data has been read.

       
    }//end main method

    public int findMaxIndex(int[] countForestType) {
        int largest = countForestType[0], index = 0;
        for (int i = 1; i < countForestType.length; i++) {
            if (countForestType[i] >= largest) {
                largest = countForestType[i];
                index = i;
            }
        }
        //System.out.println("index = " + index);
        return index;
    }

    public double euclideanDistance(int[] testData, int[] trainData, int length) {
        int i;
        double distance = 0;
        for (i = 1; i < length; i++) {
            distance += Math.pow((testData[i] - trainData[i]), 2);
        }
        distance = Math.sqrt(distance);
        return distance;
    }

    public double[][] getNeighbors(int[][] trainData, int[] testData, int k) {
        double distance[][] = new double[trainData.length][2];
        double neighbor[][] = new double[k][2];
        int x, c = 0, length = 11;
        double dist;
        Test t1 = new Test();
        for (x = 0; x < trainData.length; x++) {
            dist = t1.euclideanDistance(testData, trainData[x], length);
            for (c = 0; c < 2; c++) {
                if (c == 0) {
                    distance[x][c] = trainData[x][0];
                } else {
                    distance[x][c] = dist;
                }
            }
        }
        java.util.Arrays.sort(distance, new java.util.Comparator<double[]>() {
            @Override
            public int compare(double[] a, double[] b) {
                return Double.compare(a[1], b[1]);
            }
        });
        for (x = 0; x < k; x++) {

            neighbor[x] = distance[x];
        }
        return neighbor;
    }

    public int[][] putDetails(int[][] trainData, int[][] detail, int label) {
        int r = 0, i = 0, c = trainData[r].length - 1;
        if (label == 1) {
            for (r = 0; r < trainData.length; r++) {
                if (trainData[r][c] == 1) {
                    detail[i] = trainData[r];
                    i++;
                }
            }

        } else if (label == 2) {
            for (r = 0; r < trainData.length; r++) {
                if (trainData[r][c] == 2) {
                    detail[i] = trainData[r];
                    i++;
                }
            }

        } else if (label == 3) {
            for (r = 0; r < trainData.length; r++) {
                if (trainData[r][c] == 3) {
                    detail[i] = trainData[r];
                    i++;
                }
            }

        } else if (label == 4) {
            for (r = 0; r < trainData.length; r++) {
                if (trainData[r][c] == 4) {
                    detail[i] = trainData[r];
                    i++;
                }
            }

        } else if (label == 5) {
            for (r = 0; r < trainData.length; r++) {
                if (trainData[r][c] == 5) {
                    detail[i] = trainData[r];
                    i++;
                }
            }

        } else if (label == 6) {
            for (r = 0; r < trainData.length; r++) {
                if (trainData[r][c] == 6) {
                    detail[i] = trainData[r];
                    i++;
                }
            }

        } else {
            for (r = 0; r < trainData.length; r++) {
                if (trainData[r][c] == 7) {
                    detail[i] = trainData[r];
                    i++;
                }
            }

        }
        /*for (i = 0; i < detail.length; i++) {
         for (c = 0; c < detail[0].length; c++) {
         System.out.print(detail[i][c] + "\t");
         }
         System.out.println("");
         }
         System.out.println(i);
         */
        return detail;
    }

    public void insertIntoArray(int[][] myDataArray1, int[] dataArray1, int rowNumber) {
        int r, c = 0;
        int val;
        int rows = rowNumber + 1;
        for (r = rowNumber; r < rows; r++) {
            for (c = 0; c < dataArray1.length; c++) {
                val = dataArray1[c];
                myDataArray1[r][c] = val;
            }
        }
    }

    public int[] countInstances(int[][] trainSet, int[] labelCount) {
        int r = 0, c = 0;
        c = trainSet[c].length - 1;
        for (r = 0; r < trainSet.length; r++) {
            if (trainSet[r][c] == 1) {
                labelCount[0]++;

            } else if (trainSet[r][c] == 2) {
                labelCount[1]++;

            } else if (trainSet[r][c] == 3) {
                labelCount[2]++;

            } else if (trainSet[r][c] == 4) {
                labelCount[3]++;

            } else if (trainSet[r][c] == 5) {
                labelCount[4]++;

            } else if (trainSet[r][c] == 6) {
                labelCount[5]++;

            } else {
                labelCount[6]++;
            }

        }
        return labelCount;
    }

    /*public double calcEntropyOfCover(int[] cntCovType) {
        double sum = 0, temp, tempX;
        double total = 0;
        double entropyVal = 0;
        for (int i = 0; i < cntCovType.length; i++) {
            sum += cntCovType[i];
        }
        for (int i = 0; i < cntCovType.length; i++) {
            temp = cntCovType[i] / sum;
            tempX = (temp * (Math.log(temp) / Math.log(2)) * (-1));
            entropyVal += tempX;
        }
        return entropyVal;
    }*/

}//end class


/*for (i = 0; i < seventyTrain; i++) {
 for (c = 0; c < COLS; c++) {
 System.out.print(trainData[i][c] + "\t");
 }
 System.out.println("");
 }

 for (j = 0; j < testData.length; j++) {
 for (c = 0; c < COLS; c++) {
 System.out.print(testData[j][c] + "\t");
 }
 System.out.println("");
 }*/

/*
        
 ################### PRINTING THE 2D ARRAY ####################################
 ## for(i = 0; i < r; i++){                                                 ###
 ##    for(c = 0; c < COLS; c++ ){                                          ###
 ##        System.out.print(myDataArray[i][c] + "\t");                      ###
 ##    }                                                                    ###
 ## System.out.println("");                                                 ###
 ##############################################################################
        
 }*/
/*  for (i = 0; i < details7.length; i++) {
 for (c = 0; c < details7[0].length; c++) {
 System.out.print(details7[i][c] + "\t");
 }
 System.out.println("");
 }*/

 /*countCoverType = t.countInstances(trainData, countCoverType);
        entropy = t.calcEntropyOfCover(countCoverType);
        System.out.println("the entropy val is: " + entropy);

        for (i = 0; i < countCoverType.length; i++) {
            System.out.print(countCoverType[i] + "\t");
        }
        System.out.println("");
        int[][] details1 = new int[countCoverType[0]][labels.length];
        int[][] details2 = new int[countCoverType[1]][labels.length];
        int[][] details3 = new int[countCoverType[2]][labels.length];
        int[][] details4 = new int[countCoverType[3]][labels.length];
        int[][] details5 = new int[countCoverType[4]][labels.length];
        int[][] details6 = new int[countCoverType[5]][labels.length];
        int[][] details7 = new int[countCoverType[6]][labels.length];

        i = 1;
        while (i < 8) {
            if (i == 1) {
                details1 = t.putDetails(trainData, details1, i);
            } else if (i == 2) {
                details2 = t.putDetails(trainData, details2, i);
            } else if (i == 3) {
                details3 = t.putDetails(trainData, details3, i);
            } else if (i == 4) {
                details4 = t.putDetails(trainData, details4, i);
            } else if (i == 5) {
                details5 = t.putDetails(trainData, details5, i);
            } else if (i == 6) {
                details6 = t.putDetails(trainData, details6, i);
            } else {
                details7 = t.putDetails(trainData, details7, i);
            }
            i++;
        }
        i = 0;*/

/* ###############################################*/
/* ############## MOST INPORTANT #################*/
/* ###############################################*/

 /*
         for (i = 0; i < testData.length; i++) {

         countForestType = tempType;
         for (j = 0; j < countForestType.length; j++) {
         countForestType[j] = 0;
         }
         //System.out.println("the type is inshALLAH: " + trainData[i][COLS - 1]);
         neighbors = t.getNeighbors(trainData, testData[i], k);
         int determine, index = 0, covType;
         for (j = 0; j < neighbors.length; j++) {
         determine = (int) neighbors[j][0];

         index = determine - 1;
         covType = trainData[index][COLS - 1];
         if (covType == 1) {
         countForestType[0]++;

         } else if (covType == 2) {
         countForestType[1]++;

         } else if (covType == 3) {
         countForestType[2]++;

         } else if (covType == 4) {
         countForestType[3]++;

         } else if (covType == 5) {
         countForestType[4]++;

         } else if (covType == 6) {
         countForestType[5]++;

         } else {
         countForestType[6]++;
         }
         }
         int maxVal = t.findMaxIndex(countForestType);
         int cov = maxVal + 1;
         System.out.println("The forest Type is: " + cov);
            
         for(c = 0; c < countForestType.length; c++){
         System.out.printf("%d ", countForestType[c]);
         }
         System.out.println("");
         System.out.println("");
             
         }*/

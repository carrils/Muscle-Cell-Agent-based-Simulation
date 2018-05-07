//Sam Carrillo
//5.1.18
//Youth sim
//CSC 318

public class Main {


    public static void main(String[] args) {
        muscleCell[][] tissueGrid = new muscleCell[10][10];//tissueGrid array holds cell objects
        int tabint = 1;//this is the heatlhTable iterator
        int[][] healthTable = new int[10][10];//this is the healthTable holding the healthStatus' for each cell 0:HM, 1:DMD, 2:MW
        int[] hsCount = new int[4];//holds the number of each type of cell in the grid
        int i, j, maxtab = 10;//maxtab is the max number of times we will iterate the table, 10 for 10 years


        for (i = 0; i <= 9; i++) {
            for (j = 0; j <= 9; j++) {
                //create the tissueGrid and assign each muscle cell a healthStatus
                tissueGrid[i][j] = new muscleCell(i, j, 9, 9);
                healthTable[i][j] = tissueGrid[i][j].getMyHealthStatus();
            }
        }

        //now print the health table
        printTable(healthTable, tabint, 9, 9);
        System.out.println("*-------------------------------------*");

        //now change the health status in the tissueGrid
        //base case
        while (tabint <= maxtab) {
            //lastchange for all cells
            for (i = 0; i <= 9; i++) {
                for (j = 0; j <= 9; j++) {
                    tissueGrid[i][j].updateLastChange();
                    //System.out.println(tissueGrid[i][j].getLastChange());
                }
            }

            for (i = 0; i <= 9; i++) {
                for (j = 0; j <= 9; j++) {
                    //update the healthstatus for all cells
                    tissueGrid[i][j].changeHealthStatus(tissueGrid, healthTable, i, j);
                    if ((i == 0) && (j == 0)) {
                        System.out.println("\nThis is myi: " + tissueGrid[i][j].getMyi() + ", myj: " + tissueGrid[i][j].getMyj());
                        System.out.println("For [0][0], healthStatus is: " + tissueGrid[i][j].getMyHealthStatus() + ", lastchange: " + tissueGrid[i][j].getLastChange() + ", direction (if dmd): " + tissueGrid[i][j].getMyDirection() + "\n");
                    }
                }
            }

            for (i = 0; i <= 9; i++) {
                for (j = 0; j <= 9; j++) {
                    //now update healthTable and print it
                    healthTable[i][j] = tissueGrid[i][j].getMyHealthStatus();
                    if (i == 9 && j == 9) {
                        System.out.println("THIS IS THE GRID AFTER 10 YEARS FROM 50 - 60:");
                        printTable(healthTable, tabint, 9, 9);
                        printHSCount(hsCount);
                        double variance = ((hsCount[0] * hsCount[0]) / 100.0) - ((hsCount[0] / 100.0) * (hsCount[0] / 100.0));
                        double mean = (hsCount[0] / 100.0);
                        double varianceDMD = ((hsCount[1] * hsCount[1]) / 100.0) - ((hsCount[1] / 100.0) * (hsCount[1] / 100.0));
                        double meanDMD = (hsCount[1] / 100.0);
                        System.out.println("Variance for HM cells: " + variance + ", mean for HM cells: " + mean);
                        System.out.println("Variance for DMD cells: " + varianceDMD + ", mean for DMD cells: " + meanDMD);
                    }
                }
                countTable(healthTable, hsCount);
                tabint++;
            }

        }//end of while(tabint <= maxtab), table iteration

        //reset tabint for youth run
        tabint = 1;

        //this is the youth run
        while (tabint <= maxtab) {
            //lastchange for all cells
            for (i = 0; i <= 9; i++) {
                for (j = 0; j <= 9; j++) {
                    tissueGrid[i][j].updateLastChange();
                }
            }

            //inject the youth cells
            for (i = 0; i <= 9; i++) {
                for (j = 0; j <= 9; j++) {
                    if (tabint == 1) {
                        //5 cells for first year
                        tissueGrid[i][j].injectYouth(tissueGrid, 5);
                    } else {
                        //3 cells for other years
                        tissueGrid[i][j].injectYouth(tissueGrid, 3);
                    }

                }
            }
            for (i = 0; i <= 9; i++) {
                for (j = 0; j <= 9; j++) {
                    //update the healthstatus for all cells
                    tissueGrid[i][j].changeHealthStatus(tissueGrid, healthTable, i, j);
                }
            }

            for (i = 0; i <= 9; i++) {
                for (j = 0; j <= 9; j++) {
                    //now update healthTable and print it
                    healthTable[i][j] = tissueGrid[i][j].getMyHealthStatus();
                    if (i == 9 && j == 9) {
                        System.out.println("THIS IS THE GRID AFTER 10 YEARS FROM 50 - 60 WITH YOUTH:");
                        printTable(healthTable, tabint, 9, 9);
                        printHSCount(hsCount);
                        double variance = ((hsCount[0] * hsCount[0]) / 100.0) - ((hsCount[0] / 100.0) * (hsCount[0] / 100.0));
                        double mean = (hsCount[0] / 100.0);
                        double varianceDMD = ((hsCount[1] * hsCount[1]) / 100.0) - ((hsCount[1] / 100.0) * (hsCount[1] / 100.0));
                        double meanDMD = (hsCount[1] / 100.0);
                        System.out.println("Variance for HM cells: " + variance + ", mean for HM cells: " + mean);
                        System.out.println("Variance for DMD cells: " + varianceDMD + ", mean for DMD cells: " + meanDMD);
                    }
                }
                countTable(healthTable, hsCount);
                tabint++;
            }

        }//end of while(tabint <= maxtab), table iteration

    }//end of main

    //*** Methods ***
    public static void printTable(int[][] healthTable, int tprint, int imax, int jmax) {
        //this function prints the health table.
        System.out.println("This is the " + tprint + " iteration of the health table.");
        //print the table header
        System.out.println("H1  H2  H3  H4  H5  H6  H7  H8  H9  H10");
        System.out.println("*-------------------------------------*");
        for (int i = 0; i <= imax; i++) {
            for (int j = 0; j <= jmax; j++) {
                Hname(healthTable[i][j]);
            }
            //now skip a line
            System.out.println("");
        }//next row of cells
    }

    public static void Hname(int hnum) {
        //this is the name of the cells
        if (hnum == 0) {
            System.out.print("HM  ");
        } else if (hnum == 1) {
            System.out.print("DMD ");
        } else {
            System.out.print("MW  ");
        }
    }

    public static void printHSCount(int[] hsCount) {
        //this function prints the cell type count
        System.out.println("*-------------------------------------*");
        System.out.println("    HM    DMD    MW");
        System.out.println("    " + hsCount[0] + "    " + hsCount[1] + "      " + hsCount[2] + "\n");
    }

    public static void countTable(int[][] healthTable, int[] hsCount) {
        //this function updates hsCount by counting the cells in the table
        //first clear the array
        for (int i = 0; i < 3; i++) {
            hsCount[i] = 0;
        }
        //now enter in the new count
        for (int irow = 0; irow <= 9; irow++) {
            for (int jcol = 0; jcol <= 9; jcol++) {
                hsCount[healthTable[irow][jcol]]++;
            }
        }
    }

}

class muscleCell {

    //this is the muscle cell class
    protected int myi;//this is the i location of me in the tissue grid
    protected int myj;//this is the j location of me in the tissue grid
    protected int myHealthStatus;//0:HM, 1:DMD, 2:MW, 3: YTH
    protected int lastChange;//this is the iteration since my last change
    protected int myDirection;//if cell is a dmd cell, this is it's tendril direction. 0:UP, 1:DOWN, 2:LEFT, 3:RIGHT
    protected int nrows;//rows of the tissue grid go from 0 to nrows
    protected int ncols;//cols of the tissue grid go from 0 to ncols

    public muscleCell(int i, int j, int n, int m) {
        //constructor for muscleCell, sets location in tissue grid and original health status
        myi = i;
        myj = j;
        ncols = n - 1;//columns in the tissue grid
        nrows = m - 1;//rows in the tissue grid
        myHealthStatus = newHealthStatus();//returns a 0 or 1, for 0:HM, 1:DMD, 2:MW. MW can only be assigned by DMD
        lastChange = 0;//Maybe unnecessary. Find out

        //randomly determine DMD cell tendril direction
        if (myHealthStatus == 2) {
            int x;
            x = (int) (Math.random() * 100);

            if (x <= 15) {
                myDirection = 0;//up
            } else if (x <= 30) {
                myDirection = 1;//down
            } else if (x <= 65) {
                myDirection = 2;//left
            } else {
                myDirection = 3;//right
            }
        }
    }//end of muscleCell constructor

    //*****Getters*****
    public int getMyi() {
        return myi;
    }

    public int getMyj() {
        return myj;
    }

    public int getMyHealthStatus() {
        return myHealthStatus;
    }

    public int getLastChange() {
        return lastChange;
    }

    public int getMyDirection() {
        return myDirection;
    }

    public int getNrows() {
        return nrows;
    }

    public int getNcols() {
        return ncols;
    }

    public void setMyHealthStatus(int myHealthStatus) {
        this.myHealthStatus = myHealthStatus;
    }

    public int newHealthStatus() {
        //this is the process generator for the health status of any given cell.
        //Cells have a 5% chance of being a DMD cell; 0:HM, 1:DMD

        int x;
        int healthStatus;
        x = (int) (Math.random() * 100);

        if (x <= 95) {
            healthStatus = 0; //healthy
        } else {
            healthStatus = 1;//DMD
        }
        return healthStatus;
    }

    public void injectYouth(muscleCell[][] tissueGrid, int injections) {
        for (int i = 0; i < injections; i++) {
            int iLocation = (int) (Math.random() * 10);
            int jLocation = (int) (Math.random() * 10);
            tissueGrid[iLocation][jLocation].setMyHealthStatus(3);
        }
    }

    public int updateLastChange() {
        //this function updates the time since last health change for a cell
        lastChange++;
        return lastChange;
    }


    public void changeHealthStatus(muscleCell[][] tissueGrid, int[][] healthTable, int myi, int myj) {
        /*
            This is the primary behavior of the muscle cell. This function will be accessed
            for each cell once per cycle. It can change health status base on the following rules:
            1. The chance can only come if the cell can change in this cycle i.e. lastChange >= 1 (for once per year)
            2. Given that the cell can change it will change if:
                a. It is adjacent to a DMD cell tendriling in its direction. (HM -> MW)
                b. It is currently a MW cell and it has been for 1 year. (MW -> DMD)
                c. It has been hit with the YOUTH cell cure pattern (look into this after establishing base case)
         */


        int i, j, k;

        if (lastChange > 0) {
            //check these:
            //a. It is adjacent to a DMD cell tendriling in its direction. (HM -> MW)
            //b. It is currently a MW cell and it has been for 1 year. (MW -> DMD)
            //c. It has been hit with the YOUTH cell cure pattern

            //reset lastChange first
            lastChange = 0;

            //check first the corners of the tissue grid then the cases above
            if (myi == 0 && myj == 0) {
                //upper left corner of tissueGrid, look right and down for DMD
                if (healthTable[0][1] == 1 || healthTable[1][0] == 1) {
                    //if they are tendriling towards me change health status to MW:2
                    if (tissueGrid[0][1].getMyDirection() == 2 || tissueGrid[1][0].getMyDirection() == 0) {
                        myHealthStatus = 2;
                        lastChange = 0;
                    }
                }
                if (myHealthStatus == 2) {
                    //has been MW for 1 year, change to a DMD
                    myHealthStatus = 1;
                    lastChange = 0;
                }
                if (healthTable[0][2] == 3 || healthTable[2][0] == 3) {
                    //youth star in radius
                    int x = (int) (Math.random() * 100);
                    if (x <= 81) {
                        //cured
                        myHealthStatus = 0;
                        lastChange = 0;
                    }
                }
            } else if (myi == 0 && myj == ncols - 1) {
                //upper right corner, look left and down for DMD
                if (healthTable[0][ncols - 2] == 1 || healthTable[1][ncols - 1] == 1) {
                    //if they are tendriling towards me change health status to MW:2
                    if (tissueGrid[0][ncols - 2].getMyDirection() == 3 || tissueGrid[1][ncols - 1].getMyDirection() == 0) {
                        myHealthStatus = 2;
                        lastChange = 0;
                    }
                }
                if (myHealthStatus == 2) {
                    //has been MW for 1 year, change to a DMD
                    myHealthStatus = 1;
                    lastChange = 0;
                }
                if (healthTable[0][ncols - 3] == 3 || healthTable[2][ncols - 1] == 3) {
                    //youth star in radius
                    int x = (int) (Math.random() * 100);
                    if (x <= 81) {
                        //cured
                        myHealthStatus = 0;
                        lastChange = 0;
                    }
                }

            } else if (myi == nrows - 1 && myj == 0) {
                //we are in the lower left corner, look up and right for DMD
                if (healthTable[nrows - 2][0] == 1 || healthTable[nrows - 1][1] == 1) {
                    //if they are tendriling towards me change health status to MW:2
                    if (tissueGrid[nrows - 2][0].getMyDirection() == 1 || healthTable[nrows - 1][1] == 2) {
                        myHealthStatus = 2;
                        lastChange = 0;
                    }
                }
                if (myHealthStatus == 2) {
                    //has been MW for 1 year, change to a DMD
                    myHealthStatus = 1;
                    lastChange = 0;
                }
                if (healthTable[nrows - 2][0] == 3 || healthTable[nrows - 1][2] == 3) {
                    //youth star in radius
                    int x = (int) (Math.random() * 100);
                    if (x <= 81) {
                        //cured
                        myHealthStatus = 0;
                        lastChange = 0;
                    }
                }

            } else if (myi == nrows - 1 && myj == ncols - 1) {
                //we are in lower right corner, look up and left for DMD
                if (healthTable[nrows - 2][ncols - 1] == 1 || healthTable[nrows - 1][ncols - 2] == 1) {
                    //if they are tendriling towards me change health status to MW:2
                    if (tissueGrid[nrows - 2][ncols - 1].getMyDirection() == 1 || tissueGrid[nrows - 1][ncols - 2].getMyDirection() == 3) {
                        myHealthStatus = 2;
                        lastChange = 0;
                    }
                }
                if (myHealthStatus == 2) {
                    //has been MW for 1 year, change to a DMD
                    myHealthStatus = 1;
                    lastChange = 0;
                }
                if (healthTable[nrows - 3][ncols - 1] == 3 || healthTable[nrows - 1][ncols - 3] == 3) {
                    //youth star in radius
                    int x = (int) (Math.random() * 100);
                    if (x <= 81) {
                        //cured
                        myHealthStatus = 0;
                        lastChange = 0;
                    }
                }

            } else if (myi == 0 && myj > 0 && myj <= ncols - 1) {
                //now we check the upper row, check cells on either side and below for DMD
                if (healthTable[0][myj + 1] == 1 || healthTable[0][myj - 1] == 1 || healthTable[1][myj] == 1) {
                    //if they are tendriling towards me change health status to MW:2
                    if (tissueGrid[0][myj + 1].getMyDirection() == 2 || tissueGrid[0][myj - 1].getMyDirection() == 3 || tissueGrid[1][myj].getMyDirection() == 0) {
                        myHealthStatus = 2;
                        lastChange = 0;
                    }
                }
                if (myHealthStatus == 2) {
                    //has been MW for 1 year, change to a DMD
                    myHealthStatus = 1;
                    lastChange = 0;
                }
                if (healthTable[0][myj + 1] == 3 || healthTable[0][myj - 1] == 3 || healthTable[1][myj] == 3) {
                    //youth star in radius
                    int x = (int) (Math.random() * 100);
                    if (x <= 81) {
                        //cured
                        myHealthStatus = 0;
                        lastChange = 0;
                    }
                }

            } else if (myi == nrows && myj > 0 && myj < ncols) {
                //now we check the bottom row, check cells on either side and above for DMD
                if (healthTable[nrows][myj - 1] == 1 || healthTable[nrows][myj + 1] == 1 || healthTable[nrows - 1][myj] == 1) {
                    //if they are tendriling towards me change health status to MW:2
                    if (tissueGrid[nrows][myj - 1].getMyDirection() == 3 || tissueGrid[nrows][myj + 1].getMyDirection() == 2 || tissueGrid[nrows - 1][myj].getMyDirection() == 1) {
                        myHealthStatus = 2;
                        lastChange = 0;
                    }
                }
                if (myHealthStatus == 2) {
                    //has been MW for 1 year, change to a DMD
                    myHealthStatus = 1;
                    lastChange = 0;
                }
                if (healthTable[nrows][myj - 1] == 3 || healthTable[nrows][myj + 1] == 3 || healthTable[nrows - 1][myj] == 3) {
                    //youth star in radius
                    int x = (int) (Math.random() * 100);
                    if (x <= 81) {
                        //cured
                        myHealthStatus = 0;
                        lastChange = 0;
                    }
                }

            } else if (myj == 0 && myi > 0 && myi < nrows - 1) {
                //now we check the left column, check cells below above and right for DMD
                if (healthTable[myi - 1][0] == 1 || healthTable[myi + 1][0] == 1 || healthTable[myi][1] == 1) {
                    //if they are tendriling towards me change health status to MW:2
                    if (tissueGrid[myi - 1][0].getMyDirection() == 0 || tissueGrid[myi + 1][0].getMyDirection() == 1 || tissueGrid[myi][1].getMyDirection() == 2) {
                        myHealthStatus = 2;
                        lastChange = 0;
                    }
                }
                if (myHealthStatus == 2) {
                    //has been MW for 1 year, change to a DMD
                    myHealthStatus = 1;
                    lastChange = 0;
                }
                if (healthTable[myi - 1][0] == 3 || healthTable[myi + 1][0] == 3 || healthTable[myi][1] == 3) {
                    //youth star in radius
                    int x = (int) (Math.random() * 100);
                    if (x <= 81) {
                        //cured
                        myHealthStatus = 0;
                        lastChange = 0;
                    }
                }

            } else if (myj == ncols - 1 && myi > 0 && myi < nrows - 1) {
                //now we check the right column, check cells below above and left for DMD
                if (healthTable[myi - 1][myj] == 1 || healthTable[myi + 1][myj] == 1 || healthTable[myi][myj - 1] == 1) {
                    //if they are tendriling towards me change health status to MW:2
                    if (tissueGrid[myi - 1][myj].getMyDirection() == 0 || tissueGrid[myi + 1][myj].getMyDirection() == 1 || tissueGrid[myi][myj - 1].getMyDirection() == 3) {
                        myHealthStatus = 2;
                        lastChange = 0;
                    }
                }
                if (myHealthStatus == 2) {
                    //has been MW for 1 year, change to a DMD
                    myHealthStatus = 1;
                    lastChange = 0;
                }

                if (healthTable[myi - 1][myj] == 3 || healthTable[myi + 1][myj] == 3 || healthTable[myi][myj - 1] == 3) {
                    //youth star in radius
                    int x = (int) (Math.random() * 100);
                    if (x <= 81) {
                        //cured
                        myHealthStatus = 0;
                        lastChange = 0;
                    }
                }

            } else if (myi > 0 && myi < nrows - 1 && myj > 0 && myj < ncols - 1) {
                //now we check for cells in the middle of the array, check below above left right for dmd
                if (healthTable[myi - 1][myj] == 1 || healthTable[myi + 1][myj] == 1 || healthTable[myi][myj - 1] == 1 || healthTable[myi][myj + 1] == 1) {
                    //if they are tendriling toward me change health status to MW:2
                    if (tissueGrid[myi - 1][myj].getMyDirection() == 0 || tissueGrid[myi + 1][myj].getMyDirection() == 1 || tissueGrid[myi][myj - 1].getMyDirection() == 3 || tissueGrid[myi][myj + 1].getMyDirection() == 2) {
                        myHealthStatus = 2;
                        lastChange = 0;
                    }
                }
                if (myHealthStatus == 2) {
                    //has been MW for 1 year, change to a DMD
                    myHealthStatus = 1;
                    lastChange = 0;
                }
                if (healthTable[myi - 1][myj] == 3 || healthTable[myi + 1][myj] == 3 || healthTable[myi][myj - 1] == 3) {
                    //youth star in radius
                    int x = (int) (Math.random() * 100);
                    if (x <= 81) {
                        //cured
                        myHealthStatus = 0;
                        lastChange = 0;
                    }
                }
            }
        }//end of if (lastchange > 1)
        return;
    }
}
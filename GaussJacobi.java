//Loosineh Hartoonian
//Project2

import java.util.Scanner;
import java.io.*;

class GaussJacobi
{
    //A function for jacobi iterative method
	static void jacobiIterativeMethod(double matrix[][], double num[], double error)
	{
		int number = num.length;
		double temp[] = new double[number];
        int numOfIteration = 50;  //maximum iteration is 50
		double maxError = 0;
		for(int i = 0; i < number; i++) 
        {
			temp[i] = 0;
        }

		//Repeat until it reaches to the last iteration(50th)
		for(int k=1; k <= numOfIteration; k++)
		{
			double err = 0.0;
			for(int i = 0; i < number; i++)
			{
				double sum = 0.0;
				for(int j = 0; j < number; j++)
				{
					if(i != j)
                    {
						sum = sum + matrix[i][j] * num[j]; //calculating the sum of each row
                    }
				}
	
				double y = (matrix[i][number] - sum) / matrix[i][i];
				double relativeError = Math.abs((num[i] - y) / y); //Calculatin the relative error
				
				if(relativeError > err) 
                {
					maxError = relativeError;
                }
				
				temp[i] = y;
			}//end for
			
			//Updating the value of num after each iteration from the temp vector
			for(int i = 0; i < number; i++)
			{
				num[i] = temp[i];
			}

			//printing each solution
			System.out.print("\nIteration"+k+": [");
			for(int i = 0; i < number; i++)
			{
				System.out.print(String.format("%.4f", num[i]) + " ");
			}
			System.out.print("\b]");
			
            //Stopping error
			if(maxError < error) 
			{
				System.out.println("\nIt is converged!");
				return;
			}
		}//end for loop

		System.out.println("Error has not reached yet! It does not converge for iteration 50th!");
	}// end jacobiIterativeMethod 


	//A function for Gauss Seidel
	static void gaussSeidel(double matrix[][], double num[],double error)
	{
		int number = num.length;
        int numOfIteration = 50;  //maximum iteration is 50
		double maxError = 0;

		//Repeat until it reaches to the last iteration (50th)
		for(int k = 1; k <= numOfIteration; k++)
		{
			maxError=0.0;
			for(int i=0; i<number; i++)
			{
				double sum = 0.0;
				for(int j = 0; j < number; j++)
				{
					if(i != j)
						sum = sum + matrix[i][j] * num[j];
				}
				
				double y = (matrix[i][number] - sum) / matrix[i][i];
				
				double relativeError = Math.abs((num[i] - y) / y);
				
				if(relativeError > maxError) 
                {
					maxError = relativeError;
                }
				num[i] = y;
			}//end for

			//printing each iteration 
			System.out.print("\nIteration"+k+": [");
			for(int i = 0; i < number ;i++)
			{
				System.out.print(String.format("%.4f", num[i]) + " ");
			}
			System.out.print("\b]");

			//Stopping error
			if(maxError < error) 
			{
				System.out.println("\nIt is converged!");
				return;
			}
		}//end for loop

		System.out.println("Error has not reached yet! It does not converge for iteration 50th!");
	}// end gaussSeidel


	public static void main(String args[])
	{
        int choice;
		int num;
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter the number of linear equations: ");
		num = input.nextInt();
		
		if(num < 2 || num >10)
        {
			System.out.println("Enter n between 2 and 10");
			return;
		}
		
		double matrix[][] = new double[num][num+1];  
		
		double x[] = new double[num];

		System.out.println("1)Enter the coefficients from the command line");
		System.out.println("2)Enter the file name to get the coefficients of the matrix");
		System.out.println("Please make a selection: ");
		choice = input.nextInt();

        if(choice == 1)
		{
			System.out.println("Please enter the values of coefficients for each row including b value then press enter. ");
			for(int i = 0; i < num; i++)
			{
				for(int j = 0; j <= num; j++)
				{
					matrix[i][j]=input.nextInt();
				}
			}
        }//end if

		else if(choice == 2)
        {
			System.out.println("Please enter the file name that contains the values of coefficients. ");
			try
            {
                String line; 
				int i=0;
				input.nextLine();
				String fileName;
				fileName = input.nextLine();
				File file = new File(fileName); 
				BufferedReader reader = new BufferedReader(new FileReader(file)); 
				
				while ((line = reader.readLine()) != null) 
                {
					String []tokens = line.split(" ");
					for(int j = 0; j < num + 1; j++)
					{
						matrix[i][j] = Double.parseDouble(tokens[j]);
					}
					i++;
				}//end while loop

            }catch(Exception e){
                System.out.println("Error! The file cannot be opened.");
					return;
            }
        }//end else if

        else
        {
				System.out.println("Error!");
        }//end else

        double error;
		System.out.println("Please enter the desired stopping error: ");
	    error = input.nextDouble();
		System.out.println("Please enter the starting solutions for the iterative methods: ");
		for(int i = 0; i < num; i++)
		{
			System.out.print("\nX["+(i+1)+"] : ");
			x[i] = input.nextDouble();
		}//edn for 
		

        double a[]  = new double[num];
		for(int i = 0; i < num; i++)
		{
			a[i] = x[i];
		}//end for

		System.out.println("--------------------Jocobi Method Solution--------------------------");
		jacobiIterativeMethod(matrix, a, error);
		
		System.out.println("--------------------Gauss Seidel Method Solution----------------------");
		for(int i = 0; i < num; i++)
		{
			a[i] = x[i];
		}
		gaussSeidel(matrix, a, error);
	}// end main
}// end GaussJacobi class
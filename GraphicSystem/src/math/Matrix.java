package math;

public class Matrix {
	public static double[][] multiplyMatrix(double[][] a, double[][] b){
		int a_row_size = a[0].length;
		int b_column_size = b.length;

		if(a_row_size != b_column_size)
			System.out.println("size of row from a must be equal to the size of column from b!");
		
		int number_of_rows = a.length;
		int number_of_columns = b[0].length;
		
		double[][] c = new double[number_of_rows][number_of_columns];
		
		for(int i = 0; i < number_of_rows; i++){
			for(int j = 0; j < number_of_columns; j++){
				double[] a_line = a[i];
				double[] b_column = getColumn(b, j);
//				System.out.println();
//				System.out.println("i,j: "+i+","+j);
				c[i][j] = dotProduct(a_line, b_column);
			}
		}
		
		return c;
	}
	
	private static double[] getColumn(double[][] matrix, int column_index){
		int column_size = matrix.length;
		
		double[] column = new double[column_size];
		
		for(int i = 0; i < column_size; i++)
			column[i] = matrix[i][column_index];
		
		return column;
	}
	
	private static double dotProduct(double[] line, double[] column){
		if(line.length != column.length)
			System.out.println("size of line must be equal to the size of the column!");
//		printArray(line);
//		System.out.println();
//		printArray(column);
		
		
		int size = line.length;
		
		double dotProduct = 0;
		
		for(int i = 0; i < size; i++){
			dotProduct += line[i] * column[i];
		}
		System.out.println();
		System.out.println("dot_product = " + dotProduct);
		return dotProduct;
	}
	
	public static void printMatrix(double[][] matrix){
		for(int i = 0; i<matrix.length; i++){
			double[] line = matrix[i];
			System.out.println();
			System.out.print("[");
			for(int j = 0; j < line.length; j++){
				System.out.print(" "+ matrix[i][j] + " ");
			}
			System.out.print("]");
		}
	}
	
	public static void printArray(double[] array){
		System.out.print("[");
		for(int i = 0; i<array.length; i++){
			System.out.print(" " +array[i]+ " ");
		}
		System.out.print("]");

	}
}

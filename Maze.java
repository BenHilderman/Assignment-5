import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Class Maze.
 * @author  BenHilderman
 * @version 1.0
 * @since   2020-05-25
 */

public class Maze {
	
	// Variables
	private String outputFilename;
	static Scanner scanner = new Scanner(System.in);
	private char[][] mazeSelected;
	private int columnStart, rowStart; 
	private int rows, columns;
	public static void main(String[] args) throws IOException{
		
		// Ask user which maze they want to complete
		System.out.println("What maze do you want the bot to complete? (1 , 2 , 3)");
		int choice = scanner.nextInt();
		
		if (choice == 1) {
			
			// Copies original maze to seperate file to be completed
			File maze1Unfinished = new File("maze1Unfinished.txt");
			File Maze = new File("maze1.txt");
			Files.copy(maze1Unfinished.toPath(), Maze.toPath(), StandardCopyOption.REPLACE_EXISTING);
			
			try {
				new Maze("maze1.txt");
			    System.out.println("File has been output!");
				
			} catch (Exception e) {
				System.out.println("ERROR : "+e.getMessage());
				e.printStackTrace();

			}
		}
		
		if (choice == 2) {
			
			// Copies original maze to seperate file to be completed
			File maze2Unfinished = new File("maze2Unfinished.txt");
			File Maze = new File("maze2.txt");
			Files.copy(maze2Unfinished.toPath(), Maze.toPath(), StandardCopyOption.REPLACE_EXISTING);

			try {
				new Maze("maze2.txt");
			    System.out.println("File has been output!");
				
			} catch (Exception e) {
				System.out.println("ERROR : "+e.getMessage());
				e.printStackTrace();

			}
		}
		
		if (choice == 3) {
			
			// Copies original maze to seperate file to be completed
			File maze3Unfinished = new File("maze3Unfinished.txt");
			File Maze = new File("maze3.txt");
			Files.copy(maze3Unfinished.toPath(), Maze.toPath(), StandardCopyOption.REPLACE_EXISTING);
			
			try {
				new Maze("maze3.txt");
			    System.out.println("File has been output!");
				
			} catch (Exception e) {
				System.out.println("ERROR : "+e.getMessage());
				e.printStackTrace();

			}
		}
		
		// If user doesn't input 1, 2 or 3
		if (choice != 1 && choice != 2 && choice != 3) {
			System.out.println("Invalid input, you mush enter one of the options.");

			}
		}
	
		public boolean complete(int row, int column) {
		
		// North, South, East, West directions with recursion
		char goNorth = this.mazeSelected[row - 1][column];
		char goEast = this.mazeSelected[row][column + 1];
		char goSouth = this.mazeSelected[row + 1][column];
		char goWest = this.mazeSelected[row][column - 1];
		
		if (goNorth == 'G' || goSouth == 'G' || goEast == 'G' || goWest == 'G') {
			
			// When bot completes maze
			this.mazeSelected[row][column] = '+';
			this.printMaze();
			return true;
		}
		
		// Recursion to choose direction
		boolean completed = false;
		if (this.mazeSelected[row][column] != 'S') {
			this.mazeSelected[row][column] = '+';
		}
		
		// Bot checks north if it is open
		if (goNorth == '.' && !completed) {
			
			// x - 1 moves up
			completed = complete(row - 1, column);
		}
		if (goEast == '.' && !completed) {
			
			// y + 1 moves up
			completed = complete(row, column + 1);
		}										
		if (goSouth == '.' && !completed) {	
			
			// x + 1 moves up
			completed = complete(row + 1, column);
		}										
		if (goWest == '.' && !completed) {		
			
			// y - 1 moves up
			completed = complete(row, column - 1);
		}										
		if (!completed) {
			
			// If it is not completed
			this.mazeSelected[row][column] = '.';
		}
		
		try {
			
			// 1 second break to show bot process
			Thread.sleep(1000);
		}
		catch(InterruptedException ex)
		
		{
			Thread.currentThread().interrupt();
			
		}
		
		this.printMaze();
		return completed;
	}
	
	public Maze(String filename) throws IOException {
		
		try {
			this.outputFilename = filename;
			Scanner scan = new Scanner(new File(filename));
			StringBuilder sb = new StringBuilder();
			while (scan.hasNext()) {
				sb.append(scan.nextLine());
				this.rows++;
			}
			
			this.columns = sb.length() / this.rows;
			this.mazeSelected = new char[this.rows][this.columns];
			int a = 0;
			System.out.println();
			
			for (int i = 0; i < this.rows; i++) {
				for (int j = 0; j < this.columns; j++) {
					this.mazeSelected[i][j] = sb.charAt(a++);
				}
			}
			scan.close();
			findStart();
			complete(this.rowStart, this.columnStart);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("ERROR : " + e.getMessage());
		}
	}

	private void findStart() {
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				if (mazeSelected[i][j] == 'S') {
					this.rowStart = i;
					this.columnStart = j;
				}
			}
		}
	}
	
	public void printMaze() {
		try {
			File mazeFile = new File(this.outputFilename);

			PrintWriter writer = new PrintWriter(mazeFile);
			for (int i = 0; i < this.rows; i++) {
				for (int j = 0; j < this.columns; j++) {
					writer.print(this.mazeSelected[i][j]);

				}
				// Print .txt file
				writer.println();
				
			}
		    // Closes .txt file
			writer.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("ERROR : " + e.getMessage());
		}
	}
}
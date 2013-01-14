package fileManipulations;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import jxl.*;
import jxl.read.biff.BiffException;

public class HungarianInputReader {

	private ArrayList<String> people;
	private ArrayList<String> reviewers;
	
	public HungarianInputReader() {
		people = new ArrayList<String>();
		reviewers = new ArrayList<String>();
	}

	public ArrayList<String> getPeople() {
		return people;
	}

	public ArrayList<String> getReviewers() {
		return reviewers;
	}
	
	public int getMaxReviews() {
		return (int) Math.ceil(people.size()/reviewers.size());
	}

	public double[][] read(String inputFile) throws IOException  {
		File inputWorkbook = new File(inputFile);
		Workbook w;
		double[][] array = null;
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet sheet = w.getSheet("to algorithm");
			// Loop over first 10 column and lines
			
			Cell[] peopleColumn = sheet.getColumn(0);
			for(int i=1; i<peopleColumn.length; i++) {
				people.add(peopleColumn[i].getContents());
			}
			Cell[] reviewersRow = sheet.getRow(0);
			for(int i=1; i<reviewersRow.length; i++) {
				reviewers.add(reviewersRow[i].getContents());
			}
			array = new double[peopleColumn.length][reviewersRow.length];
			for (int i = 1; i < sheet.getRows(); i++) {
				for (int j = 1; j < sheet.getColumns(); j++) {
				
					Cell cell = sheet.getCell(i,j);
					array[i-1][j-1] = Double.parseDouble(cell.getContents());

				}
			}
		} catch (BiffException e) {
			e.printStackTrace();
		}
		return array;
	}

	/**
	 * @param args
	 */
	public double[][] readInputFromFile(String filename) {
		double[][] array = null;
		try {
			File file = new File(filename);
			if (!file.getName().endsWith(".csv")) {
				throw new FileTypeExtensionException();
			}
			BufferedReader reader = new BufferedReader(new FileReader(file));
			ArrayList<double[]> arrayList = new ArrayList<double[]>();
			String line = reader.readLine();
			String[] contents = line.split(",");
			for(int i=1; i<contents.length; i++) {
				reviewers.add(contents[i]);
			}
			line = reader.readLine();
			while (line != null) {
				if (!line.equals("")) {
					contents = line.split(",");
					double[] numbers = parseContents(contents);
					arrayList.add(numbers);
				}
				line = reader.readLine();
			}
			//if (isValidMatrix(arrayList)) {
				array = new double[arrayList.size()][arrayList.get(0).length];
				for (int i = 0; i < arrayList.size(); i++) {
					array[i] = arrayList.get(i);
				}
			//}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileTypeExtensionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}/* catch (InvalidMatrixDimensionsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return array;

	}

	private static boolean isValidMatrix(ArrayList<double[]> arrayList)
			throws InvalidMatrixDimensionsException {
		int length = arrayList.get(0).length;
		for (int i = 1; i < arrayList.size(); i++) {
			if (length != arrayList.get(i).length) {
				throw new InvalidMatrixDimensionsException();
			}
		}
		return true;
	}

	private double[] parseContents(String[] contents) {
		double[] numbers = new double[contents.length-1];
		people.add(contents[0]);
		for (int i = 1; i < contents.length; i++) {
			numbers[i-1] = Double.parseDouble(contents[i]);
		}
		return numbers;
	}

}

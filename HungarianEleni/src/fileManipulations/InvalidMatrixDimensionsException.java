package fileManipulations;

public class InvalidMatrixDimensionsException extends Exception {
	
	@Override
	public String getMessage() {
		return "Matrix rows have different sizes.";
	}

}

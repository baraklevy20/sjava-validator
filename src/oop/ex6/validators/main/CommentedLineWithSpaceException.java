package oop.ex6.validators.main;

/**
 * The exception that's thrown when having an commented line that starts with a space
 */
public class CommentedLineWithSpaceException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 */
	public CommentedLineWithSpaceException(FileValidator validator) {
		super(validator, "Commented line starts with a space");
	}
}

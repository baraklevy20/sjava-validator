package oop.ex6.validators.main;

/**
 * The exception that's thrown when having a line with } that contains text
 */
public class ClosingCurlyBracketWithTextException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 */
	public ClosingCurlyBracketWithTextException(FileValidator validator) {
		super(validator, "A line with } contains text");
	}
}
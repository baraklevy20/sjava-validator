package oop.ex6.validators.main;

/**
 * The exception that's thrown when having '{' that was not closed
 */
public class ExtraOpeningCurlyBracketException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 */
	public ExtraOpeningCurlyBracketException(FileValidator validator) {
		super(validator, "{ was not closed");
	}
}

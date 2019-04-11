package oop.ex6.validators.main;

/**
 * The exception that's thrown when having an extra '}'
 */
public class ExtraClosingCurlyBracketException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 */
	public ExtraClosingCurlyBracketException(FileValidator validator) {
		super(validator, "Extra }");
	}
}

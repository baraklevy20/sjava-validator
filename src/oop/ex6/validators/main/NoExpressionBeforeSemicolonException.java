package oop.ex6.validators.main;

/**
 * The exception that's thrown when having no expression before semicolon
 */
public class NoExpressionBeforeSemicolonException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 */
	public NoExpressionBeforeSemicolonException(FileValidator validator) {
		super(validator, "No expression before semicolon");
	}
}

package oop.ex6.validators.main;

/**
 * The exception that's thrown when having no expression before opening curly bracket
 */
public class NoExpressionBeforeCurlyBracketException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 */
	public NoExpressionBeforeCurlyBracketException(FileValidator validator) {
		super(validator, "No expression before opening curly bracket");
	}
}

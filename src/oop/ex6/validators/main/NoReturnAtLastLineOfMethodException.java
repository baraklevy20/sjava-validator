package oop.ex6.validators.main;

/**
 * The exception that's thrown when having no return in the last line of the method
 */
public class NoReturnAtLastLineOfMethodException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 */
	public NoReturnAtLastLineOfMethodException(FileValidator validator) {
		super(validator, "No return in the last line of the method");
	}
}

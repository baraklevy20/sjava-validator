package oop.ex6.validators.main;

/**
 * The exception that's thrown when the trying to define a method inside of another method
 */
public class MethodInsideMethodException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 */
	public MethodInsideMethodException(FileValidator validator) {
		super(validator, "Trying to define a method inside of another method");
	}
}

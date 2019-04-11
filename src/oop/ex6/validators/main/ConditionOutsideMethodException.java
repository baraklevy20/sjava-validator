package oop.ex6.validators.main;

/**
 * The exception that's thrown when having a condition outside of a method
 */
public class ConditionOutsideMethodException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 */
	public ConditionOutsideMethodException(FileValidator validator) {
		super(validator, "A condition outside of a method");
	}
}

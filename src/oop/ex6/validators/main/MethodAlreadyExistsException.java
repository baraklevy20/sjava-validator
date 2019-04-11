package oop.ex6.validators.main;

/**
 * The exception that's thrown when the declaring a method that already exists
 */
public class MethodAlreadyExistsException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 * @param name - the method name
	 */
	public MethodAlreadyExistsException(FileValidator validator, String name) {
		super(validator, "A method with the name " + name + " already exists");
	}
}

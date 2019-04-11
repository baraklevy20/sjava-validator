package oop.ex6.validators.main;

/**
 * The exception that's thrown when declaring a variable that already exists
 */
public class VariableAlreadyExistsException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 * @param name - the variable name
	 */
	public VariableAlreadyExistsException(FileValidator validator, String name) {
		super(validator, "Variable already exists: " + name);
	}
}

package oop.ex6.validators.parameter;

import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;

/**
 * The exception that's thrown when passing an undeclared variable as a parameter
 */
public class UndeclaredParameterException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 * @param name - the undeclared variable name
	 */
	public UndeclaredParameterException(FileValidator validator, String name) {
		super(validator, "Passed an undeclared variable as a parameter: " + name);
	}
}

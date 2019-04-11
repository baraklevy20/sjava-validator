package oop.ex6.validators.parameter;

import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;

/**
 * The exception that's thrown when passing an unassigned variable as a parameter
 */
public class UnassignedParameterException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 * @param name - the unassigned variable name
	 */
	public UnassignedParameterException(FileValidator validator, String name) {
		super(validator, "Passed an unassigned variable as a parameter: " + name);
	}
}

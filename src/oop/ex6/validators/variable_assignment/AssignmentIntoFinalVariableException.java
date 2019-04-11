package oop.ex6.validators.variable_assignment;

import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;

/**
 * The exception that's thrown when trying to assign into a final variable
 */
public class AssignmentIntoFinalVariableException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 * @param name - the final variable name
	 */
	public AssignmentIntoFinalVariableException(FileValidator validator, String name) {
		super(validator, "Trying to assign into a final variable: " + name);
	}
}

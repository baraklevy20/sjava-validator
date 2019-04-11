package oop.ex6.validators.variable_assignment;

import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;

/**
 * The exception that's thrown when trying to assign into a final variable
 */
public class AssignmentOfDifferentTypesException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 */
	public AssignmentOfDifferentTypesException(FileValidator validator) {
		super(validator, "Assignment of different types");
	}
}

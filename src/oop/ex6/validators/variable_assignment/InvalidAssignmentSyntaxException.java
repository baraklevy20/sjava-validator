package oop.ex6.validators.variable_assignment;

import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;

/**
 * The exception that's thrown when having an invalid assignment syntax
 */
public class InvalidAssignmentSyntaxException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 */
	public InvalidAssignmentSyntaxException(FileValidator validator) {
		super(validator, "Invalid assignment syntax");
	}
}

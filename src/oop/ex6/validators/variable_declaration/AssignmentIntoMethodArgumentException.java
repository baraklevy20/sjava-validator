package oop.ex6.validators.variable_declaration;

import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;

/**
 * The exception that's thrown when trying to assign values into method arguments
 */
public class AssignmentIntoMethodArgumentException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 */
	public AssignmentIntoMethodArgumentException(FileValidator validator) {
		super(validator, "Can not assign into method arguments");
	}
}

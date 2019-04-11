package oop.ex6.validators.variable_assignment;

import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;

/**
 * The exception that's thrown when trying to assign into an undeclared variable
 */
public class AssignmentOfUndeclaredVariableException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 * @param variableName - the variable name
	 */
	public AssignmentOfUndeclaredVariableException(FileValidator validator, String variableName) {
		super(validator, "Assignment of undeclared variable: " + variableName);
	}
}

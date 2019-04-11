package oop.ex6.validators.variable_assignment;

import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;

/**
 * The exception that's thrown when trying to assign into an unassigned variable
 */
public class AssignmentOfUnassignedVariableException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 * @param variableName - the variable name
	 */
	public AssignmentOfUnassignedVariableException(FileValidator validator, String variableName) {
		super(validator, "Assignment of unassigned variable: " + variableName);
	}
}

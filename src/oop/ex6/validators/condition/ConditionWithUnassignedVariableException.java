package oop.ex6.validators.condition;

import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;

/**
 * The exception that's thrown when trying to condition with an unassigned variable
 */
public class ConditionWithUnassignedVariableException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 * @param name - the unassigned variable name
	 */
	public ConditionWithUnassignedVariableException(FileValidator validator, String name) {
		super(validator, "Trying to condition with an unassigned variable: " + name);
	}
}

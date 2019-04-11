package oop.ex6.validators.variable_assignment;

import oop.ex6.components.VariableType;
import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;

/**
 * The exception that's thrown when trying to assign an invalid value
 */
public class InvalidValueException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 * @param type - the variable type
	 * @param value - the variable value
	 */
	public InvalidValueException(FileValidator validator, VariableType type, String value) {
		super(validator, "Invalid value for type " + type.toString().toLowerCase() + ": " + value);
	}
}

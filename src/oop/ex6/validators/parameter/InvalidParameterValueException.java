package oop.ex6.validators.parameter;

import oop.ex6.components.VariableType;
import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;

/**
 * The exception that's thrown when having an invalid parameter value
 */
public class InvalidParameterValueException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 * @param type - the type of the parameter
	 * @param value - the invalid value that was passed to the parameter
	 */
	public InvalidParameterValueException(FileValidator validator, VariableType type, String value) {
		super(validator, "Invalid parameter value for type "+type.toString().toLowerCase() + ": " + value);
	}
}

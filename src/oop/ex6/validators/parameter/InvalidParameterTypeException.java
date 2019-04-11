package oop.ex6.validators.parameter;

import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;

/**
 * The exception that's thrown when passing a parameter of a different type
 */
public class InvalidParameterTypeException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 */
	public InvalidParameterTypeException(FileValidator validator) {
		super(validator, "Passed a parameter of a different type");
	}
}

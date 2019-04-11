package oop.ex6.validators.condition;

import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;

/**
 * The exception that's thrown when having an invalid condition statement
 */
public class InvalidConditionSyntaxException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 */
	public InvalidConditionSyntaxException(FileValidator validator) {
		super(validator, "Invalid condition statement. Did you forget ()?");
	}
}

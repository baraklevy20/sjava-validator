package oop.ex6.validators.condition;

import oop.ex6.components.VariableType;
import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;

/**
 * The exception that's thrown when trying to condition with an invalid type variable
 */
public class ConditionWithInvalidTypeVariableException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 * @param type - the wrong type
	 */
	public ConditionWithInvalidTypeVariableException(FileValidator validator, VariableType type) {
		super(validator, "Trying to condition with an invalid type variable: " + type);
	}
}

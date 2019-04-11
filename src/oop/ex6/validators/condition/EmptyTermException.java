package oop.ex6.validators.condition;

import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;

/**
 * The exception that's thrown when trying to condition with an empty term
 */
public class EmptyTermException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 */
	public EmptyTermException(FileValidator validator) {
		super(validator, "Trying to condition with an empty term");
	}
}

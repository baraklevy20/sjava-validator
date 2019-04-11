package oop.ex6.validators.return_statement;

import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;

/**
 * The exception that's thrown when the return command is outside of a method
 */
public class ReturnOutsideMethodException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 */
	public ReturnOutsideMethodException(FileValidator validator) {
		super(validator, "Return command outside of a method");
	}
}

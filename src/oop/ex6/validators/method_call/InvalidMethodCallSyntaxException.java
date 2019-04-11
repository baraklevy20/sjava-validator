package oop.ex6.validators.method_call;

import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;

/**
 * The exception that's thrown when having an invalid method call syntax
 */
public class InvalidMethodCallSyntaxException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 */
	public InvalidMethodCallSyntaxException(FileValidator validator) {
		super(validator, "Invalid method call syntax");
	}
}

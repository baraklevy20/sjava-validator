package oop.ex6.validators.method_call;

import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;

/**
 * The exception that's thrown when calling an undeclared method
 */
public class UndeclaredMethodCallException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 */
	public UndeclaredMethodCallException(FileValidator validator) {
		super(validator, "Calling an undeclared method");
	}
}

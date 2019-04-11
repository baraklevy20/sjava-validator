package oop.ex6.validators.method_call;

import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;

/**
 * The exception that's thrown when having a method call outside of any scope
 */
public class MethodCallOutsideScopeException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 */
	public MethodCallOutsideScopeException(FileValidator validator) {
		super(validator, "Method call outside of any scope");
	}
}

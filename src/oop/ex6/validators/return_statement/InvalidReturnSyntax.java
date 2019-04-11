package oop.ex6.validators.return_statement;

import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;

/**
 * The exception that's thrown when the return command syntax is wrong
 */
public class InvalidReturnSyntax extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 */
	public InvalidReturnSyntax(FileValidator validator) {
		super(validator, "Return command syntax is wrong");
	}
}

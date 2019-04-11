package oop.ex6.validators.variable_declaration;

import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;

/**
 * The exception that's thrown when having an invalid declaration syntax
 */
public class InvalidDeclarationSyntaxException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 */
	public InvalidDeclarationSyntaxException(FileValidator validator) {
		super(validator, "Invalid declaration syntax");
	}
}

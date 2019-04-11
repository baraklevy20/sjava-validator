package oop.ex6.validators.method_declaration;

import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;

/**
 * The exception that's thrown when having an invalid method declaration syntax
 */
public class InvalidMethodDeclarationSyntaxException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 */
	public InvalidMethodDeclarationSyntaxException(FileValidator validator) {
		super(validator, "Invalid method declaration syntax");
	}
}

package oop.ex6.validators.variable_declaration;

import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;

/**
 * The exception that's thrown when having an invalid declaration type
 */
public class InvalidDeclarationTypeException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 */
	public InvalidDeclarationTypeException(FileValidator validator) {
		super(validator, "Invalid declaration type");
	}
}

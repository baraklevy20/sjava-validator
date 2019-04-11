package oop.ex6.validators.variable_declaration;

import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;

/**
 * The exception that's thrown when having an invalid variable name declaration
 */
public class InvalidVariableNameException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 * @param name - the variable name
	 */
	public InvalidVariableNameException(FileValidator validator, String name) {
		super(validator, "Invalid variable name declaration: " + name);
	}
}

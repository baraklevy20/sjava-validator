package oop.ex6.validators.variable_declaration;

import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;

/**
 * The exception that's thrown when having no assignment into a final variable
 */
public class FinalVariableWithoutValueException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 */
	public FinalVariableWithoutValueException(FileValidator validator) {
		super(validator, "No assignment into a final variable");
	}
}

package oop.ex6.validators;

import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;

/**
 * A base class for all validators. Contains the file validator,
 * and a method that validates a given text
 */
public abstract class BaseValidator {
	protected FileValidator validator;

	/**
	 * Creates a new validator with the given file validator
	 * @param validator the file validator
	 */
	public BaseValidator(FileValidator validator) {
		this.validator = validator;
	}

	/**
	 * Validates the text. If the text is invalid, an exception with the cause is thrown
	 * @param text the text to validate
	 * @throws ValidatorException if the text is invalid
	 */
	public abstract void validate(String text) throws ValidatorException;
}

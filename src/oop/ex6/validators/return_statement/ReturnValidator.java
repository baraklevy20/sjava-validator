package oop.ex6.validators.return_statement;

import oop.ex6.validators.BaseValidator;
import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;

/**
 * A class responsible for validating a return statement (return)
 */
public class ReturnValidator extends BaseValidator {
	/**
	 * Creates a new return validator with the given file validator
	 * @param validator the file validator
	 */
	public ReturnValidator(FileValidator validator) {
		super(validator);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(String text) throws ValidatorException {
		// We need to make sure the text is return command
		if (!text.trim().equals("return")) {
			throw new InvalidReturnSyntax(validator);
		}

		// We also need to make sure 'return' is only used inside a scope
		// Since if/whiles also must be in a scope, this means the return command will
		// be in a method as well
		if (validator.isGlobalScope()) {
			throw new ReturnOutsideMethodException(validator);
		}
	}
}

package oop.ex6.validators.condition;

import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;

/**
 * The exception that's thrown when having an unknown symbol in the condition
 */
public class UnknownSymbolTermException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 * @param term - the unknown term
	 */
	public UnknownSymbolTermException(FileValidator validator, String term) {
		super(validator, "Unknown symbol in condition: " + term);
	}
}

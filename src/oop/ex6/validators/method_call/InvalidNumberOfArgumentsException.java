package oop.ex6.validators.method_call;

import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;

/**
 * The exception that's thrown when the number of arguments passed does not match
 * the number of arguments the method receives
 */
public class InvalidNumberOfArgumentsException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 * @param expected - the expected number of arguments
	 * @param received - the received number of arguments
	 */
	public InvalidNumberOfArgumentsException(FileValidator validator, int expected, int received) {
		super(validator, "Number of arguments passed does not match" +
				" the number of arguments the method receives. Expected: " + expected +
				" Received: " + received);
	}
}

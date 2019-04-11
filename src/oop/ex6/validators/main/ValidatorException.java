package oop.ex6.validators.main;

import java.io.IOException;

/**
 * A general exception that prints the line that was problematic and an informative message
 */
public class ValidatorException extends IOException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 * @param message - the message to return
	 */
    public ValidatorException(FileValidator validator, String message) {
        super("Error in line " + validator.getLineNumber() + ": " + message);
    }
}

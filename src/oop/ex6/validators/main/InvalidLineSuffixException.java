package oop.ex6.validators.main;

/**
 * The exception that's thrown when the line ends in an invalid suffix
 */
public class InvalidLineSuffixException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 * @param suffix - the invalid suffix
	 */
	public InvalidLineSuffixException(FileValidator validator, char suffix) {
		super(validator, "Line ends in " + suffix);
	}
}

package oop.ex6.validators.main;

/**
 * The exception that's thrown when having a non-existing keyword
 */
public class NonExistingKeywordException extends ValidatorException {
	/**
	 * Creates the exception
	 * @param validator - the file validator
	 * @param keyword - the non-existing keyword
	 */
	public NonExistingKeywordException(FileValidator validator, String keyword) {
		super(validator, "Non-existing keyword: " + keyword);
	}
}

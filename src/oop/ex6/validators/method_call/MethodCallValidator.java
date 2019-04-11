package oop.ex6.validators.method_call;

import oop.ex6.components.Method;
import oop.ex6.validators.BaseValidator;
import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;
import oop.ex6.validators.parameter.ParameterValidator;
import oop.ex6.validators.variable_declaration.VariablesDeclarationValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class responsible for validating a method call (foo(a, b, c))
 */
public class MethodCallValidator extends BaseValidator {
	/** The pattern of an method call statement **/
	private static final String PATTERN = "\\s*(" +
			VariablesDeclarationValidator.VALID_VARIABLE_NAME + ")\\s*\\((.*)\\)\\s*";
	/**
	 * Creates a new condition validator with the given file validator
	 * @param validator the file validator
	 */
	public MethodCallValidator(FileValidator validator) {
		super(validator);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(String text) throws ValidatorException {
		// If we're at the global scope
		if (validator.isGlobalScope()) {
			throw new MethodCallOutsideScopeException(validator);
		}

		// Check if the pattern matches
		Matcher matcher = Pattern.compile(PATTERN).matcher(text);

		if (!matcher.matches()) {
			throw new InvalidMethodCallSyntaxException(validator);
		}

		// Get the method name and its terms text. i.e "a, 5, 3"
		String termsText = matcher.group(2);
		String methodName = matcher.group(1);

		Method method = validator.getMethod(methodName);
		if (method == null) {
			throw new UndeclaredMethodCallException(validator);
		}

		String[] terms = termsText.trim().split(",");

		// Split on an empty strings returns 1 array with an empty string.
		// This is a small tweak to make terms the size of 0 if the parameter text is empty
		if (termsText.trim().length() == 0) {
			terms = new String[0];
		}

		// If the number of arguments does not match the number of arguments given
		if (terms.length != method.getArguments().size()) {
			throw new InvalidNumberOfArgumentsException(validator, method.getArguments().size(), terms.length);
		}

		// If there are terms, we need to validate them
		if (termsText.trim().length() != 0) {
			ParameterValidator parameterValidator = new ParameterValidator(validator, method);

			for (int i = 0; i < terms.length; i++) {
				parameterValidator.setParameterIndex(i);
				parameterValidator.validate(terms[i].trim());
			}
		}
	}
}

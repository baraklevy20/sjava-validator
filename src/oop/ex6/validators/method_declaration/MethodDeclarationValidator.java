package oop.ex6.validators.method_declaration;

import oop.ex6.validators.BaseValidator;
import oop.ex6.validators.variable_declaration.VariablesDeclarationValidator;
import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;
import oop.ex6.components.Method;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class responsible for validating a method declaration (void foo())
 */
public class MethodDeclarationValidator extends BaseValidator {
	/** The pattern of a valid methods names **/
	private static final String VALID_METHOD_NAME = "[A-Za-z]+[\\w_]*";

	/** The pattern of an method declaration statement **/
	private static final String PATTERN = "void\\s+(" + VALID_METHOD_NAME + ")\\s*\\((.*)\\)\\s*";

	/** Is it the first pass? **/
	private boolean isFirstPass;

	/**
	 * Creates a new method declaration validator with the given file validator
	 * @param validator the file validator
	 * @param isFirstPass is it the first pass?
	 */
	public MethodDeclarationValidator(FileValidator validator, boolean isFirstPass) {
		super(validator);
		this.isFirstPass = isFirstPass;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(String text) throws ValidatorException {
		Matcher matcher = Pattern.compile(PATTERN).matcher(text);
		VariablesDeclarationValidator declaration = new VariablesDeclarationValidator(validator, false, true);

		if (!matcher.matches()) {
			throw new InvalidMethodDeclarationSyntaxException(validator);
		}

		Method method = new Method();
		String termsText = matcher.group(2);

		// If there are terms, we need to validate them
		if (termsText.trim().length() != 0) {
			String[] terms = matcher.group(2).split(",", -1);

			for (String term : terms) {
				declaration.setFinal(term.trim().startsWith("final"));
				declaration.validate(term);

				// If we get here, the declaration went well and we can continue
				// to the next declaration
			}

			// We now get the variables that were declared by accessing the current scope (the method's)
			method.getArguments().addAll(validator.getCurrentScope().getVariables().values());
		}

		method.setName(matcher.group(1));
		// Notify the validator a new method was added
		validator.addNewMethod(method, isFirstPass);
	}
}

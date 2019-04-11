package oop.ex6.validators.condition;

import oop.ex6.components.Variable;
import oop.ex6.components.VariableType;
import oop.ex6.validators.BaseValidator;
import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;
import oop.ex6.validators.variable_assignment.VariablesAssignmentValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class responsible for validating a condition (if and while)
 */
public class ConditionValidator extends BaseValidator {
	/** The delimiter between each term in the () of the if/while **/
	private static final String DELIMITER = "&&|\\|\\|";

	/** The pattern of an if/while statement **/
	private static final String PATTERN = "\\s*(if|while)\\s*\\((.*)\\)\\s*";

	/**
	 * Creates a new condition validator with the given file validator
	 * @param validator the file validator
	 */
	public ConditionValidator(FileValidator validator) {
		super(validator);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(String text) throws ValidatorException {
		Matcher withoutParenthesis = Pattern.compile(PATTERN).matcher(text);
		if (!withoutParenthesis.matches()) {
			throw new InvalidConditionSyntaxException(validator);
		}

		String textWithoutParenthesis = withoutParenthesis.group(2);
		String[] terms = textWithoutParenthesis.split(DELIMITER, -1);

		for (String term : terms) {
			// Remove spaces
			term = term.trim();

			if (term.length() == 0) {
				throw new EmptyTermException(validator);
			}

			// If the term is true or false, it's valid
			if (term.equals("true") || term.equals("false")) {
				continue;
			}

			// If it's a variable, check if it exists
			Variable termVariable = validator.getVariable(term);
			if (termVariable != null) {
				// Check if it's initialized
				if (!termVariable.hasValue) {
					throw new ConditionWithUnassignedVariableException(validator, termVariable.name);
				}
				// Check if its type is either boolean, int or double
				if (termVariable.type != VariableType.BOOLEAN &&
					termVariable.type != VariableType.INT &&
					termVariable.type != VariableType.DOUBLE) {
					throw new ConditionWithInvalidTypeVariableException(validator, termVariable.type);
				}

				// If we get here, the variable is initialized and of the correct type
				continue;
			}

			// If we get here, the variable above did not exist. But it may be a number
			if (Pattern.matches(VariablesAssignmentValidator.DOUBLE_VALUES, term)) {
				continue;
			}

			// If we get here, it's not true or false, it's not a variable and it's not a number
			throw new UnknownSymbolTermException(validator, term);
		}
	}
}

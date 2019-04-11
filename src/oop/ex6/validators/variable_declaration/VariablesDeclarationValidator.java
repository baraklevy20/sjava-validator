package oop.ex6.validators.variable_declaration;

import oop.ex6.components.Variable;
import oop.ex6.components.VariableType;
import oop.ex6.validators.BaseValidator;
import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;
import oop.ex6.validators.variable_assignment.VariablesAssignmentValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class responsible for validating a variable declaration (final int x = 3, y, z = 1)
 */
public class VariablesDeclarationValidator extends BaseValidator {
	/** The pattern of the available types **/
	private static final String AVAILABLE_TYPES = "\\s*(double|int|boolean|String|char)";

	/** The pattern of invalid variables names **/
	private static final String INVALID_NAMES = "final|if|while|void|return|true|false|" + AVAILABLE_TYPES;

	/** The pattern of valid variables names **/
    public static final String VALID_VARIABLE_NAME = "[A-Za-z]+[\\w_]*|_[\\w_]+";

    // Either an assignment or just a variable name
	/** The pattern of each declaration. Each declaration is either an assignment or a variable name **/
	private static final String DECLARATION_SEPARATOR = "\\s*((" + VariablesAssignmentValidator.ASSIGNMENT + ")|(" +
			VALID_VARIABLE_NAME + "))\\s*";

	/** Is it the first pass? **/
    private boolean isFirstPass;

	/** Is the declaration in a method argument? **/
    private boolean isDeclarationInMethodArgument;

	/** Is the declaration final? **/
    private boolean isFinal;

	/**
	 * Creates a new declaration validator
	 * @param validator the file validator
	 * @param isDeclarationInMethodArgument Is the declaration in a method argument?
	 * @param isFirstPass is it the first pass?
	 */
	public VariablesDeclarationValidator(FileValidator validator, boolean isFirstPass, boolean isDeclarationInMethodArgument) {
		super(validator);
		this.isFirstPass = isFirstPass;
		this.isDeclarationInMethodArgument = isDeclarationInMethodArgument;
	}

	/**
	 * {@inheritDoc}
	 */
	public void validate(String text) throws ValidatorException {
		// Skip the first word
		if (isFinal) {
			text = skipFirstWord(text.split(" "));
		}

        Matcher typeMatcher = Pattern.compile(AVAILABLE_TYPES).matcher(text);

        // If we're not having a correct type, leave
		if (!typeMatcher.lookingAt()) {
			throw new InvalidDeclarationTypeException(validator);
		}

		VariableType type = VariableType.valueOf(typeMatcher.group(1).toUpperCase());

		// Skip the type and split by commas
		// The -1 is there to make sure split returns empty values as well
		String[] declarations = text.substring(typeMatcher.end()).split(",", -1);

		// Go through each declaration of a variable
		for (String declaration : declarations) {
			Matcher nameMatcher = Pattern.compile(DECLARATION_SEPARATOR).matcher(declaration);
			if (!nameMatcher.matches()) {
				throw new InvalidDeclarationSyntaxException(validator);
			}
			Variable variable = new Variable();
			variable.type = type;
			variable.isFinal = isFinal;
			variable.hasValue = isDeclarationInMethodArgument;

			// If we're having an assignment
			if (Pattern.matches(VariablesAssignmentValidator.ASSIGNMENT, declaration)) {
				variable.name = nameMatcher.group(3);
				if (isDeclarationInMethodArgument) {
					throw new AssignmentIntoMethodArgumentException(validator);
				}

				// Check the validity of the assignment
				new VariablesAssignmentValidator(validator, variable, isFirstPass).validate(declaration);
			}
			// Otherwise, there's no assignment.
			// If the variable is not final, We simply need to make sure we're having
			// a variable name. Otherwise, we throw an exception
			else {
				// A final value with no value is an error, unless the final variable
				// is a method argument. In this case, it's fine
				if (isFinal && !isDeclarationInMethodArgument) {
					throw new FinalVariableWithoutValueException(validator);
				}
				variable.name = nameMatcher.group(1);
			}

			// Check for invalid name
			if (!isValidName(variable.name)) {
				throw new InvalidVariableNameException(validator, variable.name);
			}

			// Inform the validator there is a new variable. We must each new variable
			// to fail the case "int a, a;"
			validator.addNewVariable(variable, isFirstPass);
		}
    }

	/**
	 * Skips the first word in a string array
	 * @param stringArray the string array
	 * @return the string array, joined with ' ', but without the first word
	 */
	private static String skipFirstWord(String[] stringArray) {
		StringBuilder value = new StringBuilder();
		for (int i = 1; i < stringArray.length; i++) {
			value.append(stringArray[i]).append(i != stringArray.length - 1 ? " " : "");
		}

		return value.toString();
	}

	/**
	 * Checks if a variable name is valid
	 * @param name the variable name
	 * @return true iff the variable name is valid
	 */
	public static boolean isValidName(String name) {
		return Pattern.matches(VariablesDeclarationValidator.VALID_VARIABLE_NAME, name) && !Pattern.matches(VariablesDeclarationValidator.INVALID_NAMES, name);
	}

	/**
	 * Sets the isFinal field
	 * @param isFinal - the new isFinal value
	 */
	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}
}

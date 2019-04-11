package oop.ex6.validators.variable_assignment;

import oop.ex6.components.VariableType;
import oop.ex6.validators.BaseValidator;
import oop.ex6.validators.variable_declaration.VariablesDeclarationValidator;
import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;
import oop.ex6.components.Variable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class responsible for validating a variable assignment (x = 3)
 */
public class VariablesAssignmentValidator extends BaseValidator {
	/** The pattern of an assignment **/
    public static final String ASSIGNMENT = "\\s*(\\w+)\\s*=\\s*(.+)\\s*";

	/** The pattern of double values **/
	public static final String DOUBLE_VALUES = "-?\\d+(\\.\\d+)?";

	/** The pattern of int values **/
	private static final String INT_VALUES = "-?\\d+";

	/** The pattern of char values **/
	private static final String CHAR_VALUES = "\'.\'";

	/** The pattern of String values **/
	private static final String STRING_VALUES = "\".*\"";

	/** The variable to assign into **/
    private Variable variable;

    /** Is it the first pass? **/
	private boolean isFirstPass;

	/**
	 * Creates a new assignment validator
	 * @param validator the file validator
	 * @param variable the variable to assign into
	 * @param isFirstPass is it the first pass?
	 */
	public VariablesAssignmentValidator(FileValidator validator, Variable variable, boolean isFirstPass) {
		super(validator);
		this.variable = variable;
		this.isFirstPass = isFirstPass;
	}

	/**
	 * {@inheritDoc}
	 */
	public void validate(String text) throws ValidatorException {
		Matcher matcher = Pattern.compile(ASSIGNMENT).matcher(text);

		if (!matcher.matches()) {
			throw new InvalidAssignmentSyntaxException(validator);
		}

		if (variable.isFinal && variable.hasValue) {
			throw new AssignmentIntoFinalVariableException(validator, variable.name);
		}

		String value = matcher.group(2).trim();

		// Check if the value is correct

		// If it's a variable
		if (VariablesDeclarationValidator.isValidName(value)) {
			/* Check for the validity of variables iff this is the second pass OR
			* if we're at the first pass and at the global scope.
			* This ensures we don't throw an error if a global appears after a local variable access,
			* and that if a global variable tries to access another global variable that does not
			* exist, we would get an error */
			if (!isFirstPass || validator.isGlobalScope()) {
				Variable otherVariable = validator.getVariable(value);
				// Make sure it exists
				if (otherVariable == null) {
					throw new AssignmentOfUndeclaredVariableException(validator, value);
				}
				// Is of the same type
				else if (!canAssign(otherVariable.type, variable.type)) {
					throw new AssignmentOfDifferentTypesException(validator);
				}
				// And has a value
				else if (!otherVariable.hasValue) {
					throw new AssignmentOfUnassignedVariableException(validator, otherVariable.name);
				}
			}
		}
		// Otherwise, we need to make sure the value matches the type
		else if (!checkCorrectValue(variable.type, value)) {
			throw new InvalidValueException(validator, variable.type, value);
		}

		// Once we get here, the assignment went well and the variable now has a value
		variable.hasValue = true;
    }

	/**
	 * Checks if one type can be assigned into another
	 * @param from the type to assign from
	 * @param to the type to assign to
	 * @return true iff a value of type 'from' can be inserted into a variable of type 'to'
	 */
    public static boolean canAssign(VariableType from, VariableType to) {
		switch (to) {
			case DOUBLE:
				return from == VariableType.DOUBLE || from == VariableType.INT;
			case INT:
				return from == VariableType.INT;
			case BOOLEAN:
				return from == VariableType.BOOLEAN || from == VariableType.INT ||
						from == VariableType.DOUBLE;
			case CHAR:
				return from == VariableType.CHAR;
			case STRING:
				return from == VariableType.STRING;
		}

		return false;
	}

	/**
	 * Check if a value can be assigned into a variable of a given type
	 * @param type - the type of the variable
	 * @param value - the value
	 * @return true iff 'value' can be assigned into 'type'
	 */
	public static boolean checkCorrectValue(VariableType type, String value) {
		switch (type) {
			case BOOLEAN:
				return value.equals("true") || value.equals("false") ||
						checkCorrectValue(VariableType.DOUBLE, value);
			case CHAR:
				return Pattern.matches(CHAR_VALUES, value);
			case STRING:
				return Pattern.matches(STRING_VALUES, value);
			case INT:
				return Pattern.matches(INT_VALUES, value);
			case DOUBLE:
				return Pattern.matches(DOUBLE_VALUES, value);
		}

		return false;
	}
}

package oop.ex6.validators.parameter;

import oop.ex6.validators.BaseValidator;
import oop.ex6.validators.variable_assignment.VariablesAssignmentValidator;
import oop.ex6.validators.variable_declaration.VariablesDeclarationValidator;
import oop.ex6.validators.main.FileValidator;
import oop.ex6.validators.main.ValidatorException;
import oop.ex6.components.Method;
import oop.ex6.components.Variable;

/**
 * A class responsible for validating a parameter (a,b, or 3 in foo(a, b, 3))
 */
public class ParameterValidator extends BaseValidator {
	/** The method of the parameter **/
	private Method method;

	/** The index of the parameter **/
	private int parameterIndex;

	/**
	 * Creates a new parameter validator with the given file validator and the current method
	 * @param validator the file validator
	 * @param method the method of the parameter
	 */
	public ParameterValidator(FileValidator validator, Method method) {
		super(validator);
		this.method = method;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(String text) throws ValidatorException {
		// The parameter the method needs to receive
		Variable correctParameter = method.getArguments().get(parameterIndex);

		// If the parameter is a variable
		if (VariablesDeclarationValidator.isValidName(text)) {
			Variable variable = validator.getVariable(text);
			// Make sure it exists
			if (variable == null) {
				throw new UndeclaredParameterException(validator, text);
			}
			// Is of the correct type
			else if (!VariablesAssignmentValidator.canAssign(variable.type, correctParameter.type)) {
				throw new InvalidParameterTypeException(validator);
			}
			// And has a value
			else if (!variable.hasValue) {
				throw new UnassignedParameterException(validator, text);
			}
		}
		// If it isn't, we just need to make sure the value can be assigned to the type
		else if (!VariablesAssignmentValidator.checkCorrectValue(correctParameter.type, text)) {
			throw new InvalidParameterValueException(validator, correctParameter.type, text);
		}
	}

	/**
	 * Sets the parameter index
	 * @param parameterIndex the new parameter index
	 */
	public void setParameterIndex(int parameterIndex) {
		this.parameterIndex = parameterIndex;
	}
}

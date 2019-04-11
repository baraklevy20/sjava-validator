package oop.ex6.validators.main;

import oop.ex6.components.Method;
import oop.ex6.components.Scope;
import oop.ex6.components.Variable;
import oop.ex6.validators.*;
import oop.ex6.validators.condition.ConditionValidator;
import oop.ex6.validators.method_call.MethodCallValidator;
import oop.ex6.validators.method_declaration.MethodDeclarationValidator;
import oop.ex6.validators.return_statement.ReturnValidator;
import oop.ex6.validators.variable_assignment.VariablesAssignmentValidator;
import oop.ex6.validators.variable_declaration.VariablesDeclarationValidator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * The class validates an entire text file
 */
public class FileValidator {
	/** The structure of each line in the text **/
    private static final String LINE_STRUCTURE = ".*[;{}]";

	/** The lines to ignore. These are empty spaces or commented lines **/
    private static final String LINES_TO_IGNORE = "(/{2}.*)|(\\s*)";

	/** A space before a comment regex. Will be used to fail comments with spaces before them **/
	private static final String SPACE_BEFORE_COMMENT = "\\s+/{2}";

	/** The current scope **/
    private Scope currentScope;

	/** The methods of the file **/
    private HashMap<String, Method> methods;

	/** The members (global variables) of the file **/
    private HashMap<String, Variable> members;

	/** The current line we're validating **/
    private int currentLine = 0;

	/** True if the last command was return **/
    private boolean lastCommandIsReturn;

    public void validate(File file) throws FileNotFoundException, ValidatorException {
    	// Initialize the methods and members
		methods = new HashMap<String, Method>();
		members = new HashMap<String, Variable>();

		// Read the lines
		ArrayList<String> lines = readFile(file);

        // Validate the file. Once with isFirstPass set to true and once without
		// The first pass does not validate if access to variable/method is valid
		// while the second does.
        validateFile(lines, true);
        validateFile(lines, false);
    }

	/** Validates the entire file **/
    private void validateFile(ArrayList<String> lines, boolean isFirstPass) throws ValidatorException {
		currentLine = 0;

        while (currentLine < lines.size()) {
            // Skip empty lines and comments
            while (currentLine < lines.size() && Pattern.matches(LINES_TO_IGNORE, lines.get(currentLine))) {
                currentLine++;
            }

            if (currentLine == lines.size()) {
            	break;
			}

			String line = lines.get(currentLine);
            String trimmedLine = line.trim();

            // Check if there's a space before a comment or if the line doesn't end in a correct suffix
			if (Pattern.compile(SPACE_BEFORE_COMMENT).matcher(line).lookingAt()) {
				throw new CommentedLineWithSpaceException(this);
			}

            if (!Pattern.matches(LINE_STRUCTURE, trimmedLine)) {
				throw new InvalidLineSuffixException(this, trimmedLine.charAt(trimmedLine.length() - 1));
            }

            String textWithoutSuffix = trimmedLine.substring(0, trimmedLine.length() - 1).trim();
			String[] words = textWithoutSuffix.split("\\s+");

            // Handle expressions and blocks
			boolean isStatement = false;
            switch (trimmedLine.charAt(trimmedLine.length() - 1)) {
				case ';':
					isStatement = true;
				case '{':
					BaseValidator validator = getValidator(words[0], isStatement, isFirstPass);

					// If we need to validate
					if (validator != null) {
						validator.validate(textWithoutSuffix);
					}
					break;
				case '}':
					if (!trimmedLine.equals("}")) {
						throw new ClosingCurlyBracketWithTextException(this);
					}
					removeScope();
					break;
				default:
					break;
			}

            currentLine++;
        }

        // If there are scopes we haven't closed
        if (currentScope != null) {
        	throw new ExtraOpeningCurlyBracketException(this);
		}
    }

	/**
	 * Creates a new scope
	 * @param isMethodScope true iff the scope is the start of a method (and not an if/while statement)
	 */
	private void createScope(boolean isMethodScope) {
    	Scope newScope = new Scope(isMethodScope, members);
    	newScope.setParentScope(currentScope);
		currentScope = newScope;
	}

	/**
	 * Removes a scope
	 * @throws ValidatorException if there is a problem with closing the current scope
	 */
	private void removeScope() throws ValidatorException {
    	// If we're having an extra }, we throw an exception
    	if (currentScope == null) {
    		throw new ExtraClosingCurlyBracketException(this);
		}

		// If we didn't have a return statement right before the }
		if (currentScope.isMethodScope() && !lastCommandIsReturn) {
    		throw new NoReturnAtLastLineOfMethodException(this);
		}
    	currentScope = currentScope.getParentScope();
    	lastCommandIsReturn = false;
	}

	/**
	 * Gets the correct validator, based on the first word of the line
	 * @param firstWord - the first word of the line
	 * @param isStatement - true iff the line ends in ';' and not '{'
	 * @param isFirstPass - true iff this is the first pass
	 * @return the validator that handles the line or null if there's no need to validate the line
	 * @throws ValidatorException if there's a general error with the line, i.e empty line
	 */
	private BaseValidator getValidator(String firstWord, boolean isStatement, boolean isFirstPass) throws ValidatorException {
		VariablesDeclarationValidator variablesDeclaration =
				new VariablesDeclarationValidator(this, isFirstPass, false);
		MethodCallValidator methodCallValidator = new MethodCallValidator(this);
		ReturnValidator returnValidator = new ReturnValidator(this);
		ConditionValidator condition = new ConditionValidator(this);
		MethodDeclarationValidator methodDeclarationValidator = new MethodDeclarationValidator(this, isFirstPass);

    	if (isStatement) {
			lastCommandIsReturn = false;
			switch (firstWord) {
				case "final":
					variablesDeclaration.setFinal(true);
					return variablesDeclaration;
				case "int":
				case "double":
				case "String":
				case "boolean":
				case "char":
					variablesDeclaration.setFinal(false);
					return variablesDeclaration;
				case "return":
					lastCommandIsReturn = true;
					return returnValidator;
			}

			// If we get here, either the first word is a variable, a method call or a non existing keyword

			Variable variable = getVariable(firstWord);
			if (variable != null) {
				return new VariablesAssignmentValidator(this, variable, isFirstPass);
			}

			// If it isn't a variable, we might have a method or a non existing keyword
			// In either way, we can verify if the code is valid in the second pass
			if (!isFirstPass) {
				// Check if there's any expression
				if (firstWord.length() == 0) {
					throw new NoExpressionBeforeSemicolonException(this);
				}

				return methodCallValidator;
			}

			return null;
		}

		// If we get here, we're having a block

		if (firstWord.equals("void")) {
			if (currentScope != null) {
				throw new MethodInsideMethodException(this);
			}
			createScope(true);
			return methodDeclarationValidator;
		}

		// Handle if and whiles
		if (firstWord.startsWith("if") || firstWord.startsWith("while")) {
			if (currentScope == null) {
				throw new ConditionOutsideMethodException(this);
			}
			createScope(false);
			if (!isFirstPass) {
				return condition;
			}

			return null;
		}

		if (firstWord.length() == 0) {
			throw new NoExpressionBeforeCurlyBracketException(this);
		}

		throw new NonExistingKeywordException(this, firstWord);
	}

	/**
	 * Reads the file into an array of lines
	 * @param file - the file to validate
	 * @throws FileNotFoundException if the file was not found
	 */
    private ArrayList<String> readFile(File file) throws FileNotFoundException {
		ArrayList<String> lines = new ArrayList<String>();
        Scanner input = new Scanner(file);

        while (input.hasNextLine()) {
            lines.add(input.nextLine());
        }

        input.close();
        return lines;
    }

	/**
	 * Gets the current line number
	 * @return the current line number
	 */
	public int getLineNumber() {
        return currentLine + 1;
    }

	/**
	 * Gets a variable by its name
	 * @param name - the variable to get
	 * @return the variable, or null if the variable was not found
	 */
	public Variable getVariable(String name) {
    	Scope scope = currentScope;

    	// Go through each scope from the current one all the way up to the global variables scope
    	while (scope != null) {
    		Variable variableInScope = scope.getVariables().get(name);

    		// If we found the variable, return it
    		if (variableInScope != null) {
    			return variableInScope;
			}

			// Move on to the parent scope
			scope = scope.getParentScope();
		}

		// Check the global variables
		if (currentScope == null) {
    		return members.get(name);
		}

		return currentScope.getMembers().get(name);
	}

	/**
	 * Adds a new variable
	 * @param variable - the variable to add
	 * @param isFirstPass - is it the first pass?
	 * @throws ValidatorException if the variable already exists
	 */
	public void addNewVariable(Variable variable, boolean isFirstPass) throws ValidatorException {
    	// If we're not in any scope, we're at the global variables scope
    	if (currentScope == null) {
    		// Validate only on the first pass
    		if (isFirstPass && members.get(variable.name) != null) {
    			throw new VariableAlreadyExistsException(this, variable.name);
			}
    		members.put(variable.name, variable);
		}
		else {
    		// If we're in a local scope, validate only on the second pass
    		if (!isFirstPass && currentScope.getVariables().get(variable.name) != null) {
				throw new VariableAlreadyExistsException(this, variable.name);
			}
			currentScope.getVariables().put(variable.name, variable);
		}
	}

	/**
	 * Adds a new method
	 * @param method - the method to add
	 * @param isFirstPass - is it the first pass?
	 * @throws ValidatorException if the method already exists
	 */
	public void addNewMethod(Method method, boolean isFirstPass) throws ValidatorException {
    	// Check if the method already exists
    	if (isFirstPass && methods.get(method.getName()) != null) {
    		throw new MethodAlreadyExistsException(this, method.getName());
		}

    	methods.put(method.getName(), method);
	}

	/**
	 * Gets a method by name
	 * @param methodName the method name
	 * @return the method, or null if a method with the given name does not exist
	 */
	public Method getMethod(String methodName) {
    	return methods.get(methodName);
	}

	/**
	 * Gets the current scope
	 * @return the current scope
	 */
	public Scope getCurrentScope() {
		return currentScope;
	}

	/**
	 * Is it the global scope?
	 * @return true iff the current line is in the global scope
	 */
	public boolean isGlobalScope() {
		return currentScope == null;
	}
}

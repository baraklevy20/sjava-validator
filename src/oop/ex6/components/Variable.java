package oop.ex6.components;

/**
 * A class that represents a variable
 */
public class Variable {
	/** The name of the variable **/
    public String name;

	/** The type of the variable **/
    public VariableType type;

	/** Is the variable final or not **/
    public boolean isFinal;

	/** True if the variable is assigned **/
    public boolean hasValue;

	/**
	 * Creates a new variable
	 */
	public Variable() {
	}

	/**
	 * Deep-copies another variable
	 * @param variable the variable to copy
	 */
	public Variable(Variable variable) {
		this.name = variable.name;
		this.type = variable.type;
		this.isFinal = variable.isFinal;
		this.hasValue = variable.hasValue;
	}
}

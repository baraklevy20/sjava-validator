package oop.ex6.components;

import java.util.LinkedList;


/**
 * The class represents a method
 */
public class Method {
	/** The name of the method **/
    private String name;

	/** The arguments of the methods **/
    private LinkedList<Variable> arguments;

	/**
	 * Creates a new method with no arguments nor name
	 */
	public Method() {
    	this.arguments = new LinkedList<Variable>();
	}

	/**
	 * Get the name of the method
	 * @return the name of the method
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the method
	 * @param name the name of the method
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the arguments of the method
	 * @return the arguments of the method
	 */
	public LinkedList<Variable> getArguments() {
		return arguments;
	}
}

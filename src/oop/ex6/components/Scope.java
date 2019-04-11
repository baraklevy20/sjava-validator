package oop.ex6.components;

import java.util.HashMap;

/**
 * The class represents a scope. Each scope contains variables, members and a pointer to the parent scope
 */
public class Scope {
	/** The local variables defined in the scope **/
	private HashMap<String, Variable> variables;

	/** The members in the scope. Since each global variable may have values inside one scope
	   and no values inside of another scope, each scope needs to store the members as well **/
	private HashMap<String, Variable> members;

	/** The pointer to the parent scope. It will make is possible to search for variables in upper scope **/
	private Scope parentScope;

	/** A variable that states if this scope is a method scope or an if/while scope **/
	private boolean isMethodScope;

	/**
	 * Creates a new scope
	 * @param isMethodScope - is the scope a method scope
	 * @param members - the members. Each scope will create a deep copy of the members
	 */
	public Scope(boolean isMethodScope, HashMap<String, Variable> members) {
		this.variables = new HashMap<String, Variable>();
		this.members = new HashMap<String, Variable>();

		// Create a deep copy of each member. This is because each scope may set the value
		// of a member, but it should still be unassigned in another scope
		for (String memberName : members.keySet()) {
			this.members.put(memberName, new Variable(members.get(memberName)));
		}

		this.isMethodScope = isMethodScope;
	}

	/**
	 * Is the scope a method scope
	 * @return true if the scope is a method scope
	 */
	public boolean isMethodScope() {
		return isMethodScope;
	}

	/**
	 * Get the local variables in the scope
	 * @return the local variables in the scope
	 */
	public HashMap<String, Variable> getVariables() {
		return variables;
	}

	/**
	 * Get the global variables in the scope
	 * @return the global variables in the scope
	 */
	public HashMap<String, Variable> getMembers() {
		return members;
	}

	/**
	 * Get the parent scope of this scope
	 * @return the parent scope of this scope or null if there isn't one
	 */
	public Scope getParentScope() {
		return parentScope;
	}

	/**
	 * Set the parent scope of this scope
	 * @param parentScope the parent scope of this scope
	 */
	public void setParentScope(Scope parentScope) {
		this.parentScope = parentScope;
	}
}

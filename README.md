barak.levy
meirav.caspi



=============================
=      File description     =
=============================
components/Method.java - The class represents a method

components/Scope.java - The class represents a scope. Each scope contains variables,
members and a pointer to the parent scope

components/Variable.java - The class that represents a variable

components/VariableType.java - The enum represents the different variable types
supported by the validator

main/Sjavac.java - The class runs the s-java validator

validators/BaseValidator.java - A base class for all validators. Contains the file validator,
and a method that validates a given text

validators/main/FileValidator.java - The class validates an entire text file

validators/main/ValidatorException.java - A general exception that prints the
line that was problematic while validating and an informative message

validators/main/*Exception.java - A general problem with the code that may
occur while validating the file. i.e no semicolon

validators/condition/ConditionValidator.java - A class responsible for validating
a condition (if and while)
										
validators/condition/*Exception.java - A problem with the code that may occur while
validating a condition

validators/method_call/MethodCallValidator.java - A class responsible for validating
a method call (foo(a, b, c))
										
validators/method_call/*Exception.java - A problem with the code that may occur while
validating a method call

validators/method_declaration/MethodDeclarationValidator.java - A class responsible for validating
a method declaration (void foo())
										
validators/method_declaration/*Exception.java - A problem with the code that may occur while
validating a method declaration

validators/parameter/ParameterValidator.java - A class responsible for validating
a parameter (a,b, or 3 in foo(a, b, 3))
										
validators/parameter/*Exception.java - A problem with the code that may occur while
validating a parameter

validators/return_statement/ReturnValidator.java - A class responsible for validating
a return statement (return)
										
validators/return_statement/*Exception.java - A problem with the code that may occur while
validating a return statement

validators/variable_assignment/VariablesAssignmentValidator.java - A class responsible for validating
a variable assignment (x = 3)
										
validators/variable_assignment/*Exception.java - A problem with the code that may occur while
validating a variable assignment

validators/variable_declaration/VariablesDeclarationValidator.java - A class responsible for validating
a variable declaration (final int x = 3, y, z = 1)
										
validators/variable_declaration/*Exception.java - A problem with the code that may occur while
validating a variable declaration

README - this file

=============================
=          Design           =
=============================
When we approached this project, we first thought about the questions in the object oriented design section.
This allowed us to think about different cases that the validator may need to support in the future,
and how we should make our code easier to maintain.
The approach we took is as follows:
A basic file validator starts validating the code, line by line. It checks for basic syntax mistakes
such as not having a semicolon or having an additional closing curly bracket.
The basic file validator then reads the line and with a factory function, returns the appropriate validator.
For instance, if the basic file validator reads 'if (something)', an if validator would return.
The validator then simply calls the validate method on the returned validator.
This enforces the single choice principle and makes the code easier to maintain.

To support additional properties to different components of sjava, such as adding access
modifiers, or new return types for methods, each variable, method and scope is a represented by a class.
This class holds all of the information a variable, method or a scope needs.
For instance, a variable stores its name, its type, if it's final and if it has a value.
To store additional properties, we simply need to create additional members in the appropriate class.

* Because global variables or methods may be declared after they're used, two passes of the code
are required.
The first pass needs to find the different global variables and methods, and does not validate
the use of an unknown variable/method, while the second pass needs to check everything.
To avoid code duplication, we chose to make our validation methods parameterized. They're almost the same,
except that the first run or second run may skip some tests.

* To support additional features, one simply needs to add a new case to the factory method,
and return a new validator. *

This also means that the code is split into small, independent units, where each one takes care of
a different feature of the sjava language.

6.1 To handle invalid code, all the validate methods are void. If they return,
the code is valid. However, if the code is invalid, an exception would be thrown with an
explanation of what went wrong.
The exception is then propagated up to the Sjava class, which prints it and prints 1.

When we were designing the code, we thought about simply throwing an Exception object with the error.
But then we tried to think of the different usages of our validator.
There are two main usages we came up with:
- A logging mechanism - When developing a compiler, we might want to store the invalid line of code
in a logging file. Since it's difficult to parse strings, and since exceptions text may change,
strings may not be the best choice. Maybe the user would prefer to store the exception as an XML
or a JSON, with objects that represent what went wrong instead of a a simple text, which is easier
for humans but harder for computers.
- Suggesting a fix - When developing using an IDE, the user may write invalid code. A decent IDE
will prompt the user his code is invalid, but a good IDE will suggest him how to fix it.
For instance, if the user forgot a semicolon, the IDE may add a semicolon automatically.
But in order to do that, the IDE needs a very specific exception. A string that the user
is missing a semicolon does not really help, and it is also much harder to parse.

Therefore, we chose to go with the following approach - every time the validator finds an invalid line
of code, it creates a new unique exception with the *data* that belong to the error, instead of
a message. Then, the exception itself builds the error message using that data.
This also allows the error message to be change in just one place - the exception class itself.
For instance, the constructor of an invalid number of arguments exceptions looks like this:
"InvalidNumberOfArgumentsException(FileValidator validator, int expected, int received)"
The constructor then generates a message using that data.
A better approach here would be to also support generating XMLs or JSONs with the data,
and to also pass the entire method object, but since this isn't a real validator, a simple
message with simple data would suffice.

6.2 To support a new variable type, a few minimal methods need to change. The VariableType enum
needs to have the additional type, the two methods - canAssign (if one type can be assigned into another)
and checkCorrectValue (if a value can be assigned into a certain type) need to support the additional type,
and the available types regex over the variables declaration validator need to have the new type.

Supporting multiple classes is also pretty simple.
Since the validator runs two passes on the code, and generates a list of its global variables/methods
after the first pass, we simply need to run the first pass on each class.
Then, in the getVariable(name) or getMethod(name), we simply need to search for that name
in the lists generated by another class, instead of the list in the current single class.
An additional change would be to change the regex of a variable/method name to now also support
a dot. A dot would indicate a different class.
Therefore this requires 3 changes in the code - To run the first pass on all classes,
to change the regex of the variable/method names and to look for the variable
in the list of other classes instead of the list of the current single class.

Supporting different methods return types would be simple as well.
The basic validator factory now has to be a bit more precise. Instead of sending every text that
starts with 'int' to the variable declaration validator, it might need to send it to the method
declaration validator.
The method validator also needs to change the regex of its pattern
to be 'void|int|..." instead of simply "void...".
Lastly, the Method class needs to have a type member.
Overall around 3 changes in the code.

6.3 A simple yet important regex that will probably get called the most in real programs
is the one that skips empty or commented lines - "(/{2}.*)|(\s*)".
It matches lines that start with 2 '/' and any text (or non) following it,
or lines that only have spaces (or non, in which case the line is empty).

Another important regex is the one that validates method names - "[A-Za-z]+[\\w_]*".
It matches names that start with at least one letter,
and then any amoount of digits, letters or an underscores.

=============================
=  Implementation details   =
=============================
* In order to access variables and methods quickly, we decided to use hash sets, where the key
is the variable/method name and the value is the variable/method itself.

* Every exception class extends the ValidatorException class. When an exception is thrown,
the ValidatorException calls the getLineNumber so that each exception automatically prints
the line number on which it occured on.

* Each validator type and the exceptions it may throw appear in their own package.
This simplifies lookup and it is easier to see what are the different exceptions a
single sjava feature (conditions/variable assignment and so on) may throw.

* Each scope contains variables, members and a pointer to the parent scope. When searching
for a variable by its name, we first search in the innermost scope and propagate up
until the variable is find (or not).

* Overall it was a very fun project, we had a great time, and we hope you enjoy testing our code.
Thanks for everything :)
DESCRIPTION = "multiple varibles understanding"

LICENSE = "CLOSED"


# Variable Assignment
## Types of Variable Assignments


# COMMAND
# bitbake -e <RECIPE_NAME> | grep ^<VARIABLE_NAME>=

# ?=    :  This is used to assign the default value to variable. It can be overridden.
#          it will store the first value

#TEST ?= "foo"
#TEST ?= "bar"
#TEST ?= "val"
#TEST ?= "var"

# The final value is TEST="foo"
#-----------------------------------------------------------------------------------

# ??=    :  This is used to assign the default value to variable. But it is waek assignment.
#           It can be overridden. If multiple assignments are done with this type, the last 
#           one will be considered

#TEST ??= "foo"
#TEST ??= "bar"
#TEST ??= "val"
#TEST ??= "var"

# The final value is TEST="var"
#-----------------------------------------------------------------------------------

#TEST ??= "foo"
#TEST ?= "bar"
#TEST ?= "val"
#TEST ??= "var"

# The final value is TEST="bar" 
#----------------------------------------------------------------------------------

# Override

A  ?= "foo"
A   = "bar"

# The value of A is "bar"

#----------------------------------------------------------------------------------

# Variable expansion

A = "foo"
B = "${A}"
A = "bar"

# The final B value is "bar"

#----------------------------------------------------------------------------------

# Override
A ?= "foo"
A := "bar"


# The final A value is "bar"

#----------------------------------------------------------------------------------

# variable expansion
A = "foo"
B := "${A}"
A =  "bar"

# The final value is B="foo"

#----------------------------------------------------------------------------------

# spaces are added here

# Append
A = "foo"
A += "bar"

# The final value is A = "foo bar"

# Prepend 

A = "foo"
A =+ "bar"

# The final value is A = "bar foo"

# Append
A ?= "val"
A += "var"

# The final value is A = "var"


# Prepend
A ??= "val"
A =+ "var"

# The final value is A = "var"

#----------------------------------------------------------------------------------

# No spaces are added here
# need to add extra spaces

# Append
A = "foo"
A .= "bar"

# The final value of A is "foobar"

# Prepend
A = "foo"
A =. "bar"

# The final value of A is "barfoo"

#----------------------------------------------------------------------------------

# Nospaces are added here
# need to add extra spaces
# parsed at the end

# Append
A = "foo"
A:append = "bar"

# The final value of A is "foobar"

# Append
A = "foo"
A:append = "bar"
A += "val"

# The final value of A is "foo valbar"

# Append 
A = "foo"
A:append = " bar"

# The final value of A is "foo bar"


# Prepend
A = "foo"
A:prepend = "bar"

# The final value of A is "barfoo"

# Prepend
A = "foo"
A:prepend = "bar"
A =+ "val"


# The final value of A is "barval foo"

# Prepend
A = "foo"
A:prepend = "bar "

# The final value of A is "bar foo"


# remove

A  = "foo bar foo foo"
A:remove = "foo"


# The final value of A is "bar"









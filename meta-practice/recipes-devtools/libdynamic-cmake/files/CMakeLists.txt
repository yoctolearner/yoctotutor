# cmake minimum
cmake_minimum_required(VERSION 3.28)

#project
project(libdynamic LANGUAGES C)

# generate library
add_library(libdynamic SHARED math.c)

set_target_properties(libdynamic PROPERTIES
			VERSION 1.0
			SOVERSION 1)

# install library in lib
install(TARGETS libdynamic
		LIBRARY DESTINATION lib)

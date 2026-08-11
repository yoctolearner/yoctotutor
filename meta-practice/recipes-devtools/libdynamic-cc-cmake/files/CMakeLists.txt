#cmake
cmake_minimum_required(VERSION 3.28)

#project
project(libdynamic-cmake LANGUAGES CXX)

# dynamic library creation
add_library(libdynamic-cmake SHARED math.cpp)

# set properties to executable
set_target_properties(libdynamic-cmake PROPERTIES
			VERSION 1.0
			SOVERSION 1)

# install the library in to lib
install(TARGETS libdynamic-cmake
		LIBRARY DESTINATION lib)

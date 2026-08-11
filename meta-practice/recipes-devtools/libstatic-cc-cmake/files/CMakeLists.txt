# cmake
cmake_minimum_required(VERSION 3.28)

#project
project(libstatic LANGUAGES CXX)


# generate the lib
add_library(libstatic STATIC math.cpp)

# install library
install(TARGETS libstatic 
		RUNTIME DESTINATION lib)

# cmake 
cmake_minimum_required(VERSION 3.28)

# project
project(libstatic LANGUAGES C)


# generate executable
add_library(libstatic STATIC  math.c)

# install the exectuble into bin
install(TARGETS libstatic 
	ARCHIVE DESTINATION lib)

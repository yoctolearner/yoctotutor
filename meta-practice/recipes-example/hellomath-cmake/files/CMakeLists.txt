# hello cmake
cmake_minimum_required(VERSION 3.28)

# project nanme
project(hellomath LANGUAGES C)

# executable
add_executable(hellomath hellomath.c)

# install the executable in bin folder
install(TARGETS hellomath 
		RUNTIME DESTINATION bin)

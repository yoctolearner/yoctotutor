# cmake version
cmake_minimum_required(VERSION 3.28.3)

# project
project(hello-cmake LANGUAGES C)

# exectue
add_executable(hello-cmake hello-cmake.c)

# install
install(TARGETS hello-cmake 
	RUNTIME DESTINATION bin)

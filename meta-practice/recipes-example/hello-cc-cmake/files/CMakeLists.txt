# cmake
cmake_minimum_required(VERSION 3.28)

# project
project(hello-cc-cmake LANGUAGES CXX)

# executable
add_executable(hello-cc-cmake hello.cpp)

# install bin
install(TARGETS hello-cc-cmake
	RUNTIME DESTINATION bin)

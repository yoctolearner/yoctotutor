# dynamic lib using make in cc

	
CXX ?= g++
CXX_FLAGS = -Wall -fPIC
LINKER_FLAGS = -Wl,-soname,libdynamic.so.1

TARGET = libdynamic.so.1.0.0 
SRC = math.cpp
OBJ = math.o

all: $(TARGET)

$(TARGET): $(OBJ)
	$(CXX) -shared $(LINKER_FLAGS) -o $(TARGET) $(OBJ)

$(OBJ):  $(SRC)
	$(CXX) $(CXX_FLAGS) -c $(SRC) -o $(OBJ)

clean:
	rm -rf $(OBJ)
	rm -rf  libdynamic.so*

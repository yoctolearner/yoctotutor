# make file for hello
CXX ?= g++
CFLAGS ?= = -Wall

TARGET = hello-cc-make
SRC = hello.cpp
OBJ = hello.o

all: $(TARGET)

$(TARGET): $(OBJ)
	$(CXX) $(LDFLAGS) -o $(TARGET) $(OBJ)

$(OBJ): $(SRC)
	$(CXX) $(FLAGS) -c $(SRC) -o $(OBJ)

clean:
	rm -rf $(OBJ) $(TAEGET)

.PHONEY: all clean

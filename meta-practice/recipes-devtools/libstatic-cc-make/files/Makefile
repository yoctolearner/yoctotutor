# make file
CXX ?= g++
CFLAGS ?= -Wall

TARGET = libstatic.a
OBJ = math.o
SRC = math.cpp

all: $(TARGET)

$(TARGET):  $(OBJ)
	ar rcs $(TARGET) $(OBJ)

$(OBJ):  $(SRC)
	$(CXX) $(CFLAGS) -c $(SRC) -o $(OBJ)

clean:
	rm -rf $(TARGET) $(OBJ)

.PHONEY: all clean

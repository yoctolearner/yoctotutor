# makefile for the dynamic library
CC ?= gcc
CC_FLAGS ?= -Wall -fPIC
LINKER_FLAGS = -Wl,-soname,libdynamic.so.1

TARGET = libdynamic.so.1.0.0
SRCS = math.c
OBJS = math.o

all: $(TARGET)

$(TARGET):  $(OBJS)
	$(CC) -shared $(LINKER_FLAGS) -o $(TARGET) $(OBJS)

$(OBJS):  $(SRCS)
	$(CC) $(CC_FLAGS) -c $(SRCS) -o $(OBJS) 

clean: 
	rm -rf $(OBJS) $(TARGET)

.PHONY:  all clean

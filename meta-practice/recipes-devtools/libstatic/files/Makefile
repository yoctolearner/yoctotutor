# makefile for the static library
CC ?= gcc
CFLAGS ?= -Wall

TARGET = libstatic.a
SRCS = math.c
OBJS = math.o

all: $(TARGET)

$(TARGET):  $(OBJS)
	ar rcs $(TARGET) $(OBJS)

$(OBJS):  $(SRCS)
	$(CC) $(LDFLAGS) -c $(SRCS) -o $(OBJS) 

clean: 
	rm -rf $(OBJS) $(TARGET)

.PHONY:  all clean

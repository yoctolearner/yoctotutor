# make file

CC ?= gcc
CFLAGS ?= -Wall

TARGET = hellomath
OBJS = hellomath.o
SRCS = hellomath.c

$(TARGET):  $(OBJS)
	${CC} ${LDFLAGS} ${OBJS} -o $(TARGET)


$(OBJS):  $(SRCS)
	${CC} ${CFLAGS} -c ${SRCS} -o $(OBJS)

clean:
	rm -rf $(OBJS) $(TARGET)

#include <stdio.h>

int add(int a, int b)
{
	return (a + b);
}

int sub(int a, int b)
{
	if(a > b)
	{
		return (a - b);
	}else{
		return (b - a);
	}
}

int mul(int a, int b)
{
	return (a * b);
}

int main()
{
	printf("sum of two numbers is %d\n",add(12345, 6789));
	printf("subtraction of two numbers is %d\n",sub(12345, 6789));
	printf("multiplication of two numbers is %ld\n",mul(12345, 678));

	return 0;
}


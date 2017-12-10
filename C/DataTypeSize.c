#include <stdio.h>

int main()
{
    int integerValue = 1;

    while(integerValue > 0)
    {
        integerValue += 100;

        printf("Value : %d \n", integerValue);
    }

    printf("Value : %d \n", &integerValue);
}

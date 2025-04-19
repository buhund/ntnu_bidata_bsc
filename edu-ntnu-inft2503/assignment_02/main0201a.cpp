#include <iostream>

using namespace std;


int main()
{
    int i = 3;
    int j = 5;

    int *p = &i;
    int *q = &j;

    cout << "Value " << i << " resides at address " << p << endl;
    cout << "Value " << j << " resides at address " << q << endl;
}

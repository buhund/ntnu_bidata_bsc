#include <iostream>

using namespace std;

int main()
{
    // Fra oppgave A
    int i = 3;
    int j = 5;

    int *p = &i;
    int *q = &j;

    cout << "Verdier fra del A" << endl;
    cout << "Value of i: " << i << " resides at address " << p << endl;
    cout << "Value of j: " << j << " resides at address " << q << endl;
    cout << endl;

    // Endringer til oppgave B
    *p = 7;
    *q += 4;
    *q = *p + 1;
    p = q;
    // cout << *p << " " << *q << endl;

    cout << "Oppdaterte verdier til del B" << endl;
    cout << "Value of i: " << i << " resides at address " << p << endl;
    cout << "Value of j: " << j << " resides at address " << q << endl;
    cout << "Pointer p now points to address " << p << " with value " << *p << endl;
    cout << "Pointer q now points to address " << q << " with value " << *q << endl;
    cout << "Final output: " << *p << " " << *q << endl;

}
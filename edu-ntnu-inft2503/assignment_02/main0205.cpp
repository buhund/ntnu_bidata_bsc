# include <iostream>
using namespace std;

int main()
{
    double number = 0.0;
    double* pointer = &number;
    double &reference = number;

    number = 5;
    cout << "Metode 1 for å tilordne verdi til 'number': " << endl;
    cout << "Direkte tilordning: number = 4" << endl;
    cout << "number = " << number << endl;
    cout << endl;

    *pointer = 12.9;
    cout << "Metode 2 for å tilordne verdi til 'number': " << endl;
    cout << "Tilordning ved bruk av peker: *pointer = " << *pointer << endl;
    cout << "number = " << number << endl;
    cout << endl;

    reference = 19.5;
    cout << "Metode 3 for å tilordne verdi til 'number': " << endl;
    cout << "Tilordning via referanse: &reference = " << reference << endl;
    cout << "number = " << number << endl;
    cout << endl;
}

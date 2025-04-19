// Assignment 08 Task 01

/**
* Lag en funksjonstemplate som finner ut om to verdier er like, for eksempel:
*   template <typename Type> bool equal(Type a, Type b) {...}
* Du kan anta at sammenlikningsoperatorene er implementert for den aktuelle datatypen.
*
* Lag en spesialutgave for double, for eksempel:
*   bool equal(double a, double b) {...}
* Denne funksjonen skal kunne brukes for beregnede desimaltallsverdier.
* Anta at to slike verdier betraktes som like dersom forskjellen mellom dem er mindre
* enn for eksempel 0.00001.
*
* Lag et lite program som prøver både template-utgaven og spesialutgaven.
* Sjekk at riktig utgave blir brukt ved å legge inn utskrift-setninger inni funksjonene.
* Du vil kanskje trenge å skrive ut double med mer enn 6 desimaler,
* bruk i tilfelle manipulatoren setprecision().
*/

#include <iostream>
#include <iomanip>

using namespace std;

// Function template, determining if two values are equal
template <typename Type>
// Basic comparison template
bool equal(Type firstType, Type secondType)
{
    cout << "Using Template funcion" << endl;
    return firstType == secondType;
}


// Special Double comparison template
// Check if the absolute value of the difference between a and b is less than 1e-
// If so, they are considered equal = True
bool equal(double a, double b)
{
    cout << "Using double special function" << endl;
    return abs(a - b) < 1e-5; // 0.00001
}


int main()
{
    // Enable printing true/false instead of 1/0
    cout << boolalpha;

    // Test template function with int
    // Not equal, so ill return False
    int x1 = 12;
    int y1 = 13;
    cout << "Result of equal(int, int): " << equal(x1, y1) << endl;
    cout << "Comparing values: x1 = " << x1 << ", y1 = " << y1 << "\n" << endl;

    // Test template function with int
    // Equal, so ill return True
    int x2 = 12;
    int y2 = 12;
    cout << "Result of equal(int, int): " << equal(x2, y2) << endl;
    cout << "Comparing values: x2 = " << x2 << ", y2 = " << y2 << "\n" << endl;

    // Test template function with char
    // Two identical char, 'C'
    char c1 = 'C';
    char c2 = 'C';
    cout << "Result of equal(char, char): " << equal(c1, c2) << endl;
    cout << "Comparing values: c1 = " << c1 << ", c2 = " << c2 << "\n" << endl;

    // Test special function with double
    // False, because they are not long enough to trigger the decimal length clause.
    double i = 12.12;
    double j = 13.13;
    cout << "Result of equal(double, double): " << equal(i, j) << endl;
    cout << "Comparing values: i = " << i << ", j = " << j << "\n" << endl;

    // Test double special function
    // Returns True, because they both rounds to 12,12345679
    double a = 12.12345678999;
    double b = 12.12345678888;
    cout << "Result of equal(double, double): " << setprecision(10) << equal(a, b) << endl;
    cout << "Comparing values: a = " << setprecision(10) << a << " , b = " << b << "\n" << endl;

    // Test double special function
    // Returns false, because they start differing at the 5th decimal place.
    double n = 12.12345555555;
    double p = 12.12344444444;
    cout << "Result of equal(double, double): " << setprecision(10) << equal(n, p) << endl;
    cout << "Comparing values: n = " << setprecision(10) << n << " , p = " << p << "\n" << endl;

    return 0;
}
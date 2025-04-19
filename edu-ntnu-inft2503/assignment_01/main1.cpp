#include <iostream>
#include <fstream>

using namespace std;

int main() {

    // Constant for number of temperatures
    const int length = 5;

    // Variable to read and hold temp input
    double temperature;

    // Initialize temp intervals
    int under_10_degree = 0;
    int between_10_and_20_degree = 0;
    int over_20_degree = 0;

    // cout is Character OUTput
    cout << "Skriv inn " << length << " temperaturer:" << endl;

    for (int i = 0; i < length; i++)
    {
        cout << "Tempeeratur nr. " << i+1 << ": "; // Ikke endl her, vil skrive på input på samme linje.
        cin >> temperature;

        if (temperature < 10)
        {
            ++under_10_degree; // Add to sub-10 counter
        } else if (temperature >= 10 && temperature <= 20)
        {
            ++between_10_and_20_degree; // Om mellom 10 og 20, legg inn der
        } else
        {
            ++over_20_degree; // Hvis den ikke er en av de andre, må den være over 20
        }
    }

    cout << "Antall under 10 grader er " << under_10_degree << endl;
    cout << "Antall mellom 10 og 20 grader er " << between_10_and_20_degree << endl;
    cout << "Antall over 20 grader er " << over_20_degree << endl;

}


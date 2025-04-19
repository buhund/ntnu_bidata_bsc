#include <iostream>
#include <fstream>
#include <unistd.h>

using namespace std;

void read_temperatures(double temperature[], int length);

int main() {
    const int length = 20;
    double temperatures[length];
    read_temperatures(temperatures, length);

    // Initialize the temp intervals
    int under_10 = 0;
    int between_10_and_20 = 0;
    int over_20 = 0;


    for (int i = 0; i < length; i++)
    {
        if (temperatures[i] < 10)
        {
            ++under_10; // Add to sub-10 counter
        } else if (temperatures[i] >= 10 && temperatures[i] <= 20)
        {
            ++between_10_and_20; // Om mellom 10 og 20, legg inn der
        } else
        {
            ++over_20; // Hvis den ikke er en av de andre, må den være over 20
        }
    }

    cout << "Antall under 10 Kelvin er " << under_10 << endl;
    cout << "Antall mellom 10 og 20 Kelvin er " << between_10_and_20 << endl;
    cout << "Antall over 20 Kelvin er " << over_20 << endl;

    return 0;
}

void read_temperatures(double temperatures[], int length)
{
    ifstream file("temperature.txt");

    if (!file) // Om ikke fil åpner skikkelig
    {
        cout << "Unable to open selected file." << endl;
        exit(1); // Avslutt programmet
    }

    // For-loop for å lese inn temp og lagre i array
    for (int i = 0; i < length; i++)
    {
        file >> temperatures[i];
    }

    file.close(); // Lukker fila etter lesing.
    cout << "Temperatures read successfully." << endl;
    cout << "Here they are: " << endl;

    for (int i = 0; i < length; i++)
    {
        cout << temperatures[i] << " Kevlin" << endl;
    }

}

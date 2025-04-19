# include <iostream>
using namespace std;

int find_sum(const int *table, int length)
{
    int sum = 0;
    for (int i = 0; i < length; i++)
    {
        sum += table[i];
    }
    return sum;
}

int main()
{
    int table[20]; // Tabell med plass til 20 tall.
    for (int i = 0; i <20; i++)
    {
        table[i] = i + 1; // Setter inn tallene fra og med 1 til og med 20 i tabellen.
    }

    // Summering av tall
    int sum_first_10 = find_sum(table, 10);
    cout << "Summen av de første 10 tallene (1-10) er: " << sum_first_10 << endl;

    int sum_next_5 = find_sum(table + 10, 5);
    cout << "Summan av de neste 5 tallene (11-15) er: " << sum_next_5 << endl;

    int sum_last_10 = find_sum(table + 15, 5);
    cout << "Summan av de neste 5 tallene (16-20) er: " << sum_last_10 << endl;

}
// Assignment 08 Task 02
/**
* Du skal lage en template-klasse for par. De to elementene som inngår i et par kan være av forskjellig type.
* Klassen skal tilby følgende:
*   - En konstruktør som tar begge elementene som argumenter.
*   - En operator for å legge sammen to par. Den skal lages ved elementvis summering, se eksemplet nedenfor.
*   - En operator for å finne ut om et par er større enn et annet par.
*     Her skal du sammenligne summen av elementene i hvert enkelt par, se eksemplet nedenfor.
*/

#include <iostream>

using namespace std;

// Generic (template) class, which will take two different types, T1 and T2.
template <typename T1, typename T2>
class Pair
{
public:
    // Two variables to keep each element of the pair.
    T1 first;
    T2 second;

    // Constructor, taking in two arguments of type T1 and T2.
    // Initialise the variables first and second with values given by a and b and the type.
    Pair(T1 firstElement, T2 secondElement) : first(firstElement), second(secondElement) {}

    // Overloading operator +
    // Purpose: Sum first+first, second+second, from both pairs.
    // & other is a reference directly to the second Pair object, naming it other
    // Setting both this and other constant, so that they cant be changed. Dunno if this is smart, but it
    // struck med as a good idea, as I would think one wouldn't want changes to be made in the
    // comparison template method thing.
    Pair<T1, T2> operator+(const Pair<T1, T2>& other) const
    {
        return Pair<T1, T2>(first + other.first, second + other.second);
    }

    // Overloading operator > to compare the sum of one pair to another
    bool operator>(const Pair<T1, T2>& other) const
    {
        // First we find the sum of the first pair, then evaluate if first is greaterThan second (other) pair.
        // The goal is to compare the sum of the pairs, to find if one pair is greater than the other.
        // Adds up this.first and this.second, then adds other.first and other.second.
        // this.sum is compared to other.sum
        return (this->first + this->second) > (other.first + other.second);
    }
};


int main() {
    Pair<double, int> p1(3.5, 14);
    Pair<double, int> p2(2.1, 7);
    cout << "p1: " << p1.first << ", " << p1.second << endl;
    cout << "p2: " << p2.first << ", " << p2.second << endl;

    if (p1 > p2)
        cout << "p1 er størst" << endl;
    else
        cout << "p2 er størst" << endl;

    auto sum = p1 + p2;
    cout << "Sum: " << sum.first << ", " << sum.second << endl;
}

/* Utskrift:
p1: 3.5, 14
p2: 2.1, 7
p1 er størst
Sum: 5.6, 21
*/
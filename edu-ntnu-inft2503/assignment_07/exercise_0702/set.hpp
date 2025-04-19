#pragma once
#include <vector>
#include <iostream>

using namespace std;

class Set {
public:
    // Constructor: Lager en tom mengde.
    Set() = default;

    // Constructor: Lager en mengde med tall.
    Set(const std::vector<int> &elements);

    // Legg til et element i mengden, hvis det ikke allerede finnes.
    void add(int element);

    // Union: Overload for operator+ for å lage unionen av to mengder.
    Set operator+(const Set &other) const;

    // Tilordning: Setter en mengde lik en annen.
    Set &operator=(const Set &other);

    // Overload for operator<< for å skrive ut mengden.
    friend std::ostream &operator<<(std::ostream &out, const Set &set);

private:
    std::vector<int> elements_;

    // Hjelpefunksjon for å sjekke om et element finnes i mengden.
    bool contains(int element) const;
};

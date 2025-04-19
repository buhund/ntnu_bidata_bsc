#pragma once

#include <ostream>

class Fraction {
public:
    Fraction();
    Fraction(int numerator, int denominator);

    void set(int numerator_, int denominator_ = 1);

    // Overload for Fraction minus int
    Fraction operator-(int value) const;

    // Overload for Fraction minus Fraction
    Fraction operator-(const Fraction &other) const;
    Fraction &operator-=(const Fraction &other);
    Fraction operator-() const;

    // Friend function for int minus Fraction (non-member function)
    friend Fraction operator-(int value, const Fraction &fraction);

    friend std::ostream &operator<<(std::ostream &out, const Fraction &fraction);

private:
    int numerator;  // Data member
    int denominator;  // Data member
    void reduce();
    int compare(const Fraction &other) const;
};

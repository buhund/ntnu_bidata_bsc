#include "fraction.hpp"
#include <iostream>

Fraction::Fraction() : numerator(0), denominator(1) {}

Fraction::Fraction(int numerator_, int denominator_) {
    set(numerator_, denominator_);
}

void Fraction::set(int numerator_, int denominator_) {
    if (denominator_ != 0) {
        numerator = numerator_;
        denominator = denominator_;
        reduce();
    } else {
        throw "nevneren ble null. ";
    }
}

Fraction Fraction::operator-(const Fraction &other) const {
    Fraction fraction = *this;
    fraction -= other;
    return fraction;
}

Fraction &Fraction::operator-=(const Fraction &other) {
    set(numerator * other.denominator - denominator * other.numerator,
        denominator * other.denominator);
    return *this;
}

Fraction Fraction::operator-(int value) const {
    // Subtract an integer from a fraction
    Fraction fraction;
    fraction.set(numerator - value * denominator, denominator);
    return fraction;
}

// Non-member function: int minus Fraction
Fraction operator-(int value, const Fraction &fraction) {
    Fraction result;
    result.set(value * fraction.denominator - fraction.numerator, fraction.denominator);
    return result;
}

Fraction Fraction::operator-() const {
    Fraction fraction;
    fraction.numerator = -numerator;
    fraction.denominator = denominator;
    return fraction;
}

void Fraction::reduce() {
    if (denominator < 0) {
        numerator = -numerator;
        denominator = -denominator;
    }
    int a = numerator;
    int b = denominator;
    int c;
    if (a < 0)
        a = -a;

    while (b > 0) {
        c = a % b;
        a = b;
        b = c;
    }
    numerator /= a;
    denominator /= a;
}

int Fraction::compare(const Fraction &other) const {
    Fraction fraction = *this - other;
    if (fraction.numerator > 0)
        return 1;
    else if (fraction.numerator == 0)
        return 0;
    else
        return -1;
}

std::ostream &operator<<(std::ostream &out, const Fraction &fraction) {
    out << fraction.numerator << "/" << fraction.denominator;
    return out;
}

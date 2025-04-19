#include "fraction.hpp"
#include <iostream>

int main() {
    Fraction fraction1(3, 4);  // 3/4
    Fraction fraction2(2, 3);  // 2/3

    // Demonstration of Fraction minus int
    Fraction result1 = fraction1 - 5;
    std::cout << "fraction1 - 5 = " << result1 << std::endl;

    // Demonstration of int minus Fraction
    Fraction result2 = 5 - fraction2;
    std::cout << "5 - fraction2 = " << result2 << std::endl;

    return 0;
}

#include <iostream>

using namespace std;

const double pi = 3.141592;

class Circle
{
public:
    explicit Circle(double radius_);
    [[nodiscard]] double get_area() const;
    [[nodiscard]] double get_circumference() const;
private:
    double radius;
};

Circle::Circle(double radius_) : radius(radius_) {}

double Circle::get_area() const
{
    return pi * radius * radius;
}

double Circle::get_circumference() const
{
    return 2.0 * pi * radius;
}


// ----- Oppgave 2 ----- //

int main() {
    Circle circle(5);

    double area = circle.get_area();
    cout << "Arealet er " << area << " måleenheter av ukjent type." << endl;

    double circumference = circle.get_circumference();
    cout << "Omkretsen er " << circumference << " måleenheter av ukjent type." << endl;
}
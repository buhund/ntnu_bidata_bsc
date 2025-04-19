// Endringene og beskrivelse fra "feil" kode står i kommentarene over/rundt/mellom/her/og/der koden.

const double pi = 3.141592;

class Circle
{
    public:
        // Stor C i konstrukløren for riktig initialisering
        explicit Circle(double radius_);

        // Arealet må være double siden resten av tallene er double
        [[nodiscard]] double get_area() const;

        // Funksjonen manglet returtype double i implementasjonen
        [[nodiscard]] double get_circumference() const;
        // explicit og [[nodiscard]] ble foreslått av CLion.

    private: // pritave: var plassert feil.
        double radius;
};

// ==> Implementasjon av klassen Circle

// Bruk av public skal ikke være i definisjon av funksjon utenfor en klasse.
// Brukes kun i klassedeklarasjoner.
// Initialiseringen av radius var i feil rekkefølge. Medlemsvariabelen radius initialiseres med verdi av parameter radius_, ikke motsatt.
Circle::Circle(double radius_) : radius(radius_) {}

// Må være double
double Circle::get_area() const
{
    return pi * radius * radius;
}

// Manglet returtype
double Circle::get_circumference() const
{
    // Her var ikke circumference initialisert.
    // Men vi kan like greit bare utføre regnestykket i returen, i stedet for å
    // lage en ny variabel, sette den lik regnestykket og så returnere variabelen.
    return 2.0 * pi * radius;
}


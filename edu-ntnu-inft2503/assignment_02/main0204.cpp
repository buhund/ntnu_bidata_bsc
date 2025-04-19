  # include <iostream>
  using namespace std;

  int main()
  {

     int a = 5; // Riktig. Nothing to see here.
     cout << "a = " << a << endl;

     /**
      * int &b;
      * Feil! &b betyr "adressen til b", eller "b er en referanse til [noe]".
      * En referanse må initiliseres når den blir deklarert.
      * Løsning: b må ha en referanse til en eksisterende variabel, f.eks a.
      */
     int b = a;
     cout << "b = a = " << b << endl;
     cout << endl;


     /**
      * int *c;
      * Ingen problem. c peker til int.
      */
     int *c;
     cout << "*c = " << *c << endl;
     cout << endl;


     /**
      * c = &b;
      * Feil! b er en referanse, og vi kan ikke hente ut adressen til en referanse slik.
      */
     cout << "c = " << c << endl;
     c = &a;
     cout << "c = &a = " << &a << endl;
     cout << endl;


     /**
      * *a = *b + *c;
      * Feil! a er en int, ikke en peker. Den kan ikke bli deferert. b, som nå
      * peker på a er også dermed en int, så samme gjelder for b.
      * Løsning: Fjerne pekernotasjon * foran a.
      */
     cout << "a = " << a << endl;
     cout << "b = " << b << endl;
     cout << "*c = " << *c << endl;
     a = b + *c;
     cout << "a = b + *c" << endl;
     cout << "a = " << b << " + " << *c << " = " << a << endl;
     cout << endl;
     /**
      * Her vil vi få noe rart pga rekkefølgen på utskrift og utregning!
      * Utskriften viser a = 5 + 10 = 10
      * a blir her satt til summen av b og *c, dvs b=5 + c=5.
      * c peker på a, som etter tilordningen = 10. Dermed vil utskriften
      * vise 5 + 10 = 10, fordi den skriver ut verdiene etter at de er oppdatert.
      * Skriver vi ut verdiene FØR de oppdateres ser vi at *c = 5.
      */


     /**
      * &b = 2;
      * Feil! Vi kan ikke tilordne en verdi direkte til en referanse.
      * Løsning: Sett b = 2. Dette vil endre verdien til a, fordi vi
      * satte b til å være en referanse til a.
      * b => a = 2
      */
     b = 2;
     cout << "b = " << b << endl;
  }

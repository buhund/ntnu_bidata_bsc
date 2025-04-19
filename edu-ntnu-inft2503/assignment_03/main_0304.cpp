#include <iostream>
#include <string>
#include <algorithm>
#include <cctype>

using namespace std;

string to_lowercase(const string& str) {
    string lower_str = str;
    transform(lower_str.begin(), lower_str.end(), lower_str.begin(), ::tolower);
    return lower_str;
}

int main()
{
    string word1, word2, word3;
    cout << "Skriv inn tre ord: ";
    cin >> word1 >> word2 >> word3;

    string sentence = word1 + " " + word2 + " " + word3 + ".";
    cout << sentence << endl;
    cout << "Lengden av ordet " + word1 + " er " << word1.length() << endl;
    cout << "Lengden av ordet " + word2 + " er " <<  word2.length() << endl;
    cout << "Lengden av ordet " + word3 + " er " <<  word3.length() << endl;
    cout << "Lengden av setningen " + sentence + ", inkludert mellomrom og punktum, er " << sentence.length() << endl;

    string sentence2 = sentence;

    if (sentence2.length() > 12)
    {
        // Nummer 10-12 eller indeks 10-12? Usikker, siden det spesifiseres at "tegnene nummereres fra og med 0. Hva er da 10 og 12?
        sentence2[10] = 'x';
        sentence2[11] = 'x';
        sentence2[12] = 'x';
    }

    cout << "Original setning: " << sentence << endl;
    cout << "Modifisert setning: " << sentence2 << endl;

    string sentence_start;
    if (sentence.length() >= 5)
    {
        sentence_start = sentence.substr(0, 5);
    }
    cout << "5 første tegn: " << sentence_start << endl;

   to_lowercase(sentence);
    if (sentence.find("hallo") != string::npos)
    {
        cout << "Setningen inneholder ordet 'hallo'." << endl;
    } else
    {
        cout << "Setningen inneholder ikke ordet 'hallo'." << endl;
    }


    size_t pos = sentence.find("er");
    while (pos != string::npos)
    {
        cout << "StringLocationizer2000-X har funnet en forekomst av strengen 'er' på tegnposisjon: " << pos << endl;
        pos = sentence.find("er", pos + 1);
        // Single quotes sjekker etter 'karakter', mens doble sjekker etter "string". Prøv å huske det nå da!
    }

    return 0;
}


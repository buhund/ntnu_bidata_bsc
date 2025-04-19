#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int main() {
    vector<int> v1 = {3, 3, 12, 14, 17, 25, 30};
    // Lager en kopi av opprinnelig tabell for å endre på.
    vector<int> v1_copy(v1.size());

    // Erstatter alle oddetall med 100
    replace_copy_if(v1.begin(), v1.end(), v1_copy.begin(),[](int x) { return x % 2 != 0; }, 100);

    cout << "v1 før replace_copy_if: ";
    for (int n : v1) {
        cout << n << " ";
    }
    cout << endl;

    cout << "v1 etter replace_copy_if: ";
    for (int n : v1_copy) {
        cout << n << " ";
    }
    cout << endl;

    return 0;
}
#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

int main() {
    vector<int> v1 = {3, 3, 12, 14, 17, 25, 30};

    // Finner det første elementet som er større enn 15 og returnerer det.
    auto it = find_if(v1.begin(), v1.end(), [&](int x) { return x > 15; });

    // Skriver ut hvis det finnes et element x > 15, før vi kommer til endes i tabellen.
    if (it != v1.end()) {
        cout << "Første element i v1 som er større enn 15 er: " << *it << endl;
    // Hvis vi kommer til endes, er det ikke noen elementer større enn 15.
    } else {
        cout << "Ingen elementer i v1 er større enn 15." << endl;
    }

    return 0;
}

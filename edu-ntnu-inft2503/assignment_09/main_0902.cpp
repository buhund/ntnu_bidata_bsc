#include <iostream>
#include <vector>
#include <algorithm>
#include <cmath>

using namespace std;

int main() {
    vector<int> v1 = {3, 3, 12, 14, 17, 25, 30};
    vector<int> v2 = {2, 3, 12, 14, 24};

    // Sjekker de første 5 elementer i v1 og hele v2.
    bool is_equal_5 = equal(v1.begin(), v1.begin() + 5, v2.begin(), v2.end(),
                                 [](int a, int b) { return abs(a - b) <= 2; });
    cout << "Intervall [v1.begin(), v1.begin() + 5> og v2 er "
              << (is_equal_5 ? "omtrent like" : "ikke like") << endl;


    // Litt usikker på akkurat hva jeg skal sjekke her, siden det ikke står hvor mye av v2 jeg skal sammenligne med.
    // Sjekker dermed de første 4 elementer i v1 og første 4 i v2, dvs. sjekker de 4 første elementene i hver.
    bool is_equal_4 = equal(v1.begin(), v1.begin() + 4, v2.begin(), v2.begin() + 4,
                                 [](int a, int b) { return abs(a - b) <= 2; });
    cout << "Intervall [v1.begin(), v1.begin() + 4> og tilsvarende i v2 er "
              << (is_equal_4 ? "omtrent like" : "ikke like") << endl;

    return 0;
}

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int main()
{
    // Del 1
    vector<double> numbers;

    numbers.emplace_back(1.2);
    numbers.emplace_back(2.3);
    numbers.emplace_back(3.4);
    numbers.emplace_back(4.5);
    numbers.emplace_back(5.6);

    cout << "--- Part 1 ---" << endl;
    cout << "Original vector:" << endl;
    for (double number : numbers)
    {
        cout << number << endl;
    }
    cout << endl;

    // Del 2
    cout << "--- Part 2 ---" << endl;
    cout << "Front vector element: " << numbers.front() << endl;
    cout << "Back vector element: " << numbers.back() << endl;
    cout << endl;

    // Del 3
    auto it = numbers.begin();
    double emplace_value = 9.9;
    cout << "--- Part 3 ---" << endl;
    cout << "Inserting element " << emplace_value << " into the vector, after the first element."  << endl;
    numbers.emplace(it + 1, emplace_value);

    cout << "Modified vektor: " << endl;
    for (double number : numbers)
    {
        cout << number << endl;
    }
    cout << "First element: " << numbers.front() << endl;
    cout << endl;

    // Del 4
    cout << "--- Part 4 ---" << endl;
    double search_value = 3.4;
    cout << "Search value: " << search_value << endl;

    auto it2 = find(numbers.begin(), numbers.end(), search_value);
    if (it2 != numbers.end()) {
        cout << "Value " << *it2 << " was found in the vector." << endl;
    } else {
        cout << "Value " << search_value << " was *not* found in the vector." << endl;
    }

    return 0;
}

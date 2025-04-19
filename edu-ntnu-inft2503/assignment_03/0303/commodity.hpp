#ifndef COMMODITY_HPP
#define COMMODITY_HPP
#include <string>

using namespace std;

const double SALES_TAX = 0.25;

class Commodity {
    public:
        // Constructor
        Commodity(const std::string& name_, int id_, double price_);

        // Nova Scotia Duck Tolling Retriever
        string get_name() const;
        int get_id() const;
        double get_price() const;
        double get_price(double quantity) const;
        double get_price_with_sales_tax(double quantity) const;

        // Irish, English and Gordon
        void set_price(double new_price);

    private:
        string name;
        int id;
        double price;
};


#endif //COMMODITY_HPP

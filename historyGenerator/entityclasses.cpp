#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <stdlib.h>
#include <cmath>
#include <sstream>
using namespace std;


class Product {
public: 
    string productName;
    int productID;
    float orderPrice;
    float sellPrice;
    float currentStock;
    float desiredStock;
    float quantitySoldYTD;

    Product() {}
    Product(vector<string> line) {
        productName = line[0];
        productID = stoi(line[1]);
        orderPrice = stof(line[2]);
        sellPrice = stof(line[3]);
        currentStock = stof(line[4]);
        desiredStock = stof(line[5]);
        quantitySoldYTD = stof(line[6]);
    }

    void updateQuantity(float quantitySold) {
        currentStock -= quantitySold;
        quantitySoldYTD += quantitySold;
    }

    string generateLine() {
        string line = "";
        line += productName;
        line += "," + to_string(productID);
        line += "," + to_string(orderPrice);
        line += "," + to_string(sellPrice);
        line += "," + to_string(currentStock);
        line += "," + to_string(desiredStock);
        line += "," + to_string(quantitySoldYTD) + "\n";
        return line;
    }
};

class SaleLineItem {
public:
    int saleLineID;
    int saleInvoiceID;
    int productID;
    float quantitySold;

    SaleLineItem() {saleLineID = 0; saleInvoiceID = 0; productID = 0; quantitySold = 0.0;}
    SaleLineItem(int lineID, int invoiceID, int prodID, float quantity) {
        saleLineID = lineID;
        saleInvoiceID = invoiceID;
        productID = prodID;
        quantitySold = quantity;
        return;
    }

    string generateLine() {
        string line = "";
        line += to_string(saleLineID);
        line += "," + to_string(saleInvoiceID);
        line += "," + to_string(productID);
        line += "," + to_string(quantitySold) + "\n";
        return line;
    }
};

class SaleInvoice {
public:
    int saleInvoiceID;
    string saleDate;
    float saleTotalCost;
    int employeeID;

    SaleInvoice() {saleInvoiceID = 0; saleDate = ""; saleTotalCost = 0.0; employeeID = 0;}
    SaleInvoice(int id, string date, int employee) : saleInvoiceID{id}, saleDate{date}, employeeID{employee} {saleTotalCost = 0.0;}
    
    //pass line item and products table, update total cost
    void updateTotalCost(SaleLineItem lineItem, vector<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            if (products[i].productID == lineItem.productID) {
                saleTotalCost += products[i].sellPrice * lineItem.quantitySold;
                break;
            }
        }
    }

    string generateLine() {
        string line = "";
        line += to_string(saleInvoiceID);
        line += "," + saleDate;
        line += "," + to_string(saleTotalCost);
        line += "," + to_string(employeeID) + "\n";
        return line;
    }
};

class Employee {
public:
    int employeeID;
    string employeeName;
    bool isManager;

    Employee() {}
    Employee(int id, string name, bool manager) : employeeID{id}, employeeName{name}, isManager{manager} {}

    string generateLine() {
        string line = "";
        line += to_string(employeeID);
        line += "," + employeeName;
        line += "," + to_string(isManager) + "\n";
        return line;
    }
};

class OrderLineItem {
public:
    int orderLineID;
    int orderInvoiceID;
    int productID;
    float quantityOrdered;

    OrderLineItem() {}
    OrderLineItem(int lineID, int invoiceID, int prodID, float quantity) {
        orderLineID = lineID;
        orderInvoiceID = invoiceID;
        productID = prodID;
        quantityOrdered = quantity;
    }

    string generateLine() {
        string line = "";
        line += to_string(orderLineID);
        line += "," + to_string(orderInvoiceID);
        line += "," + to_string(productID);
        line += "," + to_string(quantityOrdered) + "\n";
        return line;
    }
};

class OrderInvoice {
public: 
    int orderInvoiceID;
    string orderDate;
    float orderTotalCost;
    int distributorID;
    int employeeID;

    OrderInvoice(){}
    OrderInvoice(int invoiceID, string date, int distributor, int employee) {
        orderInvoiceID = invoiceID;
        orderDate = date;
        distributorID = distributor;
        employeeID = employee;
        orderTotalCost = 0.0;
    }

    string generateLine() {
        string line = "";
        line += to_string(orderInvoiceID);
        line += "," + orderDate;
        line += "," + to_string(orderTotalCost);
        line += "," + to_string(distributorID);
        line += "," + to_string(employeeID) + "\n";
        return line;
    }
};


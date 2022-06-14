#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <stdlib.h>
#include <cmath>
#include <sstream>
#include "entityclasses.cpp"
using namespace std;

/*  
    These history generator files are pretty poorly written,
    mainly because I only intended to use them once to generate the csvs
    and then not use them again.
*/

// Reused from one of my 313 assignments
vector<string> split(string s, char delim) {
    //splits s into substrings along delim
    //returns substrings in vector
    vector<string> ret;
    string temp;  // store substrings
    int sq = 0;
    int dq = 0;
    for (int i = 0; i < s.length(); i++) {
        char c = s[i];
        
        if (c == '\'') {sq++;}
        else if (c == '\"') {dq++;}
        else if (c == delim && sq%2 == 0 && dq%2 == 0) {
            //cout << c << "/" << dq << "/" << sq << endl;
            temp = s.substr(0,i);
            s = s.substr(i+1);
            i=0;
            ret.push_back(temp);
        }
    }
    ret.push_back(s);
    return ret;
}

/* Read in a .csv file, store values in a matrix*/
vector<vector<string>> fileToMatrix(string fileName) {
    vector<vector<string>> matrix;
    
    fstream infile;
    infile.open(fileName);

    string line;
    while (getline(infile, line)) {
        matrix.push_back(split(line, ','));
    }
    infile.close();
    return matrix;
}

vector<string> getColumn(vector<vector<string>> matrix, int columnIndex) {
    vector<string> column;
    for (int i = 0; i < matrix.size(); i++) {
        column.push_back(matrix[i][columnIndex]);
    }
    return column;
}

int getProductIndex (vector<Product> products, int id) {
    for (int i = 0; i < products.size(); i++) {
        if (products[i].productID == id) {return i;}
    }
}


int main() {

    srand(time(NULL));

    int numDays = 21;
    int maxSales = 15;
    int maxLines = 8;
    int numEmployees = 6;
    int maxSold = 6;
    int numDistributors = 5;

    bool useDefaults = true;
    cout << "Enter 1 to use default parameters, 0 to set parameters: ";
    cin >> useDefaults;
    if (!useDefaults) {
        cout << "Enter the amount of days to enter history for: ";
        cin >> numDays;
        cout << "Enter the maximum number of sales per day: ";
        cin >> maxSales;
        cout << "Enter the maximum number of line items per sale: ";
        cin >> maxLines;
        cout << "Enter the maximum quantity per sale: ";
        cin >> maxSold;
        cout << "Enter the number of employees to generate:";
        cin >> numEmployees;
        cout << "Enter the number of distributors:";
        cin >> numDistributors;
    }

    vector<vector<string>> productsMatrix = fileToMatrix("productsDefault.csv");
    vector<Product> products;
    for (int i = 0; i < productsMatrix.size(); i ++) {
        products.push_back(Product(productsMatrix[i]));
    }

    vector<string> firstNames = {"Ethan", "Cyrus", "Preston", "Rob", "John", "Saul", "Carlos", "Alex", "Anthony", "Walter", "James", "Michael", "Jesse", "Mike", "Gus"};
    vector<string> lastNames = {"Martinez", "Garcia", "Lopez", "Goodman", "Egbert", "Rodriguez", "White", "Jackson", "McGill", "Heisenberg", "Pinkman", "Fring"};
    vector<Employee> employees;
    bool generatedManager = false;
    for (int i = 0; i < numEmployees; i++) {
        int employeeID = i+1;
        string employeeName = firstNames[rand()%firstNames.size()] + " " + lastNames[rand()%lastNames.size()];
        bool isManager = (rand()%3 == 1);
        if (isManager) {generatedManager = true;}
        cout << "manager:" << isManager << endl;
        employees.push_back(Employee(employeeID, employeeName, isManager));
    }
    if (!generatedManager) {employees[0].isManager = true;}

    int saleInvoiceID = 1;
    int saleLineID = 1;
    int orderInvoiceID = 1;
    int orderLineID = 1;
    vector<SaleLineItem> saleLineItems;
    vector<SaleInvoice> saleInvoices;
    vector<OrderInvoice> orders;
    vector<OrderLineItem> orderLineItems;
    
    bool festivalDays[numDays] = {false};
    for (int i = 0; i < 2; i++) {
        int x = rand()%numDays;
        festivalDays[x] = true;
        cout << "Day " << x << " is a festival day.\n";
    }

    for (int i = 0; i < numDays; i++) {
        int numSales = rand() % maxSales + 1;
        if (festivalDays[i]) {numSales *= 5;}
        string date = "5/" + to_string(i+1) + "/22";

        for (int j = 0; j < numSales; j++) {
            int numLines = rand() % maxLines + 1;
            int employeeID = rand() % numEmployees + 1;
            SaleInvoice invoice(saleInvoiceID, date, employeeID);

            for (int k = 0; k < numLines; k++) {
                int index = rand() % products.size();
                int productID = products[index].productID;
                float  quantitySold = static_cast <float> (rand()) / (static_cast <float> (RAND_MAX/maxSold)) + 1;
                SaleLineItem lineItem(saleLineID, saleInvoiceID, productID, quantitySold);

                invoice.updateTotalCost(lineItem, products);
                products[index].updateQuantity(quantitySold);
                saleLineItems.push_back(lineItem);
                saleLineID++;
            }

            saleInvoices.push_back(invoice);
            saleInvoiceID++;
        }
    
        
        bool makeOrder = false;
        int empID = rand() % numEmployees + 1;
        while (!(employees[empID-1].isManager)) {empID = rand() % numEmployees;}
        OrderInvoice invoice(orderInvoiceID, date, rand()%numDistributors+1, empID);

        for (int i = 0; i < products.size(); i++) {
            if (products[i].currentStock <= products[i].desiredStock/2) {
                makeOrder = true;
                float orderQuantity = products[i].desiredStock - products[i].currentStock;
                OrderLineItem lineItem(orderLineID, orderInvoiceID, products[i].productID, orderQuantity);
                orderLineItems.push_back(lineItem);
                invoice.orderTotalCost += orderQuantity * products[i].orderPrice;
                products[i].currentStock += orderQuantity;
                orderLineID++;
            }
        }

        if (makeOrder) {
            orderInvoiceID++;
            orders.push_back(invoice);
        }
    }



    fstream saleInvoicesFile;
    fstream saleLineItemsFile;
    fstream orderInvoicesFile;
    fstream orderLineItemsFile;
    fstream employeesFile;
    fstream productsFile;

    saleInvoicesFile.open("saleInvoiceHistory.csv", ios::out | ios::trunc);
    for (int i = 0; i < saleInvoices.size(); i++) {
        saleInvoicesFile << saleInvoices[i].generateLine();
    }

    saleLineItemsFile.open("saleLineItems.csv", ios::out | ios::trunc);
    for (int i = 0; i < saleLineItems.size(); i++) {
        saleLineItemsFile << saleLineItems[i].generateLine();
    }

    orderInvoicesFile.open("orderInvoiceHistory.csv", ios::out | ios::trunc);
    for (int i = 0; i < orders.size(); i++) {
        orderInvoicesFile << orders[i].generateLine();
    }

    orderLineItemsFile.open("orderLineItems.csv", ios::out | ios::trunc);
    for (int i = 0; i < orderLineItems.size(); i++) {
        orderLineItemsFile << orderLineItems[i].generateLine();
    }

    employeesFile.open("employees.csv", ios::out | ios::trunc);
    for (int i = 0; i < employees.size(); i++) {
        employeesFile << employees[i].generateLine();
    }

    productsFile.open("products.csv", ios::out | ios::trunc);
    for (int i = 0; i < products.size(); i++) {
        productsFile << products[i].generateLine();
    }

}
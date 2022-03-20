package com.livexp.tests;


import com.livexp.AbstractTests;
import com.livexp.assertions.CustomersAssert;
import com.livexp.model.Customer;
import com.livexp.pages.TrySqlPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

@Epic("Try sql page")
public class TrySqlTests extends AbstractTests {

    private TrySqlPage trySqlPage;

    @BeforeEach
    public void beforeEach() {
        open("/trysql.asp?filename=trysql_select_all");
        trySqlPage = new TrySqlPage();
        trySqlPage.restoreDatabase();
    }

    @Test
    @Description("Вывести все строки таблицы *Customers* и убедиться, что запись с ContactName равной ‘СGiovanni Rovelli’ имеет Address = ‘Via Ludovico il Moro 22’")
    public void GiovanniRovelliAddressTest() {
        trySqlPage.executeQuery("SELECT * FROM Customers;");

        var results = trySqlPage.getResults(Customer.class);

        var expectedCustomer = new Customer();
        expectedCustomer.setContactName("Giovanni Rovelli");
        expectedCustomer.setAddress("Via Ludovico il Moro 22");
        CustomersAssert.assertThat(results).contains(expectedCustomer);
    }

    @Test
    @Description("Вывести только те строки таблицы *Customers*, где city=‘London’. Проверить, что в таблице ровно 6 записей.")
    public void CityOfLondonTest() {
        trySqlPage.executeQuery("SELECT * FROM Customers WHERE City=\\'London\\'");

        var results = trySqlPage.getResults(Customer.class);

        CustomersAssert.assertThat(results).hasSize(6);
    }

    @Test
    @Description("Добавить новую запись в таблицу *Customers* и проверить, что эта запись добавилась.")
    public void AddNewCustomerTest() {
        var expectedCustomer = new Customer();
        expectedCustomer.setName("Anton Svetlakov");
        expectedCustomer.setContactName("Anton");
        expectedCustomer.setAddress("Frunze 123");
        expectedCustomer.setCity("Novosibirsk");
        expectedCustomer.setCountry("Russia");
        expectedCustomer.setPostalCode("630003");

        trySqlPage.executeQuery("INSERT INTO Customers VALUES (123, \\'Anton Svetlakov\\', \\'Anton\\', \\'Frunze 123\\', \\'Novosibirsk\\', \\'630003\\', \\'Russia\\')");

        trySqlPage.waitResultMessage("You have made changes to the database. Rows affected: 1");

        trySqlPage.executeQuery("SELECT * FROM Customers WHERE CustomerID=123");

        var results = trySqlPage.getResults(Customer.class);

        CustomersAssert.assertThat(results).hasSize(1).contains(expectedCustomer);
    }

    @Test
    @Description("Обновить все поля (кроме CustomerID) в любой записи таблицы *Customers*и проверить, что изменения записались в базу.")
    public void UpdateCustomerTest() {
        var expectedCustomer = new Customer();
        expectedCustomer.setName("Anton Svetlakov");
        expectedCustomer.setContactName("Anton");
        expectedCustomer.setAddress("Frunze 123");
        expectedCustomer.setCity("Novosibirsk");
        expectedCustomer.setCountry("Russia");
        expectedCustomer.setPostalCode("630003");

        trySqlPage.executeQuery("UPDATE Customers SET CustomerName = \\'Anton Svetlakov\\', ContactName=\\'Anton\\', Address=\\'Frunze 123\\', City=\\'Novosibirsk\\', PostalCode=\\'630003\\', Country=\\'Russia\\' WHERE CustomerID = 3");

        trySqlPage.waitResultMessage("You have made changes to the database. Rows affected: 1");

        trySqlPage.executeQuery("SELECT * FROM Customers WHERE CustomerID=3");

        var results = trySqlPage.getResults(Customer.class);

        CustomersAssert.assertThat(results).hasSize(1).contains(expectedCustomer);
    }

    @Test
    @Description("Проверка пустого результата вывода")
    public void CheckEmptyResultTest() {
        trySqlPage.executeQuery("SELECT * FROM Customers WHERE Country=\\'Germany1\\'");

        trySqlPage.waitResultMessage("No result.");
    }
}

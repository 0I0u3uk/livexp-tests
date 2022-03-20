package com.livexp.model;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class Customer implements TableField {
    private Integer id;
    private String name;
    private String contactName;
    private String address;
    private String city;
    private String postalCode;
    private String country;

    public void parseToModel(Map<String, String> data) {
        for (var field : data.entrySet()) {
            switch (field.getKey().toLowerCase(Locale.ROOT)) {
                case "customerid":
                    id = Integer.parseInt(field.getValue());
                    break;
                case "customername":
                    name = field.getValue();
                    break;
                case "contactname":
                    contactName = field.getValue();
                    break;
                case "address":
                    address = field.getValue();
                    break;
                case "city":
                    city = field.getValue();
                    break;
                case "postalcode":
                    postalCode = field.getValue();
                    break;
                case "country":
                    country = field.getValue();
                    break;
                default:
                    throw new IllegalArgumentException(String.format("Can't parse %s field", field.getKey()));
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return customer.id == null ? true : Objects.equals(id, customer.id)
                && customer.name == null ? true :Objects.equals(name, customer.name)
                && customer.contactName == null ? true :Objects.equals(contactName, customer.contactName)
                && customer.address == null ? true :Objects.equals(address, customer.address)
                && customer.city == null ? true :Objects.equals(city, customer.city)
                && customer.postalCode == null ? true :Objects.equals(postalCode, customer.postalCode)
                && customer.country == null ? true :Objects.equals(country, customer.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, contactName, address, city, postalCode, country);
    }

    public Integer getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getContactName() {
        return contactName;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}

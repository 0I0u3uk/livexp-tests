package com.livexp.assertions;

import com.livexp.model.Customer;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import java.util.List;

public class CustomersAssert extends AbstractAssert<CustomersAssert, List<Customer>> {
    public CustomersAssert(List<Customer> actual) {
        super(actual, CustomersAssert.class);
    }

    public static CustomersAssert assertThat(List<Customer> actual) {
        return new CustomersAssert(actual);
    }

    public CustomersAssert hasSize(Integer size) {
        Assertions.assertThat(actual).as("Expected customer count doesn't equal actual").hasSize(size);
        return this;
    }

    public CustomersAssert contains(Customer expected) {
        Assertions.assertThat(actual).as("Expected customer doesn't exist in sequence").contains(expected);
        return this;
    }

    public CustomersAssert contains(Customer expected, Integer expectedCount) {
        var actualCount = 0;
        for (var customer : actual) {
            if (customer.equals(expected)) actualCount++;
        }
        Assertions.assertThat(actualCount).as("Expected customer doesn't exist in sequence in the right amount").isEqualTo(expectedCount);
        return this;
    }
}

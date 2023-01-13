package com.cbe.card.customer;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.*;
/* import static org.junit.jupiter.api.Assertions.*; */

@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    void tearDown(){
        customerRepository.deleteAll();
    }
    @Test
    @DisplayName("Customer should exist by account number ")
    void itShouldCheckWhenCustomerExistsByAccountNumber() {
        String accountNumber = "1000100131289";
        // Given
        Customer customer = new Customer("Bosena", "Alem",accountNumber, "2022:12:12", true
        );
        customerRepository.save(customer);
        // When
        boolean expected = customerRepository.existsByAccountNumber(accountNumber);
        // Then
        assertThat(expected).isTrue();
    }
    @Test
    @DisplayName("Customer should not exist by account number ")
    void itShouldCheckWhenCustomerDoesNotExistsByAccountNumber() {
        String accountNumber = "1000100131289";
        // Given
        Customer customer = new Customer("Bosena", "Alem", "1000100131290", "2022:12:12", true
        );
        customerRepository.save(customer);
        // When
        boolean expected = customerRepository.existsByAccountNumber(accountNumber);
        // Then
        assertThat(expected).isFalse();
    }
}
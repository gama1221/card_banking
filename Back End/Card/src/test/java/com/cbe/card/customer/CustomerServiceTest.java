package com.cbe.card.customer;

import com.cbe.card.exception.BadRequestException;
import com.cbe.card.exception.CustomerNotFoundException;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {CustomerService.class})
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerService(customerRepository);
    }

    @Disabled
    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Should verify that can fetch all customers")
    void getAllCustomers() {
        // When
        customerService.getAllCustomers();
        // Then
        verify(customerRepository).findAll();
    }

    /**
     * Method under test: {@link CustomerService#getAllCustomers()}
     */
    @Test
    void testGetAllCustomers() {
        ArrayList<Customer> customerList = new ArrayList<>();
        when(customerRepository.findAll()).thenReturn(customerList);
        List<Customer> actualAllCustomers = customerService.getAllCustomers();
        assertSame(customerList, actualAllCustomers);
        assertTrue(actualAllCustomers.isEmpty());
        verify(customerRepository).findAll();
    }

    /**
     * Method under test: {@link CustomerService#getAllCustomers()}
     */
    @Test
    void testGetAllCustomers2() {
        when(customerRepository.findAll()).thenThrow(new BadRequestException("An error occurred"));
        assertThrows(BadRequestException.class, () -> customerService.getAllCustomers());
        verify(customerRepository).findAll();
    }

    @Test
    @DisplayName("Can be able to add Customer to table")
    void canAddCustomer() {
        String accountNumber = "100100131289";
        // Given
        Customer customer = new Customer("Tsigereda", "Abeba", accountNumber, "2022:12:02", true
        );
        // When
        customerService.addCustomer(customer);
        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(customerArgumentCaptor.capture());
        Customer captureCustomer = customerArgumentCaptor.getValue();
        assertThat(captureCustomer).isEqualTo(customer);
    }

    /**
     * Method under test: {@link CustomerService#addCustomer(Customer)}
     */
    @Test
    void testAddCustomer() {
        Customer customer = new Customer();
        customer.setAccountNumber("42");
        customer.setCratedDate("2020-03-01");
        customer.setFirstName("Jane");
        customer.setId(1);
        customer.setLastName("Doe");
        customer.setStatus(true);
        when(customerRepository.existsByAccountNumber((String) any())).thenReturn(true);
        when(customerRepository.save((Customer) any())).thenReturn(customer);

        Customer customer1 = new Customer();
        customer1.setAccountNumber("42");
        customer1.setCratedDate("2020-03-01");
        customer1.setFirstName("Jane");
        customer1.setId(1);
        customer1.setLastName("Doe");
        customer1.setStatus(true);
        assertThrows(BadRequestException.class, () -> customerService.addCustomer(customer1));
        verify(customerRepository).existsByAccountNumber((String) any());
    }

    /**
     * Method under test: {@link CustomerService#addCustomer(Customer)}
     */
    @Test
    void testAddCustomer2() {
        Customer customer = new Customer();
        customer.setAccountNumber("42");
        customer.setCratedDate("2020-03-01");
        customer.setFirstName("Jane");
        customer.setId(1);
        customer.setLastName("Doe");
        customer.setStatus(true);
        when(customerRepository.existsByAccountNumber((String) any())).thenReturn(false);
        when(customerRepository.save((Customer) any())).thenReturn(customer);

        Customer customer1 = new Customer();
        customer1.setAccountNumber("42");
        customer1.setCratedDate("2020-03-01");
        customer1.setFirstName("Jane");
        customer1.setId(1);
        customer1.setLastName("Doe");
        customer1.setStatus(true);
        customerService.addCustomer(customer1);
        verify(customerRepository).existsByAccountNumber((String) any());
        verify(customerRepository).save((Customer) any());
        assertEquals("42", customer1.getAccountNumber());
        assertTrue(customer1.getStatus());
        assertEquals("Doe", customer1.getLastName());
        assertEquals(1, customer1.getId().intValue());
        assertEquals("Jane", customer1.getFirstName());
        assertEquals("2020-03-01", customer1.getCratedDate());
    }

    /**
     * Method under test: {@link CustomerService#addCustomer(Customer)}
     */
    @Test
    void testAddCustomer3() {
        when(customerRepository.existsByAccountNumber((String) any()))
                .thenThrow(new CustomerNotFoundException("An error occurred"));
        when(customerRepository.save((Customer) any())).thenThrow(new CustomerNotFoundException("An error occurred"));

        Customer customer = new Customer();
        customer.setAccountNumber("42");
        customer.setCratedDate("2020-03-01");
        customer.setFirstName("Jane");
        customer.setId(1);
        customer.setLastName("Doe");
        customer.setStatus(true);
        assertThrows(CustomerNotFoundException.class, () -> customerService.addCustomer(customer));
        verify(customerRepository).existsByAccountNumber((String) any());
    }

    /**
     * Method under test: {@link CustomerService#deleteCustomer(Integer)}
     */
    @Test
    void testDeleteCustomer() {
        doNothing().when(customerRepository).deleteById((Integer) any());
        when(customerRepository.existsById((Integer) any())).thenReturn(true);
        customerService.deleteCustomer(1);
        verify(customerRepository).existsById((Integer) any());
        verify(customerRepository).deleteById((Integer) any());
    }

    /**
     * Method under test: {@link CustomerService#deleteCustomer(Integer)}
     */
    @Test
    void testDeleteCustomer2() {
        doNothing().when(customerRepository).deleteById((Integer) any());
        when(customerRepository.existsById((Integer) any())).thenReturn(true);
        customerService.deleteCustomer(1);
        verify(customerRepository).existsById((Integer) any());
        verify(customerRepository).deleteById((Integer) any());
    }

    /**
     * Method under test: {@link CustomerService#deleteCustomer(Integer)}
     */
    @Test
    void testDeleteCustomer3() {
        doThrow(new BadRequestException("An error occurred")).when(customerRepository).deleteById((Integer) any());
        when(customerRepository.existsById((Integer) any())).thenReturn(true);
        assertThrows(BadRequestException.class, () -> customerService.deleteCustomer(1));
        verify(customerRepository).existsById((Integer) any());
        verify(customerRepository).deleteById((Integer) any());
    }

    /**
     * Method under test: {@link CustomerService#deleteCustomer(Integer)}
     */
    @Test
    void testDeleteCustomer4() {
        doNothing().when(customerRepository).deleteById((Integer) any());
        when(customerRepository.existsById((Integer) any())).thenReturn(false);
        assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomer(1));
        verify(customerRepository).existsById((Integer) any());
    }
}
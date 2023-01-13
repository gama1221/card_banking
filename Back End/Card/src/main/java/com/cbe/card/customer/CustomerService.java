package com.cbe.card.customer;

import com.cbe.card.exception.BadRequestException;
import com.cbe.card.exception.CustomerNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }
    public void addCustomer(Customer customer){
        if (customerRepository.existsByAccountNumber(customer.getAccountNumber())){
            throw new BadRequestException("Account number "+customer.getAccountNumber()+" taken");
        }
        customerRepository.save(customer);
    }
    public void deleteCustomer(Integer id){
        if (!customerRepository.existsById(id)){
            throw new CustomerNotFoundException("Customer with id "+id+" does not exist");
        }
        customerRepository.deleteById(id);
    }
}

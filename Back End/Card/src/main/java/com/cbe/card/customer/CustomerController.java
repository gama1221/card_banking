package com.cbe.card.customer;

import com.cbe.card.exception.ResourceNotFoundException;
import com.cbe.card.response.message.MessageResponse;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Slf4j
//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/cbe/card/customer")
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;
    //Add
    //Edit
    //Drop
    //Fetch
    @PostMapping("/add")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customerRequest) {
        String pageCreatedDate = new SimpleDateFormat("yyyy:MM:dd").format(Calendar.getInstance().getTime());
        Customer _page = new Customer();
        if (customerRepository.existsByAccountNumber(customerRequest.getAccountNumber())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(customerRequest.getFirstName()+" Already exists "));
        }else {
            _page.setId(customerRequest.getId());
            _page.setFirstName(customerRequest.getFirstName());
            _page.setLastName(customerRequest.getLastName());
            _page.setAccountNumber(customerRequest.getAccountNumber());
            _page.setCratedDate(pageCreatedDate);
            _page.setStatus(false);
            customerRepository.save(_page);
            return ResponseEntity.ok(new MessageResponse("Done..."));
        }
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editCustomer(@PathVariable("id") Integer id,
                                       @RequestBody Customer customer) {
        Customer _customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found with id = " + id));
        _customer.setAccountNumber(customer.getAccountNumber());
        _customer.setFirstName(customer.getFirstName());
        _customer.setLastName(customer.getLastName());
        _customer.setStatus(customer.getStatus());
        customerRepository.save(_customer);
        return ResponseEntity.ok(new MessageResponse("updated..."));
    }
    @GetMapping("/all")
    public ResponseEntity<?> getCustomers() {
        List<Customer> customers = customerRepository.findAll();
        if (!customers.isEmpty()){
            return new ResponseEntity<>(customers, HttpStatus.OK);
        }else{
            return ResponseEntity.ok(new MessageResponse("Not found..."));
        }
    }
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> removeCustomer(@PathVariable("id") Integer id) {
//        customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found with id :"+id));
//        customerRepository.deleteById(id);
//        return ResponseEntity.ok(new MessageResponse("Deleted..."));
        try {
            customerRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()){
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }else{
            return ResponseEntity.ok(new MessageResponse("Not found..."));
        }
    }
}
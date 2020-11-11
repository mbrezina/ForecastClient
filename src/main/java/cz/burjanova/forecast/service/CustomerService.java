package cz.burjanova.forecast.service;

import cz.burjanova.forecast.entity.Customer;
import cz.burjanova.forecast.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer findUsersBySub(String sub) {
        return customerRepository.findBySub(sub);
    }

    public Customer saveCustomer(Customer customer) {
        customerRepository.saveAndFlush(customer);
        return customer;
    }
}

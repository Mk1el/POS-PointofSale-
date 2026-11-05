package com.Moses.Moses.POS.System.service.customer;

import com.Moses.Moses.POS.System.modal.customerModal.Customer;

import java.util.List;

public interface CustomerService {
    Customer  createCustomer(Customer customer);
    Customer updateCustomer(Long id, Customer customer) throws Exception;
    void deleteCustomer(Long id) throws Exception;
    Customer getCustomer(Long id) throws Exception;
    List<Customer> getAllCustomers() throws Exception;
    List<Customer> searchCustomers(String keyword) throws Exception;
}

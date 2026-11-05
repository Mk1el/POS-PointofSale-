package com.Moses.Moses.POS.System.controller;

import com.Moses.Moses.POS.System.modal.customerModal.Customer;
import com.Moses.Moses.POS.System.payload.response.ApiResponse;
import com.Moses.Moses.POS.System.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping()
    public ResponseEntity<Customer> create(@RequestBody Customer customer){
        return ResponseEntity.ok(customerService.createCustomer(customer));
    }
    @PostMapping("/{id}")
    public ResponseEntity<Customer> update(
            @PathVariable Long id,
            @RequestBody Customer customer
    ) throws Exception{
        return ResponseEntity.ok(customerService.updateCustomer(id, customer));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) throws Exception {
        customerService.deleteCustomer(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Customer deleted!");
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping()
    public ResponseEntity<List<Customer>> getAll()throws Exception{
        return ResponseEntity.ok(customerService.getAllCustomers());
    }
    @GetMapping("/search")
    public ResponseEntity<List<Customer>> search(@RequestParam String q)throws Exception{
        return ResponseEntity.ok(customerService.searchCustomers(q));
    }
}

package food_delivery.service;

import food_delivery.enumeration.ApplicationErrorEnum;
import food_delivery.exception.BusinessException;
import food_delivery.model.Customer;
import food_delivery.repository.CustomerRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Data
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer getCustomerById(Long customerId)
    {
        return customerRepository.findById(customerId).orElseThrow(()->new BusinessException(ApplicationErrorEnum.CUSTOMER_NOT_FOUND));
    }
}

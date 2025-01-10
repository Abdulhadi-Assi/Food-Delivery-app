package food_delivery.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import food_delivery.model.Address;
import food_delivery.repository.AddressRepository;
import food_delivery.request.AddressRequest;
import food_delivery.service.AddressService;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService{

	@Autowired
	private AddressRepository addressRepository;
	@Override
	public Address createAddress(AddressRequest address) {
		
		Address newAddress = new Address();
		
		newAddress.setCity(address.getCity());
		newAddress.setCountry(address.getCountry());
		newAddress.setPostalCode(address.getPostalCode());
		newAddress.setAddressLine1(address.getAddressLine1());
		
		
		return addressRepository.save(newAddress);
	}

	@Override
	public Address updateAddress(Address address, AddressRequest addressRequest) {
		if (addressRequest == null) {
			return address;
		}

		Optional.ofNullable(addressRequest.getCity()).ifPresent(address::setCity);
		Optional.ofNullable(addressRequest.getCountry()).ifPresent(address::setCountry);
		Optional.ofNullable(addressRequest.getPostalCode()).ifPresent(address::setPostalCode);
		Optional.ofNullable(addressRequest.getAddressLine1()).ifPresent(address::setAddressLine1);

		return addressRepository.save(address);
	}

}

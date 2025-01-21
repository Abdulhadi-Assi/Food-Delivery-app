package food_delivery.mapper;

import food_delivery.model.Address;
import food_delivery.request.AddressRequest;

public class AddressMapper {
    public static AddressRequest toAddressRequest(Address address)
    {
        if (address == null)return null;
        return new AddressRequest(address.getCity(), address.getCountry(), address.getPostalCode(), address.getAddressLine1());
    }
}

package com.example.PixelCartP4.service;

import com.example.PixelCartP4.model.Address;
import com.example.PixelCartP4.model.User;
import java.util.List;
import java.util.Optional;

public interface AddressService {

    // Address Operations
    Address saveAddress(Address address);

    Optional<Address> getAddressById(Long id);

    List<Address> getAddressesByUser(User user);

    void deleteAddress(Long id);
}

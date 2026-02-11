package com.example.PixelCartP4.service;

import com.example.PixelCartP4.model.Address;
import com.example.PixelCartP4.model.User;
import com.example.PixelCartP4.repository.AddressRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private final AddressRepo addressRepo;

    public AddressServiceImpl(AddressRepo addressRepo) {
        this.addressRepo = addressRepo;
    }

    @Override
    public Address saveAddress(Address address) {
        return addressRepo.save(address);
    }

    @Override
    public Optional<Address> getAddressById(Long id) {
        return addressRepo.findById(id);
    }

    @Override
    public List<Address> getAddressesByUser(User user) {
        return addressRepo.findAllByUser(user);
    }

    @Override
    public void deleteAddress(Long id) {
        addressRepo.deleteById(id);
    }
}

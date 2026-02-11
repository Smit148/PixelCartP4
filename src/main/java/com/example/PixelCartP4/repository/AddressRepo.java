package com.example.PixelCartP4.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.PixelCartP4.model.Address;
import com.example.PixelCartP4.model.User;

public interface AddressRepo extends JpaRepository<Address, Long> {
    List<Address> findAllByUser(User user);
}

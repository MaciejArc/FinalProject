package pl.coderslab.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import pl.coderslab.entity.Addresses;
import pl.coderslab.repository.AddressesRepository;

public class AddressesConverter implements Converter<String,Addresses> {

    @Autowired
  private   AddressesRepository addressesRepository;

    @Override
    public Addresses convert(String s) {
        return addressesRepository.findById(Long.parseLong(s)).get();
    }
}

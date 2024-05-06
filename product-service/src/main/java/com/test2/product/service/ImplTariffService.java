package com.test2.product.service;

import com.test2.product.entity.Tariff;
import com.test2.product.payload.TariffDTO;
import com.test2.product.repository.TariffRepository;
import com.test2.product.service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImplTariffService implements TariffService {
    private final TariffRepository tariffRepository;
    private final ProductMapper productMapper;

    @Transactional
    @Override
    public TariffDTO createTariff(TariffDTO tariff) {
        UUID uuid = productMapper.mapToUUID(tariff.getId());
        Tariff newTariff = new Tariff(uuid, tariff.version);
        this.tariffRepository.save(newTariff);
        return tariff;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Tariff> getTariffById(String id) {
        UUID uuidTariff = productMapper.mapToUUID(id);
        return this.tariffRepository.findById(uuidTariff);
    }
}

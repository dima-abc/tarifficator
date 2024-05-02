package com.test2.users.validator;

import com.test2.users.controller.payload.NewAccountPayload;
import com.test2.users.entity.Platform;
import com.test2.users.service.PlatformService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class PayloadValidator {
    private final PlatformService platformService;

    public Platform getPlatform(String header) {
        return platformService.findPlatformByName(header)
                .orElseThrow(() -> new NoSuchElementException("customer.errors.platform.not_found"));
    }

    public BindingResult isValid(String header, NewAccountPayload payload) {
        final Platform platform = getPlatform(header);
        final AccountValidator validator = new AccountValidator(platform);
        final DataBinder dataBinder = new DataBinder(payload);
        dataBinder.addValidators(validator);
        dataBinder.validate();
        return dataBinder.getBindingResult();
    }
}

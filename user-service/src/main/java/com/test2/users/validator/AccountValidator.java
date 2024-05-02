package com.test2.users.validator;

import com.test2.users.controller.payload.NewAccountPayload;
import com.test2.users.entity.Platform;
import com.test2.users.service.mapper.AccountMapper;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.UUID;


public class AccountValidator implements Validator {

    public static final String DATE_REGEXP = "^\\d{4}-\\d{2}-\\d{2}";
    public static final String PASSPORT_REGEXP = "\\d{4} \\d{6}";
    public static final String PHONE_REGEXP = "^7\\d{10}";

    private final Platform platform;

    public AccountValidator(Platform platform) {
        this.platform = platform;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return NewAccountPayload.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        NewAccountPayload accountPayload = (NewAccountPayload) target;
        validBankId(this.platform, accountPayload, errors);
        validFirstName(this.platform, accountPayload, errors);
        validLastName(this.platform, accountPayload, errors);
        validMiddleName(this.platform, accountPayload, errors);
        validBirthDate(this.platform, accountPayload, errors, DATE_REGEXP);
        validPassport(this.platform, accountPayload, errors, PASSPORT_REGEXP);
        validPlaceBirth(this.platform, accountPayload, errors);
        validPhone(this.platform, accountPayload, errors, PHONE_REGEXP);
        validEmail(this.platform, accountPayload, errors);
        validAddressRegistered(this.platform, accountPayload, errors);
        validAddressLife(this.platform, accountPayload, errors);
    }

    public void validBankId(Platform platform, NewAccountPayload accountPayload, Errors errors) {
        if (platform.isBankId()) {
            UUID uuid = AccountMapper.mapToUUID(accountPayload.getBankId());
            if (uuid == null) {
                errors.rejectValue("bankId", "bankId.empty", "customer.errors.account.bank_id");
            }
        }
    }

    public void validFirstName(Platform platform, NewAccountPayload accountPayload, Errors errors) {
        if (platform.isFirstName()
                && (accountPayload.getFirstName() == null
                || accountPayload.getFirstName().isBlank())) {
            errors.rejectValue("firstName", "firstName.empty", "customer.errors.account.first_name");
        }
    }

    public void validLastName(Platform platform, NewAccountPayload accountPayload, Errors errors) {
        if (platform.isLastName()
                && (accountPayload.getLastName() == null
                || accountPayload.getLastName().isBlank())) {
            errors.rejectValue("lastName", "lastName.empty", "customer.errors.account.last_name");
        }
    }

    public void validMiddleName(Platform platform, NewAccountPayload accountPayload, Errors errors) {
        if (platform.isMiddleName()
                && (accountPayload.getMiddleName() == null
                || accountPayload.getMiddleName().isBlank())) {
            errors.rejectValue("middleName", "middleName.empty", "customer.errors.account.middle_name");
        }
    }

    public void validBirthDate(Platform platform,
                               NewAccountPayload accountPayload,
                               Errors errors, String regexp) {
        if (platform.isBirthDate()
                && (accountPayload.getBirthDate() == null
                || accountPayload.getBirthDate().isBlank()
                || !accountPayload.getBirthDate().matches(regexp))) {
            errors.rejectValue("birthDate", "birthDate.empty", "customer.errors.account.birth_date");
        }
        if (!platform.isBirthDate()
                && (accountPayload.getBirthDate() != null
                && !accountPayload.getBirthDate().isBlank()
                && !accountPayload.getBirthDate().matches(regexp))) {
            errors.rejectValue("birthDate", "birthDate.empty", "customer.errors.account.birth_date");
        }
    }

    public void validPassport(Platform platform, NewAccountPayload accountPayload, Errors errors, String regexp) {
        if (platform.isPassport()
                && (accountPayload.getPassport() == null
                || accountPayload.getPassport().isBlank()
                || !accountPayload.getPassport().matches(regexp))) {
            errors.rejectValue("passport", "passport.empty", "customer.errors.account.passport");
        }
        if (!platform.isPassport()
                && (accountPayload.getPassport() != null
                && !accountPayload.getPassport().isBlank()
                && !accountPayload.getPassport().matches(regexp))) {
            errors.rejectValue("passport", "passport.empty", "customer.errors.account.passport");
        }
    }

    public void validPlaceBirth(Platform platform, NewAccountPayload accountPayload, Errors errors) {
        if (platform.isPlaceBirth()
                && (accountPayload.getPlaceBirth() == null
                || accountPayload.getPlaceBirth().isBlank())) {
            errors.rejectValue("placeBirth", "placeBirth.empty", "customer.errors.account.place_birth");
        }
    }

    public void validPhone(Platform platform, NewAccountPayload accountPayload, Errors errors, String regexp) {
        if (platform.isPhone()
                && (accountPayload.getPhone() == null
                || accountPayload.getPhone().isBlank()
                || !accountPayload.getPhone().matches(regexp))) {
            errors.rejectValue("phone", "phone.empty", "customer.errors.account.phone");
        }
        if (!platform.isPhone()
                && (accountPayload.getPhone() != null
                && !accountPayload.getPhone().isBlank()
                && !accountPayload.getPhone().matches(regexp))) {
            errors.rejectValue("phone", "phone.empty", "customer.errors.account.phone");
        }
    }

    public void validEmail(Platform platform, NewAccountPayload accountPayload, Errors errors) {
        if (platform.isEmail()
                && (accountPayload.getEmail() == null
                || accountPayload.getEmail().isBlank())) {
            errors.rejectValue("email", "email.empty", "customer.errors.account.email");
        }
    }

    public void validAddressRegistered(Platform platform, NewAccountPayload accountPayload, Errors errors) {
        if (platform.isAddressRegistered()
                && (accountPayload.getAddressRegistered() == null
                || accountPayload.getAddressRegistered().isBlank())) {
            errors.rejectValue("addressRegistered", "addressRegistered.empty", "customer.errors.account.address_registered");
        }
    }

    public void validAddressLife(Platform platform, NewAccountPayload accountPayload, Errors errors) {
        if (platform.isAddressLife()
                && (accountPayload.getAddressLife() == null
                || accountPayload.getAddressLife().isBlank())) {
            errors.rejectValue("addressLife", "addressLife.empty", "customer.errors.account.address_life");
        }
    }


}

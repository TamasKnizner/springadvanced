package com.epam.tamasknizner.springadvancedtraining.soap.transformer;

import com.epam.tamasknizner.springadvancedtraining.soap.domain.User;
import com.epam.tamasknizner.springadvancedtraining.soap.domain.UserAccount;
import org.springframework.stereotype.Component;

@Component
public class SoapUserTransformer {

    public User transform(final com.epam.tamasknizner.springadvancedtraining.domain.User user) {
        User soapUser = new User();
        soapUser.setId(user.getId());
        soapUser.setFirstName(user.getFirstName());
        soapUser.setLastName(user.getLastName());
        soapUser.setBirthday(user.getBirthday().toString());
        soapUser.setEmail(user.getEmail());
        soapUser.setRoles(user.getRoles());
        soapUser.setUserAccount(transform(user.getUserAccount()));
        return soapUser;
    }

    private UserAccount transform(final com.epam.tamasknizner.springadvancedtraining.domain.UserAccount userAccount) {
        UserAccount soapUserAccount = new UserAccount();
        soapUserAccount.setId(userAccount.getId());
        soapUserAccount.setPrepaidAmount(userAccount.getPrepaidAmount());
        return soapUserAccount;
    }

}

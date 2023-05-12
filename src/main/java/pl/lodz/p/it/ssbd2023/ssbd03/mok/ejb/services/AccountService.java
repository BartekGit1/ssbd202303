package pl.lodz.p.it.ssbd2023.ssbd03.mok.ejb.services;

import jakarta.ejb.Local;
import jakarta.persistence.NoResultException;
import pl.lodz.p.it.ssbd2023.ssbd03.entities.Account;
import pl.lodz.p.it.ssbd2023.ssbd03.entities.Admin;
import pl.lodz.p.it.ssbd2023.ssbd03.entities.Manager;
import pl.lodz.p.it.ssbd2023.ssbd03.entities.Owner;
import pl.lodz.p.it.ssbd2023.ssbd03.entities.PersonalData;

import java.util.List;
import pl.lodz.p.it.ssbd2023.ssbd03.entities.PersonalData;

@Local
public interface AccountService {
    void createOwner(Account account);

    void confirmAccountFromActivationLink(String confirmationToken);

    void changePasswordFromResetPasswordLink(String resetPasswordToken, String newPassword, String newRepeatedPassword);

    String authenticate(String username, String password);

    void updateLoginData(String username, boolean flag);

    void changePhoneNumber(String newPhoneNumber);

    void adminLoggedInEmail(String email);

    void changeSelfEmail(String newEmail);

    void changeUserEmail(String newEmail, String username);

    void confirmNewEmailAccountFromActivationLink(String confirmationToken);

    Account getAccount(String username);

    Owner getOwner();

    Manager getManager();

    Admin getAdmin();

    PersonalData getPersonalData();

    PersonalData getUserPersonalData(String username);

    void editSelfPersonalData(String firstName, String surname);

    void editUserPersonalData(String username, String firstName, String surname);

    void changeSelfPassword(String oldPassword, String newPassword, String newRepeatedPassword);

    void changeUserPassword(String username, String newPassword, String newRepeatedPassword);

    void resetPassword(String username);

    void disableUserAccount(String username) throws IllegalArgumentException, NoResultException;

    void enableUserAccount(String username) throws IllegalArgumentException, NoResultException;

    void addAccessLevelManager(String username, String license);

    void addAccessLevelOwner(String username, String phoneNumber);

    void addAccessLevelAdmin(String username);

    void revokeAccessLevel(String username, String accessLevel);

    List<Account> getListOfAccounts(String sortBy, int pageNumber);
}
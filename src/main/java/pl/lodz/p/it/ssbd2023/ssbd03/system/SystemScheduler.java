package pl.lodz.p.it.ssbd2023.ssbd03.system;

import jakarta.annotation.security.RunAs;
import jakarta.ejb.*;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import pl.lodz.p.it.ssbd2023.ssbd03.config.Roles;
import pl.lodz.p.it.ssbd2023.ssbd03.entities.Account;
import pl.lodz.p.it.ssbd2023.ssbd03.entities.AccountConfirmationToken;
import pl.lodz.p.it.ssbd2023.ssbd03.interceptors.TrackerInterceptor;
import pl.lodz.p.it.ssbd2023.ssbd03.mok.ejb.facade.AccountConfirmationTokenFacade;
import pl.lodz.p.it.ssbd2023.ssbd03.mok.ejb.facade.AccountFacade;
import pl.lodz.p.it.ssbd2023.ssbd03.mok.ejb.facade.ResetPasswordTokenFacade;

import java.util.List;

@Startup
@Singleton
@RunAs(Roles.ADMIN)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors(TrackerInterceptor.class)
public class SystemScheduler {
    @Inject
    private AccountConfirmationTokenFacade accountConfirmationTokenFacade;

    @Inject
    private ResetPasswordTokenFacade resetPasswordTokenFacade;

    @Inject
    private AccountFacade accountFacade;

    @Schedule(hour = "*", minute = "*/1", persistent = false)
    private void cleanUnconfirmedAccounts() {
        final List<AccountConfirmationToken> allUnconfirmedAccounts = accountConfirmationTokenFacade.findAllUnconfirmedAccounts();
        if (!allUnconfirmedAccounts.isEmpty()) {
            allUnconfirmedAccounts.forEach(accountConfirmationToken -> {
                accountFacade.remove(accountConfirmationToken.getAccount());
                accountConfirmationTokenFacade.remove(accountConfirmationToken);
            });
        }
    }

    @Schedule(hour = "*", minute = "*/1", persistent = false)
    private void deleteResetPasswordExpiredTokens() {
        resetPasswordTokenFacade.deleteExpiredResetPasswordToken();
    }

    @Schedule(hour = "*", minute = "*/1", persistent = false)
    public void unlockAccounts() {
        final List<Account> accounts = accountFacade.findAllBlockedAccounts();
        if (!accounts.isEmpty()) {
            accounts.forEach(account -> {
                account.setIsEnable(true);
                account.getLoginData().setInvalidLoginCounter(0);
                accountFacade.edit(account);
            });
        }
    }
}

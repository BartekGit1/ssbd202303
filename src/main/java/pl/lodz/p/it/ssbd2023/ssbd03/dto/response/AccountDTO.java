package pl.lodz.p.it.ssbd2023.ssbd03.dto.response;

import lombok.*;
import pl.lodz.p.it.ssbd2023.ssbd03.dto.AbstractDTO;
import pl.lodz.p.it.ssbd2023.ssbd03.util.etag.Signable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO extends AbstractDTO implements Signable {
    private String email;
    private String username;
    private String password;
    private String repeatedPassword;

    public AccountDTO(String email, String username) {
        this.email = email;
        this.username = username;
    }

    @Override
    public String messageToSign() {
        return email
                .concat(username)
                .concat(password)
                .concat(repeatedPassword)
                .concat(getId().toString())
                .concat(getVersion().toString());
    }
}

package org.pchardwarestore.service.accountService.confirmationCodeService;

import lombok.RequiredArgsConstructor;
import org.pchardwarestore.entity.accountEntity.ConfirmationCode;
import org.pchardwarestore.entity.accountEntity.User;
import org.pchardwarestore.repository.accountRepository.ConfirmationCodeRepository;
import org.pchardwarestore.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.pchardwarestore.service.mail.MailUtil;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConfirmationCodeService {

    private final ConfirmationCodeRepository repository;
    private final MailUtil mailUtil;
    private final int EXPIRATION_PERIOD = 1;
    private final String LINK_PATH = "code=";
    private final  String BASE_URL = "http://localhost:8080/api/users/confirm?";

    public void confirmationCodeHandle(User user){
        String code = generateCode();
        saveConfirmationCode(code, user);
        sendCodeByEmail(code, user);
    }

    private String generateCode(){
        String code = UUID.randomUUID().toString();
        return code;
    }

    private void saveConfirmationCode(String code, User user){
        ConfirmationCode newCode = new ConfirmationCode();
        newCode.setCode(code);
        newCode.setUser(user);
        newCode.setExpireDateTime(LocalDateTime.now().plusDays(EXPIRATION_PERIOD));
        newCode.setConfirmed(false);
        repository.save(newCode);
    }

    public void sendCodeByEmail(String code, User user){
        String linkToSend = BASE_URL + LINK_PATH + code;
        System.out.println(linkToSend);
        mailUtil.sendEmail(
                user.getFirstName(),
                user.getLastName(),
                linkToSend,
                "Code confirmation email",
                user.getEmail()
        );
    }

    public User confirmUserByCode(String code){
        ConfirmationCode confirmationCode = repository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Confirmation code : " + code + " not found"));
        User user = confirmationCode.getUser();
        confirmationCode.setConfirmed(true);
        repository.save(confirmationCode);

        return user;
    }
}

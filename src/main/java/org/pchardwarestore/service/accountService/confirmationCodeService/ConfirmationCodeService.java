package org.pchardwarestorefour.service.accountService.confirmationCodeService;
//
//import lombok.RequiredArgsConstructor;
//import org.pchardwarestorefour.entity.accountEntity.ConfirmationCode;
//import org.pchardwarestorefour.entity.accountEntity.User;
//import org.pchardwarestorefour.repository.accountRepository.ConfirmationCodeRepository;
//import org.pchardwarestorefour.service.exception.NotFoundException;
//import org.pchardwarestorefour.service.exception.ValidationException;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class ConfirmationCodeService {
//
//    private final ConfirmationCodeRepository repository;
//    private final int EXPIRATION_PERIOD = 1;
//    private final String LINK_PATH = "localhost:8080/api/public/confirmation?code=";
//
//    private String generateCode(){
//        String code = UUID.randomUUID().toString();
//        return code;
//    }
//
//    private void saveConfirmationCode(String code, User user){
//        ConfirmationCode newCode = new ConfirmationCode();
//        newCode.setCode(code);
//        newCode.setUser(user);
//        newCode.setConfirmed(false);
//TODO : Добавил
//        newCode.setExpireDateTime(LocalDateTime.now().plusHours(EXPIRATION_PERIOD));
////
//        repository.save(newCode);
//    }
//
//    public void sendCodeByEmail(String code, String userEmail){
//        String message = LINK_PATH + code;
//        // тут будет отправка пользователю письма с кодом подтверждения
//    }
//
//    public void confirmationCodeHandle(User user){
//        String code = generateCode();
//        saveConfirmationCode(code, user);
//        sendCodeByEmail(code, user.getEmail());
//    }
//
//    public User confirmUserByCode(String code){
//        ConfirmationCode confirmationCode = repository.findByCode(code)
//                .orElseThrow(() -> new NotFoundException("Confirmation code : " + code + " not found"));
//
////        if (confirmationCode.getExpireDateTime().isBefore(LocalDateTime.now())) {
////            throw new ValidationException("Confirmation code expired");
////        }
////
////        if (confirmationCode.isConfirmed()) {
////            throw new ValidationException("Confirmation code has already been used");
////        }
//
//
//        User user = confirmationCode.getUser();
//
//        confirmationCode.setConfirmed(true);
//        repository.save(confirmationCode);
//
//        return user;
//    }
//}

//TODO Добавил 07.05.25
import lombok.RequiredArgsConstructor;
import org.pchardwarestorefour.entity.accountEntity.ConfirmationCode;
import org.pchardwarestorefour.entity.accountEntity.User;
import org.pchardwarestorefour.repository.accountRepository.ConfirmationCodeRepository;
import org.pchardwarestorefour.service.exception.NotFoundException;
import org.pchardwarestorefour.service.exception.ValidationException;
import org.springframework.stereotype.Service;
import org.pchardwarestorefour.service.mail.MailUtil;


import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConfirmationCodeService {

    private final ConfirmationCodeRepository repository;

    private final MailUtil mailUtil;

    private final int EXPIRATION_PERIOD = 1;

    private final String LINK_PATH = "code=";

    //TODO Добавил 07.05.25
    private final  String BASE_URL = "http://localhost:8080/api/users/confirm?";
    //TODO Добавил 07.05.25

    public void confirmationCodeHandle(User user){
        String code = generateCode();
        saveConfirmationCode(code, user);
        sendCodeByEmail(code, user);
    }

    private String generateCode(){

        // universal uniq identifier
        // формат 128 bit
        // xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
        // где каждый символ 'x' - это либо цифра либо символ от a-f
        // 3f29c3b2-9fc2-11ed-a8fc-0242ac120002

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
        //TODO Добавил 07.05.25
        String linkToSend = BASE_URL + LINK_PATH + code;
        //TODO Добавил 07.05.25

//        String linkToSend = LINK_PATH + code;
        // тут будет отправка пользователю письма с кодом подтверждения
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

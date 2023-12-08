package uz.pdp.learning_center_full.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.learning_center_full.dto.request.UserCr;
import uz.pdp.learning_center_full.dto.response.UserResponse;
import uz.pdp.learning_center_full.entity.UserEntity;
import uz.pdp.learning_center_full.entity.enums.UserRole;
import uz.pdp.learning_center_full.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository ;
    private final ModelMapper modelMapper;
    private final MailSenderService senderService;
    public UserResponse addAdmin(UserCr userCr){
        String verificationCode = senderService.sendVerificationCode(userCr.getEmail());
        UserEntity save = userRepository.save(modelMapper.map(userCr, UserEntity.class));
//        save.builder()
//                .name(userCr.getName())
//                .surname(userCr.getSurname())
//                .phoneNumber(userCr.getPhoneNumber())
//                .build();
        save.setEmail(userCr.getEmail());
        save.setPassword(verificationCode);
        save.setRole(UserRole.ADMIN);
        userRepository.save(save);
        return modelMapper.map( save,UserResponse.class);
    }

}

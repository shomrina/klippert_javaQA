package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
//подробнее про ломбок тут https://habr.com/ru/post/345520/

@Log4j2
@Data                   //генерирует конструктор, геттеры, сеттеры, методы equals, hashCode, toString. А также делает все поля private.
@Builder
@AllArgsConstructor     //создание конструктора включающего все возможные поля

public class User {

    private String email;
    private String firstName;
    private Long id;
    private String lastName;
    private String password;
    private String phone;
    private Long userStatus;
    private String username;



}

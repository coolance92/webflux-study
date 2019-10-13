package com.coolance.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @ClassName User
 * @Description 领域对象
 * @Author Coolance
 * @Version
 * @Date 2019/10/13 14:33
 */
@Document(collection = "user")
@Data
public class User {
    @Id
    private String id;
    private String name;
    private int age;
}

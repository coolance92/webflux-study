package com.coolance.repository;

import com.coolance.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName UserRepository
 * @Description 仓库类
 * @Author Coolance
 * @Version
 * @Date 2019/10/13 14:35
 */
@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
}

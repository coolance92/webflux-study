package com.coolance.controller;

import com.coolance.domain.User;
import com.coolance.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @ClassName UserController
 * @Description 控制器类
 * @Author Coolance
 * @Version
 * @Date 2019/10/13 14:37
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 以数组形式一次性返回数据
     *
     * @return
     */
    @GetMapping("/")
    public Flux<User> getAll() {
        return userRepository.findAll();
    }

    /**
     * 以Server-Sent Events形式多次返回数据
     *
     * @return
     */
    @GetMapping(value = "/stream/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamGetAll() {
        return userRepository.findAll();
    }

    /**
     * 新增数据
     *
     * @param user
     * @return
     */
    @PostMapping("/")
    public Mono<User> create(@RequestBody User user) {
        //spring data jna里面，新增和修改都是save，有id是修改，id为空是新增
        //根据实际情况是否置空id
        user.setId(null);
        return userRepository.save(user);
    }

    /**
     * 根据id删除用户 存在的时候返回200, 不存在返回404
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") String id) {
        // deleteByID 没有返回值, 不能判断数据是否存在
        // this.repository.deleteById(id)
        return userRepository.findById(id)
                // 当你要操作数据, 并返回一个Mono 这个时候使用flatMap
                // 如果不操作数据, 只是转换数据, 使用map
                .flatMap(user -> userRepository.delete(user)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 修改数据 存在的时候返回200 和修改后的数据, 不存在的时候返回404
     *
     * @param id
     * @param user
     * @return
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<User>> update(@PathVariable("id") String id, @RequestBody User user) {
        return userRepository.findById(id)
                // flatMap 操作数据
                .flatMap(u -> {
                    u.setName(user.getName());
                    u.setAge(user.getAge());
                    return userRepository.save(u);
                })
                // map: 转换数据
                .map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    /**
     * 根据ID查找用户 存在返回用户信息, 不存在返回404
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> get(@PathVariable("id") String id) {
        return userRepository.findById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity(HttpStatus.NOT_FOUND));
    }
}

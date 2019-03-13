package com.wangshu.advanceprogrammer.demo;


import javax.persistence.*;
import javax.validation.constraints.Min;

/**
 * Create by LSL on 2019\3\11 0011
 * 描述：
 * 版本：1.0.0
 */
@Entity
@Table(name = "user_info")
public class DemoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nickname")
    private String nickname;

    @Min(value = 18, message = "未成年不得访问")
    @Column(name = "age")
    private Integer age;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String name) {
        this.nickname = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

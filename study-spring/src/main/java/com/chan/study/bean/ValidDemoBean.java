package com.chan.study.bean;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ValidDemoBean {
    @NotNull
    @Pattern(regexp = "Ct*", message = "要以Ct打头")
    private String name;

    @NotNull
    @Min(value = 0, message = "要大于0")
    private Integer id;

    public String getName() {
        return name;
    }

    public ValidDemoBean setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public ValidDemoBean setId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "ValidDemoBean{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}

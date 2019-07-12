package com.syh.framework.design_patters.create_type;

/**
 * 用链式的形式创建复杂的复合对象
 */
public class BuilderDto {

    private String firstName;
    private String lastName;
    private int age;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private BuilderDto(UserBuild builder) {
        firstName = builder.firstName;
        lastName = builder.lastName;
        age = builder.age;
    }

    public static class UserBuild {
        private String firstName;
        private String lastName;
        private int age;

        public UserBuild setFirstName(final String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuild setLastName(final String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuild setAge(final int age) {
            this.age = age;
            return this;
        }

        public BuilderDto build() {
            return new BuilderDto(this);
        }
    }

}

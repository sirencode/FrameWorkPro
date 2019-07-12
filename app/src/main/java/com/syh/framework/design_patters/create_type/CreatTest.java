package com.syh.framework.design_patters.create_type;

public class CreatTest {
    public static void main(String[] args) {
        BuilderDto builder = new BuilderDto.UserBuild()
                .setAge(1)
                .setFirstName("shen")
                .setLastName("yonghe")
                .build();
        Book book = new Book("《香水》");
        try {
            System.out.println(book.clone().getName());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}

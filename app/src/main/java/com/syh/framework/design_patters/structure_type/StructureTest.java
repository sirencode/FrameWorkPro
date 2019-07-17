package com.syh.framework.design_patters.structure_type;

import com.syh.framework.design_patters.structure_type.adapter.Adapter;
import com.syh.framework.design_patters.structure_type.adapter.Electric;
import com.syh.framework.design_patters.structure_type.adapter.PhoneAdapter;
import com.syh.framework.design_patters.structure_type.bridge.Clothes;
import com.syh.framework.design_patters.structure_type.bridge.Coder;
import com.syh.framework.design_patters.structure_type.bridge.Person;
import com.syh.framework.design_patters.structure_type.bridge.Shirt;
import com.syh.framework.design_patters.structure_type.bridge.Student;
import com.syh.framework.design_patters.structure_type.bridge.Uniform;
import com.syh.framework.design_patters.structure_type.composite.File;
import com.syh.framework.design_patters.structure_type.composite.Folder;
import com.syh.framework.design_patters.structure_type.decorator.Bedroom;
import com.syh.framework.design_patters.structure_type.decorator.Kitchen;
import com.syh.framework.design_patters.structure_type.decorator.NewRoom;
import com.syh.framework.design_patters.structure_type.decorator.Room;
import com.syh.framework.design_patters.structure_type.decorator.RoomDecorator;
import com.syh.framework.design_patters.structure_type.facade.GameSdk;
import com.syh.framework.design_patters.structure_type.flayweight.BikeFactory;
import com.syh.framework.design_patters.structure_type.flayweight.IBike;
import com.syh.framework.design_patters.structure_type.proxy.Actor;
import com.syh.framework.design_patters.structure_type.proxy.Agent;
import com.syh.framework.design_patters.structure_type.proxy.DynamicProxy;
import com.syh.framework.design_patters.structure_type.proxy.Star;

import java.lang.reflect.Proxy;

public class StructureTest {
    public static void main(String[] args) {
        System.out.println("代理模式：");
        Actor star = new Star();
        Actor proxy = new Agent(star);
        proxy.movie();
        DynamicProxy domestic = new DynamicProxy(star);
        ClassLoader classLoader = domestic.getClass().getClassLoader();   //获取ClassLoader
        Actor actor = (Actor) Proxy.newProxyInstance(classLoader, new Class[]{Actor.class}, domestic);
        actor.movie();

        System.out.println();
        System.out.println("组合模式：");
        Folder DSFolder = new Folder("设计模式资料");
        File note1 = new File("组合模式笔记.md", "组合模式组合多个对象形成树形结构以表示具有 \"整体—部分\" 关系的层次结构");
        File note2 = new File("工厂方法模式.md", "工厂方法模式定义一个用于创建对象的接口，让子类决定将哪一个类实例化。");
        DSFolder.add(note1);
        DSFolder.add(note2);
        Folder codeFolder = new Folder("样例代码");
        File readme = new File("README.md", "# 设计模式示例代码项目");
        Folder srcFolder = new Folder("src");
        File code1 = new File("组合模式示例.java", "这是组合模式的示例代码");
        srcFolder.add(code1);
        codeFolder.add(readme);
        codeFolder.add(srcFolder);
        DSFolder.add(codeFolder);
        DSFolder.print();

        System.out.println();
        System.out.println("适配器模式：");
        Electric electric = new Electric();
        System.out.println("默认电压：" + electric.output_220v());
        Adapter phoneAdapter = new PhoneAdapter(electric);//传递一个对象给适配器
        System.out.println("适配转换后的电压：" + phoneAdapter.convert_5v());

        System.out.println();
        System.out.println("装饰者模式：");
        Room newRoom = new NewRoom();
        RoomDecorator bedroom = new Bedroom(newRoom);
        bedroom.fitment();
        RoomDecorator kitchen = new Kitchen(newRoom);
        kitchen.fitment();

        System.out.println();
        System.out.println("享元模式：");
        BikeFactory factory=new BikeFactory();
        IBike ofo = factory.getBike("ofo");
        ofo.billing(2);
        IBike mobike = factory.getBike("Mobike");
        mobike.billing(1);
        IBike ofo1 = factory.getBike("ofo");
        ofo1.billing(3);

        System.out.println();
        System.out.println("外观模式：");
        GameSdk gameSdk = new GameSdk();
        gameSdk.login();
        gameSdk.pay(6);

        System.out.println();
        System.out.println("桥接模式：");
        Clothes uniform = new Uniform();
        Clothes shirt = new Shirt();
        Person coder = new Coder();
        coder.setClothes(shirt);
        coder.dress();
        System.out.println("--------------------------------------");
        Person student = new Student();
        student.setClothes(uniform);
        student.dress();
        System.out.println("--------------------------------------");
        student.setClothes(shirt);
        student.dress();
    }
}

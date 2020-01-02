package com.syh.framework.design_patters.behavior_type;

import com.syh.framework.design_patters.behavior_type.command.Command;
import com.syh.framework.design_patters.behavior_type.command.Invoker;
import com.syh.framework.design_patters.behavior_type.command.Receiver;
import com.syh.framework.design_patters.behavior_type.command.ShutdownCommand;
import com.syh.framework.design_patters.behavior_type.interpreter.And;
import com.syh.framework.design_patters.behavior_type.interpreter.Constant;
import com.syh.framework.design_patters.behavior_type.interpreter.MyContext;
import com.syh.framework.design_patters.behavior_type.interpreter.Expression;
import com.syh.framework.design_patters.behavior_type.interpreter.Not;
import com.syh.framework.design_patters.behavior_type.interpreter.Or;
import com.syh.framework.design_patters.behavior_type.interpreter.Variable;
import com.syh.framework.design_patters.behavior_type.iterator.ConcreteContainer;
import com.syh.framework.design_patters.behavior_type.iterator.Container;
import com.syh.framework.design_patters.behavior_type.iterator.Iterator;
import com.syh.framework.design_patters.behavior_type.mediator.Landlord;
import com.syh.framework.design_patters.behavior_type.mediator.Purchaser;
import com.syh.framework.design_patters.behavior_type.mediator.Wuba;
import com.syh.framework.design_patters.behavior_type.memo.Game;
import com.syh.framework.design_patters.behavior_type.memo.Memento;
import com.syh.framework.design_patters.behavior_type.memo.MemoManager;
import com.syh.framework.design_patters.behavior_type.observer.Boy;
import com.syh.framework.design_patters.behavior_type.observer.Girl;
import com.syh.framework.design_patters.behavior_type.observer.Observer;
import com.syh.framework.design_patters.behavior_type.observer.Observerble;
import com.syh.framework.design_patters.behavior_type.observer.Postman;
import com.syh.framework.design_patters.behavior_type.responsibility.CTO;
import com.syh.framework.design_patters.behavior_type.responsibility.HR;
import com.syh.framework.design_patters.behavior_type.responsibility.TeamLeader;
import com.syh.framework.design_patters.behavior_type.state.Developer;
import com.syh.framework.design_patters.behavior_type.stragety.Drive;
import com.syh.framework.design_patters.behavior_type.stragety.GreenStragety;
import com.syh.framework.design_patters.behavior_type.stragety.RedStragety;
import com.syh.framework.design_patters.behavior_type.template.CodeBicycle;
import com.syh.framework.design_patters.behavior_type.template.ScanBicycle;
import com.syh.framework.design_patters.behavior_type.visitor.ConcreteElementNodeA;
import com.syh.framework.design_patters.behavior_type.visitor.ConcreteElementNodeB;
import com.syh.framework.design_patters.behavior_type.visitor.ConcreteVisitorA;
import com.syh.framework.design_patters.behavior_type.visitor.ObjectStructure;
import com.syh.framework.design_patters.behavior_type.visitor.Vistor;

import java.util.ArrayList;
import java.util.List;

public class BehaviorTest {
    public static void main(String[] args) {
        System.out.println("状态模式:");
        Drive it;
        System.out.println("绿灯:");
        it = new Drive(new GreenStragety());
        it.doSomething();
        System.out.println("红灯:");
        it = new Drive(new RedStragety());
        it.doSomething();

        System.out.println();
        System.out.println("策略模式:");
        Developer developer = new Developer();
        developer.develop();
        developer.meet();
        developer.test();

        System.out.println();
        System.out.println("责任链模式:");
        CTO cto = new CTO();
        TeamLeader teamLeader = new TeamLeader();
        HR hr = new HR();
        cto.nextReviewman = teamLeader;
        teamLeader.nextReviewman = hr;
        System.out.println("请7天假");
        cto.review(7);
        System.out.println("请3天假");
        cto.review(3);
        System.out.println("请1天假");
        cto.review(1);

        System.out.println();
        System.out.println("观察者模式:");
        Observerble postman = new Postman();
        Observer boy1 = new Boy("路飞");
        Observer boy2 = new Boy("乔巴");
        Observer girl1 = new Girl("娜美");
        postman.add(boy1);
        postman.add(boy2);
        postman.add(girl1);
        postman.notify("快递到了,请下楼领取.");

        System.out.println();
        System.out.println("模板模式:");
        ScanBicycle scanBicycle = new ScanBicycle();
        scanBicycle.use();
        System.out.println("========================");
        CodeBicycle codeBicycle = new CodeBicycle();
        codeBicycle.use();

        System.out.println();
        System.out.println("迭代器模式:");
        List<Object> list = new ArrayList<>();
        list.add("Android");
        list.add("IOS");
        list.add("C++");
        Container container = new ConcreteContainer(list);
        container.add("C");
        Iterator iterator = container.createIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        System.out.println();
        System.out.println("备忘录模式:");
        System.out.println("首次进入游戏");
        Game game = new Game();
        game.play();//玩游戏
        Memento memento = game.createMemento();//创建存档
        MemoManager caretaker = new MemoManager();
        caretaker.setMemento(memento);//保存存档
        game.exit();//退出游戏
        System.out.println("-------------");
        System.out.println("二次进入游戏");
        Game secondGame = new Game();
        secondGame.setMemento(caretaker.getMemento());//读取存档
        secondGame.play();//继续玩游戏
        secondGame.exit();//不存档,嘿嘿嘿

        System.out.println();
        System.out.println("访问者模式:");
        ObjectStructure objectStructure = new ObjectStructure();
        objectStructure.add(new ConcreteElementNodeA());
        objectStructure.add(new ConcreteElementNodeB());
        Vistor vistor = new ConcreteVisitorA();
        objectStructure.action(vistor);

        System.out.println();
        System.out.println("中介者模式:");
        Wuba houseMediator = new Wuba();
        Purchaser purchaser = new Purchaser(houseMediator);
        Landlord landlord = new Landlord(houseMediator);
        houseMediator.setLandlord(landlord);
        houseMediator.setPurchaser(purchaser);
        landlord.send("出售一套别墅");
        System.out.println("------------------------");
        purchaser.send("求购一套学区房");

        System.out.println();
        System.out.println("解释器模式:");
        MyContext context = new MyContext();
        Variable xVariable = new Variable("X");
        Variable yVariable = new Variable("Y");
        Constant constant = new Constant(true);
        context.assign(xVariable, false);
        context.assign(yVariable, true);
        Expression expression = new Or(new And(constant, xVariable), new And(yVariable, new Not(xVariable)));
        System.out.println("X = " + xVariable.interpret(context));
        System.out.println("Y = " + yVariable.interpret(context));
        System.out.println(expression.toString() + " = " + expression.interpret(context));

        System.out.println();
        System.out.println("命令模式:");
        Receiver receiver = new Receiver();
        Command command = new ShutdownCommand(receiver);
        Invoker invoker = new Invoker(command);
        invoker.action();
    }
}

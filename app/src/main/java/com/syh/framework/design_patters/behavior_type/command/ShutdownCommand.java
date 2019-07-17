package com.syh.framework.design_patters.behavior_type.command;

public class ShutdownCommand implements Command {
    private Receiver receiver;//接受者

    public ShutdownCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        System.out.println("命令角色执行关机命令");
        receiver.action();//调用接受者
    }
}

package com.tactfactory.commandworker;

import java.util.ArrayList;
import java.util.List;

public class CommandWorker extends Thread {
	private List<Command<?>> commands = new ArrayList<>();
	private Boolean loop = true;
	private RemoteControl control;
	private Integer currentCommand;
	private Integer maxCommand;
	private Integer commandIndex;
	
	public CommandWorker() {
		super();
		this.currentCommand = -1;
		this.maxCommand = 0;
		this.control = new RemoteControl();
	}
	
	public void add(Command<?> command){
		this.commands.add(command);
		this.maxCommand++;
	}

	public void start(Integer commandIndex) {
		this.commandIndex = commandIndex;
		super.start();
	}
	
	public void start() {
		this.start(0);
	}
	
	public void halt(){
		this.control.getExecutor().shutdownNow();
	}

	@Override
	public void run() {
		loop = true;
		Integer oldCommand = -1;
		while(loop && (commandIndex != oldCommand)){
			this.currentCommand = commandIndex;
			control.setCommand(commands.get(currentCommand));
			control.execute();
			commandIndex++;
			if (commandIndex == this.commands.size()) {
				loop = false;
			}
		}
	}
}

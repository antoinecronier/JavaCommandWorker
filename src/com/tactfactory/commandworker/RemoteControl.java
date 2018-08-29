package com.tactfactory.commandworker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RemoteControl {
	private Command command;
	private ExecutorService executor;

	public void setCommand(Command command) {
		this.command = command;
	}
	
	public ExecutorService getExecutor() {
		return this.executor;
	}
	
	public RemoteControl(){
		this.executor = Executors.newCachedThreadPool(); 
	}

	public void execute() {
		if (!this.executor.isShutdown()) {
			this.executor.submit(command);
		}
	}
}

package com.tactfactory.commandworker;

import java.util.concurrent.ExecutionException;
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

	public Integer execute(int commandIndex) {
		if (!this.executor.isShutdown() && !Thread.interrupted()) {
			try {
				this.executor.submit(command).get();
				return commandIndex;
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
				return null;
			}
		}else{
			return null;
		}
	}
}

package com.tactfactory.commandworker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CommandWorker extends Thread {

	private List<Command<?>> commands = new ArrayList<>();
	private Boolean loop = true;
	private RemoteControl control;
	private Integer currentCommand;
	private Integer maxCommand;
	private ExecutorService currentExecutor;

	public CommandWorker() {
		super();
		this.currentCommand = -1;
		this.maxCommand = 0;
	}

	public void add(Command<?> command) {
		this.commands.add(command);
		this.maxCommand++;
		
		if (!this.loop) {
			this.start(this.currentCommand);
		}
	}

	public void start(Integer commandIndex) {
		if (this.commands.size() >= commandIndex + 1 && commandIndex > -1) {
			this.loop = true;
			this.currentCommand = commandIndex;
			this.currentExecutor = Executors.newCachedThreadPool();
			this.control = new RemoteControl();
			this.currentExecutor.submit(this);
		}
	}
	
	public void playSingle(Integer commandIndex){
		if (commandIndex > -1 && commandIndex < this.commands.size()) {
			RemoteControl tempRemote = new RemoteControl();
			tempRemote.setCommand(this.commands.get(commandIndex));
			tempRemote.execute(commandIndex);
		}
	}
	
	public void previous(){
		if (this.currentCommand - 1 > -1) {
			this.currentCommand--;
			this.playSingle(this.currentCommand);
		}
	}
	
	public void next(){
		if (this.currentCommand + 1 < this.commands.size()) {
			this.currentCommand++;
			this.playSingle(this.currentCommand);
		}
	}

	public void start() {
		this.start(0);
	}

	public synchronized Integer halt() {
		try {
			this.control.getExecutor().shutdownNow();
			this.control.getExecutor().awaitTermination(1, TimeUnit.NANOSECONDS);

			for (int i = 0; i <= this.currentCommand && i < this.commands.size(); i++) {
				this.commands.remove(0);
			}
			
			return this.currentCommand;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return -1;
		} finally {
			this.currentExecutor.shutdownNow();
		}
	}

	@Override
	public void run() {
		this.loop = true;
		while (this.loop) {
			if (!this.control.getExecutor().isShutdown()) {
				this.control.setCommand(this.commands.get(this.currentCommand));
				this.control.execute(this.currentCommand);
				this.currentCommand++;
			}

			if (this.currentCommand == this.commands.size()) {
				this.loop = false;
			}
		}
	}
}

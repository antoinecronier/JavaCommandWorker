package com.tactfactory.commandworker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CommandWorker extends Thread {
	// /** Constructeur privé */
	// private CommandWorker()
	// {}
	//
	// /** Instance unique non préinitialisée */
	// private static CommandWorker INSTANCE = null;
	//
	// /** Point d'accès pour l'instance unique du singleton */
	// public static synchronized CommandWorker getInstance()
	// {
	// if (INSTANCE == null)
	// { INSTANCE = new CommandWorker();
	// }
	// return INSTANCE;
	// }

	private List<Command<?>> commands = new ArrayList<>();
	private Boolean loop = true;
	private RemoteControl control;
	private Integer currentCommand;
	private Integer maxCommand;
	private Integer lastPlayedCommandIndex;
	private List<Runnable> notPlayedCommands;
	private ExecutorService currentExecutor;

	public CommandWorker() {
		super();
		this.currentCommand = -1;
		this.maxCommand = 0;
		this.notPlayedCommands = new ArrayList<Runnable>();
	}

	public void add(Command<?> command) {
		this.commands.add(command);
		this.maxCommand++;
	}

	public void start(Integer commandIndex) {
		if (this.commands.size() > commandIndex + 1 && commandIndex > -1) {
			this.loop = true;
			this.currentCommand = commandIndex;
			this.currentExecutor = Executors.newCachedThreadPool();
			this.control = new RemoteControl();
			this.currentExecutor.submit(this);
		}
	}

	public void start() {
		this.start(0);
	}

	public synchronized Integer halt() {
		try {
			this.notPlayedCommands = this.control.getExecutor().shutdownNow();
			this.control.getExecutor().awaitTermination(1, TimeUnit.NANOSECONDS);

			for (int i = 0; i <= this.currentCommand && i < this.commands.size(); i++) {
				this.commands.remove(0);
			}
			
//			List<Command<?>> tempCommands = new ArrayList<Command<?>>();
//			for (Command<?> command : commands) {
//				if (!this.notPlayedCommands.contains(command)) {
//					tempCommands.add(command);
//				}
//			}
//			this.commands.removeAll(tempCommands);
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
				this.lastPlayedCommandIndex = this.control.execute(this.currentCommand);
				this.currentCommand++;
			}

			if (this.currentCommand == this.commands.size()) {
				this.loop = false;
			}
		}
	}
}

package com.tactfactory.commandworker;

public class Application {

	public static void main(String[] args){
		CommandWorker cW = new CommandWorker();
		
		cW.add(new Command<Void>() {

			@Override
			public Void call() throws Exception {
				execute();
				return null;
			}

			@Override
			public void execute() {
				System.out.println("ho");
			}
		});
		cW.add(new Command<Void>() {

			@Override
			public Void call() throws Exception {
				execute();
				return null;
			}

			@Override
			public void execute() {
				System.out.println("hi");
			}
		});
		cW.add(new Command<Void>() {

			@Override
			public Void call() throws Exception {
				execute();
				return null;
			}

			@Override
			public void execute() {
				System.out.println("ha");
			}
		});
		cW.add(new Command<Void>() {

			@Override
			public Void call() throws Exception {
				execute();
				return null;
			}

			@Override
			public void execute() {
				System.out.println("he");
			}
		});
		cW.add(new Command<Void>() {

			@Override
			public Void call() throws Exception {
				execute();
				return null;
			}

			@Override
			public void execute() {
				System.out.println("do");
			}
		});
		
		cW.start();
		
		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		cW.halt();
	}
}

package com.tactfactory.commandworker;

public class Application {

	public static void main(String[] args) {
		CommandWorker cW = new CommandWorker();

		for (int i = 0; i < 10; i++) {
			cW.add(new Command<Void>() {

				@Override
				public Void call() throws Exception {
					execute();
					return null;
				}

				@Override
				public void execute() {
					if (!Thread.interrupted()) {
						for (int j = 0; j < 10; j++) {
							if (!Thread.interrupted()) {
								System.out.println("test ::: " + j);
							}
						}
					}
				}
			});
		}

		cW.start();
		
		generateNewActionWithTime(cW);

		generateNewActionWithTime(cW);
		
		cW.playSingle(11);
		
		System.out.println("------------------");
		cW.previous();
		cW.previous();
		cW.previous();
		
		cW.next();
//		try {
//			Thread.sleep(3);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//
//		System.out.println(cW.halt());
//		System.out.println("----------------------");
//
//		try {
//			Thread.sleep(2000);
//			System.out.println("sleep ended");
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		cW.start();
//		
//		cW.add(new Command<Void>() {
//
//			@Override
//			public Void call() throws Exception {
//				execute();
//				return null;
//			}
//
//			@Override
//			public void execute() {
//				if (!Thread.interrupted()) {
//					System.out.println("coucou2");
//				}
//			}
//		});
//		
//		cW.add(new Command<Void>() {
//
//			@Override
//			public Void call() throws Exception {
//				execute();
//				return null;
//			}
//
//			@Override
//			public void execute() {
//				if (!Thread.interrupted()) {
//					System.out.println("coucou3");
//				}
//			}
//		});
	}

	private static void generateNewActionWithTime(CommandWorker cW) {
		try {
			Thread.sleep(3000);
			System.out.println("wait ended");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			
		cW.add(new Command<Void>() {

			@Override
			public Void call() throws Exception {
				execute();
				return null;
			}

			@Override
			public void execute() {
				if (!Thread.interrupted()) {
					System.out.println("coucou");
				}
			}
		});
	}
}

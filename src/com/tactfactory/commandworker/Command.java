package com.tactfactory.commandworker;

import java.util.concurrent.Callable;

public interface Command<T> extends Callable<T> {
  public void execute();
}
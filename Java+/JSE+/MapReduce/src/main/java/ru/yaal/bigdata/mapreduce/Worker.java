package ru.yaal.bigdata.mapreduce;

import java.util.concurrent.Callable;

public interface Worker<T> extends Callable<T> {
}

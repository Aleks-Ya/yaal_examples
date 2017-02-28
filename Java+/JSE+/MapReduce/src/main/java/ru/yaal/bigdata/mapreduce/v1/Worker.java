package ru.yaal.bigdata.mapreduce.v1;

import java.util.concurrent.Callable;

public interface Worker<T> extends Callable<T> {
}

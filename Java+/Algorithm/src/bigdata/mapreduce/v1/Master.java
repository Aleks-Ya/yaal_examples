package bigdata.mapreduce.v1;

import java.io.Reader;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface Master {
	List<? extends Callable<Result>> map(Reader data, long dataSize, int workerCount);

	Set<String> reduce(List<Future<Result>> jobs);
}

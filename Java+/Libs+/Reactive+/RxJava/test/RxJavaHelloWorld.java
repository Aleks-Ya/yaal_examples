import org.junit.Test;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class RxJavaHelloWorld {
    @Test
    public void superCompact() {
        Observable.just("Hello, world!").subscribe(System.out::println);
    }

    @Test
    public void compact() {
        Observable<String> myObservable = Observable.just("Hello, world!");
        @SuppressWarnings({"Convert2Lambda", "Anonymous2MethodRef"})
        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        };
        myObservable.subscribe(onNextAction);
    }

    @Test
    public void boilerplate() {
        Observable<String> myObservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("Hello, world!");
                        sub.onCompleted();
                    }
                }
        );

        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) {
                System.out.println(s);
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }
        };

        myObservable.subscribe(mySubscriber);
    }
}

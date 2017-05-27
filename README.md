# arch-lifecycle-rxjava-binder
This library provides LifecycleRxJavaBinder class for binding Android Architecture Component LifecycleOwner.

You can use LifecycleRxJavaBinder class like this.



```java
public class MainActivity extends LifecycleActivity {

    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable.timer(1, TimeUnit.SECONDS).repeat()
            .compose(LifecycleRxJavaBinder.applyObservable(this))
            .map(aLong -> "Tick"+(i++))
            .subscribe(s -> Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show());
```
# arch-lifecycle-rxjava-binder
This library provide LifecycleRxJavaBinder class for bind Android Architecture Component LifecycleOwner.

You can use LifecycleRxJavaBinder class like this.

``

```java
Observable.timer(1, TimeUnit.SECONDS).repeat()
    .compose(LifecycleRxJavaBinder.applyObservable(this))
    .map(aLong -> "Tick"+(i++))
    .subscribe(s -> Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show());
```
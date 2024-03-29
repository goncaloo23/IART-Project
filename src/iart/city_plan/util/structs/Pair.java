package iart.city_plan.util.structs;

public class Pair<T, U> {
    private T _t;
    private U _u;

    public Pair(T t, U u) {
        _t = t;
        _u = u;
    }

    public Pair(Pair<T, U> pair) {
        this._t = pair._t;
        this._u = pair._u;
    }

    public T getFirst() {
        return _t;
    }

    public U getSecond() {
        return _u;
    }
}

package dp.election;

public interface GenericHashBuilder<T> {
    public int hash(T key, int size);
}

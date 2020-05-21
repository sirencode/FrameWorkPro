import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/**
 * Created by shenyonghe on 2020/4/29.
 */
public class JniTest {
    private native void print();

    public static void main(String[] args) {
        WeakReference<Object> weakReference = new WeakReference<Object>(new Object());
        new JniTest().print();
    }

    static {
        System.loadLibrary("Hello");
    }
}

package child.ryl.child.test;

/**
 * Created by Administrator on 2017/3/8 0008.
 */

public @interface Test {
    String findById() default "";

    String findByStr() default "";
}

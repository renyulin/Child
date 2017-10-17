package child.ryl.child.test;

/**
 *
 */
public class Atest {
    @Test(findById = "str" ,findByStr="str")
    private String str;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}

package child.ryl.child.bean;

import java.util.List;

/**
 * 一级Bean
 */
public class Bean {
    private String group;
    private Boolean flag;
    private String id;
    private String name;
    private List<SecondBean> list;

    public List<SecondBean> getList() {
        return list;
    }

    public void setList(List<SecondBean> list) {
        this.list = list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}

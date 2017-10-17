package child.ryl.child.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 序列化bean
 * http://www.jb51.net/article/62626.htm
 */
public class SerializeBean implements Parcelable{
    private String name;
    private int age;

//    protected SerializeBean(Parcel in) {
//        name = in.readString();
//        age = in.readInt();
//    }

    public static final Creator<SerializeBean> CREATOR = new Creator<SerializeBean>() {
        @Override
        public SerializeBean createFromParcel(Parcel in) {
            SerializeBean person = new SerializeBean();
            person.name = in.readString();
            person.age = in.readInt();

            return person;
        }

        @Override
        public SerializeBean[] newArray(int size) {
            return new SerializeBean[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }
}

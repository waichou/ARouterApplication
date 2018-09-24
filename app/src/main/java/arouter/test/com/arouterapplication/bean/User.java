package arouter.test.com.arouterapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author zhouwei
 * @date 2018/9/24
 * @time 19:03
 * @desc
 */
public class User implements Parcelable {

    private String name;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }

    public User(String name) {
        this.name = name;
    }

//    public User() {
//    }

    protected User(Parcel in) {
        this.name = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}

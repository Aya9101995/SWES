package swes.swes.DataClasses;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by win on 01/05/2017.
 */

public class CourseInfo {
    String photo;
    ArrayList<Map<String,String>> prerequisets ;
    String desc;

    public CourseInfo() {
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public ArrayList<Map<String, String>> getPrerequisets() {
        return prerequisets;
    }

    public void setPrerequisets(ArrayList<Map<String, String>> prerequisets) {
        this.prerequisets = prerequisets;
    }

    public String getDesc() {

        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

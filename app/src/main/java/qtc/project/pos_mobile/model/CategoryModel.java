package qtc.project.pos_mobile.model;

public class CategoryModel extends BaseResponseModel {

    private String id = "";
    private String name = "";
    private Object description = "";
    private Object image = "";

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Object getDescription() {
        return description;
    }

    public Object getImage() {
        return image;
    }
}

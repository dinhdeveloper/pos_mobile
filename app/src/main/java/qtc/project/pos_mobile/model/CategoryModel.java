package qtc.project.pos_mobile.model;

public class CategoryModel extends BaseResponseModel {

    private String id = "";
    private String id_business = "";
    private String name = "";
    private Object description = "";
    private Object image = "";

    public String getId() {
        return id;
    }

    public String getId_business() {
        return id_business;
    }

    public void setId_business(String id_business) {
        this.id_business = id_business;
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

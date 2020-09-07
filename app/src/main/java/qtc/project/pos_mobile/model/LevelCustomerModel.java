package qtc.project.pos_mobile.model;

public class LevelCustomerModel extends BaseResponseModel {


    private String id;
    private String id_business;
    private String id_code;
    private String name;
    private String description;
    private String image;
    private String discount;

    private String total_customer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_business() {
        return id_business;
    }

    public void setId_business(String id_business) {
        this.id_business = id_business;
    }

    public String getId_code() {
        return id_code;
    }

    public void setId_code(String id_code) {
        this.id_code = id_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
    public String getTotal_customer() {
        return total_customer;
    }

    public void setTotal_customer(String total_customer) {
        this.total_customer = total_customer;
    }
}

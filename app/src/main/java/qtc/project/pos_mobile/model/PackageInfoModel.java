package qtc.project.pos_mobile.model;

public class PackageInfoModel {

    private String pack_id;
    private String manufacturer_id;
    private String manufacturer_name;
    private String manufacturer_email;
    private String manufacturer_phone_number;
    private String manufacturer_address;
    private String manufacturing_date;
    private String import_date;
    private String description;
    private String import_price;
    private String sale_price;
    private String quantity_order;
    private String quantity_storage;

    //add
    private String quantityCar = "";

    public String getQuantityCar() {
        return quantityCar;
    }

    public void setQuantityCar(String quantityCar) {
        this.quantityCar = quantityCar;
    }


    public String getPack_id() {
        return pack_id;
    }

    public void setPack_id(String pack_id) {
        this.pack_id = pack_id;
    }

    public String getManufacturer_id() {
        return manufacturer_id;
    }

    public void setManufacturer_id(String manufacturer_id) {
        this.manufacturer_id = manufacturer_id;
    }

    public String getManufacturer_name() {
        return manufacturer_name;
    }

    public void setManufacturer_name(String manufacturer_name) {
        this.manufacturer_name = manufacturer_name;
    }

    public String getManufacturer_email() {
        return manufacturer_email;
    }

    public void setManufacturer_email(String manufacturer_email) {
        this.manufacturer_email = manufacturer_email;
    }

    public String getManufacturer_phone_number() {
        return manufacturer_phone_number;
    }

    public void setManufacturer_phone_number(String manufacturer_phone_number) {
        this.manufacturer_phone_number = manufacturer_phone_number;
    }

    public String getManufacturer_address() {
        return manufacturer_address;
    }

    public void setManufacturer_address(String manufacturer_address) {
        this.manufacturer_address = manufacturer_address;
    }

    public String getManufacturing_date() {
        return manufacturing_date;
    }

    public void setManufacturing_date(String manufacturing_date) {
        this.manufacturing_date = manufacturing_date;
    }

    public String getImport_date() {
        return import_date;
    }

    public void setImport_date(String import_date) {
        this.import_date = import_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImport_price() {
        return import_price;
    }

    public void setImport_price(String import_price) {
        this.import_price = import_price;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public String getQuantity_order() {
        return quantity_order;
    }

    public void setQuantity_order(String quantity_order) {
        this.quantity_order = quantity_order;
    }

    public String getQuantity_storage() {
        return quantity_storage;
    }

    public void setQuantity_storage(String quantity_storage) {
        this.quantity_storage = quantity_storage;
    }
}

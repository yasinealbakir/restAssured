package api.payload;

public class Apply {

    public String getBasvuruId() {
        return basvuruId;
    }

    public void setBasvuruId(String basvuruId) {
        this.basvuruId = basvuruId;
    }

    //field oluştururken istek atılan parametre adıyla aynı olmalıdır.
    String basvuruId;
}

package nanodevlab.imran.aqwalehazratalias.OfflineDataBase.Models;

public class DuaModel {

    private String Title;
    private String DuaText;


    public DuaModel() {
    }

    public DuaModel(String title, String duaText) {
        Title = title;
        DuaText = duaText;
    }

    public String getTitle() {
        return Title;
    }

    public String getDuaText() {
        return DuaText;
    }


    public void setTitle(String title) {
        Title = title;
    }

    public void setDuaText(String duaText) {
        DuaText = duaText;
    }
}

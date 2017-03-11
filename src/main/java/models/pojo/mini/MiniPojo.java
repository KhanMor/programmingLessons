package models.pojo.mini;

/**
 * Created by Mordr on 10.03.2017.
 */
public class MiniPojo {
    private Integer id;
    private String name;

    public MiniPojo() {

    }

    public MiniPojo(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

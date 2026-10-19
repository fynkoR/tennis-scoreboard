package pet.project.dto;

public class AddPointRequest {
    private String name;

    public AddPointRequest(){}

    public AddPointRequest(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

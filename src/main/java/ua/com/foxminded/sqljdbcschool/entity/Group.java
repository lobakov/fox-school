package ua.com.foxminded.sqljdbcschool.entity;

public class Group {

    private Long id;
    private String name;

    public Group() {

    }

    public Group(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = prime + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Group)) {
            return false;
        }
        Group other = (Group) obj;
        return (id == other.id) &&
               (name.equals(other.name));
    }

    @Override
    public String toString() {
        return id + ". " + name;
    }
}

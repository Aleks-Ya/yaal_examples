package serialize.studytrails.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Artist {
	public String name;
    public Date birthDate;
    public int age;
    public String homeTown;
    public List<String> awardsWon = new ArrayList<String>();
}

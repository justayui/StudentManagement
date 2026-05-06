package raisetech.student.management;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

	private String name;
	private String age;
	private List<Map<String,String>>studentList=new ArrayList<>();

	public static void main(String[] args) {
			SpringApplication.run(Application.class, args);
	}

	@GetMapping("/studentInfo")
	public String getStudentInfo(){
		String result = "";
		for(Map<String,String> student : studentList){
			result += student.get("name") + " " + student.get("age") + "歳\n";
		}
		return result;
	}

	@PostMapping("/studentInfo")
	public void setStudentInfo(@RequestBody Map<String,String> student){
   this.name = student.get("name");
	 this.age = student.get("age");
	 studentList.add(student);
	}
}
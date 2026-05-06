package raisetech.student.management;

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

	private String name = "Rina Tanaka";
	private String age = "3";

	public static void main(String[] args) {
			SpringApplication.run(Application.class, args);
	}

	@GetMapping("/studentInfo")
	public String getStudentInfo(){
		return name + " " + age+"歳";
	}

	@PostMapping("/studentInfo")
	public void setStudentInfo(@RequestBody Map<String,String>student){
   this.name = student.get("name");
	 this.age = student.get("age");
	}



}
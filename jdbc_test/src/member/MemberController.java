package member;

public class MemberController {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MemberService memberservice = new MemberServiceImpl();
		
		memberservice.startProgram();
	}

}

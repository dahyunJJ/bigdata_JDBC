package member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MemberServiceImpl implements MemberService {
	MemberDAO mDAO =  new MemberDAO();
	Scanner sc = new Scanner(System.in);

	@Override
	public void startProgram() {

		int count = 0;

		while (true) {

			int choice = printMenu();

			switch (choice) {
			case 1:
				displayMsg("1. 회원 정보 등록");
				insertMember();
				break;
			case 2:
				displayMsg("2. 회원 정보 수정");
				updateMember();
				break;
			case 3:
				displayMsg("3. 회원 정보 삭제");
				deleteMember();
				break;
			case 4:
				displayMsg("4. 회원 정보 출력(이름)");
				printMember();
				break;
			case 5:
				displayMsg("5. 회원 전체 정보 출력");
				printAllMembers();
				break;
			case 6:
				displayMsg("프로그램 종료 ~!!");
				count++;
				break;
			default:
				displayMsg("잘못된 숫자가 입력됨. 1~6 사이의 숫자 입력 가능");
				break;
			}

			if (count == 1) {
				break;
			}
		}

	}

	// 0. 프로그램 메뉴 출력 및 선택
	public int printMenu() {
		displayMsg("===== 회원 관리 프로그램 =====");
		displayMsg("1. 회원 정보 등록");
		displayMsg("2. 회원 정보 수정");
		displayMsg("3. 회원 정보 삭제");
		displayMsg("4. 회원 정보 출력(이름)");
		displayMsg("5. 회원 전체 정보 출력");
		displayMsg("6. 프로그램 종료");
		System.out.print("[선택] : ");

		int choice = sc.nextInt();

		return choice;
	}
	
	// 1. 회원 정보 등록
	public void insertMember() {
			// 회원 정보를 등록할 member 객체 생성자
			Member member = new Member();

			System.out.print("회원 아이디를 입력해 주세요 : ");
			String memberId = sc.next();

			System.out.print("회원 비밀번호 : ");
			String memberPw = sc.next();

			System.out.print("회원 이름 : ");
			String memberName = sc.next();
			
			System.out.print("생년월일(ex 19900101) : ");
			String memberBirth = sc.next();

			System.out.print("이메일 주소를 입력해 주세요. : ");
			String email = sc.next();

			System.out.print("연락처 정보를 입력해 주세요. : ");
			String phone = sc.next();

			// member에 회원정보 셋팅
			member.setMemberId(memberId);
			member.setMemberPw(memberPw);
			member.setMemberName(memberName);
			member.setMemberBirth(memberBirth);
			member.setMemberEmail(email);
			member.setMemberPhone(phone);

			int chk = 0;
			
			chk = mDAO.insertMember(member);
			
			if(chk > 0) {
				System.out.println("회원 정보가 등록되었습니다.");
			} else {
				System.out.println("회원 등록에 실패하였습니다.");
			}
	}
	
	// 2. 회원 정보 수정
	public void updateMember() {
		System.out.print("회원명을 입력해 주세요 : ");
		sc.nextLine();
		String name = sc.nextLine();

		List<HashMap<String, Object>> memberList = new ArrayList();
		
		memberList = mDAO.printSearchMembers(name);
		
		System.out.println("아이디\t비밀번호\t회원명\t생년월일\t전화번호\t이메일");

		for (int i = 0; i < memberList.size(); i++) {
			System.out.print(memberList.get(i).get("member_id") + "\t");
			System.out.print(memberList.get(i).get("member_pw") + "\t");
			System.out.print(memberList.get(i).get("member_name") + "\t");
			System.out.print(memberList.get(i).get("member_birth") + "\t");
			System.out.print(memberList.get(i).get("member_phone") + "\t");
			System.out.println(memberList.get(i).get("member_email") + "\t");

			}
			
			System.out.println("수정할 회원의 순번을 입력하세요 >>>>> ");
			int num = sc.nextInt();
			int memberId = Integer.parseInt(memberList.get(num-1).get("member_idx").toString());
			
			System.out.println("변경 될 연락처를 입력하세요 >>>>> ");
			sc.nextLine();
			String updatePhone = sc.nextLine();
			
			int resultChk = 0;
			resultChk = mDAO.updateSearchMember(memberId, updatePhone);
			
			if(resultChk > 0) {
				System.out.println("회원 정보가 수정되었습니다.");
			} else {
				System.out.println("회원 정보 수정에 실패하였습니다.");
			}		
		}	
	
	// 3. 회원 정보 삭제
	public void deleteMember() {
		System.out.print("회원명을 입력해 주세요 : ");
		sc.nextLine();
		String name = sc.nextLine();

		List<HashMap<String, Object>> memberList = new ArrayList();
		
		memberList = mDAO.printSearchMembers(name);
		
		System.out.println("아이디\t비밀번호\t회원명\t생년월일\t전화번호\t이메일");

		for (int i = 0; i < memberList.size(); i++) {
			System.out.print(memberList.get(i).get("member_id") + "\t");
			System.out.print(memberList.get(i).get("member_pw") + "\t");
			System.out.print(memberList.get(i).get("member_name") + "\t");
			System.out.print(memberList.get(i).get("member_birth") + "\t");
			System.out.print(memberList.get(i).get("member_phone") + "\t");
			System.out.println(memberList.get(i).get("member_email") + "\t");

			}
			
			System.out.println("삭제할 회원의 순번을 입력하세요 >>>>> ");
			int num = sc.nextInt();
			int memberId = Integer.parseInt(memberList.get(num-1).get("member_idx").toString());
			
			System.out.println("삭제 될 회원명을 입력하세요 >>>>> ");
			sc.nextLine();
			String deleteName = sc.nextLine();
			
			int resultChk = 0;
			resultChk = mDAO.deleteSearchMemeber(memberId, deleteName);
			
			if(resultChk > 0) {
				System.out.println("회원 정보가 삭제되었습니다.");
			} else {
				System.out.println("회원 정보 삭제에 실패하였습니다.");
			}		
	}
	
	// 4. 회원 정보 출력 (회원명)
	public void printMember() {
		List<HashMap<String, Object>> memberList = new ArrayList();

		System.out.print("회원명을 입력해 주세요 : ");
		sc.nextLine();
		String name = sc.nextLine();
		
		memberList = mDAO.printSearchMembers(name);

		boolean flag = false;

		for (int i = 0; i < memberList.size(); i++) {
			if (name.equals(memberList.get(i).get("member_name"))) {
				System.out.println("===== 검색하신 회원 정보 =====");
				System.out.println("회원ID : " + memberList.get(i).get("member_id") + "\t");
				System.out.println("회원PW : " + memberList.get(i).get("member_pw") + "\t");
				System.out.println("회원명 : " + memberList.get(i).get("member_name") + "\t");
				System.out.println("생년월일 : " + memberList.get(i).get("member_birth") + "\t");
				System.out.println("전화번호 : " + memberList.get(i).get("member_phone") + "\t");
				System.out.println("이메일 : " + memberList.get(i).get("member_email") + "\t");

				flag = true;
				break;	
			}
		}

		if (flag == false) {
			System.out.println("회원이 존재하지 않습니다.");
		}

	}
	
	//5. 회원 전체 정보 출력
	public void printAllMembers() {
		List<HashMap<String, Object>> bookList = new ArrayList();
		bookList = mDAO.printAllMembers();
		
		System.out.println("아이디\t비밀번호\t\t회원명\t\t생년월일\t\t전화번호\t\t이메일");
		
		for(int i = 0; i < bookList.size(); i++) {
			System.out.print(bookList.get(i).get("member_id") + "\t");
			System.out.print(bookList.get(i).get("member_pw") + "\t\t");
			System.out.print(bookList.get(i).get("member_name") + "\t\t");
			System.out.print(bookList.get(i).get("member_birth") + "\t");
			System.out.print(bookList.get(i).get("member_phone") + "\t");
			System.out.println(bookList.get(i).get("member_email") + "\t");
		}
	}

	// 메시지 출력용
	public void displayMsg(String msg) {
		System.out.println(msg);
	}

}

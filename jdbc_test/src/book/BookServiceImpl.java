package book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class BookServiceImpl implements BookService {
	
	BookDAO bookDAO = new BookDAO();
	Scanner sc = new Scanner(System.in);
	
	@Override
	public void startProgram() {

		while (true) {

			int choice = printMenu();

			switch(choice) {
			case 1 :
				System.out.println("1. 도서 정보 등록");
				insertBook();
				break;
			case 2 :
				System.out.println("2. 도서 정보 수정");
				updateBook();
				break;
			case 3 :
				System.out.println("3. 도서 정보 삭제");
				deleteBook();
				break;
			case 4 :
				System.out.println("4. 도서 정보 출력(도서명)");
				printBook();
				break;
			case 5 :
				System.out.println("5. 도서 전체 정보 출력");
				printAllBooks();
				break;
			case 6 :
				System.out.println("프로그램 종료 ~!!");
				
				break;
			default :
				// "잘못된 숫자가 입력됨. 1~6 사이의 숫자 입력 가능");
				break;
			}
		}
	}

	@Override
	public int printMenu() {
		System.out.println("===== 도서 관리 프로그램 =====");
		System.out.println("1. 도서 정보 등록");
		System.out.println("2. 도서 정보 수정");
		System.out.println("3. 도서 정보 삭제");
		System.out.println("4. 도서 정보 출력(도서명)");
		System.out.println("5. 도서 전체 정보 출력");
		System.out.println("6. 프로그램 종료");
		System.out.print("[선택] : ");

		int choice = sc.nextInt();

		return choice;
	}
	
	// 1. 도서 정보 등록
	public void insertBook() {
		System.out.println("도서명을 입력하세요 >>>>> ");
		sc.nextLine();
		String title = sc.nextLine(); // next() : 띄워쓰기하면 종료, nextLine() : 띄워쓰기까지 인식
		
		System.out.println("도서가격을 입력하세요 >>>>> ");
		int price = sc.nextInt();
		
		System.out.println("저자를 입력하세요 >>>>> ");
		sc.nextLine();
		String author = sc.nextLine();
		
		System.out.println("출판사를 입력하세요 >>>>> ");
		String publisher = sc.nextLine();
		
		System.out.println("출판년도를 입력하세요 >>>>> ");
		String pubYear = sc.nextLine();
		
		System.out.println("ISBN을 입력하세요 >>>>> ");
		String isbn = sc.nextLine();
		
		System.out.println("총 페이지수를 입력하세요 >>>>> ");
		int page = sc.nextInt();
		
		BookInfo bookInfo = new BookInfo();
		bookInfo.setTitle(title);
		bookInfo.setPrice(price);
		bookInfo.setAuthor(author);
		bookInfo.setPublisher(publisher);
		bookInfo.setPubYear(pubYear);
		bookInfo.setIsbn(isbn);
		bookInfo.setPage(page);
		
		int resultChk = 0;
		resultChk = bookDAO.insertBook(bookInfo);
		
		if(resultChk > 0) {
			System.out.println("도서가 등록되었습니다.");
		} else {
			System.out.println("도서 등록에 실패하였습니다.");
		}		
	}
	
	// 2. 도서 정보 수정
	public void updateBook() {
		System.out.print("도서명을 입력해 주세요 : ");
		sc.nextLine();
		String findTitle = sc.nextLine();

		List<HashMap<String, Object>> bookList = new ArrayList();
		
		bookList = bookDAO.printSearchBooks(findTitle);
		
		System.out.println("도서ID\t도서명\t\t저자\t출판사\t등록일");

		for (int i = 0; i < bookList.size(); i++) {
				System.out.print(bookList.get(i).get("book_id") + "\t");
				System.out.print(bookList.get(i).get("book_title") + "\t");
				System.out.print(bookList.get(i).get("book_author") + "\t");
				System.out.print(bookList.get(i).get("book_publisher") + "\t\t");
				System.out.println(bookList.get(i).get("create_date") + "\t");
			}
			
			System.out.println("수정할 도서의 순번을 입력하세요 >>>>> ");
			int num = sc.nextInt();
			int bookId = (int) bookList.get(num-1).get("book_id");
			
			System.out.println("변경 될 도서명을 입력하세요 >>>>> ");
			sc.nextLine();
			String updateTitle = sc.nextLine();
			
			int resultChk = 0;
			resultChk = bookDAO.updateSearchBook(bookId, updateTitle);
			
			if(resultChk > 0) {
				System.out.println("도서가 수정되었습니다.");
			} else {
				System.out.println("도서 수정에 실패하였습니다.");
			}		
		}	
	
	// 3-1. 도서 정보 삭제 (같은 도서명 있을 경우)
	public void deleteBook() {
		System.out.print("도서명을 입력해 주세요 : ");
		sc.nextLine();
		String findTitle = sc.nextLine();

		List<HashMap<String, Object>> bookList = new ArrayList();
		
		bookList = bookDAO.printSearchBooks(findTitle);
		
		System.out.println("도서ID\t도서명\t\t저자\t출판사\t등록일");

		for (int i = 0; i < bookList.size(); i++) {
				System.out.print(bookList.get(i).get("book_id") + "\t");
				System.out.print(bookList.get(i).get("book_title") + "\t");
				System.out.print(bookList.get(i).get("book_author") + "\t");
				System.out.print(bookList.get(i).get("book_publisher") + "\t\t");
				System.out.println(bookList.get(i).get("create_date") + "\t");
			}
			
			System.out.println("삭제할 도서의 순번을 입력하세요 >>>>> ");
			int num = sc.nextInt();
			int bookId = (int) bookList.get(num-1).get("book_id");
			
			System.out.println("삭제 될 도서명을 입력하세요 >>>>> ");
			sc.nextLine();
			String deleteTitle = sc.nextLine();
			
			int resultChk = 0;
			resultChk = bookDAO.deleteSearchBook(bookId, deleteTitle);
			
			if(resultChk > 0) {
				System.out.println("도서가 삭제되었습니다.");
			} else {
				System.out.println("도서 삭제에 실패하였습니다.");
			}		
	}
	
	
	// 3. 도서 정보 삭제 (도서명)
//	public void deleteBook() {
//		System.out.print("도서명을 입력해 주세요 : ");
//		sc.nextLine();
//		String findTitle = sc.nextLine();
//				
//		int resultChk = 0;
//		resultChk = bookDAO.deleteSearchBook(findTitle);
//		
//		if(resultChk > 0) {
//			System.out.println("도서가 삭제되었습니다.");
//		} else {
//			System.out.println("도서 삭제에 실패하였습니다.");
//		}		
//		
//	}
	
	// 4. 도서 정보 출력 (도서명)
	public void printBook() {
		List<HashMap<String, Object>> bookList = new ArrayList();

		System.out.print("조회할 도서명을 입력해 주세요 : ");
		sc.nextLine();
		String findTitle = sc.nextLine();
		
		bookList = bookDAO.printSearchBooks(findTitle);

		boolean flag = false;

		for (int i = 0; i < bookList.size(); i++) {
			if (findTitle.equals(bookList.get(i).get("book_title"))) {
				System.out.println("===== 검색하신 도서 정보 =====");
				System.out.println("도서명 : " + bookList.get(i).get("book_title") + "\t");
				System.out.println("도서가격 : " + bookList.get(i).get("book_price") + "\t");
				System.out.println("저자 : " + bookList.get(i).get("book_author") + "\t");
				System.out.println("출판사 : " + bookList.get(i).get("book_publisher") + "\t\t");
				System.out.println("출판년도 : " + bookList.get(i).get("book_pubYear") + "\t");
				System.out.println("isbn : " + bookList.get(i).get("book_isbn") + "\t");
				System.out.println("페이지수 : " + bookList.get(i).get("book_page") + "\t");

				flag = true;
				break;	
			}
		}

		if (flag == false) {
			System.out.println("도서가 존재하지 않습니다.");
		}

	}
	
	// 5. 도서 전체 정보 출력
	public void printAllBooks() {
		List<HashMap<String, Object>> bookList = new ArrayList();
		bookList = bookDAO.printAllBooks();
		
		System.out.println("도서명\t저자\t\t출판사\t\t생성일자");
		
		for(int i = 0; i < bookList.size(); i++) {
			System.out.print(bookList.get(i).get("book_title") + "\t");
			System.out.print(bookList.get(i).get("book_author") + "\t\t");
			System.out.print(bookList.get(i).get("book_publisher") + "\t\t");
			System.out.println(bookList.get(i).get("create_date") + "\t");
		}
	}
	
}

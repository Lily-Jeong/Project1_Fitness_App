package project01;
import java.util.*;
import java.lang.Exception;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.NoSuchFileException;

public class Member extends Person {
	private int id;
	private int purpose;
	private int memberType;
	
	
	//getter & setter
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPurpose() {
		return purpose;
	}
	public void setPurpose(int purpose) {
		this.purpose = purpose;
	}
	public int getMemberType() {
		return memberType;
	}
	public void setMemberType(int memberType) {
		this.memberType = memberType;
	}
	
	//정보를 입력받는 생성자
	public Member() {
		
	}
	public Member(int id, String name, String address, int purpose, int memberType) throws IDFormatException {
		if(id == 0) {
			throw new IDFormatException("회원번호는 0번일 수 없습니다. 다시 입력해주세요.");
		} else if(id > 2000 || id < 1000) {
			throw new IDFormatException("회원번호는 1000번대로만 지정 가능합니다. 다시 입력해주세요.");
		}
		this.id = id;
		this.name = name;
		this.address = address;
		this.purpose = purpose;
		this.memberType = memberType;
		
	}
	
	//정보 저장되는 배열.
	ArrayList<Member> member = new ArrayList<Member>(30);
	
	
	//1-1 회원 등록.
	public void memberRegister() throws IOException {	
		
		HealthClubApp hc = new HealthClubApp();
		int id;
		String name;
		String address;
		int purpose;
		int memberType;
		
		Scanner sc = new Scanner(System.in);
		System.out.println("회원번호를 입력하세요 => ");
		id = sc.nextInt();
		System.out.println("이름을 입력하세요 => ");
		name = sc.next();
		System.out.println("주소를 입력하세요 => ");
		address = sc.next();
		System.out.println("운동 목적을 입력하세요 => ");
		System.out.println("1)체중감량, 2)근력증진, 3)지구력/체력 증진");
		purpose = sc.nextInt();
		if (purpose > 3) {
			System.out.println("보기에 없는 선택지입니다. 다시 입력해주세요.");
			return;
		} else if (purpose < 1) {
			System.out.println("보기에 없는 선택지입니다. 다시 입력해주세요.");
			
		} else {
			System.out.println("회원권 타입을 입력하세요 => ");
			System.out.println("1)1개월권, 2)3개월권, 3)6개월권, 4)12개월권");
			memberType = sc.nextInt();
			if (memberType > 4) {
				System.out.println("보기에 없는 선택지입니다. 다시 입력해주세요.");
				return;
			} else if (memberType < 1) {
				System.out.println("보기에 없는 선택지입니다. 다시 입력해주세요.");
				return;
			} else {
				try {
					member.add(new Member(id, name, address, purpose, memberType));
					String title = "---------- 회원정보 ----------";
					String content = title+"\n이름 : "+name+"\n회원번호 : "+id+"\n주소 : "+address
							+"\n운동 목적 : "+purpose+"\n회원권 : "+memberType+"\n\n";
					while(true) {
						hc.saveMenu();
						int i = sc.nextInt();
						switch(i) {
						case 1 : String fileName = "/Users/lilyjeong/Desktop/Project01_JAVA/FitnessApp/memberlist.txt";
							try {
								FileWriter fw = new FileWriter(fileName, true);
								fw.write(content);
								fw.flush();
								fw.close();
								System.out.println("회원 정보가 정상적으로 등록되었습니다.");
								return;
							} catch(IOException e) {
								System.out.println("파일을 저장하는 중 에러가 발생했습니다."+e.getMessage());
							}
						}

					}
				} catch (IDFormatException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	//이름을 검색해서 본인의 정보만 표시.
	@Override
	public void searchInfo() throws IOException {
		System.out.println("이름을 입력하세요 => ");
		Scanner sc = new Scanner(System.in);
		name = sc.next();
		String str = Files.readString(Paths.get("/Users/lilyjeong/Desktop/Project01_JAVA/FitnessApp/memberlist.txt"));
		String[] token = str.split("\n\n");
		for(int i=0; i<token.length; i++) {
			if(token[i].contains(name)) {
				System.out.println(token[i]);
			}
		}
		/* for(int i=0; i< member.size(); i++) {
			if(name.equals(member.get(i).getName())) {
					System.out.println("회원 번호 : "+member.get(i).getId()+"\n이름 : "+member.get(i).getName()
							+"\n주소 : "+member.get(i).getAddress()+"\n운동 목적 : "+member.get(i).getPurpose()
							+"\n회원권 : "+member.get(i).getMemberType());
			}
		} */
	}//searchInfo()---------------------
	//전체 정보를 다 출력함.
	public void showInfo() throws IOException {
		HealthClubApp hc = new HealthClubApp();
		hc.showInfo();
		String fname = "/Users/lilyjeong/Desktop/Project01_JAVA/FitnessApp/memberlist.txt";
		File file = new File(fname);
		
		FileInputStream input = new FileInputStream(file);	//노드 연결
		InputStreamReader inputreader = new InputStreamReader(input, "UTF-8");
		OutputStreamWriter outputwriter = new OutputStreamWriter(System.out, "UTF-8");
		
		int n = 0;
		while((n=inputreader.read()) != -1) {
			outputwriter.write(n);
			outputwriter.flush();
		}
		inputreader.close();
		/* for(int i=0; i< member.size(); i++) {
				System.out.println("회원 번호 : "+member.get(i).getId()+"\n이름 : "+member.get(i).getName()
						+"\n주소 : "+member.get(i).getAddress()+"\n운동 목적 : "+member.get(i).getPurpose()
						+"\n회원권 : "+member.get(i).getMemberType());
				System.out.println("--------------------"); 
		} */
	}//showInfo()----------------------
	
	//회원정보 삭제.
	@Override
	public void deleteInfo() throws IOException {
		System.out.println("삭제할 회원의 이름을 입력하세요 => ");
		Scanner sc = new Scanner(System.in);
		name = sc.next();
		System.out.println("삭제할 회원 번호를 입력하세요 => ");
		String num = sc.next();
		String str = Files.readString(Paths.get("/Users/lilyjeong/Desktop/Project01_JAVA/FitnessApp/memberlist.txt"));
		String[] token = str.split("\n\n");
		
		File file = new File("/Users/lilyjeong/Desktop/Project01_JAVA/FitnessApp/memberlist.txt");
		FileWriter fw = new FileWriter(file);
		fw.write("");
		fw.flush();
		ArrayList<String> tokens = new ArrayList<>(Arrays.asList(token));
		for(int i=0; i<token.length; i++) {
			if(token[i].contains(name) && token[i].contains(num)) {
				System.out.println("회원번호 : "+num+", 이름 : "+name+" 회원님의 정보를 삭제합니다.");
			} else {
				String returned = tokens.get(i)+"\n\n";
				fw.write(returned);
				fw.flush();
			}
		}
		System.out.println("삭제 완료");
		fw.close();
		/*for(int i=0; i<member.size(); i++) {
			if(name.equals(member.get(i).getName())) {
				if(id == member.get(i).getId()) {
					member.remove(member.get(i));
					//System.out.println("회원번호 "+id+"번, "+name+" 회원님의 정보가 삭제되었습니다.");
				} else {
					System.out.println("찾을 수 없는 회원 정보입니다. 다시 입력해주세요.");
					return;
				} 
			}
		} */
	}//----------
	
	public void memberLimit() throws IOException {
		try {
			String str = Files.readString(Paths.get("/Users/lilyjeong/Desktop/Project01_JAVA/FitnessApp/memberlist.txt"));
			String[] token = str.split("\n\n");
			int count = 0;
			String x = "회원";
			ArrayList<String> tokens = new ArrayList<>(Arrays.asList(token));
			for(int i = 0; i < token.length; i++) {
				if(token[i].contains(x)) {
					count += 1;
					if(count == 30) {
						System.out.println("회원 등록이 마감되었습니다. 메인 화면으로 돌아갑니다.");
						return;
					}
				}
			}
		} catch(NoSuchFileException e) {
			
		}
	}

}

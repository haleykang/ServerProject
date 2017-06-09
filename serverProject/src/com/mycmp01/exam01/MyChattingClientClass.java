package com.mycmp01.exam01;

// 클래스에서 사용할 클래스와 인터페이스들이 있는 패키지들을 작성하기
import java.net.*; // ServerSocket, Socket 등등
import java.io.*; // InputStream, OutputStream, BufferedWriter, BufferedReader
// IOException, PrintWriter 등등

// 스캐너 클래스를 사용해서 서버로 보낼 메시지를 사용자로부터 입력 받기
import java.util.Scanner;

// 채팅 클라이언트 클래스
public class MyChattingClientClass {

	// 스캐너 변수 선언하기
	private static Scanner scanner;

	// 채팅 서버와 통신(데이터 전송과 수신)에 사용할 변수 선언하기
	private static Socket socket;

	// isConnected( ) 함수의 결과 값을 보관할 변수 선언
	private static boolean resultIsConnected = false;
	// -> 현재 서버와 연결되어 있는지 유무를 기억하고 있는 변수

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// 예외상황을 대비하기 위한 try~catch( ) 블럭 작성
		try {

			// 스캐너 변수를 메모리에 생성합니다.
			scanner = new Scanner(System.in);

			// 사용자가 입력한 메시지를 보관할 임시 변수 선언
			String tempInputMessage = "";

			// 서버 연결을 위한 소켓 객체를 생성 : 서버 아이피 주소와 포트 번호
			socket = new Socket(MyChattingServerClass.SERVER_IP_ADDR, MyChattingServerClass.SERVER_PORT_NO);

			// 서버와 연결이 된 경우에 실행되는 명령문 작성
			if (socket != null) {

				System.out.println("서버와 연결되었습니다.");

				// 사용자가 입력한 메시지를 서버로 전송
				OutputStream outputStream;
				BufferedWriter bw = null;

				// 무한 반복문 : 논리적으로 끝나지 않는 반복문
				// -> while( 참 값 ) 또는 while( 변수 이름 ) : 변수의 자료형은
				// boolean 변수이름 = true;
				while (true) {

					if (socket == null || socket.isClosed()) {

						socket = new Socket(MyChattingServerClass.SERVER_IP_ADDR, MyChattingServerClass.SERVER_PORT_NO);

					}

					outputStream = socket.getOutputStream();

					bw = new BufferedWriter(new OutputStreamWriter(outputStream));

					// 서버에 전달할 메시지를 사용자로부터 입력 받습니다.
					System.out.print("서버로 보낼 메시지를 입력하세요(중단은 q): ");

					tempInputMessage = scanner.nextLine();

					if (tempInputMessage == null || tempInputMessage.length() == 0) {
						// 소켓 종료 하기 서버와의 연결 끊기
						// socket.close();
						break;
					}

					bw.write(tempInputMessage);
					bw.flush();
					bw.close();

					// 새로 추가된 내용 : 사용자가 q를 입력하면 반복문 탈출
					// compareToIgnoreCase( ) : 대소문자를 검사 하지 않음
					// -> 비교하고자 하는 알파벳 문자가 q인 경우 0을 반환
					if (tempInputMessage.compareToIgnoreCase("q") == 0) {
						// socket.close();
						break;
					}
				} // end of while()
			}

		} catch (IOException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			// 다 사용한 메모리 또는 데이터베이스 또는 파일 등을 메모리에서
			// 삭제합니다. -> 스트림(메모리)을 삭제

			try {

				// 소켓 변수 닫기(close() 함수 실행)
				if (socket != null) {
					socket.close();
					System.out.println("socket 변수 종료");
					socket = null;
				}

				// 스캐너 변수 닫기
				if (scanner != null) {
					scanner.close();
					System.out.println("scanner 변수 종료");
					scanner = null;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}

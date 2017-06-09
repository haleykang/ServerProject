package com.mycmp01.exam01;

import java.net.InetAddress;
/*
 * 서버 역할을 하는 클래스 입니다.
 * 
 * 스레드(Thread) 클래스를 사용해서 하나 이상의 클라이언트 연결을 기다리고
 * 연결된 클라이언트 요청을 처리하고 다시 대기 상태로 이동합니다.
 * 동기화(Synchronization)를 사용해서 스레드(클라이언트)가 
 * 특정 함수를 실행하고 있는 동안 다른 스레드가 특정 함수 안으로 
 * 올 수 없도록 합니다. 
 * 
 * 
 * 1. java.net 패키지에 있는 ServerSocket 클래스를 사용
 * -> 서버 클래스를 표현
 * 
 * 2. 클라이언트와 연결되었을 때는 Socket 클래스를 사용
 * 
 */
// ServerSocket 클래스와 Socket 클래스를 사용하기 위한 import 명령문
import java.net.ServerSocket;
import java.net.Socket;

// 3. 클라이언트와 서버 간에 데이터를 주고 받기 위해서는 InputStream,
// OutputStream 클래스를 사용
// InputStream  : 클라이언트로부터 데이터를 받아올 때 사용
// OutputStream : 서버에서 클라이언트로 데이터를 보낼 때 사용
// -> java.io 패키지에 있습니다.
import java.io.InputStream;
import java.io.OutputStream;
// -> try~catch( ) 또는 throws 절을 사용해서 예외상황에 대비하기
// IOException 클래스 : 입력 또는 출력 작업을 할 때 발생할 수 있는 예외상황
import java.io.IOException;

class MyClientServiceThread extends Thread {

	// 서버와 연결에 사용한 소켓 정보를 보관할 변수
	private Socket mRefSocket;

	// 데이터 입력 용도로 사용할 InputStream 클래스를 사용한 변수 선언
	private InputStream mRefInputStream;

	// 데이터 출력 용도로 사용할 OutputStream 클래스를 사용한 변수 선언
	private OutputStream mRefOutputStream;

	// 성능 개선을 목적으로 사용하는 변수 선언
	// 데이터 입력 용도로 사용할 변수
	private java.io.BufferedReader mRefBufferedReader;

	// 데이터 출력 용도로 사용할 변수
	private java.io.BufferedWriter mRefBufferedWriter;

	// 생성자 만들기
	// 1) 기본 생성자 함수 만들기
	public MyClientServiceThread() {

	}

	// 2) 외부 클래스 또는 함수로부터 소켓 객체의 주소를 받는 생성자 함수
	public MyClientServiceThread(Socket refClientSocket) {
		// 소켓 객체의 주소를 다른 함수에서도 사용하기 위해서 전역 변수에 저장
		this.mRefSocket = refClientSocket;

		// 데이터 입력 명령문 : 클라이언트로부터 전달 받은 데이터를 받는 명령문
		// 데이터 출력 명령문 : 서버에서 클라이언트로 새로운 데이터를 전달하는
		// 명령문 작성

		// 데이터 입력과 출력시에 발생할 수 있는 예외상황 대비하기
		try {

			// 데이터 입력시에 사용할 스트림 얻기
			mRefInputStream = this.mRefSocket.getInputStream();

			// 데이터 출력시에 사용할 스트림 얻기
			mRefOutputStream = this.mRefSocket.getOutputStream();

			// 성능 개선을 위해서 BufferedWriter 객체 만들기
			mRefBufferedWriter = new java.io.BufferedWriter(new java.io.OutputStreamWriter(mRefOutputStream));

			// OutputStreamWriter 클래스는 바이트 스트림 객체를 문자 출력
			// 스트림 객체로 변환해 줍니다. (출력용 스트림)

			mRefBufferedReader = new java.io.BufferedReader(new java.io.InputStreamReader(mRefInputStream));

			// InputStreamReader 클래스도 바이트 스트림 객체를 문자(2바이트)
			// 입력 스트림 객체로 변환해 줍니다. (입력용 스트림)

			while (true) {

				if (mRefBufferedReader == null) {
					System.out.println("mRefBufferedReader == null");
					break;
				}
				System.out.println("while(true)");

				// 클라이언트에서 보낸 데이터를 문자열 변수에 저장하기
				String readData = "";

				// mRefBufferedReader 변수가 갖고 있는
				// readLine( ) 함수를 사용하기
				readData = mRefBufferedReader.readLine();

				System.out.println("mRefBufferedReader.readLine()");

				if (readData == null || readData.length() == 0) {
					// System.out.println
					// ("클라이언트에서 보낸 데이터는 없습니다.");
					System.out.println("클라이언트에서 보낸 데이터가 없습니다.");
					break;

				} else {
					System.out.println("클라이언트에서 보낸 데이터는 " + readData);

					// mRefBufferedWriter.write(readData);
					// mRefBufferedWriter.flush();

					// 새로 추가해 주세요!! 클라이언트에서 q를 입력한 경우
					if (readData.compareToIgnoreCase("q") == 0) {
						System.out.println("클라이언트에서 q 또는 Q 입력");
						System.out.println("반복문 탈출");
						break;
					}
				}

				// 클라이언트로 새로운 데이터를 만들어서 전송합니다.
				// mRefBufferedWriter.write("안녕하세요?[서버에서 보낸 메시지]");
				// mRefBufferedWriter.flush();

				// sleep( ) 함수를 실행해서 0.5초 동안 현재 스레드 객체가 하고
				// 있는 일을 멈추게 합니다.
				try {
					// sleep( ) 함수는 static : 클래스이름으로 실행
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// 스레드 객체가 0.5초가 되기 전에 깨어난 경우에 실행할 명령문
					System.out.println("쓰레드 객체가 잠에서 깨어났습니다.");
				}

			} // end of while()

		} catch (java.io.IOException e) {

			// 소켓 통신에서 발생할 수 있는 예외상황 메시지 출력 명령문 작성
			e.printStackTrace();

		} catch (Exception e) {

			// 다른 예외상황이 발생하는 경우에 실행되는 명령문 작성
			e.printStackTrace();
		}

	}

	// 동기화 함수를 만듭니다.
	public synchronized void syncMethod01() {
		// 여기에 작성되는 명령문들은 하나의 스레드만이 실행

		// 하나의 스레드가 데이터 입력과 출력 작업을 모두 하는 경우
	}

	// run( ) 함수를 재정의합니다.
	@Override
	public void run() {
		// syncMethod01();
		// 이 곳에 명령문을 작성하면 순서대로 처리되지 않을 수도 있습니다.
		// 여러 개의 스레드들이 동시에 실행
	}

}

/*
 * 서버와 연결되는 클라이언트 소켓 정보를 관리해주는 새로운 클래스 만들기
 */
class MyClientService2Thread extends Thread {

	// 서버와 연결에 사용한 소켓 정보를 보관할 변수
	private Socket mRefSocket;

	// 데이터 입력 용도로 사용할 InputStream 클래스를 사용한 변수 선언
	private InputStream mRefInputStream;

	// 데이터 출력 용도로 사용할 OutputStream 클래스를 사용한 변수 선언
	private OutputStream mRefOutputStream;

	// 성능 개선을 목적으로 사용하는 변수 선언
	// 데이터 입력 용도로 사용할 변수
	private java.io.BufferedReader mRefBufferedReader;

	// 데이터 출력 용도로 사용할 변수
	private java.io.BufferedWriter mRefBufferedWriter;

	// 생성자 만들기
	// 1) 기본 생성자 함수 만들기
	public MyClientService2Thread() {

	}

	// 2) 외부 클래스 또는 함수로부터 소켓 객체의 주소를 받는 생성자 함수
	public MyClientService2Thread(Socket refClientSocket) {
		// 소켓 객체의 주소를 다른 함수에서도 사용하기 위해서 전역 변수에 저장
		this.mRefSocket = refClientSocket;

		// 데이터 입력 명령문 : 클라이언트로부터 전달 받은 데이터를 받는 명령문
		// 데이터 출력 명령문 : 서버에서 클라이언트로 새로운 데이터를 전달하는
		// 명령문 작성

		// 데이터 입력과 출력시에 발생할 수 있는 예외상황 대비하기
		try {

			// 데이터 입력시에 사용할 스트림 얻기
			mRefInputStream = this.mRefSocket.getInputStream();

			// 데이터 출력시에 사용할 스트림 얻기
			mRefOutputStream = this.mRefSocket.getOutputStream();

			// 성능 개선을 위해서 BufferedWriter 객체 만들기
			mRefBufferedWriter = new java.io.BufferedWriter(new java.io.OutputStreamWriter(mRefOutputStream));

			// OutputStreamWriter 클래스는 바이트 스트림 객체를 문자 출력
			// 스트림 객체로 변환해 줍니다. (출력용 스트림)

			mRefBufferedReader = new java.io.BufferedReader(new java.io.InputStreamReader(mRefInputStream));

			// InputStreamReader 클래스도 바이트 스트림 객체를 문자(2바이트)
			// 입력 스트림 객체로 변환해 줍니다. (입력용 스트림)

			// 클라이언트에서 보낸 데이터를 문자열 변수에 저장하기
			String readData = "";

			// mRefBufferedReader 변수가 갖고 있는 readLine( ) 함수를 사용하기
			readData = mRefBufferedReader.readLine();

			if (readData == null || readData.length() == 0) {
				System.out.println("클라이언트에서 보낸 데이터는 없습니다.");
			} else {
				System.out.println("클라이언트에서 보낸 데이터는 " + readData);
			}

			// 클라이언트로 새로운 데이터를 만들어서 전송합니다.
			mRefBufferedWriter.write("안녕하세요?[서버에서 보낸 메시지]");
			mRefBufferedWriter.flush();

			// mRefBufferedWriter.close();
			// mRefBufferedReader.close();

			// sleep( ) 함수를 실행해서 0.5초 동안 현재 스레드 객체가 하고
			// 있는 일을 멈추게 합니다.
			try {
				// sleep( ) 함수는 static : 클래스이름으로 실행
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// 스레드 객체가 0.5초가 되기 전에 깨어난 경우에 실행할 명령문
				System.out.println("쓰레드 객체가 잠에서 깨어났습니다.");
			}

		} catch (java.io.IOException e) {

			// 소켓 통신에서 발생할 수 있는 예외상황 메시지 출력 명령문 작성
			e.printStackTrace();

		} catch (Exception e) {

			// 다른 예외상황이 발생하는 경우에 실행되는 명령문 작성
			e.printStackTrace();
		}

	}

	// 동기화 함수를 만듭니다.
	public synchronized void syncMethod01() {
		// 여기에 작성되는 명령문들은 하나의 스레드만이 실행

		// 하나의 스레드가 데이터 입력과 출력 작업을 모두 하는 경우
	}

	// run( ) 함수를 재정의합니다.
	@Override
	public void run() {
		// syncMethod01();
		// 이 곳에 명령문을 작성하면 순서대로 처리되지 않을 수도 있습니다.
		// 여러 개의 스레드들이 동시에 실행
	}

}

public class MyChattingServerClass {

	// 변수 선언하기
	// 메모리에 서버 소켓 클래스를 사용하는 객체가 생성되어 있는 경우에만
	// 참(true)을 보관하고,
	// 그렇지 않은 경우에는 거짓(false)을 보관할 변수 -> while( ) 반복문의
	// 조건에 사용할 변수
	private static boolean isChattingServerOn = false;
	// false : 아직 메모리에 서버 소켓 클래스가 생성되기 전이므로

	// ServerSocket 클래스를 사용해서 변수를 선언
	private static ServerSocket serverSocket; // 하나의 변수만 사용

	// 포트 번호 : 0 ~ 65535 보관하는 상수 만들기
	public static final int SERVER_PORT_NO = 50000;

	// 서버 아이피 주소 : 127.0.0.1
	// 컴퓨터 이름(호스트 이름) : localhost
	public static final String SERVER_IP_ADDR = "127.0.0.1";
	public static final String SERVER_HOST_NAME = "localhost";

	// 애뮬레이터와 통신할 때 사용하는 아이피 주소 : 10.0.2.2
	public static final String EMULATOR_SERVER_IP_ADDR = "10.0.2.2";

	// 클라이언트와 서버가 연결되는 시점에 실행되는 함수 만들기
	public synchronized static void createThreadMethod(Socket refSocket) {
		// 위에서 만든 스레드 클래스를 사용해서 새로운 스레드 객체를 만듭니다.
		// 서버와 연결점을 갖고 있는 소켓 객체의 주소를 생성자 함수로 전달
		new MyClientServiceThread(refSocket).start();
		// new MyClientService2Thread(refSocket).start();

		// MyClientServiceThread 스레드_객체_참조_변수_이름;
		// 스레드_객체_참조_변수_이름 = new MyClientServiceThread(refSocket);
		// 스레드_객체_참조_변수_이름.start( ); // run( ) 함수를 실행할 수
		// 있도록 합니다.
		// 스레드_객체_참조_변수_이름 : 지역 변수 -> 함수 안에서만 사용
	}

	public static void main4(String[] args) {

		// 예외상황 대비 구조
		try {

			// 1. 서버 소켓 객체를 메모리에 생성하기
			serverSocket = new ServerSocket(SERVER_PORT_NO);

			// 2. 위 명령문이 정상적으로 실행된 경우에는 바로 아래로 이동
			System.out.println("***메모리에 서버 소켓 객체 생성***");

			// 3. 변수 isChattingServerOn를 사용해서 상태를 바꾸기 :
			// false에서 true로 변경 :
			isChattingServerOn = true;
			// isChattingServerOn = !isChattingServerOn;

			// 4. 하나 이상의 클라이언트 접속을 기다리는 반복문
			while (isChattingServerOn == true) {

				// 5. 클라이언트 접속을 기다리는 메시지 출력
				System.out.println("***클라이언트 연결을 기다립니다1***");

				Socket socket = serverSocket.accept();

				System.out.println("***클라이언트와 연결되었습니다***");

				InetAddress refInetAddress = socket.getInetAddress();

				System.out.println("***클라이언트 정보***");

				System.out.println("컴퓨터 이름은 " + refInetAddress.getHostName());
				System.out.println("컴퓨터 아이피 주소는 " + refInetAddress.getHostAddress());

				createThreadMethod(socket);

			}

		} catch (IOException e) {

		} catch (Exception e) {

		} finally {
			// 제일 마지막에 실행(무조건 실행)
		}

	}

	// 새로운 main( ) 함수 만들기 -> while( ) 반복문을 사용하는 구조로 변경
	public static void main(String[] args) {

		// 예외상황 대비 구조
		try {

			// 1. 서버 소켓 객체를 메모리에 생성하기
			serverSocket = new ServerSocket(SERVER_PORT_NO);

			// 2. 위 명령문이 정상적으로 실행된 경우에는 바로 아래로 이동
			System.out.println("***메모리에 서버 소켓 객체 생성***");

			// 3. 변수 isChattingServerOn를 사용해서 상태를 바꾸기 :
			// false에서 true로 변경 :
			isChattingServerOn = true;
			// isChattingServerOn = !isChattingServerOn;

			// 4. 하나 이상의 클라이언트 접속을 기다리는 반복문
			while (isChattingServerOn == true) {

				// 5. 클라이언트 접속을 기다리는 메시지 출력
				System.out.println("***클라이언트 연결을 기다립니다2***");

				// 6. accept( ) 함수를 실행해서 클라이언트 연결을 기다립니다.
				// -> accept( ) 함수는 연결된 클라이언트 소켓의 주소를 반환
				Socket refClientSocket = serverSocket.accept();

				// 7. 서버와 클라이언트가 연결된 경우 : 클라이언트 정보를 출력
				InetAddress inetAddress = refClientSocket.getInetAddress();

				// 8. 컴퓨터 이름은 getHostName( )
				String hostName = inetAddress.getHostName();
				System.out.println("서버와 연결된 클라이언트 컴퓨터 이름은 " + hostName);

				// 9. 서버와 연결된 클라이언트 아이피 주소 읽어오기
				String ipAddr = inetAddress.getHostAddress();
				System.out.println("클라이언트 아이피 주소는 " + ipAddr);

				// 10. 스레드 객체를 생성하기 위함 : 백그라운드에서 실행
				createThreadMethod(refClientSocket);

			}

		} catch (IOException e) {

		} catch (Exception e) {

		} finally {
			// 제일 마지막에 실행(무조건 실행)
		}

	}

	public static void main2(String[] args) {
		// TODO Auto-generated method stub

		try {

			System.out.println("***main( ) 함수가 실행되었습니다***");

			System.out.println("클라이언트를 기다리는 서버 소켓 만들기");

			serverSocket = new ServerSocket(SERVER_PORT_NO);

			System.out.println("서버 소켓 만들기 성공");

			// accept( ) : 무한 루프를 갖고 있는 함수
			// -> 클라이언트의 연결을 계속 기다립니다.
			// -> 특정 클라이언트와 연결이 되면 함수를 종료
			// -> 연결이 되면 클라이언트의 소켓 주소(Socket 클래스)를 반환
			Socket refClientSocket = serverSocket.accept();

			if (refClientSocket != null) {
				System.out.println("클라이언트와 연결 성공");

				// 위에서 만든 createThreadMethod( ) 함수를 실행해서
				// 메모리에 새로운 스레드 객체를 생성합니다.
				// refClientSocket 변수를 전달
				createThreadMethod(refClientSocket);
			}

			// sleep( ) 함수를 사용해서 0.5초간 중지
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {

			// 50000 포트 번호를 다른 프로그램에서 사용 중인 경우
			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
}

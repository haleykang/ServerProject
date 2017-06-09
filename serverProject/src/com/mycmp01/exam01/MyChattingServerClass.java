package com.mycmp01.exam01;

import java.net.InetAddress;
/*
 * ���� ������ �ϴ� Ŭ���� �Դϴ�.
 * 
 * ������(Thread) Ŭ������ ����ؼ� �ϳ� �̻��� Ŭ���̾�Ʈ ������ ��ٸ���
 * ����� Ŭ���̾�Ʈ ��û�� ó���ϰ� �ٽ� ��� ���·� �̵��մϴ�.
 * ����ȭ(Synchronization)�� ����ؼ� ������(Ŭ���̾�Ʈ)�� 
 * Ư�� �Լ��� �����ϰ� �ִ� ���� �ٸ� �����尡 Ư�� �Լ� ������ 
 * �� �� ������ �մϴ�. 
 * 
 * 
 * 1. java.net ��Ű���� �ִ� ServerSocket Ŭ������ ���
 * -> ���� Ŭ������ ǥ��
 * 
 * 2. Ŭ���̾�Ʈ�� ����Ǿ��� ���� Socket Ŭ������ ���
 * 
 */
// ServerSocket Ŭ������ Socket Ŭ������ ����ϱ� ���� import ��ɹ�
import java.net.ServerSocket;
import java.net.Socket;

// 3. Ŭ���̾�Ʈ�� ���� ���� �����͸� �ְ� �ޱ� ���ؼ��� InputStream,
// OutputStream Ŭ������ ���
// InputStream  : Ŭ���̾�Ʈ�κ��� �����͸� �޾ƿ� �� ���
// OutputStream : �������� Ŭ���̾�Ʈ�� �����͸� ���� �� ���
// -> java.io ��Ű���� �ֽ��ϴ�.
import java.io.InputStream;
import java.io.OutputStream;
// -> try~catch( ) �Ǵ� throws ���� ����ؼ� ���ܻ�Ȳ�� ����ϱ�
// IOException Ŭ���� : �Է� �Ǵ� ��� �۾��� �� �� �߻��� �� �ִ� ���ܻ�Ȳ
import java.io.IOException;

class MyClientServiceThread extends Thread {

	// ������ ���ῡ ����� ���� ������ ������ ����
	private Socket mRefSocket;

	// ������ �Է� �뵵�� ����� InputStream Ŭ������ ����� ���� ����
	private InputStream mRefInputStream;

	// ������ ��� �뵵�� ����� OutputStream Ŭ������ ����� ���� ����
	private OutputStream mRefOutputStream;

	// ���� ������ �������� ����ϴ� ���� ����
	// ������ �Է� �뵵�� ����� ����
	private java.io.BufferedReader mRefBufferedReader;

	// ������ ��� �뵵�� ����� ����
	private java.io.BufferedWriter mRefBufferedWriter;

	// ������ �����
	// 1) �⺻ ������ �Լ� �����
	public MyClientServiceThread() {

	}

	// 2) �ܺ� Ŭ���� �Ǵ� �Լ��κ��� ���� ��ü�� �ּҸ� �޴� ������ �Լ�
	public MyClientServiceThread(Socket refClientSocket) {
		// ���� ��ü�� �ּҸ� �ٸ� �Լ������� ����ϱ� ���ؼ� ���� ������ ����
		this.mRefSocket = refClientSocket;

		// ������ �Է� ��ɹ� : Ŭ���̾�Ʈ�κ��� ���� ���� �����͸� �޴� ��ɹ�
		// ������ ��� ��ɹ� : �������� Ŭ���̾�Ʈ�� ���ο� �����͸� �����ϴ�
		// ��ɹ� �ۼ�

		// ������ �Է°� ��½ÿ� �߻��� �� �ִ� ���ܻ�Ȳ ����ϱ�
		try {

			// ������ �Է½ÿ� ����� ��Ʈ�� ���
			mRefInputStream = this.mRefSocket.getInputStream();

			// ������ ��½ÿ� ����� ��Ʈ�� ���
			mRefOutputStream = this.mRefSocket.getOutputStream();

			// ���� ������ ���ؼ� BufferedWriter ��ü �����
			mRefBufferedWriter = new java.io.BufferedWriter(new java.io.OutputStreamWriter(mRefOutputStream));

			// OutputStreamWriter Ŭ������ ����Ʈ ��Ʈ�� ��ü�� ���� ���
			// ��Ʈ�� ��ü�� ��ȯ�� �ݴϴ�. (��¿� ��Ʈ��)

			mRefBufferedReader = new java.io.BufferedReader(new java.io.InputStreamReader(mRefInputStream));

			// InputStreamReader Ŭ������ ����Ʈ ��Ʈ�� ��ü�� ����(2����Ʈ)
			// �Է� ��Ʈ�� ��ü�� ��ȯ�� �ݴϴ�. (�Է¿� ��Ʈ��)

			while (true) {

				if (mRefBufferedReader == null) {
					System.out.println("mRefBufferedReader == null");
					break;
				}
				System.out.println("while(true)");

				// Ŭ���̾�Ʈ���� ���� �����͸� ���ڿ� ������ �����ϱ�
				String readData = "";

				// mRefBufferedReader ������ ���� �ִ�
				// readLine( ) �Լ��� ����ϱ�
				readData = mRefBufferedReader.readLine();

				System.out.println("mRefBufferedReader.readLine()");

				if (readData == null || readData.length() == 0) {
					// System.out.println
					// ("Ŭ���̾�Ʈ���� ���� �����ʹ� �����ϴ�.");
					System.out.println("Ŭ���̾�Ʈ���� ���� �����Ͱ� �����ϴ�.");
					break;

				} else {
					System.out.println("Ŭ���̾�Ʈ���� ���� �����ʹ� " + readData);

					// mRefBufferedWriter.write(readData);
					// mRefBufferedWriter.flush();

					// ���� �߰��� �ּ���!! Ŭ���̾�Ʈ���� q�� �Է��� ���
					if (readData.compareToIgnoreCase("q") == 0) {
						System.out.println("Ŭ���̾�Ʈ���� q �Ǵ� Q �Է�");
						System.out.println("�ݺ��� Ż��");
						break;
					}
				}

				// Ŭ���̾�Ʈ�� ���ο� �����͸� ���� �����մϴ�.
				// mRefBufferedWriter.write("�ȳ��ϼ���?[�������� ���� �޽���]");
				// mRefBufferedWriter.flush();

				// sleep( ) �Լ��� �����ؼ� 0.5�� ���� ���� ������ ��ü�� �ϰ�
				// �ִ� ���� ���߰� �մϴ�.
				try {
					// sleep( ) �Լ��� static : Ŭ�����̸����� ����
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// ������ ��ü�� 0.5�ʰ� �Ǳ� ���� ��� ��쿡 ������ ��ɹ�
					System.out.println("������ ��ü�� �ῡ�� ������ϴ�.");
				}

			} // end of while()

		} catch (java.io.IOException e) {

			// ���� ��ſ��� �߻��� �� �ִ� ���ܻ�Ȳ �޽��� ��� ��ɹ� �ۼ�
			e.printStackTrace();

		} catch (Exception e) {

			// �ٸ� ���ܻ�Ȳ�� �߻��ϴ� ��쿡 ����Ǵ� ��ɹ� �ۼ�
			e.printStackTrace();
		}

	}

	// ����ȭ �Լ��� ����ϴ�.
	public synchronized void syncMethod01() {
		// ���⿡ �ۼ��Ǵ� ��ɹ����� �ϳ��� �����常�� ����

		// �ϳ��� �����尡 ������ �Է°� ��� �۾��� ��� �ϴ� ���
	}

	// run( ) �Լ��� �������մϴ�.
	@Override
	public void run() {
		// syncMethod01();
		// �� ���� ��ɹ��� �ۼ��ϸ� ������� ó������ ���� ���� �ֽ��ϴ�.
		// ���� ���� ��������� ���ÿ� ����
	}

}

/*
 * ������ ����Ǵ� Ŭ���̾�Ʈ ���� ������ �������ִ� ���ο� Ŭ���� �����
 */
class MyClientService2Thread extends Thread {

	// ������ ���ῡ ����� ���� ������ ������ ����
	private Socket mRefSocket;

	// ������ �Է� �뵵�� ����� InputStream Ŭ������ ����� ���� ����
	private InputStream mRefInputStream;

	// ������ ��� �뵵�� ����� OutputStream Ŭ������ ����� ���� ����
	private OutputStream mRefOutputStream;

	// ���� ������ �������� ����ϴ� ���� ����
	// ������ �Է� �뵵�� ����� ����
	private java.io.BufferedReader mRefBufferedReader;

	// ������ ��� �뵵�� ����� ����
	private java.io.BufferedWriter mRefBufferedWriter;

	// ������ �����
	// 1) �⺻ ������ �Լ� �����
	public MyClientService2Thread() {

	}

	// 2) �ܺ� Ŭ���� �Ǵ� �Լ��κ��� ���� ��ü�� �ּҸ� �޴� ������ �Լ�
	public MyClientService2Thread(Socket refClientSocket) {
		// ���� ��ü�� �ּҸ� �ٸ� �Լ������� ����ϱ� ���ؼ� ���� ������ ����
		this.mRefSocket = refClientSocket;

		// ������ �Է� ��ɹ� : Ŭ���̾�Ʈ�κ��� ���� ���� �����͸� �޴� ��ɹ�
		// ������ ��� ��ɹ� : �������� Ŭ���̾�Ʈ�� ���ο� �����͸� �����ϴ�
		// ��ɹ� �ۼ�

		// ������ �Է°� ��½ÿ� �߻��� �� �ִ� ���ܻ�Ȳ ����ϱ�
		try {

			// ������ �Է½ÿ� ����� ��Ʈ�� ���
			mRefInputStream = this.mRefSocket.getInputStream();

			// ������ ��½ÿ� ����� ��Ʈ�� ���
			mRefOutputStream = this.mRefSocket.getOutputStream();

			// ���� ������ ���ؼ� BufferedWriter ��ü �����
			mRefBufferedWriter = new java.io.BufferedWriter(new java.io.OutputStreamWriter(mRefOutputStream));

			// OutputStreamWriter Ŭ������ ����Ʈ ��Ʈ�� ��ü�� ���� ���
			// ��Ʈ�� ��ü�� ��ȯ�� �ݴϴ�. (��¿� ��Ʈ��)

			mRefBufferedReader = new java.io.BufferedReader(new java.io.InputStreamReader(mRefInputStream));

			// InputStreamReader Ŭ������ ����Ʈ ��Ʈ�� ��ü�� ����(2����Ʈ)
			// �Է� ��Ʈ�� ��ü�� ��ȯ�� �ݴϴ�. (�Է¿� ��Ʈ��)

			// Ŭ���̾�Ʈ���� ���� �����͸� ���ڿ� ������ �����ϱ�
			String readData = "";

			// mRefBufferedReader ������ ���� �ִ� readLine( ) �Լ��� ����ϱ�
			readData = mRefBufferedReader.readLine();

			if (readData == null || readData.length() == 0) {
				System.out.println("Ŭ���̾�Ʈ���� ���� �����ʹ� �����ϴ�.");
			} else {
				System.out.println("Ŭ���̾�Ʈ���� ���� �����ʹ� " + readData);
			}

			// Ŭ���̾�Ʈ�� ���ο� �����͸� ���� �����մϴ�.
			mRefBufferedWriter.write("�ȳ��ϼ���?[�������� ���� �޽���]");
			mRefBufferedWriter.flush();

			// mRefBufferedWriter.close();
			// mRefBufferedReader.close();

			// sleep( ) �Լ��� �����ؼ� 0.5�� ���� ���� ������ ��ü�� �ϰ�
			// �ִ� ���� ���߰� �մϴ�.
			try {
				// sleep( ) �Լ��� static : Ŭ�����̸����� ����
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// ������ ��ü�� 0.5�ʰ� �Ǳ� ���� ��� ��쿡 ������ ��ɹ�
				System.out.println("������ ��ü�� �ῡ�� ������ϴ�.");
			}

		} catch (java.io.IOException e) {

			// ���� ��ſ��� �߻��� �� �ִ� ���ܻ�Ȳ �޽��� ��� ��ɹ� �ۼ�
			e.printStackTrace();

		} catch (Exception e) {

			// �ٸ� ���ܻ�Ȳ�� �߻��ϴ� ��쿡 ����Ǵ� ��ɹ� �ۼ�
			e.printStackTrace();
		}

	}

	// ����ȭ �Լ��� ����ϴ�.
	public synchronized void syncMethod01() {
		// ���⿡ �ۼ��Ǵ� ��ɹ����� �ϳ��� �����常�� ����

		// �ϳ��� �����尡 ������ �Է°� ��� �۾��� ��� �ϴ� ���
	}

	// run( ) �Լ��� �������մϴ�.
	@Override
	public void run() {
		// syncMethod01();
		// �� ���� ��ɹ��� �ۼ��ϸ� ������� ó������ ���� ���� �ֽ��ϴ�.
		// ���� ���� ��������� ���ÿ� ����
	}

}

public class MyChattingServerClass {

	// ���� �����ϱ�
	// �޸𸮿� ���� ���� Ŭ������ ����ϴ� ��ü�� �����Ǿ� �ִ� ��쿡��
	// ��(true)�� �����ϰ�,
	// �׷��� ���� ��쿡�� ����(false)�� ������ ���� -> while( ) �ݺ�����
	// ���ǿ� ����� ����
	private static boolean isChattingServerOn = false;
	// false : ���� �޸𸮿� ���� ���� Ŭ������ �����Ǳ� ���̹Ƿ�

	// ServerSocket Ŭ������ ����ؼ� ������ ����
	private static ServerSocket serverSocket; // �ϳ��� ������ ���

	// ��Ʈ ��ȣ : 0 ~ 65535 �����ϴ� ��� �����
	public static final int SERVER_PORT_NO = 50000;

	// ���� ������ �ּ� : 127.0.0.1
	// ��ǻ�� �̸�(ȣ��Ʈ �̸�) : localhost
	public static final String SERVER_IP_ADDR = "127.0.0.1";
	public static final String SERVER_HOST_NAME = "localhost";

	// �ֹķ����Ϳ� ����� �� ����ϴ� ������ �ּ� : 10.0.2.2
	public static final String EMULATOR_SERVER_IP_ADDR = "10.0.2.2";

	// Ŭ���̾�Ʈ�� ������ ����Ǵ� ������ ����Ǵ� �Լ� �����
	public synchronized static void createThreadMethod(Socket refSocket) {
		// ������ ���� ������ Ŭ������ ����ؼ� ���ο� ������ ��ü�� ����ϴ�.
		// ������ �������� ���� �ִ� ���� ��ü�� �ּҸ� ������ �Լ��� ����
		new MyClientServiceThread(refSocket).start();
		// new MyClientService2Thread(refSocket).start();

		// MyClientServiceThread ������_��ü_����_����_�̸�;
		// ������_��ü_����_����_�̸� = new MyClientServiceThread(refSocket);
		// ������_��ü_����_����_�̸�.start( ); // run( ) �Լ��� ������ ��
		// �ֵ��� �մϴ�.
		// ������_��ü_����_����_�̸� : ���� ���� -> �Լ� �ȿ����� ���
	}

	public static void main4(String[] args) {

		// ���ܻ�Ȳ ��� ����
		try {

			// 1. ���� ���� ��ü�� �޸𸮿� �����ϱ�
			serverSocket = new ServerSocket(SERVER_PORT_NO);

			// 2. �� ��ɹ��� ���������� ����� ��쿡�� �ٷ� �Ʒ��� �̵�
			System.out.println("***�޸𸮿� ���� ���� ��ü ����***");

			// 3. ���� isChattingServerOn�� ����ؼ� ���¸� �ٲٱ� :
			// false���� true�� ���� :
			isChattingServerOn = true;
			// isChattingServerOn = !isChattingServerOn;

			// 4. �ϳ� �̻��� Ŭ���̾�Ʈ ������ ��ٸ��� �ݺ���
			while (isChattingServerOn == true) {

				// 5. Ŭ���̾�Ʈ ������ ��ٸ��� �޽��� ���
				System.out.println("***Ŭ���̾�Ʈ ������ ��ٸ��ϴ�1***");

				Socket socket = serverSocket.accept();

				System.out.println("***Ŭ���̾�Ʈ�� ����Ǿ����ϴ�***");

				InetAddress refInetAddress = socket.getInetAddress();

				System.out.println("***Ŭ���̾�Ʈ ����***");

				System.out.println("��ǻ�� �̸��� " + refInetAddress.getHostName());
				System.out.println("��ǻ�� ������ �ּҴ� " + refInetAddress.getHostAddress());

				createThreadMethod(socket);

			}

		} catch (IOException e) {

		} catch (Exception e) {

		} finally {
			// ���� �������� ����(������ ����)
		}

	}

	// ���ο� main( ) �Լ� ����� -> while( ) �ݺ����� ����ϴ� ������ ����
	public static void main(String[] args) {

		// ���ܻ�Ȳ ��� ����
		try {

			// 1. ���� ���� ��ü�� �޸𸮿� �����ϱ�
			serverSocket = new ServerSocket(SERVER_PORT_NO);

			// 2. �� ��ɹ��� ���������� ����� ��쿡�� �ٷ� �Ʒ��� �̵�
			System.out.println("***�޸𸮿� ���� ���� ��ü ����***");

			// 3. ���� isChattingServerOn�� ����ؼ� ���¸� �ٲٱ� :
			// false���� true�� ���� :
			isChattingServerOn = true;
			// isChattingServerOn = !isChattingServerOn;

			// 4. �ϳ� �̻��� Ŭ���̾�Ʈ ������ ��ٸ��� �ݺ���
			while (isChattingServerOn == true) {

				// 5. Ŭ���̾�Ʈ ������ ��ٸ��� �޽��� ���
				System.out.println("***Ŭ���̾�Ʈ ������ ��ٸ��ϴ�2***");

				// 6. accept( ) �Լ��� �����ؼ� Ŭ���̾�Ʈ ������ ��ٸ��ϴ�.
				// -> accept( ) �Լ��� ����� Ŭ���̾�Ʈ ������ �ּҸ� ��ȯ
				Socket refClientSocket = serverSocket.accept();

				// 7. ������ Ŭ���̾�Ʈ�� ����� ��� : Ŭ���̾�Ʈ ������ ���
				InetAddress inetAddress = refClientSocket.getInetAddress();

				// 8. ��ǻ�� �̸��� getHostName( )
				String hostName = inetAddress.getHostName();
				System.out.println("������ ����� Ŭ���̾�Ʈ ��ǻ�� �̸��� " + hostName);

				// 9. ������ ����� Ŭ���̾�Ʈ ������ �ּ� �о����
				String ipAddr = inetAddress.getHostAddress();
				System.out.println("Ŭ���̾�Ʈ ������ �ּҴ� " + ipAddr);

				// 10. ������ ��ü�� �����ϱ� ���� : ��׶��忡�� ����
				createThreadMethod(refClientSocket);

			}

		} catch (IOException e) {

		} catch (Exception e) {

		} finally {
			// ���� �������� ����(������ ����)
		}

	}

	public static void main2(String[] args) {
		// TODO Auto-generated method stub

		try {

			System.out.println("***main( ) �Լ��� ����Ǿ����ϴ�***");

			System.out.println("Ŭ���̾�Ʈ�� ��ٸ��� ���� ���� �����");

			serverSocket = new ServerSocket(SERVER_PORT_NO);

			System.out.println("���� ���� ����� ����");

			// accept( ) : ���� ������ ���� �ִ� �Լ�
			// -> Ŭ���̾�Ʈ�� ������ ��� ��ٸ��ϴ�.
			// -> Ư�� Ŭ���̾�Ʈ�� ������ �Ǹ� �Լ��� ����
			// -> ������ �Ǹ� Ŭ���̾�Ʈ�� ���� �ּ�(Socket Ŭ����)�� ��ȯ
			Socket refClientSocket = serverSocket.accept();

			if (refClientSocket != null) {
				System.out.println("Ŭ���̾�Ʈ�� ���� ����");

				// ������ ���� createThreadMethod( ) �Լ��� �����ؼ�
				// �޸𸮿� ���ο� ������ ��ü�� �����մϴ�.
				// refClientSocket ������ ����
				createThreadMethod(refClientSocket);
			}

			// sleep( ) �Լ��� ����ؼ� 0.5�ʰ� ����
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {

			// 50000 ��Ʈ ��ȣ�� �ٸ� ���α׷����� ��� ���� ���
			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
}

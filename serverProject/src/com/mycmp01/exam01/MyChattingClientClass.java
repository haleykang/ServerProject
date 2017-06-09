package com.mycmp01.exam01;

// Ŭ�������� ����� Ŭ������ �������̽����� �ִ� ��Ű������ �ۼ��ϱ�
import java.net.*; // ServerSocket, Socket ���
import java.io.*; // InputStream, OutputStream, BufferedWriter, BufferedReader
// IOException, PrintWriter ���

// ��ĳ�� Ŭ������ ����ؼ� ������ ���� �޽����� ����ڷκ��� �Է� �ޱ�
import java.util.Scanner;

// ä�� Ŭ���̾�Ʈ Ŭ����
public class MyChattingClientClass {

	// ��ĳ�� ���� �����ϱ�
	private static Scanner scanner;

	// ä�� ������ ���(������ ���۰� ����)�� ����� ���� �����ϱ�
	private static Socket socket;

	// isConnected( ) �Լ��� ��� ���� ������ ���� ����
	private static boolean resultIsConnected = false;
	// -> ���� ������ ����Ǿ� �ִ��� ������ ����ϰ� �ִ� ����

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// ���ܻ�Ȳ�� ����ϱ� ���� try~catch( ) �� �ۼ�
		try {

			// ��ĳ�� ������ �޸𸮿� �����մϴ�.
			scanner = new Scanner(System.in);

			// ����ڰ� �Է��� �޽����� ������ �ӽ� ���� ����
			String tempInputMessage = "";

			// ���� ������ ���� ���� ��ü�� ���� : ���� ������ �ּҿ� ��Ʈ ��ȣ
			socket = new Socket(MyChattingServerClass.SERVER_IP_ADDR, MyChattingServerClass.SERVER_PORT_NO);

			// ������ ������ �� ��쿡 ����Ǵ� ��ɹ� �ۼ�
			if (socket != null) {

				System.out.println("������ ����Ǿ����ϴ�.");

				// ����ڰ� �Է��� �޽����� ������ ����
				OutputStream outputStream;
				BufferedWriter bw = null;

				// ���� �ݺ��� : �������� ������ �ʴ� �ݺ���
				// -> while( �� �� ) �Ǵ� while( ���� �̸� ) : ������ �ڷ�����
				// boolean �����̸� = true;
				while (true) {

					if (socket == null || socket.isClosed()) {

						socket = new Socket(MyChattingServerClass.SERVER_IP_ADDR, MyChattingServerClass.SERVER_PORT_NO);

					}

					outputStream = socket.getOutputStream();

					bw = new BufferedWriter(new OutputStreamWriter(outputStream));

					// ������ ������ �޽����� ����ڷκ��� �Է� �޽��ϴ�.
					System.out.print("������ ���� �޽����� �Է��ϼ���(�ߴ��� q): ");

					tempInputMessage = scanner.nextLine();

					if (tempInputMessage == null || tempInputMessage.length() == 0) {
						// ���� ���� �ϱ� �������� ���� ����
						// socket.close();
						break;
					}

					bw.write(tempInputMessage);
					bw.flush();
					bw.close();

					// ���� �߰��� ���� : ����ڰ� q�� �Է��ϸ� �ݺ��� Ż��
					// compareToIgnoreCase( ) : ��ҹ��ڸ� �˻� ���� ����
					// -> ���ϰ��� �ϴ� ���ĺ� ���ڰ� q�� ��� 0�� ��ȯ
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

			// �� ����� �޸� �Ǵ� �����ͺ��̽� �Ǵ� ���� ���� �޸𸮿���
			// �����մϴ�. -> ��Ʈ��(�޸�)�� ����

			try {

				// ���� ���� �ݱ�(close() �Լ� ����)
				if (socket != null) {
					socket.close();
					System.out.println("socket ���� ����");
					socket = null;
				}

				// ��ĳ�� ���� �ݱ�
				if (scanner != null) {
					scanner.close();
					System.out.println("scanner ���� ����");
					scanner = null;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}

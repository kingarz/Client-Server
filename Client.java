import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;


public class Client  {
	public byte[] byteToTransfer;

	public void main(String[] args)throws IOException  {

		String host;
		int port;

		if (args.length == 0) {
			host = "localhost";
			port = 9996;
		} else {
			host = args[0];
			String portStr = args[1];
			try {
				port = Integer.parseInt(portStr);
			} catch (NumberFormatException nfe) {
				System.out.println("Uuups, z�y numer portu. Prze��czam na domyslny port: 9999");
				port = 9996;
			}
		}
		List<Point> pLista = new ArrayList<Point>();
		Socket skt = new Socket(host, port);
		try {
			// Pr�ba po��czenia z serwerem
			System.out.println("Klient: Pr�ba pod��czenia do serwera jako host-" + host + " port: " + port + '.');
			

			// Opcje odczytu i zapisu z i do strumienia
			// Serializacja Punkt�w
			try {
				//
				// ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
				ObjectInputStream in = new ObjectInputStream(skt.getInputStream());
				try {
					Object object = in.readObject();
					pLista = (ArrayList<Point>) object;
					System.out.println(pLista.get(1));
				} catch (ClassNotFoundException e) {
					System.out.println("The title list has not come from the server");
					e.printStackTrace();
				}

				/*
				 * out.writeObject(ReadGraph.WList); out.close();
				 * byteOut.close(); byteToTransfer = out.toString().getBytes();
				 */

			} catch (IOException i) {
				i.printStackTrace();
			}
			skt.close();/*
				 * ByteArrayOutputStream bao = new ByteArrayOutputStream();
				 * ObjectOutputStream oos = new ObjectOutputStream(bao);
				 * oos.writeObject(pkt); oos.close();
				 * 
				 * byte[] byteToTransfer = oos.getBytes();
				 */
			// transfer
			/*
			 * BufferedReader Input = new BufferedReader(new
			 * InputStreamReader(skt.getInputStream())); //odczyt PrintStream
			 * Output = new PrintStream(skt.getOutputStream());
			 * Output.write(byteToTransfer); //Przes�anie sprawdzaj�cej
			 * wiadomo�ci na serwer: Output.println("Klient: Siema Heniu!");
			 * 
			 * //Sprawdzenie, czy serwer odpowiedzia�. String
			 * buf=Input.readLine(); if(buf !=null){
			 * System.out.println("Klient: Odpowied� serwera [ "+buf+" ]"); }
			 * else System.out.println("Klient: Brak odpowiedzi z serwera.");
			 * 
			 * // Zamkni�cie po��czenia ze strony klienta
			 */
			skt.close();
			System.out.println("Klient - Od��czony");

		} catch (IOException ex) {
			ex.printStackTrace();
			System.out.println("Uuuups, co� si� skopa�o. nie podzia�am!");
		}
	}
}
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
public class Server {
	 public static void main(String[] args) throws ClassNotFoundException,IOException {
		   ServerSocket myServerSocket = new ServerSocket(9996);
		   
		 List<Point> TList = new ArrayList<Point>();
	        for(int i=0; i<ReadGraph.WList.size(); i++)
	        {
	        	TList.add(i, ReadGraph.WList.get(i));
	        }  
	       
	        try {
	            while (true) {
	            	Socket skt = myServerSocket.accept();
	                try {
	                	 // Oczekiwanie na po³¹czenie od hosta
	    	            System.out.println("Serwer: Start na hoœcie-"
	    	            +InetAddress.getLocalHost().getCanonicalHostName()
	    	            +" port: "+myServerSocket.getLocalPort());
	    	            
	                	//wstawiæ serializacje etc etc
	                	 byte[] bytesFromSocket = ReadGraph.WList.toString().getBytes();
	     	            ByteArrayInputStream bis = new ByteArrayInputStream(bytesFromSocket);
	     	            ObjectInputStream ois = new ObjectInputStream(bis);
	     	            List<Point> pkt = (List<Point>) ois.readObject();
	     	            ObjectOutputStream objectOutput = new ObjectOutputStream(skt.getOutputStream());
	                     objectOutput.writeObject(pkt);   
	                   // PrintWriter out =
	                        new PrintWriter(skt.getOutputStream(), true);
	                    
	                    //out.println(pkt);
	                } finally {
	                    skt.close();
	                }
	            }
	        }
	        finally {
	            myServerSocket.close();
	        }
	 			
	        	
	        	
	          //stworzenie gniazda servwera i przypisanie mu portu (tu 9999)

	           
	            //Opcje odczytu i zapisu z i do strumienia
	                       
            
	           /* if(pkt!=null)
	            {
	            	System.out.println("ook");
	            }*/
	           // BufferedReader Input = new BufferedReader(new InputStreamReader(skt.getInputStream())); //odczyt
	            //PrintStream Output = new PrintStream(skt.getOutputStream());                            //zapis

	            //Próba odczytania wejœcia ze strumienia
	           // String buf = Input.readLine();

	            //Sprawdzenie, czy serwer odebra³ wiadomoœæ i próba odpisania hostowi
	            /*if (buf !=null){
	                System.out.println("Serwer, odczyt: [ "+buf+" ]");
	                Output.print("Serwer: No siemka!"); //OdpowiedŸ dla hosta w przypadku odebrania wiadomoœci  
	            }
	            // Zamkniêcie po³¹czenia ze strony serwera*/
	            
	   

}
}
